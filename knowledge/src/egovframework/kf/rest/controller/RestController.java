package egovframework.kf.rest.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import egovframework.kf.data.RestResultVO;
import egovframework.kf.rest.service.RestService;
import egovframework.kf.rest.vo.RestVO;

/**
 * Class Name : RestController.java
 * Description : RestFul API용
 *
 * @since 202004
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Controller
@RequestMapping(value="/rest")
public class RestController {
	private static final Logger logger = LoggerFactory.getLogger(RestController.class);

	
	@Resource(name="restService")
	private RestService restService;
	/**
	 * 인터페이스명 : Boiler knowledge List
	 * 연계대상 : AR/VR, 발전운전데이터
	 * 연계시점 : AR/VR, 발전데이터시스템에서 호출 시
	 * 연계방법 : TCP/IP
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pfsearch/{cate}", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf8")
	@ResponseBody
	public String boilerKnowledgeList(@ModelAttribute RestVO restvo, Model model, @PathVariable String cate) throws Exception {
		logger.debug("[knowledgeList] START");
		logger.debug(cate);
		logger.debug(String.valueOf(restvo.getLimit()));
		RestResultVO restResultvo;
		Gson gson = new Gson();
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		restvo.setCate(cate);
		
		restResultvo = restService.getKnowlegeList(restvo);
		
		// TODO Auto-generated catch block
		return gson.toJson(restResultvo);
	}
	
	@RequestMapping(value = "/pfsearch/{cate}/{partid}", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf8")
	@ResponseBody
	public String boilerKnowledgeRetrieve(@ModelAttribute RestVO restvo, Model model, @PathVariable String cate, @PathVariable String partid) throws Exception {
		logger.debug("[boilerKnowledgeRetrieve] START");
		logger.debug(cate);
		logger.debug(String.valueOf(restvo.getLimit()));
		RestResultVO restResultvo;
		Gson gson = new Gson();
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		restvo.setCate(cate);
		restvo.setPartid(partid);
		
		restResultvo = restService.getKnowlegeRetrieve(restvo);
		
		
		return gson.toJson(restResultvo);
	}
	
	@RequestMapping(value = "/pfsearch/{cate}/{partid}/images", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf8")
	@ResponseBody
	public String boilerKnowledgeRetrieveImages(@ModelAttribute RestVO restvo, Model model, @PathVariable String cate, @PathVariable String partid) throws Exception {
		logger.debug("[boilerKnowledgeRetrieveImages] START");
		logger.debug(cate);
		logger.debug(String.valueOf(restvo.getLimit()));
		RestResultVO restResultvo;
		Gson gson = new Gson();
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		restvo.setCate(cate);
		restvo.setPartid(partid);
		
		restResultvo = restService.getKnowlegeRetrieveImages(restvo);
		
		
		return gson.toJson(restResultvo);
	}
	
	@RequestMapping(value = "/search/{cate}", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf8")
	@ResponseBody
	public String searchList(@ModelAttribute RestVO restvo, Model model, @PathVariable String cate) throws Exception {
		logger.debug("[searchList] START");
		logger.debug(cate);
		logger.debug(String.valueOf(restvo.getLimit()));
		RestResultVO restResultvo;
		Gson gson = new Gson();
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		restvo.setCate(cate);
		
		
		restResultvo = restService.searchList(restvo);
		

		return gson.toJson(restResultvo);
	}
	
	@RequestMapping(value = "/search/{cate}/{partid}", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf8")
	@ResponseBody
	public String searchRetrieve(@ModelAttribute RestVO restvo, Model model, @PathVariable String cate, @PathVariable String partid) throws Exception {
		logger.debug("[knowledgeList] START");
		logger.debug(cate);
		logger.debug(String.valueOf(restvo.getLimit()));
		RestResultVO restResultvo;
		Gson gson = new Gson();
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		restvo.setCate(cate);
		restvo.setPartid(partid);
		
		restResultvo = restService.searchRetrieve(restvo);
		
		
		return gson.toJson(restResultvo);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////// 추천 API
	/**
	 * 인터페이스명 : 추천지식 List
	 * 연계대상 : AR/VR, 발전운전데이터
	 * 연계시점 : AR/VR, 발전데이터시스템에서 호출 시
	 * 연계방법 : TCP/IP
	 * @param restvo
	 * @param model
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/recommend/{user_id}", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf8")
	@ResponseBody
	public String recommendList(@ModelAttribute RestVO restvo, Model model, @PathVariable String user_id) throws Exception {
		logger.debug("[recommendList] user_id :: " + user_id + " , limit :: " + restvo.getLimit());
		Gson gson = new Gson();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		restvo.setUser(user_id);
		resultMap = restService.recommendList(restvo);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 인터페이스명 : 최신문서 List
	 * 연계대상 : AR/VR, 발전운전데이터
	 * 연계시점 : AR/VR, 발전데이터시스템에서 호출 시
	 * 연계방법 : TCP/IP
	 * @param restvo
	 * @param model
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/recentReport/{cate}", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf8")
	@ResponseBody
	public String recentReportList(@ModelAttribute RestVO restvo, Model model, @PathVariable String cate) throws Exception {
		logger.debug("[recommendList] START");
		logger.debug("[recommendList] user_id :: " + cate + " , limit :: " + restvo.getLimit());
		Gson gson = new Gson();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if ( "gt_turbine".equals(cate) ) {
			restvo.setCate("turbin");
			restvo.setRepo_kind("가스터빈");
		} else if ( "st_turbine".equals(cate) ) {
			restvo.setCate("turbin");
			restvo.setRepo_kind("증기터빈");
		} else if ( "gen_prev".equals(cate) ) {
			restvo.setCate("gen");
			restvo.setRepo_kind("예방진단");
		} else if ( "gen_ins".equals(cate) ) {
			restvo.setCate("gen");
			restvo.setRepo_kind("절연진단");
		} else if ( "kepri".equals(cate) ) {
			restvo.setCate("gen");
			restvo.setRepo_kind("누설흡습");
		}
		
		resultMap = restService.getRecentReportList(restvo);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 인터페이스명 : 최신문서 List
	 * 연계대상 : AR/VR, 발전운전데이터
	 * 연계시점 : AR/VR, 발전데이터시스템에서 호출 시
	 * 연계방법 : TCP/IP
	 * @param restvo
	 * @param model
	 * @param partid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/recentReport/{cate}/{partid}", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf8")
	@ResponseBody
	public String recentReportRetrieve(@ModelAttribute RestVO restvo, Model model, @PathVariable String cate, @PathVariable String partid) throws Exception {
		logger.debug("[recommendList] START");
		logger.debug("[recommendList] user_id :: " + cate + " , limit :: " + restvo.getLimit());
		RestResultVO restResultvo;
		Gson gson = new Gson();
		
		restvo.setCate(cate);
		restvo.setPartid(partid);
		
		restResultvo = restService.recommendRetrieve(restvo);
		
		return gson.toJson(restResultvo);
	}
	
	/**
	 * 인터페이스명 : 인기지식 List
	 * 연계대상 : AR/VR, 발전운전데이터
	 * 연계시점 : AR/VR, 발전데이터시스템에서 호출 시
	 * 연계방법 : TCP/IP
	 * @param restvo
	 * @param model
	 * @param cate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/popularKnowledge/{cate}", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf8")
	@ResponseBody
	public String popularKnowledgeList(@ModelAttribute RestVO restvo, Model model, @PathVariable String cate) throws Exception {
		logger.debug("[popularKnowledgeList] START");
		logger.debug("[popularKnowledgeList] cate :: " + cate + " , limit :: " + restvo.getLimit());
		Gson gson = new Gson();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		logger.debug("[popularKnowledgeList] getPopularKnowledge START");
		resultMap = restService.getPopularKnowledge(cate);
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 인터페이스명 : 인기지식 상세
	 * 연계대상 : AR/VR, 발전운전데이터
	 * 연계시점 : AR/VR, 발전데이터시스템에서 호출 시
	 * 연계방법 : TCP/IP
	 * @param restvo
	 * @param model
	 * @param cate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/popularKnowledge/{cate}/{partid}", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf8")
	@ResponseBody
	public String popularKnowledgeList(@ModelAttribute RestVO restvo, Model model, @PathVariable String cate, @PathVariable String partid) throws Exception {
		logger.debug("[popularKnowledgeList] START");
		logger.debug("[popularKnowledgeList] cate :: " + cate + " , partid :: " + partid);
		RestResultVO restResultvo;
		Gson gson = new Gson();
		
		restvo.setCate(cate);
		restvo.setPartid(partid);
		
		restResultvo = restService.recommendRetrieve(restvo);
		
		return gson.toJson(restResultvo);
	}
	
	
	@RequestMapping(value = "/commentList", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf8")
	@ResponseBody
	public String commentList(@ModelAttribute RestVO restvo, Model model) throws Exception {
		logger.debug("[commentList] START");
		
		Gson gson = new Gson();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
		
		
		return gson.toJson(result);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////분석 API
	
	/**
	 * 파일이름을 입력받아 저장된 경로(analysis)의 대상 파일의 원문을 추출한다.
	 * @param restvo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/extractFileBody", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String extractFileBody(@ModelAttribute RestVO restvo, Model model) throws Exception {
		logger.debug("[extractFileBody] START");
		
		Gson gson = new Gson();
		Map<String, String> resultMap = restService.extractFileBody(restvo.getFile_nm());
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 텍스트를 입력받아 문장 단위로 나눈다.
	 * @param restvo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sentence", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String sentence(@ModelAttribute RestVO restvo, Model model) throws Exception {
		logger.debug("[sentence] START");
		
		Gson gson = new Gson();
		Map<String, Object> resultMap = restService.sentence(restvo.getText());
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 텍스트를 입력받아 텍스트의 형태소를 분석한다.
	 * @param restvo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/posTag", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String posTag(@ModelAttribute RestVO restvo, Model model) throws Exception {
		logger.debug("[posTag] START");
		
		Gson gson = new Gson();
		Map<String, Object> resultMap = restService.posTag(restvo.getText());
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 텍스트를 입력받아 텍스트의 구문분석을 한다.
	 * @param restvo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/parse", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String parse(@ModelAttribute RestVO restvo, Model model) throws Exception {
		logger.debug("[parse] START");
		
		Gson gson = new Gson();
		Map<String, Object> resultMap = restService.parse(restvo.getText());
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 텍스트를 입력받아 시맨틱 패턴이 일치하는 항목이 있는지 확인한다.
	 * @param restvo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/symantic/{cate}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String symantic(@ModelAttribute RestVO restvo, @PathVariable String cate, Model model) throws Exception {
		logger.debug("[symantic] START");
		
		Gson gson = new Gson();
		Map<String, Object> resultMap = restService.symantic(cate, restvo.getText());
		
		return gson.toJson(resultMap);
	}
	
	/**
	 * 텍스트를 입력받아 개체명을 추출한다.
	 * @param restvo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ner", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String symantic(@ModelAttribute RestVO restvo, Model model) throws Exception {
		logger.debug("[ner] START");
		
		Gson gson = new Gson();
		Map<String, Object> resultMap = restService.ner(restvo.getText(), restvo.getCate());
		
		return gson.toJson(resultMap);
	}
}

