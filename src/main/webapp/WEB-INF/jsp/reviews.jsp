<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.common.serviceName" var="serviceName" />
<fmt:message bundle="${loc}" key="local.reviews.pageTitle" var="pageTitle" />
<fmt:message bundle="${loc}" key="local.reviews.pageHeader" var="pageHeader" />
<fmt:message bundle="${loc}" key="local.reviews.author" var="author" />
<fmt:message bundle="${loc}" key="local.reviews.mark" var="mark" />
<fmt:message bundle="${loc}" key="local.reviews.date" var="date" />
<fmt:message bundle="${loc}" key="local.reviews.openSingleReviewBtn" var="openSingleReviewBtn" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${serviceName} - ${pageTitle}</title>
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
          <div class="row panel-heading" >
          		<c:choose>
	          			<c:when test="${requestScope.reviewViewType eq 'all'}">
	          				<h2 class="text-left col-md-4 col-sm-4 col-lg-4 col-xs-4">${pageHeader}</h2>
					          <h5 class="text-right col-md-8 col-sm-8 col-lg-8 col-xs-8">
					          	<c:if test="${requestScope.numOfPages > 1}">
						          	<ul class="pagination">
									  <c:forEach begin="1" end="${requestScope.numOfPages}" step="1" var="pageNum">
									  	<c:choose> 
									  		<c:when test="${pageNum eq requestScope.curPage}">
									  			 <li class="active"><a href="<c:url value="/Controller?command=open_all_reviews&pageNum=${pageNum}" />" >${pageNum}</a></li>
									  		</c:when>
									  		<c:otherwise>
									  			<li><a href="<c:url value="/Controller?command=open_all_reviews&pageNum=${pageNum}" />" >${pageNum}</a></li>
									  		</c:otherwise>
									  	</c:choose>
									  </c:forEach>
									</ul>
								</c:if>
					          </h5>
	          			</c:when>
	          			<c:when test="${requestScope.reviewViewType eq 'user'}">
	          				<h2 class="text-left col-md-4 col-sm-4 col-lg-4 col-xs-4">${pageHeader}</h2>
					          <h5 class="text-right col-md-8 col-sm-8 col-lg-8 col-xs-8">
					          	<c:if test="${requestScope.numOfPages > 1}">
						          	<ul class="pagination">
									  <c:forEach begin="1" end="${requestScope.numOfPages}" step="1" var="pageNum">
									  	<c:choose> 
									  		<c:when test="${pageNum eq requestScope.curPage}">
									  			 <li class="active"><a href="<c:url value="/Controller?command=open_user_reviews&userId=${requestScope.userId}&pageNum=${pageNum}" />" >${pageNum}</a></li>
									  		</c:when>
									  		<c:otherwise>
									  			<li><a href="<c:url value="/Controller?command=open_user_reviews&userId=${requestScope.userId}&pageNum=${pageNum}" />" >${pageNum}</a></li>
									  		</c:otherwise>
									  	</c:choose>
									  </c:forEach>
									</ul>
								</c:if>
					          </h5>
	          			</c:when>
				</c:choose>
          </div> 
          <div class="row panel-body">
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
            
            	<c:forEach var="review" items="${requestScope.reviews}" varStatus="status">
            		<c:set var="authorLogin" value="${requestScope.logins[status.index]}" />
            		<c:set var="filmName" value="${requestScope.filmNames[status.index]}" />
            
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
                        
                    <div class="panel panel-default container-fluid">
                        <div class="row panel-heading review-heading" style="background-color:${rColor}">
                        	<div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
                        		<h4 class=" text-left"> 
			                    	<a href="<c:url value="/Controller?command=open_single_film&filmId=${review.filmId}&pageNum=1" />" >${filmName} </a>
			                    </h4>
                        	</div>
                        	<div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
                        		<c:if test="${sessionScope.isAdmin && authorLogin != null}">
	                        			<h4 class=" text-right">
	                        			${author}:  
				                    	<a href="<c:url value="/Controller?command=open_user_profile&userId=${review.author}" />" >${authorLogin} </a>
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
                        	<div class="col-md-4 col-sm-4 col-xs-4 col-lg-4">
                        		<h5 class="text-left">${mark}: ${review.mark}/5</h5>
                        	</div>
                        	<div class="col-md-4 col-sm-4 col-xs-4 col-lg-4">
                        		<h5 class="text-center">
                        			<a href="<c:url value="/Controller?command=open_single_review&userId=${review.author}&filmId=${review.filmId}" />"  >${openSingleReviewBtn}</a>
                        		</h5>
                        	</div>
                        	<div class="col-md-4 col-sm-4 col-xs-4 col-lg-4">
                        		<h5 class="text-right"> ${date}: ${review.date} ${review.time} </h5>
                        	</div>
                        </div>                        
                     </div>                        
                  </c:forEach>                        
          </div>
          </div>
          <c:if test="${requestScope.numOfPages > 1}">
          <div class="row panel-footer" >
          <c:choose>
	     			<c:when test="${requestScope.reviewViewType eq 'all'}">
	         <h5 class="text-right">
	         	<ul class="pagination">
			  <c:forEach begin="1" end="${requestScope.numOfPages}" step="1" var="pageNum">
			  	<c:choose> 
			  		<c:when test="${pageNum eq requestScope.curPage}">
			  			 <li class="active"><a href="<c:url value="/Controller?command=open_all_reviews&pageNum=${pageNum}" />" >${pageNum}</a></li>
			  		</c:when>
			  		<c:otherwise>
			  			<li><a href="<c:url value="/Controller?command=open_all_reviews&pageNum=${pageNum}" />" >${pageNum}</a></li>
			  		</c:otherwise>
			  	</c:choose>
			  </c:forEach>
			</ul>
	         </h5>
	     			</c:when>
	     			<c:when test="${requestScope.reviewViewType eq 'user'}">
	         <h5 class="text-right">
	         	<ul class="pagination">
			  <c:forEach begin="1" end="${requestScope.numOfPages}" step="1" var="pageNum">
			  	<c:choose> 
			  		<c:when test="${pageNum eq requestScope.curPage}">
			  			 <li class="active"><a href="<c:url value="/Controller?command=open_user_reviews&userId=${requestScope.userId}&pageNum=${pageNum}" />" >${pageNum}</a></li>
			  		</c:when>
			  		<c:otherwise>
			  			<li><a href="<c:url value="/Controller?command=open_user_reviews&userId=${requestScope.userId}&pageNum=${pageNum}" />" >${pageNum}</a></li>
			  		</c:otherwise>
			  	</c:choose>
			  </c:forEach>
			</ul>
	         </h5>
	     			</c:when>
				</c:choose>
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