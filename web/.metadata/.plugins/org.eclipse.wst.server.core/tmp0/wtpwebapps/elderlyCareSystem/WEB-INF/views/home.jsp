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
		console.log("TEs");
		console.log(window.sessionStorage);
		if(window.sessionStorage){
			var RequestURL = $(location).attr('pathname')+'devices';
			$.getJSON(RequestURL, function(data){
				var html = '';
				$.each(data, function(index, entry){
					html+='<p>';
					html+=entry.ename;
					html+=entry.ebirth;
					html+=entry.etel;
					html+= '</p>';
				});
			});
			$('#list').html(html);
		}
	});
	
</script>
</html>
