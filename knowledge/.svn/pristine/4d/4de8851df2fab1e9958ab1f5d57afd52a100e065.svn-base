package egovframework.kf.common.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import egovframework.kf.common.vo.RecentVO;
import egovframework.kf.common.vo.UploadVO;
import egovframework.kf.dao.RestModule;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.data.SearchVO;
import egovframework.rte.fdl.property.EgovPropertyService;

@Repository("commonDAO")
public class CommonDAO{	
	private static final Logger logger = LoggerFactory.getLogger(CommonDAO.class);
	
	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** 엔진 공통 유틸 */
	@Resource(name = "dcUtil")
	private DCUtil dcUtil;
	
	/** REST 모듈 */
	@Resource(name = "restModule")
	private RestModule restModule;
		
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	private EgovPropertyService konanPropertiesService;
	
	/**
	 *  Name : selectPowerStList
	 *  Comment : 발전사정보 List
	 */
	public List<Map<String, String>> selectPowerCompList(Map map) throws Exception {
		logger.debug("[selectPowerCompList] selectPowerCompList START");
		return sqlSession.selectList("CommonMapper.selectPowerCompList", map);
	}
	
	/**
	 *  Name : selectPowerStListEngine
	 *  Comment : 발전사정보 List
	 */
	public RestResultVO selectPowerCompListEngine(ParameterVO paramVO) throws Exception {
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		SearchVO searchVO = new SearchVO();		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		String field = "POWER_COMP_NM,count(*)";
		
		cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), paramVO.getRepoKind());
		
		// 쿼리 부분
		query.append("GROUP BY POWER_COMP_NM ORDER BY POWER_COMP_NM");
				
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		
		searchVO.setFields("*");
		
		if ( paramVO.getMenu() != null && "Y".equals(paramVO.getMenu()) ) {
			searchVO.setFrom(cateInfoMap.get("pFfrom"));
		}
		else searchVO.setFrom(cateInfoMap.get("from"));
		
		searchVO.setHilightTxt("");
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
		
		// URL 생성
		String restUrl = dcUtil.getRestURL(paramVO, searchVO); //get방식 URL생성
		logger.debug("QUERY GetDetail : " + restUrl);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearch(restUrl, restVO, field);  //get방식 호출
		
		// 2019.11.26 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}
	
	public List<Map<String, String>> selectListPerform(Map map) throws Exception {
		logger.debug("[selectPowerCompList] selectPowerCompList START");
		return sqlSession.selectList("CommonMapper.selectPerformDataGroupBy", map);
	}
	
	/**
	 *  Name : selectPowerStList
	 *  Comment : 사업소정보 List
	 */
	public List<Map<String, String>> selectPowerStList(Map map) throws Exception {
		logger.debug("[selectPowerStList] selectPowerStList START");
		return sqlSession.selectList("CommonMapper.selectPowerStList", map);
	}
	
	/**
	 *  Name : selectPowerStList
	 *  Comment : 사업소정보 List
	 */
	public RestResultVO selectPowerStListEngine(ParameterVO paramVO) throws Exception {
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		SearchVO searchVO = new SearchVO();		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		String field = "POWER_ST_NM,count(*)";
		
		cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), paramVO.getRepoKind());
		
		// 쿼리 부분
		if(!"".equals(paramVO.getPowerComp())) {
			query.append("POWER_COMP_NM = '" + paramVO.getPowerComp() + "' ");
		}
		query.append("GROUP BY POWER_ST_NM ORDER BY POWER_ST_NM");
				
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		
		searchVO.setFields("*");
		if ( paramVO.getMenu() != null && "Y".equals(paramVO.getMenu()) ) searchVO.setFrom(cateInfoMap.get("pFfrom"));
		else searchVO.setFrom(cateInfoMap.get("from"));
		
		searchVO.setHilightTxt("");
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
		
		// URL 생성
		String restUrl = dcUtil.getRestURL(paramVO, searchVO); //get방식 URL생성
		logger.debug("QUERY GetDetail : " + restUrl);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearch(restUrl, restVO, field);  //get방식 호출
		
		// 2019.11.26 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}
	
	/**
	 *  Name : selectStNoList
	 *  Comment : 호기정보 List
	 */
	public List<Map<String, String>> selectStNoList(Map map) throws Exception {
		logger.debug("[selectStNoList] selectStNoList START");
		return sqlSession.selectList("CommonMapper.selectStNoList", map);
	}
	
	/**
	 *  Name : selectPowerStList
	 *  Comment : 사업소정보 List
	 */
	public RestResultVO selectStNoListEngine(ParameterVO paramVO) throws Exception {
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		SearchVO searchVO = new SearchVO();		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		String field = "ST_NO,count(*)";
		
		cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), paramVO.getRepoKind());
		
		// 쿼리 부분
		query.append("POWER_COMP_NM = '" + paramVO.getPowerComp() + "' ");
		query.append("AND POWER_ST_NM = '" + paramVO.getPowerSt() + "' ");
		query.append("GROUP BY ST_NO ORDER BY ST_NO");
				
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		
		searchVO.setFields("*");
		if ( paramVO.getMenu() != null && "Y".equals(paramVO.getMenu()) ) searchVO.setFrom(cateInfoMap.get("pFfrom"));
		else searchVO.setFrom(cateInfoMap.get("from"));
		
		searchVO.setHilightTxt("");
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
		
		// URL 생성
		String restUrl = dcUtil.getRestURL(paramVO, searchVO); //get방식 URL생성
		logger.debug("QUERY GetDetail : " + restUrl);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearch(restUrl, restVO, field);  //get방식 호출
		
		// 2019.11.26 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}
	
	/**
	 *  Name : selectPowerStList
	 *  Comment : 사업소정보 List
	 */
	public RestResultVO selectListEngine(ParameterVO paramVO, String field) throws Exception {
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		SearchVO searchVO = new SearchVO();		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		
		cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), paramVO.getRepoKind());
		
