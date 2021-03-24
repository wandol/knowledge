package egovframework.kf.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import egovframework.kf.common.vo.UploadVO;
import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * Class Name : CommonUtil.java
 * Description : 공통 
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
 * */
@Component
public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public String fileInfoToMD5(UploadVO uploadVO) {
		// TODO Auto-generated method stub
		
		logger.debug("[fileInfoToMD5] fileName :: " + uploadVO.getFileName() + ", size :: " + uploadVO.getFileSize());
		String MD5 = ""; 
		
		if ( uploadVO.getFileName() != null && !"".equals(uploadVO.getFileName())
				&& uploadVO.getFileSize() != null && !"".equals(uploadVO.getFileSize()) ) {
			
			String str = uploadVO.getFileName() + "|" + uploadVO.getFileSize();

			try{

				MessageDigest md = MessageDigest.getInstance("MD5"); 
				md.update(str.getBytes()); 
				byte byteData[] = md.digest();
				StringBuffer sb = new StringBuffer(); 
				for(int i = 0 ; i < byteData.length ; i++){
					sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
				}

				MD5 = sb.toString();
			}catch(NoSuchAlgorithmException e){
				e.printStackTrace(); 
				MD5 = null; 
			}
		} else {
			MD5 = null;
		}
		
		return MD5;
	}
	
	public String getFileSize(String fileName, String filepath) {
		String size="";
		File mFile = new File(filepath + File.separator + fileName);
	    if (mFile.exists() )
	    {
	        long lFileSize = mFile.length();
	        size = Long.toString(lFileSize);
	        logger.debug("[getFileSize] FileSize :: " + size + ", FullName :: " + filepath + File.separator + fileName);
	    } else {
	    	size = null;
	    }
		
		return size;
	}
	
	public String getFileSize(String filepath) {
		String size="";
		File mFile = new File(filepath);
	    if (mFile.exists() )
	    {
	        long lFileSize = mFile.length();
	        size = Long.toString(lFileSize);
	    } else {
	    	size = null;
	    }
		
		return size;
	}
	
	public Map<String, String> upload(MultipartFile multipartFile, String filepath) {
		String originFilename = null;
	    String uploadPath = filepath;
	    Map<String, String> map = new HashMap<String, String>();
		
		try {
			
			logger.debug("Upload Path :: " + uploadPath);
			
			// 파일 정보
			originFilename = multipartFile.getOriginalFilename();

			logger.debug("File Write Start");
			writeFile(multipartFile, originFilename, uploadPath);
			logger.debug("File Write End");
			
			map.put("filePath", uploadPath);
			map.put("fileName", originFilename);
	    	}
    	catch (IOException e) {
    		throw new RuntimeException(e);
    	}
    	return map;
  	}
	   
  	// File Write
  	private boolean writeFile(MultipartFile multipartFile, String saveFileName, String uploadPath)
                throws IOException{
  		boolean result = false;
  		
  		File file = new File(uploadPath);
  		
  		if ( !file.exists() ) {
  			file.mkdirs();	// 디렉토리가 존재 하지 않은경우 생성
  		}
  		
  		byte[] data = multipartFile.getBytes();
  		FileOutputStream fos = new FileOutputStream(uploadPath + File.separator + saveFileName);
  		fos.write(data);
  		fos.close();
     
  		return result;
  	}
  	
//  	private void download(String filePath, String fileName, HttpServletRequest rq, HttpServletResponse rs) throws UnsupportedEncodingException {
//  	// 파일명 지정        
//  		String browser = this.getBrowser(rq);
//  		String disposition = this.getDisposition(fileName, browser);
//  		rs.setContentType("application/octer-stream");
//  		rs.setHeader("Content-Transfer-Encoding", "binary;");
//  		rs.setHeader("Content-Disposition", disposition);
//        try {
//            OutputStream os = rs.getOutputStream();
//            FileInputStream fis = new FileInputStream(filePath);
// 
//            int ncount = 0;
//            byte[] bytes = new byte[512];
// 
//            while ((ncount = fis.read(bytes)) != -1 ) {
//                os.write(bytes, 0, ncount);
//            }
//            fis.close();
//            os.close();
//            
//        } catch (FileNotFoundException ex) {
//            logger.debug("FileNotFoundException");
//        } catch (IOException ex) {
//            logger.debug("IOException");
//        }
//  	}
//  	
//  	private String getBrowser(HttpServletRequest request) {
//		String header = request.getHeader("User-Agent");
//		if (header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1)
//			return "MSIE";
//		else if (header.indexOf("Chrome") > -1)
//			return "Chrome";
//		else if (header.indexOf("Opera") > -1)
//			return "Opera";
//		return "Firefox";
//	}
//  	
//  	private String getDisposition(String filename, String browser)
//			throws UnsupportedEncodingException {
//		String dispositionPrefix = "attachment;filename=";
//		String encodedFilename = null;
//		if (browser.equals("MSIE")) {
//			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll(
//					"\\+", "%20");
//		} else if (browser.equals("Firefox")) {
//			encodedFilename = "\""
//					+ new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
//		} else if (browser.equals("Opera")) {
//			encodedFilename = "\""
//					+ new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
//		} else if (browser.equals("Chrome")) {
//			StringBuffer sb = new StringBuffer();
//			for (int i = 0; i < filename.length(); i++) {
//				char c = filename.charAt(i);
//				if (c > '~') {
//					sb.append(URLEncoder.encode("" + c, "UTF-8"));
//				} else {
//					sb.append(c);
//				}
//			}
//			encodedFilename = sb.toString();
//		}
//		return dispositionPrefix + encodedFilename;
//	}
  	
