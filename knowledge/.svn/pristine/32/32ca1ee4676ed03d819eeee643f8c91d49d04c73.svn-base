package egovframework.kf.analysis.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.DCUtil;
import egovframework.kf.common.vo.UploadVO;
import egovframework.kf.dao.RestModule;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : AnalysisDAO.java
 * Description : 텍스트 분석용 API 호출
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 * 2020년 08월  31일       이창호           최초 작성
 *
 * @since 2020년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Repository
public class AnalysisDAO {
	private static final Logger logger = LoggerFactory.getLogger(AnalysisDAO.class);
	
	/** REST 모듈 */
	@Resource(name = "restModule")
	private RestModule restModule;
	
	/** 엔진 공통 유틸 */
	@Resource(name = "dcUtil")
	private DCUtil dcUtil;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	private EgovPropertyService konanPropertiesService;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////// ENGINE(KA5,FLT) 관련
	/**
	 * 파일의 원문을 추출하기 위해 텍스트 필터를 호출한다. 
	 * 추출한 원문은 다시 자동분류 값을 얻기 위해 엔진을 RestAPI로 호출한다. 
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getClassficationNFileBody(String filePath, String fileName) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String, String> map = new HashMap<String, String>();
		String fltUrl = konanPropertiesService.getString("fltUrl");	
		String classfication = "";
		
		// 추출된 파일내용
		map = dcUtil.getTextFilter(fltUrl, filePath, fileName);
		logger.debug("[getClassficationNFileBody] data :: " + map.get("fileBody"));
		
		if ( map.containsKey("fileBody") ) {
			classfication = restModule.restClassfication(map.get("fileBody"));	
			if ( "fail".equals(classfication)) {
				map.put("message", "Check Nbc Module. Is Learn?");
			}
		}
		map.put("classfication", classfication);		
		
		return map;
	}
	
	/**
	 * 파일의 메타정보를 추출하기 위해 엔진을 restAPI로 호출한다.
	 * @param text
	 * @param fpath
	 * @param fname
	 * @return
	 * @throws Exception
	 */
	public String getMetaInfo(String text, String fpath, String fname) throws Exception {
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		StringBuffer postData = new StringBuffer();
		
		text = text.replaceAll("\\(", "\\\\(");
		text = text.replaceAll("\\)", "\\\\)");
		text = text.replaceAll("\\\"", "\\\\\"");
		
		// 검색엔진 Update문
		postData.append("#include \"../script/test/get_meta.k\"");
		postData.append("void main()");
		postData.append("{");
		postData.append("extract_metainfo(\"" + text + "\", \"" + fpath + "\", \"" + fname + "\");");
		postData.append("}");
		
		StringBuffer sb = restModule.restPost(kqlUrl, postData.toString()  , "");
		logger.debug("[getMetaInfo] get MetaInfo :: " + sb.toString());
		
		return sb.toString();
	}
	
	/**
	 * 파일 원문과 분류 정보를 가져와 원문의 문단을 분리한다. 
	 * @param classfication
	 * @param filebody
	 * @return
	 * @throws Exception
	 */
	public List<String> getParagraph(String classfication, String filebody) throws Exception {
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		StringBuffer postData = new StringBuffer();
		List<String> paraList = new ArrayList<String>();
		
		filebody = filebody.replaceAll("\\(", "\\\\(");
		filebody = filebody.replaceAll("\\)", "\\\\)");
		filebody = filebody.replaceAll("\\\"", "\\\\\"");
		
		// 문단 나누는 k파일 호출
		postData.append("#include \"../script/test/get_para_sep.k\"");
		postData.append("void main()");
		postData.append("{");
		postData.append("paragraph(\"" + filebody + "\",\"" + classfication + "\");");
		postData.append("}");
		
		StringBuffer sb = restModule.restPost(kqlUrl, postData.toString()  , "");
		logger.debug("[getParagraph] get Paragraph :: " + sb.toString());
		
		if ( !"".equals(sb.toString()) ) {
			if ( sb.toString().indexOf("##@@##") > -1 ) {
				for ( String paragraph : sb.toString().split("##@@##") ) {
					paraList.add(paragraph.split("@@")[0]);
				}
			} else {
				paraList.add(sb.toString());
			}
		}
		
		return paraList;
	}
	
