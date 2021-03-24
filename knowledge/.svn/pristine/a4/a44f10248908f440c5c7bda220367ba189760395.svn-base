package egovframework.kf.kepri.service;

import java.util.List;
import java.util.Map;

import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.kepri.vo.AttachVO;
import egovframework.kf.kepri.vo.DocCategoryVO;
import egovframework.kf.kepri.vo.DocumentVO;
import egovframework.kf.user.vo.UserVO;

/**
 * 발전문서 서비스 인터페이스
 * 
 * @author changho.lee
 * @since 2020.09.15
 */
public interface DocService {
	
	public Map<String, Object> insertDocCategory(DocCategoryVO docCategoryVO, UserVO userVO) throws Exception;
	
	public List<DocCategoryVO> selectDocCategoryList() throws Exception;
	
	public List<DocCategoryVO> selectDocCategorySelectList() throws Exception;
	
	public int selectCheckCategory(DocCategoryVO docCategoryVO) throws Exception;
	
	public void updateCategory(DocCategoryVO docCategoryVO) throws Exception;
	
	public void updateDocumentCategoryNM(DocCategoryVO docCategoryVO) throws Exception;
	
	public boolean updateCategoryVolume(DocCategoryVO docCategoryVO, String cate);
	
	public void deleteCategoryDB(List<Map<String, String>> list) throws Exception;
	
	public void deleteDocumentByCategory(List<Map<String, String>> list) throws Exception;
	
	public void deleteDocumentAttachByCategory(List<Map<String, String>> list) throws Exception;
	
	public boolean deleteCategoryVolume(List<Map<String, String>> catIdxList, String cate);
	
	public int insertDocument(DocumentVO documentVO) throws Exception;
	
	public boolean insertDocumentVolume();
	
	public void deleteDocument(DocumentVO documentVO) throws Exception;
	
	public int insertDocumentAttach(List<AttachVO> attachVOList) throws Exception;
	
	public List<DocumentVO> selectDocument(ParameterVO parameterVO) throws Exception;
	
	public int selectDocumentTotalCount() throws Exception;
	
	public List<Map<String, String>> selectDocumentFileListByDocID(String doc_idx) throws Exception;
	
	public List<Map<String, String>> selectDocumentFileListByCategory(List<Map<String, String>> list) throws Exception;
	
	public RestResultVO search(ParameterVO paramVO) throws Exception;
	
	public RestResultVO detailSearch(ParameterVO paramVO) throws Exception;
	
	public boolean updateDocumentVolume(DocumentVO documentVO, String cate) throws Exception;
	
	public void updateDocumentDB(DocumentVO documentVO) throws Exception;
	
	public boolean deleteDocumentVolume(List<Map<String, String>> docIdxList, String cate);
	
	public void deleteDocumentDB(List<Map<String, String>> list) throws Exception;
	
	public void deleteDocumentAttachDB(List<Map<String, String>> list) throws Exception;
}
