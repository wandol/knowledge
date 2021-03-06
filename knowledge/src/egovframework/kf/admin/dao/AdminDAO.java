package egovframework.kf.admin.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import egovframework.kf.admin.vo.DataTabVO;
import egovframework.kf.admin.vo.QaVO;
import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.DCUtil;
import egovframework.kf.dao.RestModule;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.data.SearchVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : AdminDAO.java
 * Description : 지식 관리
 *
 * @since 2019년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Repository
public class AdminDAO {
	private static final Logger logger = LoggerFactory.getLogger(AdminDAO.class);
	
	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;
			
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** dc util Setting */
	@Resource(name = "dcUtil")
	private DCUtil dcUtil;
	
	/** rest Moudule */
	@Resource(name = "restModule")
	private RestModule restModule;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	
	
	/**
	 * 문서 단위 조회
	 * @param dataTabVO
	 * @param sField
	 * @param sKwd
	 * @return
	 * @throws Exception
	 */
	public RestResultVO selectReportList(DataTabVO dataTabVO, String sField, String sKwd) throws Exception {
		SearchVO searchVO = new SearchVO();
		ParameterVO paramVO = new ParameterVO();
		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		
		if ( !"".equals(sKwd) && sKwd != null ) {
			String searchOp = "";

			if ( dataTabVO.getsSearch().indexOf("#@@#") > -1 ) {
				String[] sSplitKwd = dataTabVO.getsSearch().split("#@@#");
				sKwd = sSplitKwd[0];
				sField = sSplitKwd[1].isEmpty()? sField : sSplitKwd[1];
				
				if ( "PUBLISH_YM".equals(sField)) {
					sField = sSplitKwd[1];
					query.append(sField + " like '*" + sKwd + "*'");
				}
				else {
					searchOp = "allword";
					query = dcUtil.makeQuery( sField, sKwd, searchOp, query, "AND");
				}
				
				if ( !"all".equals(sSplitKwd[2]) ) query.append(" AND POWER_COMP_NM='" + sSplitKwd[2] + "'");
			}
		}
		
		String orderBy = "";
		logger.debug("[selectReportList] sortCol :: " + dataTabVO.getiSortCol() + ", sortCol_0 :: " + dataTabVO.getiSortCol_0());
		if ( dataTabVO.getiSortCol_0() == 0 ) {
			orderBy = " ORDER BY $ROWID " + dataTabVO.getsSortDir_0();
		} else if ( dataTabVO.getiSortCol_0() == 1 ) {
			orderBy = " ORDER BY REPO_FILE_NM " + dataTabVO.getsSortDir_0();
		} else if ( dataTabVO.getiSortCol_0() == 3 ) {
			orderBy = " ORDER BY PUBLISH_YM " + dataTabVO.getsSortDir_0();
		} 
		query.append(orderBy);
		
		String cate = dataTabVO.getCate();

		paramVO.setPageNum(dataTabVO.getiDisplayStart());
		paramVO.setPageSize(dataTabVO.getiDisplayLength());
		
		// 카테고리 정보를 불러온다.
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(cate, "");
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(cateInfoMap.get("field"));
		searchVO.setFrom(cateInfoMap.get("from"));
		searchVO.setHilightTxt(cateInfoMap.get("hilight"));
		
		if ( !"".equals(query.toString()) && query.toString() != null ) {
			searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
			searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
		}
		
		String postParamData = dcUtil.getParamPostDataTableSearch(paramVO, searchVO);
		logger.debug("[selectReportList] QUERY : " + query.toString());
		
		logger.debug("=================================================");
		logger.debug("resturl :: " + searchVO.getUrl() + "?" + postParamData);
		logger.debug("=================================================");
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchDataTablePost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields() + ",NO", paramVO.getPageNum(), paramVO.getPageSize()); // Post 요청
		logger.debug("[selectReportList] success :: " + success);
		
