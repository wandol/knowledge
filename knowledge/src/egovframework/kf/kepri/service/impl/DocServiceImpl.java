package egovframework.kf.kepri.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.kepri.dao.DocDAO;
import egovframework.kf.kepri.service.DocService;
import egovframework.kf.kepri.vo.AttachVO;
import egovframework.kf.kepri.vo.DocCategoryVO;
import egovframework.kf.kepri.vo.DocumentVO;
import egovframework.kf.user.vo.UserVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("docService")
public class DocServiceImpl extends EgovAbstractServiceImpl implements DocService {
	
	/** DocumentDAO */
	@Resource(name = "docDAO")
	private DocDAO docDAO;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	private static final Logger logger = LoggerFactory.getLogger(DocService.class);

	/**
	 * 카테고리 정보 추가
	 */
	@Override
	public Map<String, Object> insertDocCategory(DocCategoryVO docCategoryVO, UserVO userVO) throws Exception {
		
		boolean isInsert = true;
		boolean isDuplicate = false;
		Map<String, Object> map = new HashMap<String, Object>();

		int duplicate = 0;
		logger.debug("[insertDocCategory] insert for start");
			
		docCategoryVO.setOperator_id(String.valueOf(userVO.getUser_id()));
		docCategoryVO.setOperate_nm(userVO.getUser_nm());
		logger.debug("[insertDocCategory] insert info :: " + docCategoryVO.toString());
		
		duplicate = docDAO.selectCheckCategory(docCategoryVO);
		
		if ( duplicate > 0 ) {
			isDuplicate = true;
		}
		
		// 중복이 없을 경우
		if ( !isDuplicate ) {
			int r = docDAO.insertDocCategory(docCategoryVO);
			
			if ( r != 1 ) {
				isInsert = false;
				logger.debug("[insertDocCategory] insert FAIL [" + docCategoryVO.toString() + "]");
			}
		}
		
		map.put("isDuplicate", isDuplicate);
		
		return map;
	}

	/**
	 * 카테고리 정보 불러오기
	 */
	@Override
	public List<DocCategoryVO> selectDocCategoryList() throws Exception {
		return docDAO.selectDocCategoryList();
	}

	/**
	 * 카테고리 셀렉트박스용 정보 불러오기
	 */
	@Override
	public List<DocCategoryVO> selectDocCategorySelectList() throws Exception {
		return docDAO.selectDocCategorySelectList();
	}
	
	/**
	 * 카테고리 정보 중복체크
	 */
	@Override
	public int selectCheckCategory(DocCategoryVO docCategoryVO) throws Exception {
		return docDAO.selectCheckCategory(docCategoryVO);
	}
	
	/**
	 * 카테고리 정보 수정
	 */
	@Override
	public void updateCategory(DocCategoryVO docCategoryVO) throws Exception {
		docDAO.updateCategory(docCategoryVO);
	}
	
	/**
	 * 문서 카테고리 명 수정
	 */
	@Override
	public void updateDocumentCategoryNM(DocCategoryVO docCategoryVO) throws Exception {
		docDAO.updateDocumentCategoryNM(docCategoryVO);
	}
	
	/**
	 * 문서 카테고리 명 수정
	 */
	@Override
	public boolean updateCategoryVolume(DocCategoryVO docCategoryVO, String cate) {
		return docDAO.updateCategoryVolume(docCategoryVO, cate);
	}

	@Override
	public void deleteCategoryDB(List<Map<String, String>> list) throws Exception {
		docDAO.deleteCategoryDB(list);
	}

	@Override
	public void deleteDocumentByCategory(List<Map<String, String>> list) throws Exception {
		docDAO.deleteDocumentByCategory(list);
	}
	
	public void deleteDocumentAttachByCategory(List<Map<String, String>> list) throws Exception {
		docDAO.deleteDocumentAttachByCategory(list);
	}

	@Override
	public boolean deleteCategoryVolume(List<Map<String, String>> catIdxList, String cate) {
		return docDAO.deleteCategoryVolume(catIdxList, cate);
	}

	@Override
	public int insertDocument(DocumentVO documentVO) throws Exception {
		return docDAO.insertDocument(documentVO);
	}
	
	@Override
	public boolean insertDocumentVolume() {
		return docDAO.insertDocumentVolume();
	}
	
	@Override
	public void deleteDocument(DocumentVO documentVO) throws Exception {
		docDAO.deleteDocument(documentVO);
	}

	@Override
	public int insertDocumentAttach(List<AttachVO> attachVOList) throws Exception {
		return docDAO.insertDocumentAttach(attachVOList);
	}

	@Override
	public List<DocumentVO> selectDocument(ParameterVO parameterVO) throws Exception {
		// 가져올 데이터의 시작점
		parameterVO.setOffSet((parameterVO.getPageNum() - 1) * 10);
		
		return docDAO.selectDocument(parameterVO);
	}

	@Override
	public int selectDocumentTotalCount() throws Exception {
		return docDAO.selectDocumentTotalCount();
	}

	/**
	 * 발전문서 파일리스트 검색 : 문서 ID로 호출
	 */
	@Override
	public List<Map<String, String>> selectDocumentFileListByDocID(String doc_idx) throws Exception {
		return docDAO.selectDocumentFileListByDocID(doc_idx);
	}
	
	/**
	 * 발전문서 파일리스트 검색 : 카테고리 ID로 호출
	 */
	@Override
	public List<Map<String, String>> selectDocumentFileListByCategory(List<Map<String, String>> list) throws Exception {
		return docDAO.selectDocumentFileListByCategory(list);
	}
	
	/**
	 * 발전문서 검색
	 */
	@Override
	public RestResultVO search(ParameterVO paramVO) throws Exception {
		RestResultVO resultVO = docDAO.search(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		
		return resultVO;
	}

	/**
	 * 발전문서 1건 조회
	 */
	@Override
	public RestResultVO detailSearch(ParameterVO paramVO) throws Exception {
		RestResultVO resultVO = docDAO.detailSearch(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		
		return resultVO;
	}

	/**
	 * 발전문서 수정 - 검색엔진 볼륨
	 */
	@Override
	public boolean updateDocumentVolume(DocumentVO documentVO, String cate) throws Exception {
		return docDAO.updateDocumentVolume(documentVO, cate);
	}	
	
	/**
	 * 발전문서 수정 - DB
	 */
	public void updateDocumentDB(DocumentVO documentVO) throws Exception {
		docDAO.updateDocumentDB(documentVO);
	}

	@Override
	public boolean deleteDocumentVolume(List<Map<String, String>> docIdxList, String cate) {
		return docDAO.deleteDocumentVolume(docIdxList, cate);
	}

	@Override
	public void deleteDocumentDB(List<Map<String, String>> list) throws Exception {
		docDAO.deleteDocumentDB(list);
	}

	@Override
	public void deleteDocumentAttachDB(List<Map<String, String>> list) throws Exception {
		docDAO.deleteDocumentAttachDB(list);
	}
}
