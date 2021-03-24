package egovframework.kf.common.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.DCUtil;
import egovframework.kf.common.FileUtil;
import egovframework.kf.common.dao.CommonDAO;
import egovframework.kf.common.service.CommonService;
import egovframework.kf.common.vo.PagingVO;
import egovframework.kf.common.vo.RecentVO;
import egovframework.kf.common.vo.RepoInfoVO;
import egovframework.kf.common.vo.UploadVO;
import egovframework.kf.dao.RestModule;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.kepri.service.DocService;
import egovframework.kf.kepri.vo.DocumentVO;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;

	/** DocService */
	@Resource(name = "docService")
	private DocService docService;
	
	/** 엔진 공통 유틸 */
	@Resource(name = "dcUtil")
	private DCUtil dcUtil;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** file util Setting */
	@Resource(name = "fileUtil")
	private FileUtil fileUtil;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	private EgovPropertyService konanPropertiesService;
	
	/** REST API */
	@Resource(name = "restModule")
	private RestModule restModule;
	
	
	private static final Logger logger = LoggerFactory.getLogger(CommonService.class);
	
	@Override
	public List<Map<String, String>> selectPowerCompList(Map map) throws Exception {
		

		ParameterVO paramVO = new ParameterVO();
		paramVO.setCategory(map.get("cate").toString());
		paramVO.setMenu(map.get("isPF").toString());
		paramVO.setPageNum(1);
		paramVO.setPageSize(100);
		
		List<Map<String, String>> powerCompList = commonDAO.selectPowerCompList(map);
		
		// 지식 DB 성능일 경우 DB에서 해당 정보를 조회한다.
		if ( konanPropertiesService.getString("cate_perform").equals(paramVO.getCategory()) && "Y".equals(map.get("isPF").toString()) ) {
			List<Map<String, String>> powerCompPerformList = commonDAO.selectListPerform(map);
			
			for ( int i=0; i<powerCompList.size(); i++ ) {
				
				// 엔진에서 조회 한 결과가 있을 경우 DB에서 조회한 정보에 count값을 추가한다.
				if ( powerCompPerformList != null && powerCompPerformList.size() > 0 ) {
					for ( int j=0; j<powerCompPerformList.size(); j++ ) {
		
						if ( powerCompList.get(i).get("POWER_COMP_NM").equals(powerCompPerformList.get(j).get("POWER_COMP_NM"))) {
							powerCompList.get(i).put("count", String.valueOf(powerCompPerformList.get(j).get("COUNT")));
						}
					}
				}
				
				// count key가 없을 경우 count를 0으로 셋팅해춘다.
				if ( !powerCompList.get(i).containsKey("count") ) powerCompList.get(i).put("count", String.valueOf(0));
			}
		} else {
			RestResultVO powerCompEngineList = commonDAO.selectPowerCompListEngine(paramVO);
			for ( int i=0; i<powerCompList.size(); i++ ) {
				
				// 엔진에서 조회 한 결과가 있을 경우 DB에서 조회한 정보에 count값을 추가한다.
				if ( powerCompEngineList != null && powerCompEngineList.getTotal() > 0 ) {
					for ( int j=0; j<powerCompEngineList.getResult().size(); j++ ) {
						if ( powerCompList.get(i).get("POWER_COMP_NM").equals(powerCompEngineList.getResult().get(j).get("POWER_COMP_NM"))) {
							powerCompList.get(i).put("count", powerCompEngineList.getResult().get(j).get("count"));
						}
					}
				}
				
				// count key가 없을 경우 count를 0으로 셋팅해춘다.
				if ( !powerCompList.get(i).containsKey("count") ) powerCompList.get(i).put("count", String.valueOf(0));
			}
		}
		
		
		
		return powerCompList;
	}
	
	@Override
	public List<Map<String, String>> selectPowerStList(Map map) throws Exception {
		ParameterVO paramVO = new ParameterVO();
		paramVO.setCategory(map.get("cate").toString());
		paramVO.setPowerComp(map.get("powerCompName").toString());
		paramVO.setPageNum(1);
		paramVO.setPageSize(100);
		
		List<Map<String, String>> powerStList = commonDAO.selectPowerStList(map);
		
		// 성능일 경우 DB에서 해당 정보를 조회한다.
		if ( konanPropertiesService.getString("cate_perform").equals(paramVO.getCategory()) && "Y".equals(map.get("isPF").toString()) ) {
			// 성능 group by query 사업소, 호기 구분자값
			map.put("menu", "powerSt");
			List<Map<String, String>> powerStPerformList = commonDAO.selectListPerform(map);
			
			for ( int i=0; i<powerStList.size(); i++ ) {
				
				// 엔진에서 조회 한 결과가 있을 경우 DB에서 조회한 정보에 count값을 추가한다.
				if ( powerStPerformList != null && powerStPerformList.size() > 0 ) {
					for ( int j=0; j<powerStPerformList.size(); j++ ) {
		
						if ( powerStList.get(i).get("POWER_ST_NM").equals(powerStPerformList.get(j).get("POWER_ST_NM"))) {
							powerStList.get(i).put("count", String.valueOf(powerStPerformList.get(j).get("COUNT")));
						}
					}
				}
				
				// count key가 없을 경우 count를 0으로 셋팅해춘다.
				if ( !powerStList.get(i).containsKey("count") ) powerStList.get(i).put("count", String.valueOf(0));
			}
		} else {
			paramVO.setMenu(map.get("isPF").toString());
			RestResultVO powerStEngineList = commonDAO.selectPowerStListEngine(paramVO);
			
			for ( int i=0; i<powerStList.size(); i++ ) {
				
				// 엔진에서 조회 한 결과가 있을 경우 DB에서 조회한 정보에 count값을 추가한다.
				if ( powerStEngineList != null && powerStEngineList.getTotal() > 0 ) {
					for ( int j=0; j<powerStEngineList.getResult().size(); j++ ) {
						if ( powerStList.get(i).get("POWER_ST_NM").equals(powerStEngineList.getResult().get(j).get("POWER_ST_NM"))) {
							powerStList.get(i).put("count", powerStEngineList.getResult().get(j).get("count"));
						}
					}
				}
				
				// count key가 없을 경우 count를 0으로 셋팅해춘다.
				if ( !powerStList.get(i).containsKey("count") ) powerStList.get(i).put("count", String.valueOf(0));
			}
		}
		
		return powerStList;
	}
	
	@Override
	public List<Map<String, String>> selectStNoList(Map map) throws Exception {
		ParameterVO paramVO = new ParameterVO();
		paramVO.setCategory(map.get("cate").toString());
		paramVO.setPowerComp(map.get("powerCompName").toString());
		paramVO.setPowerSt(map.get("powerStName").toString());
		paramVO.setPageNum(1);
		paramVO.setPageSize(100);

		List<Map<String, String>> stNoList = commonDAO.selectStNoList(map);
		
		// 성능일 경우 DB에서 해당 정보를 조회한다.
		if ( konanPropertiesService.getString("cate_perform").equals(paramVO.getCategory()) && "Y".equals(map.get("isPF").toString()) ) {
			// 성능 group by query 사업소, 호기 구분자값
			map.put("menu", "stNo");
			List<Map<String, String>> stNoPerformList = commonDAO.selectListPerform(map);
			
			for ( int i=0; i<stNoList.size(); i++ ) {
				
				// 엔진에서 조회 한 결과가 있을 경우 DB에서 조회한 정보에 count값을 추가한다.
				if ( stNoPerformList != null && stNoPerformList.size() > 0 ) {
					for ( int j=0; j<stNoPerformList.size(); j++ ) {
		
						if ( stNoList.get(i).get("ST_NO").equals(stNoPerformList.get(j).get("ST_NO"))) {
							stNoList.get(i).put("count", String.valueOf(stNoPerformList.get(j).get("COUNT")));
						}
					}
				}
				
				// count key가 없을 경우 count를 0으로 셋팅해춘다.
				if ( !stNoList.get(i).containsKey("count") ) stNoList.get(i).put("count", String.valueOf(0));
			}
		} else {
			paramVO.setMenu(map.get("isPF").toString());
			RestResultVO stNoEngineList = commonDAO.selectStNoListEngine(paramVO);
			
			for ( int i=0; i<stNoList.size(); i++ ) {
				
				// 엔진에서 조회 한 결과가 있을 경우 DB에서 조회한 정보에 count값을 추가한다.
				if ( stNoEngineList != null && stNoEngineList.getTotal() > 0 ) {
					for ( int j=0; j<stNoEngineList.getResult().size(); j++ ) {
						if ( stNoList.get(i).get("ST_NO").equals(stNoEngineList.getResult().get(j).get("ST_NO"))) {
							stNoList.get(i).put("count", stNoEngineList.getResult().get(j).get("count"));
						}
					}
				}
				
				// count key가 없을 경우 count를 0으로 셋팅해춘다.
				if ( !stNoList.get(i).containsKey("count") ) stNoList.get(i).put("count", String.valueOf(0));
			}
		}
		
		return stNoList;
	}
	
	@Override
	public List<Map<String, String>> selectPartNameList(Map map, Map<String, String> cateInfoMap) throws Exception {
		ParameterVO paramVO = this.setParameter(map);
		
		// 성능일 경우 DB에서 해당 정보를 조회한다.
		if ( konanPropertiesService.getString("cate_perform").equals(paramVO.getCategory()) ) {
			// 성능 group by query 사업소, 호기 구분자값
			map.put("menu", "partName");
			List<Map<String, String>> partNameList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> partNamePerformList = commonDAO.selectListPerform(map);
			
			for ( int i=0; i<partNamePerformList.size(); i++ ) {
				Map<String, String> tempMap = new HashMap<String, String>();
				
				tempMap.put("PARTNAME", partNamePerformList.get(i).get("PARTNAME"));
				tempMap.put("count", String.valueOf(partNamePerformList.get(i).get("COUNT")));
				partNameList.add(tempMap);
				
				tempMap = null;
			}
			
			return partNameList;
		} else {
			
			String partName = cateInfoMap.get("partName");
			RestResultVO partNameEngineList = null;
			
			if(!"dashboard".equals(paramVO.getCallLoc())) {
				if ( !"all".equals(paramVO.getPartName()) ) paramVO.setPartName("all");
				if ( !"all".equals(paramVO.getPartMid()) ) paramVO.setPartMid("all");
				if ( !"all".equals(paramVO.getSymptomKwd()) ) paramVO.setSymptomKwd("all");
				if ( !"all".equals(paramVO.getActionKwd()) ) paramVO.setActionKwd("all");
			}
			
			if ( !"".equals(partName) ) {
				partNameEngineList = commonDAO.selectListEngine(paramVO, partName + ",count(*)");
			} else {
				logger.debug("[selectPartNameList] partName is empty.");
			}
			
			return partNameEngineList.getResult();
		}
		
	}
	
	@Override
	public List<Map<String, String>> selectPartMidList(Map map, Map<String, String> cateInfoMap) throws Exception {
		ParameterVO paramVO = this.setParameter(map);
		String partMid = cateInfoMap.get("partMid");
		RestResultVO partMidEngineList = null;
			
//		String field = "";
//		if (konanPropertiesService.getString("cate_boiler").equals(paramVO.getCategory()) ) field = konanPropertiesService.getString("boilerPartMid");
//		else if ( konanPropertiesService.getString("cate_gen_prev").equals(paramVO.getCategory())) field = konanPropertiesService.getString("genPartMid");
//		else if ( konanPropertiesService.getString("cate_gt_turbine").equals(paramVO.getCategory())
//				|| konanPropertiesService.getString("cate_st_turbine").equals(paramVO.getCategory()) ) field = konanPropertiesService.getString("turbinePartMid");
		
		if(!"dashboard".equals(paramVO.getCallLoc())) {
			if ( !"all".equals(paramVO.getPartMid()) ) paramVO.setPartMid("all");
			if ( !"all".equals(paramVO.getSymptomKwd()) ) paramVO.setSymptomKwd("all");
			if ( !"all".equals(paramVO.getActionKwd()) ) paramVO.setActionKwd("all");
		}
		
		if ( !"".equals(partMid) ) {
			partMidEngineList = commonDAO.selectListEngine(paramVO, partMid + ",count(*)");
		} else {
			logger.debug("[selectPartMidList] partMid is empty.");
		}
//		RestResultVO partMidEngineList = commonDAO.selectListEngine(paramVO, field + ",count(*)");
//		for ( int i=0; i<partMidEngineList.getResult().size(); i++ ) {
//			logger.debug("[partMid] " + partMidEngineList.getResult().get(i).get(field));
//		}
		return partMidEngineList.getResult();
		
	}
	
	@Override
	public List<Map<String, String>> selectSymptomKwdList(Map map, Map<String, String> cateInfoMap) throws Exception {
		ParameterVO paramVO = this.setParameter(map);
		
		if(!"dashboard".equals(paramVO.getCallLoc())) {
			if ( !"all".equals(paramVO.getSymptomKwd()) ) paramVO.setSymptomKwd("all");
			if ( !"all".equals(paramVO.getActionKwd()) ) paramVO.setActionKwd("all");
		}
		RestResultVO symptomKwdEngineList = commonDAO.selectListEngine(paramVO, "SYMPTOM_KWD,count(*)");
//		for ( int i=0; i<symptomKwdEngineList.getResult().size(); i++ ) {
//			logger.debug("[sym] " + symptomKwdEngineList.getResult().get(i).get("SYMPTOM_KWD"));
//		}
		return symptomKwdEngineList.getResult();
		
	}
	
	@Override
	public List<Map<String, String>> selectActionKwdList(Map map, Map<String, String> cateInfoMap) throws Exception {
		ParameterVO paramVO = this.setParameter(map);
		
		if ( !"all".equals(paramVO.getActionKwd()) ) paramVO.setActionKwd("all");
		
		RestResultVO actionKwdEngineList = commonDAO.selectListEngine(paramVO, "ACTION_KWD,count(*)");
//		for ( int i=0; i<actionKwdEngineList.getResult().size(); i++ ) {
//			logger.debug("[action] " + actionKwdEngineList.getResult().get(i).get("ACTION_KWD"));
//		}
		return actionKwdEngineList.getResult();
		
	}
	
	/**
	 * map 정보를 체크하여 ParameterVO에 Set
	 */
	@Override
	public ParameterVO setParameter(Map map) {
		ParameterVO paramVO = new ParameterVO();
		
		if ( map.containsKey("callLoc") ) {paramVO.setCallLoc(map.get("callLoc").toString());}else{paramVO.setCallLoc("");};
		if ( map.containsKey("cate") ) paramVO.setCategory(map.get("cate").toString());
		if ( map.containsKey("powerCompName") ) paramVO.setPowerComp(map.get("powerCompName").toString());
		if ( map.containsKey("powerStName") ) paramVO.setPowerSt(map.get("powerStName").toString());
		if ( map.containsKey("stNo") ) paramVO.setStNo(map.get("stNo").toString());
		if ( map.containsKey("partName") ) paramVO.setPartName(map.get("partName").toString());
		if ( map.containsKey("partMid") ) paramVO.setPartMid(map.get("partMid").toString());
		if ( map.containsKey("symptomKwd") ) paramVO.setSymptomKwd(map.get("symptomKwd").toString());
		if ( map.containsKey("actionKwd") ) paramVO.setActionKwd(map.get("actionKwd").toString());
		paramVO.setPageNum(1);
		paramVO.setPageSize(100);
		
		return paramVO; 
	}

	@Override
	public RepoInfoVO setRepoInfo(List<Map<String, String>> list, String cate) throws Exception {
		RepoInfoVO repoInfo = new RepoInfoVO();
		String repo_idx = "REPO_IDX";
		String equip_idx = "EQUIP_IDX";
		
		repoInfo.setRepo_kind(list.get(0).get("REPO_KIND"));
		repoInfo.setReport_nm(list.get(0).get("REPORT_NM"));
		repoInfo.setDept(list.get(0).get("DEPT"));
		repoInfo.setReporter(list.get(0).get("REPORTER"));
		repoInfo.setPublish_ym(list.get(0).get("PUBLISH_YM"));
		repoInfo.setRepo_file_nm(list.get(0).get("REPO_FILE_NM"));
		repoInfo.setReport_text(list.get(0).get("REPORT_TEXT"));				
		repoInfo.setAuto_classfication(list.get(0).get("AUTO_CLASSFICATION"));
		repoInfo.setPower_comp_nm(list.get(0).get("POWER_COMP_NM"));
		repoInfo.setPower_st_nm(list.get(0).get("POWER_ST_NM"));
		repoInfo.setSt_no(list.get(0).get("ST_NO"));
		repoInfo.setImg_files(list.get(0).get("IMG_FILES"));
		repoInfo.setMd5_key(list.get(0).get("MD5_KEY"));
		
		if ( konanPropertiesService.getString("cate_gt_turbine").equals(cate) || konanPropertiesService.getString("cate_st_turbine").equals(cate) ) {	
			repoInfo.setRepo_kind2(list.get(0).get("REPO_KIND2"));
		} else if ( konanPropertiesService.getString("cate_gen_prev").equals(cate) || konanPropertiesService.getString("cate_gen_ins").equals(cate) ) {
			repoInfo.setRepo_kind2(list.get(0).get("REPO_KIND2"));
			repoInfo.setTest_ym(list.get(0).get("TEST_YM"));
		} else if ( konanPropertiesService.getString("cate_perform").equals(cate) ) {
			repoInfo.setTest_ym(list.get(0).get("TEST_YM"));
		}
		
		repoInfo.setRepo_idx(list.get(0).get(repo_idx));
		repoInfo.setEquip_idx(list.get(0).get(equip_idx));;
		repoInfo.setCategory(cate);
		
		return repoInfo;
	}

	@Override
	public PagingVO setPagingModel(int pageNum, int totalCount) {
		PagingVO paging = new PagingVO();
		paging.setPageNo(pageNum);
		paging.setPageSize(10);
		paging.setTotalCount(totalCount);
		
		return paging;
	}

	@Override
	public List<RecentVO> getRecentByUser(int user_id) throws Exception {
		return commonDAO.getRecentByUser(user_id);
	}

	@Override
	public void updateFileNmAndSizeToMD5() throws Exception {
		List<Map> reportList = commonDAO.selectReportlist();
		
		if ( reportList != null ) {
			for ( Map report : reportList ) {
				UploadVO uploadVO = new UploadVO();
				
				logger.debug("[updateFileNmAndSizeToMD5] fileName :: " + report.get("REPO_FILE_NM").toString());
				
				String size = fileUtil.getFileSize(report.get("FILE_PATH").toString());
				logger.debug("[updateFileNmAndSizeToMD5] size :: " + size);
				uploadVO.setUploadIdx(Integer.parseInt(report.get("REPO_IDX").toString()));
				uploadVO.setFileName(report.get("REPO_FILE_NM").toString());
				uploadVO.setFileSize(size);
				uploadVO.setMd5FileNmAndSize(fileUtil.fileInfoToMD5(uploadVO));
				
				int r = commonDAO.updateReportMd5(uploadVO);
				logger.debug("[updateFileNmAndSizeToMD5] update result :: " + r);
			}
		}
	}
	
	@Override
	public String getClassfication(String filePath, String fileName) throws Exception {
		return commonDAO.getClassfication(filePath, fileName);
	}

	@Override
	public Map<String, Object> getClassficationNFileBody(String filePath, String fileName) throws Exception {
		return commonDAO.getClassficationNFileBody(filePath, fileName);
	}

	@Override
	public String mergeImg(List<String> imgList) throws Exception {
		List<BufferedImage> bufferedImagelist = null; 
		BufferedImage image = null;
		String imgName = "";
		
		if ( imgList.size() > 0 ) {
			bufferedImagelist = new ArrayList<BufferedImage>();
			int totalHeight = 0;
			int totalWidth = 0;
			int i = 0;
			String sNum = "";
			String eNum = "";
			
			for ( i=0; i<imgList.size(); i++ ) {
				
				if ( i == 0 ) sNum = imgList.get(i).split("_")[1].split("\\.")[0].replaceAll("img", "");
				if ( (i+1) == imgList.size() ) eNum = imgList.get(i).split("_")[1].split("\\.")[0].replaceAll("img", "");
				
				image = ImageIO.read(new File("C:\\KEPRI\\temp\\img\\196bac93badb659a104fd5008e04e9d6_img108.jpg"));
				
				// 합쳐질 이미지의 높이와 넓이 계산
				totalHeight += image.getHeight();
				totalWidth = image.getWidth();
				
				bufferedImagelist.add(image);
			}
			
			BufferedImage mergedImage = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) mergedImage.getGraphics();
			
			int addHeight = 0;
			graphics.setBackground(Color.WHITE);
			for ( i=0; i<bufferedImagelist.size(); i++ ) {
				graphics.drawImage(bufferedImagelist.get(i), 0, addHeight, null);
				addHeight += bufferedImagelist.get(i).getHeight();
			}
			
			imgName = imgName + "_img" + sNum + "to" + eNum;
			ImageIO.write(mergedImage, "png", new File(imgName));
		}
		return imgName;
	}

	@Override
	public Map<String, String> selectMergeImgCheck(Map map) throws Exception {
		return commonDAO.selectMergeImgCheck(map);
	}

	@Override
	public void fileDownload(Map<String, String> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String realPath = "";
		String filename = "";
		
		if ( !konanPropertiesService.getString("cate_devdoc").equals(map.get("cate")) ) {
			realPath = commonDAO.selectDownloadPath(map);
		}
		
    	if ( realPath != null && !"".equals(realPath) ) {
    		logger.debug("[fileDownload] DB filePath :: " + realPath);
    	} else {
    		realPath = commonUtil.getFilepath(map.get("cate"), "");
    		realPath = realPath + File.separator + map.get("fileName");
    	}
        
        try {
            String browser = request.getHeader("User-Agent"); 
            //파일 인코딩 
            if (browser.contains("MSIE") || browser.contains("Trident")
                    || browser.contains("Chrome")) {
                filename = URLEncoder.encode(map.get("fileName"), "UTF-8").replaceAll("\\+", "%20");
            } else {
                filename = new String(map.get("fileName").getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException ex) {
            logger.debug("UnsupportedEncodingException");
        }
        
        
        logger.debug("[fileDownload] filePath :: " + realPath);
        File file1 = new File(realPath);
        if (!file1.exists()) {
        	logger.debug("file not found.");
            return ;
        }
         
        // 파일명 지정        
        response.setContentType("application/octer-stream");
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(realPath);
 
            int ncount = 0;
            byte[] bytes = new byte[512];
 
            while ((ncount = fis.read(bytes)) != -1 ) {
                os.write(bytes, 0, ncount);
            }
            fis.close();
            os.close();
            
        } catch (FileNotFoundException ex) {
            logger.debug("FileNotFoundException");
        } catch (IOException ex) {
            logger.debug("IOException");
        }
	}
	
	@Override
	public void allDownLoad(Map<String, String> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ZipOutputStream zout = null; 
		String zipName = map.get("title").replaceAll(" ", "_") + "_" + new SimpleDateFormat("yyyyMMddHHmmSS").format(Calendar.getInstance().getTime()) + "_File.zip"; //ZIP 압축 파일명 
		String tempPath = konanPropertiesService.getString("uploadPathLinux") + "temp/"; // 임시폴더에 저장후 다운로드가 끝나면 삭제한다.
		List<Map<String,String>> list = null;
		File zipFile = null;
		try{
			if ( konanPropertiesService.getString("cate_devdoc").equals(map.get("cate")) ) {
				list = docService.selectDocumentFileListByDocID(map.get("doc_idx"));
			}
			
			try {
	            String browser = request.getHeader("User-Agent"); 
	            //파일 인코딩 
	            if (browser.contains("MSIE") || browser.contains("Trident")
	                    || browser.contains("Chrome")) {
	            	zipName = URLEncoder.encode(zipName, "UTF-8").replaceAll("\\+", "%20");
	            } else {
	            	zipName = new String(zipName.getBytes("UTF-8"), "ISO-8859-1");
	            }
	        } catch (UnsupportedEncodingException ex) {
	            logger.debug("UnsupportedEncodingException");
	        }
			
			if(list != null && list.size() > 0) {
				zout = new ZipOutputStream(new FileOutputStream(tempPath + zipName)); 
				byte[] buffer = new byte[1024]; 
				FileInputStream in = null; 
				
				for(Map<String,String> files : list) {
					File file = new File(files.get("FILE_FULL_PATH"));
					if(file.exists()) {
						in = new FileInputStream(files.get("FILE_FULL_PATH")); //압축 대상 파일
						zout.putNextEntry(new ZipEntry(files.get("FILE_NM"))); //압축파일에 저장될 파일명 
						
						int len; 
						while((len = in.read(buffer)) > 0){ 
							zout.write(buffer, 0, len); //읽은 파일을 ZipOutputStream에 Write 
						} 
						
						zout.closeEntry(); 
						in.close(); 
					}
				}
				
				zout.close(); //ZIP파일 압축 END //파일다운로드 START 
				response.setContentType("application/zip"); 
				response.addHeader("Content-Disposition", "attachment;filename=" + zipName); 
				FileInputStream fis = new FileInputStream(tempPath + zipName); 
				BufferedInputStream bis = new BufferedInputStream(fis); 
				ServletOutputStream so = response.getOutputStream(); 
				BufferedOutputStream bos = new BufferedOutputStream(so); 
				
				int n = 0; 
				while((n = bis.read(buffer)) > 0){ 
					bos.write(buffer, 0, n); 
					bos.flush(); 
				}
				
				if(bos != null) bos.close(); 
				if(bis != null) bis.close(); 
				if(so != null) so.close(); 
				if(fis != null) fis.close(); //파일다운로드 END
			}
		}catch(IOException e){ //Exception 
			e.printStackTrace();
		}finally{ 
			if (zout != null){ zout = null; }
			zipFile = new File(tempPath + zipName);
			if(zipFile.exists()) zipFile.delete();
		}
		
	}
	
	@Override
	public void deleteAttachFile(List<Map<String,String>> list) throws Exception{
		File file = null;
		try {
			if(list != null && list.size() > 0) {
				for(Map<String,String> files : list) {
					file = new File(files.get("FILE_FULL_PATH"));
					if(file.exists()) {
						file.delete();
					}
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public RestResultVO getVRPartList(Map<String, String> map) throws Exception{
		String fields ="part2_cd,part3_cd,part4_cd,part4_nm";
		
		return commonDAO.searchVRPartList(map,fields);
	}
	
	/**
	 * PART2BODY(추출 된 지식의 원문영역)에 IMG 태그가 있을 경우 <img> 태그로 변경을 시켜주는 로직
	 * 가스터빈의 경우 특정 PDF파일들의 이미지가 잘려서 저장이 되어 잘린 이미지를 계산하여 MERGE(합치기)한 이미지로 대체하는 로직도 포함이 되어 있다.
	 * MERGE IMG의 경우 tb_merge_image 테이블을 참고한다.
	 */
	public Map<String, String> changeFileBodyImgToText(Map<String,String> data, String cate) throws Exception {
		// TODO Auto-generated method stub
		String part2body = data.get("PART2BODY");
		String imgFiles = data.get("IMG_FILES");
		String imgTxts = data.get("IMG_TXTS");
		String[] splitData = data.get("FILENAME").split("\\.");
		String ext = splitData[splitData.length-1];
		String resultImg = "";
		String resultTxt = "";

		logger.debug("[changeFileBodyImgToText] imgTxts :: " + imgTxts);
		Map<String, String> dbMap = new HashMap<String, String>();
		Map<String, String> selectMap = new HashMap<String, String>();
		String[] paras = imgFiles.split("\\|");
		String[] imgTxtList = imgTxts.split("\\|");
		
		logger.debug("[changeFileBodyImgToText] part2body :: " + part2body);
		
		dbMap.put("md5_key", data.get("MD5_KEY"));
		dbMap.put("gubun_no", data.get("GUBUN_NO"));
		dbMap.put("cate", cate);
		
		// 합쳐진 이미지가 있는지 조회
		selectMap = commonDAO.selectMergeImgCheck(dbMap);
		String makeImg = ""; // 합쳐진 이미 리스트를 저장하는 문자열
		String makeImgTxt = "";
		
		for ( int i=0; i<paras.length; i++ ) {
			
			String[] words = paras[i].split("\\`");
			boolean isMerged = false;
			
			// 이미지가 2개이상이거나 파일 확장자가 pdf일 경우에만
			if ( words.length > 1 && "pdf".equals(ext)) {
				logger.debug("[changeFileBodyImgToText] merge job start");
				if ( selectMap != null && selectMap.containsKey("MERGE_IMG") ) {
					logger.debug("[changeFileBodyImgToText] merge img exist!!");
					resultImg = selectMap.get("MERGE_IMG");
					resultTxt = selectMap.get("MERGE_TXT");
				}
				else {
					// 합쳐진 이미지가 없음
					logger.debug("[changeFileBodyImgToText] merge Img is no ㅠㅠㅠㅠ");
					String checkStr = "";
					String gubunStr = "";
					List<Integer> gubunList = new ArrayList<Integer>();
					
					// IMG..INDEX 그룹핑 
					for ( int j=0; j<words.length; j++ ) {
						String checkNum = words[j].split("_")[1].split("\\.")[0].replaceAll("img", "");
						
						if ( j==0 ) gubunStr = checkNum + "to";
						
						checkStr += "..IMG_INDEX:" + checkNum + "\n";
						if ( part2body.indexOf(checkStr) == -1 && j > 0) {
							
							String endNum = words[j-1].split("_")[1].split("\\.")[0].replaceAll("img", "");
							gubunList.add(j);
							checkStr = "";
							gubunStr += endNum + "|" + checkNum + "to";
						}
						
						if ( (j+1) == words.length ) {
							gubunList.add(j+1);
							gubunStr += checkNum;
						}
					}

					logger.debug("[changeFileBodyImgToText] gubun Str :: " + gubunStr );
					List<List<String>> imgGroupList = new ArrayList<List<String>>();
					List<String> imgList = null;
					int startGroupIdx = 0;
					for ( int j=0; j<gubunList.size(); j++ ) {
						imgList = new ArrayList<String>();
						for ( int k=startGroupIdx; k<gubunList.get(j); k++ ) {
							imgList.add(words[k]);
						}
						startGroupIdx = gubunList.get(j);
						imgGroupList.add(imgList);
					}
					
					int startMergeIdx = 0;
					if ( imgGroupList.size() > 0 ) {
						
						for ( List<String> list : imgGroupList ) {
							List<String> resultList = new ArrayList<String>();

							// 이미지 합치는 로직
							resultList = this.mergeImg(list, startMergeIdx, resultList);
							
							for ( int j=0; j<resultList.size(); j++ ) {
								
								if ( "".equals(makeImg) ) {
									makeImg += resultList.get(j);
									makeImgTxt += imgTxtList[i];
								} else {
									makeImg += "|" + resultList.get(j);
									makeImgTxt += "|" + imgTxtList[i];
								}
							}
						}
						
						resultImg = makeImg;
						resultTxt = makeImgTxt;
					} else {
						logger.debug("[changeFileBodyImgToText] not imgGroupList");
					}
				}
				
			} 
			else {
				if ( "".equals(resultImg) ) {
					resultImg += paras[i];
					resultTxt += imgTxtList[i];
				}
				else {
					resultImg += "|" + paras[i];
					resultTxt += "|" + imgTxtList[i];
				}
			}
		}
		
		logger.debug("[changeFileBodyImgToText] resultImg :: " + resultImg );
		logger.debug("[changeFileBodyImgToText] resultTxt :: " + resultTxt );
		
		if ( !"".equals(makeImg) ) {
			dbMap.put("merge_img", makeImg);
			dbMap.put("merge_txt", makeImgTxt);
			int r = 0;
			if ( selectMap == null ) {
				r = commonDAO.insertMergeImg(dbMap);
				logger.debug("[changeFileBodyImgToText] DB INSERT result >> " + r);
			} else {
				r = commonDAO.updateMergeImg(dbMap);
				logger.debug("[changeFileBodyImgToText] DB UPDATE result >> " + r);
			}
		}
		dbMap.put("IMG_FILES", resultImg);
		dbMap.put("IMG_TXTS", resultTxt);
		
		return dbMap;
	}
	
	private List<String> mergeImg(List<String> list, int startIdx, List<String> resultList) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String, Object> resultMap = commonUtil.mergeImg(list, startIdx);
		int lastIdx =  Integer.parseInt(resultMap.get("lastNum").toString());
		resultList.add(resultMap.get("mergeImgName").toString());
		
		if ( lastIdx > -1 && lastIdx < list.size()) {
			this.mergeImg(list, lastIdx, resultList);
		}
		
		return resultList;
	}
}
