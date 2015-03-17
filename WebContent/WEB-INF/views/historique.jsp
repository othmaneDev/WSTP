<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row">
	<c:forEach var="ws" items="${ wsList}"> 
		<a class="btn btn-default btn-lg" href="WS?wsId=<c:out value="${ ws.idWebService}"/>"><c:out value="${ ws.name }"/></a>
	</c:forEach>
</div>
