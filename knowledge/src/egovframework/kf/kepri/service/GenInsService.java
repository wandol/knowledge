package egovframework.kf.kepri.service;

import java.util.Map;

import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;

/**
 * 발전기-절연진단을 검색하기 위한 서비스 인터페이스
 * 
 * @author seunghee.kim
 * @since 2019.01.19
 */
public interface GenInsService {
	
	/**
	 * 발전기-절연진단 정보를 검색한다.
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public RestResultVO genSearch(ParameterVO paramVO) throws Exception;

	public Map<String, Object> getDetailData(ParameterVO paramVO) throws Exception;
	
	public RestResultVO getSymptom(String category, String idx) throws Exception;
}
