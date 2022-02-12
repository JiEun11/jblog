package com.poscoict.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscoict.jblog.service.UserService;
import com.poscoict.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(UserVo userVo) {
		
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/loginform", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public void loginInterceptor() {
		System.out.println("login 왜 터져");
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout() {
		
	}
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.removeAttribute("authUser");
//		session.invalidate();
//		
//		return "redirect:/";
//	}
	
//	@RequestMapping("/logout/{id}")
//	public String logout(@PathVariable("id") String id, HttpSession session) {
//		session.removeAttribute("authUser");
//		session.invalidate();
//		
//		return "redirect:/{id}";
//	}
}
