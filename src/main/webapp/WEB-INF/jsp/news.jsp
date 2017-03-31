<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.news.pageTitle" var="pageTitle" />
<fmt:message bundle="${loc}" key="local.news.pageHeader" var="pageHeader" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${pageTitle}</title>
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

      <div class="col-md-10 col-sm-9 col-lg-10 col-xs-9 main content">
        <div class="panel panel-primary container-fluid">
          <div class="row panel-heading " >
          	 <h2 class="text-left col-md-8 col-sm-8 col-lg-8 col-xs-8">${pageHeader}</h2>
	          <h5 class="text-right col-md-4 col-sm-4 col-lg-4 col-xs-4">
	          	<c:if test="${requestScope.numOfPages > 1}">
	          	<ul class="pagination">
				  <c:forEach begin="1" end="${requestScope.numOfPages}" step="1" var="pageNum">
				  	<c:choose> 
				  		<c:when test="${pageNum eq requestScope.curPage}">
				  			 <li class="active"><a href="<c:url value="/Controller?command=open_all_news&pageNum=${pageNum}" />" >${pageNum}</a></li>
				  		</c:when>
				  		<c:otherwise>
				  			<li><a href="<c:url value="/Controller?command=open_all_news&pageNum=${pageNum}" />" >${pageNum}</a></li>
				  		</c:otherwise>
				  	</c:choose>
				  </c:forEach>
				</ul>
				</c:if>
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
                <c:forEach var="news" items="${requestScope.news}">
                    <div class="panel panel-default container-fluid">
                        <div class="row panel-heading news-heading" >
                        	<div class="col-md-9 col-sm-9 col-xs-9 col-lg-9">
                        		<h4 class="text-left">
                        			<a href="<c:url value="/Controller?command=open_single_news&newsID=${news.id}"/>" > ${news.title} </a>
                        		</h4>
                        	</div>
                        	<div class="col-md-3 col-sm-3 col-xs-3 col-lg-3">
                        		<h4 class="text-right">${news.date} ${news.time}</h4>
                        	</div>
                        </div> 
                    <div class=" panel-body">
                    	<div class="row">
                    		<p class="text-justify" style="margin:10px;"></p>
                    	 </div>
                    
                        <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
                          
                           <figure>
                             <img src="img/news/${news.id}/01.jpg" alt="No image" class="img-thumbnail img-responsive" width="210" height="140" style="float:left; margin:20px;" onError="this.onerror=null;this.src='img/no-img.jpg';"/> 
                           </figure>
                       		<br>
                           <p style="text-align:justify;"> ${news.text} </p>

                          
                        </div>
                        </div>
                        </div>

				</c:forEach>

                



          </div>
          </div>
          <c:if test="${requestScope.numOfPages > 1}">
          <div class="row panel-footer" >
          <h5 class="text-right">
          	<ul class="pagination">
			  <c:forEach begin="1" end="${requestScope.numOfPages}" step="1" var="pageNum">
			  	<c:choose> 
			  		<c:when test="${pageNum eq requestScope.curPage}">
			  			 <li class="active"><a href="<c:url value="/Controller?command=open_all_news&pageNum=${pageNum}" />" >${pageNum}</a></li>
			  		</c:when>
			  		<c:otherwise>
			  			<li><a href="<c:url value="/Controller?command=open_all_news&pageNum=${pageNum}" />" >${pageNum}</a></li>
			  		</c:otherwise>
			  	</c:choose>
			  </c:forEach>
			</ul>
          </h5>
          </div> 
          </c:if>

      </div>
      </div>      
     </div>
  </div>  
  
  <jsp:include page="/WEB-INF/static/footer.jsp"></jsp:include>
  
</body>
</html>