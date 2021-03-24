package egovframework.kf.rest.dao;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.konantech.kmp.JSONConst;
import com.konantech.kmp.JValue;
import com.konantech.kmp.KJSON;
import com.konantech.kmp.KMP;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.DCUtil;
import egovframework.kf.common.RankingDomain;
import egovframework.kf.dao.RestModule;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.data.SearchVO;
import egovframework.kf.rest.vo.RestVO;
import egovframework.rte.fdl.property.EgovPropertyService;

@Repository
public class RestDAO {
	private static final Logger logger = LoggerFactory.getLogger(RestDAO.class);

	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;
	
	@Resource(name = "dcUtil")
	private DCUtil dcUtil;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** REST 紐⑤뱢 */
	@Resource(name = "restModule")
	private RestModule restModule;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	public RestResultVO getKnowlegeList(RestVO restvo,String fields) {
		SearchVO searchVO = new SearchVO();		
		logger.debug("[knowledgeList dao] START");
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		//String strNmFd = restvo.getFields().isEmpty()? "text_idx": restvo.getFields();
		String partFd = "PART2BODY";
		
		String modName = "kwd";
		String symenticField = "";
		
		StringBuffer ksmSb = new StringBuffer();
		String[] pfKwdArr = null;
		List<String> nerList = new ArrayList<>();
		String nerTemp = "";
		
		String nerBoilerSymNm = konanPropertiesService.getString("nerBoilerName");
		String nerBoilerSymFileNm = konanPropertiesService.getString("nerBoilerFileName");
		
		
		
		//query = dcUtil.makeQuery(strNmFd, restvo.getPfKwd(), "allword", query, "AND");

		RestResultVO restResultVO = null;
		boolean success = false;
		try {
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getKwd(),""))) {
				query = dcUtil.makeQuery( "text_idx" , restvo.getKwd(), "allword", query, "AND");
			}
			
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getPower_comp_nm(),""))) {
				query = dcUtil.makeQuery( "POWER_COMP_NM" , restvo.getPower_comp_nm(), "", query, "AND");
			}
			
			
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getPower_st_nm(),""))) {
				query = dcUtil.makeQuery( "POWER_ST_NM" , restvo.getPower_st_nm(), "", query, "AND");
			}
			
			
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getSt_no(),""))) {
				query = dcUtil.makeQuery( "ST_NO" , restvo.getSt_no(), "", query, "AND");
			}		
				
			/*		
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getPartName(),""))) {
				
				String partName = "PARTNAME_KWD";
				if ( konanPropertiesService.getString("cate_boiler").equals(restvo.getCategory()) ) {
					partName = "PARTNAME";
				}
				
				query = dcUtil.makeQuery( partName , restvo.getPartName(), "", query, "AND");
			}*/
			
			
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getPartname(),""))) {
				
				String partMid = "strlong_idx";
				/*if ( konanPropertiesService.getString("cate_boiler").equals(restvo.getCate().toUpperCase()) ) {
					partMid = "PARTNAME_R";
				} else if ( konanPropertiesService.getString("cate_gen_prev").equals(restvo.getCate().toUpperCase()) ) {
					partMid = "PARTNAME_KWD_R";
				}*/
				
				query = dcUtil.makeQuery( partMid , restvo.getPartname(), "", query, "AND");
				fields +=",SYMPTOM_KWD,ACTION_KWD";
			}
			
			
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getSymptom(),""))) {
				query = dcUtil.makeQuery( "strlong_idx" , restvo.getSymptom(), "", query, "AND");
				fields +=",ACTION_KWD";
			}
			
			
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getAction(),""))) {
				query = dcUtil.makeQuery( "strlong_idx" , restvo.getAction(), "", query, "AND");
				fields +=",SYMPTOM_KWD";
			}
			
			
			if (!commonUtil.null2Str(restvo.getPublish_ym_s(),"").isEmpty() && !commonUtil.null2Str(restvo.getPublish_ym_e(),"").isEmpty()){
				String publish_ym_s = restvo.getPublish_ym_s();
				if(publish_ym_s.length()>=6) publish_ym_s = publish_ym_s.substring(0, 4) +"."+ publish_ym_s.substring(4, 6);
				
				String publish_ym_e = restvo.getPublish_ym_e();
				if(publish_ym_e.length()>=6) publish_ym_e = publish_ym_e.substring(0, 4) +"."+ publish_ym_e.substring(4, 6);
				
				query = dcUtil.makeRangeQuery("PUBLISH_YM", publish_ym_s, publish_ym_e, query) ;
			}
			
			logger.debug("query : " + query);

