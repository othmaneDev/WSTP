<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-header centerfy">
    <h1>Tag a WebService</h1>
</div>

<c:if test="${!empty success}">
	<div class=" row alert alert-success" role="alert">The webService has been successfully tagged!</div>
</c:if>

<c:if test="${!empty error}">
	<div class=" row alert alert-danger" role="alert">The webService has'nt been tagged! check your synthax!</div>
</c:if>

<c:if test="${empty sessionScope.user}">
	<c:set var="errorMSTemplate" value="You need to log in to tag a web service!" scope="request" />
	<jsp:forward page="${sessionScope.chemin}"></jsp:forward>
</c:if>


<div class="row">
	
	<div class="col-md-6">
		<jsp:include page="WS.jsp" flush="true" />
	</div>
	<div class="col-md-5 col-md-offset-1">
		<form id="formCheck" role="form" action="TagWS" method="GET">
		  	<input type="hidden" name="wsId" value="${ws.idWebService}">
		  <div class="form-group">
		    <label for="tags">Tags</label>
		    <input type="text" class="form-control" id="tags" placeholder="Tags" name="tags" onkeyup="EasySuggest.suggest(taglist,'tags','suggestions')">
		  	<u>Suggestions</u><strong><ul id="suggestions" class="list-inline"></ul></strong>
		  </div>
		
			<div class="row">
				<div class="col-md-6">
		  			<button type="submit" class="btn btn-default">Send <span class="glyphicon glyphicon-send" aria-hidden="true"></span></button>
		  		</div>
		  		<div class="col-md-5 col-md-offset-1">
		  			<button type="button" class="btn btn-default" data-toggle="modal" data-target="#graph">See graph <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></button>
				</div>
			</div>
		</form>
	</div>
	
	<div class="col-md-5 col-md-offset-1">
		<jsp:include page="cloudTags.jsp"></jsp:include>
	</div>
</div>

<div class="modal fade" id="errorSyntax" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Error Tags Syntaxe</h4>
      </div>
      <div class="modal-body">
        Syntax error! please check tags input and verify you 're write correctly with this syntax : Tag_Name:WeightTags with space beetwen them.
        thank you!
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="graph" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Graph of web service with at least one tag in common</h4>
      </div>
      <div class="modal-body">
        <jsp:include page="graph.jsp"></jsp:include>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<script>
	taglist = ['?'<c:forEach var="tag" items="${ tagList }">,'<c:out value="${ tag.name }"/>'</c:forEach>];
</script>