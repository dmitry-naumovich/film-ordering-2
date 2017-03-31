<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Error page</title>
</head>
<body>
	<h2>Application has generated an error!</h2>
	<c:if test="${errorMessage != null && !errorMessage.isEmpty()}" >
		${errorMessage}
	</c:if>
	<%-- <%= exception.toString() %> --%>
</body>
</html>