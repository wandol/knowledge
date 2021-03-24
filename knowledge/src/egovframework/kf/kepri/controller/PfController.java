package egovframework.kf.kepri.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.SetParameter;
import egovframework.kf.common.service.CommonService;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.kepri.service.PerformService;
import egovframework.kf.kepri.service.PfService;
import egovframework.kf.kepri.vo.CommentVO;
import egovframework.kf.user.vo.UserVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : PfController.java
 * Description : problem focus 조회를 위한 컨트롤러
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
@RequestMapping(value="/problemFocus")
public class PfController {
	private static final Logger logger = LoggerFactory.getLogger(PfController.class);
	
	/** PfService */
	@Resource(name = "pfService")
	private PfService pfService;	
	
	/** PerformService */
	@Resource(name = "performService")
	private PerformService performService;
	
	/** CommonService */
	@Resource(name = "commonService")
	private CommonService commonService;
	
	/** Parameter Setting */
	@Resource(name = "setParameter")
	private SetParameter setParameter;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	
	
	/**
	 * 지식 DB 탐색
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pfSearch.do")
	public String pfSearch(@RequestParam Map<String, String> map, Model model) throws Exception {
		logger.debug("[pfSearch] Problem Focus Start");		
		ParameterVO paramVO = null;
		
		// 파라미터 세팅
		try {
			paramVO = setParameter.setParameter(map);
			logger.debug(paramVO.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		setCategoryModel_problem(model, paramVO);
		
		logger.debug("[searchProFocus] kwd :: " + paramVO.getKwd());
		logger.debug("[searchProFocus] cate :: " + map.get("category"));
		
		//파라미터
		model.addAttribute("params", paramVO);
		model.addAttribute("openMenu", "problemFocus");
		model.addAttribute("menu", "problemFocus");
		model.addAttribute("pageName", konanPropertiesService.getString("pagePfSearch"));
		
		return "problemFocus/search/pfSearch";
	}
	
	/**
	 * 진단 DB 분석
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pfAnalysis.do")
	public String pfAnalysis(@RequestParam Map<String, String> map, Model model) throws Exception {
		logger.debug("[pfAnalysis] Problem Focus Start");
		ParameterVO paramVO = null;
		
		// 파라미터 세팅
		try {
			paramVO = setParameter.setParameter(map);
			logger.debug(paramVO.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//파라미터
		model.addAttribute("params", paramVO);
		model.addAttribute("openMenu", "pfAnalysis");
		model.addAttribute("menu", "problemFocus");
		model.addAttribute("pageName", konanPropertiesService.getString("pagePfAnal"));
		
		return "problemFocus/analysis/pfAnalysis";
	}
	
	/**
	 * 모델 세팅 부분을 분리
	 * 카테고리 :	지식 DB
	 * @return Model
	 * 
	 * @throws Exception
	 */
	private Model setCategoryModel_problem(Model model, ParameterVO paramVO) throws Exception {
		RestResultVO result;
		List<String> partNameList = null;
		int total = 0;
		
		logger.debug("[setCategoryModel_problem] category :: " + paramVO.getCategory());
		
		if ( konanPropertiesService.getString("cate_perform").equals(paramVO.getCategory()) ) {
			
			Map<String, Object> resultPerform;
			resultPerform = performService.performSearchDB(paramVO);
			total += (int) resultPerform.get("performTotal");
			
			partNameList = performService.selectPartNameGroupBy(paramVO);
			
			model.addAttribute("performCol", resultPerform.get("performCol"));
			model.addAttribute("performList", resultPerform.get("performData"));
			model.addAttribute("performTotal", resultPerform.get("performTotal"));		
			model.addAttribute("paging", commonService.setPagingModel(paramVO.getPageNum(), (int) resultPerform.get("performTotal")));
			
		} else {
			result = pfService.PfSearch(paramVO);
			total += result.getTotal();
			
			model.addAttribute("pfRow", result.getRows());
			model.addAttribute("pfList", result.getResult());
			model.addAttribute("pfTotal", result.getTotal());	
			model.addAttribute("paging", commonService.setPagingModel(paramVO.getPageNum(), (int) result.getTotal()));
		}
		model.addAttribute("partNameList", partNameList);
		
		model.addAttribute("formatTotal", commonUtil.formatMoney(total));
		model.addAttribute("total", total);
		
		return model;
	}
	
