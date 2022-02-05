package com.poscoict.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.jblog.vo.PostVo;


@Repository
public class PostRepository {

	@Autowired
	private SqlSession sqlSession;

	public List<PostVo> findByCateNo(Long categoryNo) {
		
		return sqlSession.selectList("post.findByCateNo",categoryNo);
	}

	public PostVo findRecentOne(Long categoryNo) {
		
		return sqlSession.selectOne("post.findRecentOne", categoryNo);
	}
	
	public PostVo findByNo(Long no) {
		return sqlSession.selectOne("post.findByNo", no);
	}
	
}
