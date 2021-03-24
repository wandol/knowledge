package egovframework.kf.rest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.dao.SmartMatchDAO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.rest.dao.RestDAO;
import egovframework.kf.rest.service.RestService;
import egovframework.kf.rest.vo.RestVO;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("restService")
public class RestServiceImpl implements RestService {
	
	private static final Logger logger = LoggerFactory.getLogger(RestServiceImpl.class);
	
	@Resource(name="restDAO")
	private RestDAO restDAO;
	
	@Resource(name = "smartMatchDAO")
	private SmartMatchDAO smartMatchDAO;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	@Override
	public RestResultVO getKnowlegeList(RestVO restvo){
		// TODO Auto-generated method stub
		RestResultVO restResultvo;
		
		String fields ="MD5_KEY,GUBUN_NO,POWER_COMP_NM,POWER_ST_NM,ST_NO,REPORT_NM,REPORTER,PUBLISH_YM,PART2BODY";
		
		
		return restDAO.getKnowlegeList(restvo,fields);
	}

	@Override
	public RestResultVO getKnowlegeRetrieve(RestVO restvo) {
		// TODO Auto-generated method stub
		RestResultVO restResultvo;
		String fields ="MD5_KEY,GUBUN_NO,POWER_COMP_NM,POWER_ST_NM,ST_NO,REPORT_NM,REPORTER,PUBLISH_YM,PART2BODY";
		return restDAO.getKnowlegeRetieve(restvo,fields);
	}

	@Override
	public RestResultVO getKnowlegeRetrieveImages(RestVO restvo) {
		// TODO Auto-generated method stub
		RestResultVO restResultvo;
		String fields ="IMG_FILES";
		return restDAO.getKnowlegeRetieveImages(restvo,fields);
	}

	@Override
	public RestResultVO searchList(RestVO restvo) {
		// TODO Auto-generated method stub
		RestResultVO restResultvo;
		
		String fields ="MD5_KEY,POWER_COMP_NM,POWER_ST_NM,ST_NO,REPORT_NM,REPORTER,PUBLISH_YM,FILEBODY,AUTO_CLASSFICATION";
		
		return restDAO.searchList(restvo,fields);
	}

	@Override
	public RestResultVO searchRetrieve(RestVO restvo) {
		// TODO Auto-generated method stub
		RestResultVO restResultvo;
		
		String fields ="MD5_KEY,POWER_COMP_NM,POWER_ST_NM,ST_NO,REPORT_NM,REPORTER,PUBLISH_YM,FILEBODY,AUTO_CLASSFICATION";
		
		return restDAO.searchRetrieve(restvo,fields);
	}

