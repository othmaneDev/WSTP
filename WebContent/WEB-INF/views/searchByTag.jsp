<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-header centerfy">
	<h1>List of categories related to the tags input</h1>
</div>

<div class="row">

<c:forEach var="key" items="${ keys }">

 <div class="panel-body">
  
  	<div class="row">
    		<h4><div class="col-md-3"><span class="label label-default">Category name:</span></div><div class="col-md-7"><c:out value="${key}"/></div></h4>
    		<h4><a class="btn btn-default btn-lg col-md-12" href="WSListByCategory?key=${key}">Link to the webserices related to the category</a></h4>

	</div>

</div>


</c:forEach>

</div>
<!-- 
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
</div>-->
