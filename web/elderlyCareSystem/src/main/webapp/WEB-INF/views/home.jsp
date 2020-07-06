<!DOCTYPE html5>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page session="true" %>
<html>
<head>
	<title>Home</title>

</head>
<body>
<%@ include file="header.jsp" %>

<h1>
	Hello world!  
</h1>
<p>session id : <%=session.getAttribute("uid") %></p>
<p>test ${uid }</p>
<P>  The time on the server is ${serverTime}. </P>
<!-- 
<c:if test = "${empty uid }">
	<a href = "users/login" class = "btn btn-default" role = "button">로그인</a>
	<a href = "users/join" class = "btn btn-default" role = "button">회원가입</a>
</c:if>
<c:if test = "${not empty uid }">
	<a href = "users/${uid }" class = "btn btn-default" role = "button">내 정보</a>
	<a href = "users/logout" class = "btn btn-default" role = "button">로그아웃</a>
	<a href = "devices/form" class = "btn btn-default" role = "button">기기등록</a>
	<br/>
	<form class = 'delete-form' action = "users/${uid }" method = "post">
	<input type = "hidden" name = "_method" value = "delete"/>
	<button type = "submit">회원 탈퇴</button>
	<a href = "users/mod-form" class = "btn btn-default" role = "button">정보 수정</a>
	
	</form>
</c:if>
 -->

</body>
</html>
