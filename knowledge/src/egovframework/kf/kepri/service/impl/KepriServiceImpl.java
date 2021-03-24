package egovframework.kf.kepri.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.kepri.dao.KepriDAO;
import egovframework.kf.kepri.service.KepriService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("kepriService")
public class KepriServiceImpl extends EgovAbstractServiceImpl implements KepriService {
	
	/** kepriDAO */
	@Resource(name = "kepriDAO")
	private KepriDAO kepriDAO;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	@Override
	public RestResultVO genSearch(ParameterVO paramVO) throws Exception {
		RestResultVO resultVO = kepriDAO.search(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		
		resultVO = kepriDAO.changeDateFormat(resultVO);
		
		return resultVO;
	}

	@Override
	public Map<String, Object> getDetailData(ParameterVO paramVO) throws Exception {
		Map<String, Object> resultMap = null;
		String message = "";
		RestResultVO resultVO = kepriDAO.getDetailData(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		else {
			resultMap = new HashMap<String, Object>();
			resultMap.put("meta", resultVO.getResult());
			
			RestResultVO resultPartVO = kepriDAO.getDetailPartData(paramVO);
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
}
