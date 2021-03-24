package egovframework.kf.kepri.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.SetParameter;
import egovframework.kf.common.service.CommonService;
import egovframework.kf.data.ParameterVO;
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
@RequestMapping(value="/dashboard")
public class DashboardController {
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	
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
	 * 대시보드 페이지
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dashboard.do")
	public String dashboard(@RequestParam Map<String, String> map, Model model) throws Exception {
		logger.debug("[dashboard] DashBoard Start");
		ParameterVO paramVO = null;
		
		// 파라미터 세팅
		try {
			//paramVO = setParameter.setParameter(map);
			//logger.debug(paramVO.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//파라미터
		//model.addAttribute("params", paramVO);
		model.addAttribute("openMenu", "dashboard");
		model.addAttribute("pageName", konanPropertiesService.getString("pageDashboard"));
		
		return "dashboard/dashboard";
	}
	
	@RequestMapping(value = "/getResultData.do", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String getResultData(@RequestParam Map map) throws Exception  {
		Gson gson = new Gson();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		String isPartName 	= "fail";
		String isSymptom  	= "fail";
		String isAction   	= "fail";
		String isPowerComp 	= "fail";
		String isPowerSt   	= "fail";
		String isPartSym   	= "fail";
		
		logger.debug("[dashboard] map :: " + map.toString());
		
		map.put("callLoc", "dashboard");
		String cate = map.get("cate").toString();
		String job = map.get("job").toString();
		cateInfoMap = commonUtil.getCategoryInfo(cate, "");
		
		// 진단부위 엔진 조회
		if("part".equalsIgnoreCase(job)) {
			List<Map<String, String>> partNameList = commonService.selectPartNameList(map, cateInfoMap);
			if ( partNameList != null && partNameList.size() > 0 ) {
				isPartName = "success";
				resultMap.put("partNameList", partNameList);
				resultMap.put("selPartName", map.get("partName"));
			}
		}
		
		// 손상현상 엔진 조회
		if("sym".equalsIgnoreCase(job)) {
			List<Map<String, String>> symptomKwdList = commonService.selectSymptomKwdList(map, cateInfoMap);
			if ( symptomKwdList != null && symptomKwdList.size() > 0 ) {
				isSymptom = "success";
				resultMap.put("symptomKwdList", symptomKwdList);
				resultMap.put("selSymptomKwd", map.get("symptomKwd"));
			}
		}
		
		
		// 손상대책 엔진 조회
		if("act".equalsIgnoreCase(job)) {
			List<Map<String, String>> actionKwdList = commonService.selectActionKwdList(map, cateInfoMap);
			if ( actionKwdList != null && actionKwdList.size() > 0 ) {
				isAction = "success";
				resultMap.put("actionKwdList", actionKwdList);
				resultMap.put("selActionKwd", map.get("actionKwd"));
			}
		}
		
		// 발전사 엔진 조회
		if("comp".equalsIgnoreCase(job)) {
			// 소스 재사용을 위한 필요 변수 설정.
			map.put("isPF", "N");
			List<Map<String, String>> powerCompList = commonService.selectPowerCompList(map);
			if ( powerCompList != null && powerCompList.size() > 0 ) {
				isPowerComp = "success";
				resultMap.put("powerCompList", powerCompList);
				resultMap.put("selPowerComp", map.get("powerCompName"));
			}
		}
		
		// 사업소 엔진 조회
		if("powerSt".equalsIgnoreCase(job)) {
			map.put("isPF", "N");
			map.put("powerCompName", "");
			List<Map<String, String>> powerStList = commonService.selectPowerStList(map);
			if ( powerStList != null && powerStList.size() > 0 ) {
				isPowerSt = "success";
				resultMap.put("powerStList", powerStList);
				resultMap.put("selPowerStName", map.get("powerStName"));
			}
		}
		
		// 진단 부위에 대한 현상 구하기.
		if("part_sym".equalsIgnoreCase(job)) {
			List<Map<String,String>> returnList = new ArrayList<Map<String,String>>();
			Map<String,String>  returnMap = new HashMap<String,String>();
			try {
				
				List<Map<String, String>> symptomKwdList = commonService.selectSymptomKwdList(map, cateInfoMap);
				if(symptomKwdList != null && symptomKwdList.size() > 0) {
					for (int i = 0; i < symptomKwdList.size(); i++) {
						if(i > 9) break;
						returnMap = new HashMap<String,String>();
						
						// return 제조.
						returnMap.put("id", "id_" + i);
						returnMap.put("name", symptomKwdList.get(i).get("SYMPTOM_KWD"));
						returnMap.put("value", symptomKwdList.get(i).get("count"));
						returnMap.put("parent", "super0");
						returnList.add(returnMap);
						// 	parameter 임시 put 
						//	현상에 대한 조치결과의 리스트를 얻기위함.
						map.put("symptomKwd", symptomKwdList.get(i).get("SYMPTOM_KWD"));
						
						List<Map<String, String>> actList  = commonService.selectActionKwdList(map, cateInfoMap);
						if(actList != null && actList.size() > 0) {
							for (int j = 0; j < actList.size(); j++) {
								if(j > 9) break;
								returnMap = new HashMap<String,String>();
								returnMap.put("id", "id_" + i + "_" + j);
								returnMap.put("name", actList.get(j).get("ACTION_KWD"));
								returnMap.put("value", actList.get(j).get("count"));
								returnMap.put("parent", "id_" + i );
								returnList.add(returnMap);
							}
						}
					}
				}
				if ( returnList != null && returnList.size() > 0 ) {
					isPartSym = "success";
					resultMap.put("parSymKwdList", returnList);
					resultMap.put("selSymptomKwd", map.get("symptomKwd"));
				}
				logger.info(returnList.toString());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		resultMap.put("isPartName", isPartName);
		resultMap.put("isSymptom", isSymptom);
		resultMap.put("isAction", isAction);
		resultMap.put("isPowerComp", isPowerComp);
		resultMap.put("isPowerSt", isPowerSt);
		resultMap.put("isPartSym", isPartSym);
		
		resultMap.put("cate", map.get("cate"));
		resultMap.put("type", map.get("type"));
		
		// Object -> json String으로 만들어준다.
		return gson.toJson(resultMap);
	}
}
