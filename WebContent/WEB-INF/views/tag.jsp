<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-header centerfy">
    <h1>Tag: ${ tag.name } <button class="btn btn-default" data-toggle="modal" data-target="#editTag">Edit <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></button></h1>
</div>

<div class="row centerfy">
	<div class="col-md-12"><b>Description:</b></div> 
	<div class="col-md-12">${ tag.description }</div>
</div>
<hr>
<h2>List of web services:</h2><br/>

<div class="row">
<c:forEach var="wsta" items="${tag.wstagAssociations}">
	<c:set var="ws" value="${wsta.webService}" scope="request" /> 
	<div class="col-md-5 col-md-offset-1 bordered-shadow wsFiche">
		<jsp:include page="WS.jsp" flush="true" />
	</div>
</c:forEach>
</div>
<jsp:include page="editTag.jsp" flush="true" />