package egovframework.kf.login.interceptor;

import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.kf.common.DCUtil;
import egovframework.kf.user.vo.UserVO;
import egovframework.rte.fdl.property.EgovPropertyService;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Resource(name = "dcUtil")
	private DCUtil dcUtil;
	
	List<String> urls;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	
	
	public void setUrls(List urls) {
		this.urls = urls;
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession(false);
		Enumeration enume =(Enumeration) request.getHeaderNames();
		String hkey ="";
		String loginUrl = konanPropertiesService.getString("loginUrl");
		
		while(enume.hasMoreElements()) {
			hkey = enume.nextElement().toString();
			if("x-requested-with".equals(hkey)) break; 
		}
		
		if(session == null){
			logger.debug("[preHandle] session is null.");
			request.getSession().getServletContext().setAttribute("ssUserId", null);
			request.getSession().getServletContext().setAttribute("ssUserName", null);
			request.getSession().getServletContext().setAttribute("ssAdminYN", null);
			
			if("x-requested-with".equals(hkey)) {
				logger.debug("[preHandle] session 끊김.");
			}
			else {
				logger.debug("[preHandle] session is null");
				
				// 챗봇에서 이미지 불러왔을 때
				String uri = request.getRequestURI();
				logger.debug("[preHandle] uri :: " + uri);
				String funcMenu[] = {"displayImg"};
				for(String uriStr : funcMenu){
					if(uri.indexOf(uriStr)!=-1){
						return true;
					}
				}
				//response.sendRedirect(request.getContextPath() + "/login.do");
				response.sendRedirect(loginUrl);
			}
			return false;
		}else{
			logger.debug("[preHandle] session is not null.");
			UserVO  user = (UserVO) session.getAttribute("login_user");
			
			if(user == null){
					
					// API 제공용 URL
					String uri = request.getRequestURI();
					logger.debug("[preHandle] uri :: " + uri);
					response.sendRedirect(loginUrl);
//				}
				return false;
			}
		}
		
		return true;
	}
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception {
		
		super.postHandle(request, response, handler, modelAndView);
	}
}
