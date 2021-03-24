package egovframework.kf.kepri.dao;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.DCUtil;
import egovframework.kf.dao.RestModule;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.data.SearchVO;
import egovframework.kf.kepri.vo.AttachVO;
import egovframework.kf.kepri.vo.DocCategoryVO;
import egovframework.kf.kepri.vo.DocumentVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : DocumentDAO.java
 * Description : 발전문서 관련
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 * 2019년 02월  19일                       최초 작성
 * 2020년 08월  31일       이창호           수정
 *
 * @since 2019년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Repository
public class DocDAO {
	private static final Logger logger = LoggerFactory.getLogger(DocDAO.class);
	
	/** 엔진 공통 유틸 */
	@Resource(name = "dcUtil")
	private DCUtil dcUtil;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** REST 모듈 */
	@Resource(name = "restModule")
	private RestModule restModule;
	
	/** DB */
	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;
		
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	

	/**
	 * 카테고리 정보 추가
	 * @param docCategoryVO
	 * @return
	 * @throws Exception
	 */
	public int insertDocCategory(DocCategoryVO docCategoryVO) throws Exception {
		return sqlSession.insert("DocMapper.insertCategoryInfo", docCategoryVO);
	}
	
	/**
	 * 카테고리 정보 불러오기
	 * @return
	 * @throws Exception
	 */
	public List<DocCategoryVO> selectDocCategoryList() throws Exception {
		return sqlSession.selectList("DocMapper.selectCategoryList");
	}
	
	/**
	 * 카테고리 셀렉트박스용 정보 불러오기
	 * @return
	 * @throws Exception
	 */
	public List<DocCategoryVO> selectDocCategorySelectList() throws Exception {
		return sqlSession.selectList("DocMapper.selectCategorySelectList");
	}
	
	/**
	 * 카테고리 정보 중복 체크
	 * @param docCategoryVO
	 * @return
	 * @throws Exception
	 */
	public int selectCheckCategory(DocCategoryVO docCategoryVO) throws Exception {
		return sqlSession.selectOne("DocMapper.selectCheckCategory", docCategoryVO);
	}
	
	/**
	 * 카테고리 정보 수정
	 * @param docCategoryVO
	 * @throws Exception
	 */
	public void updateCategory(DocCategoryVO docCategoryVO) throws Exception {
		sqlSession.update("DocMapper.updateCategory", docCategoryVO);
	}
	
	/**
	 * 문서 카테고리 명 수정
	 * @param docCategoryVO
	 * @throws Exception
	 */
	public void updateDocumentCategoryNM(DocCategoryVO docCategoryVO) throws Exception {
		sqlSession.update("DocMapper.updateDocumentCategoryNM", docCategoryVO);
	}
	
