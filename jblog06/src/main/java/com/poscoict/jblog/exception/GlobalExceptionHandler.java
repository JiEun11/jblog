package com.poscoict.jblog.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public String ExceptionHandler(Model model, Exception e) {
		// 1. loging
		// String errors = e.toString();

		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		System.out.println(errors.toString());
		LOGGER.error(errors.toString());

		// 404 Error 처리
		if (e instanceof NoHandlerFoundException) {
			return "error/404";
		}
		errors.toString();
		model.addAttribute("exception", errors.toString());

		// 404 에러 처리

		// 2. 사과 페이지 (HTML 응답, 정상 종료)
		return "error/exception";

		// 3.

	}
}
