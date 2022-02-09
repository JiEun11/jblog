package com.poscoict.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.jblog.service.BlogService;
import com.poscoict.jblog.vo.BlogVo;

public class BlogInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private BlogService blogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String RequestURI = request.getRequestURI();	// /jblog03/je/admin/basic
		String contextPath = request.getContextPath(); 	// /jblog03
		String command = RequestURI.substring(contextPath.length());	// /je/admin/basic
		String userId = command.split("/")[1];	// je
		
		BlogVo blogVo = blogService.getBlog(userId);
		if(blogVo == null) {
			// 없는 id면 메인으로 돌려보내기 
			response.sendRedirect(request.getContextPath());
			return false;
		}
		request.setAttribute("blogVo", blogVo);				
		System.out.println("interceptor에서 blogVo : " + blogVo);
		return true;
	}

	
}
