package egovframework.kf.kepri.service;

import java.util.List;
import java.util.Map;

import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;

/**
 * 성능 검색하기 위한 서비스 인터페이스
 * 
 * @author changho.lee
 * @since 2019.01.19
 */
public interface PerformService {
	
	/**
	 * 발전기-누설흡습 정보를 검색한다.
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	// Engine 영역
	public RestResultVO performSearch(ParameterVO paramVO) throws Exception;

	public Map<String, Object> getDetailData(ParameterVO paramVO) throws Exception;
	
	// DB 영역
	public Map<String, Object> performSearchDB(ParameterVO paramVO) throws Exception;
	
	public List<String> selectPartNameGroupBy(ParameterVO paramVO) throws Exception;

}