	/**
	 * 카테고리 수정 - 검색엔진 볼륨
	 * @param documentVO
	 * @param cate
	 * @return
	 */
	public boolean updateCategoryVolume(DocCategoryVO docCategoryVO, String cate) {
		logger.debug("[updateCategoryVolume] updateCategoryVolume START");
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(cate, "");
		
		try {
			StringBuffer postData = new StringBuffer();
			
			// 검색엔진 UPDATE문
			postData.append("void main()");
			postData.append("{");
			postData.append("setenv('privilege', 'admin');");
			postData.append("use volume " + cateInfoMap.get("volume") + ";");
			postData.append("update " + cateInfoMap.get("table"));
			postData.append("   set CAT_NM='" + docCategoryVO.getCat_nm() + "'");
			postData.append("      ,USE_YN='" + docCategoryVO.getUse() + "'");
			postData.append(" where CAT_IDX='" + docCategoryVO.getCat_idx() + "';");
			
			postData.append("printf('%s', KQL_STATEMENT_OUT);");
			postData.append("}");
			
			logger.debug("[updateCategoryVolume] rest call :: " + postData.toString());
			StringBuffer sb = restModule.restPost(kqlUrl, postData.toString(), "");
			logger.debug("[updateCategoryVolume] restAPI result :: " + sb.toString());
			
			if ( "error".equals(postData.toString()) ) return false;
			else return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	/**
	 * 카테고리 삭제 - DB
	 * @param documentVO
	 */
	public void deleteCategoryDB(List<Map<String, String>> list) throws Exception {
		sqlSession.delete("DocMapper.deleteCategory", list);
	}
	
	/**
	 * 카테고리 관련 문서 삭제 - DB
	 * @param documentVO
	 */
	public void deleteDocumentByCategory(List<Map<String, String>> list) throws Exception {
		sqlSession.delete("DocMapper.deleteDocumentByCategory", list);
	}
	
	/**
	 * 카테고리 관련 첨부파일 삭제 - DB
	 * @param documentVO
	 */
	public void deleteDocumentAttachByCategory(List<Map<String, String>> list) throws Exception {
		sqlSession.delete("DocMapper.deleteDocumentAttachByCategory", list);
	}
	
	/**
	 * 카테고리 삭제 - 검색엔진 볼륨
	 * @param List<Map<String, String>>
	 * @param cate
	 * @return
	 */
	public boolean deleteCategoryVolume(List<Map<String, String>> catIdxList, String cate) {
		logger.debug("[deleteCategoryVolume] deleteCategoryVolume START");
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(cate, "");
		
		try {
			StringBuffer postData = new StringBuffer();
			
			// 검색엔진 UPDATE문
			postData.append("void main()");
			postData.append("{");
			postData.append("setenv('privilege', 'admin');");
			postData.append("use volume " + cateInfoMap.get("volume") + ";");
			postData.append("delete from " + cateInfoMap.get("table"));
			postData.append(" where CAT_IDX IN {");
			
			for ( int i=0; i<catIdxList.size(); i++ ) {
				if ( i==0 ) postData.append("'" + catIdxList.get(i).get("cat_idx") + "'");
				else postData.append(", '" + catIdxList.get(i).get("cat_idx") + "'");
			}
			postData.append("};");
			
			postData.append("printf('%s', KQL_STATEMENT_OUT);");
			postData.append("}");

			logger.debug("[deleteCategoryVolume] restAPI call :: " + postData.toString());
			StringBuffer sb = restModule.restPost(kqlUrl, postData.toString(), "");
			logger.debug("[deleteCategoryVolume] restAPI result :: " + sb.toString());
			
			if ( "error".equals(postData.toString()) ) return false;
			else return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	/**
	 * 발전 문서 등록
	 * @param documentVO
	 * @return
	 * @throws Exception
	 */
	public int insertDocument(DocumentVO documentVO) throws Exception {
		return sqlSession.insert("DocMapper.insertDocument", documentVO);
	}
	
	/**
	 * 발전문서 추가 - 볼륨
	 * @return
	 */
	public boolean insertDocumentVolume() {
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		
		try {
			StringBuffer postData = new StringBuffer();
			
			postData.append("void main()");
			postData.append("{");
			// 5.0.0버전부터는 권한체크를 하기 때문에 아래와 같이 kql을 날릴때 권한 설정을 해주어야 한다.
			postData.append("setenv('privilege', 'admin');");
			postData.append("run ../script/gen_doc/bulk/bulk_all_max.k;");
			postData.append("printf('%s', KQL_STATEMENT_OUT);");
			postData.append("}");
			logger.debug("[insertDocument] postData :: " + postData.toString());
			
			StringBuffer sb = restModule.restPost(kqlUrl, postData.toString(), "");
			logger.debug("[insertDocument] call rest API result :: " + sb.toString());
			
			if ( "error".equals(sb.toString()) ) return false;
			else return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/**
	 * 발전 문서 삭제
	 * @param documentVO
	 * @throws Exception
	 */
	public void deleteDocument(DocumentVO documentVO) throws Exception {
		sqlSession.delete("DocMapper.deleteDocument", documentVO);
	}
	
	/**
	 * 발전 문서 첨부파일 등록
	 * @param attachVOList
	 * @return
	 * @throws Exception
	 */
	public int insertDocumentAttach(List<AttachVO> list) throws Exception {
		return sqlSession.insert("DocMapper.insertDocumentAttach", list);
	}
	
	/**
	 * 발전 문서 조회
	 * @return
	 * @throws Exception
	 */
	public List<DocumentVO> selectDocument(ParameterVO parameterVO) throws Exception {
		return sqlSession.selectList("DocMapper.selectDocument", parameterVO);
	}
	
	/**
	 * 발전 문서 총 갯수
	 * @return
	 * @throws Exception
	 */
	public int selectDocumentTotalCount() throws Exception {
		return sqlSession.selectOne("DocMapper.selectDocumentTotalCount");
	}
	
	/**
	 * 파일 목록 조회
	 * 문서 ID로 호출
	 * @param doc_idx
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> selectDocumentFileListByDocID(String doc_idx) throws Exception {
		return sqlSession.selectList("DocMapper.selectDocumentFileListByDocID", doc_idx);
	}
	
	/**
	 * 파일 목록 조회
	 * 카테고리 ID로 호출
	 * @param doc_idx
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> selectDocumentFileListByCategory(List<Map<String, String>> list) throws Exception {
		return sqlSession.selectList("DocMapper.selectDocumentFileListByCategory", list);
	}
	
	/**
	 * 키워드에 맞는 발전문서 조회
	 * 
	 * @param kwd
	 * @throws IOException 
	 */
	public RestResultVO search(ParameterVO paramVO) throws Exception {
		SearchVO searchVO = new SearchVO();
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		String searchFd = konanPropertiesService.getString("searchFd");		// text_idx
		String searchOpt = konanPropertiesService.getString("searchOpt");	// allword
		String strNmFd = paramVO.getFields().isEmpty()? searchFd : paramVO.getFields();
		
		// 카테고리 정보를 가져온다.
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), "");

		//	선택된 검색대상으로 검색.
		if(!"".equals(paramVO.getFields())) {
			if("CAT_NM".equals(paramVO.getFields())) {
				query = dcUtil.makeLikeQuery(paramVO.getFields(), paramVO.getKwd(), "all", query);	
			}else {
				query = dcUtil.makeQuery( paramVO.getFields() , paramVO.getKwd(), searchOpt + " synonym('d0')", query, "AND");
			}
		}else {
			if(!"".equals(paramVO.getKwdList())) {
				query = dcUtil.makeQuery( "KWD" , paramVO.getKwdList(), "", query, "AND");
			}else {
				query = dcUtil.makeQuery( strNmFd , paramVO.getKwd(), searchOpt + " synonym('d0')", query, "AND");
			}
		}
		
		if ( !"".equals(query.toString() )) query.append(" AND ");
		
		// 카테고리 사용이 Y인 것만 조회
		query.append("USE_YN='Y' ");
		
		// 최신순 정렬(Default)
		query.append(" ");
		query.append("ORDER BY OPERATE_TM DESC");
		
		//로그기록 
		//SITE@인물+$|첫검색|1|정확도순^코난	
		sbLog.append(  dcUtil.getLogInfo(commonUtil.null2Str(paramVO.getSiteNm(),"SITE"),
				"발전문서관리" ,
				commonUtil.null2Str( paramVO.getUserId(),""), paramVO.getKwd(),
				paramVO.getPageNum(),
				false,
				paramVO.getSort(),
				commonUtil.null2Str( paramVO.getRecKwd(),"" )) );
	
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(cateInfoMap.get("field"));
		searchVO.setFrom(cateInfoMap.get("from"));
		// 2019.11.07 changho.lee 키워드 없이 검색 되도록 수정
		if ( !"".equals(query.toString()) && query.toString() != null ) {
			searchVO.setHilightTxt(cateInfoMap.get("hilight"));
			searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
			searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
		}
		
		// URL 생성
		String postParamData = dcUtil.getParamPostDataSearch(paramVO, searchVO);
		logger.debug("[search] QUERY : " + query.toString());
		logger.debug("[search] RESTURL : " + postParamData);
		logger.debug("[search] LOG : " + sbLog.toString());
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields()); //post 방식 호출
		logger.debug("[search] search API Call success :: " + success);
		
		// 2019.11.07 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		if ( sbLog.length() > 0 ) sbLog.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;		
	}
	
	/**
	 * 키워드에 맞는 발전문서 조회
	 * 
	 * @param kwd
	 * @throws IOException 
	 */
	public RestResultVO detailSearch(ParameterVO paramVO) throws Exception {
		SearchVO searchVO = new SearchVO();		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		String strNmFd = "DOC_IDX";
		
		logger.debug("[detailSearch] strNmFd :: " + strNmFd);
		// 카테고리 정보를 가져온다.
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), "");
		logger.debug("[detailSearch] cateInfoMap :: " + cateInfoMap.toString());
		
		// 쿼리 부분
		query = dcUtil.makeQuery( strNmFd , paramVO.getKwd(), "", query, "AND");	
	
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(cateInfoMap.get("field"));
		searchVO.setFrom(cateInfoMap.get("from"));
		// 2019.11.07 changho.lee 키워드 없이 검색 되도록 수정
		if ( !"".equals(query.toString()) && query.toString() != null ) {
			searchVO.setHilightTxt(cateInfoMap.get("hilight"));
			searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		}
		
		paramVO.setPageNum(1);
		paramVO.setPageSize(1);
		
		// URL 생성
		String postParamData = dcUtil.getParamPostDataSearch(paramVO, searchVO);
		logger.debug("[search] QUERY : " + query.toString());
		logger.debug("[search] RESTURL : " + postParamData);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields()); //post 방식 호출
		logger.debug("[search] search API Call success :: " + success);
		
		// 2019.11.07 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;		
	}
	
