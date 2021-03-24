package egovframework.kf.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import egovframework.kf.admin.service.AdminService;
import egovframework.kf.admin.vo.DataTabVO;
import egovframework.kf.admin.vo.QaVO;
import egovframework.kf.analysis.service.AnalysisService;
import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.FileUtil;
import egovframework.kf.common.SetParameter;
import egovframework.kf.common.service.CommonService;
import egovframework.kf.common.vo.UploadVO;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.user.vo.UserVO;
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
@RequestMapping(value = "/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
		
	/** adminService */
	@Resource(name = "adminService")
	private AdminService adminService;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	/** CommonService */
	@Resource(name = "commonService")
	private CommonService commonService;
	
	/** Parameter Setting */
	@Resource(name = "setParameter")
	private SetParameter setParameter;
	
	/** 파일 업로드 시 필요한 클래스 */
	@Resource(name = "fileUtil")
	private FileUtil fileUtil;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** Analysis Service */
	@Resource(name = "analysisService")
	private AnalysisService analysisService;
	
	// Repository 관리
//	@RequestMapping(value = "/repository.do")
//	public String repository(@RequestParam String category, Model model) throws Exception {
//		logger.debug("[repository] repository Start");	
//		model.addAttribute("category", category);
//		model.addAttribute("openMenu", "repository");
//		model.addAttribute("pageName", konanPropertiesService.getString("pageAdmin"));
//		
//		return "admin/repository/repository";
//	}
	
	@RequestMapping(value = "/repository.do")
	public String repository(@RequestParam Map<String, String> map, HttpServletRequest rq, Model model) throws Exception {
		logger.debug("[repository] repository Start");	
		ParameterVO paramVO = null;
		// 파라메터 셋팅
		try {
			paramVO = setParameter.setParameter(map);
			
			UserVO user = (UserVO) rq.getSession().getAttribute("login_user");
			paramVO.setUserId(user.getUser_nm());
			logger.debug("paramVO :: " + paramVO.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		RestResultVO result = adminService.selectReportList(paramVO);
		
		Map<String,String> knowledgeCountMap = adminService.selectKnowledgeCount(paramVO.getCategory(),result.getResult());
		
		
		// 건수 삽입
		result.getResult().forEach(v -> v.put("CNT","0"));
		if(knowledgeCountMap.size() > 0) {
			result.getResult().forEach(v -> v.put("CNT",knowledgeCountMap.get(v.get("MD5_KEY"))));	
		}
		
		model.addAttribute("params", paramVO);
		model.addAttribute("resultData", result.getResult());
		model.addAttribute("paging", commonService.setPagingModel(paramVO.getPageNum(), (int) result.getTotal()));
		model.addAttribute("menu", "repository");
		model.addAttribute("pageName", konanPropertiesService.getString("pageAdmin"));
		
		return "admin/repository/repository";
	}
	
//	@RequestMapping(value = "/repository/{cate}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
//	@ResponseBody
//	public String getReportList(@ModelAttribute DataTabVO dataTabVO, @PathVariable String cate, Model model, HttpServletRequest request) throws Exception {
//		logger.debug("[getReportList] getReportList START");
//		
//		Gson gson = new Gson();
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		long reportTotal = 0;
//		
//		logger.debug("[getReportList] map [" + dataTabVO.toString() + "]");
//		logger.debug("[getReportList] cate [" + cate + "]");
//		dataTabVO.setCate(cate);
//		
//		RestResultVO result = adminService.selectReportList(dataTabVO);
//		logger.debug("[getReportList] size :: " + result.getResult().size());
//		
//		
//		Map<String,String> knowledgeCountMap = adminService.selectKnowledgeCount(cate,result.getResult());
//		
//		
//		// 건수 삽입
//		result.getResult().forEach(v -> v.put("CNT","0"));
//		if(knowledgeCountMap.size() > 0) {
//			result.getResult().forEach(v -> v.put("CNT",knowledgeCountMap.get(v.get("MD5_KEY"))));	
//		}
//		
//		
//		if ( result.getTotal() > 0 ) {
//			reportTotal = result.getTotal();
//		}
//		
//		resultMap.put("kwd", dataTabVO.getsSearch());
//		resultMap.put("aaData", result.getResult());
//		resultMap.put("iTotalRecords", reportTotal);
//		resultMap.put("iTotalDisplayRecords", reportTotal);
//		
//		return gson.toJson(resultMap);
//	}
	
	@RequestMapping(value = "/repository/{cate}/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String getKnowledgeToRepo(@PathVariable String cate, @PathVariable String id, Model model, HttpServletRequest request) throws Exception {
				
		logger.debug("[getKnowledgeToRepo] getKnowledgeToRepo START");
		
		RestResultVO meta = adminService.selectReportMeta(cate, id);
		RestResultVO result = adminService.selectKnowledgeToRepo(cate, id);
		logger.debug("[getKnowledgeToRepo] size :: " + result.getResult().size());
		
//		if ( result != null && result.getResult().size() > 0 ) {

		String repo_idx = meta.getResult().get(0).get("REPO_IDX");
		String equip_idx = meta.getResult().get(0).get("EQUIP_IDX");
		String repo_kind = meta.getResult().get(0).get("REPO_KIND");
		String dept = meta.getResult().get(0).get("DEPT");
		String st_form = meta.getResult().get(0).get("ST_FORM");
		String built_ym = meta.getResult().get(0).get("BUILT_YM");
		String productor = meta.getResult().get(0).get("PRODUCTOR");
		String fuel = meta.getResult().get(0).get("FUEL");
		String filepath = meta.getResult().get(0).get("FILEPATH");
		String reportNm = meta.getResult().get(0).get("REPORT_NM");
		String fileNm = meta.getResult().get(0).get("REPO_FILE_NM");
		String publishYm = meta.getResult().get(0).get("PUBLISH_YM");
		String reporter = meta.getResult().get(0).get("REPORTER");
		String powerCompNm = meta.getResult().get(0).get("POWER_COMP_NM");
		String powerStNm = meta.getResult().get(0).get("POWER_ST_NM");
		String stNo = meta.getResult().get(0).get("ST_NO");
		
		// 지식DB 추가 시 필요한 메타 데이터
		model.addAttribute("repo_idx", repo_idx);
		model.addAttribute("equip_idx", equip_idx);
		model.addAttribute("repo_kind", repo_kind);
		model.addAttribute("dept", dept);
		model.addAttribute("st_form", st_form);
		model.addAttribute("built_ym", built_ym);
		model.addAttribute("productor", productor);
		model.addAttribute("fuel", fuel);
		model.addAttribute("filepath", filepath);
		model.addAttribute("reportNm", reportNm);
		model.addAttribute("fileNm", fileNm);
		model.addAttribute("publishYm", publishYm);
		model.addAttribute("reporter", reporter);
		model.addAttribute("powerCompNm", powerCompNm);
		model.addAttribute("powerStNm", powerStNm);
		model.addAttribute("stNo", stNo);
//		}
		model.addAttribute("result", result.getResult());
		model.addAttribute("resultTotal", result.getTotal());
		model.addAttribute("cate", cate);
		model.addAttribute("md5_key", id);
		
		model.addAttribute("bigMenu", "admin");
		model.addAttribute("menu", "repositoryDetail");
		model.addAttribute("pageName", konanPropertiesService.getString("pageAdmin"));	// 파일 다운로드 Log 남길 시 페이지명
		
		return "admin/repository/detailRepository";
	}
	
	@RequestMapping(value = "/repository/{cate}/{id}", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String updateKnowledgeToRepo(@PathVariable String cate, @PathVariable String id, @RequestParam String jsonObj, Model model) throws Exception {
		
		logger.debug("[updateKnowledgeToRepo] updateKnowledgeToRepo START");
		
		Gson gson = new Gson();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String message = "";
		jsonObj = jsonObj.replaceAll("&quot;", "\"");
		logger.debug("[updateKnowledgeToRepo] cate :: " + cate);
		logger.debug("[updateKnowledgeToRepo] id :: " + id);
		logger.debug("[updateKnowledgeToRepo] jsonObj :: " + jsonObj);
		
		List<Map<String, String>> requestList = gson.fromJson(jsonObj, new ArrayList<Map<String, String>>().getClass());
		logger.debug("[updateKnowledgeToRepo] size :: " + requestList.size());
		
		if ( ("".equals(cate) || cate == null) || ("".equals(id) || id == null) ) { 
			message = "잘못 된 URL입니다."; 
		}
		else {
			boolean isUpdate = adminService.updateKnowledgeToRepo(cate, id, requestList);
			
			if ( isUpdate ) {
				message = "success";
			} else {
				message = "수정 실패";
			}
		}
		
		resultMap.put("message", message);
		return gson.toJson(resultMap);
	}
	
	/**
	 * 	챗봇 즉답형 q/a기능
	 * 	기존 q/a 기능으로 추가된 질문답변셋을 가지고 list 출력.
	 * 	DB 조회. 
	 * 
	 * @param map
	 * @param rq
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/qa.do")
	public String qa(@RequestParam Map<String, String> map, HttpServletRequest rq, Model model) throws Exception {
		logger.debug("[qa] qa Start");	
		
		// 	DB에서 기존 리스트를 조회 해 온다.
		ParameterVO paramVO = null;
		List<QaVO> result = null;
		
		// 파라메터 셋팅
		try {
			paramVO = setParameter.setParameter(map);
			
			UserVO user = (UserVO) rq.getSession().getAttribute("login_user");
			paramVO.setUserId(user.getUser_nm());
			logger.debug("paramVO :: " + paramVO.toString());
			
			//	결과 가지고 오기.
			result = adminService.selectQalist(paramVO);

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("QaList", result);
		model.addAttribute("params", paramVO);
		model.addAttribute("paging", commonService.setPagingModel(paramVO.getPageNum(), (int) result.size()));
		
		return "admin/qa/qa";
	}
	
	/**
	 * 클라이언트에서 가져온 파일의 메타정보를 추출한다.
	 * @param uploadVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/qaFileCheck", method = RequestMethod.POST, produces = "application/json; charset=utf8")
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

			// file 원문 텍스트 가져오기.
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
}
