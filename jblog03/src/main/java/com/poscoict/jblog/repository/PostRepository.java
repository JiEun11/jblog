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

	public List<PostVo> findAllByCateNo(Long categoryNo) {
		
		return sqlSession.selectList("post.findAllByCateNo",categoryNo);
	}

	public PostVo findRecentOne(Long categoryNo) {
		
		return sqlSession.selectOne("post.findRecentOne", categoryNo);
	}
	
	public PostVo findOneByNo(Long no) {
		return sqlSession.selectOne("post.findOneByNo", no);
	}
	
	public Long findMaxNoByNo(Long categoryNo) {
		return sqlSession.selectOne("post.findMaxNoByNo", categoryNo);
	}

	public int insertPost(PostVo postVo) {
		
		return sqlSession.insert("post.insert",postVo);
	}
	
}
