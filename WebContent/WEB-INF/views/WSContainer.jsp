<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-header centerfy">
    <h1>${ws.name}</h1>
</div>
<div class="row">
		<c:set var="ws" value="${ws}" scope="request" /> 
		<div class="bordered-shadow col-md-10 wsFiche">
			<jsp:include page="WS.jsp" flush="true" />
		</div> 
</div>