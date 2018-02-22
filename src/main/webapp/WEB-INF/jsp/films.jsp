<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.films.pageTitle" var="pageTitle" />
<fmt:message bundle="${loc}" key="local.films.mainHeader" var="mainHeader" />
<fmt:message bundle="${loc}" key="local.films.openFilmPage" var="openFilmPage" />
<fmt:message bundle="${loc}" key="local.film.director" var="director" />
<fmt:message bundle="${loc}" key="local.film.cast" var="cast" />
<fmt:message bundle="${loc}" key="local.film.genre" var="genre" />
<fmt:message bundle="${loc}" key="local.film.year" var="year" />
<fmt:message bundle="${loc}" key="local.film.country" var="country" />
<fmt:message bundle="${loc}" key="local.film.filmRating" var="filmRating" />
<fmt:message bundle="${loc}" key="local.film.description" var="description" />
<fmt:message bundle="${loc}" key="local.film.price" var="price" />
<fmt:message bundle="${loc}" key="local.film.purchased" var="purchased" />
<fmt:message bundle="${loc}" key="local.film.buyWithOneClickBtn" var="buyWithOneClickBtn" />
<fmt:message bundle="${loc}" key="local.index.editFilmBtn" var="editFilmBtn" />
<fmt:message bundle="${loc}" key="local.film.openFilmOrders" var="openFilmOrdersBtn" />


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

      <div class="col-md-8 col-sm-9 col-lg-8 col-xs-9 main content">
        <div class="panel panel-primary container-fluid">
          <div class="row panel-heading" >
          <h2 class="text-left col-md-8 col-sm-8 col-lg-8 col-xs-8">${mainHeader}</h2>
          <h5 class="text-right col-md-4 col-sm-4 col-lg-4 col-xs-4">
          	<c:if test="${requestScope.numOfPages > 1}">
          	<ul class="pagination">
			  <c:forEach begin="1" end="${requestScope.numOfPages}" step="1" var="pageNum">
			  	<c:choose> 
			  		<c:when test="${pageNum eq requestScope.curPage}">
			  			 <li class="active"><a href="<c:url value="/Controller?command=open_all_films&pageNum=${pageNum}" />" >${pageNum}</a></li>
			  		</c:when>
			  		<c:otherwise>
			  			<li><a href="<c:url value="/Controller?command=open_all_films&pageNum=${pageNum}" />" >${pageNum}</a></li>
			  		</c:otherwise>
			  	</c:choose>
			  </c:forEach>
			</ul>
			</c:if>
          </h5>
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
                
                <c:forEach items="${requestScope.films}" var="film">
                
                    <div class="panel panel-default">
                        <div class=" panel-heading" >
                          <h4 class=" text-left">${film.name}</h4>
                        </div> 
                    <div class="row panel-body">
                        <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
                          <div class="col-md-4 col-sm-4 col-xs-12 col-lg-4">
                              <figure>
                                <img src="img/films/${film.id}/folder.jpg" alt="No avatar" class="img-thumbnail img-responsive center-block" width="210" height="140" style="margin-top: 30px;" onError="this.onerror=null;this.src='img/no-img.jpg';"/> 
                              </figure>
                          </div>
                          <div class="col-md-8 col-sm-8 col-xs-12 col-lg-8">
                              <table class="table table-striped">
                              	<col width="40%">
  								<col width="60%">
                    			<thead>
                    				<br>
                      	<tr>
	              			<c:choose> 
	              				<c:when test="${sessionScope.isAdmin}">
	              					<td><a class="btn btn-primary" href="<c:url value="/Controller?command=open_film_edit_page&filmId=${film.id}"/>" role="button">${editFilmBtn}</a></td>
                  					<td><a class="btn btn-success" href="<c:url value="/Controller?command=open_single_film&filmId=${film.id}&pageNum=1"/>" role="button">${openFilmPage}</a></td>
	              				</c:when>
	              				<c:otherwise>
	              					<c:choose>
		              					<c:when test="${sessionScope.authUser!=null && !requestScope.userOrderFilmIds.isEmpty() && requestScope.userOrderFilmIds.contains(film.id)}">
		              						<td><button type="button" class="btn btn-default disabledBtn">${purchased}</button></td>
	                  					</c:when>
	                  					<c:otherwise>
	                  						<td><a class="btn btn-primary" href="<c:url value="/Controller?command=open_new_order_page&filmId=${film.id}"/>" role="button">${buyWithOneClickBtn}</a></td>
	                  					</c:otherwise>
                  					</c:choose>
                  					<td><a class="btn btn-success" href="<c:url value="/Controller?command=open_single_film&filmId=${film.id}&pageNum=1"/>" role="button">${openFilmPage}</a></td>
	              				</c:otherwise>
	              			</c:choose>
	                      </tr>
	                    </thead>
	                    <tbody>
                    	
                      <tr>
                        <td><b>${year}</b></td>
                        <td>${film.year}</td>
                      </tr>
                      <c:if test="${film.country != null}">
	                      <tr>
	                        <td><b>${country}</b></td>
	                        <td>${film.country.replace(",", ", ")}</td>
	                      </tr>
                      </c:if>
                      <c:if test="${film.director != null}">
	                      <tr>
	                        <td><b>${director}</b></td>
	                        <td>${film.director}</td>
	                      </tr>
                      </c:if>
                      <c:if test="${film.actors != null}">
	                      <tr>
	                        <td><b>${cast}</b></td>
	                        <td>${film.actors}</td>
	                      </tr>
                      </c:if>
                      <tr>
                        <td><b>${genre}</b></td>
                        <td>${film.genre.replace(",", ", ")}</td>
                      </tr>
                      <tr>
                        <td><b>${filmRating}</b></td>
                        <c:choose> 
                      		<c:when test="${film.rating eq 0}">
                      			<td>—</td>
                      		</c:when>
                      		<c:otherwise>
                        		<td>${film.rating}</td>
                        	</c:otherwise>
                        </c:choose>
                      </tr>
                      <c:if test="${film.description != null}">
	                      <tr>
	                        <td><b>${description}</b></td>
	                        <td>${film.description}</td>
	                      </tr>
	                  </c:if>
                      <tr>
                        <td><b>${price}</b></td>
                        <td>${film.price}</td>
                      </tr>
                    </tbody>
                </table>

                          </div>
                          
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
			  			 <li class="active"><a href="<c:url value="/Controller?command=open_all_films&pageNum=${pageNum}" />" >${pageNum}</a></li>
			  		</c:when>
			  		<c:otherwise>
			  			<li><a href="<c:url value="/Controller?command=open_all_films&pageNum=${pageNum}" />" >${pageNum}</a></li>
			  		</c:otherwise>
			  	</c:choose>
			  </c:forEach>
			</ul>
          </h5>
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