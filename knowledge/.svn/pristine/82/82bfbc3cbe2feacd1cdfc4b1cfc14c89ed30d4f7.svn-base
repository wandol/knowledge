package egovframework.kf.rest.service;


import java.util.Map;

import egovframework.kf.data.RestResultVO;
import egovframework.kf.rest.vo.RestVO;

public interface RestService {
	RestResultVO getKnowlegeList(RestVO restvo);

	RestResultVO getKnowlegeRetrieve(RestVO restvo);

	RestResultVO getKnowlegeRetrieveImages(RestVO restvo);

	RestResultVO searchList(RestVO restvo);

	RestResultVO searchRetrieve(RestVO restvo);
	
	/********************************************************************************* 추천 서비스 API**/
	Map<String, Object> recommendList(RestVO restvo);
	
	Map<String, Object> getRecentReportList(RestVO rest);
	
	RestResultVO recommendRetrieve(RestVO restvo);
	
	Map<String, Object> getPopularKnowledge(String cate);
	
	Map<String, String> getCommentList();
	
	Map<String, String> getCommentList(String cate, String part_id);
	
	/********************************************************************************* 분석 서비스 API**/
	Map<String, String> extractFileBody(String fileName);	// 파일 원문 추출 API
	
	Map<String, Object> sentence(String text);	// 문장 분리 API
	
	Map<String, Object> posTag(String text);	// 형태소 분석 API
	
	Map<String, Object> parse(String text);	// 구문 분석 API
	
	Map<String, Object> symantic(String cate, String text);	// 구문 분석 API
	
//	Map<String, Object> ner(String title, String text);	// 구문 분석 API
	
	Map<String, Object> ner(String sentence, String classfication);	
}
