<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Error page</title>
	<link rel="stylesheet" href="/css/errorpage.css" >
</head>
<body>
    <div class='_1'>Application has generated an error!</div><br>
	<c:if test="${errorMessage != null && !errorMessage.isEmpty()}" >
        <div class='_2'>${errorMessage}</div>
	</c:if>
	<div id="clouds">
		<div class="cloud x1"></div>
		<div class="cloud x1_5"></div>
		<div class="cloud x2"></div>
		<div class="cloud x3"></div>
		<div class="cloud x4"></div>
		<div class="cloud x5"></div>
	</div>
	<div class='c'>
		<div class='_404'>404</div>
		<hr>
		<div class='_1'>THE PAGE</div>
		<div class='_2'>WAS NOT FOUND</div>
        <input class='btn' type="button" value="GET BACK" onclick="history.back(-1)" />
    </div>
</body>
</html>