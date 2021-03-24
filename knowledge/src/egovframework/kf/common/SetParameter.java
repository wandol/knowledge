package egovframework.kf.common;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.kf.data.ParameterVO;
import egovframework.kf.user.vo.UserVO;
import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * Class Name : SetParameter.java
 * Description : 통합검색 조회를 파라미터 설정
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 * 2017년 12월  00일                       최초 작성
 *
 * @since 2017년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Component("setParameter")
public class SetParameter {
	
	/** Common Util */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;

		
	public ParameterVO setParameter(@RequestParam Map<String, String> map) throws Exception{
		ParameterVO rtnParam = new ParameterVO();
		
		// 사이트명
		rtnParam.setSiteNm(konanPropertiesService.getString("site"));
		
		// 키워드 (기본적으로 콤마는 삭제한다.)
		rtnParam.setKwd(getValue(map, "kwd", "", true).replaceAll("\\'", ""));
		
		// 하이라이트 키워드를 세팅한다.
		rtnParam.setHilightKwd(commonUtil.extractHighlightKwd(rtnParam.getKwd()));
		
		// 카테고리
		rtnParam.setCategory(getValue(map, "category", "TOTAL", false));
			
		// 페이지 번호
		rtnParam.setPageNum(getValue(map, "pageNum", 1));
		
		// 	통합검색 발전사
		rtnParam.setPowerComp(getValue(map, "powerComp", "all", false));
		
		// 	통합검색 사업소
		rtnParam.setPowerSt(getValue(map, "powerSt", "all", false));
		
		// 	통합검색 호기
		rtnParam.setStNo(getValue(map, "stNo", "all", false));
		
		//	통합검색 분야
		rtnParam.setPowerType(getValue(map, "powerType", "all", false));
		
		//	통합검색 보고서종류
		rtnParam.setRepoKind(getValue(map, "repoKind", "all", false));
			
		// problem focus 키워드 (기본적으로 콤마는 삭제한다.)
		rtnParam.setPfKwd(getValue(map, "pfKwd", "", true).replaceAll("\\'", ""));
		
		// problem focus 상세 검색 진단부위
		rtnParam.setPartName(getValue(map, "partName", "all", false));
				
		// problem focus 상세 검색 손상위치
		rtnParam.setPartMid(getValue(map, "partMid", "all", false));		
		
		// problem focus 상세 검색 손상현상
		rtnParam.setSymptomKwd(getValue(map, "symptomKwd", "all", false));
		
		// problem focus 상세 검색 손상대책
		rtnParam.setActionKwd(getValue(map, "actionKwd", "all", false));
		
		// problem focus 상세 검색 시행일시 시작일
		rtnParam.setStartPublishYm(getValue(map, "startPublishYm", "", false));
		
		// problem focus 상세 검색 시행일시 시작일		
		rtnParam.setEndPublishYm(getValue(map, "endPublishYm", "", false));
		
		// 가중치 설정		
		rtnParam.setWeight(getValue(map, "weight", "all", false));
		
		// 보고서명 설정
		rtnParam.setRepoName(getValue(map, "repoName", "", false));
		
		// 페이지 사이즈 세팅
		rtnParam.setPageSize(getValue(map, "pageSize", konanPropertiesService.getInt("pageSize")));
		
		// 검색대상
		rtnParam.setSrchFd(getValue(map, "srchFd", "ALL", false));
		
		// 정렬코드
		rtnParam.setSort(getValue(map, "sort", "r", false));
		
		// 정렬명
		if("r".equals(rtnParam.getSort())) {
			rtnParam.setSortNm("정확도순");
		} else {
			rtnParam.setSortNm("최신순");
		}
		
		// 상세검색 여부
		rtnParam.setDetailSearch(getValue(map, "detailSearch"));
		
		// 파일 확장자
		rtnParam.setFileExt(getValue(map, "fileExt", "", false));
		
		// 시작일
		rtnParam.setStartDate(getValue(map, "startDate", "", false));
		
		// 종료일
		rtnParam.setEndDate(getValue(map, "endDate", "", false));
		
		// 년도
		rtnParam.setYear(getValue(map, "year", "", false));
		
		// 작성자
		rtnParam.setWriter(getValue(map, "writer", "", false));
		
		// 제외어
		rtnParam.setExclusiveKwd(getValue(map, "notWord", "", false));
		
		// 오늘날짜
		rtnParam.setNowDate(getValue(map, "nowDate", commonUtil.getTargetDate(0), false));
		
		// 검색영역
		rtnParam.setFields(getValue(map, "fields", "", false));
		
		//	지식탐색 키워드 클릭 검색시 사용.
		rtnParam.setKwdList(getValue(map, "kwdList", "", false));
		
		// 이미지 여부
		rtnParam.setIsImg(getValue(map, "isImg", "N", false));
	
		// 재검색여부
		rtnParam.setReSrchFlag( "true".equals(getValue(map, "reSrchFlag", "", false))?true:false );
		
		if(rtnParam.getReSrchFlag() && map.get("preKwds")!=null && map.get("preKwds").length()>0) {
			
			String kwd = getValue(map, "kwd", "").replaceAll("\\'", "");
			String[] preKwds = map.get("preKwds").split("#@@#");
			String hilightKwd = "";
			boolean doubleChk = true;
			
			for (int i = 0; i < preKwds.length; i++) {	// 키워드 중복일때 
				if(preKwds[i].equals(kwd)){
					doubleChk = false;
				}
				
			}
			
			if(doubleChk) {
				rtnParam.setPreKwds(map.get("preKwds") + "#@@#" + map.get("kwd"));
				// 하이라이트 키워드를 세팅한다.
				if ( rtnParam.getPreKwds().indexOf("#@@#") > -1 ) {
					String[] hilightPreKwd =  rtnParam.getPreKwds().split("#@@#");
					for ( int j = 0; j < hilightPreKwd.length; j++ ) {
						hilightKwd += hilightPreKwd[j] + " ";
					}
					rtnParam.setHilightKwd(commonUtil.extractHighlightKwd(hilightKwd));
				}
			}
			else {
				rtnParam.setPreKwds(map.get("preKwds"));
				// 하이라이트 키워드를 세팅한다.
				if ( rtnParam.getPreKwds().indexOf("#@@#") > -1 ) {
					String[] hilightPreKwd =  rtnParam.getPreKwds().split("#@@#");
					for ( int j = 0; j < hilightPreKwd.length; j++ ) {
						hilightKwd += hilightPreKwd[j] + " ";
					}
					rtnParam.setHilightKwd(commonUtil.extractHighlightKwd(hilightKwd));
				} 
				else rtnParam.setHilightKwd(commonUtil.extractHighlightKwd(rtnParam.getPreKwds()));
			}
			
			
		}
		else {
			rtnParam.setPreKwds(map.get("kwd"));
			
			// 하이라이트 키워드를 세팅한다.
			rtnParam.setHilightKwd(commonUtil.extractHighlightKwd(rtnParam.getKwd()));
		}
		
		// 이전 쿼리
		rtnParam.setPreviousQuery(getValue(map, "previousQuery", "", false));
		
		// 이전 쿼리
		rtnParam.setOriginalQuery(getValue(map, "originalQuery", "", false));		
		
		// 이전 쿼리들
		//rtnParam.setPreviousQueries( getValues(map,"previousQuery") );	

		return rtnParam;
	}
	
	
	/**
	 * 파라미터 값을 바인딩한다.
	 * @param map 파라미터
	 * @param key 파라미터 키
	 * @param rtnValue null 인 경우 바인딩 
	 * @param chk <script 체크여부
	 * @return
	 * @throws Exception
	 */
	public String getValue(Map<String, String> map, String key, String rtnValue, boolean chk) throws Exception {		
		if(StringUtils.isEmpty(map.get(key)))
			return rtnValue;
		
		
		if(!checkValue(map.get(key).toString(), chk)){
	
			throw new Exception("허용되지 않는 URL입니다.");
		}
		return map.get(key).toString();
	}
	
