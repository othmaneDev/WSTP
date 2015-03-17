<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-header centerfy">
    <h1>List of web services</h1>
</div>
<div class="row">
	<c:forEach var="webService" items="${ wsList }">
		<c:set var="ws" value="${webService}" scope="request" /> 
		<div class="bordered-shadow col-md-5 col-md-offset-1 wsFiche">
			<jsp:include page="WS.jsp" flush="true" />
		</div> 
	</c:forEach>
</div>