package egovframework.kf.admin.service;

import java.util.List;
import java.util.Map;

import egovframework.kf.admin.vo.DataTabVO;
import egovframework.kf.admin.vo.QaVO;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;

public interface AdminService {
	
	// 문서 조회
//	public RestResultVO selectReportList(DataTabVO dataTabVO) throws Exception;
//	public RestResultVO selectReportList(ParameterVO paramVO, String cate) throws Exception;
	
	// 지식 조회
	public RestResultVO selectReportMeta(String cate, String id) throws Exception;
	
	// 지식 갯수 조회 
	public Map<String,String> selectKnowledgeCount(String cate, List<Map<String, String>> md5List) throws Exception;
		
	// 지식 조회
	public RestResultVO selectKnowledgeToRepo(String cate, String id) throws Exception;
	
	// 지식 수정
	public boolean updateKnowledgeToRepo(String cate, String id, List<Map<String, String>> requestList ) throws Exception;
	
	// 문서 조회
	public RestResultVO selectReportList(ParameterVO paramVO);

	//	qa 조회.
	public List<QaVO> selectQalist(ParameterVO paramVO);
}
