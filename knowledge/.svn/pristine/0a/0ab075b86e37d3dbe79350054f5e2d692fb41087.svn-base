package egovframework.kf.kepri.service;

import java.util.List;
import java.util.Map;

import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.kepri.vo.CommentVO;

/**
 * problem focus 검색하기 위한 서비스 인터페이스
 * 
 * @author seunghee.kim
 * @since 2019.01.19
 */
public interface PfService {
	
	/**
	 * problem focus를 검색한다.
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	
	public RestResultVO PfSearch(ParameterVO paramVO) throws Exception;

	public RestResultVO PfImageSearch(ParameterVO paramVO) throws Exception;
	
	public RestResultVO detail(ParameterVO paramVO, String cate)  throws Exception;
	
	public List<CommentVO> detailComment(String category, String idx) throws Exception;
	
	public String getSymData(ParameterVO paramVO)  throws Exception;
	
	public RestResultVO getSymptomKwdGroupBy(ParameterVO paramVO) throws Exception;
	
	public RestResultVO getActionKwdGroupBy(ParameterVO paramVO) throws Exception;
	
	public RestResultVO getPublishYMGroupBy(ParameterVO paramVO) throws Exception;
	
	public RestResultVO getDepthGroupBy(ParameterVO paramVO, String depth) throws Exception;
	
	public int setPFComment(CommentVO comment) throws Exception;
	
	public List<CommentVO> getPFCommentList(CommentVO comment) throws Exception;
	
	public Map<String, String> changeFileBodyImgToText(Map<String,String> data, String cate) throws Exception;
	
	public RestResultVO getSymptomToAction(Map map) throws Exception;
}
