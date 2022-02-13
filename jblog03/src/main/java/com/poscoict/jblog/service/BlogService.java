package com.poscoict.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.jblog.repository.BlogRepository;
import com.poscoict.jblog.repository.CategoryRepository;
import com.poscoict.jblog.repository.PostRepository;
import com.poscoict.jblog.vo.BlogVo;
import com.poscoict.jblog.vo.CategoryVo;
import com.poscoict.jblog.vo.PostVo;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	public BlogVo getBlog(String id) {  
		return blogRepository.findById(id);
	}

	public boolean updateBlog(BlogVo blogVo) {
		return blogRepository.updateBlogInfo(blogVo)==1;
		
	}

	public Map<String, Object> getBlogMain(String userId, Long categoryNo, Long postNo) {
		
		// 1. userId(blogId)로 category 리스트 받아오기(무조건 미분류는 존재->최소1개 값은 존재)
		List<CategoryVo> cateList = categoryRepository.findAllById(userId);
		
		// 2. categoryNo 없이 검색한 경우 or 없는 카테고리인 경우
		// 가장 최신 카테고리 번호로, 가장 최신 포스트 번호로 설정   
		if(categoryNo==0 || categoryRepository.checkCateExist(categoryNo)==null) {
			categoryNo = categoryRepository.getMaxNo(userId);
			postNo= postRepository.findMaxNoByNo(categoryNo);
		}
		
		// 3. categoryNo 값으로 postList 부르기
		List<PostVo> postList = postRepository.findAllByCateNo(categoryNo);
		
		// 4. postNo 값으로 한 개의 post 정보 가져오기
		PostVo postOne = new PostVo();
		
		// 5. 작성된 글이 없는 경우의 처리 
		if(postList.isEmpty()) {
			postOne.setTitle("어서오세요");
			postOne.setContents("아직 작성된 글이 없습니다.");
		}else {
			postOne = postRepository.findOneByNo(postNo);
		}
		
		// 6. 리스트 정보 및 객체 정보 맵에 저장 
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cateList", cateList);
		map.put("postList", postList);
		map.put("postOne", postOne);
		
		return map;
	}
	
	
}
