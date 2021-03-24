package egovframework.kf.kepri.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.kepri.dao.PerformDAO;
import egovframework.kf.kepri.service.PerformService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("performService")
public class PerformServiceImpl extends EgovAbstractServiceImpl implements PerformService {
	
	/** PerformDAO */
	@Resource(name = "performDAO")
	private PerformDAO performDAO;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	private static final Logger logger = LoggerFactory.getLogger(PerformService.class);
	
	@Override
	public RestResultVO performSearch(ParameterVO paramVO) throws Exception {
		RestResultVO resultVO = performDAO.search(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		
		resultVO = performDAO.changeDateFormat(resultVO);
		
		return resultVO;
	}

	@Override
	public Map<String, Object> getDetailData(ParameterVO paramVO) throws Exception {
		Map<String, Object> resultMap = null;
		String message = "";
		RestResultVO resultVO = performDAO.getDetailData(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		else {
			resultMap = new HashMap<String, Object>();
			resultMap.put("meta", resultVO.getResult());
			
			RestResultVO resultPartVO = performDAO.getDetailPartData(paramVO);
			if ( resultPartVO == null ) {
				message = "no";
			} else {
				message = "ok";
				resultMap.put("partList", resultPartVO.getResult());
			}
			resultMap.put("message", message);
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Object> performSearchDB(ParameterVO paramVO) throws Exception {
		
		List<Map<String, String>> resultPerformList = null;
		Map<String, Object> resultPerformMap = new HashMap<String, Object>();
		Map<String, String> performMap = null;
		int performCount = 0;
		
		// DB 조회용
		Map<String, Object> requestMap = new HashMap<String, Object>();
		if ( paramVO.getPowerComp() != null && !"".equals(paramVO.getPowerComp()) && !"all".equals(paramVO.getPowerComp())) {	// 발전사 정보
			requestMap.put("powerComp", paramVO.getPowerComp());
		}
		if ( paramVO.getPowerSt() != null && !"".equals(paramVO.getPowerSt()) && !"all".equals(paramVO.getPowerSt())) {	// 사업소 정보
			requestMap.put("powerSt", paramVO.getPowerSt());			
		}
		if ( paramVO.getStNo() != null && !"".equals(paramVO.getStNo()) && !"all".equals(paramVO.getStNo())) {	// 호기 정보
			requestMap.put("stNo", paramVO.getStNo());			
		}
		if ( paramVO.getPartName() != null && !"".equals(paramVO.getPartName()) && !"all".equals(paramVO.getPartName())) {	// 호기 정보
			requestMap.put("partName", paramVO.getPartName());			
		}
		if (!paramVO.getStartPublishYm().isEmpty() && !paramVO.getEndPublishYm().isEmpty()){
			requestMap.put("startDate", paramVO.getStartPublishYm());			
			requestMap.put("endDate", paramVO.getEndPublishYm());			
		}
		if ( paramVO.getKwd() != null && !"".equals(paramVO.getKwd()) && !"all".equals(paramVO.getKwd())) {	// 검색어
			requestMap.put("searchKwd", paramVO.getKwd());
		}
		
		List<String> periodList = performDAO.selectTestPeriodGroupBy(requestMap);
		if ( periodList != null && periodList.size() > 0 ) {
			requestMap.put("list", periodList);
			requestMap.put("limit", 10);
			requestMap.put("offset", (paramVO.getPageNum()-1)*paramVO.getPageSize());
			
			resultPerformMap = new HashMap<String, Object>();
			// 디버깅용
//			for ( int i=0; i<periodList.size(); i++ ) {
//				logger.debug("[PerformSearch] period :: " + periodList.get(i).toString());
//			}
			logger.debug("[PerformSearch] requestMap :: " + requestMap.toString());
			List<Map<String, String>> performList = performDAO.selectPerform(requestMap);
			if ( performList != null && performList.size() > 0 ) {
				performCount = performDAO.selectPerformCount(requestMap);
				
				periodList.add(0, "POWER_COMP_NM");
				periodList.add(1, "POWER_ST_NM");
				periodList.add(2, "ST_NO");
				periodList.add(3, "TEST_ITEM");
				
				resultPerformList = new ArrayList<Map<String, String>>();
				// 디버깅용
				for ( int i=0; i<performList.size(); i++ ) {
//					logger.debug("[PerformSearch] performList :: " + performList.get(i).toString());
					performMap = new LinkedHashMap<String, String>();
					for ( int j=0; j<periodList.size(); j++ ) {
						if ( performList.get(i).containsKey(periodList.get(j)) ) {
							performMap.put(periodList.get(j), performList.get(i).get(periodList.get(j)));
						} else {
							performMap.put(periodList.get(j), "-");
						}
					}
					logger.debug("[PerformSearch] performList :: " + performMap.toString());
					resultPerformList.add(performMap);
				}
			}
		} else {
			logger.debug("[PerformSearch] periodList is null");
		}
		
		resultPerformMap.put("performCol", periodList);
		resultPerformMap.put("performTotal", performCount);
		resultPerformMap.put("performData", resultPerformList);
		
		return resultPerformMap;
	}

	@Override
	public List<String> selectPartNameGroupBy(ParameterVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		// DB 조회용
		Map<String, Object> requestMap = new HashMap<String, Object>();
		if ( paramVO.getPowerComp() != null && !"".equals(paramVO.getPowerComp()) && !"all".equals(paramVO.getPowerComp())) {	// 발전사 정보
			requestMap.put("powerComp", paramVO.getPowerComp());
		}
		if ( paramVO.getPowerSt() != null && !"".equals(paramVO.getPowerSt()) && !"all".equals(paramVO.getPowerSt())) {	// 사업소 정보
			requestMap.put("powerSt", paramVO.getPowerSt());			
		}
		if ( paramVO.getStNo() != null && !"".equals(paramVO.getStNo()) && !"all".equals(paramVO.getStNo())) {	// 호기 정보
			requestMap.put("stNo", paramVO.getStNo());			
		}
		
		List<String> partNameList = performDAO.selectTestItemGroupBy(requestMap);
		if ( partNameList != null && partNameList.size() > 0 ) {
			return partNameList;
		}
		return null;
	}	
}
