package com.poscoict.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.jblog.repository.BlogRepository;
import com.poscoict.jblog.repository.CategoryRepository;
import com.poscoict.jblog.repository.UserRepository;
import com.poscoict.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public boolean join(UserVo userVo) {

		int resultUser = userRepository.insert(userVo);
		int resultBlog = blogRepository.insert(userVo);
		int resultCate = categoryRepository.insert(userVo.getId());

		return resultUser==1 && resultBlog==1 && resultCate == 1;
	}

	public UserVo getUser(String id, String password) {
		
		return userRepository.findByIdAndPassword(id,password);
	}

}
