<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.common.serviceName" var="serviceName" />
<fmt:message bundle="${loc}" key="local.common.rublesShorten" var="rublesShorten" />
<fmt:message bundle="${loc}" key="local.films.openFilmPage" var="openFilmPage" />
<fmt:message bundle="${loc}" key="local.orders.userOrder" var="userOrder" />
<fmt:message bundle="${loc}" key="local.orders.orderNum" var="orderNum" />
<fmt:message bundle="${loc}" key="local.orders.orderDate" var="orderDate" />
<fmt:message bundle="${loc}" key="local.orders.orderTime" var="orderTime" />
<fmt:message bundle="${loc}" key="local.orders.filmName" var="filmName" />
<fmt:message bundle="${loc}" key="local.orders.userLogin" var="userLogin" />
<fmt:message bundle="${loc}" key="local.orders.userProfile" var="userProfile" />
<fmt:message bundle="${loc}" key="local.orders.filmPrice" var="filmPrice" />
<fmt:message bundle="${loc}" key="local.orders.discount" var="discount" />
<fmt:message bundle="${loc}" key="local.orders.orderSum" var="orderSum" />
<fmt:message bundle="${loc}" key="local.footer.siteMap.orders" var="orders" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title> ${serviceName}</title>
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
      
	  <c:set var="order" value="${requestScope.order}" />
      <c:set var="orderFilmName" value="${requestScope.filmName}" />
      <c:set var="orderUserLogin" value="${requestScope.userLogin}" />
      
      <div class="col-md-8 col-sm-9 col-lg-8 col-xs-9 main content">
        <div class="panel panel-primary container-fluid">
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
                                <img src="img/films/${order.filmId}/folder.jpg" alt="No folder" class="img-thumbnail img-responsive" width="210" height="140" style="margin-top: 30px;" onError="this.onerror=null;this.src='img/no-img.jpg';"/> 
                              </figure>
                          </div>
                          <div class="col-md-8 col-sm-8 col-xs-12 col-lg-8">
                              <table class="table table-striped">
                              	<col width="40%">
  								<col width="60%">
                    <thead>
                    </thead>
                    <tbody>
                    <br>
                      <tr>
                        <td>${orderNum}</td>
                        <td>#${order.ordNum}</td>
                      </tr>
                      <tr>
                        <td>${orderDate}</td>
                        <td>${order.date}</td>
                      </tr>
                      <tr>
                        <td>${orderTime}</td>
                        <td>${order.time}</td>
                      </tr>
                      <tr>
                      	<td>${userLogin}</td>
                      	<td>${orderUserLogin}</td>
                      </tr>
                      <tr>
                        <td>${filmName}</td>
                        <td>${orderFilmName}</td>
                      </tr>
                      <tr>
                        <td>${filmPrice}</td>
                        <td>${order.price} ${rublesShorten}</td>
                      </tr>
                      <tr>
                        <td>${discount}</td>
                        <td>${order.discount}%</td>
                      </tr>
                      <tr>
                        <td>${orderSum}</td>
                        <td>${order.payment} ${rublesShorten}</td>
                      </tr>
                      <tr>
	                      <td> 
	                      	<a class="btn btn-success" href="<c:url value="/Controller?command=open_single_film&filmId=${order.filmId}&pageNum=1"/>" role="button" >${openFilmPage}</a>
	                      </td>
	                      <td> 
	                      	<a class="btn btn-info" href="<c:url value="/Controller?command=open_user_profile&userId=${order.userId}"/>" role="button" >${userProfile}</a>
	                      </td>
                      </tr>
                      <tr>
	                      <td></td>
	                      <td></td>
                      </tr>
                    </tbody>
                </table>
                          </div>
           </div>
          </div>
      </div>
      </div>
      <jsp:include page="/WEB-INF/static/right-sidebar.jsp"></jsp:include>      
     </div>
  </div>    
  <jsp:include page="/WEB-INF/static/footer.jsp"></jsp:include>
</body>
</html>