	/**
	 * 발전문서 수정 - 검색엔진 볼륨
	 * @param documentVO
	 * @param cate
	 * @return
	 */
	public boolean updateDocumentVolume(DocumentVO documentVO, String cate) {
		logger.debug("[updateDocumentVolume] updateDocumentVolume START");
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(cate, "");
		
		try {
			StringBuffer postData = new StringBuffer();
			
			// 검색엔진 UPDATE문
			postData.append("void main()");
			postData.append("{");
			postData.append("setenv('privilege', 'admin');");
			postData.append("use volume " + cateInfoMap.get("volume") + ";");
			postData.append("update " + cateInfoMap.get("table"));
			postData.append("   set TTL='" + documentVO.getDocTitle() + "'");
			postData.append("      ,ABSTRACT='" + documentVO.getDocContents() + "'");
			postData.append("      ,KWD='" + documentVO.getDocKeyword() + "'");
			postData.append("      ,CAT_IDX='" + documentVO.getCat_idx() + "'");
			postData.append("      ,CAT_NM='" + documentVO.getCat_nm() + "'");
			postData.append(" where DOC_IDX='" + documentVO.getDoc_idx() + "';");
			
			postData.append("printf('%s', KQL_STATEMENT_OUT);");
			postData.append("}");
			
			StringBuffer sb = restModule.restPost(kqlUrl, postData.toString(), "");
			logger.debug("[updateDocumentVolume] restAPI result :: " + sb.toString());
			
			if ( "error".equals(postData.toString()) ) return false;
			else return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	/**
	 * 발전문서 수정 - DB
	 * @param documentVO
	 */
	public void updateDocumentDB(DocumentVO documentVO) throws Exception {
		sqlSession.insert("DocMapper.updateDocument", documentVO);
	}
	
	/**
	 * 발전문서 삭제 - 검색엔진 볼륨
	 * @param documentVO
	 * @param cate
	 * @return
	 */
	public boolean deleteDocumentVolume(List<Map<String, String>> docIdxList, String cate) {
		logger.debug("[deleteDocumentVolume] deleteDocumentVolume START");
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(cate, "");
		
		try {
			StringBuffer postData = new StringBuffer();
			
			// 검색엔진 UPDATE문
			postData.append("void main()");
			postData.append("{");
			postData.append("setenv('privilege', 'admin');");
			postData.append("use volume " + cateInfoMap.get("volume") + ";");
			postData.append("delete from " + cateInfoMap.get("table"));
			postData.append(" where DOC_IDX IN {");
			
			for ( int i=0; i<docIdxList.size(); i++ ) {
				if ( i==0 ) postData.append("'" + docIdxList.get(i).get("doc_idx") + "'");
				else postData.append(", '" + docIdxList.get(i).get("doc_idx") + "'");
			}
			postData.append("};");
			
			postData.append("printf('%s', KQL_STATEMENT_OUT);");
			postData.append("}");
			
			StringBuffer sb = restModule.restPost(kqlUrl, postData.toString(), "");
			logger.debug("[deleteDocumentVolume] restAPI result :: " + sb.toString());
			
			if ( "error".equals(postData.toString()) ) return false;
			else return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	/**
	 * 발전문서 삭제 - DB
	 * @param documentVO
	 */
	public void deleteDocumentDB(List<Map<String, String>> list) throws Exception {
		sqlSession.delete("DocMapper.deleteDocument", list);
	}
	
	/**
	 * 발전문서 첨부파일 삭제 - DB
	 * @param documentVO
	 */
	public void deleteDocumentAttachDB(List<Map<String, String>> list) throws Exception {
		sqlSession.delete("DocMapper.deleteDocumentAttach", list);
	}
}
