package com.poscoict.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.jblog.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 1. handler 종류 확인 필요
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		
		// 2. Casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 3. Handler Method의 @Auth 받아오기
		// 없으면 auth에 null return 
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. Handler Method @Auth가 없으면 Type(Class)에 있는지 확인 
		if(auth == null) {
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}
		
		// 5. type(4)과 method(3)에 @Auth가 적용이 안 되어 있는 경우
		if(auth==null) {
			return true;
			// @Auth가 없다면 인증 필요 없다는 뜻이므로 return true로 Cont 실행 
		}
		
		// 6. @Auth가 적용 되어 있기 때문에 인증(Authentication)여부 확인
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		// 7. session이 존재하면 유효한 User인지 확인
		UserVo authUser = (UserVo)session.getAttribute("authuser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 8. 권한(Authorization) 체크를 위해서 현재 페이지의 blogId(userId)를 확인 
		String RequestURI = request.getRequestURI();	// /jblog03/je/admin/basic
		String contextPath = request.getContextPath(); 	// /jblog03
		String command = RequestURI.substring(contextPath.length());	// /je/admin/basic
		String userId = command.split("/")[1];	// je
		
		if(!userId.equals(authUser.getId())) {
			System.out.println("현재 페이지의 blogId와 로그인 하신 userId가 다릅니다.");
			response.sendRedirect(request.getContextPath() + "/"+userId);
			return false;
		}
		// 접근 허가 
		return true;
	}

	
}
