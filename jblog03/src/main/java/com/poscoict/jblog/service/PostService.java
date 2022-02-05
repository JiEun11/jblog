package com.poscoict.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.jblog.repository.PostRepository;
import com.poscoict.jblog.vo.PostVo;

@Service
public class PostService {

	@Autowired 
	private PostRepository postRepository;
	
	
	
	public List<PostVo> getPost(Long categoryNo) {
		List<PostVo> postList = postRepository.findByNo(categoryNo);
		return postList;
	}

	public PostVo getRecent(Long categoryNo) {
		PostVo postOne = postRepository.recentOne(categoryNo);
		return postOne;
	}
}
