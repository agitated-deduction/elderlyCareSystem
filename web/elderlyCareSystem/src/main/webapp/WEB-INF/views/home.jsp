<!DOCTYPE html5>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page session="true" %>
<html>
<head>
	<title>Home</title>
<script type="text/javascript"> 
function onClick_regdev() { 
document.location.href='./device/registration.jsp'; 
} 
</script> 
</head>
<body>
<h1>
	Hello world!  
</h1>
<p>session id : <%=session.getAttribute("uid") %></p>
<p>test ${uid }</p>
<P>  The time on the server is ${serverTime}. </P>

<c:if test = "${empty uid }">
	<a href = "users/login" class = "btn btn-default" role = "button">로그인</a>
	<a href = "users/join" class = "btn btn-default" role = "button">회원가입</a>
</c:if>
<c:if test = "${not empty uid }">
	<a href = "users/${uid }" class = "btn btn-default" role = "button">내 정보</a>
	<a href = "users/logout" class = "btn btn-default" role = "button">로그아웃</a>
	<input type=button onclick="onClick_regdev()" value='기기등록'>
</c:if>

</body>
</html>
