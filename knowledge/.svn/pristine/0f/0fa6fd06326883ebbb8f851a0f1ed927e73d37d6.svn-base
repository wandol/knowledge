package egovframework.kf.common.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import egovframework.kf.data.RestResultVO;
import egovframework.kf.user.vo.UserVO;

/**
 * Log 인터페이스
 * 
 * @author changho.lee
 * @since 2020.05.07
 */
public interface LogService {
	
	// rest api 요청시 log history남기기
	public boolean writeLogHistory(String user, String item, int rating) throws Exception;
	
	// 파일 다운로드 시 남기는 History Log
	public void insertFileDownLog(Map<String, String> map, UserVO user, HttpServletRequest request) throws Exception;
	
	// 지식 DB 탐색 "상세보기" 버튼 클릭 시 남기는 History Log
	public void insertRecentKnowledgeLog(RestResultVO result, UserVO user, String category) throws Exception;
}
