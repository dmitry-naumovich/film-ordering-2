<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	
	<c:set var = "language" value = "${not empty sessionScope.language ? sessionScope.language : 'en' }" scope = "session"/>
	<fmt:setLocale value="${language}" />
   	<fmt:setBundle basename="local" var="loc" />
   	<fmt:message bundle="${loc}" key="local.leftMenu.mainPage" var="mainPage" />
   	<fmt:message bundle="${loc}" key="local.leftMenu.filmsPage" var="filmsPage" />
   	<fmt:message bundle="${loc}" key="local.leftMenu.profilePage" var="profilePage" />
   	<fmt:message bundle="${loc}" key="local.leftMenu.aboutUsPage" var="aboutUsPage" />
   	<fmt:message bundle="${loc}" key="local.leftMenu.newsPage" var="newsPage" />
   	<fmt:message bundle="${loc}" key="local.leftMenu.widenedSearchPage" var="widenedSearchPage" />
   	
<div class="col-md-2 col-sm-3 col-lg-2 col-xs-3"> 
      <div class="left-sidebar" id="myScrollspy">
        <ul id='left-menu' class="nav nav-pills nav-stacked" data-spy="affix" data-offset-top="0" data-offset-bottom="240">
          <li><a href="index.jsp"> ${mainPage} </a></li>
          <li><a href="<c:url value="/Controller?command=open_all_films&pageNum=1"/>" > ${filmsPage} </a></li>
          <li><a href="<c:url value="/Controller?command=open_user_profile&userID=${sessionScope.userID}"/>"> ${profilePage} </a></li> 
          <li><a href="<c:url value="/Controller?command=open_about_us_page"/>"> ${aboutUsPage} </a></li>
          <li><a href="<c:url value="/Controller?command=open_all_news&pageNum=1"/>"> ${newsPage} </a></li>
          <li><a href="<c:url value="/Controller?command=open_widened_search_page"/>"> ${widenedSearchPage} </a></li>
        </ul>
      </div>
</div>