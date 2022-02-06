<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link href="${pageContext.request.contextPath}/assets/css/jblog.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
	<c:import url="/WEB-INF/views/include/blogHeader.jsp" />
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					
					<h4>${postOne.title }</h4>
					<p>
						${postOne.contents }
					<p>
				</div>
				<ul class="blog-list">
				<c:forEach items="${postList }" var="postVo" varStatus="status" >
					<li><a href="${pageContext.request.contextPath}/${blogVo.userId }/${postVo.categoryNo }/${postVo.no }">${postVo.title }</a> <span>${postVo.regDate }</span>	</li>
				</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blogVo.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items="${cateList }" var="cateVo" varStatus="status">
					<li><a href="${pageContext.request.contextPath}/${blogVo.userId }/${cateVo.no }">${cateVo.name } ${cateVo.no }</a></li>
				</c:forEach>
			</ul>
		</div>
		<c:import url="/WEB-INF/views/include/blogFooter.jsp" />
	</div>
</body>
</html>