	/**
	 * 파일명과 원문을 가져와 문장으로 분리한다.
	 * 파일명을 가져오는 이유는 pdf일때와 다른 확장자일때 차이가 있기 때문에 파일명을 파라메터로 넘겨준다.
	 * @param text
	 * @param fileNm
	 * @return
	 * @throws Exception
	 */
	public List<String> getSentence(String text, String fileNm) throws Exception {
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		StringBuffer postData = new StringBuffer();
		
		text = text.replaceAll("\\(", "\\\\(");
		text = text.replaceAll("\\)", "\\\\)");
		text = text.replaceAll("\\\"", "\\\\\"");
		
		// 검색엔진 Update문
		postData.append("#include \"../script/test/get_sentence.k\"");
		postData.append("void main()");
		postData.append("{");
		postData.append("extract_sentence(\"" + text + "\",\"" + fileNm + "\");");
		postData.append("}");
		
		StringBuffer sb = restModule.restPost(kqlUrl, postData.toString()  , "");
		
		logger.debug("[getSentence] get Sentence :: " + sb.toString());
		
		String temp = sb.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("'", "").replaceAll("\\\\", "");
		
		List<String> list = new ArrayList<String>();
		
		if ( temp.indexOf(",") > -1) {
			String[] tempArr = temp.split(",");
			
			for ( int i=0; i<tempArr.length; i++ ) {
				list.add(tempArr[i]);
			}
		} else {
			list.add(temp);
		}
		
		return list;
	}
	
	/**
	 * 문장을 받아와 문장의 형태소를 분석한다.
	 * @param sentence
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPosTag(String sentence) throws Exception {
		return restModule.restSearchKsmPosTag(sentence);
	}
	
	/**
	 * 문장을 받아와 시맨틱패턴 매칭을 한다.
	 * @param text
	 * @param lconf
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSymanticPattern(String text, String lconf) throws Exception {
		return restModule.restSearchKsmExplain3(text, lconf);
	}
	
	/**
	 * 문장에서 Ner을 추출한다
	 * @param fileName
	 * @param sentence
	 * @param classfication
	 * @return
	 * @throws Exception
	 */
	public List<String> getNer(String fileName, String sentence, String classfication) throws Exception {
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		List<String> nerResult = new ArrayList<String>();
		StringBuffer postData = new StringBuffer();
		
		sentence = sentence.replaceAll("\\(", "\\\\(");
		sentence = sentence.replaceAll("\\)", "\\\\)");
		sentence = sentence.replaceAll("\\\"", "\\\\\"");
		
		// Ner 추출
		postData.append("void main()");
		postData.append("{");
		// 5.0.0버전부터는 권한체크를 하기 때문에 아래와 같이 kql을 날릴때 권한 설정을 해주어야 한다.
		postData.append("setenv('privilege', 'admin');");
		postData.append("run ../script/test/get_ner.k main('" + fileName + "','" + sentence + "','" + classfication + "');");
		postData.append("printf('%s', KQL_STATEMENT_OUT);");
		postData.append("}");
		
		StringBuffer sb = restModule.restPost(kqlUrl, postData.toString()  , "");
		logger.debug("[getNer] get Ner :: " + sb.toString());
		
		if ( !"error".equals(sb.toString()) ) {
			if ( sb.toString().indexOf("##@@##") > -1 ) {
				for ( String ner : sb.toString().split("##@@##") ) {
					nerResult.add(ner);
				}
			} else {
				nerResult.add(sb.toString());
			}
		}
		
		return nerResult;
	}
	
