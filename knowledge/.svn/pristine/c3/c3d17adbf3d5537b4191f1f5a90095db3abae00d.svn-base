package egovframework.kf.kepri.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.kepri.dao.StTurbineDAO;
import egovframework.kf.kepri.service.StTurbineService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("stTurbineService")
public class StTurbineServiceImpl extends EgovAbstractServiceImpl implements StTurbineService {
	
	/** sTturbineDAO */
	@Resource(name = "stTurbineDAO")
	private StTurbineDAO sTturbineDAO;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	@Override
	public RestResultVO turbinSearch(ParameterVO paramVO) throws Exception {
		RestResultVO resultVO = sTturbineDAO.search(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		
		resultVO = sTturbineDAO.getExtractImgList(paramVO.getCategory(), resultVO);
		
		return resultVO;
	}

	@Override
	public Map<String, Object> getDetailData(ParameterVO paramVO) throws Exception {
		Map<String, Object> resultMap = null;
		String message = "";
		RestResultVO resultVO = sTturbineDAO.getDetailData(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		else {
			resultMap = new HashMap<String, Object>();
			resultMap.put("meta", resultVO.getResult());
			
			RestResultVO resultPartVO = sTturbineDAO.getDetailPartData(paramVO);
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
	public RestResultVO getSymptom(String category, String idx) throws Exception {
		
		RestResultVO metaVO = sTturbineDAO.getMeta(category, idx);
		if ( metaVO == null )
			throw processException("info.nodata.msg");
		
		String powerCompNm = metaVO.getResult().get(0).get("POWER_COMP_NM");
		String powerStNm = metaVO.getResult().get(0).get("POWER_ST_NM");
		String stNO = metaVO.getResult().get(0).get("ST_NO");
		
		RestResultVO resultVO = sTturbineDAO.getSymptom(category, powerCompNm, powerStNm, stNO);
		
		return resultVO;
	}
}