//		logger.debug("[selectPartNameListEngine] getPowerComp :: " + paramVO.getPowerComp());
//		logger.debug("[selectPartNameListEngine] getPowerSt :: " + paramVO.getPowerSt());
//		logger.debug("[selectPartNameListEngine] getStNo :: " + paramVO.getStNo());
//		logger.debug("[selectPartNameListEngine] getPartName :: " + paramVO.getPartName());
//		logger.debug("[selectPartNameListEngine] getPartMid :: " + paramVO.getPartMid());
//		logger.debug("[selectPartNameListEngine] getSymptomKwd :: " + paramVO.getSymptomKwd());
//		logger.debug("[selectPartNameListEngine] getActionKwd :: " + paramVO.getActionKwd());
		
		// 쿼리 부분
		if ( paramVO.getPowerComp() != null && !"all".equals(paramVO.getPowerComp()) ) query.append("POWER_COMP_NM = '" + paramVO.getPowerComp() + "' ");
		if ( paramVO.getPowerSt() != null && !"all".equals(paramVO.getPowerSt()) ) {
			if ( !query.toString().isEmpty() ) query.append("AND ");
			query.append("POWER_ST_NM = '" + paramVO.getPowerSt() + "' ");
		}
		if ( paramVO.getStNo() != null && !"all".equals(paramVO.getStNo()) ) {
			if ( !query.toString().isEmpty() ) query.append("AND ");
			query.append("ST_NO = '" + paramVO.getStNo() + "' ");
		}		
		if ( paramVO.getPartName() != null && !"all".equals(paramVO.getPartName()) ) {
			if ( !query.toString().isEmpty() ) query.append("AND ");
			query.append(cateInfoMap.get("partName") + " = '" + paramVO.getPartName() + "' ");
		}
		if ( paramVO.getPartMid() != null && !"all".equals(paramVO.getPartMid()) ) {
			if ( !query.toString().isEmpty() ) query.append("AND ");
			query.append(cateInfoMap.get("partMid") + " = '" + paramVO.getPartMid() + "' ");
		}		
		if ( paramVO.getSymptomKwd() != null && !"all".equals(paramVO.getSymptomKwd()) ) {
			if ( !query.toString().isEmpty() ) query.append("AND ");
			query.append("SYMPTOM_KWD = '" + paramVO.getSymptomKwd() + "' ");
		}
		if ( paramVO.getActionKwd() != null && !"all".equals(paramVO.getActionKwd()) ) {
			if ( !query.toString().isEmpty() ) query.append("AND ");
			query.append("ACTION_KWD = '" + paramVO.getActionKwd() + "' ");
		}
		
		query.append("GROUP BY " + field.split(",")[0] + " ORDER BY " + field.split(",")[1] + " DESC");
		logger.debug("[selectPartNameListEngine] query :: " + query.toString());
				
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		
		searchVO.setFields("*");
		searchVO.setFrom(cateInfoMap.get("pFfrom"));
		
		searchVO.setHilightTxt("");
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
		
		paramVO.setPageSize(1000);
		
		// URL 생성
		String restUrl = dcUtil.getRestURL(paramVO, searchVO); //get방식 URL생성
		logger.debug("QUERY GetDetail : " + restUrl);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearch(restUrl, restVO, field);  //get방식 호출
		
		// 2019.11.26 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}
	
	/**
	 *  Name : getClassfication
	 *  Comment : 파일경로와 파일명을 받아 필터링 모듈 후 자동분류값 추출
	 */
	public String getClassfication(String filePath, String fileName) throws Exception {
		// TODO Auto-generated method stub
		
		logger.debug("[getClassfication] classfication start");
		
		long handle = 0;
		String saddr = konanPropertiesService.getString("fltUrl");
		
		String in_json = "";
		String out_json = "";
		
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
		
//		in_json = js.toString();
		String fpath = filePath + fileName;
		String repl_word = "\\\\";
		in_json = "{\"function\":\"filter\",\"type\":\"AUTO\",\"mode\":\"F2B\",\"source\":\""+fpath.replaceAll(repl_word,"/")+"\"}";
		// JSON 문자열을 KMPD에 전달하고, 반환 값을 받아옵니다.
		KMP kmp = new KMP();
		handle = kmp.CreateHandle();
		if(handle < 0) {
			System.err.println("cannot create kmp handle.");
			System.exit(-1);
		}

		out_json = kmp.CallModuleAPI(handle, saddr, in_json);
		if(out_json == null) {
			logger.debug("CallModuleAPI failed: "+kmp.GetErrorMessage(handle));
			System.exit(-1);
		}
		js.parse(out_json);
		
		js0 = js.getObjectByName("content");
		js0.getValue(value);
		
		// 추출된 파일내용
		String data = value.v_string;
		
		return restModule.restClassfication(data);
	}
	
	/**
	 *  Name : getClassfication
	 *  Comment : 파일경로와 파일명을 받아 필터링 모듈 후 자동분류값 추출
	 */
	public Map<String, Object> getClassficationNFileBody(String filePath, String fileName) throws Exception {
		// TODO Auto-generated method stub
		
		logger.debug("[getClassficationNFileBody] File Filtering start");
		
		long handle = 0;
		String saddr = konanPropertiesService.getString("fltUrl");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String in_json = "";
		String out_json = "";
		
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
		
//		in_json = js.toString();
		String fpath = filePath + fileName;
		String repl_word = "\\\\";
		in_json = "{\"function\":\"filter\",\"type\":\"AUTO\",\"mode\":\"F2B\",\"source\":\""+fpath.replaceAll(repl_word,"/")+"\"}";
		// JSON 문자열을 KMPD에 전달하고, 반환 값을 받아옵니다.
		KMP kmp = new KMP();
		handle = kmp.CreateHandle();
		if(handle < 0) {
			System.err.println("cannot create kmp handle.");
//			System.exit(-1);
		}

		out_json = kmp.CallModuleAPI(handle, saddr, in_json);
		if(out_json == null) {
			resultMap.put("message", "Check Text Filter. Is Alive?");
			logger.debug("CallModuleAPI failed: "+kmp.GetErrorMessage(handle));
//			System.exit(-1);
		} else {
			js.parse(out_json);
			
			js0 = js.getObjectByName("content");
			js0.getValue(value);
			
			// 추출된 파일내용
			String data = value.v_string;
			logger.debug("[getClassficationNFileBody] data :: " + data);
			if ( !"".equals(data) && data != null ) {
				String classfication = restModule.restClassfication(data);	
				if ( "fail".equals(classfication)) {
					resultMap.put("message", "Check Nbc Module. Is Learn?");
				} else {
					resultMap.put("classfication", classfication);				
				}
			}
			logger.debug("[getClassficationNFileBody] 222 :: ");
			
			resultMap.put("fileBody", data);
		}
		
		
		return resultMap;
	}
	
	public List<RecentVO> getRecentByUser(int user_id) throws Exception {
		return sqlSession.selectList("CommonMapper.selectRecentHistoryByUser", user_id);
	}
	/**
	 * Description : 합쳐진 이미지가 있는지 체크하고 있으면 해당 이미지 정보를 return
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> selectMergeImgCheck(Map map) throws Exception {
		return sqlSession.selectOne("CommonMapper.selectMergeImgCheck", map);
	}
	
	public int insertMergeImg(Map map) throws Exception {
		return sqlSession.insert("CommonMapper.insertMergeImg", map);
	}
	
	public int updateMergeImg(Map map) throws Exception {
		return sqlSession.update("CommonMapper.updateMergeImg", map);
	}
	
	/** DB MD5 업데이트용 **/
	public List<Map> selectReportlist() throws Exception {
		return sqlSession.selectList("CommonMapper.selectReportInfo");
	}
	
	public int updateReportMd5(UploadVO upload) throws Exception {
		return sqlSession.update("CommonMapper.updateReportMD5", upload);
	}
	
	/** 파일 다운로드 **/
	public String selectDownloadPath(Map map) throws Exception {
		return sqlSession.selectOne("FileMapper.selectDownloadPath", map);
	}
	
	
	/**
	 * 
	 * @param map
	 * @param fields
	 * @return
	 */
	public RestResultVO searchVRPartList(Map<String, String> map, String fields) {
		SearchVO searchVO = new SearchVO();		
		StringBuffer query = new StringBuffer();
		RestResultVO restResultVO = null;
		boolean success = false;
		ParameterVO paramVO = new ParameterVO();
			paramVO.setOffSet(0);
			paramVO.setPageSize(1000);
		
		try {
			if(map.get("cate") == null || map.get("partname") == null)
				return null;
				
			query = dcUtil.makeQuery( "part1_cd" , map.get("cate"), "", query, "AND");
			query.append("AND partname in {" + map.get("partname") + "}");
			
			if(map.get("repflag") != null && "Y".equalsIgnoreCase( map.get("repflag"))) {
				query = dcUtil.makeQuery( "rep_flag" , "Y", "", query, "AND");
				paramVO.setPageSize(1);
			}
			
			
			logger.debug("query : " + query);

			
			Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(map.get("cate").toUpperCase(), "");
			logger.debug("cateInfoMap : " + cateInfoMap);
			
			
			
			searchVO.setUrl(konanPropertiesService.getString("url"));
			searchVO.setCharset(konanPropertiesService.getString("charset"));		
			searchVO.setFields(fields);
			searchVO.setFrom(konanPropertiesService.getString("vrfrom"));
			searchVO.setHilightTxt("");
			searchVO.setLogInfo("");
			
			if ( !"".equals(query.toString()) && query.toString() != null ) {
				searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
			}
			
			String postParamData = dcUtil.getParamPostDataTableSearch(paramVO, searchVO);
			
			
			logger.debug("QUERY pf : " + query.toString());
			logger.debug("RESTURL pf : " + postParamData);
			
			logger.debug("=================================================");
			logger.debug("resturl :: "+searchVO.getUrl()+"?"+postParamData);
			logger.debug("=================================================");
			
			restResultVO = new RestResultVO();
			success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restResultVO, searchVO.getFields());
			logger.debug("[searchVRPartList] success :: " + success);
			
			
			//System.out.println("restResultVO.getTotal() : " + restResultVO.getTotal());
			
			if ( query.length() > 0 ) query.charAt(0);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if(!success) 
			return null;
				
		return restResultVO;
	}
	
}