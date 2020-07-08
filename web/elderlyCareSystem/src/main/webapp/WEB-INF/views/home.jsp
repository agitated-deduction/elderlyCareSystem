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
<div id = "list">
</div>

</body>

<script type = "text/javascript" src = "<c:url value = '/resources/js/jquery-3.5.1.js'/>"></script>
<script type = "text/javascript">
 	$(document).ready(function(){
		var html = '';
		$.getJSON('devices', function(data){
			$.each(data, function(index, item){
				html+='<p>';
				html+=item.ename+', '+item.ebirth+', '+item.etel+', '+item.eaddr;
				
				//data 보기 button
				
				html+='</p>';
				
			});
			$('div').html(html);
		});
		/*$(데이터 보기 버튼).click(function(){
			포워드 , 데이터  화면
		});*/
		
	});
	 
</script>
</html>
