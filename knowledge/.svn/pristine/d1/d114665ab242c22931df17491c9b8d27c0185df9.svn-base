package egovframework.kf.kepri.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.kepri.dao.GenInsDAO;
import egovframework.kf.kepri.service.GenInsService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("genInsService")
public class GenInsServiceImpl extends EgovAbstractServiceImpl implements GenInsService {
	
	/** genInsDAO */
	@Resource(name = "genInsDAO")
	private GenInsDAO genInsDAO;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	@Override
	public RestResultVO genSearch(ParameterVO paramVO) throws Exception {
		RestResultVO resultVO = genInsDAO.search(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		
		resultVO = genInsDAO.getExtractImgList(paramVO.getCategory(), resultVO);
		
		return resultVO;
	}

	@Override
	public Map<String, Object> getDetailData(ParameterVO paramVO) throws Exception {
		Map<String, Object> resultMap = null;
		String message = "";
		RestResultVO resultVO = genInsDAO.getDetailData(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		else {
			resultMap = new HashMap<String, Object>();
			resultMap.put("meta", resultVO.getResult());
			
			RestResultVO resultPartVO = genInsDAO.getDetailPartData(paramVO);
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
		
		RestResultVO metaVO = genInsDAO.getMeta(category, idx);
		if ( metaVO == null )
			throw processException("info.nodata.msg");
		
		String powerCompNm = metaVO.getResult().get(0).get("POWER_COMP_NM");
		String powerStNm = metaVO.getResult().get(0).get("POWER_ST_NM");
		String stNO = metaVO.getResult().get(0).get("ST_NO");
		
		RestResultVO resultVO = genInsDAO.getSymptom(category, powerCompNm, powerStNm, stNO);
		
		return resultVO;
	}
}
