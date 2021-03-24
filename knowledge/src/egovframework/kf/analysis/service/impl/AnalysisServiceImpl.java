package egovframework.kf.analysis.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.kf.analysis.dao.AnalysisDAO;
import egovframework.kf.analysis.service.AnalysisService;
import egovframework.kf.common.service.CommonService;
import egovframework.kf.common.vo.UploadVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("analysisService")
public class AnalysisServiceImpl extends EgovAbstractServiceImpl implements AnalysisService {
	
	@Resource(name = "analysisDAO")
	private AnalysisDAO analysisDAO;
	
	/** Common Service */
	@Resource(name = "commonService")
	private CommonService commonService;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	private EgovPropertyService konanPropertiesService;
	
	private static final Logger logger = LoggerFactory.getLogger(AnalysisService.class);
	
	///////////////////////////////////////////////////////////////////////////////////////////// ENGINE(KA5,FLT) 관련
	@Override
	public Map<String, String> getClassficationNFileBody(String filePath, String fileName) throws Exception {
		// TODO Auto-generated method stub
		return analysisDAO.getClassficationNFileBody(filePath, fileName);
	}
	
	@Override
	public Map<String, String> getMetaInfo(String text, String fpath, String fname) throws Exception {
		// TODO Auto-generated method stub
	
		logger.debug("[getMetaInfo] getMetaInfo");
		Map<String, String> resultMap = new HashMap<String, String>();
		String metaInfo = analysisDAO.getMetaInfo(text, fpath, fname);
		logger.debug("[getMetaInfo] metaInfo :: " + metaInfo);
		
		String report_nm = "";
		String test_day = "";
		String tester_dept = "";
		String tester = "";
		String company = "";
		String power_st = "";
		String st_no = "";
	
		if ( metaInfo.indexOf("|") > -1 && !"||||||".equals(metaInfo)) {
			String[] metaInfoList = metaInfo.split("\\|");

			logger.debug("[getMetaInfo] meta setting start");
			report_nm = metaInfoList[0];
			test_day = metaInfoList[1];
			tester_dept = metaInfoList[2];
			tester = metaInfoList[3];
			company = metaInfoList[4];
			power_st = metaInfoList[5];
			st_no = metaInfoList[6];
			logger.debug("[getMetaInfo] meta setting end");
			
			metaInfoList = null;
		}
		
		resultMap.put("report_nm", report_nm);
		resultMap.put("test_day", test_day);
		resultMap.put("tester_dept", tester_dept);
		resultMap.put("tester", tester.replaceAll("\\\\", ""));
		logger.debug("[getMetaInfo] meta replace");
		resultMap.put("company", company);
		resultMap.put("power_st", power_st);
		resultMap.put("st_no", st_no);
		logger.debug("[getMetaInfo] meta end");
		
		return resultMap;
	}
	
	@Override
	public List<String> getParagraph(String classfication, String filebody) throws Exception {
		return analysisDAO.getParagraph(classfication, filebody);
	}
	
	@Override
	public List<String> getSentence(List<String> paraList, String filename) throws Exception {
//	public List<List<String>> getSentence(List<String> paraList, String filename) throws Exception {
//		List<List<String>> result = new ArrayList<List<String>>();
		List<String> result = new ArrayList<String>();
		List<String> sentenceList = null;
		
		for ( int i=0; i<paraList.size(); i++ ) {
			sentenceList = new ArrayList<String>();
			
			sentenceList = analysisDAO.getSentence(paraList.get(i), filename);
			if ( sentenceList.size() > 0 ) {
				for ( int j=0; j<sentenceList.size(); j++ ) {
					result.add(sentenceList.get(j));
				}
//				result.add(sentenceList);
			}
			sentenceList = null;
		}
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getPosTag(List sentenceList) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		
		if ( sentenceList.size() > 0 ) {
			for ( int i=0; i<sentenceList.size(); i++ ) {

				map = new HashMap<String, Object>();
				if ( !"".equals(sentenceList.get(i).toString()) ) {
					map = analysisDAO.getPosTag(sentenceList.get(i).toString());
				} else {
					map.put("resultTag", "");
					map.put("sfxJson", "");
				}
				
				map.put("sentence", sentenceList.get(i).toString());
				
				resultList.add(map);
				map = null;
			}
		}
		return resultList;
	}
	
