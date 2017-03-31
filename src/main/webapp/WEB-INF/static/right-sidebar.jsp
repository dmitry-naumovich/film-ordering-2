<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="col-md-2 col-lg-2"> 
      <div id="right-sidebar">
      	<jsp:include page="/Controller"> 
				<jsp:param name="command" value="get_sidebar_news"/>
		</jsp:include>
      	<c:forEach items="${requestScope.sidebarNews}" var="news">
      		<figure>
      		<a href="<c:url value="/Controller?command=open_single_news&newsID=${news.id}"/>" >
      			<img src="img/news/${news.id}/01.jpg" alt="Sidebar News Img" class="img-thumbnail img-responsive" width="180" height="140" /> 
      				<figcaption style="padding: 1px 5px 1px 5px;">
      					<a href="<c:url value="/Controller?command=open_single_news&newsID=${news.id}" />" > ${news.title} </a>
      				</figcaption> 
      				</a>
      		</figure>
      		
		            <br>
        </c:forEach>
      </div>
    </div>