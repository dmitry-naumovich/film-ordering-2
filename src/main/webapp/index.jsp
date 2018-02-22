<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.common.serviceName" var="serviceName" />
<fmt:message bundle="${loc}" key="local.index.pageTitle" var="pageTitle" />
<fmt:message bundle="${loc}" key="local.index.novelty" var="novelty" />
<fmt:message bundle="${loc}" key="local.index.director" var="director" />
<fmt:message bundle="${loc}" key="local.index.cast" var="cast" />
<fmt:message bundle="${loc}" key="local.index.genre" var="genre" />
<fmt:message bundle="${loc}" key="local.index.rublesShorten" var="rublesShorten" />
<fmt:message bundle="${loc}" key="local.index.readMoreBtn" var="readMore" />
<fmt:message bundle="${loc}" key="local.index.addInfo" var="addInfo" />
<fmt:message bundle="${loc}" key="local.index.editFilmBtn" var="editFilmBtn" />
<fmt:message bundle="${loc}" key="local.film.purchased" var="purchased" />
   	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${serviceName} - ${pageTitle}</title>
  <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />

  <link rel="icon" type="image/x-icon" href="../static.img/tab-logo.png">
  <link rel="stylesheet" href="/css/bootstrap.min.css" >
  <link rel="stylesheet" href="/css/styles.css">
  <script src="/js/scripts.js"></script>
  <script src="/js/masonry.pkgd.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="http://mybootstrap.ru/wp-content/themes/clear-theme/js/bootstrap-affix.js"></script>
</head>

<body data-spy="scroll" data-target="#myScrollspy" data-offset-top="15">
   	
  	<jsp:include page="/WEB-INF/static/header.jsp"></jsp:include>

  <div class="container-fluid"> 
    <div class="row content ">
    
     <jsp:include page="/WEB-INF/static/left-menu.jsp"></jsp:include>

      <div class="col-md-8 col-sm-9 col-lg-8 col-xs-9 main content" style="text-align:justify;">
      			<c:if test="${errorMessage != null && !empty errorMessage}">
					<div class="alert alert-danger fade in">
					  <a href="#" class="close" data-dismiss="alert" aria-label="close"> &times;</a>
					 ${errorMessage} 
					</div>
				</c:if>
				<c:if test="${successMessage != null && !empty successMessage}">
					<div class="alert alert-success fade in">
					  <a href="#" class="close" data-dismiss="alert" aria-label="close"> &times;</a>
					 ${successMessage} 
					</div>
				</c:if>
      
         
          <div class="row panel-body row grid masonry-container masonry js-masonry" style="padding:0px; margin:0px">
              <jsp:include page="/Controller?command=get_novelty" />
				<c:forEach items="${sessionScope.noveltyList}" var="film">
		            <div class="col-sm-6 col-md-4 col-xs-12 col-lg-4 grid-item" style="display:inline-block; padding:10px; margin:0px; min-height:200px;">
		              <h2 style="text-align:center">${film.name} (${film.year}) </h2>
		              <p><b>${director}:</b> ${film.director} </p>
		              <c:choose>
		              <c:when test="${film.actors != null}">
		                       <p><b>${cast}:</b> ${film.actors} </p>
	                      </c:when>
	                      <c:otherwise>
	                      <p><b>${cast}:</b> — </p>
	                      </c:otherwise>
	                  </c:choose>
	                  <c:choose>
	                  <c:when test="${film.genre != null}">
		                       <p><b>${genre}:</b> ${fn:replace(film.genre, ",", ", ")} </p>
	                      </c:when>
	                      <c:otherwise>
	                      <p><b>${genre}:</b> — </p>
	                      </c:otherwise>
	                  </c:choose>
		             
		              
		              <img src="/img/films/${film.id}/01.jpg" alt="No frame" class="img-rounded" style="width: 100%; height: auto;" onError="this.onerror=null;this.src='/img/no-img.jpg';"/>
		              <br> <br>
		              <c:if test="${film.description != null}">
		              	<p style="text-align:justify">${fn:substring(film.description, 0,60)}... 
		              	<a class="btn btn-link" href="<c:url value="/Controller?command=open_single_film&filmId=${film.id}&pageNum=1"/>" role="button"> ${readMore} &raquo;</a>
		              	</p>
		              </c:if>
		              
		              <p>
		              	<c:choose> 
              				<c:when test="${sessionScope.isAdmin}">
              					<a class="btn btn-info" href="<c:url value="/Controller?command=open_film_edit_page&filmId=${film.id}"/>" role="button">${editFilmBtn}</a>
              				</c:when>
              				<c:otherwise>
              					<c:choose>
	             					<c:when test="${sessionScope.authUser!=null && !empty requestScope.userOrderFilmIds && fn:contains(requestScope.userOrderFilmIds, film.id)}">
	             						<button type="button" class="btn btn-default disabledBtn">${purchased}</button>
	               					</c:when>
	               					<c:otherwise>
	               						<a class="btn btn-info" href="<c:url value="/Controller?command=open_new_order_page&filmId=${film.id}"/>" role="button">${film.price} ${rublesShorten}</a>
	               					</c:otherwise>
               					</c:choose>
              					
              				</c:otherwise>
		              	</c:choose> 
		                
		                 
		              </p>
		            </div>
           		</c:forEach>
          </div>
    </div>
		<jsp:include page="/WEB-INF/static/right-sidebar.jsp"></jsp:include>
     </div>
  </div>  
  <jsp:include page="/WEB-INF/static/footer.jsp"></jsp:include>
</body>
</html>