/*
			sbLog.append(  dcUtil.getLogInfo(commonUtil.null2Str(restvo.getSiteNm(),"SITE"),
					"Problem focus" ,
					commonUtil.null2Str( restvo.getUserId(),""), restvo.getPfKwd(),
					restvo.getPageNum(),
					false,
					restvo.getSort(),
					commonUtil.null2Str( restvo.getRecKwd(),"" )) );
*/
			
			Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(restvo.getCate().toUpperCase(), "");
			logger.debug("cateInfoMap : " + cateInfoMap);
			
			if ( konanPropertiesService.getString("cate_boiler").equals(restvo.getCate().toUpperCase()) ) {
				fields += ",PARTNAME,PART2NAME,PART_MID";
			} else if ( konanPropertiesService.getString("cate_gen_prev").equals(restvo.getCate().toUpperCase()) ) {
				fields += ",PARTNAME_KWD,PART2NAME_KWD,PART2NAME_DETAIL";
			} else {
				fields += ",PARTNAME_KWD,PART2NAME_KWD,PART2NAME_ETC";
			}
			
			searchVO.setUrl(konanPropertiesService.getString("url"));
			searchVO.setCharset(konanPropertiesService.getString("charset"));		
			searchVO.setFields(fields);
			searchVO.setFrom(cateInfoMap.get("pFfrom"));
			searchVO.setHilightTxt(cateInfoMap.get("pFhilight"));
			
			if ( !"".equals(query.toString()) && query.toString() != null ) {
				searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
				searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
			}

			String postParamData = dcUtil.getParamPostDataRest(restvo, searchVO);
			
			
			logger.debug("QUERY pf : " + query.toString());
			logger.debug("RESTURL pf : " + postParamData);
			
			logger.debug("=================================================");
			logger.debug("resturl :: "+searchVO.getUrl()+"?"+postParamData);
			logger.debug("=================================================");
			
			restResultVO = new RestResultVO();
			success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restResultVO, searchVO.getFields());
			logger.debug("[pfSearch] success :: " + success);
			
			
			
			if ( query.length() > 0 ) query.charAt(0);
			if ( sbLog.length() > 0 ) sbLog.charAt(0);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!success) 
			return null;
				
		return restResultVO;		
	}

	public RestResultVO getKnowlegeRetieve(RestVO restvo, String fields) {
		SearchVO searchVO = new SearchVO();		
		StringBuffer query = new StringBuffer();
		
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		
		logger.debug("[getKnowlegeRetieve] cate : " + restvo.getCate());
		RestResultVO restResultVO = null;
		boolean success = false;
		try {
			cateInfoMap = commonUtil.getCategoryInfo(restvo.getCate().toUpperCase(), "");
			
			logger.debug("[getKnowlegeRetieve] cate : " + restvo.getCate());
			if(restvo.getPartid()!=null ){
				
				if(restvo.getPartid().indexOf("-") > 0) {
					
					String arr[] = restvo.getPartid().split("-");
					String md5_key = arr[0];
					String gubun_no = arr[1];
					
					query = dcUtil.makeQuery( "MD5_KEY" , md5_key, "", query, "AND");
					query = dcUtil.makeQuery( "GUBUN_NO" , gubun_no, "", query, "AND");
				
				}
			}
			
			if ( konanPropertiesService.getString("cate_boiler").equals(restvo.getCate().toUpperCase()) ) {
				fields += ",PARTNAME,PART2NAME,PART_MID";
			} else if ( konanPropertiesService.getString("cate_gen_prev").equals(restvo.getCate().toUpperCase()) ) {
				fields += ",PARTNAME_KWD,PART2NAME_KWD,PART2NAME_DETAIL";
			} else {
				fields += ",PARTNAME_KWD,PART2NAME_KWD,PART2NAME_ETC";
			}
			fields += ",SYMPTOM_KWD,ACTION_KWD";
			searchVO.setUrl(konanPropertiesService.getString("url"));
			searchVO.setCharset(konanPropertiesService.getString("charset"));
			searchVO.setFields(fields);
			searchVO.setFrom(cateInfoMap.get("pFfrom"));
			searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
			searchVO.setLogInfo("");
			
			//restvo.setPageSize(20);
			
			String postParamData = dcUtil.getParamPostDataRest(restvo, searchVO);
			
			logger.debug("QUERY pfGetDetail : " + query.toString());
			logger.debug("RESTURL pfGetDetail : " + postParamData);
			
			restResultVO = new RestResultVO();
			success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restResultVO, searchVO.getFields());

			// 2019.11.26 changho.lee
			// query, sbLog가 비어있을 때 에러 방지
			if ( query.length() > 0 ) query.charAt(0);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!success) 
			return null;
				
		return restResultVO;	
	}

	public RestResultVO getKnowlegeRetieveImages(RestVO restvo, String fields) {
		// TODO Auto-generated method stub
		SearchVO searchVO = new SearchVO();		
		StringBuffer query = new StringBuffer();
		
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		
		logger.debug("[getKnowlegeRetieveImages] cate : " + restvo.getCate());
		RestResultVO restResultVO = null;
		boolean success = false;
		try {
		cateInfoMap = commonUtil.getCategoryInfo(restvo.getCate().toUpperCase(), "");
		
		
		
			logger.debug("[getKnowlegeRetieve] cate : " + restvo.getCate());
			if(restvo.getPartid()!=null ){
				
				if(restvo.getPartid().indexOf("-") > 0) {
					
					String arr[] = restvo.getPartid().split("-");
					String md5_key = arr[0];
					String gubun_no = arr[1];
					
					query = dcUtil.makeQuery( "MD5_KEY" , md5_key, "", query, "AND");
					query = dcUtil.makeQuery( "GUBUN_NO" , gubun_no, "", query, "AND");
				
				}
			}

			searchVO.setUrl(konanPropertiesService.getString("url"));
			searchVO.setCharset(konanPropertiesService.getString("charset"));
			searchVO.setFields(fields);
			searchVO.setFrom(cateInfoMap.get("pFfrom"));
			searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
			searchVO.setLogInfo("");
			
			//restvo.setPageSize(20);
			
			String postParamData = dcUtil.getParamPostDataRest(restvo, searchVO);
			
			logger.debug("QUERY pfGetDetail : " + query.toString());
			logger.debug("RESTURL pfGetDetail : " + postParamData);
			
			restResultVO = new RestResultVO();
			success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restResultVO, searchVO.getFields());

			// 2019.11.26 changho.lee
			// query, sbLog가 비어있을 때 에러 방지
			if ( query.length() > 0 ) query.charAt(0);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!success) 
			return null;
				
		return restResultVO;	
	}

	public RestResultVO searchList(RestVO restvo, String fields) {
		// TODO Auto-generated method stub
		SearchVO searchVO = new SearchVO();		
		
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		//String strNmFd = restvo.getFields().isEmpty()? "text_idx": restvo.getFields();
		String partFd = "PART2BODY";
		
		String modName = "kwd";
		String symenticField = "";
		
		StringBuffer ksmSb = new StringBuffer();
		String[] pfKwdArr = null;
		List<String> nerList = new ArrayList<>();
		String nerTemp = "";
		
		String nerBoilerSymNm = konanPropertiesService.getString("nerBoilerName");
		String nerBoilerSymFileNm = konanPropertiesService.getString("nerBoilerFileName");
		
		
		
		//query = dcUtil.makeQuery(strNmFd, restvo.getPfKwd(), "allword", query, "AND");

		RestResultVO restResultVO = null;
		boolean success = false;
		try {
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getKwd(),""))) {
				query = dcUtil.makeQuery( "text_idx" , restvo.getKwd(), "allword", query, "AND");
			}
			
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getPower_comp_nm(),""))) {
				query = dcUtil.makeQuery( "POWER_COMP_NM" , restvo.getPower_comp_nm(), "", query, "AND");
			}
			
			
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getPower_st_nm(),""))) {
				query = dcUtil.makeQuery( "POWER_ST_NM" , restvo.getPower_st_nm(), "", query, "AND");
			}
			
			
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getSt_no(),""))) {
				query = dcUtil.makeQuery( "ST_NO" , restvo.getSt_no(), "", query, "AND");
			}		
				
			/*		
			if(!"".equalsIgnoreCase(commonUtil.null2Str(restvo.getPartName(),""))) {
				
				String partName = "PARTNAME_KWD";
				if ( konanPropertiesService.getString("cate_boiler").equals(restvo.getCategory()) ) {
					partName = "PARTNAME";
				}
				
				query = dcUtil.makeQuery( partName , restvo.getPartName(), "", query, "AND");
			}*/
			
			
			
			if (!commonUtil.null2Str(restvo.getPublish_ym_s(),"").isEmpty() && !commonUtil.null2Str(restvo.getPublish_ym_e(),"").isEmpty()){
				String publish_ym_s = restvo.getPublish_ym_s();
				if(publish_ym_s.length()>=6) publish_ym_s = publish_ym_s.substring(0, 4) +"."+ publish_ym_s.substring(4, 6);
				
				String publish_ym_e = restvo.getPublish_ym_e();
				if(publish_ym_e.length()>=6) publish_ym_e = publish_ym_e.substring(0, 4) +"."+ publish_ym_e.substring(4, 6);
				
				query = dcUtil.makeRangeQuery("PUBLISH_YM", publish_ym_s, publish_ym_e, query) ;
			}
			
			logger.debug("query : " + query);

