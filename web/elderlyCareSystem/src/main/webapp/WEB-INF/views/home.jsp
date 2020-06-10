<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>
<p>session id : ${m_id }</p>
<p></p>
<P>  The time on the server is ${serverTime}. </P>
<a href = "memberLogin" class = "btn btn-default" role = "button">로그인</a>
<a href = "memberJoin" class = "btn btn-default" role = "button">회원가입</a>
<a href = "mypage" class = "btn btn-default" role = "button">내 정보</a>
</body>
</html>