		// StringBuffer 초기화
		if ( query.length() > 0 ) query.charAt(0);
		if ( sbLog.length() > 0 ) sbLog.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;		
	}
	
	/**
	 * 문서의 메타정보 가져오기
	 * @param cate
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RestResultVO selectReportMeta(String cate, String id) throws Exception {
		SearchVO searchVO = new SearchVO();
		ParameterVO paramVO = new ParameterVO();
		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		
		// id가 빈값이 아닐경우에만 로직을 타도록
		if(id != null && !"".equals(id) ) {
			query = dcUtil.makeQuery( "MD5_KEY" , id, "", query, "AND");
			
			paramVO.setPageNum(1);
			paramVO.setPageSize(100);
			
			// 카테고리 정보를 불러온다.
			Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(cate, "");
			searchVO.setUrl(konanPropertiesService.getString("url"));
			searchVO.setCharset(konanPropertiesService.getString("charset"));		
			searchVO.setFields(cateInfoMap.get("field"));
			searchVO.setFrom(cateInfoMap.get("from"));
			searchVO.setHilightTxt(cateInfoMap.get("hilight"));
			
			if ( !"".equals(query.toString()) && query.toString() != null ) {
				searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
				searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
			}
			
			String postParamData = dcUtil.getParamPostDataTableSearch(paramVO, searchVO);
			logger.debug("[selectKnowledgeToRepo] QUERY : " + query.toString());
			
			logger.debug("=================================================");
			logger.debug("resturl :: "+searchVO.getUrl() + "?" + postParamData);
			logger.debug("=================================================");
			
			RestResultVO restVO = new RestResultVO();
			boolean success = restModule.restSearchDataTablePost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields() + ",NO", paramVO.getPageNum(), paramVO.getPageSize()); // Post 요청
			logger.debug("[selectKnowledgeToRepo] success :: " + success);
			
			// StringBuffer 초기화
			if ( query.length() > 0 ) query.charAt(0);
			if ( sbLog.length() > 0 ) sbLog.charAt(0);
			
			if(!success) 
				return null;
					
			return restVO;		
		} else {
			return null;
		}
		
		
	}
	
	/**
	 * 지식 DB 조회
	 * @param cate
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RestResultVO selectKnowledgeToRepo(String cate, String id) throws Exception {
		SearchVO searchVO = new SearchVO();
		ParameterVO paramVO = new ParameterVO();
		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		
		// id가 빈값이 아닐경우에만 로직을 타도록
		if(id != null && !"".equals(id) ) {
			query = dcUtil.makeQuery( "MD5_KEY" , id, "", query, "AND");
			
			paramVO.setPageNum(1);
			paramVO.setPageSize(100);
			
			// 카테고리 정보를 불러온다.
			Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(cate, "");
			searchVO.setUrl(konanPropertiesService.getString("url"));
			searchVO.setCharset(konanPropertiesService.getString("charset"));		
			searchVO.setFields(cateInfoMap.get("pFfield"));
			searchVO.setFrom(cateInfoMap.get("pFfrom"));
			searchVO.setHilightTxt(cateInfoMap.get("pFhilight"));
			
			if ( !"".equals(query.toString()) && query.toString() != null ) {
				searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
				searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
			}
			
			String postParamData = dcUtil.getParamPostDataTableSearch(paramVO, searchVO);
			logger.debug("[selectKnowledgeToRepo] QUERY : " + query.toString());
			
			logger.debug("=================================================");
			logger.debug("resturl :: "+searchVO.getUrl() + "?" + postParamData);
			logger.debug("=================================================");
			
			RestResultVO restVO = new RestResultVO();
			boolean success = restModule.restSearchDataTablePost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields() + ",NO", paramVO.getPageNum(), paramVO.getPageSize()); // Post 요청
			logger.debug("[selectKnowledgeToRepo] success :: " + success);
			
			// StringBuffer 초기화
			if ( query.length() > 0 ) query.charAt(0);
			if ( sbLog.length() > 0 ) sbLog.charAt(0);
			
			if(!success) 
				return null;
					
			return restVO;		
		} else {
			return null;
		}
		
		
	}
	
	/**
	 * 지식 DB 조회
	 * @param cate
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RestResultVO selectKnowledgeCount(String cate, String id) throws Exception {
		SearchVO searchVO = new SearchVO();
		ParameterVO paramVO = new ParameterVO();
		String field = "MD5_KEY,count(*)";
		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		
		// id가 빈값이 아닐경우에만 로직을 타도록
		if(id != null && !"".equals(id) ) {
			query.append("MD5_KEY in {").append(id).append("}");
			query.append(" GROUP BY MD5_KEY");
			
			paramVO.setPageNum(1);
			paramVO.setPageSize(100);
			
			// 카테고리 정보를 불러온다.
			Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(cate, "");
			searchVO.setUrl(konanPropertiesService.getString("url"));
			searchVO.setCharset(konanPropertiesService.getString("charset"));		
			searchVO.setFields("*");
			searchVO.setFrom(cateInfoMap.get("pFfrom"));
			searchVO.setHilightTxt("");
			
			if ( !"".equals(query.toString()) && query.toString() != null ) {
				searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
				searchVO.setLogInfo("");
			}
			
			String restUrl = dcUtil.getRestURL(paramVO, searchVO); //get방식 URL생성
			
			logger.debug("[selectKnowledgeCount] QUERY : " + query.toString());
			
			logger.debug("=================================================");
			logger.debug("resturl :: "+searchVO.getUrl() + restUrl);
			logger.debug("=================================================");
			
			RestResultVO restVO = new RestResultVO();
			boolean success = restModule.restSearch(restUrl, restVO, field); 
			
			
			logger.debug("[selectKnowledgeCount] success :: " + success);
			
			// StringBuffer 초기화
			if ( query.length() > 0 ) query.charAt(0);
			
			if(!success) 
				return null;
					
			return restVO;		
		} else {
			return null;
		}
	}
	
	
	/**
	 * 지식 DB 추가
	 * @param cate
	 * @param id
	 * @param knowledge
	 * @return
	 * @throws Exception
	 */
	public boolean insertKnowledgeToRepo(String cate, String id, Map<String, String> knowledge ) throws Exception {
		logger.debug("[insertKnowledgeToRepo] insertKnowledgeToRepo START");
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(cate, "");
		
//		String filepath = knowledge.get("filepath");
//		logger.debug("[insertKnowledgeToRepo] filepath :: " + filepath);
//		filepath = filepath.replaceAll("\\", "\\\\");
//		logger.debug("[insertKnowledgeToRepo] replace filepath :: " + filepath);
		
		try {
			StringBuffer postData = new StringBuffer();
			
			// 검색엔진 INSERT문
			postData.append("void main()");
			postData.append("{");
			postData.append("setenv('privilege', 'admin');");
			postData.append("use volume " + cateInfoMap.get("realVolume") + ";");
			
			postData.append("insert into " + cateInfoMap.get("pFtable") + " (");
			postData.append("REPO_IDX,");
			postData.append("EQUIP_IDX,");
			postData.append("REPO_KIND,");
			postData.append("DEPT,");
			postData.append("ST_FORM,");
			postData.append("BUILT_YM,");
			postData.append("PRODUCTOR,");
			postData.append("FUEL,");
			postData.append("FILEPATH,");
			postData.append("REPORT_NM,");
			postData.append("FILENAME,");
			postData.append("PUBLISH_YM,");
			postData.append("REPORTER,");
			postData.append("POWER_COMP_NM,");
			postData.append("POWER_ST_NM,");
			postData.append("ST_NO,");
			postData.append("PARTNAME,");
			postData.append("PART2BODY,");
			postData.append("SYMPTOM_KWD,");
			postData.append("ACTION_KWD,");
			postData.append("GUBUN_NO,");
			postData.append("MD5_KEY,");
//			postData.append("PARTNAME_R,");
//			postData.append("SYMPTOM_KWD_R,");
//			postData.append("ACTION_KWD_R");
			
			postData.append(") values (");
			
			postData.append(Integer.parseInt(knowledge.get("repo_idx")));
			postData.append("," + Integer.parseInt(knowledge.get("equip_idx")));
			postData.append(",'" + knowledge.get("repo_kind") + "'");
			postData.append(",'" + knowledge.get("dept") + "'");
			postData.append(",'" + knowledge.get("st_form") + "'");
			postData.append(",'" + knowledge.get("built_ym") + "'");
			postData.append(",'" + knowledge.get("productor") + "'");
			postData.append(",'" + knowledge.get("fuel") + "'");
//			postData.append(",''");
			postData.append(",'" + knowledge.get("filepath").replaceAll("\\\\", "\\\\\\\\") + "'");
			postData.append(",'" + knowledge.get("report_nm") + "'");
			postData.append(",'" + knowledge.get("file_nm") + "'");
			postData.append(",'" + knowledge.get("publish_ym") + "'");
			postData.append(",'" + knowledge.get("reporter") + "'");
			postData.append(",'" + knowledge.get("power_comp_nm") + "'");
			postData.append(",'" + knowledge.get("power_st_nm") + "'");
			postData.append(",'" + knowledge.get("st_no") + "'");
			postData.append(",'" + knowledge.get("partname") + "'");
			postData.append(",'" + knowledge.get("part2body") + "'");
			postData.append(",'" + knowledge.get("symptom_kwd") + "'");
			postData.append(",'" + knowledge.get("action_kwd") + "'");
			postData.append("," + (Integer.parseInt(knowledge.get("gubun_no")) - 1));
			postData.append(",'" + knowledge.get("md5_key") + "'");
//			postData.append(",'" + knowledge.get("partname") + "'");
//			postData.append(",'" + knowledge.get("symptom_kwd") + "'");
//			postData.append(",'" + knowledge.get("action_kwd") + "'");
			postData.append(");");
			
			postData.append("printf('%s', KQL_STATEMENT_OUT);");
			postData.append("}");
			logger.debug("postData :: " + postData.toString());
			
			StringBuffer sb = restModule.restPost(kqlUrl, postData.toString(), "");
			//logger.debug("sb :: " + sb.toString());
			
			if ( "error".equals(postData.toString()) ) return false;
			else return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	/**
	 * 지식 DB 수정
	 * @param cate
	 * @param id
	 * @param knowledge
	 * @return
	 * @throws Exception
	 */
	public boolean updateKnowledgeToRepo(String cate, String id, Map<String, String> knowledge ) throws Exception {
		logger.debug("[updateKnowledgeToRepo] updateKnowledgeToRepo START");
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(cate, "");
		
		try {
			StringBuffer postData = new StringBuffer();
			
			// 검색엔진 UPDATE문
			postData.append("void main()");
			postData.append("{");
			postData.append("setenv('privilege', 'admin');");
			postData.append("use volume " + cateInfoMap.get("realVolume") + ";");
			postData.append("update " + cateInfoMap.get("pFtable"));
			postData.append("   set SYMPTOM_KWD='" + knowledge.get("symptom_kwd") + "'");
			postData.append("      ,ACTION_KWD='" + knowledge.get("action_kwd") + "'");
			postData.append("      ," + cateInfoMap.get("partName") + "='" + knowledge.get("partname") + "'");
			postData.append("      ,IMG_FILES='" + knowledge.get("img_files") + "'");
			postData.append("      ,IMG_TXTS='" + knowledge.get("img_txts") + "'");
			postData.append(" where MD5_KEY='" + id + "' and GUBUN_NO=" + (Integer.parseInt(knowledge.get("gubun_no")) - 1) + ";");
			
			postData.append("printf('%s', KQL_STATEMENT_OUT);");
			postData.append("}");
			
			StringBuffer sb = restModule.restPost(kqlUrl, postData.toString(), "");
			//logger.debug("sb :: " + sb.toString());
			
			if ( "error".equals(postData.toString()) ) return false;
			else return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	/**
	 * 지식 DB 삭제
	 * @param cate
	 * @param id
	 * @param knowledge
	 * @return
	 * @throws Exception
	 */
	public boolean deleteKnowledgeToRepo(String cate, String id, Map<String, String> knowledge ) throws Exception {
		logger.debug("[deleteKnowledgeToRepo] deleteKnowledgeToRepo START");
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(cate, "");
		
		try {
			StringBuffer postData = new StringBuffer();
			
			// 검색엔진 DELETE문
			postData.append("void main()");
			postData.append("{");
			postData.append("setenv('privilege', 'admin');");
			postData.append("use volume " + cateInfoMap.get("realVolume") + ";");
			postData.append("delete from " + cateInfoMap.get("pFtable"));
			postData.append(" where MD5_KEY='" + id + "' and GUBUN_NO=" + (Integer.parseInt(knowledge.get("gubun_no")) - 1) + ";");
			
			postData.append("printf('%s', KQL_STATEMENT_OUT);");
			postData.append("}");
			
			StringBuffer sb = restModule.restPost(kqlUrl, postData.toString(), "");
			//logger.debug("sb :: " + sb.toString());
			
			if ( "error".equals(postData.toString()) ) return false;
			else return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public RestResultVO selectReportList(ParameterVO paramVO) {
		SearchVO searchVO = new SearchVO();
		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		String srchFd = "search_adm_idx";
		
		// 발전사 select box
		if(!"all".equalsIgnoreCase(paramVO.getPowerComp())) {
			query = dcUtil.makeQuery( "POWER_COMP_NM" , paramVO.getPowerComp(), "", query, "AND");
		}
		
		// 사업소 select box
		if(!"all".equalsIgnoreCase(paramVO.getSrchFd())) {
			query = dcUtil.makeQuery( paramVO.getSrchFd() , paramVO.getKwd(), "allword", query, "AND");
		}else{
			query = dcUtil.makeQuery( srchFd , paramVO.getKwd(), "allword", query, "AND");
		}
		
		String orderBy = " ORDER BY REPO_FILE_NM asc";
		query.append(orderBy);
		
		// 카테고리 정보를 불러온다.
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), "");
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(cateInfoMap.get("field"));
		searchVO.setFrom(cateInfoMap.get("from"));
		searchVO.setHilightTxt(cateInfoMap.get("hilight"));
		
		if ( !"".equals(query.toString()) && query.toString() != null ) {
			try {
				searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			try {
				searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		String postParamData = "";
		try {
			postParamData = dcUtil.getParamPostDataTableSearch(paramVO, searchVO);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.debug("[selectReportList] QUERY : " + query.toString());
		
		logger.debug("=================================================");
		logger.debug("resturl :: " + searchVO.getUrl() + "?" + postParamData);
		logger.debug("=================================================");
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchDataTablePost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields() + ",NO", paramVO.getPageNum(), paramVO.getPageSize()); // Post 요청
		logger.debug("[selectReportList] success :: " + success);
		
		// StringBuffer 초기화
		if ( query.length() > 0 ) query.charAt(0);
		if ( sbLog.length() > 0 ) sbLog.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}

	public List<QaVO> selectQalist(ParameterVO paramVO) {
		return sqlSession.selectList("AdminMapper.selectQaList",paramVO);
	}
}

