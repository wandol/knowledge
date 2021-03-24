package egovframework.kf.kepri.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.dao.CommonDAO;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.kepri.dao.PfDAO;
import egovframework.kf.kepri.service.PfService;
import egovframework.kf.kepri.vo.CommentVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("pfService")
public class PfServiceImpl extends EgovAbstractServiceImpl implements PfService {
	
	/** PfDAO */
	@Resource(name = "pfDAO")
	private PfDAO pfDAO;
	
	/** CommonDAO */
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;

	private static final Logger logger = LoggerFactory.getLogger(PfService.class);
	
	@Override
	public RestResultVO PfSearch(ParameterVO paramVO) throws Exception {
		
		RestResultVO resultVO = pfDAO.pfSearch(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		
//		if ( !"Y".equals(paramVO.getIsImg()) ) {
			// BOILER, GT_TURBINE, ST_TURBINE, GEN_INS, GEN_PREV인지 확인용
		
		String cate = paramVO.getCategory();
		//	화면에 보여주는 이미지 확장자 
		String allowImgExt = konanPropertiesService.getString("allowImgExt");
		
		for ( int i=0; i<resultVO.getResult().size(); i++ ) {
			
			Map<String, String> result = resultVO.getResult().get(i);
			
			/////////////////////////////////////////////////////////////////////////// 지식 이미지 갯수 계산 및 추가 START
			// 발전기 절연진단은 IMG_FILES 필드가 없음
			if ( !konanPropertiesService.getString("cate_gen_ins").equals(cate) && !konanPropertiesService.getString("cate_gen_kepri").equals(cate)
					&& !konanPropertiesService.getString("cate_perform").equals(cate) ) {

				logger.debug("[PfSearch] category :: " + cate);
				Map<String, String> map = new HashMap<String, String>();
				map = this.changeFileBodyImgToText(resultVO.getResult().get(i), cate);
//				resultVO.getResult().get(i).put("IMG_FILES", map.get("IMG_FILES"));
//				resultVO.getResult().get(i).put("IMG_TXTS", map.get("IMG_TXTS"));
				logger.info("TEXTS :: " + map.get("IMG_TXTS"));
				logger.info("FILES :: " + map.get("IMG_FILES"));
//					이미지 확장자를 체크.하여 지울것 지우거 map을 리턴.
				commonUtil.checkAllowImgExt(resultVO.getResult().get(i), map.get("IMG_FILES"), map.get("IMG_TXTS"), allowImgExt);
//				int imgCount = 0;
//				// IMG_FILES에 구분자 | 또는 `가 있을 경우에만
//				if ( result.get("IMG_FILES").indexOf("|") > -1 || result.get("IMG_FILES").indexOf("`") > -1 ) {
//					imgCount = result.get("IMG_FILES").split("\\||\\`").length;
//				} else {	// 이미지가 하나인 경우
//					imgCount = 1;
//				}
//				resultVO.getResult().get(i).put("imgCount", String.valueOf(imgCount));
			}
			/////////////////////////////////////////////////////////////////////////// 지식 이미지 갯수 계산 및 추가 END 
		}
//		}
		
		return resultVO;
	}
	
	@Override
	public RestResultVO PfImageSearch(ParameterVO paramVO) throws Exception {
		RestResultVO resultVO = pfDAO.PfImageSearch(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		
		String cate = paramVO.getCategory();
		for ( int i=0; i<resultVO.getResult().size(); i++ ) {
			
			Map<String, String> result = resultVO.getResult().get(i);
			
			/////////////////////////////////////////////////////////////////////////// 지식 이미지 갯수 계산 및 추가 START
			// 발전기 절연진단은 IMG_FILES 필드가 없음
			if ( !konanPropertiesService.getString("cate_gen_ins").equals(cate) && !konanPropertiesService.getString("cate_gen_kepri").equals(cate)
					&& !konanPropertiesService.getString("cate_perform").equals(cate) ) {
				Map<String, String> map = new HashMap<String, String>();
				map = this.changeFileBodyImgToText(resultVO.getResult().get(i), cate);
				resultVO.getResult().get(i).put("IMG_FILES", map.get("IMG_FILES"));
				resultVO.getResult().get(i).put("IMG_TXTS", map.get("IMG_TXTS"));
			}
			/////////////////////////////////////////////////////////////////////////// 지식 이미지 갯수 계산 및 추가 END 
		}
		
		return resultVO;
	}
	
	@Override
	public RestResultVO detail(ParameterVO paramVO, String cate)  throws Exception {
		RestResultVO resultVO = pfDAO.detail(cate, paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		
		// 발전기 절연진단은 IMG_FILES 필드가 없음
		if ( !konanPropertiesService.getString("cate_gen_ins").equals(cate) && !konanPropertiesService.getString("cate_gen_kepri").equals(cate)
				&& !konanPropertiesService.getString("cate_perform").equals(cate) ) {
			Map<String, String> map = new HashMap<String, String>();
			map = this.changeFileBodyImgToText(resultVO.getResult().get(0), cate);
			resultVO.getResult().get(0).put("IMG_FILES", map.get("IMG_FILES"));
			resultVO.getResult().get(0).put("IMG_TXTS", map.get("IMG_TXTS"));
		}
		
		return resultVO;
	}

	@Override
	public List<CommentVO> detailComment(String category, String idx) throws Exception {
		// TODO Auto-generated method stub
		
		CommentVO comment = new CommentVO();
		
		String id = idx.split("_")[0];
		String gubun = idx.split("_")[1];
		
		comment.setRepo_key(id);
		comment.setGubun_no(gubun);
		comment.setCategory(category);
		
		// 검색 된 결과의 Comment가 몇개 달려있는지 가져온다.
		List<CommentVO> commentList = pfDAO.getPFCommentList(comment);
		
		return commentList;
	}

	@Override
	public String getSymData(ParameterVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		String result="";
		result = pfDAO.getSymData(paramVO);
		
		return result;
	}
	
	@Override
	public RestResultVO getSymptomKwdGroupBy(ParameterVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		String groupByFd = konanPropertiesService.getString("pfGroupField").split(",")[0];
		String orderByFd = konanPropertiesService.getString("groupByCount");
		String orderByVal = konanPropertiesService.getString("orderByValueDESC");
		int limit = konanPropertiesService.getInt("limit");
		
		RestResultVO resultVO = pfDAO.getGroupByKwdData(paramVO, groupByFd, orderByFd, orderByVal,  limit);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}
	
	@Override
	public RestResultVO getActionKwdGroupBy(ParameterVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		String groupByFd = konanPropertiesService.getString("pfGroupField").split(",")[1];
		String orderByFd = konanPropertiesService.getString("groupByCount");
		String orderByVal = konanPropertiesService.getString("orderByValueDESC");
		int limit = konanPropertiesService.getInt("limit");
		
		RestResultVO resultVO = pfDAO.getGroupByKwdData(paramVO, groupByFd, orderByFd, orderByVal,  limit);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}

	@Override
	public RestResultVO getPublishYMGroupBy(ParameterVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		String groupByFd = konanPropertiesService.getString("pfGroupField").split(",")[2];
		String orderByFd = groupByFd;
		String orderByVal = konanPropertiesService.getString("orderByValueASC");
		int limit = konanPropertiesService.getInt("maxLimit");
		
		RestResultVO resultVO = pfDAO.getGroupByKwdData(paramVO, groupByFd, orderByFd, orderByVal, limit);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}
	
	@Override
	public RestResultVO getDepthGroupBy(ParameterVO paramVO, String depth) throws Exception {
		// TODO Auto-generated method stub
		String groupByFd = null;
		
		if ( "powerSt".equals(depth) ) groupByFd = konanPropertiesService.getString("pfGroupDepthField").split(",")[1];
		else if ( "stNo".equals(depth) ) groupByFd = konanPropertiesService.getString("pfGroupDepthField").split(",")[2];
		else groupByFd = konanPropertiesService.getString("pfGroupDepthField").split(",")[0];

		String orderByFd = konanPropertiesService.getString("groupByCount");
		String orderByVal = konanPropertiesService.getString("orderByValueDESC");
		int limit = konanPropertiesService.getInt("maxLimit");
		
		RestResultVO resultVO = pfDAO.getGroupByDepthData(paramVO, groupByFd, orderByFd, orderByVal, limit);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}

	@Override
	public int setPFComment(CommentVO comment) throws Exception {
		// TODO Auto-generated method stub
		return pfDAO.setPFComment(comment);
	}

	@Override
	public List<CommentVO> getPFCommentList(CommentVO comment) throws Exception {
		// TODO Auto-generated method stub
		return pfDAO.getPFCommentList(comment);
	}

	/**
	 * PART2BODY(추출 된 지식의 원문영역)에 IMG 태그가 있을 경우 <img> 태그로 변경을 시켜주는 로직
	 * 가스터빈의 경우 특정 PDF파일들의 이미지가 잘려서 저장이 되어 잘린 이미지를 계산하여 MERGE(합치기)한 이미지로 대체하는 로직도 포함이 되어 있다.
	 * MERGE IMG의 경우 tb_merge_image 테이블을 참고한다.
	 */
	@Override
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

	@Override
	public RestResultVO getSymptomToAction(Map map) throws Exception {
		// TODO Auto-generated method stub
		if ( "ACTION".equals(map.get("gubun")) ) {
			return pfDAO.getSymptomToAction(map, "SYMPTOM_KWD");
		} else {
			return pfDAO.getSymptomToAction(map, "ACTION_KWD");
		}
		
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
