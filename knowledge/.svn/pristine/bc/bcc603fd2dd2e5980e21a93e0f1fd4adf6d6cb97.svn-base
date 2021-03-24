package egovframework.kf.aop;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.service.LogService;
import egovframework.kf.common.service.SmartMatchService;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.user.vo.UserVO;

@Component
@Aspect
public class LogAdviceAOP {
	private static final Logger logger = LoggerFactory.getLogger(LogAdviceAOP.class);
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** Log Service */
	@Resource(name = "logService")
	private LogService logService;
	
	/** SmartMatchService */
	@Resource(name = "smartMatchService")
	private SmartMatchService smartMatchService;
	
	@Around("execution(* egovframework.kf.rest..*Controller.*(..))")
	public Object logPrint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		
		Object result = proceedingJoinPoint.proceed();
		
		String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
		String name = "";
		
		if ( type.contains("Controller") ) {
			name = "Controller : ";
		} else if ( type.contains("Service") ) {
			name = "Service : ";
		} else if ( type.contains("DAO") ) {
			name = "DAO : ";
		}
		
		long end = System.currentTimeMillis();
		
		logger.info(name + type + "." + proceedingJoinPoint.getSignature().getName() + "()");
		logger.info("Argument/Parameter : " + Arrays.toString(proceedingJoinPoint.getArgs()));
		logger.info("Running Time : " + (end - start));
		logger.info("------------------------------------------------------------------------");
		
		return result;
	}
	
	/**
	 * 파일 다운로드 후(AFTER) History log 남기기
	 * @param joinPoint
	 * @throws Throwable
	 */
	@After("execution(* egovframework.kf.common.controller.CommonController.fileDownload*(..)) ")
	public void doAfterFiledownLog(JoinPoint joinPoint)  throws Throwable {
	    // do stuff here
		logger.info("===============================================================================================================");
		logger.info("================= FileDownload AOP start : "+ joinPoint.getSignature().getName()+"==============================");
		
		int joinPointLength = joinPoint.getArgs().length;
		
		if ( joinPointLength > 0 ) {
			
			// 클라이언트 요청 정보를 가져오기 위해 request 객체를 생성
			HttpServletRequest request = 
			        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			
			// 사용자 정보를 가져오기 위해 Session에서 User정보를 가져옴
			UserVO user = (UserVO) request.getSession().getAttribute("login_user");
			
			// 파라메터 캐치
			Object[ ] getArgs = joinPoint.getArgs();
			
			Map<String, String> params = (Map<String, String>) getArgs[0];	// 첫번째 파라메터 형변환
			logger.info("[doAfterFiledownLog] params :: " + params.toString());
			
			if ( params != null ) {
				logger.info("[doAfterFiledownLog] write File Download Log...........");
				logService.insertFileDownLog(params, user, request);
			}
		}
		logger.info("================= FileDownload AOP end : "+ joinPoint.getSignature().getName()+"==============================");
		logger.info("===============================================================================================================");
		
	}
	
	/**
	 * 기술문서 탐색 상세페이지(AFTER) 최근 조회한 지식 History log 남기기
	 * @param joinPoint
	 * @throws Throwable
	 */
	@AfterReturning(pointcut="execution(* egovframework.kf.kepri..*Impl.detail(..)) ", returning="returnValue")
	public void doAfterRecentLog(JoinPoint joinPoint, Object returnValue)  throws Throwable {
	    // do stuff here
		logger.info("===============================================================================================================");
		logger.info("================= Recent Knowledge History AOP start : "+ joinPoint.getSignature().getName()+"==============================");
		
		// 클라이언트 요청 정보를 가져오기 위해 request 객체를 생성
		HttpServletRequest request = 
		        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		Object[] getArgs = joinPoint.getArgs();
		String category = getArgs[1].toString();
		logger.info("[doAfterRecentLog] cate :: " + category);
		RestResultVO result = (RestResultVO) returnValue;
		logger.info("[doAfterRecentLog] result :: " + result.getResult().get(0).toString());
		
		// 사용자 정보를 가져오기 위해 Session에서 User정보를 가져옴
		UserVO user = (UserVO) request.getSession().getAttribute("login_user");
		
		if ( result != null ) {
			logger.info("[doAfterRecentLog] write Recent Log...........");
			logService.insertRecentKnowledgeLog(result, user, category);
			
			// SmartMatch log 남기기용 category 약어(B_, GT_, ST_, GP_, GI_)
			String kind = commonUtil.getCategoryAcronym(category);
			String md5_key = result.getResult().get(0).get("MD5_KEY");
			String gubun_no = "";
			if ( result.getResult().get(0).containsKey("GUBUN_NO") ) {
				gubun_no = result.getResult().get(0).get("GUBUN_NO");
			} else {
				gubun_no = "0";
			}
			
			String key = kind + md5_key + "_" + gubun_no;
			
			// smartMatch Log를 남긴다. userId, Pk를 log로 쌓는다.
			boolean isLog = smartMatchService.writeSmartMatchLog(String.valueOf(user.getUser_id()), key, 1);
			if ( isLog ) {
				logger.debug("[getDetailData] log success");
			}
		}
		
		logger.info("================= Recent Knowledge History AOP end : "+ joinPoint.getSignature().getName()+"==============================");
		logger.info("===============================================================================================================");
		
	}
}
