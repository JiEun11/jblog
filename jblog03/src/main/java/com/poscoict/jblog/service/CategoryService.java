package com.poscoict.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.jblog.repository.CategoryRepository;
import com.poscoict.jblog.vo.CategoryVo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<CategoryVo> getCategory(String id) {
		return categoryRepository.findAllById(id);
	}

	public boolean addCategory(CategoryVo categoryVo) {
		return categoryRepository.addCategory(categoryVo)==1;
		
	}

	public boolean deleteCategory(Long categoryNo) {
		return categoryRepository.delete(categoryNo) == 1;
		
	}
}
