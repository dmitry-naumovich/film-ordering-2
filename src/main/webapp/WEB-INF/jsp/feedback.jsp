<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var = "language" value = "${not empty sessionScope.language ? sessionScope.language : 'en' }" scope = "session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.feedback.pageTitle" var="pageTitle" />
<fmt:message bundle="${loc}" key="local.feedback.writeToUs" var="writeToUs" />
<fmt:message bundle="${loc}" key="local.feedback.name" var="name" />
<fmt:message bundle="${loc}" key="local.feedback.surname" var="surname" />
<fmt:message bundle="${loc}" key="local.feedback.company" var="company" />
<fmt:message bundle="${loc}" key="local.feedback.phoneNumber" var="phoneNumber" />
<fmt:message bundle="${loc}" key="local.feedback.emailAdress" var="emailAdress" />
<fmt:message bundle="${loc}" key="local.feedback.yourWebSite" var="yourWebSite" />
<fmt:message bundle="${loc}" key="local.feedback.startWithHttp" var="startWithHttp" />
<fmt:message bundle="${loc}" key="local.feedback.whenToContact" var="whenToContact" />
<fmt:message bundle="${loc}" key="local.feedback.morning" var="morning" />
<fmt:message bundle="${loc}" key="local.feedback.day" var="day" />
<fmt:message bundle="${loc}" key="local.feedback.evening" var="evening" />
<fmt:message bundle="${loc}" key="local.feedback.night" var="night" />
<fmt:message bundle="${loc}" key="local.feedback.receiveNewsletter" var="receiveNewsletter" />
<fmt:message bundle="${loc}" key="local.feedback.ofCourse" var="ofCourse" />
<fmt:message bundle="${loc}" key="local.feedback.sendBtn" var="sendBtn" />

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
          <h2 class="text-left"> ${writeToUs} </h2>
          </div> 
          <div class="row panel-body">
            <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
                
  <form>
    <div class="row">
      <div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
        <div class="form-group">
          <label for="first">${name}</label>
          <input type="text" class="form-control" placeholder="" id="first" required>
        </div>
      </div>
      <!--  col-md-6 col-sm-6 col-xs-6 col-lg-6   -->

      <div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
        <div class="form-group">
          <label for="last">${surname}</label>
          <input type="text" class="form-control" placeholder="" id="last" required>
        </div>
      </div>
      <!--  col-md-6 col-sm-6 col-xs-6 col-lg-6   -->
    </div>


    <div class="row">
      <div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
        <div class="form-group">
          <label for="company">${company}</label>
          <input type="text" class="form-control" placeholder="" id="company">
        </div>


      </div>
      <!--  col-md-6 col-sm-6 col-xs-6 col-lg-6   -->

      <div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">

        <div class="form-group">
          <label for="phone">${phoneNumber}</label>
          <input type="tel" class="form-control" id="phone">
        </div>
      </div>
      <!--  col-md-6 col-sm-6 col-xs-6 col-lg-6   -->
    </div>
    <!--  row   -->


    <div class="row">
      <div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">

        <div class="form-group">
          <label for="email">${emailAdress}</label>
          <input type="email" class="form-control" id="email">
        </div>
      </div>
      <!--  col-md-6 col-sm-6 col-xs-6 col-lg-6   -->

      <div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
        <div class="form-group">
          <label for="url">${yourWebSite}<small> (${startWithHttp})</small></label>
          <input type="url" class="form-control" id="url">
        </div>

      </div>
      <!--  col-md-6 col-sm-6 col-xs-6 col-lg-6   -->
    </div>
    <!--  row   -->

    <label>${whenToContact}</label>
    <div class="radio">
      <label>
        <input type="radio" name="contact-preference" value="am">${morning}
      </label>
    </div>
    <div class="radio">
      <label>
        <input type="radio" name="contact-preference" value="pm" checked >${day}
      </label>
    </div>
    <div class="radio">
      <label>
        <input type="radio" name="contact-preference" value="pm" >${evening}
      </label>
    </div>
    <div class="radio">
      <label>
        <input type="radio" name="contact-preference" value="pm" >${night}
      </label>
    </div>

    <label for="newsletter">${receiveNewsletter}</label>
    <div class="checkbox">

      <label>
        <input type="checkbox" value="Sure!" id="newsletter"> ${ofCourse}
      </label>
    </div>
    <button type="submit" class="btn btn-primary">${sendBtn}</button>
  </form>
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