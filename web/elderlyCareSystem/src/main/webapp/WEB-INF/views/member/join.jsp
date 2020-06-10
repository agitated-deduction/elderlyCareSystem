<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>JOIN</h1>
	<form action ="memberJoin" method = "POST">
	<div>
	<label for = "inp_m_id">id: </label>
	<input type = "text" name = "inp_m_id" id = "inp_m_id">
	</div>
	<div>
	<label for = "inp_m_pwd">password: </label>
	<input type = "password" name = "inp_m_pwd" id = "inp_m_pwd">
	</div>
	<div>
	<label for = "inp_m_name">name: </label>
	<input type = "text" name = "inp_m_name" id = "inp_m_name">
	</div>
	<div>
	<label for = "inp_m_tel">tel: </label>
	<input type = "text" name = "inp_m_tel" id = "inp_m_tel">
	</div>
	<div>
	<label for = "inp_m_email">email: </label>
	<input type = "text" name = "inp_m_email" id = "inp_m_email">
	</div>
	<div>
	<button type = "submit" class = "btn btn-default">가입 신청</button>
	</div>
	</form>
</body>
</html>