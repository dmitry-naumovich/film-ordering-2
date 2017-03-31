<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var = "language" value = "${not empty sessionScope.language ? sessionScope.language : 'en' }" scope = "session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.help.pageTitle" var="pageTitle" />
<fmt:message bundle="${loc}" key="local.help.header" var="helpHeader" />
<fmt:message bundle="${loc}" key="local.help.firstQuestion" var="firstQuestion" />
<fmt:message bundle="${loc}" key="local.help.secondQuestion" var="secondQuestion" />
<fmt:message bundle="${loc}" key="local.help.firstBtn" var="firstBtn" />
<fmt:message bundle="${loc}" key="local.help.secondBtn" var="secondBtn" />

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
        <div class="panel panel-primary">
          <div class=" panel-heading" >
          <h2 class=" text-left"> ${helpHeader} </h2>
          </div> 
          <div class="row panel-body">
            <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
              <p>  ${firstQuestion} <a href="<c:url value="/Controller?command=open_feedback_page"/>" class="btn btn-primary" role="button">${firstBtn}</a> </p>
              <p>  ${secondQuestion} <a href="<c:url value="/Controller?command=open_about_us_page"/>" class="btn btn-primary" role="button">${secondBtn}</a> </p>

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