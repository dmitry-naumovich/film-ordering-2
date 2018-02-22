<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.common.serviceName" var="serviceName" />
<fmt:message bundle="${loc}" key="local.users.pageTitle" var="pageTitle" />
<fmt:message bundle="${loc}" key="local.users.pageHeader" var="pageHeader" />
<fmt:message bundle="${loc}" key="local.profile.editProfile" var="editProfile" />
<fmt:message bundle="${loc}" key="local.leftMenu.profilePage" var="userProfile" />
<fmt:message bundle="${loc}" key="local.profile.userReviews" var="userReviews" />
<fmt:message bundle="${loc}" key="local.profile.userOrders" var="userOrders" />
<fmt:message bundle="${loc}" key="local.profile.banUser" var="banUser" />
<fmt:message bundle="${loc}" key="local.profile.setDiscount" var="setDiscount" />
<fmt:message bundle="${loc}" key="local.profile.currentDiscount" var="currentDiscount" />
<fmt:message bundle="${loc}" key="local.profile.editProfile" var="editProfile" />
<fmt:message bundle="${loc}" key="local.profile.name" var="name" />
<fmt:message bundle="${loc}" key="local.profile.surname" var="surname" />
<fmt:message bundle="${loc}" key="local.profile.login" var="login" />
<fmt:message bundle="${loc}" key="local.profile.regDateTime" var="regDateTime" />
<fmt:message bundle="${loc}" key="local.profile.banned" var="banned" />
<fmt:message bundle="${loc}" key="local.ban.banLength" var="banLength" />
<fmt:message bundle="${loc}" key="local.ban.banReason" var="banReason" />
<fmt:message bundle="${loc}" key="local.ban.banUserBtn" var="banUserBtn" />
<fmt:message bundle="${loc}" key="local.ban.closeBtn" var="closeBtn" />
<fmt:message bundle="${loc}" key="local.ban.enterReason" var="enterReason" />
<fmt:message bundle="${loc}" key="local.ban.enterLength" var="enterLength" />
<fmt:message bundle="${loc}" key="local.ban.userWord" var="userWord" />
<fmt:message bundle="${loc}" key="local.ban.unbanBtn" var="unbanBtn" />
<fmt:message bundle="${loc}" key="local.ban.isBanned" var="isBanned" />
<fmt:message bundle="${loc}" key="local.discount.editDiscount" var="editDiscount" />
<fmt:message bundle="${loc}" key="local.discount.setDiscountFor" var="setDiscountFor" />
<fmt:message bundle="${loc}" key="local.discount.editDiscountFor" var="editDiscountFor" />
<fmt:message bundle="${loc}" key="local.discount.amount" var="amount" />
<fmt:message bundle="${loc}" key="local.discount.enterAmount" var="enterAmount" />
<fmt:message bundle="${loc}" key="local.discount.endDate" var="endDate" />
<fmt:message bundle="${loc}" key="local.discount.endTime" var="endTime" />
<fmt:message bundle="${loc}" key="local.discount.dateFormat" var="dateFormat" />
<fmt:message bundle="${loc}" key="local.discount.timeFormat" var="timeFormat" />
<fmt:message bundle="${loc}" key="local.discount.deleteDiscountBtn" var="deleteDiscountBtn" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title> ${serviceName} - ${pageTitle}</title>
  <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
  <link rel="icon"  type="image/x-icon" href="img/tab-logo.png">
  <link rel="stylesheet" href="css/bootstrap.min.css" >
  <link rel="stylesheet" href="css/styles.css">
  <script type="text/javascript">
	  function validateBanForm(event)
	  {
	      event.preventDefault(); // this will prevent the submit event
	      if(document.banForm.banLength.value=="") {
		      alert("Ban length can not be left blank");
		      document.banForm.banLength.focus();
		      return false;
			}
	      else if(document.getElementByClass("banReasonTextArea").value.length < 2) {
	          alert("Ban reason must be at least 2 symbols");
	          document.banForm.banReason.focus();
	          return false;
	        }
	      else {
	          document.banForm.submit();
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
	  
	  
      <div class="col-md-8 col-sm-9 col-lg-8 col-xs-9 main content">
        <div class="panel panel-primary container-fluid">
          <div class="panel-heading row" >
          		<h2 class="text-left col-md-8 col-sm-8 col-lg-8 col-xs-8">${pageHeader}</h2>
          		<h5 class="text-right col-md-4 col-sm-4 col-lg-4 col-xs-4">
	          	<c:if test="${requestScope.numOfPages > 1}">
		          	<ul class="pagination">
					  <c:forEach begin="1" end="${requestScope.numOfPages}" step="1" var="pageNum">
					  	<c:choose> 
					  		<c:when test="${pageNum eq requestScope.curPage}">
					  			 <li class="active"><a href="<c:url value="/Controller?command=open_all_users&pageNum=${pageNum}" />" >${pageNum}</a></li>
					  		</c:when>
					  		<c:otherwise>
					  			<li><a href="<c:url value="/Controller?command=open_all_users&pageNum=${pageNum}" />" >${pageNum}</a></li>
					  		</c:otherwise>
					  	</c:choose>
					  </c:forEach>
					</ul>
				</c:if>
          </h5>
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
                
                <c:forEach items="${requestScope.users}" var="user" varStatus="status">
                
                    <div class="panel panel-default container-fluid">
                    <div class="row panel-body">
                        <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 text-center">
                          <div class="col-md-4 col-sm-4 col-xs-12 col-lg-4">
                            <figure><img src="img/avatars/avatars${user.id}.gif" alt="No avatar" class="img-thumbnail img-responsive" width="150" height="150" onError="this.onerror=null;this.src='img/no-avatar.jpg';"/> </figure>
                          </div>
                          <div class="col-md-8 col-sm-8 col-xs-12 col-lg-8">
                          
                          <table class="table table-striped">
                          <col width="40%">
  						  <col width="60%">
                    <thead>
                      <tr>
                        <th>
                          
                        </th>
                        <th>
                        	
                        </th>
                      </tr>
                      <tr>
                        
                      </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${name}</td>
                        <td>${user.name}</td>
                      </tr>
                      <tr>
                        <td>${surname}</td>
                        <td>${user.surname }</td>
                      </tr>
                      <tr>
                        <td>${login}</td>
                        <td>${user.login}</td>
                      </tr>
                      <tr>
                        <td>${regDateTime}</td>
                        <td>${user.regDate} ${user.regTime}</td>
                      </tr>
                      <c:if test="${requestScope.discountList[status.index] != null}">
	                      
		                      <tr>
		                      	<td>${currentDiscount}</td>
		                      	<td>${requestScope.discountList[status.index].amount} %</td>
		                      </tr>
	                      
                      </c:if>
                   
                    </tbody>
                </table>
                          </div>
                          
                        </div>
                        <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 text-center">
                        	<a href="<c:url value="/Controller?command=open_user_profile&userId=${user.id}"/>" class="btn btn-primary" role="button">${userProfile}</a>
                          <c:choose>
                           	<c:when test="${sessionScope.isAdmin && user.id != sessionScope.userId}">
	                          	<a href="<c:url value="/Controller?command=open_user_orders&userId=${user.id}&pageNum=1"/>" class="btn btn-default" role="button">${userOrders}</a>
	                          	<a href="<c:url value="/Controller?command=open_user_reviews&userId=${user.id}&pageNum=1"/>" class="btn btn-info" role="button">${userReviews}</a>
	                          	<c:choose>
	                          		<c:when test="${requestScope.discountList[status.index] == null}">
	                          			<a data-toggle="modal" data-target="#setDiscountModal${user.id}" class="btn btn-warning" role="button">${setDiscount}</a>
	                          		</c:when>
	                          		<c:otherwise>
	                          			<a data-toggle="modal" data-target="#editDiscountModal${user.id}" class="btn btn-default" role="button">${editDiscount}</a>
	                          		</c:otherwise>
	                          	</c:choose>
	                          	
	                          	<c:choose>
	                          		<c:when test="${!banList[status.index]}">
	                          			<a data-toggle="modal" data-target="#banModal${user.id}" class="btn btn-danger" role="button">${banUser}</a>
	                          		</c:when>
	                          		<c:otherwise>
	                          			<a data-toggle="modal" data-target="#unbanModal${user.id}" class="btn btn-default" role="button">${banned}</a>
	                          		</c:otherwise>
	                          	</c:choose>
	                        </c:when>
	                        <c:when test="${sessionScope.isAdmin && user.id == sessionScope.userId}">
	                        	<a href="<c:url value="/Controller?command=open_user_settings&userId=${sessionScope.userId}"/>" class="btn btn-danger" role="button">${editProfile}</a>
	                        </c:when>
	                        
                      	 </c:choose>
                        </div>
                        </div>
                        </div>
                        
                        	<div class="modal fade" id="banModal${user.id}" role="dialog">
							    <div class="modal-dialog">
							      <div class="modal-content">
							      	<form  name="banForm" class="form-horizontal" method="post" action="Controller" >
							        <div class="modal-header">
							          <button type="button" class="close" data-dismiss="modal">&times;</button>
							          <h4 class="modal-title">${banUser} ${user.login}</h4>
							        </div>
							        <div class="modal-body">
							        	
							          		<div class="form-group">
										    	<input type="hidden" name="command" value="ban_user"/>
										  	</div>
										  	<div class="form-group">
										    	<input type="hidden" name="userId" value="${user.id}"/>
										  	</div>
										  	
							          		<div class="form-group">
									      		<label class="col-sm-3 control-label">${banLength}</label>
									      		<div class="col-sm-9">
									        		<input class="form-control" name="banLength" type="text" placeholder="${enterLength}">
									      		</div>
											</div>
											<div class="form-group">
											    <label class="col-sm-3 control-label">${banReason}</label>
											    <div class="col-sm-9"> 
											    	<textarea class="form-control banReasonTextArea" rows="3" name="banReason" placeholder="${enterReason}"></textarea>
											    </div>
											</div>
							        	
							        </div>
							        <div class="modal-footer">
							          <button type="submit" class="btn btn-danger">${banUserBtn}</button>
							          <button type="button" class="btn btn-default" data-dismiss="modal">${closeBtn}</button>
							        </div>
							        </form>
							      </div>
							      
							    </div>
							  </div>
							  
							  <div class="modal fade" id="unbanModal${user.id}" role="dialog">
							    <div class="modal-dialog">
							      <div class="modal-content">
							        <div class="modal-header">
							          <button type="button" class="close" data-dismiss="modal">&times;</button>
							          <h4 class="modal-title">${userWord} ${user.login} ${isBanned}</h4>
							        </div>
							        
							        <div class="modal-footer">
							        	<a href="<c:url value="/Controller?command=unban_user&userId=${user.id}"/>" class="btn btn-default" role="button">${unbanBtn}</a>
							          	<button type="button" class="btn btn-default" data-dismiss="modal">${closeBtn}</button>
							        </div>
							      </div>
							    </div>
							  </div>
							  
						  
							  <div class="modal fade" id="setDiscountModal${user.id}" role="dialog">
							    <div class="modal-dialog">
							      <div class="modal-content">
							      	<form  name="setDiscountForm" class="form-horizontal" method="post" action="Controller">
							        <div class="modal-header">
							          <button type="button" class="close" data-dismiss="modal">&times;</button>
							          <h4 class="modal-title">${setDiscountFor} ${user.login}</h4>
							        </div>
							        <div class="modal-body">
							        	
							          		<div class="form-group">
										    	<input type="hidden" name="command" value="add_discount"/>
										    	<input type="hidden" name="userId" value="${user.id}"/>
										  	</div>
										  	
							          		<div class="form-group">
									      		<label class="col-sm-3 control-label">${amount}</label>
									      		<div class="col-sm-9">
									        		<input class="form-control" name="amount" type="text" placeholder="${enterAmount}">
									      		</div>
											</div>
											<div class="form-group">
									      		<label class="col-sm-3 control-label">${endDate}</label>
									      		<div class="col-sm-9">
									        		<input class="form-control" name="endDate" type="text" placeholder="${dateFormat}">
									      		</div>
											</div>
											<div class="form-group">
									      		<label class="col-sm-3 control-label">${endTime}</label>
									      		<div class="col-sm-9">
									        		<input class="form-control" name="endTime" type="text" placeholder="${timeFormat}">
									      		</div>
											</div>
											
							        	
							        </div>
							        <div class="modal-footer">
							          <button type="submit" class="btn btn-danger">${setDiscount}</button>
							          <button type="button" class="btn btn-default" data-dismiss="modal">${closeBtn}</button>
							        </div>
							        </form>
							      </div>
							      
							    </div>
							  </div>
							
							  <c:if test="${requestScope.discountList[status.index] != null}">
							  <div class="modal fade" id="editDiscountModal${user.id}" role="dialog">
							  	<c:set var="discount" value="${requestScope.discountList[status.index]}" />
							    <div class="modal-dialog">
							      <div class="modal-content">
							      	<form  name="editDiscountForm" class="form-horizontal" method="post" action="Controller">
							        <div class="modal-header">
							          <button type="button" class="close" data-dismiss="modal">&times;</button>
							          <h4 class="modal-title">${editDiscountFor} ${user.login}</h4>
							        </div>
							        <div class="modal-body">
							        		
							          		<div class="form-group">
										    	<input type="hidden" name="command" value="edit_discount">
										    	<input type="hidden" name="discountId" value="${discount.id}">
										    	<input type="hidden" name="userId" value="${user.id}">
										  	</div>
										  	
							          		<div class="form-group">
									      		<label class="col-sm-3 control-label">${amount}</label>
									      		<div class="col-sm-9">
									        		<input class="form-control" name="amount" type="text" value="${requestScope.discountList[status.index].amount}">
									      		</div>
											</div>
											<div class="form-group">
									      		<label class="col-sm-3 control-label">${endDate}</label>
									      		<div class="col-sm-9">
									        		<input class="form-control" name="endDate" type="text" value="${requestScope.discountList[status.index].enDate}">
									      		</div>
											</div>
											<div class="form-group">
									      		<label class="col-sm-3 control-label">${endTime}</label>
									      		<div class="col-sm-9">
									        		<input class="form-control" name="endTime" type="text" value="${requestScope.discountList[status.index].enTime}">
									      		</div>
											</div>
											
							        	
							        </div>
							        <div class="modal-footer row">
							        	<div class="text-left col-md-4">
							          		<button type="submit" class="btn btn-success">${editDiscount}</button>
							          	</div>
							        	<div class="text-center col-md-4">
							          		<a href="<c:url value="/Controller?command=delete_discount&discountId=${discount.id}&userId=${user.id}"/>" class="btn btn-danger" role="button">${deleteDiscountBtn}</a>
							          	</div>
							          	<div class="text-right col-md-4">
							          		<button type="button" class="btn btn-default" data-dismiss="modal">${closeBtn}</button>
							          	</div>
							        </div>
							        </form>
							      </div>
							      
							    </div>
							  </div>
							</c:if>
                    </c:forEach>
          </div>
          </div>
          <c:if test="${requestScope.numOfPages > 1}">
          <div class="row panel-footer" >
          <h5 class="text-right">
          	<ul class="pagination">
			  <c:forEach begin="1" end="${requestScope.numOfPages}" step="1" var="pageNum">
			  	<c:choose> 
			  		<c:when test="${pageNum eq requestScope.curPage}">
			  			 <li class="active"><a href="<c:url value="/Controller?command=open_all_users&pageNum=${pageNum}" />" >${pageNum}</a></li>
			  		</c:when>
			  		<c:otherwise>
			  			<li><a href="<c:url value="/Controller?command=open_all_users&pageNum=${pageNum}" />" >${pageNum}</a></li>
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