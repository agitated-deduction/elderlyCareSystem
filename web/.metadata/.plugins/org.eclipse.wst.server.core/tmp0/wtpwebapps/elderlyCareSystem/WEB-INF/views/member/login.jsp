<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<%@ include file="../header.jsp" %>
<h1>login page</h1>
<form action = "login" id ="login-form" method = "POST">
<div>
	<label for = "uid">ID:</label>
	<input id = "uid" type = "text" name = "uid" >
	</div>
	<div>
	<label for = "upwd">PASSWORD:</label>
	<input id = "upwd" type = "password" name = "upwd" >
</div>
<button type = "submit">로그인</button>
</form>
</body>
<script type = "text/javascript" src = "/elderlycare/resources/jquery-3.5.1.js"></script>
<script type = "text/javascript">
$(function(){
	$('#login-form').submit(function(event){
		event.preventDefault();
		
		//var data = {"uid": "staff101058", "upwd": "staff101058"};
		var data = {uid: $("#uid").val(), upwd: $('#upwd').val()};

		$.ajax({
				type : 'POST',                            
				url : 'login',                        
				dataType : 'json',                          
				contentType : 'application/json',            
				data : JSON.stringify(data),            
				success : function(response){
					if(response.result){
						alert(response.uid+'님 환영합니다.');
						$(location).attr("href", "${contextPath}/");
					}else
						alert("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.");
				},                      
				error   : function(response){
					alert(response.uid);
				}
		});
	});
});
	


</script>
</html>