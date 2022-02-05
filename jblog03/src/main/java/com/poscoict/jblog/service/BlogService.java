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
	
	public Map<String,Object> getPost(String id) {

		// 1. 해당 id에 대한 blog 정보 가져오기  
		BlogVo blogVo = blogRepository.findById(id);
		
		// 2. 해당 id에 대한 category 정보 가져오기 
		List<CategoryVo> cateList = categoryRepository.findById(id);
		System.out.println(cateList.get(0).getNo());
		
		// 3. 해당 id의 category의 category no에 대한 post 정보 가져오기
		List<PostVo> postList = postRepository.findByNo(cateList.get(0).getNo());
		PostVo postOne = postRepository.recentOne(cateList.get(0).getNo());
		
		// 4. 가져온 blogVo, List 객체 map에 저장 
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("blogVo", blogVo);
		map.put("cateList", cateList);
		map.put("postList", postList);
		map.put("postOne", postOne);
		
		
		return map;
	}

}
