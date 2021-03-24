package egovframework.kf.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.kf.common.dao.MemecheckerDAO;
import egovframework.kf.common.service.MemecheckerService;
import egovframework.kf.data.RestResultVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("memecheckerService")
public class MemecheckerServiceImpl extends EgovAbstractServiceImpl implements MemecheckerService {
	
	@Resource(name = "memecheckerDAO")
	private MemecheckerDAO memecheckerDAO;

	@Override
	public boolean updateMemechecker(String group) throws Exception {
		// TODO Auto-generated method stub
		return memecheckerDAO.updateMemechecker(group);
	}

	@Override
	public List<Map<String,String>> getMemecheckerRanking(Map map, String cate) throws Exception {
		// TODO Auto-generated method stub
		
		List<Map<String,String>> list = memecheckerDAO.getMemecheckerRanking(map, cate);
		
		if ( list.size() > 0 ) {
			
			RestResultVO resultVO = memecheckerDAO.getMemecheckerFileNames(list);
			
			for ( int i=0; i<list.size(); i++ ) {
				for ( int j=0; j<resultVO.getTotal(); j++) {
					//System.out.println("[getMemecheckerRanking] resultVO ID :: " + resultVO.getResult().get(j).get("id"));
					if ( list.get(i).get("docId").equals(resultVO.getResult().get(j).get("id"))) {
						list.get(i).put("filename", resultVO.getResult().get(j).get("filename"));
					}
				}
			}
			return list;
		} else	
			return null;
	}	
		
	
}
