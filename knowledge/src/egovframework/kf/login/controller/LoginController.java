package egovframework.kf.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/** User Service */
	@Resource(name = "userService")
	private UserService userService;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	
	
	
	// 로그인 화면
	@RequestMapping(value = "/login.do")
	public String login(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("[login] Go to the login Page");
		return "login/login";
	}
	
	@RequestMapping(value = "/loginCheck.do", method = RequestMethod.GET)
	public void loginCheck(@RequestParam Map<String, String> map, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		UserVO userVO = userService.selectUserInfo(map);
		String mainUrl = konanPropertiesService.getString("mainUrl");
		String loginUrl = konanPropertiesService.getString("loginUrl");
		
		if ( userVO != null ) {
			logger.debug("[userCheck] login Success");
			request.getSession().invalidate();
			userVO.setUser_pwd(null);
			request.getSession().setAttribute("login_user", userVO);
			
			request.getSession().getServletContext().setAttribute("ssUserId", userVO.getUser_id());
			request.getSession().getServletContext().setAttribute("ssUserName", userVO.getUser_nm());
			request.getSession().getServletContext().setAttribute("ssAdminYN", userVO.getAdmin_yn());
			
			response.sendRedirect(mainUrl);
			
			
		} else {
			logger.debug("[userCheck] login Fail");
			response.sendRedirect(loginUrl);
		}
	}
	
	@RequestMapping(value = "/ssoCheck.do", method = RequestMethod.GET)
	public void ssoCheck(@ModelAttribute UserVO userVO, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		logger.debug("[ssoCheck] userVO :: " + userVO.toString());
		String loginUrl = konanPropertiesService.getString("loginUrl");
		String mainUrl = konanPropertiesService.getString("mainUrl");
		
		if ( userVO != null ) {
			logger.debug("[ssoCheck] login Success");
			request.getSession().invalidate();
			userVO.setUser_pwd(null);
			userVO.setAdmin_yn('Y');
			userVO.setDel_yn('N');
			request.getSession().setAttribute("login_user", userVO);
			
			request.getSession().getServletContext().setAttribute("ssUserId", userVO.getUser_id());
			request.getSession().getServletContext().setAttribute("ssUserName", userVO.getUser_nm());
			request.getSession().getServletContext().setAttribute("ssAdminYN", userVO.getAdmin_yn());
			
			//response.sendRedirect("problemFocus/pfAnalysis.do?category=BOILER");
			response.sendRedirect(mainUrl);
		} else {
			logger.debug("[ssoCheck] login Fail");
			response.sendRedirect(loginUrl);
		}
	}
	
	/**
	 * 로그아웃을 하여 세션을 종료한다.
	 * method 방식을 구분해서 으로 json과 url 이동을 할 수 있도록 구분했다.
	 * @param map
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public void logout(@RequestParam Map<String, String> map, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("[logout] logout Success");
		
		String loginUrl = konanPropertiesService.getString("loginUrl");
		
		// session 삭제
		request.getSession().invalidate();
		request.getSession().removeAttribute("userName");
		
		response.sendRedirect(loginUrl);
	}
	
	/**
	 * 로그아웃을 하여 세션을 종료한다.
	 * method 방식을 구분해서 으로 json과 url 이동을 할 수 있도록 구분했다.
	 * @param map
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout.do", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String logout_json(@RequestParam Map<String, String> map, Model model, HttpServletRequest request) throws Exception {
				
		Gson gson = new Gson();
		Map<String, String> resultMap = new HashMap<String, String>();
		String resultMessage = "";
		
		logger.debug("[logout] logout Success");
		resultMessage = "success";
		// session 삭제
		request.getSession().invalidate();
		request.getSession().removeAttribute("userName");
		
		resultMap.put("result", resultMessage);
		
		return gson.toJson(resultMap);
	}
	
	@RequestMapping(value = "/session/getSession_dev.do", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String seTest(@RequestParam Map<String, String> map, Model model, HttpServletRequest request) throws Exception {
				
		Gson gson = new Gson();
		Map<String, String> resultMap = new HashMap<String, String>();
		
		resultMap.put("userId", "admin");
		resultMap.put("password", "admin");
		resultMap.put("role", "ROLE_ADMIN");
		
		return gson.toJson(resultMap);
	}
}

