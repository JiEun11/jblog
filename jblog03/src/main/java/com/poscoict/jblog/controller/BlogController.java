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
//@RequestMapping("/{blogId :(?!assets|image|search).*}")
//@RequestMapping("/{id :(?!assets|images).*}")
@RequestMapping("/{id}")
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
		for(CategoryVo cate : cateList) {
			System.out.println("cate: " + cate);
		}
		List<PostVo> postList = postService.getPost(cateList.get(0).getNo());
		PostVo postOne = postService.getRecent(cateList.get(0).getNo());
		servletContext.setAttribute("blogVo", blogVo);
		servletContext.setAttribute("cateList", cateList);
//		servletContext.setAttribute("postList", postList);
//		servletContext.setAttribute("postOne", postVo);
//		model.addAttribute("cateList",cateList);
		model.addAttribute("postList",postList);
		model.addAttribute("postOne",postOne);
		return "/blog/blog-main";
	}
	
	@RequestMapping(value="/{no}", method=RequestMethod.GET)
	public String main(@PathVariable("no") Long categoryNo, Model model) {
		List<PostVo> postList = postService.getPost(categoryNo);
		PostVo postOne = postService.getRecent(categoryNo);
		if(postList==null) {
			System.out.println("없는 카테고리 번호");
		}
		model.addAttribute("postList",postList);
		model.addAttribute("postOne",postOne);
		return "/blog/blog-main";
	}
	
	@RequestMapping(value="/{categoryNo}/{no}", method=RequestMethod.GET)
	public String main(@PathVariable("categoryNo") Long categoryNo, @PathVariable("no") Long no, Model model) {
		List<PostVo> postList = postService.getPost(categoryNo);
		PostVo postOne = postService.getOnePost(no);
		
		if(postList==null) {
			System.out.println("없는 카테고리 번호");
		}
		if(postOne==null) {
			System.out.println("없는 포스트 번호");
		}
		
		model.addAttribute("postList",postList);
		model.addAttribute("postOne",postOne);
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
