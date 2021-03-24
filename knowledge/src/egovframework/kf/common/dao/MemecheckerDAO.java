package egovframework.kf.common.dao;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.DCUtil;
import egovframework.kf.dao.RestModule;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.data.SearchVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : SmartMatchDAO.java
 * Description : SmartMatch 관련 로직
 *
 * @since 2019년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Repository
public class MemecheckerDAO {
	private static final Logger logger = LoggerFactory.getLogger(MemecheckerDAO.class);
	
	/** 엔진 공통 유틸 */
	@Resource(name = "dcUtil")
	private DCUtil dcUtil;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** REST 모듈 */
	@Resource(name = "restModule")
	private RestModule restModule;
		
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	

	/**
	 *  Name : updateMemechecker
	 *  Comment : group정보를 받아 해당 group을 다시 계산하여 Memechecker에 저장.
	 */
	public boolean updateMemechecker(String group) throws Exception {
		
		String memDomain = konanPropertiesService.getString("memDomain");
		
		StringBuffer memUrl = new StringBuffer();
		memUrl.append(konanPropertiesService.getString("memUrl"));
		memUrl.append("sim/measure");
		memUrl.append("/" + memDomain);
		memUrl.append("/" + group);
		logger.debug("[getMemecheckerRanking] url : " + memUrl.toString());
		
		StringBuffer sb = restModule.restPost(memUrl.toString(), "{}", "json");
		
		if ( "error".equals(sb.toString()) ) return false;
		
		return true;
	}
	
	/**
	 *  Name : getMemecheckerRanking
	 *  Comment : Map정보(카테고리, 인덱스값) 받아 유사문서의 docId를 구함.
	 */
	public List<Map<String, String>> getMemecheckerRanking(Map paramMap, String cate) throws Exception {
		logger.debug("[getMemecheckerRanking] getMemecheckerRanking START");
		String memDomain = konanPropertiesService.getString("memDomain");
		String memGroup = konanPropertiesService.getString("memGroup");
		String charSet = konanPropertiesService.getString("charset");
		
		StringBuffer memUrl = new StringBuffer();
		memUrl.append(konanPropertiesService.getString("memUrl"));
		memUrl.append("sim/score");
		memUrl.append("/" + memDomain);
		memUrl.append("/" + memGroup);
		memUrl.append("/" + cate + paramMap.get("idx"));
		logger.debug("[getMemecheckerRanking] url : " + memUrl.toString());
		
		StringBuffer sb = dcUtil.getUrlData(memUrl.toString(), charSet);
				
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(sb.toString());
		
		JSONObject jsonObj = (JSONObject) obj;
		JSONArray jsonArr = (JSONArray) jsonObj.get("list");
		
		int max = jsonArr.size() > 3 ? 3 : jsonArr.size();
		
		for ( int i=0; i<max; i++ ) {
			JSONObject jsonRow = (JSONObject) jsonArr.get(i);
			map = new HashMap<String, String>();
			
			map.put("docId", (String) jsonRow.get("docId"));
			map.put("score", String.valueOf(jsonRow.get("score")));
			map.put("category", paramMap.get("category").toString().toLowerCase());
			
			list.add(map);
		}
		
		return list;
	}
	
	/**
	 *  Name : getMemecheckerRanking
	 *  Comment : docId 리스트를 파라메터로 받아 파일명을 return.
	 */
	public RestResultVO getMemecheckerFileNames(List<Map<String,String>> list) throws Exception {
		logger.debug("[getMemecheckerRanking] getMemecheckerRanking START");
		
		SearchVO searchVO = new SearchVO();		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		String strNmFd = "NO_idx";
		String[] docIds = new String[list.size()];
		
		for ( int i=0; i<list.size(); i++ ) {
			docIds[i] = list.get(i).get("docId");
		}

		// 쿼리 부분
		query = dcUtil.makeINQuery(strNmFd, docIds, false, query);
		logger.debug("[getMemecheckerRanking] query : " + query.toString());
		
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(konanPropertiesService.getString("memField"));
		searchVO.setFrom(konanPropertiesService.getString("memFrom"));
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		searchVO.setHilightTxt("");
		searchVO.setDefaultHilite("off");
		
		String restUrl = dcUtil.getURL(searchVO);
		logger.debug("[getMemecheckerRanking] restUrl : " + restUrl);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearch(restUrl, restVO, searchVO.getFields()); //get방식 호출	
		
		//초기화
		query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}
}
