package egovframework.kf.error.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class Name : ErrorController.java
 * Description : 에러시 에러페이지로 이동
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 * 2018년 08월  06일       이창호           최초 작성
 *
 * @since 2017년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Controller
@RequestMapping(value="/error")
public class ErrorController {
	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
	
	/*** 404 에러 ***/
	@RequestMapping(value = "/notFound.do")
	public String notFound(Model model) throws Exception {
		logger.debug("[notFound] 404 ERROR");		
		return "error/error404";
	}
}
