<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>기기 등록</h1>
	<form action ="form" method = "POST">
	<h3>노인정보</h3>
	<div>
	<label for = "inp_e_name">name: </label>
	<input type = "text" name = "inp_e_name" id = "inp_e_name">
	</div>
	<div>
	<label for = "inp_e_birth">birth: </label>
	<input type = "date" name = "inp_e_birth" id = "inp_e_birth">
	</div>
	<div>
	<label for = "inp_e_tel">tel: </label>
	<input type = "text" name = "inp_e_tel" id = "inp_e_tel">
	</div>
	<div>
	<label for = "inp_e_addr">address: </label>
	<input type = "text" name = "inp_e_addr" id = "inp_e_addr">
	</div>
	<h3>기기정보</h3>
	<div>
	<label for = "inp_homeiot">homeiot: </label>
	<input type = "text" name = "inp_homeiot" id = "inp_homeiot">
	</div>
	<div>
	<label for = "inp_bandiot">bandiot: </label>
	<input type = "text" name = "inp_bandiot" id = "inp_bandiot">
	</div>
	<div>
	<button type = "submit" class = "btn btn-default">기기등록</button>
	</div>
	</form>
</body>
</html>