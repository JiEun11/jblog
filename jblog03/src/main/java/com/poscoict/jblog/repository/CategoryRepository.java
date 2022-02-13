package com.poscoict.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public int insert(String id) {
		
		CategoryVo cateVo = new CategoryVo();
		cateVo.setName("미분류");
		cateVo.setDescription("기본 카테고리");
		cateVo.setBlogId(id);
		return sqlSession.insert("category.insert", cateVo);
		
	}

	public List<CategoryVo> findAllById(String id) {
		return sqlSession.selectList("category.findAllById", id);
	}
	
	public List<CategoryVo> checkCateExist(String userId,Long no){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("blogId", userId);
		map.put("categoryNo", no);
		return sqlSession.selectList("category.findAllByNo", map);
	}
	
	public Long getMaxNo(String id) {
		return sqlSession.selectOne("category.findMaxNoById",id);
	}
	
	public int addCategory(CategoryVo categoryVo) {
		return sqlSession.insert("category.addCategory",categoryVo);
	}

	public int delete(Long categoryNo) {
		return sqlSession.delete("category.delete",categoryNo);
	}

}
