package com.poscoict.jblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.jblog.service.BlogService;
import com.poscoict.jblog.service.CategoryService;
import com.poscoict.jblog.service.FileUploadService;
import com.poscoict.jblog.service.PostService;
import com.poscoict.jblog.vo.BlogVo;
import com.poscoict.jblog.vo.CategoryVo;
import com.poscoict.jblog.vo.PostVo;
import com.poscoict.jblog.vo.UserVo;

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
	private FileUploadService fileUploadService;

	
	@RequestMapping({"", "/{pathNo1}", "/{pathNo1}/{pathNo2}"})
	public String main(@PathVariable("id") String id,
						@PathVariable("pathNo1") Optional<Long> pathNo1,
						@PathVariable("pathNo2") Optional<Long> pathNo2,
						Model model) {
		Long categoryNo = 0L;
		Long postNo = 0L;
		Map<String,Object> map = new HashMap<String, Object>();
		List<CategoryVo> cateList = null;
		List<PostVo> postList = null;
		PostVo postOne = null;
		
		BlogVo blogVo = blogService.getBlog(id);
		System.out.println("blogVo : " + blogVo);
		if(blogVo==null) {
			return "redirect:/";	// 없는 id로 path 입력 시 메인으로 
		}
		/* 여기까지 왔다는 건 id있다는 얘기이므로 category List 추출 */
		cateList = categoryService.getCategory(id);	 
		System.out.println("cateList : " + cateList );
		if(pathNo2.isPresent()) {
			categoryNo = pathNo1.get();
			postNo = pathNo2.get();
		}
		
		if(pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
		}
		
		/* categoryNo값으로 postList 부르기 */
		postList = postService.getPostAll(categoryNo);	
		if(postList.isEmpty()) {
			System.out.println("postList 전 :" + postList);
			// 없는 category번호였다면 제일 상단 카테고리번호로 postList 부르기 
			postList = postService.getPostAll(cateList.get(0).getNo());
			System.out.println("postList 가져온 후 :" + postList);
		}
		
		/* postNo 값으로 post 한 개 부르기 */
		postOne = postService.getOnePost(postNo);
		if(postOne==null) {
			// 없는 postNo였다면 제일 상단 post 보여주기 
			System.out.println("postOne 전 : " + postOne);
			postOne = postService.getRecentOne(postList.get(0).getCategoryNo());
			System.out.println("postOne 후 : " + postOne);
		}
//		servletContext.setAttribute("blogVo", blogVo);
//		servletContext.setAttribute("cateList", cateList);
//		servletContext.setAttribute("postList", postList);
//		servletContext.setAttribute("postOne", postOne);
		model.addAttribute("blogVo", blogVo);
		map.put("cateList", cateList);
		map.put("postList", postList);
		map.put("postOne", postOne);
		model.addAttribute("map",map);
//		model.addAttribute("cateList",cateList);
//		model.addAttribute("postList",postList);
//		model.addAttribute("postOne",postOne);
		return "/blog/blog-main";
	}
	
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String basic(HttpSession session, BlogVo blogVo, Model model) {
		/* access control */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		blogVo = blogService.getBlog(authUser.getId());
		System.out.println(blogVo);
		
		model.addAttribute("blogVo", blogVo);
		return "/blog/blog-admin-basic";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.POST)
	public String basicUpdate(HttpSession session, 
							BlogVo blogVo, 
							@RequestParam(value="logo-file")MultipartFile mutipartFile,
							Model model) {
		
		/* access control */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		String logo = fileUploadService.restore(mutipartFile);
		blogVo.setUserId(authUser.getId());
		
		if(logo!=null) {
			blogVo.setLogo(logo);
			blogService.updateBlog(blogVo);
		}
		System.out.println(".>>>>>>>>>>>> blogVo : "+blogVo);
		model.addAttribute("blogVo", blogVo);
		return "redirect:/"+authUser.getId()+"/admin/basic";
	}
	
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String category(HttpSession session,Model model,BlogVo blogVo) {
		/* access control */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		blogVo = blogService.getBlog(authUser.getId());
		
		List<CategoryVo> cateList = categoryService.getCategory(authUser.getId());
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("cateList", cateList);
		
		return "/blog/blog-admin-category";
	}
	
	@RequestMapping(value="/admin/category/add", method=RequestMethod.POST)
	public String categoryAdd(@PathVariable("id") String userId, CategoryVo categoryVo, Model model) {
	
		categoryVo.setBlogId(userId); 
		categoryVo.setPostCnt((long)0);
		System.out.println("category add " + categoryVo);
		categoryService.addCategory(categoryVo);
		
		List<CategoryVo > cateList = categoryService.getCategory(userId);
		model.addAttribute("cateList",cateList);
		return "redirect:/"+ userId +"/admin/category";
	}
	
	@RequestMapping(value="/admin/category/delete/{no}", method=RequestMethod.GET)
	public String categoryDelete(HttpSession session, @PathVariable("id") String userId,
				@PathVariable(value="no") Long categoryNo) {
		/* access control */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		categoryService.deleteCategory(categoryNo);
		return "redirect:/"+ userId +"/admin/category";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String write(HttpSession session, @PathVariable("id") String userId,Model model) {
		/* access control */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		List<CategoryVo> cateList = categoryService.getCategory(userId);
		BlogVo blogVo = blogService.getBlog(authUser.getId());
		model.addAttribute("cateList",cateList);
		model.addAttribute("blogVo",blogVo);
		return "/blog/blog-admin-write";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String writePost(@PathVariable("id") String userId) {
		return "redirect:/";
	}
	
}
