package egovframework.kf.common.service.impl;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.FileUtil;
import egovframework.kf.common.dao.CommonDAO;
import egovframework.kf.common.dao.LogDAO;
import egovframework.kf.common.service.LogService;
import egovframework.kf.common.vo.LogVO;
import egovframework.kf.common.vo.RecentVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.user.vo.UserVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("logService")
public class LogServiceImpl extends EgovAbstractServiceImpl implements LogService {
	
	private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** file util Setting */
	@Resource(name = "fileUtil")
	private FileUtil fileUtil;
	
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	@Resource(name = "logDAO")
	private LogDAO logDAO;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	private EgovPropertyService konanPropertiesService;

	@Override
	public boolean writeLogHistory(String user, String item, int rating) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void insertFileDownLog(Map<String, String> map, UserVO user, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		LogVO downloadLog = new LogVO();
		
		String fileName = map.get("fileName");
		String category = map.get("cate");
		String key = map.get("md5_key");
		String pageName = map.get("pageName");
		
		// 클라이언트 정보(IP, Brower, OS) Get
		String ip = commonUtil.getClientIP(request);	// IP
		String brower = commonUtil.getClientBrower(request);	// BROWER
		String os = commonUtil.getClientOS(request);	// OS
		
		String fileFullPath = "";
		if ( !konanPropertiesService.getString("cate_devdoc").equals(map.get("cate")) ) {
			fileFullPath = commonDAO.selectDownloadPath(map);
		} else {
			fileFullPath = commonUtil.getFilepath(map.get("cate"), "");
			fileFullPath = fileFullPath + File.separator + map.get("fileName");
		}
		String fileSizeStr = fileUtil.getFileSize(fileFullPath);
		int fileSize = 0;
		
		// 파일 사이즈 체크 후 int로 형변환
		if ( fileSizeStr != null && !"".equals(fileSizeStr) ) fileSize = Integer.parseInt(fileSizeStr);
		
		logger.info("[insertFileDownLog] file size :: " + fileSize);
		
		downloadLog.setUser_id(String.valueOf(user.getUser_id()));
		downloadLog.setUser_nm(user.getUser_nm());
		downloadLog.setFile_idx(key);
		downloadLog.setFile_nm(fileName);
		downloadLog.setFile_size(fileSize);
		downloadLog.setDown_page(pageName);
		downloadLog.setUser_ip(ip);
		downloadLog.setUser_browser(brower);
		downloadLog.setUser_os(os);
		
		logger.info("[insertFileDownLog] download VO :: " + downloadLog.toString());
		
		logDAO.insertFiledownLog(downloadLog);
	}

	@Override
	public void insertRecentKnowledgeLog(RestResultVO result, UserVO user, String category) throws Exception {
		// TODO Auto-generated method stub
		
		RecentVO recentVO = new RecentVO();
		String gubun_no = "";
		
		if ( result.getResult().get(0).containsKey("GUBUN_NO") ) {
			gubun_no = result.getResult().get(0).get("GUBUN_NO");
		} else {
			gubun_no = "0";
		}
		
		recentVO.setRepo_key(result.getResult().get(0).get("MD5_KEY"));
		recentVO.setGubun_no(gubun_no);
		recentVO.setRepo_file_nm(result.getResult().get(0).get("FILENAME"));
		recentVO.setUser_id(user.getUser_id());
		recentVO.setCategory(category);
		
		logDAO.insertRecentKnowledgeLog(recentVO);
	}
	
}
