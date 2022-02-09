package com.poscoict.jblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.jblog.security.Auth;
import com.poscoict.jblog.service.BlogService;
import com.poscoict.jblog.service.CategoryService;
import com.poscoict.jblog.service.FileUploadService;
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
	private FileUploadService fileUploadService;

	
	@RequestMapping({"", "/{pathNo1}", "/{pathNo1}/{pathNo2}"})
	public String main(@PathVariable("id") String userId,
						@PathVariable("pathNo1") Optional<Long> pathNo1,
						@PathVariable("pathNo2") Optional<Long> pathNo2,
						Model model) {
		Long categoryNo = 0L;
		Long postNo = 0L;
		Map<String,Object> map = new HashMap<String, Object>();
		List<CategoryVo> cateList = null;
		List<PostVo> postList = null;
		PostVo postOne = null;
		
//		BlogVo blogVo = blogService.getBlog(id);
		
//		if(blogVo==null) {
//			return "redirect:/";	// 없는 id로 path 입력 시 메인으로 
//		}
		/* 여기까지 왔다는 건 id있다는 얘기이므로 category List 추출 */
		cateList = categoryService.getCategory(userId);	 
		System.out.println("cateList : " + cateList );
		
		if(pathNo2.isPresent()) {
			categoryNo = pathNo1.get();
			postNo = pathNo2.get();
		}else if(pathNo1.isPresent()) {
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
			if(postList.isEmpty()) {
				postOne = new PostVo();
				postOne.setTitle("어서오세요");
				postOne.setContents("첫 글입니다.");
			}else {
				postOne = postService.getRecentOne(postList.get(0).getCategoryNo());				
			}
			System.out.println("postOne 후 : " + postOne);
		}

//		model.addAttribute("blogVo", blogVo);
		map.put("cateList", cateList);
		map.put("postList", postList);
		map.put("postOne", postOne);
		model.addAttribute("map",map);

		return "/blog/blog-main";
	}
	
	@Auth
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String basic(@PathVariable("id") String userId) {
		
//		blogVo = blogService.getBlog(userId);
//		System.out.println(blogVo);
//		
//		model.addAttribute("blogVo", blogVo);
//		System.out.println();
		return "/blog/blog-admin-basic";
	}
	
	@Auth
	@RequestMapping(value="/admin/basic", method=RequestMethod.POST)
	public String basicUpdate(@PathVariable("id") String userId, 
							BlogVo blogVo, 
							@RequestParam(value="logo-file")MultipartFile mutipartFile,
							Model model) {
		
		blogVo.setUserId(userId);
		String logo = fileUploadService.restore(mutipartFile);
		System.out.println("logo >>>>>> "+logo);
		if(logo!=null) {
			blogVo.setLogo(logo);
		}
		blogService.updateBlog(blogVo);
		System.out.println(".>>>>>>>>>>>> blogVo : "+blogVo);
//		model.addAttribute("blogVo", blogVo);
		return "redirect:/"+userId+"/admin/basic";
	}
	
	@Auth
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String category(@PathVariable("id") String userId,
			Model model
							) {
		// post들으로 넘기는 데이터들 or url로 넘기는 데이터들을 받는 경우 
		
		List<CategoryVo> cateList = categoryService.getCategory(userId);
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
	
	@Auth
	@RequestMapping(value="/admin/category/delete/{no}", method=RequestMethod.GET)
	public String categoryDelete(@PathVariable("id") String userId,
								@PathVariable(value="no") Long categoryNo) {
		
		categoryService.deleteCategory(categoryNo);
		return "redirect:/"+ userId +"/admin/category";
	}
	
	@Auth
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String write(@PathVariable("id") String userId, Model model) {
		
		List<CategoryVo> cateList = categoryService.getCategory(userId);
//		BlogVo blogVo = blogService.getBlog(userId);
		model.addAttribute("cateList",cateList);
//		model.addAttribute("blogVo",blogVo);
		return "/blog/blog-admin-write";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String writePost(@PathVariable("id") String userId,
							PostVo postVo) {
		System.out.println("postVo : " + postVo);
		postService.addPost(postVo);
		return "redirect:/"+userId+"/admin/basic";
	}
	
}
