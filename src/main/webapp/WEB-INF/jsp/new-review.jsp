<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.addReview.pageTitle" var="pageTitle" />
<fmt:message bundle="${loc}" key="local.addReview.pageHeader" var="pageHeader" />
<fmt:message bundle="${loc}" key="local.addReview.yourMark" var="yourMark" />
<fmt:message bundle="${loc}" key="local.addReview.chooseType" var="chooseType" />
<fmt:message bundle="${loc}" key="local.addReview.positive" var="positive" />
<fmt:message bundle="${loc}" key="local.addReview.neutral" var="neutral" />
<fmt:message bundle="${loc}" key="local.addReview.negative" var="negative" />
<fmt:message bundle="${loc}" key="local.addReview.reviewText" var="reviewText" />
<fmt:message bundle="${loc}" key="local.addReview.sendBtn" var="sendBtn" />

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
  <script type="text/javascript">
  function validateForm(event)
  {
      event.preventDefault(); // this will prevent the submit event
      
      var mark = document.getElementsByName('mark');
      var isMarkChecked = false;
      for ( var i = 0; i < mark.length; i++) {
          if(mark[i].checked) {
        	  isMarkChecked = true;
              break;
          }
      }
      
      var type = document.getElementsByName('type');
      var isTypeChecked = false;
      for ( var i = 0; i < type.length; i++) {
          if(type[i].checked) {
        	  isTypeChecked = true;
              break;
          }
      }
      if(!isMarkChecked)   {
    	  alert("Mark can not be empty");
          return false;
      }
      else if(!isTypeChecked) {
        alert("Type can not be left blank");
        return false;
      }
      else if(document.getElementById("reviewTextArea").value.length < 50) {
        alert("Review text must contain at least 50 symbols");
        document.newReviewForm.reviewText.focus();
        return false;
      }
      else {
          document.newReviewForm.submit();
      }
  }
  </script>
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
	  <c:set var="film" value="${requestScope.film}" />
       <div class="col-md-8 col-sm-9 col-lg-8 col-xs-9 main content">
        <div class="panel panel-primary">
          <div class=" panel-heading" >
          <h2 class=" text-left">${pageHeader} ${film.name}</h2>
          </div> 
          <div class="row panel-body">
            <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
				<c:if test="${errorMessage != null && !errorMessage.isEmpty()}">
					<div class="alert alert-danger fade in">
					  <a href="#" class="close" data-dismiss="alert" aria-label="close"> &times;</a>
					 ${errorMessage} 
					</div>
				</c:if>

<form  name="newReviewForm" class="form-horizontal" method="post"action="Controller" onSubmit="return validateForm(event);">
  <div class="form-group">
    	<input type="hidden" name="command" value="add_review"/>
    	<input type="hidden" name="userID" value="${sessionScope.userID}" />
  		<input type="hidden" name="filmID" value="${film.id}" />
  	</div>
  	
  <div class="form-group">
      <label class="col-md-2 col-sm-2 col-xs-2 col-lg-2 control-label">${yourMark}</label>
      <div class="col-md-10 col-sm-10 col-xs-10 col-lg-10">
      <label class="radio-inline"><input type="radio" name="mark" value="1">1</label>
      <label class="radio-inline"><input type="radio" name="mark" value="2">2</label>
      <label class="radio-inline"><input type="radio" name="mark" value="3">3</label>
      <label class="radio-inline"><input type="radio" name="mark" value="4">4</label>
      <label class="radio-inline"><input type="radio" name="mark" value="5">5</label>
      </div>
    </div>
  <div class="form-group">
      <label class="col-md-2 col-sm-2 col-xs-2 col-lg-2 control-label">${chooseType} </label>
      <div class="col-md-10 col-sm-10 col-xs-10 col-lg-10">
      <label class="radio-inline"><input type="radio" name="type" value="ps">${positive}</label>
      <label class="radio-inline"><input type="radio" name="type" value="nt">${neutral}</label>
      <label class="radio-inline"><input type="radio" name="type" value="ng">${negative}</label>
      </div>
    </div>
  <div class="form-group">
    <label class="col-md-2 col-sm-2 col-xs-2 col-lg-2 control-label">${reviewText}</label>
    <div class="col-md-10 col-sm-10 col-xs-10 col-lg-10"> 
    	<textarea class="form-control" rows="10" name="reviewText" id="reviewTextArea"></textarea>
    </div>
  </div>
   <div class="col-md-2 col-sm-2 col-xs-2 col-lg-2 col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-lg-offset-2">
		<button type="submit" class="btn btn-primary">${sendBtn}</button>
	</div>
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