	/**
	 * 문자열 리턴 메소드
	 * 
	 * @param map
	 * @param key
	 * @param rtnValue
	 * 
	 * @return string
	 */
	public String getValue(Map<String, String> map, String key, String rtnValue) {		
		if(StringUtils.isEmpty(map.get(key)))
			return rtnValue;
		
		return map.get(key).toString();
	}
	
	
	/**
	 * 정수 리턴 메소드
	 * 
	 * @param map
	 * @param key
	 * @param rtnValue
	 * 
	 * @return integer
	 */
	public int getValue(Map<String, String> map, String key, int rtnValue) {
		if(StringUtils.isEmpty(map.get(key)) || !NumberUtils.isNumber(map.get(key))){
			return rtnValue;
		}
		
		return Integer.parseInt(map.get(key));
	}
	
	
	/**
	 * boolean 리턴 메소드
	 * 
	 * @param map
	 * @param key
	 * @param rtnValue
	 * 
	 * @return boolean
	 */
	public boolean getValue(Map<String, String> map, String key) {		
		if(StringUtils.isEmpty(map.get(key)))
			return false;
		
		if("true".equals(map.get(key)) || "on".equals(map.get(key)))
			return true;
		
		return false;
	}
	
	/**
	 * 문자열에 특수문자 및 이상여부를 체크하여 반환한다.
	 * @param value 문자열
	 * @param chk script, frame 체크
	 * @return
	 */
	public boolean checkValue(String value, boolean chk){		

		if(chk){
			if(  (value.toLowerCase()).indexOf("<script") > -1
					|| (value.toLowerCase()).indexOf("<frame") > -1
				    || (value.toLowerCase()).indexOf("<iframe") > -1 ) {
				return false;
			}
		}
		/*
		if(!value.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝|,|_|.]*")) {
			return false;
		}*/
		return true;
	}
}