	@Override
	public Map<String, Object> getSymanticPattern(List sentenceList, String category) throws Exception {
		// TODO Auto-generated method stub
		String lconf = "";
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> resultMatchList = new ArrayList<Map<String,Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = null;
		
		if ( konanPropertiesService.getString("cate_boiler").equals(category)) {
			lconf = konanPropertiesService.getString("boiler_lconf");
		} else if ( konanPropertiesService.getString("cate_gt_turbine").equals(category)) {
			lconf = konanPropertiesService.getString("gt_lconf");
		} else if ( konanPropertiesService.getString("cate_st_turbine").equals(category)) {
			lconf = konanPropertiesService.getString("st_lconf");
		} else if ( konanPropertiesService.getString("cate_gen_prev").equals(category)) {
			lconf = konanPropertiesService.getString("gen_lconf");
		}
		
		if ( !"".equals(lconf) && sentenceList.size() > 0 ) {
			
			for ( int i=0; i<sentenceList.size(); i++ ) {
				map = new HashMap<String, Object>();
				if ( !"".equals(sentenceList.get(i).toString()) ) {
					map = analysisDAO.getSymanticPattern(sentenceList.get(i).toString(), lconf);
				} else {
					List<Map<String, String>> list = new ArrayList<Map<String, String>>();
					map.put("result", list);
				}
				map.put("category", category);
				resultList.add(map);
				map = null;
			}
		}
		
		List<Map<String, String>> matchList = null;
		
		// 시맨틱 매칭이 된 애들만 새로 추출하여 List를 새로 만든다.
		for ( Map<String, Object> matchMap : resultList ) {
			matchList = new ArrayList<Map<String, String>>();
			matchList = (List<Map<String, String>>) matchMap.get("result");
			
			if ( matchList != null && matchList.size() > 0 ) {
				resultMatchList.add(matchMap);
			}
			matchList = null;
		}
		
		resultMap.put("symanticList", resultList);
		resultMap.put("symanticListToMatch", resultMatchList);
		
		return resultMap;
	}
	
	@Override
	public List<String> getNer(String fileName, List sentenceList, String classfication) throws Exception {
		// TODO Auto-generated method stub
		List<String> nerList = new ArrayList<String>();
		// 문장 List를 스트링으로 변환한다. 
		// 한건씩 날릴 경우 속도가 엄청 느려 List 데이터를 스트링으로 보내기로 결정
		String sentence = StringUtils.join(sentenceList, "##@@##");
		logger.debug("[getNer] sentence :: " + sentence);
			
		nerList = analysisDAO.getNer(fileName, sentence, classfication);
		
		logger.debug("[getNer] nerList size :: " + nerList.size());
		
		return nerList;
	}
	
	@Override
	public boolean restIndexingPost(String category, UploadVO uploadVO) throws Exception {
		// TODO Auto-generated method stub
		return analysisDAO.restIndexingPost(category, uploadVO);
	}	

	@Override
	public boolean deleteRecordEngine(UploadVO uploadVO) throws Exception {
		// TODO Auto-generated method stub
		return analysisDAO.deleteRecordEngine(uploadVO);
	}
	
	@Override
	public boolean analysis(Map<String, String> fileInfo, UploadVO uploadVO, String category) throws Exception {
		// TODO Auto-generated method stub
		
		// 업로드 된 문서 자동분류
		String classfication = "Fail";
		int insResult = -1;
		boolean isSuccess = false;
		
		classfication = commonService.getClassfication(fileInfo.get("filePath") + File.separator, fileInfo.get("fileName"));
		uploadVO.setClassfication(classfication);
		
		//업로드 정보 Insert
		insResult = analysisDAO.insertReportInfo(uploadVO);
		logger.debug("[upload][SUCCESS] insert result => " + insResult);
		logger.debug("[upload][SUCCESS] insert idx => " + uploadVO.getUploadIdx());
		
		// insert 성공 시
		if ( insResult > 0 ) {
			
			Map<String, Object> insertData = analysisDAO.selectReportInfo(uploadVO.getUploadIdx());
			
			if ( insertData != null ) {
				logger.debug("[upload] insertData :: " + insertData.toString());
				isSuccess = analysisDAO.restIndexingPost(category, uploadVO);
				
				if ( !isSuccess ) {
					// 색인 실패시 Insert한 정보 삭제
					logger.debug("[upload] idx : " + uploadVO.getUploadIdx());
//					analysisDAO.deleteReportInfo(uploadVO.getUploadIdx());
					analysisDAO.deleteRecordEngine(uploadVO);
					logger.debug("[upload][SUCCESS] DELETE SUCCESS");
				}
			}
			
		} else {
			logger.debug("[upload][ERROR] INSERT FAIL. DB CHECK PLEASE");
		}
		
		return isSuccess;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////// DB(Mysql) 관련
	@Override
	public List<Map<String, Object>> selectCheckDupMd5(UploadVO uploadVO) throws Exception {
		// TODO Auto-generated method stub
		return analysisDAO.selectCheckDupMd5(uploadVO);
	}
	
	@Override
	public int insertReportInfo(UploadVO uploadVO) throws Exception {
		// TODO Auto-generated method stub		
		return analysisDAO.insertReportInfo(uploadVO);
	}
	
	@Override
	public int updateReportInfo(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub		
		return analysisDAO.updateReportInfo(map);
	}
	
	@Override
	public int deleteReportInfo(int idx) throws Exception {
		// TODO Auto-generated method stub
		return analysisDAO.deleteReportInfo(idx);
	}
}
