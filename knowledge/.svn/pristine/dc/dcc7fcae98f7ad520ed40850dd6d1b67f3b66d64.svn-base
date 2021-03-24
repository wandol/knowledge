package egovframework.kf.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.konantech.konansearch.KSEARCH;

import egovframework.kf.common.DCUtil;
import egovframework.kf.data.RestResultVO;
import egovframework.rte.fdl.property.EgovPropertyService;
/**
 * Class Name : RestModule.java
 * Description : 검색엔진이용하여 검색결과 조회 모듈
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 * 2017년 12월  00일                       최초 작성
 *
 * @since 2017년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Component("restModule")
public class RestModule {
	
	private static final Logger logger = LoggerFactory.getLogger(RestModule.class);

	@Autowired
	private DCUtil dc;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	private EgovPropertyService konanPropertiesService;

	//개발라이선스 표시 문자열
	private String txtWarning ="\\(WARNING: EVALUATION COPY\\[SEARCH\\]\\)";
		
	/**
	 * restFull API이용하여 검색엔진  리턴결과를 gson을통해 파싱하여 객체로 전달한다.
	 * @param restUrl 호출 URL
	 * @param restVO	입력vo
	 * @param selectField 조회필드명
	 * @return
	 */
	public boolean restSearch(String restUrl, RestResultVO restVO, String selectField) {
		String charset = konanPropertiesService.getString("charset");
		
		StringBuffer sb = dc.getUrlData(restUrl, charset);

		//logger.debug("sb :: " + sb.toString());
		// 결과 파싱
		try{
			Gson gson = new Gson();
			
			JsonObject jsonObject = gson.fromJson( sb.toString(), JsonObject.class);
			JsonObject rsObject = jsonObject.get("result").getAsJsonObject() ;
			
			//결과 set
			restVO.setStatus( jsonObject.get("status").getAsString() );
			restVO.setTotal(rsObject.get("total_count").getAsLong() );
			
			JsonArray arr = rsObject.get("rows").getAsJsonArray();
			List<Map<String, String>> list = new ArrayList<> ();
			
			if ( restVO.getTotal() > 0 ) {
				boolean realLic = konanPropertiesService.getBoolean("realLic");
				
				String[] fields = selectField.split(",");
				Map<String, String> map;
				JsonObject fieldobj;
				for(JsonElement element : arr){
					map = new HashMap<>();				
					fieldobj = (element.getAsJsonObject()).get("fields").getAsJsonObject();
					
					for(String field:fields){
						if( realLic) {
							map.put(field, fieldobj.get(field).getAsString());
						}else{					
							if ( "count(*)".equals(field) ) map.put("count", fieldobj.get(field).getAsString().replaceAll(txtWarning, ""));
							else map.put(field, fieldobj.get(field).getAsString().replaceAll(txtWarning, ""));
						}
					}
					
					list.add(map);
					map = null;
				}
				
				restVO.setResult(list);
			}
			
		} catch (JsonParseException e){
			logger.error("konan search engine search error...", e);
			return false;
		}
		return true;

	}
	
	/**
	 * restFull API이용하여 검색엔진  리턴결과를 gson을통해 파싱하여 객체로 전달한다.
	 * @param restUrl 호출 URL
	 * @param restVO	입력vo
	 * @param selectField 조회필드명
	 * @return
	 */
	public boolean restSearchGroupBy(String restUrl, RestResultVO restVO, String selectField) {
		String charset = konanPropertiesService.getString("charset");
		
		StringBuffer sb = dc.getUrlData(restUrl, charset);

		// 결과 파싱
		try{
			Gson gson = new Gson();
			
			JsonObject jsonObject = gson.fromJson( sb.toString(), JsonObject.class);
			JsonObject rsObject = jsonObject.get("result").getAsJsonObject() ;
			
			//결과 set
			restVO.setStatus( jsonObject.get("status").getAsString() );
			restVO.setTotal(rsObject.get("total_count").getAsLong() );
			
			JsonArray arr = rsObject.get("rows").getAsJsonArray();
			
			boolean realLic = konanPropertiesService.getBoolean("realLic");
			List<Map<String, String>> list = new ArrayList<> ();
			String[] fields = selectField.split(",");
			Map<String, String> map;
			JsonObject fieldobj;
			for(JsonElement element : arr){
				map = new HashMap<>();				
				fieldobj = (element.getAsJsonObject()).get("fields").getAsJsonObject();
				
				for(String field:fields){
					if( realLic) {
						map.put(field, fieldobj.get(field).getAsString());
					}else{					
						if ( "count(*)".equals(field) ) map.put("count", fieldobj.get(field).getAsString().replaceAll(txtWarning, ""));
						else map.put(field, fieldobj.get(field).getAsString().replaceAll(txtWarning, ""));
					}
				}
				
				list.add(map);
				map = null;
			}
			
			restVO.setResult(list);

		} catch (JsonParseException e){
			logger.error("konan search engine search error...", e);
			return false;
		}
		return true;

	}
	
	/**
	 * 테스트용.
	 * @param restUrl 호출 URL
	 * @param restVO	입력vo
	 * @param selectField 조회필드명
	 * @return
	 */
	public boolean restSearchDepthGroupBy(String restUrl, RestResultVO restVO, String selectField) {
		String charset = konanPropertiesService.getString("charset");
		
		StringBuffer sb = dc.getUrlData(restUrl, charset);

		// 결과 파싱
		try{
			Gson gson = new Gson();
			
			JsonObject jsonObject = gson.fromJson( sb.toString(), JsonObject.class);
			JsonObject rsObject = jsonObject.get("result").getAsJsonObject() ;
			
			//결과 set
			restVO.setStatus( jsonObject.get("status").getAsString() );
			restVO.setTotal(rsObject.get("total_count").getAsLong() );
			
			JsonArray arr = rsObject.get("rows").getAsJsonArray();
			
			boolean realLic = konanPropertiesService.getBoolean("realLic");
			List<Map<String, String>> list = new ArrayList<> ();
			String[] fields = selectField.split(",");
			Map<String, String> map;
			JsonObject fieldobj;
			for(JsonElement element : arr){
				map = new HashMap<>();				
				fieldobj = (element.getAsJsonObject()).get("fields").getAsJsonObject();
				
				for(String field:fields){
					if( realLic) {
						map.put(field, fieldobj.get(field).getAsString());
					}else{					
						if ( "count(*)".equals(field) ) map.put("count", fieldobj.get(field).getAsString().replaceAll(txtWarning, ""));
						else map.put("data", fieldobj.get(field).getAsString().replaceAll(txtWarning, ""));
					}
				}
				
				list.add(map);
				map = null;
			}
			
			restVO.setResult(list);

		} catch (JsonParseException e){
			logger.error("konan search engine search error...", e);
			return false;
		}
		return true;

	}
	
	/**
	 * 테스트용.
	 * @param restUrl 호출 URL
	 * @param restVO	입력vo
	 * @param selectField 조회필드명
	 * @return
	 */
	public List<Map<String, Object>> restSearchKsm(String restUrl, String selectField, String moduleName) {
		String charset = konanPropertiesService.getString("charset");
		
		StringBuffer sb = dc.getUrlData(restUrl, charset);
		String[] field = selectField.split(",");
		List<Map<String, Object>> list = null;
		// 결과 파싱
		try{
			JSONParser parser = new JSONParser();
			Object obj;
			obj = parser.parse(sb.toString());
			JSONObject jsonObj = (JSONObject) obj;
			JSONArray resultArr = (JSONArray) jsonObj.get("result");
			Map<String, Object> map = null;
			list = new ArrayList<Map<String,Object>>();
			for(int i=0; i < resultArr.size(); i++){
				map = new HashMap<String, Object>();
				JSONObject row = (JSONObject) resultArr.get(i);
				
				if ( "sfx".equals(moduleName) ) {
					map.put(field[0], row.get(field[0]).toString());
					map.put(field[1], row.get(field[1]).toString());
				} else {
					map.put(field[0], row.get(field[0]).toString());
				}
				
				list.add(map);
			}
		} catch (JsonParseException e){
			logger.error("konan search engine search error...", e);
			return list;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}
	
	/**
	 * 테스트용.
	 * @param restUrl 호출 URL
	 * @param restVO	입력vo
	 * @param selectField 조회필드명
	 * @return
	 */
//	public List<String> restSearchKsmNer(String restUrl, String selectField) {
//		String charset = konanPropertiesService.getString("charset");
//		
//		StringBuffer sb = dc.getUrlData(restUrl, charset);
//		String[] field = selectField.split(",");
//		List<String> list = null;
//		String result = "";
//		// 결과 파싱
//		try{
//			JSONParser parser = new JSONParser();
//			Object obj;
//			obj = parser.parse(sb.toString());
//			JSONObject jsonObj = (JSONObject) obj;
//			JSONArray resultArr = (JSONArray) jsonObj.get("result");
//			list = new ArrayList<>();
//			for(int i=0; i < resultArr.size(); i++){
//				JSONObject row = (JSONObject) resultArr.get(i);
//				
////				if ( (i+1) == resultArr.size() ) result += row.get(selectField).toString();
////				else result += row.get(selectField).toString() + "_";
//				list.add(row.get(selectField).toString());
//			}
//		} catch (JsonParseException e){
//			logger.error("konan search engine search error...", e);
//			return list;
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
//
//	}

	/**
	 * restFull API이용하여 검색엔진  리턴결과를 gson을통해 파싱하여 객체로 전달한다.
	 * @param restUrl 호출 URL
	 * @param restVO	입력vo
	 * @param selectField 조회필드명
	 * @return
	 */
	public boolean restSearchPost(String restUrl, String postParamData, RestResultVO restVO, String selectField) {
		String charset = konanPropertiesService.getString("charset");
		
		StringBuffer sb = dc.getUrlDataPost(restUrl,postParamData, charset, "");
		logger.debug("QUERY result : " + sb.toString());
		// 결과 파싱
		try{
			Gson gson = new Gson();
			
			JsonObject jsonObject = gson.fromJson( sb.toString(), JsonObject.class);
			JsonObject rsObject = jsonObject.get("result").getAsJsonObject() ;
			//결과 set
			restVO.setStatus( jsonObject.get("status").getAsString() );
			restVO.setTotal(rsObject.get("total_count").getAsLong() );
			
			JsonArray arr = rsObject.get("rows").getAsJsonArray();
			boolean realLic = konanPropertiesService.getBoolean("realLic");
			List<Map<String, String>> list = new ArrayList<> ();
			String[] fields = selectField.split(",");
			Map<String, String> map;
			JsonObject fieldobj;
			for(JsonElement element : arr){
				map = new HashMap<>();				
				fieldobj = (element.getAsJsonObject()).get("fields").getAsJsonObject();
				for(String field:fields){
					if( realLic) {
						map.put(field, fieldobj.get(field).getAsString());
					}else{
						map.put(field, fieldobj.get(field).getAsString().replaceAll(txtWarning, ""));
					}
				}
				list.add(map);
				map = null;
			}
			
			restVO.setResult(list);
		} catch (JsonParseException e){
			logger.error("konan search engine search error...", e);
			return false;
		}
		return true;

	}
	
	/**
	 * restFull API이용하여 검색엔진 리턴결과를JSONParser를 통해 파싱하여 객체로 전달한다.
	 * @param restUrl 호출 URL
	 * @param restVO	입력vo
	 * @param selectField 조회필드명
	 * @return
	 */
	public boolean restSearchJSONParser(String restUrl, RestResultVO restVO, String selectField) {
		String charset = konanPropertiesService.getString("charset");
		StringBuffer sb = dc.getUrlData(restUrl, charset);
		
		// 결과 파싱
		try{

			// 파싱하는 부분
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(sb.toString());
			
			JSONObject jsonObj = (JSONObject) obj;
			restVO.setStatus((String)jsonObj.get("status"));
			
			JSONObject resultObj = (JSONObject) jsonObj.get("result");
			restVO.setTotal((long)resultObj.get("total_count"));
			
			JSONArray arr = (JSONArray) resultObj.get("rows");
			int arrCnt = arr.size();
			int fieldCnt = 0;
			boolean realLic = konanPropertiesService.getBoolean("realLic");
			
			if(arr != null && arrCnt > 0) {
				String[] fields = selectField.split(",");
				Map<String, String> map;
				List<Map<String, String>> list = new ArrayList<> ();
						
				JSONObject result;
				JSONObject record;
				fieldCnt = fields.length;
				for(int i=0; i<arrCnt; i++) {
					map = new HashMap<String, String> ();
					
					result = (JSONObject) arr.get(i);
					record = (JSONObject) result.get("fields");
					
					for(int j=0; j<fieldCnt; j++) {
						if( !realLic) {
							map.put(fields[j], ((String)record.get(fields[j])).replaceAll(txtWarning, "") );	
						}else {
							map.put(fields[j], (String)record.get(fields[j]));	
						}
					}
					
					list.add(map);
					map = null;
				}
				
				restVO.setResult(list);
			}		
			
		} catch (Exception e){
			logger.error("konan search engine search error...", e);
			return false;
		}
		return true;

	}
	
	/**
	 * restFull API 이용하여 색인명령어를 싫행한다.
	 * @param restUrl 호출 URL
	 * @param restVO	입력vo
	 * @param selectField 조회필드명
	 * @return
	 */
	public StringBuffer restPost(String kqlUrl, String postData, String type) {
		// TODO Auto-generated method stub
		
		String charSet = konanPropertiesService.getString("charset");
		
		return dc.getUrlDataPost(kqlUrl, postData.toString(), charSet, type);
	}

	
	/**
	 * restFull API이용하여 lconf가 등록되어 있는지 확인하며 등록이 안되어 있으면 등록한다.
	 * @param restUrl 호출 URL
	 * @param restVO	입력vo
	 * @param selectField 조회필드명
	 * @return
	 */
	public boolean restCheckLconf(String restUrl, String name, String fileName) {
		String charset = konanPropertiesService.getString("charset");
		StringBuffer lconfUrl = new StringBuffer();
		lconfUrl.append(konanPropertiesService.getString("ksmUrl"));
		lconfUrl.append("/kana/lconf/get?name=" + name);
		
		StringBuffer sb = dc.getUrlData(lconfUrl.toString(), charset);
		logger.debug("lconf Check :: " + sb.toString());
		if ( "".equals(sb.toString()) || sb.toString() == null ) {
			lconfUrl.setLength(0);
			
			lconfUrl.append(konanPropertiesService.getString("ksmUrl"));
			lconfUrl.append("/kana/lconf/attach?name=" + name + "&file=" + fileName);
			
			sb = dc.getUrlData(lconfUrl.toString(), charset);
			logger.debug("lconf Add :: " + sb.toString());
			
			if ( "true".equals(sb.toString()) ) {
				return true;
			} else {
				return false;
			}
		}
		return true;

	}
	
	/**
	 * Glossary Anchor 기능
	 * @param String inText
	 * @param 
	 * @param 
	 * @return
	 */
	public Map<String, String> getGlossaryAnchor(String inText) {
		
		String saddr = konanPropertiesService.getString("basicUrl");
		int domain_no = Integer.parseInt("0");
		
		String tag_scheme = "<a href='javascript:void(0);' class='glossary' data-toggle='tooltip' data-placement='right' title='$DESC'>$TERM</a>";
		String option = "ACT_GLOBAT_MATCH";
		Map<String, String> resultMap = new HashMap<>();
		
		String[] out_text = new String[1];
		long hc;
		int rc = 0;
		KSEARCH ks = new KSEARCH();
		hc = ks.CreateHandle();
		
		try {
			rc = ks.AnchorText(hc, saddr, out_text, inText, tag_scheme, option, domain_no);
		} catch (IOException e) {
			logger.debug("Glossary Anchor IOException.");
			
			resultMap.put("result", "false");
			return resultMap;
		} catch (RuntimeException e) {
			logger.debug("Glossary Anchor RuntimeException.");
			
			resultMap.put("result", "false");
			return resultMap;
		}
		
		if( rc < 0 )
		{
			logger.debug("rc : " + rc);
			logger.debug("Message : " + ks.GetErrorMessage(hc));
			
			resultMap.put("result", "false");
			return resultMap;
		} else {
			// 필요없는 데이터 정규식으로 제거
			String data = out_text[0].replaceAll("<CATEGORY-1>WORD<WOR_SEAR>([^ \r\n][ ]{0,})++\n<WOR1_BODY>", "");
			
			resultMap.put("glossary", data);
			resultMap.put("result", "success");
		}
		ks.DestroyHandle(hc);
		
		return resultMap;
	}
	
	/**
	 * 자동분류
	 * @param fileBody
	 * @return
	 */
	public String restClassfication(String fileBody) {

		// 자동분류 시작
		String charset = konanPropertiesService.getString("charset");
		String classfication= "";
		try {
			StringBuffer ksmUrl = new StringBuffer();
			ksmUrl.append(konanPropertiesService.getString("ksmUrl"));
			ksmUrl.append("/ktc/nbc-predict?domain=1&debug=true");
			StringBuffer sb = dc.getUrlDataPost(ksmUrl.toString(), "title=" + fileBody, charset, "");
			
			Double etcVal = 0.6;
			Double prob = Double.valueOf(sb.toString().split("prob")[1].split(",")[0].replaceAll(":", ""));
			
			if ( prob < etcVal ) {
				classfication = "기타";
			} else {
				String temp = sb.toString().split("prob")[1].split(",")[1].replaceAll(" cat:", "");			
				classfication = temp;
			}
		} catch (Exception e) {
			return "fail";
		}
		
		return classfication;
	}
	
	/**
	 * 테스트용.
	 * @param restUrl 호출 URL
	 * @param restVO	입력vo
	 * @param selectField 조회필드명
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public Map<String, Object> restSearchKsm(String text) throws UnsupportedEncodingException {
		String ksmUrl = konanPropertiesService.getString("ksmUrl");
		URL url =null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		StringBuffer sfxUrl = new StringBuffer();
		
		sfxUrl.append(ksmUrl);
		sfxUrl.append("/kana/analyze?mod=kma&");
		sfxUrl.append("text=" + URLEncoder.encode(text, "UTF-8"));
		sfxUrl.append("&language=korean&charset=utf8&option=*&format=json");
		
		// 조회
		try{
			url = new URL(sfxUrl.toString());
			conn = (HttpURLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));

			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}
			//logger.debug("sb :: " + sb.toString());
			
			Object object = JSONValue.parse(sb.toString());
			JSONObject jsonObj = (JSONObject) object;
			
			sfxUrl.setLength(0);
			sfxUrl.append(ksmUrl);
			sfxUrl.append("/kana/analyze?mod=ksa&");
			sfxUrl.append("text=" + URLEncoder.encode(text, "UTF-8"));
			sfxUrl.append("&language=korean&charset=utf8&option=*&format=plain");
			
			url = new URL(sfxUrl.toString());
			conn = (HttpURLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));
			
			sb.setLength(0);
			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
				sb.append("<br>");
			}
			
			logger.debug(sb.toString());
			
			map.put("sfxJson", jsonObj);
			map.put("sfxPlain", sb.toString());

		} catch (IOException ie) {
			logger.debug("konan search engine search error..." + ie);
			return null;
		}
		return map;

	}
	
	/**
	 * 테스트용.
	 * @param restUrl 호출 URL
	 * @param restVO	입력vo
	 * @param selectField 조회필드명
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public Map<String, Object> restSearchKsmCandidate(String text) throws UnsupportedEncodingException {
		String ksmUrl = konanPropertiesService.getString("ksmUrl");
		
		URL url =null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		StringBuffer sfxUrl = new StringBuffer();
		sfxUrl.append(ksmUrl);
		sfxUrl.append("/sfx/candidate?");
		sfxUrl.append("text=" + URLEncoder.encode(text, "UTF-8"));
		sfxUrl.append("&title=" + URLEncoder.encode("제목", "UTF-8") + "&property=" + URLEncoder.encode("테스트", "UTF-8") + "&value&charset=utf8&language=korean&format=json&lconf=boiler_sym");
		
		// 조회
		try{
			url = new URL(sfxUrl.toString());
			conn = (HttpURLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));

			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}
			//logger.debug("sb :: " + sb.toString());
			
			Object object = JSONValue.parse(sb.toString());
			JSONObject jsonObj = (JSONObject) object;
			
			sfxUrl.setLength(0);
			sfxUrl.append(ksmUrl);
			sfxUrl.append("/sfx/extract?");
			sfxUrl.append("text=" + URLEncoder.encode(text, "UTF-8"));
			sfxUrl.append("&language=korean&option=lqit&charset=utf8&format=json&lconf=boiler_sym");
			
			url = new URL(sfxUrl.toString());
			conn = (HttpURLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));
			
			sb.setLength(0);
			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}			
			logger.debug(sb.toString());
			
			Object object2 = JSONValue.parse(sb.toString());
			JSONObject jsonObj2 = (JSONObject) object2;
			
			map.put("candidate", jsonObj);
			map.put("patternInfo", jsonObj2);

		} catch (IOException ie) {
			logger.debug("konan search engine search error..." + ie);
			return null;
		}
		return map;

	}
	
	public Map<String, Object> restSearchKsmPosTag(String text) throws Exception {
		String ksmUrl = konanPropertiesService.getString("ksmUrl");
		
		URL url =null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		
		StringBuffer sfxUrl = new StringBuffer();
		sfxUrl.append(ksmUrl);
		sfxUrl.append("/kana/analyze?mod=kma&");
		sfxUrl.append("text=" + URLEncoder.encode(text, "UTF-8"));
		sfxUrl.append("&language=korean&charset=utf8&option=*&format=json");
		
		logger.debug("org Text :: " + text);
		logger.debug("url :: " + sfxUrl.toString());
		// 조회
		try{
			url = new URL(sfxUrl.toString());
			conn = (HttpURLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));

			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}
			
			logger.debug("new Text :: " + sb.toString());
			
			String resultTag = "";
			String resultVal = "";
			Object object = JSONValue.parse(sb.toString());
			JSONObject jsonObj = (JSONObject) object;
			JSONArray sentsArray = (JSONArray) jsonObj.get("sents");
			
			for ( int k=0; k<sentsArray.size(); k++ ) {
				JSONObject sents = (JSONObject) sentsArray.get(k);
				JSONArray wordsArray = (JSONArray) sents.get("words");
				
//				logger.debug("words size :: " + wordsArray.size());
				for ( int i=0; i<wordsArray.size(); i++ ) {
					JSONObject words = (JSONObject) wordsArray.get(i);
					JSONArray nbestArray = (JSONArray) words.get("nbest");
					JSONObject nbest = (JSONObject) nbestArray.get(0);
					JSONArray lemmasArray = (JSONArray) nbest.get("lemmas");
					for ( int j=0; j<lemmasArray.size(); j++ ) {
						JSONObject lemmas = (JSONObject) lemmasArray.get(j);
						resultTag += lemmas.get("string") + "<span class=\"tagging\">/" + lemmas.get("tag") + "</span> ";
						resultVal += lemmas.get("string") + "/" + lemmas.get("tag") + " ";
					}
				}
			
			}
			
			logger.debug("resultTag :: " + resultTag);
			
			map.put("resultTag", resultTag);
			map.put("resultVal", resultVal);
			map.put("sfxJson", jsonObj);

		} catch (IOException ie) {
			logger.debug("konan search engine search error..." + ie);
			return null;
		}
		return map;

	}
	
	public Map<String, Object> restSearchKsmExplain(String text, String lconf) throws Exception {
		String ksmUrl = konanPropertiesService.getString("ksmUrl");
		
		URL url =null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		
		StringBuffer sfxUrl = new StringBuffer();
		sfxUrl.append(ksmUrl);
		sfxUrl.append("/sfx/explain?");
		sfxUrl.append("text=" + URLEncoder.encode(text, "UTF-8"));
		sfxUrl.append("&language=korean&charset=utf8&lconf=" + lconf);
		
		// 조회
		try{
			url = new URL(sfxUrl.toString());
			conn = (HttpURLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));

			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}
			
			logger.debug(sb.toString());
			
			String resultPattern = "";
			String resultHitrange = "";
			String resultTitle = "";
			String resultPattern_hilight = "";
			
			Object object = JSONValue.parse(sb.toString());
			JSONObject jsonObj = (JSONObject) object;
			JSONArray resultObjArray = (JSONArray) jsonObj.get("result");
			JSONObject resultObj = (JSONObject) resultObjArray.get(0);
			JSONArray keywordObjArray = (JSONArray) resultObj.get("keyword");
			if ( keywordObjArray.size() > 0 ) {
				JSONObject keywordObj = (JSONObject) keywordObjArray.get(0);
				if ( keywordObj.containsKey("pattern") ) {
					resultPattern = (String) keywordObj.get("pattern");
				}
				if ( keywordObj.containsKey("title") ) {
					resultTitle = (String) keywordObj.get("title");
				}
				if ( keywordObj.containsKey("hitrange") ) {
					resultHitrange = (String) keywordObj.get("hitrange");
				}
			}
			
			if ( resultPattern.indexOf("=") > -1 ) {
				String[] pattern = resultPattern.split("=");
				resultPattern_hilight = pattern[0] + " = <span class='symantic-hilight'>" + pattern[1] + "</span>";
			}
			
			map.put("sentence", resultObj.get("sent"));
			map.put("pattern", resultPattern);
			map.put("pattern_hilight", resultPattern_hilight);
			map.put("title", resultTitle);
			map.put("hitrange", resultHitrange);

		} catch (IOException ie) {
			logger.debug("konan search engine search error..." + ie);
			return null;
		}
		return map;

	}
	
	// Rest API 용
	public List<Map<String, String>> restSearchKsmExplain2(String text, String lconf) throws Exception {
		String ksmUrl = konanPropertiesService.getString("ksmUrl");
		
		URL url =null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		StringBuffer sfxUrl = new StringBuffer();
		sfxUrl.append(ksmUrl);
		sfxUrl.append("/sfx/explain?");
		sfxUrl.append("text=" + URLEncoder.encode(text, "UTF-8"));
		sfxUrl.append("&language=korean&charset=utf8&lconf=" + lconf);
		
		// 조회
		try{
			url = new URL(sfxUrl.toString());
			conn = (HttpURLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));

			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}
			
			logger.debug(sb.toString());
			
			String resultPattern = "";
			String resultHitrange = "";
			String resultTitle = "";
			
			Map<String, String> symanticMap = null;
			Object object = JSONValue.parse(sb.toString());
			JSONObject jsonObj = (JSONObject) object;
			JSONArray resultObjArray = (JSONArray) jsonObj.get("result");
			for ( int i=0; i<resultObjArray.size(); i++ ) {
				JSONObject resultObj = (JSONObject) resultObjArray.get(i);
				JSONArray keywordObjArray = (JSONArray) resultObj.get("keyword");
				
				logger.debug("[restSearchKsmExplain2] keywordObjArray size :: " + keywordObjArray.size());
				if ( keywordObjArray.size() > 0 ) {
					for ( int j=0; j<keywordObjArray.size(); j++ ) {
						symanticMap = new HashMap<String, String>();
						
						JSONObject keywordObj = (JSONObject) keywordObjArray.get(j);
						logger.debug("[restSearchKsmExplain2] :: " + keywordObj.toString());
						if ( keywordObj.containsKey("pattern") ) {
							resultPattern = (String) keywordObj.get("pattern");
							symanticMap.put("symantic", resultPattern);
						}
						if ( keywordObj.containsKey("hitrange") ) {
							resultHitrange = (String) keywordObj.get("hitrange");
							symanticMap.put("hitrange", resultHitrange);
						}
						if ( keywordObj.containsKey("title") ) {
							resultTitle = (String) keywordObj.get("title");
							symanticMap.put("title", resultTitle);
						}
						
						list.add(symanticMap);
					}
				}
			}
			logger.debug("[restSearchKsmExplain2] list size :: " + list.size());

		} catch (IOException ie) {
			logger.debug("konan search engine search error..." + ie);
			return null;
		}
		return list;

	}
	
	// 시뮬레이션 용( 분석 가시화)
	public Map<String, Object> restSearchKsmExplain3(String text, String lconf) throws Exception {
		String ksmUrl = konanPropertiesService.getString("ksmUrl");
		
		URL url =null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		StringBuffer sfxUrl = new StringBuffer();
		sfxUrl.append(ksmUrl);
		sfxUrl.append("/sfx/explain?");
		sfxUrl.append("text=" + URLEncoder.encode(text, "UTF-8"));
		sfxUrl.append("&language=korean&charset=utf8&lconf=" + lconf);
		logger.debug("[symantic] sfxUrl :: " + sfxUrl.toString());
		
		// 조회
		try{
			url = new URL(sfxUrl.toString());
			conn = (HttpURLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));

			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}
			
			logger.debug(sb.toString());
			
			String resultPattern = "";
			String resultHitrange = "";
			String resultTitle = "";
			String resultProperty = "";
			String resultPattern_hilight = "";
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			
			Object object = JSONValue.parse(sb.toString());
			JSONObject jsonObj = (JSONObject) object;
			JSONArray resultObjArray = (JSONArray) jsonObj.get("result");
			for ( int i=0; i<resultObjArray.size(); i++ ) {
				JSONObject resultObj = (JSONObject) resultObjArray.get(i);
				JSONArray keywordObjArray = (JSONArray) resultObj.get("keyword");
				if ( keywordObjArray.size() > 0 ) {
					for ( int j=0; j<keywordObjArray.size(); j++ ) {
						map = new HashMap<String, String>();
						
						JSONObject keywordObj = (JSONObject) keywordObjArray.get(j);
						if ( keywordObj.containsKey("pattern") ) {
							resultPattern = (String) keywordObj.get("pattern");
						}
						if ( keywordObj.containsKey("title") ) {
							resultTitle = (String) keywordObj.get("title");
						}
						if ( keywordObj.containsKey("hitrange") ) {
							resultHitrange = (String) keywordObj.get("hitrange");
						}
						if ( keywordObj.containsKey("property") ) {
							resultProperty = (String) keywordObj.get("property");
						}
						
						if ( resultPattern.indexOf("=") > -1 ) {
							String[] pattern = resultPattern.split("=");
							resultPattern_hilight = pattern[0] + " = <span class='symantic-hilight'>" + pattern[1] + "</span>";
						}
						
						map.put("sentence", resultObj.get("sent").toString());
						map.put("pattern", resultPattern);
						map.put("pattern_hilight", resultPattern_hilight);
						map.put("title", resultTitle);
						map.put("property", resultProperty);
						map.put("hitrange", resultHitrange);
						
						list.add(map);
						map = null;
					}
				}
				
				
			}
			
			resultMap.put("result", list);
			

		} catch (IOException ie) {
			logger.debug("konan search engine search error..." + ie);
			return null;
		}
		return resultMap;

	}
	
	public Map<String, String> restSearchKsmSyntax(String text) throws Exception {
		String ksmUrl = konanPropertiesService.getString("ksmUrl");
		
		URL url =null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		
		StringBuffer sfxUrl = new StringBuffer();

		sfxUrl.append(ksmUrl);
		sfxUrl.append("/kana/analyze?mod=ksa&");
		sfxUrl.append("text=" + URLEncoder.encode(text, "UTF-8"));
		sfxUrl.append("&language=korean&charset=utf8&option=*&format=plain");
		
		// 조회
		try{
			url = new URL(sfxUrl.toString());
			conn = (HttpURLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));

			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
				sb.append("<br>");
			}
			
			logger.debug(sb.toString());
			
			map.put("sfxPlain", sb.toString());

		} catch (IOException ie) {
			logger.debug("konan search engine search error..." + ie);
			return null;
		}
		return map;

	}
	
	public List<Map<String, String>> restSearchKsmNer(String text, String lconf, String title) throws Exception {
		String ksmUrl = konanPropertiesService.getString("ksmUrl");
		
		URL url =null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		List<Map<String, String>> result = null;
		Map<String, String> map = null;
		
		StringBuffer urlSb = new StringBuffer();

		urlSb.append(ksmUrl);
		urlSb.append("/ner2?");
		urlSb.append("text=" + URLEncoder.encode(text, "UTF-8"));
		urlSb.append("&lconf=" + lconf);
		urlSb.append("&language=korean&charset=utf8&option=b1");
		
		logger.debug("[restSearchKsmSymptom] url :: " + urlSb.toString());
		
		// 조회
		try{
			url = new URL(urlSb.toString());
			conn = (HttpURLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));

			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}
			
			logger.debug("[restSearchKsmSymptom] ner" + sb.toString());
			
			Object object = JSONValue.parse(sb.toString());
			JSONObject jsonObj = (JSONObject) object;
			
			JSONArray arr = (JSONArray) jsonObj.get("result");
			logger.debug("[restSearchKsmSymptom] arr size :: " + arr.size());
			
			if ( arr.size() > 0 ) {
				// response 결과를 담을 List 객체 초기화
				result = new ArrayList<Map<String,String>>();
				
				for(int i=0; i<arr.size(); i++){
					map = new HashMap<String, String>();
					JSONObject tempJson = (JSONObject) arr.get(i);
					
					map.put("string", (String) tempJson.get("string"));
					map.put("tag", (String) tempJson.get("tag"));
					map.put("title", title);
					map.put("sentence", text);
					
					result.add(map);
				}
			} else {
				
				if ( !"lc_pos".equals(lconf) ) {
					
					urlSb.setLength(0);
					
					urlSb.append(ksmUrl);
					urlSb.append("/ner2?");
					urlSb.append("text=" + URLEncoder.encode(text, "UTF-8"));
					urlSb.append("&lconf=" + lconf + "2");
					urlSb.append("&language=korean&charset=utf8&option=b1");
					
					logger.debug("[restSearchKsmSymptom] url22 :: " + urlSb.toString());
					
					url = new URL(urlSb.toString());
					conn = (HttpURLConnection) url.openConnection();
					br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));

					for(String line = br.readLine(); line != null ; line = br.readLine()){
						sb.append(line);
					}
					
					logger.debug("[restSearchKsmSymptom] ner" + sb.toString());
					
					Object object2 = JSONValue.parse(sb.toString());
					JSONObject jsonObj2 = (JSONObject) object;
					
					JSONArray arr2 = (JSONArray) jsonObj.get("result");
					logger.debug("[restSearchKsmSymptom] arr size :: " + arr.size());

					// response 결과를 담을 List 객체 초기화
					result = new ArrayList<Map<String,String>>();
					if ( arr.size() > 0 ) {
						
						for(int i=0; i<arr.size(); i++){
							map = new HashMap<String, String>();
							JSONObject tempJson = (JSONObject) arr.get(i);
							
							map.put("string", (String) tempJson.get("string"));
							map.put("tag", (String) tempJson.get("tag"));
							map.put("title", title);
							map.put("sentence", text);
							
							result.add(map);
						}
					} else {
						logger.debug("[restSearchKsmSymptom] no ner Keyword....");
					}
				}
			}
			
			logger.debug("[restSearchKsmSymptom] ner >>>>>>>>>>>>>> END");

			return result;

		} catch (IOException ie) {
			logger.debug("konan search engine search error..." + ie);
			return null;
		}

	}
	
	/**
	 * restFull API이용하여 검색엔진  리턴결과를 gson을통해 파싱하여 객체로 전달한다.
	 * @param restUrl 호출 URL
	 * @param restVO	입력vo
	 * @param selectField 조회필드명
	 * @return
	 */
	public boolean restSearchDataTablePost(String restUrl, String postParamData, RestResultVO restVO, String selectField, int offset, int limit) {
		String charset = konanPropertiesService.getString("charset");
		
		StringBuffer sb = dc.getUrlDataPost(restUrl,postParamData, charset, "");
		logger.debug("QUERY result : " + sb.toString());
		// 결과 파싱
		try{
			Gson gson = new Gson();
			
			JsonObject jsonObject = gson.fromJson( sb.toString(), JsonObject.class);
			JsonObject rsObject = jsonObject.get("result").getAsJsonObject() ;
			//결과 set
			restVO.setStatus( jsonObject.get("status").getAsString() );
			restVO.setTotal(rsObject.get("total_count").getAsLong() );
			
			JsonArray arr = rsObject.get("rows").getAsJsonArray();
			boolean realLic = konanPropertiesService.getBoolean("realLic");
			List<Map<String, String>> list = new ArrayList<> ();
			String[] fields = selectField.split(",");
			Map<String, String> map;
			JsonObject fieldobj;
			for(JsonElement element : arr){
				map = new HashMap<>();				
				fieldobj = (element.getAsJsonObject()).get("fields").getAsJsonObject();
				for(String field:fields){
					if( realLic) {
						map.put(field, fieldobj.get(field).getAsString());
					}else{
						if ( "NO".equals(field) ) {
							map.put(field, String.valueOf(++offset));
						} else {
							map.put(field, fieldobj.get(field).getAsString().replaceAll(txtWarning, ""));
						}
					}
				}
				list.add(map);
				map = null;
			}
			
			restVO.setResult(list);
		} catch (JsonParseException e){
			logger.error("konan search engine search error...", e);
			return false;
		}
		return true;

	}
}
