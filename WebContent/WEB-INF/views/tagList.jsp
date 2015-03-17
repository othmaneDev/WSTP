<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-header centerfy">
    <h1>List of Tag</h1>
</div>
<div class="row">
	<c:forEach var="tag" items="${ tagList }"> 
		<a class="btn btn-primary col-md-2 col-md-offset-1 jumbotron" href="Tag?tagId=<c:out value="${ tag.idTag }"/>"><c:out value="${ tag.name }"/></a>	
	</c:forEach>
</div>