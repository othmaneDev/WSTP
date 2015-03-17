<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-header centerfy">
    <h1>Category: ${ cat.name }</h1>
</div>

<h2>List of web services:</h2><br/>
<div class="row">
<c:forEach var="webService" items="${cat.webServices}">
	<c:set var="ws" value="${webService}" scope="request" /> 
	<div class="col-md-5 col-md-offset-1 bordered-shadow wsFiche">
		<jsp:include page="WS.jsp" flush="true" />
	</div>
</c:forEach>
</div>