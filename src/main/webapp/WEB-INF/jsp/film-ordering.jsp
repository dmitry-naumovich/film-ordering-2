<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.order.pageTitle" var="pageTitle" />
<fmt:message bundle="${loc}" key="local.order.pageHeader" var="pageHeader" />
<fmt:message bundle="${loc}" key="local.order.price" var="price" />
<fmt:message bundle="${loc}" key="local.order.discount" var="discount" />
<fmt:message bundle="${loc}" key="local.order.orderSum" var="orderSum" />
<fmt:message bundle="${loc}" key="local.order.rublesShorten" var="rublesShorten" />
<fmt:message bundle="${loc}" key="local.order.cancelBtn" var="cancelBtn" />
<fmt:message bundle="${loc}" key="local.order.buyBtn" var="buyBtn" />

<c:set var="film" value="${requestScope.film}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${pageTitle}</title>
  <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
  <link rel="icon"  type="image/x-icon" href="img/tab-logo.png">
  <link rel="stylesheet" href="css/bootstrap.min.css">
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
        <div class="panel panel-primary">
          <div class=" panel-heading" >
          <h2 class=" text-left"> ${pageHeader} ${film.name}</h2>
          </div> 
          <div class="row panel-body">
            <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
            	<c:if test="${errorMessage != null && !errorMessage.isEmpty()}">
					<div class="alert alert-danger fade in">
					  <a href="#" class="close" data-dismiss="alert" aria-label="close"> &times;</a>
					 ${errorMessage} 
					</div>
				</c:if>
            
                <c:set var="discountAmount" value="${requestScope.discountAmount}" />
                <c:set var="payment" value="${requestScope.orderSum}" />
                <div class="col-md-4 col-sm-4 col-xs-4 col-lg-4">
	                <figure>
	                	<img src="img/films/${film.id}/folder.jpg" alt="No folder" class="img-thumbnail img-responsive" width="210" height="140" onError="this.onerror=null;this.src='img/no-img.jpg';"/> 
	                </figure>
	            </div>
                <div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
	                <table class="table table-striped">
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
	                        <td>${price}</td>
	                        <td>${film.price} ${rublesShorten}</td>
	                      </tr>
	                      <tr>
	                        <td>${discount}</td>
	                        <td>${discountAmount}%</td>
	                      </tr>
	                      <tr>
	                        <td>${orderSum}</td>
	                        <td>${requestScope.orderSum} ${rublesShorten}</td>
	                      </tr>
	
	                      <tr>
	                        <th>
	                          <a href="#" onclick="history.back();" class="btn btn-danger" role="button">${cancelBtn}</a>
	                        </th>
	                        <td> <a href="<c:url value="/Controller?command=add_order&filmId=${film.id}&userId=${sessionScope.userId}&price=${film.price}&discount=${discountAmount}&payment=${payment}" />" class="btn btn-primary" role="button">${buyBtn}</a></td>
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