////////////////////////////////////////////////////////////////////////////////////////////////////////  SFTP  	
  	
  		/**
	 * 파일을 서버에 SFTP를 통해 업로드  
	 * 
	 * @param 멀티파일, 분야
	 * 
	 * @return  map
	 */
  	/*** 로컬 -> 서버 파일업로드 ***/
  	private Session session = null; 
  	private Channel channel = null;
  	private ChannelSftp channelSftp = null;
  	
  	public Map<String, String> sftpUpload(MultipartFile multipartFile, String cate) throws IllegalStateException, IOException {
  		String originFilename = null;
	    String uploadPath = null;
	    FileInputStream in = null;
	    SftpATTRS attrs=null;
	    Map<String, String> map = new HashMap<String, String>();
	    
	   serverConnect();
	   
	   uploadPath = "/mnt/data/doc/";

	   	if ( "BOILER".equals(cate) )uploadPath = uploadPath + "boiler_src";
		else if ( "GT_TURBINE".equals(cate) ) uploadPath = uploadPath + "gs_turbin";
		else if ( "ST_TURBINE".equals(cate) ) uploadPath = uploadPath + "st_turbine";
		else if ( "GEN_PREV".equals(cate) ) uploadPath = uploadPath + "gen_prev";
		else if ( "GEN_INS".equals(cate) ) uploadPath = uploadPath + "gen_ins";
	   	
	   
   		logger.debug("Upload Path :: " + uploadPath);
		
		// 파일 정보
		originFilename = multipartFile.getOriginalFilename();

		logger.debug("File SFTP Write Start");
	   File uploadFile = new File(multipartFile.getOriginalFilename());
	   multipartFile.transferTo(uploadFile);
	   
	   try{ 
		   in = new FileInputStream(uploadFile); 
		   try {
			    	attrs = channelSftp.stat(uploadPath);
			} catch (Exception e) {
			    	logger.debug(uploadPath+" not found");
			}
		   if (attrs == null) channelSftp.mkdir(uploadPath);
		   
		   channelSftp.cd(uploadPath); 
		   channelSftp.put(in,originFilename); 
		   
	   }catch(SftpException se){ 
		   se.printStackTrace(); 
	   }catch(FileNotFoundException fe){
		   fe.printStackTrace(); 
	   }finally{ 
		   	try{ 
		   		in.close(); 
		   	} catch(IOException ioe){
		   		ioe.printStackTrace(); 
		   	} 
		} 
	   
	   	logger.debug("File SFTP Write End");
	    map.put("filePath", uploadPath);
	    map.put("fileName", originFilename);
	    
	    serverDisconnect();
	   	return map;
  	} 
  	

	 public void serverConnect(){
	   String host = "180.69.234.9";
	   int port = 922;
	   String user = "hanjun"; 
	   String password = "konan415!"; 
	   
	    
	   //JSch 객체 생성 
	  JSch jsch = new JSch();
	   try { 
		   	session = jsch.getSession(user, host, port);
            session.setPassword(password);
 
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
		   
	   } catch (JSchException e) { 
		   e.printStackTrace(); 
		   
	   } 
	   
	   channelSftp = (ChannelSftp) channel; 
	 }	 
	 
	 
  	  public void serverDisconnect() {
        if(session.isConnected()){
            channelSftp.disconnect();
            channel.disconnect();
            session.disconnect();
        }
    }

}


