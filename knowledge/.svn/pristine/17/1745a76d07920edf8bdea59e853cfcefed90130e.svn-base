package egovframework.kf.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.kf.common.dao.SmartMatchDAO;
import egovframework.kf.common.service.SmartMatchService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("smartMatchService")
public class SmartMatchServiceImpl extends EgovAbstractServiceImpl implements SmartMatchService {
	
	private static final Logger logger = LoggerFactory.getLogger(SmartMatchServiceImpl.class);
	
	@Resource(name = "smartMatchDAO")
	private SmartMatchDAO smartMatchDAO;
	
	@Override
	public boolean writeSmartMatchLog(String user, String item, int rating) throws Exception {
		// TODO Auto-generated method stub
		return smartMatchDAO.writeSmartMatchLog(user, item, rating);
	}

	@Override
	public List<Map<String, String>> getSmartMatchList(String user) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, String>> list = smartMatchDAO.getSmartMatchIDList(user); 
		List<Map<String, String>> resultList = null;
		Map<String, String> map = null;
				
		logger.debug("[getSmartMatchList] id List get Success");
		
		if ( list.size() > 0 ) {
			resultList = new ArrayList<Map<String, String>>();
			
			for ( int i=0; i<list.size(); i++ ) {	
				map = new HashMap<String, String>();
				
				logger.debug("[getSmartMatchList] item : " + list.get(i).get("item"));
				map = smartMatchDAO.getSmartMatchFileInfo(list.get(i).get("item"));
				
				if ( map != null ) {
					resultList.add(map);
				} else {
					logger.debug("[getSmartMatchList] map is null");
				}
			}
		} else {
			logger.debug("[getSmartMatchList] list size zero");
		}
		
		return resultList;
	}
	
	
	
	
}
