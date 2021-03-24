package egovframework.kf.common.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.FileUtil;
import egovframework.kf.common.service.CommonService;
import egovframework.kf.common.service.MemecheckerService;
import egovframework.kf.data.RestResultVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : CommonController.java
 * Description : 공통 기능
 *
 * @since 201903
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Controller
@RequestMapping(value="/comm")
public class CommonController {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** file util Setting */
	@Resource(name = "fileUtil")
	private FileUtil fileUtil;
	
	/** Common Service */
	@Resource(name = "commonService")
	private CommonService commonService;
	
	/** Memechecker Service */
	@Resource(name = "memecheckerService")
	private MemecheckerService memecheckerService;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	
	
		
	/**
	 * 필터링 옵션 조회(진단부위, 현상, 대책, 발전사, 사업소, 호기)
	 * @param map
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOptionList.do", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String getOptionList(@RequestParam Map map) throws Exception {
		
		Gson gson = new Gson();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		String isPowerComp 	= "fail";
		String isPowerSt   	= "fail";
		String isStNo 	  	= "fail";
		String isPartName 	= "fail";
		String isPartMid  	= "fail";
		String isSymptom  	= "fail";
		String isAction   	= "fail";
		logger.debug("[getOptionList] map :: " + map.toString());
		
		String cate = map.get("cate").toString();		
		cateInfoMap = commonUtil.getCategoryInfo(cate, "");
		
		// 발전사 정보 DB조회
		List<Map<String, String>> powerCompList = commonService.selectPowerCompList(map);
		// DB에서 조회한 데이터가 있을 경우
		if ( powerCompList.size() > 0 ) {
			isPowerComp = "success";
			resultMap.put("powerCompList", powerCompList);
			resultMap.put("selPowerComp", map.get("powerCompName"));
		}
		
		// 사업소 정보 DB조회
		if ( !"all".equals(map.get("powerCompName")) ) {	// 발전사 정보가 있을때 리스트 조회
			List<Map<String, String>> powerStList = commonService.selectPowerStList(map);				
			// DB에서 조회한 데이터가 있을 경우
			if ( powerStList.size() > 0 ) {
				isPowerSt = "success";
				resultMap.put("powerStList", powerStList);
				resultMap.put("selPowerStName", map.get("powerStName"));
				
				// 메타 추출기능에서 value가 "화력"이란 단어를 붙여야 하는 케이스가 있음
				// 복합이란 단어가 포함이 되지 않을 때만 실행이 되며 서버에서 받아온 List와 비교하여 같은 값이 없을 경우 "화력"이란 단어를 붙임.
				if ( map.get("powerStName").toString().indexOf("복합") < 0 ) {
					boolean isPowerStSame = false;
					for ( int i=0; i<powerStList.size(); i++ ) {
						if ( map.get("powerStName").toString().equals(powerStList.get(i).get("POWER_ST_NM"))) {
							isPowerStSame = true;
						}
					}
					
					if ( !isPowerStSame && !"all".equals(map.get("powerStName")) ) map.put("powerStName", map.get("powerStName").toString() + "화력");
				}
			}
		}
		
		// 호기 정보 DB조회
		if ( !"all".equals(map.get("powerStName")) ) {	// 사업소 정보가 있을때 리스트 조회
			List<Map<String, String>> StNoList = commonService.selectStNoList(map);
			// DB에서 조회한 데이터가 있을 경우
			if ( StNoList.size() > 0 ) {
				isStNo = "success";
				resultMap.put("stNoList", StNoList);
				resultMap.put("selStNo", map.get("stNo"));
			}
		}
		
		// 발전기-절연진단, 발전기-누설흡습, 성능의 경우 진단 부위 범위가 없기(아직 미정) 때문에 아래 로직을 타지 않도록 한다.
		if ( !konanPropertiesService.getString("cate_gen_ins").equals(cate) && !konanPropertiesService.getString("cate_gen_kepri").equals(cate)
				&& !konanPropertiesService.getString("cate_perform").equals(cate) ) {
			
			// 진단부위 엔진 조회
			List<Map<String, String>> partNameList = commonService.selectPartNameList(map, cateInfoMap);
			if ( partNameList != null && partNameList.size() > 0 ) {
				isPartName = "success";
				resultMap.put("partNameList", partNameList);
				resultMap.put("selPartName", map.get("partName"));
			}
			
			// 손상현상 엔진 조회
			List<Map<String, String>> symptomKwdList = commonService.selectSymptomKwdList(map, cateInfoMap);
			if ( symptomKwdList != null && symptomKwdList.size() > 0 ) {
				isSymptom = "success";
				resultMap.put("symptomKwdList", symptomKwdList);
				resultMap.put("selSymptomKwd", map.get("symptomKwd"));
			}
			
			// 손상대책 엔진 조회
			List<Map<String, String>> actionKwdList = commonService.selectActionKwdList(map, cateInfoMap);
			if ( actionKwdList != null && actionKwdList.size() > 0 ) {
				isAction = "success";
				resultMap.put("actionKwdList", actionKwdList);
				resultMap.put("selActionKwd", map.get("actionKwd"));
			}
		} else {
			logger.debug("[getOptionList] No Option");
		}
		
		resultMap.put("isPowerComp", isPowerComp);
		resultMap.put("isPowerSt", isPowerSt);
		resultMap.put("isStNo", isStNo);
		resultMap.put("isPartName", isPartName);
		resultMap.put("isSymptom", isSymptom);
		resultMap.put("isAction", isAction);
		
		resultMap.put("cate", map.get("cate"));
		
		// Object -> json String으로 만들어준다.
		return gson.toJson(resultMap);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////// 유사도
	/**
	 * id에 해당하는 유사문서 top 3를 가져온다.
	 * @param map
	 * @param rs
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/memechecker/{category}/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String getMemechecker(@PathVariable String category, @PathVariable String id, HttpServletResponse rs) throws Exception {
		List<Map<String,String>> result;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String message = "false";
		Gson gson = new Gson();
		
		String cate = "";
		cate = commonUtil.getCategoryAcronym(category);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("category", category);
		map.put("idx", id);
		
		result = memecheckerService.getMemecheckerRanking(map, cate);
		
		if ( result != null ) {
			message = "success";
		}
		
		resultMap.put("message", message);
		resultMap.put("cate", map.get("category"));
//		resultMap.put("repoKind", map.get("repoKind"));
		resultMap.put("result", result);
		
		// Object -> json String으로 만들어준다.
		return gson.toJson(resultMap);
	}
	
	/**
	 * 이미지를 서버에서 불러온다.
	 * @param req
	 * @param session
	 * @param res
	 * @param fileName
	 * @throws Exception
	 */
	@RequestMapping(value="/displayImg.do", method = RequestMethod.GET)
	public void displayImg(HttpServletResponse res, @RequestParam String fileName) throws Exception {
		
		String realFile = "";		
		String path = "";
		String fileNm = fileName;
		String ext = fileName.split("\\.")[1];
		
		if ( commonUtil.getOSType().indexOf("window") > -1 ){
			path = konanPropertiesService.getString("imagePathWindow");
		} else {
			path = konanPropertiesService.getString("imagePathLinux");
		}
		
		realFile = path + fileName;
		BufferedOutputStream out = null;
		InputStream in = null;
		
		try {
			
			// 확장자가 tiff, bmp이면 jpg로 변환하여 화면에 보여준다.
			if ( "tiff".equals(ext) || "bmp".equals(ext)) {
				final BufferedImage bi = ImageIO.read(new File(realFile));	// 기존 tiff, bmp파일 읽기
				ImageIO.write(bi, "jpg", new File(path + fileName.split("\\.")[0] + ".jpg"));	// jpg파일로 저장
				
				ext = "jpg";
				realFile = path + fileName.split("\\.")[0] + ".jpg";	// 서버에서 읽어들일 파일 새로 저장 된 jpg파일로 셋팅 
			}
			//	png 변환 추가.
			if("png".equals(ext)) {

		        BufferedImage originalImage = ImageIO.read(new File(realFile));

		        // jpg needs BufferedImage.TYPE_INT_RGB
		        // png needs BufferedImage.TYPE_INT_ARGB

		        // create a blank, RGB, same width and height
		        BufferedImage newBufferedImage = new BufferedImage(
		                originalImage.getWidth(),
		                originalImage.getHeight(),
		                BufferedImage.TYPE_INT_RGB);

		        // draw a white background and puts the originalImage on it.
		        newBufferedImage.createGraphics()
		                .drawImage(originalImage,
		                        0,
		                        0,
		                        Color.WHITE,
		                        null);

		        // save an image
		        ImageIO.write(newBufferedImage, "jpg", new File(path + fileName.split("\\.")[0] + ".jpg"));
		        ext = "jpg";
				realFile = path + fileName.split("\\.")[0] + ".jpg";	
			}
			
			res.setContentType("image/" + ext);
			res.setHeader("Content-Disposition", "inline;filename=" + fileNm);
			File file = new File(realFile);
			
			String mimeType = new MimetypesFileTypeMap().getContentType(file);
			if(file.exists()){
				in = new FileInputStream(file);
				out = new BufferedOutputStream(res.getOutputStream());	
				int len;
				byte[] buf = new byte[1024];
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			} else {
				logger.debug("[displayImg] realFile is not exist");
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		} finally {
			if(out != null){ out.flush(); }
			if(out != null){ out.close(); }
			if(in != null){ in.close(); }
		}
	}
	

	/**
	 * Comment : 파일 다운로드 기능
	 * @param map
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/fileDownload.do")
	public void fileDownload(@RequestParam Map<String, String> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String filename = map.get("fileName");
		String cate = map.get("cate");
        logger.debug("[fileDownload] fileName :: " + filename);
        
        if ( !"".equals(cate) && cate != null ) {
        	commonService.fileDownload(map, request, response);
        } else {
        	logger.debug("[fileDownload] category is null or empty.");
        }
	}
	
	@RequestMapping(value = "/updateMD5.do", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String updateMD5(HttpServletRequest rq, HttpServletResponse rs){
		List<Map<String,String>> result;
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Gson gson = new Gson();
		try {
			commonService.updateFileNmAndSizeToMD5();
			resultMap.put("result", "success");
			return gson.toJson(resultMap);
		}catch (Exception e) {
			resultMap.put("result", e.getMessage());
			return gson.toJson(resultMap);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////// 챗봇
	/**
	 * chatbot layer
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chatbot.do")
	public String chatbot(@RequestParam Map<String, String> map, Model model) throws Exception {
		logger.debug("[chatbot] Go to the chatbot Page");
		return "chatbot/konanbot";
	}
	
	/**
	 * VR 골라보기
	 *  - part 리스트 불러오기
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getVRPartInfo.do", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String getVRPartInfo(@RequestParam Map<String, String> map, HttpServletRequest rq, HttpServletResponse rs) throws Exception {
		logger.debug("[Common] getVRPartInfo");
		RestResultVO restResultvo;
		Gson gson = new Gson();
		
		restResultvo = commonService.getVRPartList(map);
		
		return gson.toJson(restResultvo);
	}
	
	/**
	 * 파일 일괄저장 기능
	 * @param request
	 * @param response
	 * @param handler
	 */
	@RequestMapping(value = "/allDownLoad.do")
	public void allDownLoad(@RequestParam Map<String, String> map, HttpServletRequest request, HttpServletResponse response){ 
		try {
			System.out.println("[allDownLoad] " + map);
			if(map.get("cate") != null && map.get("doc_idx") != null && map.get("title") != null) {
				commonService.allDownLoad(map, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