/*
			sbLog.append(  dcUtil.getLogInfo(commonUtil.null2Str(restvo.getSiteNm(),"SITE"),
					"Problem focus" ,
					commonUtil.null2Str( restvo.getUserId(),""), restvo.getPfKwd(),
					restvo.getPageNum(),
					false,
					restvo.getSort(),
					commonUtil.null2Str( restvo.getRecKwd(),"" )) );
*/
			
			Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(restvo.getCate().toUpperCase(), "");
			logger.debug("cateInfoMap : " + cateInfoMap);
			
			
			
			searchVO.setUrl(konanPropertiesService.getString("url"));
			searchVO.setCharset(konanPropertiesService.getString("charset"));		
			searchVO.setFields(fields);
			searchVO.setFrom(cateInfoMap.get("from"));
			searchVO.setHilightTxt(cateInfoMap.get("hilight"));
			
			if ( !"".equals(query.toString()) && query.toString() != null ) {
				searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
				searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
			}

			String postParamData = dcUtil.getParamPostDataRest(restvo, searchVO);
			
			
			logger.debug("QUERY pf : " + query.toString());
			logger.debug("RESTURL pf : " + postParamData);
			
			logger.debug("=================================================");
			logger.debug("resturl :: "+searchVO.getUrl()+"?"+postParamData);
			logger.debug("=================================================");
			
			restResultVO = new RestResultVO();
			success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restResultVO, searchVO.getFields());
			logger.debug("[searchList] success :: " + success);
			
			
			
			if ( query.length() > 0 ) query.charAt(0);
			if ( sbLog.length() > 0 ) sbLog.charAt(0);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!success) 
			return null;
				
		return restResultVO;		
	}

	public RestResultVO searchRetrieve(RestVO restvo, String fields) {
		// TODO Auto-generated method stub
		SearchVO searchVO = new SearchVO();		
		
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		//String strNmFd = restvo.getFields().isEmpty()? "text_idx": restvo.getFields();
		String partFd = "PART2BODY";
		
		String modName = "kwd";
		String symenticField = "";
		
		StringBuffer ksmSb = new StringBuffer();
		String[] pfKwdArr = null;
		List<String> nerList = new ArrayList<>();
		String nerTemp = "";
		
		String nerBoilerSymNm = konanPropertiesService.getString("nerBoilerName");
		String nerBoilerSymFileNm = konanPropertiesService.getString("nerBoilerFileName");
		
		
		
		//query = dcUtil.makeQuery(strNmFd, restvo.getPfKwd(), "allword", query, "AND");

		RestResultVO restResultVO = null;
		boolean success = false;
		try {
			if(restvo.getPartid()!=null ){
					
				query = dcUtil.makeQuery( "MD5_KEY" , restvo.getPartid(), "", query, "AND");
				
			}	
				
			
			
			logger.debug("query : " + query);

/*
			sbLog.append(  dcUtil.getLogInfo(commonUtil.null2Str(restvo.getSiteNm(),"SITE"),
					"Problem focus" ,
					commonUtil.null2Str( restvo.getUserId(),""), restvo.getPfKwd(),
					restvo.getPageNum(),
					false,
					restvo.getSort(),
					commonUtil.null2Str( restvo.getRecKwd(),"" )) );
*/
			
			Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(restvo.getCate().toUpperCase(), "");
			logger.debug("cateInfoMap : " + cateInfoMap);
			
			
			
			searchVO.setUrl(konanPropertiesService.getString("url"));
			searchVO.setCharset(konanPropertiesService.getString("charset"));		
			searchVO.setFields(fields);
			searchVO.setFrom(cateInfoMap.get("from"));
			//searchVO.setHilightTxt(cateInfoMap.get("hilight"));
			
			if ( !"".equals(query.toString()) && query.toString() != null ) {
				searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
				searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
			}

			String postParamData = dcUtil.getParamPostDataRest(restvo, searchVO);
			
			
			logger.debug("QUERY pf : " + query.toString());
			logger.debug("RESTURL pf : " + postParamData);
			
			logger.debug("=================================================");
			logger.debug("resturl :: "+searchVO.getUrl()+"?"+postParamData);
			logger.debug("=================================================");
			
			restResultVO = new RestResultVO();
			success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restResultVO, searchVO.getFields());
			logger.debug("[searchList] success :: " + success);
			
			
			
			if ( query.length() > 0 ) query.charAt(0);
			if ( sbLog.length() > 0 ) sbLog.charAt(0);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!success) 
			return null;
				
		return restResultVO;
	}
	
	// 맞춤형 지식 추천
	public List<Map<String, String>> recommendList(RestVO restvo) throws Exception {
		// TODO Auto-generated method stub		
		
		int limit = restvo.getLimit() > 0 ? restvo.getLimit() : 10;
		
		StringBuffer smUrl = new StringBuffer();
		smUrl.append(konanPropertiesService.getString("smUrl"));
		smUrl.append("/ksm/reccf/recommend");
		smUrl.append("?type=user_based");
		smUrl.append("&volume=kepri");
		smUrl.append("&user=" + restvo.getUser());
		smUrl.append("&limit=" + limit);
		
		logger.debug("[call RestAPI] smUrl : " + smUrl.toString());
		
		StringBuffer sb = dcUtil.getUrlData(smUrl.toString(), konanPropertiesService.getString("charset"));
		logger.debug("[call RestAPI] smUrl result :: " + sb.toString());
		
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
	
	/**
	 * 최신문서 List를 가져오는 로직
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRecentReportList(RestVO rest) throws Exception {
		return sqlSession.selectList("RestMapper.selectRestRecentReportList", rest);
	}
	
	/**
	 * 최신문서 List의 Total Count를 가져오는 로직
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getRecentReportListTotalCount(RestVO rest) throws Exception {
		return sqlSession.selectOne("RestMapper.selectRestRecentReportTotal", rest);
	}
	
	public List<Map<String, String>> getPopularKnowledge(String cate) {
		
		// cate 정보로 domain 번호를 받아온다.
		String domain = RankingDomain.valueOf(cate.toUpperCase()).getName();
		
		StringBuffer ksfUrl = new StringBuffer();
		ksfUrl.append(konanPropertiesService.getString("ksfUrl"));
		ksfUrl.append("rankings");
		ksfUrl.append("?domain_no=" + domain);	// 1: all, 2: boiler(보일러), 3: gt_turbine(가스터빈), 4: st_turbine(증기터빈), 5: gen_prev(예방진단), 6: gen_ins(절연진단), 7: kepri(누설흡습), 8: perform(성능) 
		ksfUrl.append("&max_count=10");				 
		
		logger.debug("[call RestAPI] rankings url : " + ksfUrl.toString());
		String charset = konanPropertiesService.getString("charset");  
		StringBuffer sb = dcUtil.getUrlData(ksfUrl.toString(), charset);

		// 결과 파싱
		try{
			
          JSONParser parser = new JSONParser();
          Object obj = parser.parse(sb.toString());
          
          JSONArray arr = (JSONArray) obj;

			List<Map<String, String>> list = new ArrayList<> ();
			Map<String, String> map;
			JSONArray ppk;
			for( Object o : arr){
				map = new HashMap<>();				
				ppk =(JSONArray) o;
				
				// 인기지식의 경우 ppk값에 파일명, key, 카테고리 정보가 들어있으므로 파싱을 따로 해줘야 된다.
				if ( ppk.get(0).toString().indexOf("#@@#") > -1 ) {
					String[] ppkArr = ppk.get(0).toString().split("#@@#");
					String ppkStr = "";
					String md5_key = "";
					String category = "";
					
					if ( ppkArr.length == 3 ) {
						ppkStr = ppkArr[0];
						md5_key = ppkArr[1];
						category = ppkArr[2];
						
						map.put("REPORT_NM", ppkStr);
						map.put("KEY", md5_key);
						map.put("CATE", category);
					} else {
						logger.debug("[call RestAPI] ppk split length not 3.");
					}
				} else {
					map.put("REPORT_NM", ppk.get(0).toString());
				}
				map.put("META", ppk.get(1).toString());
				
				list.add(map);
				map = null;
			}
			
			return list;

		} catch (ParseException e){
			logger.error("[call RestAPI] konan search engine search error...", e);
			return null;
		}
		
	}
	
	public String extractFileBody(String filePath, String fileName) throws Exception{
		
		long handle = 0;
		String saddr = konanPropertiesService.getString("fltUrl"); //35698
		
		String in_json = "";
		String out_json = "";
		String fpath = filePath + File.separator + fileName;
		
		// JSON 문자열을 생성하는데 사용할 KJSON 클래스의 객체를 생성하고 초기화 합니다.
		// KJSON 클래스를 사용하지 않고 JSON 문자열을 직접 만들어도 상관 없습니다.
		KJSON js = new KJSON();
		KJSON js0;
		KJSON pJs;
		JValue value = new JValue();

		// 다음과 같은 JSON 문자열을 만듭니다.
		// {"param1":300, "param2":400}
		js.initAsObject();
		value.type = JSONConst.json_type_int;
		value.v_int = 300;
		js.addObject("param1", value);
		value.v_int = 400;
		pJs = js.addObject("param2", value);
		
		String repl_word = "\\\\";
		in_json = "{\"function\":\"filter\",\"type\":\"AUTO\",\"mode\":\"F2B\",\"source\":\""+fpath.replaceAll(repl_word,"/")+"\"}";
		// JSON 문자열을 KMPD에 전달하고, 반환 값을 받아옵니다.
		KMP kmp = new KMP();
		handle = kmp.CreateHandle();
		if(handle < 0) {
			System.err.println("cannot create kmp handle.");
		}

		out_json = kmp.CallModuleAPI(handle, saddr, in_json);
		if(out_json == null) {
			logger.error("CallModuleAPI failed: "+kmp.GetErrorMessage(handle));
		}
		
		js.parse(out_json);
		
		js0 = js.getObjectByName("content");
		js0.getValue(value);
		
		return value.v_string;
	}
	
	public List<String> sentence(String text) throws Exception {
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		StringBuffer postData = new StringBuffer();
		
		text = text.replaceAll("\\(", "\\\\(");
		text = text.replaceAll("\\)", "\\\\)");
		text = text.replaceAll("\\\"", "\\\\\"");
		
		// 검색엔진 Update문
		postData.append("#include \"../script/test/test.k\"");
		postData.append("void main()");
		postData.append("{");
		postData.append("extract_sentence(\"" + text + "\");");
		postData.append("}");
		
		StringBuffer sb = restModule.restPost(kqlUrl, postData.toString()  , "");
		
		String temp = sb.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("'", "").replaceAll("\\\\", "");
		
		List<String> list = new ArrayList<String>();
		
		if ( temp.indexOf(",") > -1) {
			String[] tempArr = temp.split(",");
			
			for ( int i=0; i<tempArr.length; i++ ) {
				list.add(tempArr[i]);
			}
		} else {
			list.add(temp);
		}
		
		return list;
	}
	
	public String posTag(String text) throws Exception {
		
		Map<String, Object> posTag = restModule.restSearchKsmPosTag(text);
		if ( posTag.containsKey("resultTag") ) {
			return posTag.get("resultTag").toString();
		}
		
		return null;
	}
	
	public String parse(String text) throws Exception {
		
		Map<String, String> posTag = restModule.restSearchKsmSyntax(text);
		if ( posTag.containsKey("sfxPlain") ) {
			return posTag.get("sfxPlain").toString();
		}
		
		return null;
	}
	
	public Map<String, Object> symantic(String text, String lconf) throws Exception {
		return restModule.restSearchKsmExplain3(text, lconf);
	}
	
	/**
	 * 문장에서 Ner을 추출한다
	 * @param fileName
	 * @param sentence
	 * @param classfication
	 * @return
	 * @throws Exception
	 */
	public List<String> ner(String sentence, String classfication) throws Exception {
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		List<String> nerResult = new ArrayList<String>();
		StringBuffer postData = new StringBuffer();
		
		sentence = sentence.replaceAll("\\(", "\\\\(");
		sentence = sentence.replaceAll("\\)", "\\\\)");
		sentence = sentence.replaceAll("\\\"", "\\\\\"");
		
		// Ner 추출
		postData.append("void main()");
		postData.append("{");
		// 5.0.0버전부터는 권한체크를 하기 때문에 아래와 같이 kql을 날릴때 권한 설정을 해주어야 한다.
		postData.append("setenv('privilege', 'admin');");
		postData.append("run ../script/test/get_ner.k main('','" + sentence + "','" + classfication + "');");
		postData.append("printf('%s', KQL_STATEMENT_OUT);");
		postData.append("}");
		
		StringBuffer sb = restModule.restPost(kqlUrl, postData.toString()  , "");
		logger.debug("[getNer] get Ner :: " + sb.toString());
		
		if ( !"error".equals(sb.toString()) ) {
			if ( sb.toString().indexOf("##@@##") > -1 ) {
				for ( String ner : sb.toString().split("##@@##") ) {
					nerResult.add(ner);
				}
			} else {
				nerResult.add(sb.toString());
			}
		}
		
		return nerResult;
	}
}
