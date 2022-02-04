package com.poscoict.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/{id}")
public class BlogController {
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String main() {
		
		return "/blog/blog-main";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String basic() {
		return "/blog/blog-admin-basic";
	}
	
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String category() {
		return "/blog/blog-admin-category";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String write() {
		return "/blog/blog-admin-write";
	}
	
}