	/**
	 * 지식DB 상세조회
	 * @param category
	 * @param idx
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail/{category}/{idx}", produces="text/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String detail(@PathVariable String category, @PathVariable String idx, HttpServletRequest rq, HttpServletResponse rs) throws Exception {
		RestResultVO result;
		ParameterVO paramVO = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		String message = "no";
		
		if ( !StringUtils.isEmpty(idx)) {
			
			// idx의 형태는 {MD5_KEY}_{GUBUN_NO}이다.
			if ( idx.indexOf("_") > -1 ) {
				paramVO = new ParameterVO();
				paramVO.setKwd(idx.split("_")[0]);	// MD5_KEY 분리
				paramVO.setGubun_no(idx.split("_")[1]);	// GUBUN_NO 분리
				
				result = pfService.detail(paramVO, category);
				resultMap.put("result", result);
				message = "ok";
			} else {
				paramVO = new ParameterVO();
				paramVO.setKwd(idx);	// MD5_KEY 셋팅
				
				result = pfService.detail(paramVO, category);
				resultMap.put("result", result);
				message = "ok";
			}
		}
		
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 지식DB 코멘트 상세조회
	 * @param category
	 * @param idx
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detailComment/{category}/{idx}", produces="text/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String detailCommentList(@PathVariable String category, @PathVariable String idx, HttpServletRequest rq, HttpServletResponse rs) throws Exception {
		List<CommentVO> result;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		String message = "no";
		
		if ( !StringUtils.isEmpty(idx)) {
			
			// idx의 형태는 {MD5_KEY}_{GUBUN_NO}이다.
			if ( idx.indexOf("_") > -1 ) {
				
				result = pfService.detailComment(category, idx);
				if ( result != null ) {
					resultMap.put("result", result);
					message = "ok";
				}
			}
		}
		
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 지식DB 코멘트 추가
	 * @param category
	 * @param idx
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detailComment/{category}/{idx}", produces="text/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String detailCommentAdd(@PathVariable String category, @PathVariable String idx, @RequestBody String paramComment, HttpServletRequest rq, HttpServletResponse rs) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		String message = "no";
		
		if ( !StringUtils.isEmpty(idx)) {
			
			// idx의 형태는 {MD5_KEY}_{GUBUN_NO}이다.
			if ( idx.indexOf("_") > -1 ) {
				
				UserVO user = (UserVO) rq.getSession().getAttribute("login_user");
				
				// POST로 넘어온 RequestBody값은 URL Encoding이 되어서 날라오기 때문에 Decoding을 해주어야 한다.
				String decodeParam = URLDecoder.decode(paramComment.toString(), "UTF-8");
				logger.debug("[detailCommentAdd] decodeParam :: " + decodeParam );
				
				String rating = decodeParam.split("&")[0].split("=")[1];
				String comment = decodeParam.split("&")[1].split("=")[1];
				
				logger.debug("[detailCommentAdd] rating :: " + rating);
				logger.debug("[detailCommentAdd] comment :: " + comment);
				
				CommentVO commentVO = new CommentVO();
				commentVO.setCategory(category);
				commentVO.setRepo_key(idx.split("_")[0]);
				commentVO.setGubun_no(idx.split("_")[1]);
				commentVO.setRating(Integer.parseInt(rating));
				commentVO.setComment(comment);
				commentVO.setUser_id(user.getUser_id());	// session으로 교체
				
				int r = pfService.setPFComment(commentVO);
				
				if ( r == 1 ) {
					message = "ok";
				}
			}
		}
		
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 지식DB 이미지조회
	 * @param category
	 * @param idx
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pfImage/{category}", produces="text/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String image(@PathVariable String category, @RequestParam Map<String, String> map, HttpServletRequest rq, HttpServletResponse rs) throws Exception {
		RestResultVO result;
		ParameterVO paramVO = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		String message = "no";
		
		// 파라미터 세팅
		try {
			paramVO = setParameter.setParameter(map);
			paramVO.setCategory(category);
			logger.debug(paramVO.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		result = pfService.PfImageSearch(paramVO);
		
		if ( result != null && result.getTotal() > 0 ) {
			message = "ok";
			resultMap.put("result", result);
		}
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////// 진단 DB 분석 
	//////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 지식DB 코멘트 상세조회
	 * @param category
	 * @param idx
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/analysis/{category}", produces="text/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String analysis(@PathVariable String category, @RequestParam Map<String, String> map, HttpServletRequest rq, HttpServletResponse rs) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		ParameterVO paramVO = null;
		
		// 파라미터 세팅
		try {
			paramVO = setParameter.setParameter(map);
			paramVO.setCategory(category);
			logger.debug(paramVO.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		RestResultVO symptomKwdVO = pfService.getSymptomKwdGroupBy(paramVO);
		RestResultVO actionKwdVO = pfService.getActionKwdGroupBy(paramVO);
		RestResultVO publishYMVO = pfService.getPublishYMGroupBy(paramVO);
		RestResultVO depthVO = null;
		
		if ( !"all".equals(paramVO.getPowerComp()) && "all".equals(paramVO.getPowerSt()) ) {
			depthVO = pfService.getDepthGroupBy(paramVO, "powerSt");
		} else if ( !"all".equals(paramVO.getPowerComp()) && !"all".equals(paramVO.getPowerSt()) && "all".equals(paramVO.getStNo()) ) {
			depthVO = pfService.getDepthGroupBy(paramVO, "stNo");
		} else if ( "all".equals(paramVO.getPowerComp()) ) {
			depthVO = pfService.getDepthGroupBy(paramVO, "powerComp");
		}
		
		if ( symptomKwdVO.getResult().size() == 0 ) {
			resultMap.put("isSymptomData", "no");
		} else {
			resultMap.put("isSymptomData", "yes");
		}
		
		if ( actionKwdVO.getResult().size() == 0 ) {
			resultMap.put("isActionData", "no");
		} else {
			resultMap.put("isActionData", "yes");
		}
		
		if ( publishYMVO.getResult().size() == 0 ) {
			resultMap.put("isPublicData", "no");
		} else {
			resultMap.put("isPublicData", "yes");
		}
		
		if ( depthVO == null || depthVO.getResult().size() == 0 ) {
			resultMap.put("isDepthData", "no");
		} else {
			resultMap.put("isDepthData", "yes");
		}
		
		resultMap.put("symptomKwdList", symptomKwdVO);
		resultMap.put("actionKwdList", actionKwdVO);
		resultMap.put("publishYMList", publishYMVO);
		resultMap.put("depthList", depthVO);
		resultMap.put("powerComp", paramVO.getPowerComp());
		resultMap.put("powerSt", paramVO.getPowerSt());
		resultMap.put("stNo", paramVO.getStNo());
		resultMap.put("partName", paramVO.getPartName());
		
		return gson.toJson(resultMap);
	}
	
	@RequestMapping(value = "/symptomToActionTop10", produces="text/json;charset=UTF-8")
	@ResponseBody
	public String symptomToAction(@RequestParam Map map, HttpServletResponse rs) throws Exception {
		logger.debug("[symptomToAction] start");
		Gson gson = new Gson();
		RestResultVO actionKwdVO = pfService.getSymptomToAction(map);
		
		return gson.toJson(actionKwdVO);
	}
}
