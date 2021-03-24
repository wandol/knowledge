package egovframework.kf.kepri.dao;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.DCUtil;
import egovframework.kf.dao.RestModule;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.data.SearchVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : KepriDAO.java
 * Description : 검색대상 Kepri 조회
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 * 2019년 02월  19일                       최초 작성
 * 2020년 08월  31일       이창호           수정
 *
 * @since 2019년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Repository
public class KepriDAO {
	private static final Logger logger = LoggerFactory.getLogger(KepriDAO.class);
	
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
	 * 키워드에 맞는 문서관리 내용 리턴
	 * 
	 * @param kwd
	 * @throws IOException 
	 */
	public RestResultVO search(ParameterVO paramVO) throws Exception {
		SearchVO searchVO = new SearchVO();		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		String searchFd = konanPropertiesService.getString("searchFd");		// text_idx
		String searchOpt = konanPropertiesService.getString("searchOpt");	// allword
		String strNmFd = paramVO.getFields().isEmpty()? searchFd : paramVO.getFields();
		
		// 카테고리 정보를 가져온다.
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), "");

		// 쿼리 부분
		query = dcUtil.makeQuery( strNmFd , paramVO.getKwd(), searchOpt + " synonym('d0')", query, "AND");	
		
		// 발전사 select box
		if(!"all".equalsIgnoreCase(paramVO.getPowerComp())) {
			query = dcUtil.makeQuery( "POWER_COMP_NM" , paramVO.getPowerComp(), "", query, "AND");
		}
		
		// 사업소 select box
		if(!"all".equalsIgnoreCase(paramVO.getPowerSt())) {
			query = dcUtil.makeQuery( "POWER_ST_NM" , paramVO.getPowerSt(), "", query, "AND");
		}
		
		// 호기 select box
		if(!"all".equalsIgnoreCase(paramVO.getStNo())) {
			query = dcUtil.makeQuery( "ST_NO" , paramVO.getStNo(), "", query, "AND");
		}

		/**
		 * 날짜검색기간 조회
		 * 기간/일/월/년도, 구간검색으로 조회시 자바스크립트에서 시작날짜와 종료날짜 조회하여 전달함.
		 */
		if (!paramVO.getStartPublishYm().isEmpty() && !paramVO.getEndPublishYm().isEmpty()){
			query = dcUtil.makeRangeQuery("PUBLISH_YM", paramVO.getStartPublishYm(), paramVO.getEndPublishYm(), query) ;
		}
		
		// 최신순 정렬(Default)
		query.append(" ");
		query.append("ORDER BY PUBLISH_YM DESC");
		
		//로그기록 
		//SITE@인물+$|첫검색|1|정확도순^코난	
		sbLog.append(  dcUtil.getLogInfo(commonUtil.null2Str(paramVO.getSiteNm(),"SITE"),
				"발전기-누설흡습" ,
				commonUtil.null2Str( paramVO.getUserId(),""), paramVO.getKwd(),
				paramVO.getPageNum(),
				false,
				paramVO.getSort(),
				commonUtil.null2Str( paramVO.getRecKwd(),"" )) );
	
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(cateInfoMap.get("field"));
		searchVO.setFrom(cateInfoMap.get("from"));
		// 2019.11.07 changho.lee 키워드 없이 검색 되도록 수정
		if ( !"".equals(query.toString()) && query.toString() != null ) {
			searchVO.setHilightTxt(cateInfoMap.get("hilight"));
			searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
			searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
		}
		
		// URL 생성
		String postParamData = dcUtil.getParamPostDataSearch(paramVO, searchVO);
		logger.debug("[search] QUERY : " + query.toString());
		logger.debug("[search] RESTURL : " + postParamData);
		logger.debug("[search] LOG : " + sbLog.toString());
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields()); //post 방식 호출
		logger.debug("[search] search API Call success :: " + success);
		
		// 2019.11.07 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		if ( sbLog.length() > 0 ) sbLog.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;		
	}

	public RestResultVO getDetailData(ParameterVO paramVO)  throws Exception {
		
		SearchVO searchVO = new SearchVO();		
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), "");
		
		// 쿼리 부분
		query = dcUtil.makeQuery( "MD5_KEY" , paramVO.getKwd(), "", query, "AND");
		
		// 인기지식용 log
		sbLog.append(  dcUtil.getLogInfo("ranking",
				"발전기-누설흡습" ,
				commonUtil.null2Str( paramVO.getUserId(),""), paramVO.getRepoName() + "#@@#" + paramVO.getKwd() + "#@@#" + "KEPRI",
				paramVO.getPageNum(),
				false,
				paramVO.getSort(),
				commonUtil.null2Str( paramVO.getRecKwd(),"" )) );
				
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(cateInfoMap.get("field"));
		searchVO.setFrom(cateInfoMap.get("from"));
		searchVO.setHilightTxt(cateInfoMap.get("hilight"));
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
		
		paramVO.setPageSize(1);
		
		// URL 생성
		String postParamData = dcUtil.getParamPostData(paramVO, searchVO);
		logger.debug("[getDetailData] QUERY GetDetail : " + query.toString());
		logger.debug("[getDetailData] RESTURL GetDetail : " + postParamData);
		logger.debug("[getDetailData] LOG : " + sbLog.toString());
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields()); //post 방식 호출
		
		// 2019.11.26 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}
	
	/**
	 * 기술문서 탐색 > 분석내용 영역의 데이터 불러오기
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public RestResultVO getDetailPartData(ParameterVO paramVO)  throws Exception {
		
		SearchVO searchVO = new SearchVO();		
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), "");
		
		// 쿼리 부분
		query = dcUtil.makeQuery( "MD5_KEY" , paramVO.getKwd(), "", query, "AND");
				
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(cateInfoMap.get("pFfield"));
		searchVO.setFrom(cateInfoMap.get("from"));
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		
		paramVO.setPageSize(100);
		
		// URL 생성
		String postParamData = dcUtil.getParamPostData(paramVO, searchVO);
		logger.debug("[getDetailPartData] QUERY GetDetail : " + query.toString());
		logger.debug("[getDetailPartData] RESTURL GetDetail : " + postParamData);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields()); //post 방식 호출
		
		// 2019.11.26 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}
	
	/**
	 * 날짜 포맷 변경
	 * @param category
	 * @param restVO
	 * @return
	 * @throws Exception
	 */
	public RestResultVO changeDateFormat(RestResultVO restVO) throws Exception {
		
		for ( Map<String, String> result : restVO.getResult() ) {
			
			////////////////////////////////////////////////////////////////////////////////////////////날짜 포맷 변경
			String date = commonUtil.changeDateFormat(result.get("PUBLISH_YM"));
			result.put("PUBLISH_YM", date);
		}
		
		return restVO;
	}
	
	/**
	 * 기술문서 탐색 > 상세페이지 > 연관현상
	 * 연관현상 데이터를 조회하기 위해 post테이블에서 Meta 정보를 가져온다.(필요한 값 : 발전사,사업소,호기)
	 * @param category
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public RestResultVO getMeta(String category, String idx) throws Exception {
		
		SearchVO searchVO = new SearchVO();		
		StringBuffer query = new StringBuffer();
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(category, "");

		// 쿼리 부분
		query = dcUtil.makeQuery( "MD5_KEY" , idx, "", query, "AND");
		
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(cateInfoMap.get("field"));
		searchVO.setFrom(cateInfoMap.get("from"));
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		
		// URL 생성
		String restUrl = dcUtil.getNoHilightURL(searchVO); //get방식 URL생성
		logger.debug("[getMeta] restUrl : " + restUrl);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearch(restUrl, restVO, searchVO.getFields()); //get방식 호출	
		
		// 2019.11.26 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}
	
	/**
	 * 기술문서 탐색 > 상세페이지 > 연관현상
	 * getMeta에서 조회 한 메타정보(발전사,사업소,호기)를 이용하여 현상키워드(SYMPTOM_KWD)를 Group by 한다.
	 * @param category
	 * @param powerCompNm
	 * @param powerStNm
	 * @param stNo
	 * @return
	 * @throws Exception
	 */
	public RestResultVO getSymptom(String category, String powerCompNm, String powerStNm, String stNo) throws Exception {
		
		SearchVO searchVO = new SearchVO();		
		StringBuffer query = new StringBuffer();
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(category, "");

		// 쿼리 부분
		// 발전사
		if ( !StringUtils.isEmpty(powerCompNm) ) {
			query = dcUtil.makeQuery( "POWER_COMP_NM" , powerCompNm, "", query, "AND");
		}
		
		// 사업소
		if ( !StringUtils.isEmpty(powerStNm) ) {
			query = dcUtil.makeQuery( "POWER_ST_NM" , powerStNm, "", query, "AND");
		}
		
		// 호기
		if ( !StringUtils.isEmpty(stNo) ) {
			query = dcUtil.makeQuery( "ST_NO" , stNo, "", query, "AND");
		}
		
		query.append(" GROUP BY SYMPTOM_KWD");
		
		
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields("*");
		searchVO.setFrom(cateInfoMap.get("pFfrom"));
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		
		// URL 생성
		String restUrl = dcUtil.getParamGroupByData(searchVO, 30); //get방식 URL생성
		logger.debug("[getSymptom] restUrl : " + restUrl);
		logger.debug("[getSymptom] QUERY GetDetail : " + query.toString());
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearch(restUrl, restVO, "SYMPTOM_KWD,count(*)"); //get방식 호출	
		
		// 2019.11.26 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}
}
