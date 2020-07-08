
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page session="true" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:set var = "contextPath" value = "<%=request.getContextPath() %>"></c:set>
<a href = "${contextPath}/">HOME</a>
<c:if test = "${empty uid }">
	<a href = "users/login" class = "btn btn-default" role = "button">로그인</a>
	<a href = "users/join" class = "btn btn-default" role = "button">회원가입</a>
</c:if>
<c:if test = "${not empty uid }">
	<a href = "users/info" class = "btn btn-default" role = "button">내 정보</a>
	<a class = "btn btn-default" id = "btn-logout" role = "button">로그아웃</a>
	<c:if test = "${auth == 1 }">
	<a href = "devices/form" class = "btn btn-default" role = "button">기기등록</a>
	<a href  = "" class = "btn btn-default" role = "button">가입 승인</a>
	</c:if>
	<br/>
	<form class = 'delete-form' action = "users/info}" method = "post">
	<input type = "hidden" name = "_method" value = "delete"/>
	<button type = "submit">회원 탈퇴</button>
	</form>
	<a href = "users/mod-form" class = "btn btn-default" role = "button">정보 수정</a>
	

</c:if>
</body>
<script type = "text/javascript" src = "<c:url value = '/resources/js/jquery-3.5.1.js'/>"></script>
<script type = "text/javascript">
$(document).ready(function(){
	$('#btn-logout').click(function(){
		$.getJSON('users/logout', function(data){
			window.location.replace('');
		});
	});
});

</script>
</html>