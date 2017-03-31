<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.common.serviceName" var="serviceName" />
<fmt:message bundle="${loc}" key="local.editFilm.pageTitle" var="pageTitle" />
<fmt:message bundle="${loc}" key="local.editFilm.pageHeader" var="pageHeader" />
<fmt:message bundle="${loc}" key="local.editFilm.editFilmBtn" var="editFilmBtn" />
<fmt:message bundle="${loc}" key="local.addFilm.folder" var="folder" />
<fmt:message bundle="${loc}" key="local.addFilm.frame" var="frame" />
<fmt:message bundle="${loc}" key="local.addFilm.name" var="name" />
<fmt:message bundle="${loc}" key="local.film.director" var="director" />
<fmt:message bundle="${loc}" key="local.film.cast" var="cast" />
<fmt:message bundle="${loc}" key="local.film.genre" var="genre" />
<fmt:message bundle="${loc}" key="local.film.year" var="year" />
<fmt:message bundle="${loc}" key="local.film.country" var="country" />
<fmt:message bundle="${loc}" key="local.film.composer" var="composer" />
<fmt:message bundle="${loc}" key="local.film.lengthmin" var="lengthmin" />
<fmt:message bundle="${loc}" key="local.film.description" var="description" />
<fmt:message bundle="${loc}" key="local.film.price" var="price" />
<fmt:message bundle="${loc}" key="local.widenedSearch.multilpleChoice" var="multilpleChoice" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${serviceName} - ${pageTitle}</title>
  <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
  <link rel="icon"  type="image/x-icon" href="img/tab-logo.png">
  <link rel="stylesheet" href="css/bootstrap.min.css" >
  <link rel="stylesheet" href="css/styles.css">
  <script type="text/javascript">
	  function validateForm(event)
	  {
	      event.preventDefault(); // this will prevent the submit event
	      if(document.editFilmForm.name.value=="") {
		      alert("Film name can not be left blank");
		      document.editFilmForm.name.focus();
		      return false;
			}
	      else if (document.editFilmForm.year.value=="") {
	    	  alert("Film year can not be left blank");
		      document.editFilmForm.year.focus();
		      return false;
	      }
	      else if (document.editFilmForm.director.value=="") {
	    	  alert("Film director can not be left blank");
		      document.editFilmForm.director.focus();
		      return false;
	      }
	      else if (document.editFilmForm.length.value=="") {
	    	  alert("Film length can not be left blank");
		      document.editFilmForm.length.focus();
		      return false;
	      }
	      else if (document.editFilmForm.price.value=="") {
	    	  alert("Film price can not be left blank");
		      document.editFilmForm.price.focus();
		      return false;
	      }
	      else {
	          document.editFilmForm.submit();
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

<c:set var="film" value="${requestScope.film}" />

  <div class="container-fluid"> 
    <div class="row content ">
    
      <jsp:include page="/WEB-INF/static/left-menu.jsp"></jsp:include>
      
      <div class="col-md-8 col-sm-9 col-lg-8 col-xs-9 main content">
        <div class="panel panel-primary">
          <div class=" panel-heading" >
          <h2 class=" text-left">${pageHeader} ${film.name} (${film.year})</h2>
          </div> 
          <div class="row panel-body">
            <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
				<c:if test="${errorMessage != null && !errorMessage.isEmpty()}" >
					<div class="alert alert-danger fade in">
					  <a href="#" class="close" data-dismiss="alert" aria-label="close"> &times;</a>
					 ${errorMessage} 
					</div>
				</c:if>
				
	<form  name="editFilmForm" class="form-horizontal" method="post" action="Controller?command=edit_film&filmID=${film.id}" enctype="multipart/form-data" onSubmit="return validateForm(event);">
	  	<div class="form-group">
      <label class="col-md-3 col-sm-3 col-xs-3 col-lg-3 control-label">${name}*:</label>
      <div class="col-md-9 col-sm-9 col-xs-9 col-lg-9">
        <input class="form-control" name="name" type="text" value="${film.name}" >
      </div>
    </div>
    <div class="form-group">
      <label class="col-md-3 col-sm-3 col-xs-3 col-lg-3 control-label">${year}*:</label>
      <div class="col-md-9 col-sm-9 col-xs-9 col-lg-9">
        <input class="form-control" name="year" type="text" value="${film.year}" >
      </div>
    </div>
    <div class="form-group">
      <label class="col-md-3 col-sm-3 col-xs-3 col-lg-3 control-label">${director}*:</label>
      <div class="col-md-9 col-sm-9 col-xs-9 col-lg-9">
        <input class="form-control" name="director" type="text" value="${film.director}" >
      </div>
    </div>
    <div class="form-group">
      <label class="col-md-3 col-sm-3 col-xs-3 col-lg-3 control-label">${cast}:</label>
      <div class="col-md-9 col-sm-9 col-xs-9 col-lg-9">
        <input class="form-control" name="cast" type="text" value="${film.actors}">
      </div>
    </div>
    <div class="form-group">
    	<div class="col-md-3 col-sm-3 col-xs-3 col-lg-3 text-right">
	  		<label class="control-label" for="sel2">${country}:</label>
	  		<div class="text-right"> ${multilpleChoice}</div>
	  	</div>
	  <div class="col-md-9 col-sm-9 col-xs-9 col-lg-9">
		  <select multiple class="form-control" name="country" id="sel2">
		    <c:forEach items="${requestScope.availableCountries}" var="fCountry">
		    	<c:choose>
		    		<c:when test="${film.country.contains(fCountry)}">
		    			<option selected="selected">${fCountry}</option>
		    		</c:when>
		    		<c:otherwise>
		    			<option>${fCountry}</option>
		    		</c:otherwise>
		    	</c:choose>
		    </c:forEach>
		  </select>
	  </div>
    </div>
    <div class="form-group">
      <label class="col-md-3 col-sm-3 col-xs-3 col-lg-3 control-label">${composer}:</label>
      <div class="col-md-9 col-sm-9 col-xs-9 col-lg-9">
        <input class="form-control" name="composer" type="text" value="${film.composer}">
      </div>
    </div>
    <div class="form-group">
    	<div class="col-md-3 col-sm-3 col-xs-3 col-lg-3 text-right">
	  		<label class="control-label" for="sel1">${genre}:</label>
	    	<div class="text-right"> ${multilpleChoice}</div>
	   </div>
	  <div class="col-md-9 col-sm-9 col-xs-9 col-lg-9">
		  <select multiple class="form-control" name="genre" id="sel1">
		    <c:forEach items="${requestScope.availableGenres}" var="fGenre">
		    	<c:choose>
		    		<c:when test="${film.genre.contains(fGenre)}">
		    			<option selected>${fGenre}</option>
		    		</c:when>
		    		<c:otherwise>
		    			<option>${fGenre}</option>
		    		</c:otherwise>
		    	</c:choose>
		    </c:forEach>
		  </select>
	  </div>
    </div>
     <div class="form-group">
      <label class="col-md-3 col-sm-3 col-xs-3 col-lg-3 control-label">${lengthmin}*:</label>
      <div class="col-md-9 col-sm-9 col-xs-9 col-lg-9">
        <input class="form-control" name="length" type="text" value="${film.length}">
      </div>
    </div>
    <div class="form-group">
      <label class="col-md-3 col-sm-3 col-xs-3 col-lg-3 control-label">${price}*:</label>
      <div class="col-md-9 col-sm-9 col-xs-9 col-lg-9">
        <input class="form-control" name="price" type="text" value="${film.price}">
      </div>
    </div>
    <div class="form-group">
      <label class="col-md-3 col-sm-3 col-xs-3 col-lg-3 control-label">${folder}: </label>
      <div class="col-md-3 col-sm-3 col-xs-3 col-lg-3">
            <input type="file" name="folder" size="1" accept=".gif,.jpg,.jpeg,.png, image/png, image/gif, image/jpg, image/jpeg">
      </div>
      <div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
      	<figure>
          <img src="img/films/${film.id}/folder.jpg" alt="$ {film.name}" class="img-thumbnail img-responsive center-block" width="80" height="140" style="margin-top: 30px;" onError="this.onerror=null;this.src='img/no-img.jpg';"/> 
        </figure>
      </div>
    </div>   
    <div class="form-group">
      <label class="col-md-3 col-sm-3 col-xs-3 col-lg-3 control-label">${frame}: </label>
      <div class="col-md-3 col-sm-3 col-xs-3 col-lg-3">
            <input type="file" name="frame" size="1" accept=".gif,.jpg,.jpeg,.png, image/png, image/gif, image/jpg, image/jpeg">
      </div>
      <div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
      	<figure>
          <img src="img/films/${film.id}/01.jpg" alt="${film.name}" class="img-thumbnail img-responsive center-block" width="80" height="140" style="margin-top: 30px;" onError="this.onerror=null;this.src='img/no-img.jpg';"/> 
        </figure>
      </div>
    </div>    
    <div class="form-group">
      <label class="col-md-3 col-sm-3 col-xs-3 col-lg-3 control-label" for="comment">${description}:</label>
      <div class="col-md-9 col-sm-9 col-xs-9 col-lg-9">
        <textarea class="form-control" name="description" rows="5">${film.description}</textarea>
      </div>
    </div>  
	  <div class="col-md-2 col-sm-2 col-xs-2 col-lg-2 col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-lg-offset-2">
	  	<button type="submit" class="btn btn-primary">${editFilmBtn}</button>
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