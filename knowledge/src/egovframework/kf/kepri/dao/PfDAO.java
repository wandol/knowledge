package egovframework.kf.kepri.dao;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.DCUtil;
import egovframework.kf.dao.RestModule;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.data.SearchVO;
import egovframework.kf.kepri.vo.CommentVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : BoilerDAO.java
 * Description : 寃��깋���긽 Boiler 議고쉶
 *
 * Modification Information
 *
 *
 * @since 2019
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Repository
public class PfDAO {
	private static final Logger logger = LoggerFactory.getLogger(PfDAO.class);
	
	/** �뿏吏� 怨듯넻 �쑀�떥 */
	@Resource(name = "dcUtil")
	private DCUtil dcUtil;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** REST 紐⑤뱢 */
	@Resource(name = "restModule")
	private RestModule restModule;
	
	//@Autowired
	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;
		
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	

	/**
	 * 지식 DB탐색 검색
	 * 
	 * @param kwd
	 * @throws IOException 
	 */
	public RestResultVO pfSearch(ParameterVO paramVO) throws Exception {
		SearchVO searchVO = new SearchVO();		
		// 荑쇰━ �깮�꽦
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		String strNmFd = paramVO.getFields().isEmpty()? "search_pf_idx": paramVO.getFields();
		
		
		// 이미지 검색
		if ( "Y".equals(paramVO.getIsImg()) ) {
			query.append("IMG_FILES like '*'");
		} else {
//			if ( !"".equals(paramVO.getKwd()) && paramVO.getKwd() != null ) query.append(strNmFd + "='" + paramVO.getKwd() + "'");
			query = dcUtil.makeQuery(strNmFd, paramVO.getKwd(), "allword synonym", query, "AND");
		}
		
		// 諛쒖쟾�궗 select box
		if(!"all".equalsIgnoreCase(paramVO.getPowerComp())) {
			query = dcUtil.makeQuery( "POWER_COMP_NM" , paramVO.getPowerComp(), "", query, "AND");
		}
		
		// �궗�뾽�냼 select box
		if(!"all".equalsIgnoreCase(paramVO.getPowerSt())) {
			query = dcUtil.makeQuery( "POWER_ST_NM" , paramVO.getPowerSt(), "", query, "AND");
		}
		
		// �샇湲� select box
		if(!"all".equalsIgnoreCase(paramVO.getStNo())) {
			query = dcUtil.makeQuery( "ST_NO" , paramVO.getStNo(), "", query, "AND");
		}
		
		// 蹂닿퀬�꽌醫낅쪟 check box
		if(!"all".equalsIgnoreCase(paramVO.getRepoKind())) {
			String[] temp = paramVO.getRepoKind().split(",");
			query = dcUtil.makeINQuery("REPO_KIND", temp, false, query);
		}
		
		// 吏꾨떒遺��쐞 select box
		if(!"all".equalsIgnoreCase(paramVO.getPartName())) {
			
			String partName = "PARTNAME_KWD";
			if ( konanPropertiesService.getString("cate_boiler").equals(paramVO.getCategory()) ) {
				partName = "PARTNAME";
			}
			
			query = dcUtil.makeQuery( partName , paramVO.getPartName(), "", query, "AND");
		}
		
		// �넀�긽�쐞移� select box
		if(!"all".equalsIgnoreCase(paramVO.getPartMid())) {
			
			String partMid = "PART2NAME_KWD2";
			if ( konanPropertiesService.getString("cate_boiler").equals(paramVO.getCategory()) ) {
				partMid = "PART_MID";
			} else if ( konanPropertiesService.getString("cate_gen_prev").equals(paramVO.getCategory()) ) {
				partMid = "PART2NAME_KWD";
			}
			
			query = dcUtil.makeQuery( partMid , paramVO.getPartMid(), "", query, "AND");
		}
		
		// �넀�긽�쁽�긽 select box
		if(!"all".equalsIgnoreCase(paramVO.getSymptomKwd())) {
			query = dcUtil.makeQuery( "SYMPTOM_KWD" , paramVO.getSymptomKwd(), "", query, "AND");
		}
		
		// �넀�긽��梨� select box
		if(!"all".equalsIgnoreCase(paramVO.getActionKwd())) {
			query = dcUtil.makeQuery( "ACTION_KWD" , paramVO.getActionKwd(), "", query, "AND");
		}
		
		// �떆�뻾�씪�떆 
		if (!paramVO.getStartPublishYm().isEmpty() && !paramVO.getEndPublishYm().isEmpty()){
			query = dcUtil.makeRangeQuery("PUBLISH_YM", paramVO.getStartPublishYm(), paramVO.getEndPublishYm(), query) ;
		}

		//�젙�젹議곌굔	(理쒖떊�닚)
		if ("d".equals(paramVO.getSort())){
			query.append(" order by PUBLISH_YM desc");
		} else {
			if ( query.toString().indexOf("text_idx") > -1 ) {
				query.append(" order by $RELEVANCE desc");
			}
		}
		
		sbLog.append(  dcUtil.getLogInfo(commonUtil.null2Str(paramVO.getSiteNm(),"SITE"),
				"Problem focus" ,
				commonUtil.null2Str( paramVO.getUserId(),""), paramVO.getPfKwd(),
				paramVO.getPageNum(),
				false,
				paramVO.getSort(),
				commonUtil.null2Str( paramVO.getRecKwd(),"" )) );
	
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), paramVO.getRepoKind());
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		
		// 이미지 검색
		if ( "Y".equals(paramVO.getIsImg()) ) {
			searchVO.setFields("MD5_KEY,IMG_FILES,IMG_TXTS,GUBUN_NO,REPORT_NM,PART2BODY,FILENAME");
		} else {
			searchVO.setFields(cateInfoMap.get("pFfield"));
		}
		
		
		searchVO.setFrom(cateInfoMap.get("pFfrom"));
		
		searchVO.setHilightTxt(cateInfoMap.get("pFhilight"));
		
		if ( !"".equals(query.toString()) && query.toString() != null ) {
			searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
			searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
		}
	
		String postParamData = dcUtil.getParamPostDataPF(paramVO, searchVO);
		logger.debug("QUERY pf : " + query.toString());
		logger.debug("RESTURL pf : " + postParamData);
		
		logger.debug("=================================================");
		logger.debug("resturl :: "+searchVO.getUrl()+"?"+postParamData);
		logger.debug("=================================================");
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields()); // Post 요청
		logger.debug("[pfSearch] success :: " + success);
		
		//초기화
		if ( query.length() > 0 ) query.charAt(0);
		if ( sbLog.length() > 0 ) sbLog.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;		
	}
	
	public RestResultVO PfImageSearch(ParameterVO paramVO) throws Exception {
		
		SearchVO searchVO = new SearchVO();
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		
		query.append("IMG_FILES like '*'");
		
		Map<String, String> cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), "");
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields("MD5_KEY,IMG_FILES,IMG_TXTS,GUBUN_NO,REPORT_NM,PART2BODY,FILENAME");
		searchVO.setFrom(cateInfoMap.get("pFfrom"));
		searchVO.setHilightTxt(cateInfoMap.get("pFhilight"));
		
		if ( !"".equals(query.toString()) && query.toString() != null ) {
			searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
			searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
		}
	
		String postParamData = dcUtil.getParamPostDataPF(paramVO, searchVO);
		logger.debug("QUERY pf : " + query.toString());
		logger.debug("RESTURL pf : " + postParamData);
		
		logger.debug("=================================================");
		logger.debug("resturl :: "+searchVO.getUrl()+"?"+postParamData);
		logger.debug("=================================================");
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields()); // Post 요청
		logger.debug("[pfSearch] success :: " + success);
		
		//초기화
		if ( query.length() > 0 ) query.charAt(0);
		if ( sbLog.length() > 0 ) sbLog.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;		
	}
	
	public RestResultVO detail(String cate, ParameterVO paramVO)  throws Exception {
		
		SearchVO searchVO = new SearchVO();	
		StringBuffer query = new StringBuffer();
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		
		logger.debug("[detail] cate : " + cate + ", id : " + paramVO.getKwd() + ", gubunNO : " + paramVO.getGubun_no());
		paramVO.setPageNum(1);
		paramVO.setPageSize(100);
		
		// 카테고리 정보 조회
		cateInfoMap = commonUtil.getCategoryInfo(cate, "");

		query = dcUtil.makeQuery( cateInfoMap.get("idx") , paramVO.getKwd(), "", query, "AND");
		query = dcUtil.makeQuery( "GUBUN_NO" , paramVO.getGubun_no(), "", query, "AND");
		
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(cateInfoMap.get("pFfield"));
		searchVO.setFrom(cateInfoMap.get("pFfrom"));
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		searchVO.setLogInfo("");		
		
		String postParamData = dcUtil.getParamPostData(paramVO, searchVO);
		logger.debug("QUERY pfGetDetail : " + query.toString());
		logger.debug("RESTURL pfGetDetail : " + postParamData);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields());

		// 2019.11.26 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}

	public String getSymData(ParameterVO paramVO) {
		// TODO Auto-generated method stub
		String s="\nmembrane_洹좎뿴,3\n留덈え,3\nlug_�넀�긽,2\nmembrane_�넀�긽,2\ngrouping_遺��쟻�빀,1\nlug_洹좎뿴,1\nlug_蹂��삎,1\nnozzle_�넀�긽,1\nplate_蹂��삎,1\nprotector_留덈え,1";
		//String ksearch_addr = konanPropertiesService.getString("kqlUrl");
		String kqlAddr = konanPropertiesService.getString("kqlUrl");
		String input_idx = paramVO.getPfKwd();
		String query_str="";
		String rest_result="";
		try {
			query_str += "#include '../script/boiler/getSymData.k'\n";
			query_str += "void main(){\n";
				query_str +="\tprintz symdata('"+input_idx+"');\n";
			query_str += "}\n";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.TEXT_PLAIN);
			HttpEntity param= new HttpEntity(query_str, headers);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders resheaders;
			restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
			//rest_result = miningDao.callKqlCommand(kqlAddr,query_str);
			//rest_result = restTemplate.postForObject(kqlAddr, param, String.class);
			HttpEntity<String> response = restTemplate.exchange(kqlAddr, HttpMethod.POST, param, String.class);
			rest_result= response.getBody();
			resheaders = response.getHeaders();
			s = rest_result.trim();
			logger.debug("Kql getSymData : " + s);
			
		}catch(Exception e){
			
		}
		
		return s;
	}
	
	public RestResultVO getGroupByKwdData(ParameterVO paramVO, String groupByFd, String orderByFd, String orderByVal, int limit) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("[getGroupByKwdData] START");
		
		SearchVO searchVO = new SearchVO();		
		// 荑쇰━ �깮�꽦
		StringBuffer query = new StringBuffer();
		List<String> queryList = new ArrayList<String>();
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), "");
		String field = groupByFd + ",count(*)";
		
		if ( paramVO.getPowerComp() != null ) {
			String powerComp = paramVO.getPowerComp();
			if (powerComp != null && !"".equals(powerComp) && !"all".equals(powerComp) ) queryList.add("POWER_COMP_NM ='" + powerComp + "'");
		}
		
		if ( paramVO.getPowerSt() != null ) {
			String powerSt =paramVO.getPowerSt();
			if (powerSt != null && !"".equals(powerSt) && !"all".equals(powerSt) ) queryList.add("POWER_ST_NM ='" + powerSt + "'");
		}
		
		if ( paramVO.getStNo() != null ) {
			String stNo = paramVO.getStNo();
			if (stNo != null && !"".equals(stNo) && !"all".equals(stNo) ) queryList.add("ST_NO ='" + stNo + "'");
		}
		
		if ( paramVO.getPartName() != null ) {
			String partName = paramVO.getPartName();
			if (partName != null && !"".equals(partName) && !"all".equals(partName) ) queryList.add(cateInfoMap.get("partName") + " ='" + partName + "'");
		}
		
		if( queryList.size() > 0 ){	
			query = dcUtil.makeGroupByQuery( queryList, groupByFd, orderByFd, orderByVal );
		}
		
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields("*");
		searchVO.setFrom(cateInfoMap.get("pFfrom"));
		searchVO.setHilightTxt(cateInfoMap.get("pFhilight"));
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		searchVO.setLogInfo("");
		
		String restUrl = dcUtil.getParamGroupByData(searchVO, limit);
		logger.debug("postUrl :: " + restUrl);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchGroupBy(restUrl, restVO, field); //post 諛⑹떇 �샇異�
		
		// 2019.11.26 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}
	
	public RestResultVO getGroupByDepthData(ParameterVO paramVO, String groupByFd, String orderByFd, String orderByVal, int limit) throws Exception {
		// TODO Auto-generated method stub
		SearchVO searchVO = new SearchVO();		
		StringBuffer query = new StringBuffer();
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		String field = groupByFd + ",count(*)";
		
		cateInfoMap = commonUtil.getCategoryInfo(paramVO.getCategory(), "");
		
		String powerComp = paramVO.getPowerComp();
		String powerSt = paramVO.getPowerSt();
		String stNo = paramVO.getStNo();
		String partName = paramVO.getPartName();
		
		List<String> queryList = new ArrayList<String>();
		
		if (powerComp != null && !"".equals(powerComp) && !"all".equals(powerComp) ) queryList.add("POWER_COMP_NM ='" + powerComp + "'");
		if (powerComp != null && !"".equals(powerSt) && !"all".equals(powerSt) ) queryList.add("POWER_ST_NM ='" + powerSt + "'");
		if (powerComp != null && !"".equals(stNo) && !"all".equals(stNo) ) queryList.add("ST_NO ='" + stNo + "'");
		if (powerComp != null && !"".equals(partName) && !"all".equals(partName) ) queryList.add(cateInfoMap.get("partName") + " ='" + partName + "'");
		
		// make Query
		if(paramVO.getPartName() != null){
			query = dcUtil.makeGroupByQuery( queryList, groupByFd, orderByFd, orderByVal );
		}
		
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields("*");
		searchVO.setFrom(cateInfoMap.get("pFfrom"));
		searchVO.setHilightTxt(cateInfoMap.get("pFhilight"));
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		searchVO.setLogInfo("");
		
		String restUrl = dcUtil.getParamGroupByData(searchVO, limit);
		logger.debug("postUrl :: " + restUrl);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchDepthGroupBy(restUrl, restVO, field); //post 諛⑹떇 �샇異�
		
		// 2019.11.26 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}
	
	/**
	 * Problem 분석서비스에서 현상을 클릭 했을 때 현상과 관련 된 대책을 가져온다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public RestResultVO getSymptomToAction(Map map, String groupField) throws Exception {
		SearchVO searchVO = new SearchVO();		
		StringBuffer query = new StringBuffer();
		Map<String, String> cateInfoMap = new HashMap<String, String>();
		String field = groupField + ",count(*)";
		
		String groupByFd = groupField;
		String orderByFd = konanPropertiesService.getString("groupByCount");
		String orderByVal = konanPropertiesService.getString("orderByValueDESC");
		int limit = konanPropertiesService.getInt("maxLimit100");
		
		String powerComp = (String) map.get("powerComp");
		String powerSt = (String) map.get("powerSt");
		String stNo = (String) map.get("stNo");
		String partName = (String) map.get("partName");
		String keyword = (String) map.get("keyword");
		String gubun = (String) map.get("gubun");
		
		cateInfoMap = commonUtil.getCategoryInfo(map.get("cate").toString(), "");
		
		List<String> queryList = new ArrayList<String>();
		
		if (powerComp != null && !"".equals(powerComp) && !"all".equals(powerComp) ) queryList.add("POWER_COMP_NM ='" + powerComp + "'");
		if (powerComp != null && !"".equals(powerSt) && !"all".equals(powerSt) ) queryList.add("POWER_ST_NM ='" + powerSt + "'");
		if (powerComp != null && !"".equals(stNo) && !"all".equals(stNo) ) queryList.add("ST_NO ='" + stNo + "'");
		if (partName != null && !"".equals(partName) && !"all".equals(partName) ) queryList.add(cateInfoMap.get("partName") + " ='" + partName + "'");
		if ("ACTION".equals(gubun) ) {
			queryList.add("ACTION_KWD ='" + keyword + "'");
		} else {
			queryList.add("SYMPTOM_KWD ='" + keyword + "'");
		}
		
		query = dcUtil.makeGroupByQuery( queryList, groupByFd, orderByFd, orderByVal );
		
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields("*");
		searchVO.setFrom(cateInfoMap.get("pFfrom"));
		searchVO.setHilightTxt(cateInfoMap.get("pFhilight"));
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		searchVO.setLogInfo("");
		
		String restUrl = dcUtil.getParamGroupByData(searchVO, limit);
		logger.debug("postUrl :: " + restUrl);
		
		RestResultVO restVO = new RestResultVO();
		boolean success = restModule.restSearchDepthGroupBy(restUrl, restVO, field); //post 諛⑹떇 �샇異�
		
		// 2019.11.26 changho.lee
		// query, sbLog가 비어있을 때 에러 방지
		if ( query.length() > 0 ) query.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;	
	}
	
	/**
	 * Problem Focus의 데이터에 코멘트,점수를 기록한다.
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public int setPFComment(CommentVO comment) throws Exception {
		return sqlSession.insert("CommentMapper.insertCommentHistory", comment);
	}
	
	/**
	 * Problem Focus 게시글에 등록 된 코멘트의 갯수를 return해준다.
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public int getPFCommentCount(CommentVO comment) throws Exception {
		return sqlSession.selectOne("CommentMapper.selectCommentCount", comment);
	}
	
	/**
	 * Problem Focus 게시글에 등록 된 코멘트 List를 return해준다.
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public List<CommentVO> getPFCommentList(CommentVO comment) throws Exception {
		return sqlSession.selectList("CommentMapper.selectCommentList", comment);
	}
	
	
}
