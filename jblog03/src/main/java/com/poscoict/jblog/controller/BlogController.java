package com.poscoict.jblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscoict.jblog.service.BlogService;
import com.poscoict.jblog.service.CategoryService;
import com.poscoict.jblog.service.PostService;
import com.poscoict.jblog.vo.BlogVo;
import com.poscoict.jblog.vo.CategoryVo;
import com.poscoict.jblog.vo.PostVo;

@Controller
@RequestMapping(value="/{id}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String main(@PathVariable("id") String id, Model model) {
		
		BlogVo blogVo = blogService.getBlog(id);
		List<CategoryVo> cateList = categoryService.getCategory(id);
		List<PostVo> postList = postService.getPost(cateList.get(0).getNo());
		PostVo postVo = postService.getRecent(cateList.get(0).getNo());
		servletContext.setAttribute("blogVo", blogVo);
		servletContext.setAttribute("cateList", cateList);
		servletContext.setAttribute("postList", postList);
		servletContext.setAttribute("postOne", postVo);
//		model.addAttribute("map",map);
		return "/blog/blog-main";
	}
	
	@RequestMapping(value="/{categoryNo}", method=RequestMethod.GET)
	public String main(@PathVariable("no") Long categoryNo, Model model) {
		List<PostVo> plist = postService.getPost(categoryNo);
		
		model.addAttribute("plist",plist);
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
