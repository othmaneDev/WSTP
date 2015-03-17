<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-header centerfy">
	<h1>Results</h1>
</div>

<div class="row">
<c:forEach var="ClassifiedWS" items="${ WSClassment }">
	<c:set var="ws" value="${ClassifiedWS.WS}" scope="request" /> 
	<div class="col-md-5 col-md-offset-1 bordered-shadow wsFiche">
		<jsp:include page="WS.jsp" flush="true" />
		<b>Note:</b> <c:out value="${ ClassifiedWS.markToWatch }"/>
	</div>
</c:forEach>
</div>