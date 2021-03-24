package egovframework.kf.kepri.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.SetParameter;
import egovframework.kf.common.service.CommonService;
import egovframework.kf.common.service.KsfService;
import egovframework.kf.common.service.SmartMatchService;
import egovframework.kf.common.vo.RepoInfoVO;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.kepri.service.BoilerService;
import egovframework.kf.kepri.service.GenInsService;
import egovframework.kf.kepri.service.GenPrevService;
import egovframework.kf.kepri.service.KepriService;
import egovframework.kf.kepri.service.PerformService;
import egovframework.kf.kepri.service.StTurbineService;
import egovframework.kf.kepri.service.TurbineService;
import egovframework.kf.user.vo.UserVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : SearchController.java
 * Description : 통합 검색
 *
 * @since 2017�뀈
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Controller
@RequestMapping(value="/kepri")
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	/** BoilerService */
	@Resource(name = "boilerService")
	private BoilerService boilerService;
	
	/** TurbinService */
	@Resource(name = "turbineService")
	private TurbineService turbineService;
	
	/** ST TurbinService */
	@Resource(name = "stTurbineService")
	private StTurbineService sTturbineService;
	
	/** GenPrevService */
	@Resource(name = "genPrevService")
	private GenPrevService genPrevService;
	
	/** GenInsService */
	@Resource(name = "genInsService")
	private GenInsService genInsService;
	
	/** KepriService */
	@Resource(name = "kepriService")
	private KepriService kepriService;
	
	/** PerformService */
	@Resource(name = "performService")
	private PerformService performService;
	
	/** CommonService */
	@Resource(name = "commonService")
	private CommonService commonService;
	
	/** SmartMatchService */
	@Resource(name = "smartMatchService")
	private SmartMatchService smartMatchService;
	
	/** KsfService */
	@Resource(name = "ksfService")
	private KsfService ksfService;
	
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
	 * 기본 통합 검색
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search.do")
	public String search(@RequestParam Map<String, String> map, HttpServletRequest rq, Model model) throws Exception {
		
		ParameterVO paramVO = null;
		String spellKwd = null;
		// 파라메터 셋팅
		try {
			paramVO = setParameter.setParameter(map);
			
			UserVO user = (UserVO) rq.getSession().getAttribute("login_user");
			paramVO.setUserId(user.getUser_nm());
			logger.debug("paramVO :: " + paramVO.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}

		// 데이터 셋팅
		setCategoryModel(model, paramVO);
		
		// 오타교정
		if ( paramVO.getKwd() != null && !"".equals(paramVO.getKwd()) ) {
			spellKwd = ksfService.getSpellChek(paramVO.getKwd());
			model.addAttribute("spellKwd", spellKwd);
		}

		model.addAttribute("params", paramVO);
		model.addAttribute("openMenu", "search");
		model.addAttribute("pageName", konanPropertiesService.getString("pageReport"));	// 파일 다운로드 Log 남길 시 페이지명
		
		return "search/search";
	}
	
	/**
	 * 카테고리별로 검색
	 *
	 * @return Model
	 * @throws Exception
	 */
	private Model setCategoryModel(Model model, ParameterVO paramVO) throws Exception {
		RestResultVO result;
		int total = 0;
		
		// 보일러
		if (commonUtil.easyChkEqual("TOTAL,BOILER", paramVO.getCategory(), ",", false)) {
			logger.debug("[setCategoryModel] Search Boiler Start");
			result = boilerService.BoilerSearch(paramVO);
			total += result.getTotal();
			
			model.addAttribute("boilerRow", result.getRows());
			model.addAttribute("boilerList", result.getResult());
			model.addAttribute("boilerTotal", result.getTotal());	
			model.addAttribute("paging", commonService.setPagingModel(paramVO.getPageNum(), (int) result.getTotal()));
			logger.debug("[setCategoryModel] Search Boiler End");
		}
		
		// 터빈
		if (commonUtil.easyChkEqual("TOTAL,GT_TURBINE", paramVO.getCategory(), ",", false)) {
			logger.debug("[setCategoryModel] Search Turbin Start");
			result = turbineService.turbinSearch(paramVO);
			total += result.getTotal();
			
			model.addAttribute("turbineRow", result.getRows());
			model.addAttribute("turbineList", result.getResult());
			model.addAttribute("turbineTotal", result.getTotal());		
			model.addAttribute("paging", commonService.setPagingModel(paramVO.getPageNum(), (int) result.getTotal()));
			logger.debug("[setCategoryModel] Turbin Total : " + result.getTotal());
			logger.debug("[setCategoryModel] Search Turbin End");
		}
		
		// 증기 터빈
		if (commonUtil.easyChkEqual("TOTAL,ST_TURBINE", paramVO.getCategory(), ",", false)) {
			logger.debug("[setCategoryModel] Search ST Turbine Start");
			result = sTturbineService.turbinSearch(paramVO);
			total += result.getTotal();
			
			model.addAttribute("sTturbineRow", result.getRows());
			model.addAttribute("sTturbineList", result.getResult());
			model.addAttribute("sTturbineTotal", result.getTotal());		
			model.addAttribute("paging", commonService.setPagingModel(paramVO.getPageNum(), (int) result.getTotal()));
			logger.debug("[setCategoryModel] ST Turbin Total : " + result.getTotal());
			logger.debug("[setCategoryModel] Search ST Turbin End");
		}
		
		// 발전기 예방진단
		if (commonUtil.easyChkEqual("TOTAL,GEN_PREV", paramVO.getCategory(), ",", false)) {
			logger.debug("[setCategoryModel] Search GEN_PREV Start");
			result = genPrevService.genSearch(paramVO);
			total += result.getTotal();
			
			model.addAttribute("genPrevRow", result.getRows());
			model.addAttribute("genPrevList", result.getResult());
			model.addAttribute("genPrevTotal", result.getTotal());		
			model.addAttribute("paging", commonService.setPagingModel(paramVO.getPageNum(), (int) result.getTotal()));
			logger.debug("[setCategoryModel] GEN_PREV Total : " + result.getTotal());
			logger.debug("[setCategoryModel] Search GEN_PREV End");
		}
		
		// 발전기 절연진단
		if (commonUtil.easyChkEqual("TOTAL,GEN_INS", paramVO.getCategory(), ",", false)) {
			logger.debug("[setCategoryModel] Search GEN_INS Start");
			result = genInsService.genSearch(paramVO);
			total += result.getTotal();
			
			model.addAttribute("genInsRow", result.getRows());
			model.addAttribute("genInsList", result.getResult());
			model.addAttribute("genInsTotal", result.getTotal());		
			model.addAttribute("paging", commonService.setPagingModel(paramVO.getPageNum(), (int) result.getTotal()));
			logger.debug("[setCategoryModel] GEN_INS Total : " + result.getTotal());
			logger.debug("[setCategoryModel] Search GEN_INS End");
		}
		
		// 발전기 누설/흡습
		if (commonUtil.easyChkEqual("TOTAL,KEPRI", paramVO.getCategory(), ",", false)) {
			logger.debug("[setCategoryModel] Search KEPRI Start");
			result = kepriService.genSearch(paramVO);
			total += result.getTotal();
			
			model.addAttribute("kepriRow", result.getRows());
			model.addAttribute("kepriList", result.getResult());
			model.addAttribute("kepriTotal", result.getTotal());		
			model.addAttribute("paging", commonService.setPagingModel(paramVO.getPageNum(), (int) result.getTotal()));
			model.addAttribute("imgPath",konanPropertiesService.getString("imagePathLinux"));
			logger.debug("[setCategoryModel] KEPRI Total : " + result.getTotal());
			logger.debug("[setCategoryModel] Search KEPRI End");
		}
		
		// 성능
		if (commonUtil.easyChkEqual("TOTAL,PERFORM", paramVO.getCategory(), ",", false)) {
			logger.debug("[setCategoryModel] Search PERFORM Start");
			result = performService.performSearch(paramVO);
			total += result.getTotal();
			
			model.addAttribute("performRow", result.getRows());
			model.addAttribute("performList", result.getResult());
			model.addAttribute("performTotal", result.getTotal());		
			model.addAttribute("paging", commonService.setPagingModel(paramVO.getPageNum(), (int) result.getTotal()));
			logger.debug("[setCategoryModel] PERFORM Total : " + result.getTotal());
			logger.debug("[setCategoryModel] Search PERFORM End");
		}
		
		model.addAttribute("formatTotal", commonUtil.formatMoney(total));
		model.addAttribute("total", total);
		
		return model;
	}
	
	/**
	 * 보고서 상세조회
	 * @param category
	 * @param idx
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detailReport/{category}/{idx}", produces="text/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String getDetailData(@PathVariable String category, @PathVariable String idx, HttpServletRequest rq, HttpServletResponse rs) throws Exception {
		ParameterVO paramVO = new ParameterVO();
		Map<String, Object> resultMap = null;
		Gson gson = new Gson();
		
		try {
			// 사용자 정보 조회
			UserVO user = (UserVO) rq.getSession().getAttribute("login_user");
			String pkVal = "";
			String memeCate = "";
			String cate = "";
			
			// 유사문서 Modal창에서 클릭 했을 경우 PK 앞에 약어가 붙어있으므로 짤라서 번호만 담아준다.
			if (idx.indexOf("_") > -1 ) {
				pkVal =idx.split("_")[1];
				memeCate = idx.split("_")[0];
				logger.debug("[getDetailData] memeCate :: " + memeCate);
				if (!"".equals(memeCate) && memeCate != null ) {
					if ( "GT".equals(memeCate) || "GTTR".equals(memeCate) ) cate = "GT_TURBINE";
					else if ( "ST".equals(memeCate) || "STTR".equals(memeCate) ) cate = "ST_TURBINE";
					else if ( "GP".equals(memeCate) ) cate = "GEN_PREV";
					else cate = "BOILER";
				}
			}
			else {
				pkVal = idx;
				cate = category;
			}
			
			// 조회를 위한 조건으로 pk값을 kwd로 담는다.
			paramVO.setKwd(pkVal);
			paramVO.setPageNum(1);
			paramVO.setCategory(cate);
			// log용 사용자 정보 셋팅
			paramVO.setUserId(user.getUser_nm());
			logger.debug("[getDetailData] setKwd :: " + paramVO.getKwd());
			
			// SmartMatch log 남기기용 category 약어(B_, GT_, ST_, GP_, GI_)
			String kind = commonUtil.getCategoryAcronym(cate);
			
			// 카테고리로 이동하여 pk를 조회한 결과를 담는다.
			if ( konanPropertiesService.getString("cate_perform").equals(cate) ) {
//				resultMap = performService.getDetailData(paramVO);
			} else if ( konanPropertiesService.getString("cate_gt_turbine").equals(cate) ) {
				resultMap = turbineService.getDetailData(paramVO);
			} else if ( konanPropertiesService.getString("cate_st_turbine").equals(cate)) {
				resultMap = sTturbineService.getDetailData(paramVO);
			} else if ( konanPropertiesService.getString("cate_gen_prev").equals(cate) ) {
				resultMap = genPrevService.getDetailData(paramVO);
			} else if ( konanPropertiesService.getString("cate_gen_ins").equals(cate) ) {
				resultMap = genInsService.getDetailData(paramVO);
			} else if ( konanPropertiesService.getString("cate_gen_kepri").equals(cate) ) {
				resultMap = kepriService.getDetailData(paramVO);
			} else {
				resultMap = boilerService.getDetailData(paramVO);
			}
			
			// 조회한 결과가 있을 경우
			if ( resultMap != null && resultMap.containsKey("meta") ) {				
				
				// IDX필드가 각 카테고리마다 다 달라서 통합용 VO로 다시 정제한다.
				RepoInfoVO repoInfo = commonService.setRepoInfo( (List<Map<String, String>>) resultMap.get("meta"), cate);
				
				// smartMatch Log를 남긴다. userId, Pk, 점수를 log로 쌓는다.
				// 상세화면 조회의 경우 점수는 무조건 1로 셋팅한다.
				// 점수를 다르게 주는 경우는 코멘트 별점 기능에서 별점에 따라 점수를 다르게 셋팅한다.
				boolean isLog = smartMatchService.writeSmartMatchLog(String.valueOf(user.getUser_id()), kind + pkVal, 1);
				if ( isLog ) {
					logger.debug("[getDetailData] smartMatch log success");
				}
						
				resultMap.put("meta", repoInfo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return gson.toJson(resultMap);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////// 랭킹
	// 텍스트분석 > 문장 분리
	@RequestMapping(value = "/symptom/{category}/{idx}", produces = "application/json; charset=utf8", method = RequestMethod.GET)
	@ResponseBody
	public String symptom(@PathVariable String category, @PathVariable String idx, Model model) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		String message = "no";
		
		if ( !StringUtils.isEmpty(idx) ) {
			
			RestResultVO result = boilerService.getSymptom(category, idx);
			if ( result != null && result.getTotal() > 0 ) {
				message = "ok";
				resultMap.put("symptomList", result);
			}
		}
		resultMap.put("message", message);
		
		return gson.toJson(resultMap);
	}
}
