package egovframework.kf.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import egovframework.kf.user.service.UserService;
import egovframework.kf.user.vo.UserVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : CommonController.java
 * Description : 공통 기능
 *
 * @since 201903
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Controller
@RequestMapping(value="/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/** User Service */
	@Resource(name = "userService")
	private UserService userService;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	
	
	// 사용자 관리 페이지
	@RequestMapping(value = "/userList.do")
	public String userList(Model model) throws Exception {
		logger.debug("[userList] Go to the userList Page");
				
//		int userTotal = 0;
//		Map<String, Object> requestMap = new HashMap<String, Object>();
//		requestMap.put("start", 0);
//		requestMap.put("offset", 10);
//		
//		List<UserVO> userList = userService.selectUserList(requestMap);
//		
//		if ( userList != null && userList.size() > 0 ) {
//			userTotal = userList.size();
//			logger.debug("[getUserList] size :" + userTotal);
//		}
		
		model.addAttribute("bigMenu", "admin");
		model.addAttribute("menu", "user");
//		model.addAttribute("userTotal", userTotal);
//		model.addAttribute("userResult", userList);
		
		return "main";
	}
	
	@RequestMapping(value = "/getUserList.do", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String getUserList(@RequestParam int start, @RequestParam int offset, Model model, HttpServletRequest request) throws Exception {
				
		logger.debug("[getUserList] getUserList");
		
		Gson gson = new Gson();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int userTotal = 0;
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("start", start);
		requestMap.put("offset", offset);
		
		//////////////////////////// User 리스트 조회
		List<UserVO> userList = userService.selectUserList(requestMap);
		if ( userList != null && userList.size() > 0 ) {
			userTotal = userList.size();
			logger.debug("[getUserList] size :" + userTotal);
		}
		
		resultMap.put("aaData", userList);
		resultMap.put("userTotal", userTotal);
		
		return gson.toJson(resultMap);
	}
	
	@RequestMapping(value = "/userAdd.do", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String userAdd(@ModelAttribute UserVO userVO, Model model, HttpServletRequest request) throws Exception {
				
		Gson gson = new Gson();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String resultMessage = "fail";
		int r = -1;
		
		logger.debug("[userAdd] Start");
		logger.debug("[userAdd] User VO :" + userVO.toString());
		
		if ( userVO != null ) {
			UserVO creater = (UserVO) request.getSession().getAttribute("login_user");			
			r = userService.insertUser(userVO, creater.getUser_nm());
			
			if ( r == 1 ) {
				resultMessage = "success";
			}
		}
		
		resultMap.put("resultMessage", resultMessage);
		
		return gson.toJson(resultMap);
	}
	
	@RequestMapping(value = "/userMod.do", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String userMod(@ModelAttribute UserVO userVO, Model model, HttpServletRequest request) throws Exception {
				
		Gson gson = new Gson();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String resultMessage = "fail";
		int r = -1;
		
		logger.debug("[userMod] Start");
		logger.debug("[userMod] User VO :" + userVO.toString());
		
		if ( userVO != null ) {
			UserVO creater = (UserVO) request.getSession().getAttribute("login_user");			
			r = userService.updateUser(userVO, creater.getUser_nm());
			
			if ( r == 1 ) {
				resultMessage = "success";
			}
		}
		
		resultMap.put("resultMessage", resultMessage);
		
		return gson.toJson(resultMap);
	}
	
	@RequestMapping(value = "/userDel.do", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String userDel(@RequestParam(value="deleteArr[]") List<String> deleteArr, Model model, HttpServletRequest request) throws Exception {
				
		Gson gson = new Gson();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String resultMessage = "fail";
		int r = -1;
		
		logger.debug("[userDel] Start");
		logger.debug("[userDel] deleteArr : " + deleteArr.size());
		
		for ( int i=0; i<deleteArr.size(); i++ ) logger.debug("[userDel] delete :" + deleteArr.get(i) );
		
		if ( deleteArr.size() > 0 ) {
			r = userService.deleteUser(deleteArr);
			
			if ( r > 0 ) {
				resultMessage = "success";
			}
		}
		
		resultMap.put("resultMessage", resultMessage);
		
		return gson.toJson(resultMap);
	}
	
	@RequestMapping(value = "/getUserStatistics.do", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String getUserStatistics(Model model, HttpServletRequest request) throws Exception {
		
		Gson gson = new Gson();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int userTotal = 0;	
			
		////////////////////////////User 통계 시작
		
		userTotal = userService.selectUserCount();
		if ( userTotal < 1 ) {
			logger.debug("[userStatistics] userTotal :: " + userTotal);
			userTotal = 0;
		}
		resultMap.put("userTotal", userTotal);
		
		List<Map> partDivGroupBy = new ArrayList<Map>();	// 부서별
		partDivGroupBy = userService.selectUserPartDivCount();
		if ( partDivGroupBy.size() < 1 ) {
			logger.debug("[userStatistics] partDivGroupBy :: " + partDivGroupBy.size());
			partDivGroupBy = null;
		}
		resultMap.put("partDivList", partDivGroupBy);
		
		List<Map> positionGroupBy = new ArrayList<Map>();	// 직급별
		positionGroupBy = userService.selectUserPositionGDCount();
		if ( positionGroupBy.size() < 1 ) {
			logger.debug("[userStatistics] positionGroupBy :: " + positionGroupBy.size());
			positionGroupBy = null;
		}
		resultMap.put("positionList", positionGroupBy);
		
		List<Map> authGBGroupBy = new ArrayList<Map>();	// 권한별
		authGBGroupBy = userService.selectUserAuthGBCount();
		if ( authGBGroupBy.size() < 1 ) {
			logger.debug("[userStatistics] authGBGroupBy :: " + authGBGroupBy.size());
			authGBGroupBy = null;
		}
		resultMap.put("authGBList", authGBGroupBy);
		//////////////////////////// User 통계 종료
		
		return gson.toJson(resultMap);
	}
}

