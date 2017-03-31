<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.film.pageTitle" var="pageTitle" />
<fmt:message bundle="${loc}" key="local.film.director" var="director" />
<fmt:message bundle="${loc}" key="local.film.cast" var="cast" />
<fmt:message bundle="${loc}" key="local.film.genre" var="genre" />
<fmt:message bundle="${loc}" key="local.film.year" var="year" />
<fmt:message bundle="${loc}" key="local.film.country" var="country" />
<fmt:message bundle="${loc}" key="local.film.composer" var="composer" />
<fmt:message bundle="${loc}" key="local.film.lengthmin" var="lengthmin" />
<fmt:message bundle="${loc}" key="local.film.filmRating" var="filmRating" />
<fmt:message bundle="${loc}" key="local.film.originName" var="originName" />
<fmt:message bundle="${loc}" key="local.film.description" var="description" />
<fmt:message bundle="${loc}" key="local.film.price" var="price" />
<fmt:message bundle="${loc}" key="local.film.buyWithOneClickBtn" var="buyWithOneClickBtn" />
<fmt:message bundle="${loc}" key="local.film.writeReviewBtn" var="writeReviewBtn" />
<fmt:message bundle="${loc}" key="local.film.myReviewBtn" var="myReviewBtn" />
<fmt:message bundle="${loc}" key="local.film.myOrderBtn" var="myOrderBtn" />
<fmt:message bundle="${loc}" key="local.film.reviewBy" var="reviewBy" />
<fmt:message bundle="${loc}" key="local.reviews.mark" var="mark" />
<fmt:message bundle="${loc}" key="local.reviews.date" var="date" />
<fmt:message bundle="${loc}" key="local.reviews.openSingleReviewBtn" var="openSingleReviewBtn" />
<fmt:message bundle="${loc}" key="local.index.rublesShorten" var="rublesShorten" />
<fmt:message bundle="${loc}" key="local.index.editFilmBtn" var="editFilmBtn" />
<fmt:message bundle="${loc}" key="local.film.deleteFilmBtn" var="deleteFilmBtn" />
<fmt:message bundle="${loc}" key="local.film.openFilmOrders" var="openFilmOrdersBtn" />

<c:set var="film" value="${requestScope.film}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${pageTitle} - ${film.name} (${film.year })</title>
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
          	<h2 class="text-left">${film.name}</h2>
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
            	<div class="col-md-4 col-sm-4 col-xs-12 col-lg-4">
                  <figure>
                    <img src="img/films/${film.id}/folder.jpg" alt="No folder" class="img-thumbnail img-responsive center-block" width="210" height="140" style="margin-top: 30px;" onError="this.onerror=null;this.src='img/no-img.jpg';"/> 
                  </figure>
                  <br>
          			<c:choose> 
          				<c:when test="${sessionScope.isAdmin}">
          					<a class="btn btn-primary center-block" href="<c:url value="/Controller?command=open_film_edit_page&filmID=${film.id}"/>" role="button">${editFilmBtn}</a>
          					<br>
             				<a class="btn btn-warning center-block" href="<c:url value="/Controller?command=open_film_orders&filmID=${film.id}&pageNum=1"/>" role="button">${openFilmOrdersBtn}</a>
             				<br>
             				<a class="btn btn-danger center-block" href="<c:url value="/Controller?command=delete_film&filmID=${film.id}"/>" role="button">${deleteFilmBtn}</a>
          				</c:when>
          				<c:otherwise>
          				<c:choose>
          						<c:when test="${!requestScope.ownOrderExists}">
          							<a class="btn btn-primary center-block" href="<c:url value="/Controller?command=open_new_order_page&filmID=${film.id}"/>" role="button">${buyWithOneClickBtn}</a>
             					</c:when>
             					<c:otherwise>
	             					<a class="btn btn-warning center-block" href="<c:url value="/Controller?command=open_single_order&orderNum=${requestScope.orderNum}"/>" role="button">${myOrderBtn}</a>
	          					</c:otherwise>
	          				</c:choose>
          					
          					<br>
          					<c:choose>
          						<c:when test="${!requestScope.ownReviewExists}">
          							<a class="btn btn-primary center-block" href="<c:url value="/Controller?command=open_new_review_page&filmID=${film.id}"/>" role="button">${writeReviewBtn}</a>
             					</c:when>
             					<c:otherwise>
	             					<a class="btn btn-warning center-block" href="<c:url value="/Controller?command=open_single_review&userID=${sessionScope.userID}&filmID=${film.id}"/>" role="button">${myReviewBtn}</a>
	          					</c:otherwise>
	          				</c:choose>
          				</c:otherwise>
          			</c:choose>
                </div>
                <div class="col-md-8 col-sm-8 col-xs-12 col-lg-8">
	                <table class="table table-striped">
	                	<col width="40%">
  						<col width="60%">
	                
	                    <thead>
	                      <tr>
	                        <th>
	                          <p>    </p>
	                        </th>
	                        <th>
	                          <p>     </p>
	                        </th>
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
	                      <tr>
	                        <td><b>${director}</b></td>
	                        <td>${film.director}</td>
	                      </tr>
	                      <c:if test="${film.actors != null}">
		                      <tr>
		                        <td><b>${cast}</b></td>
		                        <td>${film.actors}</td>
		                      </tr>
	                      </c:if>
	                      <c:if test="${film.composer != null}">
		                      <tr>
		                        <td><b>${composer}</b></td>
		                        <td>${film.composer}</td>
		                      </tr>
	                       </c:if>
	                       
	                       <c:if test="${film.genre != null}">
		                      <tr>
		                        <td><b>${genre}</b></td>
		                        <td>${film.genre.replace(",", ", ")}</td>
		                      </tr>
	                      </c:if>
	                      <tr>
	                        <td><b>${lengthmin}</b></td>
	                        <td>${film.length}</td>
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
				<br>
				<br>
				<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
                <div class="panel-group">
						<c:forEach items="${requestScope.reviews}" var="review" varStatus="status">
							<c:set var="authorLogin" value="${requestScope.logins[status.index]}" />
			                     
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
		                        	<h4 class=" text-left"> 
					                	${reviewBy} 
					                	<a href="<c:url value="/Controller?command=open_user_profile&userID=${review.author}" />" > ${authorLogin} </a>
					                </h4>
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
		                        			<a href="<c:url value="/Controller?command=open_single_review&userID=${review.author}&filmID=${review.filmId}" />"  >${openSingleReviewBtn}</a>
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
          </div>
          <c:if test="${requestScope.numOfPages > 1}">
          <div class="row panel-footer" >
	         <h5 class="text-right">
	         	<ul class="pagination">
			  <c:forEach begin="1" end="${requestScope.numOfPages}" step="1" var="pageNum">
			  	<c:choose> 
			  		<c:when test="${pageNum eq requestScope.curPage}">
			  			 <li class="active"><a href="<c:url value="/Controller?command=open_single_film&filmID=${film.id}&pageNum=${pageNum}" />" >${pageNum}</a></li>
			  		</c:when>
			  		<c:otherwise>
			  			<li><a href="<c:url value="/Controller?command=open_single_film&filmID=${film.id}&pageNum=${pageNum}" />" >${pageNum}</a></li>
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