package egovframework.kf.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.kf.admin.dao.AdminDAO;
import egovframework.kf.admin.service.AdminService;
import egovframework.kf.admin.vo.DataTabVO;
import egovframework.kf.admin.vo.QaVO;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Resource(name = "adminDAO")
	private AdminDAO adminDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

//	@Override
//	public RestResultVO selectReportList(DataTabVO dataTabVO) throws Exception {
//		// TODO Auto-generated method stub
//		
//		String sField = "";	// 검색 대상
//		String sKwd = "";	// 검색어
//				
//		if ( !"".equals(dataTabVO.getsSearch_1()) && dataTabVO.getsSearch_1() != null ) {
//			sField = dataTabVO.getmDataProp_1();
//			sKwd = dataTabVO.getsSearch_1();
//		} else if ( !"".equals(dataTabVO.getsSearch_2()) && dataTabVO.getsSearch_2() != null ) {
//			sField = dataTabVO.getmDataProp_2();
//			sKwd = dataTabVO.getsSearch_2();
//		} else if ( !"".equals(dataTabVO.getsSearch_3()) && dataTabVO.getsSearch_3() != null ) {
//			sField = dataTabVO.getmDataProp_3();
//			sKwd = dataTabVO.getsSearch_3();
//		} else if ( !"".equals(dataTabVO.getsSearch_4()) && dataTabVO.getsSearch_4() != null ) {
//			sField = dataTabVO.getmDataProp_4();
//			sKwd = dataTabVO.getsSearch_4();
//		} else {
//			sField = "search_adm_idx";
//			sKwd = dataTabVO.getsSearch();
//		}
//		
//		return adminDAO.selectReportList(dataTabVO, sField, sKwd);
//	}
	
	@Override
	public RestResultVO selectReportMeta(String cate, String id) throws Exception {
		// TODO Auto-generated method stub
		
		return adminDAO.selectReportMeta(cate, id);
	}
	
	@Override
	public RestResultVO selectKnowledgeToRepo(String cate, String id) throws Exception {
		// TODO Auto-generated method stub
		
		return adminDAO.selectKnowledgeToRepo(cate, id);
	}
	
	@Override
	public Map<String,String> selectKnowledgeCount(String cate, List<Map<String, String>> md5List) throws Exception {
		Map<String,String> resultMap = new HashMap<String, String>();
		RestResultVO result;
		ArrayList<String> idList = new ArrayList<String>();

		if( md5List.size() > 0) {
			// 해당 md5에 지식이 없을 수도 있기때문에 미리 기본값으로 0건을 셋팅해준다. (에러방지)
			for(int i=0; i < md5List.size(); i++) {
				String md5_key = md5List.get(i).get("MD5_KEY");
				resultMap.put(md5_key, "0");
				idList.add(md5_key);
			}
			
			result = adminDAO.selectKnowledgeCount(cate, String.join(",", idList));
			
			// 값 담기.
			for(int i=0; i < result.getTotal(); i++) {
				resultMap.put(result.getResult().get(i).get("MD5_KEY"), result.getResult().get(i).get("count"));
			}
		}
		
		return resultMap; 
	}
	
	@Override
	public boolean updateKnowledgeToRepo(String cate, String id, List<Map<String, String>> requestList ) throws Exception {
		// TODO Auto-generated method stub
		
		boolean isUpdate = true;
		
		for ( Map<String, String> knowledge : requestList ) {
			
			if ( !"".equals(knowledge.get("status")) && knowledge.get("status") != null ) {
				if ( "udt".equals(knowledge.get("status")) ) {
					logger.debug("[updateKnowledgeToRepo] update knowledge :: " + knowledge.toString() + " START");
					isUpdate = adminDAO.updateKnowledgeToRepo(cate, id, knowledge);
					logger.debug("[updateKnowledgeToRepo] isUpdate :: " + isUpdate);
					logger.debug("[updateKnowledgeToRepo] update knowledge END");
				} else if ( "ins".equals(knowledge.get("status")) ) {
					logger.debug("[updateKnowledgeToRepo] insert knowledge :: " + knowledge.toString() + " START");
					isUpdate = adminDAO.insertKnowledgeToRepo(cate, id, knowledge);
					logger.debug("[updateKnowledgeToRepo] isUpdate :: " + isUpdate);
					logger.debug("[updateKnowledgeToRepo] insert knowledge END");
				} else if ( "del".equals(knowledge.get("status")) ) {
					logger.debug("[updateKnowledgeToRepo] delete knowledge :: " + knowledge.toString() + " START");
					isUpdate = adminDAO.deleteKnowledgeToRepo(cate, id, knowledge);
					logger.debug("[updateKnowledgeToRepo] isUpdate :: " + isUpdate);
					logger.debug("[updateKnowledgeToRepo] delete knowledge END");
				} else {
					logger.debug("[updateKnowledgeToRepo] status not found");
				}
			} 
//			else {
//				logger.debug("[updateKnowledgeToRepo] status not found");
//			}
		}
		
		return isUpdate;
	}

	@Override
	public RestResultVO selectReportList(ParameterVO paramVO) {
		return adminDAO.selectReportList(paramVO);
	}

	@Override
	public List<QaVO> selectQalist(ParameterVO paramVO) {
		return adminDAO.selectQalist(paramVO);
	}
}
