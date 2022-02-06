package com.poscoict.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.jblog.vo.BlogVo;
import com.poscoict.jblog.vo.UserVo;

@Repository
public class BlogRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public int insert(UserVo userVo) {
		BlogVo blogVo = new BlogVo();
		blogVo.setTitle(userVo.getName()+"님의 블로그");
		blogVo.setLogo("/images/202214102733647.jpeg");
		blogVo.setUserId(userVo.getId());
		return sqlSession.insert("blog.insert", blogVo);
	}

	public BlogVo findById(String id) {
		
		return sqlSession.selectOne("blog.findById",id);
	}

	public int updateBlogInfo(BlogVo blogVo) {
		return sqlSession.update("blog.update", blogVo);
	}

}
