package egovframework.kf.kepri.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.FileUtil;
import egovframework.kf.common.SetParameter;
import egovframework.kf.common.service.CommonService;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.kepri.service.DocService;
import egovframework.kf.kepri.vo.AttachVO;
import egovframework.kf.kepri.vo.DocCategoryVO;
import egovframework.kf.kepri.vo.DocumentVO;
import egovframework.kf.user.vo.UserVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : DocumentController.java
 * Description : 발전 문서를 위한 컨트롤러
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 * 2018년 08월  06일       이창호           최초 작성
 *
 * @since 2017년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Controller
@RequestMapping(value="/document")
public class DocumentController {
	private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);
	
	/** DocService */
	@Resource(name = "docService")
	private DocService docService;
	
	/** CommonService */
	@Resource(name = "commonService")
	private CommonService commonService;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** 파일 업로드 시 필요한 클래스 */
	@Resource(name = "fileUtil")
	private FileUtil fileUtil;
	
	/** Parameter Setting */
	@Resource(name = "setParameter")
	private SetParameter setParameter;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	
	
	/**
	 * 카테고리 관리
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/categoryManage.do")
	public String categoryManage(@RequestParam Map<String, String> map, Model model) throws Exception {
		
		model.addAttribute("openMenu", "document");
		model.addAttribute("menu", "document");
		model.addAttribute("pageName", konanPropertiesService.getString("pageCategory"));	// 파일 다운로드 Log 남길 시 페이지명
		
		return "document/categoryManage";
	}
	
	/**
	 * 발전문서 등록
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/docAdd.do")
	public String docAdd(@RequestParam Map<String, String> map, Model model) throws Exception {
		model.addAttribute("openMenu", "document");
		model.addAttribute("menu", "document");
		
		return "document/docAdd";
	}
	
	/**
	 * 발전문서 수정
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/docModify.do")
	public String docModify(@ModelAttribute DocumentVO documentVO, Model model) throws Exception {
		
		logger.debug("[docModify] documentVO :: " + documentVO);
		ParameterVO paramVO = new ParameterVO();
		RestResultVO result;
		
		logger.debug("[docModify] docID :: " + documentVO.getDoc_idx());
		paramVO.setKwd(documentVO.getDoc_idx());
		paramVO.setCategory(konanPropertiesService.getString("cate_devdoc"));	// GEN_DOC
		
		logger.debug("[docModify] detailSearch Start :: ");
		result = docService.detailSearch(paramVO);
		logger.debug("[docModify] detailSearch END :: ");
		
		model.addAttribute("docItem", result.getResult());
		
		model.addAttribute("openMenu", "document");
		model.addAttribute("menu", "document");
		
		return "document/docModify";
	}
	
	/**
	 * 발전문서 검색
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/docSearch.do")
	public String docSearch(@RequestParam Map<String, String> map, Model model) throws Exception {
		logger.debug("[docSearch] Document Start");	
		
		ParameterVO paramVO = null;
		RestResultVO result;
		int total = 0;
		
		// 파라메터 셋팅
		try {
			paramVO = setParameter.setParameter(map);
			paramVO.setCategory(konanPropertiesService.getString("cate_devdoc"));	// GEN_DOC
			logger.debug(paramVO.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		result = docService.search(paramVO);
		total += result.getTotal();

		model.addAttribute("params", paramVO);
		
		model.addAttribute("docList", result.getResult());
		model.addAttribute("docTotal", result.getTotal());	
		model.addAttribute("paging", commonService.setPagingModel(paramVO.getPageNum(), (int) result.getTotal()));
		
		model.addAttribute("openMenu", "document");
		model.addAttribute("menu", "document");
		model.addAttribute("pageName", konanPropertiesService.getString("pageDocument"));	// 파일 다운로드 Log 남길 시 페이지명
		
		return "document/docSearch";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////// 카테고리 관련 
	/**
	 * category 정보 등록
	 * @param categoryList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/category", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String category(@ModelAttribute DocCategoryVO docCategoryVO, HttpServletRequest request) throws Exception {	
		
		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, Object> insertMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		String message = "fail";
		
		// User session
		UserVO user = (UserVO) request.getSession().getAttribute("login_user");
		logger.debug("[category] user :: " + user.toString());
		
		// insert Category
		insertMap = docService.insertDocCategory(docCategoryVO, user);
		
		if( (boolean) insertMap.get("isDuplicate") ) message = "duplicate";
		else {
			message = "ok";
		}
		
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 카테고리 정보 수정
	 * @param docCategoryVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/categoryUpdate", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String categoryUpdate(@ModelAttribute DocCategoryVO docCategoryVO, HttpServletRequest request) throws Exception {	
		
		Map<String, String> resultMap = new HashMap<String, String>();
		Gson gson = new Gson();
		String message = "fail";

		boolean isUpdateVolume = false;
		
		logger.debug("[categoryUpdate] docCategory :: " + docCategoryVO.toString());
		docService.updateCategory(docCategoryVO);
		docService.updateDocumentCategoryNM(docCategoryVO);
		
		isUpdateVolume = docService.updateCategoryVolume(docCategoryVO, konanPropertiesService.getString("cate_devdoc"));
		
		if ( !isUpdateVolume ) {
			message = "volume update ERROR.";
		} else {
			message = "ok";
		}
		
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 카테고리 정보삭제
	 * @param docCategoryVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/categoryDelete", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String categoryDelete(@RequestParam String jsonObj, HttpServletRequest request) throws Exception {	
		
		Map<String, String> resultMap = new HashMap<String, String>();
		Gson gson = new Gson();
		String message = "fail";
		List<Map<String,String>> list = null;
		
		boolean isDeleteVolume = false;
		
		jsonObj = jsonObj.replaceAll("&quot;", "\"");
		logger.debug("[categoryDelete] jsonObj :: " + jsonObj);
		
		List<Map<String, String>> requestList = gson.fromJson(jsonObj, new ArrayList<Map<String, String>>().getClass());
		logger.debug("[categoryDelete] size :: " + requestList.size());
		
		// DB 삭제전에 파일 목록을 조회하여 파일부터 삭제한다.
		list = docService.selectDocumentFileListByCategory(requestList);
		commonService.deleteAttachFile(list);

		docService.deleteCategoryDB(requestList);	// 카테고리  DB 삭제
		docService.deleteDocumentAttachByCategory(requestList);	// 카테고리 관련 DB 첨부파일 목록 삭제
		docService.deleteDocumentByCategory(requestList);	// 카테고리 관련  DB 문서 삭제
		
		isDeleteVolume = docService.deleteCategoryVolume(requestList, konanPropertiesService.getString("cate_devdoc")); // 카테고리 관련 볼륨 문서 삭제
		
		if ( !isDeleteVolume ) {
			message = "volume delete ERROR.";
		} else {
			message = "ok";
		}
		
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 카테고리 정보 불러오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/category", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String categoryList(HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		String message = "fail";
		String listType = request.getParameter("listType") != null?request.getParameter("listType"):"";
		
		List<DocCategoryVO> list = new ArrayList<DocCategoryVO>();
		
		if("select".equals(listType)) {
			list = docService.selectDocCategorySelectList();
		}else {
			list = docService.selectDocCategoryList();
		}
		
		if(list != null && list.size() > 0) {
			message = "ok";
			resultMap.put("list", list);
		}
		
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////발전문서 관련
	/**
	 * 발전문서 추가
	 * @param documentVO
	 * @param rq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/document", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String document(@ModelAttribute DocumentVO documentVO, HttpServletRequest rq) throws Exception {	
		
		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String,String> fileInfo = new HashMap<String, String>();
		List<AttachVO> attachList = new ArrayList<AttachVO>();
		Gson gson = new Gson();
		String realpath = "";
		String message = "fail";
		int seq = 1;
		boolean isInsert = false;
		
		UserVO user = (UserVO) rq.getSession().getAttribute("login_user");
		String user_id = String.valueOf(user.getUser_id());
		String user_nm = user.getUser_nm();
		
		documentVO.setReg_id(user_id);
		documentVO.setReg_nm(user_nm);
		documentVO.setAtt_cnt(documentVO.getFiles().size());
		int insertDoc = docService.insertDocument(documentVO);
		
		if ( insertDoc > 0 ) {
			
			// Filepath 가져오기
			realpath = commonUtil.getFilepath(konanPropertiesService.getString("cate_devdoc"), "");
			logger.debug("[document] ADD :: " + documentVO.toString());
			
			for ( MultipartFile file : documentVO.getFiles() ) {
				
				// 파일 업로드
				fileInfo = fileUtil.upload(file, realpath);
				
				AttachVO attach = new AttachVO();
				attach.setDoc_idx(documentVO.getDoc_idx());
				attach.setSeq(seq++);
				attach.setFile_nm(fileInfo.get("fileName"));
				attach.setFile_path(realpath);
				attach.setFile_size(Integer.parseInt(fileUtil.getFileSize(attach.getFile_nm(), realpath)));
				attach.setReg_id(String.valueOf(user.getUser_id()));
				attach.setReg_nm(user.getUser_nm());

				logger.debug("[document] ADD file :: " + attach.toString());
				attachList.add(attach);
			}
			
			logger.debug("[document] attachlist size :: " + attachList.size());
			int insertAttach = docService.insertDocumentAttach(attachList);
			
			// insert 성공 시
			if ( insertAttach > 0 ) {
				
				isInsert = docService.insertDocumentVolume();
				
				if ( isInsert ) {
					message = "ok";
				} else {
					message = "Insert Volume ERROR.";
				}
				
			} else {	// insert 실패 시
				logger.debug("[document] delete START");
				docService.deleteDocument(documentVO);
				logger.debug("[document] delete END");
			}
		}
		
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 발전문서 수정
	 * @param documentVO
	 * @param doc_id
	 * @param rq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/document/{doc_id}", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String documentUpdate(@ModelAttribute DocumentVO documentVO, @PathVariable String doc_id,  HttpServletRequest rq) throws Exception {	
	
		logger.debug("[documentUpdate] documentVO :: " + documentVO.toString());
		logger.debug("[documentUpdate] doc_id :: " + doc_id);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		String message = "";
		
		boolean isUpdateVolume = false;
		
		documentVO.setDoc_idx(doc_id);
		
		logger.debug("[documentUpdate] update Document DB START");
		docService.updateDocumentDB(documentVO);
		logger.debug("[documentUpdate] update Document DB END");
		
		logger.debug("[documentUpdate] update Document VOLUME START");
		isUpdateVolume = docService.updateDocumentVolume(documentVO, konanPropertiesService.getString("cate_devdoc"));
		logger.debug("[documentUpdate] update Document VOLUME END");
		
		if ( !isUpdateVolume ) {
			message = "volume update ERROR.";
		} else {
			message = "ok";
		}
		
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	@RequestMapping(value = "/documentDelete", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String documentDelete(@RequestParam String jsonObj,  HttpServletRequest rq) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		String message = "";
		List<Map<String,String>> list = null;
		
		boolean isDeleteVolume = false;
		
		jsonObj = jsonObj.replaceAll("&quot;", "\"");
		logger.debug("[documentDelete] jsonObj :: " + jsonObj);
		
		List<Map<String, String>> requestList = gson.fromJson(jsonObj, new ArrayList<Map<String, String>>().getClass());
		logger.debug("[documentDelete] size :: " + requestList.size());
		
		// DB 삭제전에 파일 목록을 조회하여 파일부터 삭제한다.
		for(Map<String, String> map : requestList) {
			list = docService.selectDocumentFileListByDocID(map.get("doc_idx"));
			commonService.deleteAttachFile(list);
		}
		docService.deleteDocumentDB(requestList);
		docService.deleteDocumentAttachDB(requestList);
		
		logger.debug("[documentDelete] delete Document VOLUME START");
		isDeleteVolume = docService.deleteDocumentVolume(requestList, konanPropertiesService.getString("cate_devdoc"));
		logger.debug("[documentDelete] delete Document VOLUME END");
		
		if ( !isDeleteVolume ) {
			message = "volume delete ERROR.";
		} else {
			message = "ok";
		}
		
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
}
