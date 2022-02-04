package com.poscoict.jblog.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscoict.jblog.service.BlogService;

@Controller
@RequestMapping(value="/{id}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String main(@PathVariable("id") String id, Model model) {
		
		Map<String,Object> map = blogService.getPost(id);
		
		model.addAttribute("map",map);
		return "/blog/blog-main";
	}
	
	@RequestMapping(value="/{cateNo}", method=RequestMethod.GET)
	public String main(@PathVariable("no") Long cateNo) {
		
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