	@Override
	public Map<String, Object> recommendList(RestVO restvo) {
		// TODO Auto-generated method stub

		Map<String, Object> result = new HashMap<String, Object>();
		String message = "";
		String code = "";
		int totalCount = 0;
		
		List<Map<String, String>> list;
		try {
			list = restDAO.recommendList(restvo);
					
			logger.debug("[call RestAPI] id List get Success");
			List<Map<String, String>> resultList = null;
			Map<String, String> map = null;
			
			if ( list.size() > 0 ) {
				resultList = new ArrayList<Map<String, String>>();
				
				for ( int i=0; i<list.size(); i++ ) {	
					map = new HashMap<String, String>();
					
					logger.debug("[call RestAPI] item : " + list.get(i).get("item"));
					map = smartMatchDAO.getSmartMatchFileInfo(list.get(i).get("item"));
					
					if ( map != null ) {
						resultList.add(map);
					} else {
						logger.debug("[call RestAPI] map is null");
					}
				}
				
				message = "OK";
				totalCount = list.size();
				result.put("rows", resultList);
			} else {
				message = "result size zero.";
				logger.debug("[call RestAPI] list size zero");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message = "Check Smart Engine Please.";
			e.printStackTrace();
		} finally {
			
			result.put("total", totalCount);
			result.put("message", message);
			return result;
		}
	}

	@Override
	public Map<String, Object> getRecentReportList(RestVO rest) {
		// TODO Auto-generated method stub
		
		Map<String, Object> result = new HashMap<String, Object>();
		String message = "";
		String code = "";
		int totalCount = 0;
		
		try {
			List<Map> reportList = new ArrayList<Map>();
			reportList = restDAO.getRecentReportList(rest);
			totalCount = restDAO.getRecentReportListTotalCount(rest);
			
			if ( reportList != null ) {
				message = "OK";
				result.put("rows", reportList);
			} else {
				message = "result is null";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message = "DB Error. DB Check Please.";
			e.printStackTrace();
		} finally {
			
			result.put("total", totalCount);
			result.put("message", message);
			return result;
		}
	}

	@Override
	public RestResultVO recommendRetrieve(RestVO restvo) {
		// TODO Auto-generated method stub
		RestResultVO restResultvo;
		
		String fields ="MD5_KEY,POWER_COMP_NM,POWER_ST_NM,ST_NO,REPORT_NM,REPO_KIND,DEPT,PUBLISH_YM,REPO_FILE_NM,FILEBODY,IMG_FILES,AUTO_CLASSFICATION";
		
		return restDAO.searchRetrieve(restvo,fields);
	}

	@Override
	public Map<String, Object> getPopularKnowledge(String cate) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		String message = "";
		String code = "";
		int totalCount = 0;
		
		try {
			
			List<Map<String, String>> popularList = restDAO.getPopularKnowledge(cate);
			
			if ( popularList != null ) {
				message = "OK";
				totalCount = popularList.size();
				result.put("rows", popularList);
			} else {
				message = "result is null.";
			}
		} catch (Exception e) {
			message = "Engine Error. Service Module Check(KSF).";
			// TODO: handle exception
		} finally {
			
			result.put("total", totalCount);
			result.put("message", message);
			return result;
		}
	}
	

	@Override
	public Map<String, String> getCommentList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getCommentList(String cate, String part_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, String> extractFileBody(String fileName) {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("file_nm", fileName);
		
		try {
			// 분석 API 전용 업로드 폴더
			String filePath = commonUtil.getFileDirectory() + "analysis";
			String fileBody = "";
			
			fileBody = restDAO.extractFileBody(filePath, fileName);
			
			if ( fileBody != null && fileBody.length() > 0 ) {
				resultMap.put("file_body", fileBody); 
			} else {
				resultMap.put("message", "No extract body.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resultMap.put("error", e.getMessage());
			e.printStackTrace();
		}
		
		return resultMap;
	}

	@Override
	public Map<String, Object> sentence(String text) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("text", text);
		
		try {
			List<String> sentenceList = restDAO.sentence(text);
			if ( sentenceList != null && sentenceList.size() > 0 ) {
				resultMap.put("sentence", sentenceList);
			} else {
				resultMap.put("message", "No sentence.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resultMap.put("error", e.getMessage());
			e.printStackTrace();
		} 
		return resultMap;
	}

	@Override
	public Map<String, Object> posTag(String text) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("text", text);
		
		try {
			String tagging = "";
			tagging = restDAO.posTag(text);
			
			if ( tagging != null && tagging.length() > 0 ) {
				resultMap.put("tagging", tagging);
			} else {
				resultMap.put("message", "No sentence.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resultMap.put("error", e.getMessage());
			e.printStackTrace();
		} 
		return resultMap;
	}
	
	@Override
	public Map<String, Object> parse(String text) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("text", text);
		
		try {
			String parse = "";
			parse = restDAO.parse(text);
			
			if ( parse != null && parse.length() > 0 ) {
				resultMap.put("parse", parse);
			} else {
				resultMap.put("message", "No sentence.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resultMap.put("error", e.getMessage());
			e.printStackTrace();
		} 
		return resultMap;
	}
	
	@Override
	public  Map<String, Object> symantic(String cate, String text) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("text", text);
		
		String lconf = "";
		
		if ( konanPropertiesService.getString("cate_boiler").equals(cate.toUpperCase())) {
			lconf = konanPropertiesService.getString("boiler_lconf");
		} else if ( konanPropertiesService.getString("cate_gt_turbine").equals(cate.toUpperCase())) {
			lconf = konanPropertiesService.getString("gt_lconf");
		} else if ( konanPropertiesService.getString("cate_st_turbine").equals(cate.toUpperCase())) {
			lconf = konanPropertiesService.getString("st_lconf");
		} else if ( konanPropertiesService.getString("cate_gen_prev").equals(cate.toUpperCase())) {
			lconf = konanPropertiesService.getString("gen_lconf");
		}
		
		try {
//			List<Map<String, String>> symanticList = restDAO.symantic(lconf, text);
			
			Map<String, Object> symantic = restDAO.symantic(text, lconf);
			logger.debug("symantic :: " + symantic.toString());
			
			if ( symantic != null ) { 
				resultMap.put("result", symantic);
			} else {
				resultMap.put("message", "No symantic.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resultMap.put("error", e.getMessage());
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> ner(String sentence, String classfication) {
		// TODO Auto-generated method stub
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> nerList = new ArrayList<String>();
		String message = "";
			
		try {
			nerList = restDAO.ner(sentence, classfication);
			
			if ( nerList.size() > 0 ) {
				List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
				
				for ( String ner : nerList ) {
					Map<String, String> nerInfo = new HashMap<String, String>();
					
					nerInfo.put("ner", ner.split("@@@@")[0]);
					nerInfo.put("sentence", ner.split("@@@@")[1]);
					
					mapList.add(nerInfo);
					nerInfo = null;
				}
				
				message = "success";
				
				resultMap.put("result", mapList);
				resultMap.put("totalCount", nerList.size());
			} else {
				message = "no Ner";
			}
			logger.debug("[getNer] nerList size :: " + nerList.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message = "[ERROR] Check NBC Module";
			e.printStackTrace();
		} finally {
			resultMap.put("message", message);
		}
		
		return resultMap;
	}

}
