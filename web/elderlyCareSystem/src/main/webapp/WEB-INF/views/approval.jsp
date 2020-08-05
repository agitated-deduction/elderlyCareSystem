<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta charset="utf-8">
    <title>Login - Bootstrap Admin Template</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes"> 
    
<link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet" type="text/css" />
<link href="<c:url value='/resources/css/bootstrap-responsive.min.css' />" rel="stylesheet" type="text/css" />

<link href="<c:url value='/resources/css/font-awesome.css' />" rel="stylesheet">
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600" rel="stylesheet">
    
<link href="<c:url value='/resources/css/style.css' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/resources/css/pages/signin.css' />" rel="stylesheet" type="text/css">

</head>

<body>
	<%@include file = "header.jsp" %>



<div class="account-container">
	
	<div class="content clearfix">
		
		
		
			<h1>Join Approval</h1>		
			<br/>
			<div class="login-fields">
				
			<c:forEach var = "id" items = "${aids }">
			<pre id = '${id }'>${id }					<a href = "${contextPath }/users/approval/${id }">v</a></pre>
			
			</c:forEach>
			
			
		</div>
	</div> <!-- /content -->
	
</div> <!-- /account-container -->






<script src="<c:url value='/resources/js/jquery-1.7.2.min.js' />"></script>
<script src="<c:url value='/resources/js/bootstrap.js' />"></script>
<script type = "text/javascript">

$(function(){
	function approve(id){
		$(id).remove();
	}
});
	


</script>
<!--  <script src="<c:url value='/resources/js/signin.js' />"></script>-->

</body>

</html>
