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
	
	
	public List<PostVo> getPostAll(Long categoryNo) {
		return postRepository.findAllByCateNo(categoryNo);
	}

	public PostVo getRecentOne(Long categoryNo) {
		return postRepository.findRecentOne(categoryNo);
	}
	
	public PostVo getOnePost(Long no) {
		return postRepository.findOneByNo(no);
	}
	
	public boolean addPost(PostVo postVo) {
		return postRepository.insertPost(postVo)==1;
	}
	
	
}
