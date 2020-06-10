<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>login page</h1>
<form action ="memberLogin" method = "POST">
<div>
	<label for = "inp_m_id">ID:</label>
	<input id = "inp_m_id" type = "text" name = "inp_m_id" >
	</div>
	<div>
	<label for = "inp_m_pwd">PASSWORD:</label>
	<input id = "inp_m_pwd" type = "password" name = "inp_m_pwd" >
</div>
<button type = "submit">로그인</button>
</form>
</body>
</html>