package com.poscoict.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/{id:(?!assets).*}")
//@RequestMapping("/{id}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping({"", "/{pathNo1}", "/{pathNo1}/{pathNo2}"})
	public String main(@PathVariable("id") String id,
						@PathVariable("pathNo1") Optional<Long> pathNo1,
						@PathVariable("pathNo2") Optional<Long> pathNo2) {
		Long categoryNo = 0L;
		Long postNo = 0L;
		List<CategoryVo> cateList = null;
		List<PostVo> postList = null;
		PostVo postOne = null;
		
		BlogVo blogVo = blogService.getBlog(id);
		if(blogVo==null) {
			return "redirect:/";	// 없는 id로 path 입력 시 메인으로 
		}
		/* 여기까지 왔다는 건 id있다는 얘기이므로 category List 추출 */
		cateList = categoryService.getCategory(id);	 
		
		if(pathNo2.isPresent()) {
			categoryNo = pathNo1.get();
			postNo = pathNo2.get();
		}
		
		if(pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
		}
		System.out.println("categoryNo : " + categoryNo);
		System.out.println("postNo : " + postNo);
		
		/* categoryNo값으로 postList 부르기 */
		postList = postService.getPostAll(categoryNo);	
		System.out.println(categoryNo + " : categoryNo 로 postList 부름 " + postList);
		if(postList.isEmpty()) {
			// 없는 category번호였다면 제일 상단 카테고리번호로 postList 부르기 
			postList = postService.getPostAll(cateList.get(0).getNo());
			System.out.println("postList가 null이었네!!!!!최근꺼 가져와 " +postList);
		}
		
		/* postNo 값으로 post 한 개 부르기 */
		postOne = postService.getOnePost(postNo);
		System.out.println(postNo+" : postNo로 postOne 부름 " + postOne);
		if(postOne==null) {
			// 없는 postNo였다면 제일 상단 post 보여주기 
			postOne = postService.getRecentOne(postList.get(0).getCategoryNo());
			System.out.println("postOne이 null이었네!!!!최근꺼 가져와 " + postOne);
		}
		servletContext.setAttribute("blogVo", blogVo);
		servletContext.setAttribute("cateList", cateList);
		servletContext.setAttribute("postList", postList);
		servletContext.setAttribute("postOne", postOne);
//		model.addAttribute("cateList",cateList);
//		model.addAttribute("postList",postList);
//		model.addAttribute("postOne",postOne);
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
