<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:set var = "language" value = "${not empty sessionScope.language ? sessionScope.language : 'en' }" scope = "session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.header.serviceName" var="serviceName" />
<fmt:message bundle="${loc}" key="local.header.search" var="search" />
<fmt:message bundle="${loc}" key="local.header.ruLanguage" var="ru_lang" />
<fmt:message bundle="${loc}" key="local.header.enLanguage" var="en_lang" />
<fmt:message bundle="${loc}" key="local.header.login" var="login" />
<fmt:message bundle="${loc}" key="local.header.logout" var="logout" />
<fmt:message bundle="${loc}" key="local.header.password" var="password" />
<fmt:message bundle="${loc}" key="local.header.signIn" var="signIn" />
<fmt:message bundle="${loc}" key="local.header.signUp" var="signUp" />
<fmt:message bundle="${loc}" key="local.header.profilePage" var="profile" />
<fmt:message bundle="${loc}" key="local.header.myReviews" var="myReviews" />
<fmt:message bundle="${loc}" key="local.header.myOrders" var="myOrders" />
<fmt:message bundle="${loc}" key="local.header.settings" var="settings" />
<fmt:message bundle="${loc}" key="local.header.addAFilm" var="addAFilm" />
<fmt:message bundle="${loc}" key="local.header.addNews" var="addNews" />

<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="nav navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">${serviceName}</a>
            <form name="searchForm" class="navbar-form navbar-left search-form" action="Controller" role="search">
	          <div class="form-group">
			    	<input type="hidden" name="command" value="search_films" />
			  	</div>
	          <div class="input-group">
	              <input class="form-control" name="searchText" type="search" placeholder="${search}" required>
	              <div class="input-group-btn">
	                <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
	              </div>
	          </div>
	        </form>
        </div>
        
        <div id="navbar" class="navbar-collapse collapse">
	        
	        
                <c:choose>
              <c:when test="${sessionScope.authUser!=null}"> 
              		<form class="navbar-form nav navbar-right profileMenuBar">
              		
              		<div class="form-group lang-group">
                              		<c:choose>
										<c:when test="${language eq 'en'}">
											 <span style="color:white">${en_lang}</span>
											 <a href = "<c:url value="/Controller?command=change_language">
												<c:param name="language" value="ru" ></c:param> </c:url>">${ru_lang}</a>
										</c:when>
										<c:otherwise>
											<a href = "<c:url value="/Controller?command=change_language">
												<c:param name="language" value="en" ></c:param> </c:url>">${en_lang}</a>
											 <span style="color:white">${ru_lang}</span>
										</c:otherwise>
									</c:choose>
			          </div>
						<div class="form-group dropdown">
                                <a href="<c:url value="/Controller?command=open_user_profile&userID=${sessionScope.userID}"/>" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" style="color:white;">${sessionScope.authUser}<span class="caret"></span></a>
                                <c:choose>
	                                <c:when test="${sessionScope.isAdmin}">
	                                	<ul class="dropdown-menu" role="menu">
                                		  <li><a href="<c:url value="/Controller?command=open_user_profile&userID=${sessionScope.userID}"/>" role="menuItem">${profile}</a></li>
		                                  <li><a href="<c:url value="/Controller?command=open_new_film_page"/>" role="menuItem">${addAFilm}</a></li>
		                                  <li><a href="<c:url value="/Controller?command=open_new_news_page"/>" role="menuItem">${addNews}</a></li>
		                                  <li><a href="<c:url value="/Controller?command=open_user_settings&userID=${sessionScope.userID}"/>"  role="menuItem">${settings}</a></li>
		                                  <li class="divider"></li>
		                                  <li><a href="<c:url value="/Controller?command=logout" />" role="menuItem">${logout}</a></li>
			                            </ul>
	                                </c:when>
                                	<c:otherwise> 
		                                <ul class="dropdown-menu" role="menu">
		                                  <li><a href="<c:url value="/Controller?command=open_user_profile&userID=${sessionScope.userID}"/>" role="menuItem">${profile}</a></li>
		                                  <li><a href="<c:url value="/Controller?command=open_user_reviews&userID=${sessionScope.userID}&pageNum=1"/>" role="menuItem">${myReviews}</a></li>
		                                  <li><a href="<c:url value="/Controller?command=open_user_orders&userID=${sessionScope.userID}&pageNum=1"/>" role="menuItem">${myOrders}</a></li>
		                                  <li><a href="<c:url value="/Controller?command=open_user_settings&userID=${sessionScope.userID}"/>" role="menuItem">${settings}</a></li>
		                                  <li class="divider"></li>
		                                  <li><a href="<c:url value="/Controller?command=logout" />" role="menuItem">${logout}</a></li>
		                                </ul>
                                	</c:otherwise>
                                </c:choose>
                     	</div>
                     	<span class="divider-vertical"> </span>
                     	
              		</form>
              </c:when>
              <c:otherwise>
              
              		<form class="navbar-form navbar-nav nav navbar-right" action="Controller" method="post">
                              <div class="form-group">
                              	<input type="hidden" name="command" value="login" />
                              </div>
                              <div class="form-group">
                                <input type="text" placeholder="${login}" class="form-control" name="login" required>
                              </div>
                              <div class="form-group">
                                <input type="password" placeholder="${password}" class="form-control" name="password" required>
                              </div>
                              <button type="submit" class="btn btn-primary" name="Sign in">${signIn}</button>
                              <a href="<c:url value="/Controller?command=open_sign_up_page" />" class="btn btn-primary" role="button">${signUp}</a>
                              <span class="divider-vertical"> </span>
                              
                              <div class="form-group lang-group" style="margin-right:10px">
                              		<c:choose>
										<c:when test="${language eq 'en'}">
											 <span style="color:white">${en_lang}</span>
											 <a href = "<c:url value="/Controller?command=change_language">
												<c:param name="language" value="ru" ></c:param> </c:url>">${ru_lang}</a>
										</c:when>
										<c:otherwise>
											<a href = "<c:url value="/Controller?command=change_language">
												<c:param name="language" value="en" ></c:param> </c:url>">${en_lang}</a>
											 <span style="color:white">${ru_lang}</span>
										</c:otherwise>
									</c:choose>
			          			</div>                     
              </form>
               </c:otherwise>
              </c:choose>
          
        </div>
      </div>
  </nav>