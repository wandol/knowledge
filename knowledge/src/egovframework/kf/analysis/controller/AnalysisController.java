package egovframework.kf.analysis.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import egovframework.kf.analysis.service.AnalysisService;
import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.FileUtil;
import egovframework.kf.common.service.CommonService;
import egovframework.kf.common.vo.UploadVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : AnalysisController.java
 * Description : 텍스트분석 가시화를 위한 컨트롤러
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 * 2018년 08월  28일       이창호           최초 작성
 *
 * @since 2017년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Controller
@RequestMapping(value = "/analysis")
public class AnalysisController {
	
	/** 파일 업로드 시 필요한 클래스 */
	@Resource(name = "fileUtil")
	private FileUtil fileUtil;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** Analysis Service */
	@Resource(name = "analysisService")
	private AnalysisService analysisService;
	
	/** Common Service */
	@Resource(name = "commonService")
	private CommonService commonService;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	private static final Logger logger = LoggerFactory.getLogger(AnalysisController.class);
		
	/*** 텍스트분석 가시화 페이지 ***/
	@RequestMapping(value = "/visualize.do")
	public String visualize(@RequestParam Map<String, String> map, Model model) throws Exception {
		logger.debug("[visualize] Text Visualize Start");
		model.addAttribute("openMenu", "visualize");
		model.addAttribute("pageName", konanPropertiesService.getString("pageVisual"));
		return "visualize/textVisualize";
	}
	
	/**
	 * 클라이언트에서 가져온 파일의 메타정보를 추출한다.
	 * @param uploadVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/meta", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String meta(@ModelAttribute UploadVO uploadVO, Model model) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		try{
			
			String tempPath = "";
			
			// 업로드 할 Filepath 가져오기
			tempPath = commonUtil.getFilepath("", "");
			
			/*** 파일 업로드 ***/
			Map<String,String> fileInfo = fileUtil.upload(uploadVO.getFile(), tempPath);
			uploadVO.setFileName(fileInfo.get("fileName"));
			uploadVO.setUpLoadDept("");
			uploadVO.setUpLoadReporter("");
			uploadVO.setUpLoadReportText("");
			uploadVO.setFileSize(fileUtil.getFileSize(uploadVO.getFileName(), tempPath));
			uploadVO.setMd5FileNmAndSize(fileUtil.fileInfoToMD5(uploadVO));	
			
			logger.debug("[meta] upload vo :: " + uploadVO.toString());
			
			// 자동 분류, 파일 원문
			Map<String, String> filebodyMap = new HashMap<String, String>();
			filebodyMap = analysisService.getClassficationNFileBody(fileInfo.get("filePath") + File.separator, fileInfo.get("fileName"));
			
			if ( !filebodyMap.containsKey("message") ) {
				logger.debug("[meta] message key not exist.");
				filebodyMap.put("message", "");
			}
			
			// 메타 정보
			Map<String, String> metaInfoMap = analysisService.getMetaInfo(filebodyMap.get("fileBody").toString(), fileInfo.get("filePath"), fileInfo.get("fileName"));
			