	/**
	 * 색인 Rest API실행.
	 * 실행 스크립트가 각각 다르다.
	 * @param category
	 * @param uploadVO
	 * @return
	 * @throws Exception
	 */
	public boolean restIndexingPost(String category, UploadVO uploadVO) throws Exception {
		// TODO Auto-generated method stub
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		
		String bulkFile = "";
		String troubleStr = "고장진단";
		
		
		logger.debug("[restIndexingPost] category : " + category);
		logger.debug("[restIndexingPost] repokind : " + uploadVO.getUpLoadRepoKind() + ", repokind2 : " + uploadVO.getUpLoadRepoKind2());
		
		// 실행 스크립트 셋팅
		if ( konanPropertiesService.getString("cate_boiler").equals(category) ) {
			if ( troubleStr.equals(uploadVO.getUpLoadRepoKind()) ) bulkFile = "boiler/bulk_trouble/bulk_max.k;";	//보일러 고장진단
			else bulkFile = "boiler/bulk/bulk_max.k;";	//보일러 정밀진단
		}
		else if ( konanPropertiesService.getString("cate_gt_turbine").equals(category) ) {
			if ( troubleStr.equals(uploadVO.getUpLoadRepoKind2()) ) bulkFile = "gs_turbin/bulk_trouble/bulk_max.k;";	//가스터빈 고장진단
			else bulkFile = "gs_turbin/bulk/bulk_max.k;";	//가스터빈 정밀진단
		}
		else if ( konanPropertiesService.getString("cate_st_turbine").equals(category) ) {
			if ( troubleStr.equals(uploadVO.getUpLoadRepoKind2()) ) bulkFile = "st_turbin/bulk_trouble/bulk_max.k;";	//증기터빈 고장진단
			else bulkFile = "st_turbin/bulk/bulk_max.k;";	//증기터빈 정밀진단
		}
		else if ( konanPropertiesService.getString("cate_gen_prev").equals(category) ) bulkFile = "gen_prev/bulk/bulk_max.k;";	//발전기 예방진단
		else if ( konanPropertiesService.getString("cate_gen_ins").equals(category) ) bulkFile = "gen_ins/bulk/bulk_max.k;";	//발전기 절연진단
		else if ( konanPropertiesService.getString("cate_gen_kepri").equals(category) ) bulkFile = "kepri/bulk/bulk_max.k;";	//발전기 누설흡습
		
		// 셋팅 된 스크립트가 있을 경우에만
		if ( !"".equals(bulkFile) ) {
			try {
				StringBuffer postData = new StringBuffer();
				
				postData.append("void main()");
				postData.append("{");
				// 5.0.0버전부터는 권한체크를 하기 때문에 아래와 같이 kql을 날릴때 권한 설정을 해주어야 한다.
				postData.append("setenv('privilege', 'admin');");
				postData.append("run ../script/" + bulkFile);
				postData.append("printf('%s', KQL_STATEMENT_OUT);");
				postData.append("}");
				logger.debug("[restIndexingPost] postData :: " + postData.toString());
				
				StringBuffer sb = restModule.restPost(kqlUrl, postData.toString(), "");
				logger.debug("[restIndexingPost] call rest API result :: " + sb.toString());
				
				if ( "error".equals(sb.toString()) ) return false;
				else return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		} else {
			logger.debug("[restIndexingPost] category not exist.");
			return false;
		}
		
	}
	
	public boolean deleteRecordEngine(UploadVO uploadVO) throws Exception {
		logger.debug("[deleteRecordEngine] ####################### ENGINE DELETE START #######################");
		logger.debug("[deleteRecordEngine] >>>>>>>>>>>>>>>>>>>>>>> REPO_IDX = [" + uploadVO.getUploadIdx() + "]");
		logger.debug("[deleteRecordEngine] >>>>>>>>>>>>>>>>>>>>>>> CATEGORY = [" + uploadVO.getUpLoadPowerType() + "]");
		logger.debug("[deleteRecordEngine] >>>>>>>>>>>>>>>>>>>>>>> REPO_KIND = [" + uploadVO.getUpLoadRepoKind() + "]");
		
		String kqlUrl = konanPropertiesService.getString("kqlUrl");
		String idxName = "REPO_IDX";
		
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(uploadVO.getUpLoadPowerType(), uploadVO.getUpLoadRepoKind());
		logger.debug("[deleteRecordEngine] >>>>>>>>>>>>>>>>>>>>>>> VOLUME = [" + cateInfoMap.get("realVolume") + "]");
		logger.debug("[deleteRecordEngine] >>>>>>>>>>>>>>>>>>>>>>> TABLE = [" + cateInfoMap.get("table") + "]");
		try {
			StringBuffer postData = new StringBuffer();
			
			// 검색엔진 DELETE문
			postData.append("void main()");
			postData.append("{");
			postData.append("setenv('privilege', 'admin');");
			postData.append("use volume " + cateInfoMap.get("realVolume") + ";");
			// 기본 테이블 삭제
			postData.append("delete from " + cateInfoMap.get("table") + " where " + idxName + "=" + uploadVO.getUploadIdx() + "; " );
			
			if ( cateInfoMap.get("pFtable") != null && !"".equals(cateInfoMap.get("pFtable")) ) {
				logger.debug("[deleteRecordEngine] >>>>>>>>>>>>>>>>>>>>>>> PF TABLE = [" + cateInfoMap.get("pFtable") + "]");
				// 분석 테이블 삭제
				postData.append("delete from " + cateInfoMap.get("pFtable") + " where " + idxName + "=" + uploadVO.getUploadIdx() + "; " );
			}
			postData.append("printf('%s', KQL_STATEMENT_OUT);");
			postData.append("}");
			
			logger.debug("[deleteRecordEngine] postData :: " + postData.toString());
			
			StringBuffer sb = restModule.restPost(kqlUrl, postData.toString(), "");
			
			logger.debug("[deleteRecordEngine] call rest API result :: " + sb.toString());
			logger.debug("[deleteRecordEngine] ####################### ENGINE DELETE END #######################");
			
			if ( "error".equals(sb.toString()) ) return false;
			else return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////// DB(Mysql) 관련
	/**
	 * Md5key를 조건으로 DB에 중복 된 데이터가 있는지 확인
	 * @param md5
	 * @return REPO_IDX, count(*)
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectCheckDupMd5(UploadVO uploadVO) throws Exception {
		return sqlSession.selectList("FileMapper.selectCheckMD5", uploadVO);
	}
	
	/**
	 * 업로드 파일정보 DB Insert
	 * @param uploadVO
	 * @return
	 * @throws Exception
	 */
	public int insertReportInfo(UploadVO uploadVO) throws Exception {
		logger.debug("[insertReportInfo] insertReportInfo START");
		
		if ( konanPropertiesService.getString("cate_boiler").equals(uploadVO.getUpLoadPowerType()) ) {
			uploadVO.setCate("boiler");
			uploadVO.setUpLoadRepoKind2("");
		}else if ( konanPropertiesService.getString("cate_gen_prev").equals(uploadVO.getUpLoadPowerType()) ){
			uploadVO.setCate("gen");
			uploadVO.setUpLoadRepoKind("예방진단");
			uploadVO.setUpLoadRepoKind2("");
			uploadVO.setUpLoadPowerType("예방진단");
		}else if ( konanPropertiesService.getString("cate_gen_ins").equals(uploadVO.getUpLoadPowerType()) ){
			uploadVO.setCate("gen");
			uploadVO.setUpLoadRepoKind("절연진단");
			uploadVO.setUpLoadRepoKind2("");
			uploadVO.setUpLoadPowerType("절연진단");
		}else if ( konanPropertiesService.getString("cate_gen_kepri").equals(uploadVO.getUpLoadPowerType()) ){
			uploadVO.setCate("gen");
			uploadVO.setUpLoadRepoKind("누설흡습");
			uploadVO.setUpLoadRepoKind2("");
			uploadVO.setUpLoadPowerType("누설흡습");
		}else {

			uploadVO.setCate("turbin");
			String troubleStr = "고장진단";
			
			if ( troubleStr.equals(uploadVO.getUpLoadRepoKind()) ) {
				uploadVO.setUpLoadRepoKind2("고장진단");
			} else {
				uploadVO.setUpLoadRepoKind2("정밀진단");
			}
			
			if ( konanPropertiesService.getString("cate_st_turbine").equals(uploadVO.getUpLoadPowerType()) ) {
				uploadVO.setUpLoadRepoKind("증기터빈");
				uploadVO.setUpLoadPowerType("증기터빈");
			} else {
				uploadVO.setUpLoadRepoKind("가스터빈");
				uploadVO.setUpLoadPowerType("가스터빈");
			}
		}
		logger.debug("[insertReportInfo] uploadVO :: " + uploadVO.toString());
		
		return sqlSession.insert("FileMapper.insertReportInfo", uploadVO);
	}
	
	/**
	 *  Name : updateReportInfo
	 *  Comment : 
	 */
	public int updateReportInfo(Map<String, Object> map) throws Exception {
		return sqlSession.update("FileMapper.updateReportInfo", map);
	}
	
	/**
	 *  Name : deleteReportInfo
	 *  Comment : category정보와 idx를 받아 DB에서 해당 정보 Delete.
	 */
	public int deleteReportInfo(int idx) throws Exception {
		return sqlSession.delete("FileMapper.deleteReportInfo", idx);
	}
	
	/**
	 * Insert한 정보를 DB에서 조회한다.(확인용)
	 * @param cate
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectReportInfo(int idx) throws Exception {	
		return sqlSession.selectOne("FileMapper.selectReportInfo", idx);
	}
}
