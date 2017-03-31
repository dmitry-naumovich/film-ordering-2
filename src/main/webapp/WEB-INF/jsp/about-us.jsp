<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'en' }" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.aboutUs.pageTitle" var="pageTitle" />
<fmt:message bundle="${loc}" key="local.aboutUs.header" var="helpHeader" />

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
          <h2 class=" text-left"> ${helpHeader}</h2>
          </div> 
          <div class="row panel-body">
            <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
              <p>  Id probo mutat est. Qui ut prompta philosophia. Sea eu tritani sapientem suscipiantur, ad recteque ocurreret reformidans nam. Putant diceret his ne, nam quidam option id, primis numquam no cum.

                  Ne volumus qualisque eloquentiam sit, eius patrioque at usu. Sit odio clita inciderint ea, per ignota menandri inciderint et. No eum nisl mutat corpora. Vel assum percipitur ne, modus dolorem signiferumque per ne.

                  Quo reformidans definitionem no, cu sale percipit maluisset mea. Accusam gloriatur eu sea. Omittam honestatis his ei. Ad melius aliquid sit. Sumo graeci erroribus et mea, fierent liberavisse mediocritatem no mei, illud facilis epicurei cum id. Ei adipisci eleifend similique mel, eos no putent deterruisset.

                  Eos nemore corrumpit aliquando ea, congue discere ut sed. Quo ei stet admodum dissentias, nec ex omnesque argumentum, et sea novum exerci. Eu nemore commune imperdiet pro. Id per aliquip fabellas, error fastidii has ex.

                  Quo malis omittam cu, est vocent scaevola mnesarchum ei. Malis nonumy temporibus at pro, te essent molestie assueverit vel. Maiorum consetetur vel ex, mea mucius viderer te. Ad brute principes scriptorem mei, vis ne ridens neglegentur. Ut iuvaret scriptorem cum, id eos agam ocurreret. Per in quem dicat diceret, natum melius consectetuer ne eam. Cu nonumes adipisci neglegentur vel.

                  Nam mucius aperiam euismod te. Quas falli labores et vis, at vel dolor facete, ea vim percipit honestatis definitionem. Amet sale nemore duo in. Te vivendum urbanitas sit, sint veritus mnesarchum no pro, diam viderer elaboraret pri eu.

                  Graecis pertinacia sit an. Cu eirmod propriae voluptatibus quo. Et mei possit sapientem gloriatur, ne euismod offendit electram eam. Meis impetus accusamus ea vix, mea an quando inermis. Ex his dicant insolens, has aeque blandit id. Clita signiferumque his ut, sea an clita luptatum argumentum, essent utroque vituperata eam eu.

                  Ex eros assueverit vim, mutat inani sit et. Sea quis cetero mnesarchum ut, viris suavitate cu vel. Ne mea idque voluptua interesset. No cum sint posse sententiae. Sed legere prompta luptatum in.
                  </p>
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