			resultMap.put("fileBody", filebodyMap);
			resultMap.put("metaInfo", metaInfoMap);
			resultMap.put("fileName", fileInfo.get("fileName"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 클라이언트에서 가져온 파일원문의 문단을 분리한다.
	 * @param uploadVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/paragraph", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String paragraph(@RequestParam String classfication, @RequestParam String filebody) throws Exception {
		
		List<String> paraList = new ArrayList<String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		String message = "fail";
		
		paraList = analysisService.getParagraph(classfication, filebody);
		
		if ( paraList.size() > 0 ) { 
			message = "success";
			resultMap.put("paraList", paraList);
		}
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 클라이언트에서 가져온 문단을 다시 문장으로 분리한다.
	 * @param uploadVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sentence", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String sentence(@RequestParam(value="paraList[]") List<String> paraList, @RequestParam String fileName) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> sentenceResult = new ArrayList<String>();
//		List<List<String>> sentenceResult = new ArrayList<List<String>>();
		Gson gson = new Gson();
		String message = "fail";
		
		sentenceResult = analysisService.getSentence(paraList, fileName);
		if ( sentenceResult.size() > 0 ) {
			message = "success";
			resultMap.put("sentenceList", sentenceResult);
		}
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 문장 List를 가져와 각 문장 별로 형태소 분석을 한다.
	 * @param sentenceList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/posTag", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String posTag(@RequestParam(value="sentenceList[]") List<String> sentenceList) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> posTagList = new ArrayList<Map<String, Object>>();
		Gson gson = new Gson();
		String message = "fail";
		
		posTagList = analysisService.getPosTag(sentenceList);
		if ( posTagList.size() > 0 ) {
			message = "success";
			resultMap.put("posTagList", posTagList);
		}
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 문장 리스트를 가져와 시맨틱 패턴을 매칭
	 * @param sentenceList
	 * @param category
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/symantic", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String symantic(@RequestParam(value="sentenceList[]") List<String> sentenceList, @RequestParam String category, Model model) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> symanticList = new ArrayList<Map<String, Object>>();
		Gson gson = new Gson();
		String message = "fail";
		logger.debug("[symantic] sentenceList :: " + sentenceList.size());
		logger.debug("[symantic] category :: " + category);
		
		result = analysisService.getSymanticPattern(sentenceList, category);
		
		if ( result != null ) {
//		if ( symanticList != null && symanticList.size() > 0 ) {	
			message = "success";
			resultMap.put("symantic", result);
		}
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	@RequestMapping(value = "/ner", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String ner(@RequestParam(value="sentenceList[]") List<String> sentenceList, @RequestParam String classfication, 
			@RequestParam String fileNm, Model model) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> result = new ArrayList<String>();
		Gson gson = new Gson();
		String message = "fail";

		logger.debug("[ner] getNer START");
		result = analysisService.getNer(fileNm, sentenceList, classfication);
		logger.debug("[ner] getNer END");
		
		if ( result != null && result.size() > 0 ) {
			message = "success";
			resultMap.put("ner", result);
		}
		resultMap.put("message", message);
		
		logger.debug("[ner] resultMap :: " + resultMap.toString());
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 클라이언트에서 가져온 파일을 분석한다.
	 * @param uploadVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String upload(@ModelAttribute UploadVO uploadVO, Model model) throws Exception {
		
		String realpath = "";
		String result = "fail";
		String message = "";
		boolean isSuccess = false;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String,String> fileInfo = new HashMap<String, String>();
		Gson gson = new Gson();
		
		// Filepath 가져오기
		realpath = commonUtil.getFilepath(uploadVO.getUpLoadPowerType(), uploadVO.getUpLoadRepoKind());

		logger.debug("[upload] isRework :: " + uploadVO.getIsRework());
		if ( !"Y".equals(uploadVO.getIsRework()) ) {
			/*** 파일 업로드 ***/
			fileInfo = fileUtil.upload(uploadVO.getFile(), realpath);
			
			uploadVO.setFileName(fileInfo.get("fileName"));
			uploadVO.setUpLoadDept("");
			uploadVO.setUpLoadReporter("");
			uploadVO.setUpLoadReportText("");
			uploadVO.setFileSize(fileUtil.getFileSize(uploadVO.getFileName(), realpath));
			uploadVO.setMd5FileNmAndSize(fileUtil.fileInfoToMD5(uploadVO));		
		} else {
			fileInfo.put("fileName", uploadVO.getFileName());
			fileInfo.put("filePath", realpath);
		}
		
		// 업로드 된 문서 자동분류
		String classfication = "Fail";
		int insResult = -1;
		
		classfication = commonService.getClassfication(fileInfo.get("filePath") + File.separator, fileInfo.get("fileName"));
		uploadVO.setClassfication(classfication);

		String category = uploadVO.getUpLoadPowerType();
		String repoKind = uploadVO.getUpLoadRepoKind();
		uploadVO.setCate(category);
		List<Map<String, Object>> checkDupList = analysisService.selectCheckDupMd5(uploadVO);
		logger.debug("[upload] checkDupList :: " + checkDupList.size());
		logger.debug("[upload] upload vo :: " + uploadVO.toString());
		
		if ( checkDupList.size() > 0  ) {
			
			// 중복된 데이터 제거 하고 재 분석
			if ( "Y".equals(uploadVO.getIsRework()) ) {
				logger.debug("[upload] rework START");
				
				//업로드 정보 Insert
				insResult = analysisService.insertReportInfo(uploadVO);
				logger.debug("[upload][SUCCESS] insert result => " + insResult);
				logger.debug("[upload][SUCCESS] insert idx => " + uploadVO.getUploadIdx());
				
				// insert 성공 시
				if ( insResult > 0 ) {
					
					// 재분석 시작
					isSuccess = analysisService.restIndexingPost(category, uploadVO);
					
					if ( !isSuccess ) {
						// 색인 실패시 Insert한 정보 삭제
						logger.debug("[upload] idx : " + uploadVO.getUploadIdx());
						uploadVO.setUpLoadPowerType(category);
						uploadVO.setUpLoadRepoKind(repoKind);
						analysisService.deleteReportInfo(uploadVO.getUploadIdx());
						analysisService.deleteRecordEngine(uploadVO);
						logger.debug("[upload][SUCCESS] DELETE SUCCESS");
					} else {
						// 중복된 데이터를 삭제하기 위한 key값 셋팅
						int deleteIdx = Integer.valueOf(checkDupList.get(0).get("REPO_IDX").toString());
						logger.debug("[deleteRecordEngine] ###############################################################################################################");
						logger.debug("[deleteRecordEngine] ##################################################### DUPLICATE DATA DELETE ( REPO_IDX=" + deleteIdx + ") START");
						int r = analysisService.deleteReportInfo(deleteIdx);
						
						if ( r != 1 ) {
							message = "delete fail";
						} else {
							UploadVO deleteVO = new UploadVO();
							deleteVO.setUpLoadPowerType(category);
							deleteVO.setUpLoadRepoKind(repoKind);
							deleteVO.setUploadIdx(deleteIdx);
							
							boolean isDelete = analysisService.deleteRecordEngine(deleteVO);
							
							if ( !isDelete ) {
								message = "delete fail";
							}
						}
						logger.debug("[deleteRecordEngine] ##################################################### DUPLICATE DATA DELETE ( REPO_IDX=" + deleteIdx + ") END");
						logger.debug("[deleteRecordEngine] ###############################################################################################################");
					}
					
				} else {
					message = "db fail";
					logger.debug("[upload][ERROR] INSERT FAIL. DB CHECK PLEASE");
				}
				
			} else {
				message = "duplicate";
				logger.debug("[upload][ERROR] DUPLICATE CHECK FAIL. DB CHECK PLEASE");
			}
		} else {
			
			//업로드 정보 Insert
			insResult = analysisService.insertReportInfo(uploadVO);
			logger.debug("[upload][SUCCESS] insert result => " + insResult);
			logger.debug("[upload][SUCCESS] insert idx => " + uploadVO.getUploadIdx());
			
			// insert 성공 시
			if ( insResult > 0 ) {
				
				// 분석 시작
				isSuccess = analysisService.restIndexingPost(category, uploadVO);
				
				if ( !isSuccess ) {
					// 색인 실패시 Insert한 정보 삭제
					logger.debug("[upload] idx : " + uploadVO.getUploadIdx());
					uploadVO.setUpLoadPowerType(category);
					uploadVO.setUpLoadRepoKind(repoKind);
					analysisService.deleteReportInfo(uploadVO.getUploadIdx());
					analysisService.deleteRecordEngine(uploadVO);
					logger.debug("[upload][SUCCESS] DELETE SUCCESS");
				}
				
			} else {
				message = "db fail";
				logger.debug("[upload][ERROR] INSERT FAIL. DB CHECK PLEASE");
			}
		}
		
		if ( isSuccess ) result = "ok";
		
		resultMap.put("message", message);
		resultMap.put("uploadResult", result);
		resultMap.put("uploadInfo", uploadVO);
		
		return gson.toJson(resultMap);
	}
}
