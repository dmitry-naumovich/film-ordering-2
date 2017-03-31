<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.common.serviceName" var="serviceName" />
<fmt:message bundle="${loc}" key="local.reviews.author" var="author" />
<fmt:message bundle="${loc}" key="local.reviews.mark" var="mark" />
<fmt:message bundle="${loc}" key="local.reviews.date" var="date" />
<fmt:message bundle="${loc}" key="local.reviews.deleteReview" var="deleteReview" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${serviceName}</title>
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
			<c:if test="${errorMessage != null && !errorMessage.isEmpty()}">
				<div class="alert alert-danger fade in">
				  <a href="#" class="close" data-dismiss="alert" aria-label="close"> &times;</a>
				 ${errorMessage} 
				</div>
			</c:if>
			<c:set var="review" value="${requestScope.review}" />
			<c:set var="authorLogin" value="${requestScope.login}" />
	  		<c:set var="filmName" value="${requestScope.filmName}" />
  
	          <c:choose>
	        		<c:when test="${review.type eq  'ps'}"> 
	         			<c:set var="rColor" value="#ccffcc"/>
	         		</c:when>
	         		<c:when test="${review.type eq 'ng'}"> 
	         			<c:set var="rColor" value="#ffcccc"/>
	         		</c:when>
	         		<c:otherwise>
	         			<c:set var="rColor" value="#e6e6ff"/>
	         		</c:otherwise>
	          </c:choose>
              
         <div class="col-md-8 col-sm-9 col-lg-8 col-xs-9 main content">   
          <div class="panel panel-default container-fluid">
              <div class="row panel-heading review-heading" style="background-color:${rColor}">
	              <div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
	              	<h4 class=" text-left"> 
	             		<a href="<c:url value="/Controller?command=open_single_film&filmID=${review.filmId}&pageNum=1" />" >${filmName} </a>
	             	</h4>
	              </div>
	              <div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
	              	<c:if test="${sessionScope.isAdmin}">
	               		<h4 class=" text-right">
	               			${author}:  
	              			<a href="<c:url value="/Controller?command=open_user_profile&userID=${review.author}" />" >${authorLogin} </a>
	              		</h4>
	             	</c:if>
	              </div>
              </div> 
          	<div class="row panel-body">
               <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
                 <p> <br>
                     ${review.text}
                 </p>
               </div>
            </div>
              <div class="row panel-footer" style="background-color:${rColor}">
              	<c:choose>
               	<c:when test="${sessionScope.isAdmin || sessionScope.userID eq review.author}">
	               	<div class="col-md-4 col-sm-4 col-xs-4 col-lg-4">
	               		<h5 class="text-left">${mark}: ${review.mark}/5</h5>
	               	</div>
		            <div class="col-md-4 col-sm-4 col-xs-4 col-lg-4">
	               		<h5 class="text-center">
	               			<a href="<c:url value="/Controller?command=delete_review&userID=${review.author}&filmID=${review.filmId}" />" >${deleteReview}</a>
	               		</h5>
	               	</div>
	               	<div class="col-md-4 col-sm-4 col-xs-4 col-lg-4">
               			<h5 class="text-right"> ${date}: ${review.date} ${review.time} </h5>
                	</div>
               	</c:when>
               	<c:otherwise>
               		<div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
	               		<h5 class="text-left">${mark}: ${review.mark}/5</h5>
	               	</div>
	               	<div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
               			<h5 class="text-right"> ${date}: ${review.date} ${review.time} </h5>
                	</div>
               	</c:otherwise>
               	</c:choose>
              </div>
           </div>
          </div>
      <jsp:include page="/WEB-INF/static/right-sidebar.jsp"></jsp:include>
     </div>
  </div>  
  <jsp:include page="/WEB-INF/static/footer.jsp"></jsp:include>
</body>
</html>