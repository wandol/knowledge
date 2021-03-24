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
public class SmartMatchDAO {
	private static final Logger logger = LoggerFactory.getLogger(SmartMatchDAO.class);
	
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

	public boolean writeSmartMatchLog(String user, String item, int rating) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer smUrl = new StringBuffer();
		smUrl.append(konanPropertiesService.getString("smUrl"));
		smUrl.append("/ksm/logflow/write-user-action");
		smUrl.append("?site=kepri");
		smUrl.append("&user=" + user);
		smUrl.append("&session=sess1");
		smUrl.append("&item=" + URLEncoder.encode(item, "UTF-8"));
		smUrl.append("&action=click");
		smUrl.append("&rating=" + String.valueOf(rating + ".0"));
		smUrl.append("&is-rec=false");
		
		logger.debug("smUrl : " + smUrl.toString());
		
		StringBuffer sb = restModule.restPost(smUrl.toString(), "", "");
				
		if ( "error".equals(sb.toString()) ) return false;
		
		return true;
	}
	
	public List<Map<String, String>> getSmartMatchIDList(String user) throws Exception {
		// TODO Auto-generated method stub		
		StringBuffer smUrl = new StringBuffer();
		smUrl.append(konanPropertiesService.getString("smUrl"));
		smUrl.append("/ksm/reccf/recommend");
		smUrl.append("?type=user_based");
		smUrl.append("&volume=kepri");
		smUrl.append("&user=" + user);
		
		logger.debug("smUrl : " + smUrl.toString());
		
		StringBuffer sb = dcUtil.getUrlData(smUrl.toString(), konanPropertiesService.getString("charset"));
		logger.debug("smUrl result :: " + sb.toString());
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(sb.toString());
		
		JSONObject jsonObj = (JSONObject) obj;
		JSONArray jsonArr = (JSONArray) jsonObj.get("result");
		
		int max = jsonArr.size() > 10 ? 10 : jsonArr.size();
		
		for ( int i=0; i<max; i++ ) {
			JSONObject jsonRow = (JSONObject) jsonArr.get(i);
			map = new HashMap<String, String>();
			
			map.put("item", (String) jsonRow.get("item"));
			list.add(map);
		}
		
		return list;
	}
	
	public Map<String, String> getSmartMatchFileInfo(String id) throws Exception {
		// TODO Auto-generated method stub		
		SearchVO searchVO = new SearchVO();		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		
		String tempCate = id.split("_")[0];
		String idx = id.split("_")[1];
		String gubun_no = id.split("_")[2];
		
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		String cate = "";
		String idxName = "MD5_KEY";
		String from = "";
		
		logger.debug("[getSmartMatchFileInfo] cate : " + tempCate);
		logger.debug("[getSmartMatchFileInfo] idx : " + idx);
		
		cate = commonUtil.getCategoryFullName(tempCate);
		cateInfoMap = commonUtil.getCategoryInfo(cate, "");		// repokind가 따로 필요없음
		
		// 쿼리 부분
		query.append("MD5_KEY='" + idx + "'");
		query.append(" AND ");
		query.append("GUBUN_NO='" + gubun_no + "'");
				
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(konanPropertiesService.getString("smField"));
		searchVO.setFrom(cateInfoMap.get("pFfrom"));
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		searchVO.setHilightTxt("");
		searchVO.setLogInfo("");
				
		// URL 생성
		String restUrl = dcUtil.getURL(searchVO); //get방식 URL생성
		logger.debug("restUrl : " + restUrl);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearch(restUrl, restVO, searchVO.getFields()); //get방식 호출	
		
		//초기화
		query.charAt(0);
		
		if(!success) 
			return null;
		
		if ( restVO.getResult() != null ) {
			restVO.getResult().get(0).put("id", idx);
			restVO.getResult().get(0).put("cate", cate);
			restVO.getResult().get(0).put("gubun_no", gubun_no);
			return restVO.getResult().get(0);
		} else {
			return null;
		}
	}
}
