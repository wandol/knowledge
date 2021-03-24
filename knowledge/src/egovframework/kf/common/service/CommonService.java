package egovframework.kf.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.kf.common.vo.PagingVO;
import egovframework.kf.common.vo.RecentVO;
import egovframework.kf.common.vo.RepoInfoVO;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.rest.vo.RestVO;

public interface CommonService {
	
	///////////////////////////////////////////////////////////////////////////////////////// 옵션 조회
	
	public List<Map<String, String>> selectPowerCompList(Map map) throws Exception;	//발전사 조회
	
	public List<Map<String, String>> selectPowerStList(Map map) throws Exception;	//사업소 조회
	
	public List<Map<String, String>> selectStNoList(Map map) throws Exception;	//호기 조회
	
	public List<Map<String, String>> selectPartNameList(Map map, Map<String, String> cateInfoMap) throws Exception;	//진단부위 조회
	
	public List<Map<String, String>> selectPartMidList(Map map, Map<String, String> cateInfoMap) throws Exception;	//위치 조회
	
	public List<Map<String, String>> selectSymptomKwdList(Map map, Map<String, String> cateInfoMap) throws Exception;	//현상 조회
	
	public List<Map<String, String>> selectActionKwdList(Map map, Map<String, String> cateInfoMap) throws Exception;	//대책 조회
	
	public ParameterVO setParameter(Map map);
	
	public String getClassfication(String filePath, String fileName) throws Exception;	// 자동분류값 return
	
	public Map<String, Object> getClassficationNFileBody(String filePath, String fileName) throws Exception;	//자동분류, 파일원문 조회
	
	public RepoInfoVO setRepoInfo(List<Map<String, String>> list, String cate) throws Exception;
	
	public PagingVO setPagingModel(int pageNum, int totalCount);
	
	public List<RecentVO> getRecentByUser(int user_id) throws Exception;
	
	public Map<String, String> selectMergeImgCheck(Map map) throws Exception;
	
	public void updateFileNmAndSizeToMD5() throws Exception;
	
	public String mergeImg(List<String> imgList) throws Exception;
	
	public void fileDownload(Map<String, String> map, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public void allDownLoad(Map<String, String> map, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public void deleteAttachFile(List<Map<String,String>> list) throws Exception;
	
	public RestResultVO getVRPartList(Map<String, String> map) throws Exception; // VR PART 리스트 조회
}
