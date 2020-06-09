<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Join</h1>
	<form action = "member/memberJoin" method = "POST">
		<div>
		<label for = "m_id">ID:</label><input type = "text" name = "m_id">
		</div>
		<div>
		<label for = "m_pwd">password:</label><input type = "password" name = "m_pwd">
		</div>
		<div>
		<label for = "m_name">name:</label><input type = "text" name = "m_name">
		</div>
		<div>
		<label for = "m_tel">tel:</label><input type = "text" name = "m_tel">
		</div>
		<div>
		<label for = "m_email">email:</label><input type = "email" name = "m_email">
		</div>
		<button type = "submit" class = "btn btn-default">Submit</button>
		
	</form>
</body>
</html>