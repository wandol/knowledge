package egovframework.kf.analysis.service;

import java.util.List;
import java.util.Map;

import egovframework.kf.common.vo.UploadVO;

/**
 * 텍스트 분석을 위한 인터페이스
 * 
 * @author seunghee.kim
 * @since 2019.01.19
 */
public interface AnalysisService {
	
	////////////////////////////////////////////////////////////////////////////////////////// ENGINE
	// 파일의 자동분류값과 파일원문을 가져온다.
	public Map<String, String> getClassficationNFileBody(String filePath, String fileName) throws Exception;
	
	// 파일의 메타정보를 가져온다.
	public Map<String, String> getMetaInfo(String text, String fpath, String fname) throws Exception;
	
	// 파일의 원문을 문단으로 나누어 리스트로 전달.
	public List<String> getParagraph(String classfication, String filebody) throws Exception;
	
	// 파일의 문단을 문장으로 나누어 리스트로 전달.
//	public List<List<String>> getSentence(List<String> paraList, String filename) throws Exception;
	public List<String> getSentence(List<String> paraList, String filename) throws Exception;
	
	// 문장 List를 가져와 문장의 형태소를 분석한다.
	public List<Map<String, Object>> getPosTag(List sentenceList) throws Exception;
	
	// 문장 List를 가져와 시맨틱 패턴 매칭
	public Map<String, Object> getSymanticPattern(List sentenceList, String cate) throws Exception;
//	public List<Map<String, Object>> getSymanticPattern(List sentenceList, String cate) throws Exception;
	
	// 문장에서 Ner 추출
	public List<String> getNer(String fileName, List sentenceList, String classfication) throws Exception;
	
	// 색인 Rest API 실행
	public boolean restIndexingPost(String category, UploadVO uploadVO) throws Exception;
	
	// 색인 실패 시 
	public boolean deleteRecordEngine(UploadVO uploadVO) throws Exception;
	
	// 파일 분석
	public boolean analysis(Map<String, String> fileInfo, UploadVO uploadVO, String category) throws Exception;
	
	
	///////////////////////////////////////////////////////////////////////////////////////// DB
	// 업로드 된 파일이 중복인지 아닌지 체크 
	public List<Map<String, Object>> selectCheckDupMd5(UploadVO uploadVO) throws Exception;
	
	// 업로드 된 파일 insert
	public int insertReportInfo(UploadVO uploadVO) throws Exception;
	
	// 업로드 된 파일 자동분류필드 update
	public int updateReportInfo(Map<String, Object> map) throws Exception;
	
	// 업로드 된 파일 삭제
	public int deleteReportInfo(int idx) throws Exception;
}
