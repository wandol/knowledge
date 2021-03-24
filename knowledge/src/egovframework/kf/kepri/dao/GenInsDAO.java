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
 * Class Name : GenInsvDAO.java
 * Description : 검색대상 Gen Ins 조회
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
public class GenInsDAO {
	private static final Logger logger = LoggerFactory.getLogger(GenInsDAO.class);
	
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
				"발전기-절연진단" ,
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
				"발전기-절연진단" ,
				commonUtil.null2Str( paramVO.getUserId(),""), paramVO.getRepoName() + "#@@#" + paramVO.getKwd() + "#@@#" + "GEN_INS",
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
		searchVO.setFrom(cateInfoMap.get("pFfrom"));
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
	 * 기술문서 탐색 > 데이터에 추출 된 이미지가 몇개씩 있는지 조회
	 * 기술문서 탐색 페이지는 기본 post테이블을 조회하지만 이미지의 경우 part테이블을 조회한다.
	 * @param category
	 * @param restVO
	 * @return
	 * @throws Exception
	 */
	public RestResultVO getExtractImgList(String category, RestResultVO restVO) throws Exception {
		
		SearchVO searchVO = new SearchVO();		
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(category, "");
		
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(konanPropertiesService.getString("imgFieldGenIns"));	// Img만 추출하기 위한 MD5_KEY,IMG_FILES
		searchVO.setFrom(cateInfoMap.get("pFfrom"));
		
		for ( Map<String, String> result : restVO.getResult() ) {
			StringBuffer query = new StringBuffer(); // 쿼리 생성
			
			query.append("MD5_KEY='" + result.get("MD5_KEY") + "'");
			searchVO.setQuery( URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset")) );
			
			// URL 생성
			String restUrl = dcUtil.getNoHilightURL(searchVO); //get방식 URL생성
			logger.debug("[getExtractImgList] restUrl : " + restUrl);
			
			RestResultVO restImgVO = new RestResultVO();
			boolean success = restModule.restSearch(restUrl, restImgVO, searchVO.getFields()); //get방식 호출

			int imgCount = 0;
					
			// Part Table에서 조회한 Img List가 있을 경우
			if ( restImgVO.getTotal() > 0 ) {
				String img = "";
				String finalImg = "";
				
				// 조회된 List의 Img 목록을 하나의 문자열로 만든다.
				for (Map<String, String> resultImg : restImgVO.getResult() ) {
					if ( resultImg.get("PATTERN_IMG") != null && !"".equals(resultImg.get("PATTERN_IMG")) ) {						
						img = img + resultImg.get("PATTERN_IMG") + "|";
					}
				}
				
				// IMG_FILES에 구분자 | 또는 `가 있을 경우에만
				if ( !"".equals(img) ) {
					if ( img.indexOf("|") > -1 || img.indexOf("`") > -1 ) {
						imgCount = img.split("\\||\\`").length;
					} else {	// 이미지가 하나인 경우
						imgCount = 1;
					}
				}
				
				// 기존 IMG_FILES를 새로 조회한 IMG_FILES로 교체한다.
				result.put("IMG_FILES", img);
				result.put("IMG_COUNT", String.valueOf(imgCount));	// 이미지 갯수를 map에 담는다.
			} else {
				result.put("IMG_FILES", "");
				result.put("IMG_COUNT", String.valueOf(imgCount));	// 이미지 갯수를 map에 담는다.
			}
			
			////////////////////////////////////////////////////////////////////////////////////////////날짜 포맷 변경
			String date = commonUtil.changeDateFormat(result.get("PUBLISH_YM"));
			result.put("PUBLISH_YM", date);
			
			// query, sbLog가 비어있을 때 에러 방지
			if ( query.length() > 0 ) query.charAt(0);
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
