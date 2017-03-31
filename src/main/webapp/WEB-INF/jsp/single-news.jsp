<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.news.editNewsBtn" var="editNewsBtn" />
<fmt:message bundle="${loc}" key="local.news.deleteNewsBtn" var="deleteNewsBtn" />

<c:set var="news" value="${requestScope.news}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${news.title}</title>
  <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
  <link rel="icon"  type="image/x-icon" href="img/tab-logo.png">
  <link rel="stylesheet" href="css/bootstrap.min.css" >
  <link rel="stylesheet" href="css/styles.css">
  <script src="js/scripts.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="http://mybootstrap.ru/wp-content/themes/clear-theme/js/bootstrap-affix.js"></script>
</head>

<body data-spy="scroll" data-target="#myScrollspy" data-offset-top="15">

  <jsp:include page="/WEB-INF/static/header.jsp"></jsp:include>


  <div class="container-fluid"> 
    <div class="row content ">
    
      <jsp:include page="/WEB-INF/static/left-menu.jsp"></jsp:include>
	  
	  
      <div class="col-md-8 col-sm-9 col-lg-8 col-xs-9 main content">
        <div class="panel panel-primary container-fluid">
          <div class="panel-heading row" style="margin-top: 0px;padding-top:0px">
	          <div class="col-md-8 col-sm-8 col-xs-8 col-lg-8" >
	          	<h4 class="text-left"> ${news.title} </h4>
	          </div>
	          <div class="col-md-4 col-sm-4 col-xs-4 col-lg-4" >
	          	<h4 class="text-right"> ${news.date} ${news.time}</h4>
	          </div>
          </div> 
          <div class="row panel-body ">
            <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
            	<c:if test="${errorMessage != null && !errorMessage.isEmpty()}">
					<div class="alert alert-danger fade in">
					  <a href="#" class="close" data-dismiss="alert" aria-label="close"> &times;</a>
					 ${errorMessage} 
					</div>
				</c:if>
				<c:if test="${successMessage != null && !successMessage.isEmpty()}">
					<div class="alert alert-success fade in">
					  <a href="#" class="close" data-dismiss="alert" aria-label="close"> &times;</a>
					 ${successMessage} 
					</div>
				</c:if>
            
	            <img src="img/news/${news.id}/01.jpg" alt="No image" class="img-thumbnail img-responsive" style="float:left; margin:20px;" width="210" height="140" onError="this.onerror=null;this.src='img/no-img.jpg';"/>
	            <br><p style="text-align:justify;"> ${news.text} </p>
          	</div>
          </div>
          <c:if test="${sessionScope.isAdmin}">
          <div class="row panel-footer">
          	<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
          		<h5 class="text-right">
          		<a href="<c:url value="/Controller?command=open_news_edit_page&newsID=${news.id}" />" >${editNewsBtn}</a> |
            		<a href="<c:url value="/Controller?command=delete_news&newsID=${news.id}" />" >${deleteNewsBtn}</a>
	            </h5>
          	</div>
          </div>
          </c:if>

      </div>
      </div>


      <jsp:include page="/WEB-INF/static/right-sidebar.jsp"></jsp:include>
      
     </div>
  </div>  
  
  <jsp:include page="/WEB-INF/static/footer.jsp"></jsp:include>
  
</body>
</html>