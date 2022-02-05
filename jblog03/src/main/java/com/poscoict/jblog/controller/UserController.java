package com.poscoict.jblog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session, @RequestParam(value="id", required=true, defaultValue="") String id,
			@RequestParam(value="password", required=true, defaultValue="")String password,
			Model model) {
		// method 이름이 login이 아니라 getUser임을 주의 
		UserVo authUser = userService.getUser(id, password); 
		System.out.println(id + ", " + password );
		
		if(authUser == null) {
			//로그인 틀렸다면 다시 로그인 해라 
			model.addAttribute("result", "fail");
			model.addAttribute("id", id);
			return "user/login";
		}
		
		/* 인증 처리 */
		session.setAttribute("authUser", authUser);	//filter로 빠져야하지만 지금은 해줌 
		
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/";
	}
	
	@RequestMapping("/logout/{id}")
	public String logout(@PathVariable("id") String id, HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/{id}";
	}
}
