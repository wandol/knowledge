package egovframework.kf.common.service;

import java.util.List;
import java.util.Map;

/**
 * Smart Match 서비스 인터페이스
 * 
 * @author seunghee.kim
 * @since 2019.01.19
 */
public interface SmartMatchService {
	
	// 추천 item log 남기기용
	public boolean writeSmartMatchLog(String user, String item, int rating) throws Exception;
	
	// 추천 item(문서) 리스트 가져오기
	public List<Map<String,String>> getSmartMatchList(String user) throws Exception;
}
