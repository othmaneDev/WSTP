<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-header centerfy">
    <h1>Add a new WS to the server</h1>
</div>

<c:if test="${empty sessionScope.user}">
	<!--<c:set var="content" value="/WEB-INF/views/index.jsp" scope="request" />-->
	<c:set var="errorMSTemplate" value="You need to log in to add a web service" scope="request" />
	<jsp:forward page="${sessionScope.chemin }"></jsp:forward>
</c:if>

<c:if test="${!empty success}">
<div class="alert alert-success" role="alert">The webService has been successfully created!</div>
</c:if>

<c:if test="${!empty error}">
<div class="alert alert-danger" role="alert">${error}</div>
</c:if>
<div class="row">

<form role="form" action="AddWS" method="POST" enctype="multipart/form-data">
	<div class="jumbotron col-md-6">
	
	  <div class="form-group">
	    <label for="wsName">WS Name *</label>
	    <input type="text" class="form-control" id="wsName" name="wsName" placeholder="Enter WS name" required>
	  </div>
	  <div class="form-group">
	    <label for="wsDescription">WS Description</label>
	    <input type="text" class="form-control" id="wsDescription" placeholder="WS description" name="wsDescription">
	  </div>
	  
	  <div class="form-group">
	    <label for="wsURL">WS URL *</label>
	    <input type="text" class="form-control" id="wsURL" placeholder="WS URL" name="wsURL" required>
	  </div>
	  
	  <div class="form-group">
	    <label for="catName">Category *</label>
	    <input required type="text" class="form-control" id="catName" placeholder="Category name" name="catName" onkeyup="EasySuggest.suggest(catlist,'catName','suggestions')">
	    <u>Suggestions</u><strong><ul id="suggestions" class="list-inline"></ul></strong>
	  </div>
	  <div class="form-group">
		    <label for="hrest">Service's description</label>
			    <span class="btn btn-default btn-file">
			    	<span class="glyphicon glyphicon-import" aria-hidden="true"></span> Browse...<input type="file" id="hrest" name="hrest">
				</span>
				<label id="hrest_name"></label>
		   <!--  <p class="help-block">Upload Hrest file.</p>-->
		  </div>
		<span><i>* = Required field</i></span>
   </div>
  
  <div class="row col-md-5 col-md-offset-1">
  		<div class="row jumbotron">
		  
		  
		  <div class="form-group">
		    <label for="tags">Tags</label>
		    <input type="text" class="form-control" id="tags" placeholder="Tags" name="tags" onkeyup="EasySuggest.suggest(taglist,'tags','suggestions2')">
		  	<u>Suggestions</u><strong><ul id="suggestions2" class="list-inline"></ul></strong>
		  </div>
		  <button type="submit" class="btn btn-default">Send <span class="glyphicon glyphicon-send" aria-hidden="true"></span></button>
  		</div>
  		<div class="row jumbotron reduceHeight">
  			<button type="button" class="btn btn-default" data-toggle="modal" data-target="#cloudTag">See popular tags <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></button>
  		</div>
  </div>
</form>
</div>
	<div class="modal fade" id="cloudTag" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Cloud of Tag</h4>
	      </div>
	      <div class="modal-body">
	        <jsp:include page="/WEB-INF/views/cloudTags.jsp"></jsp:include>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- Fenetre modale 
	<div class=row>
	<div class="col-md-6"></div>
	
	</div>
	<!--<jsp:include page="/WEB-INF/views/cloudTags.jsp"></jsp:include>  -->
<script>
	taglist = ['?'<c:forEach var="tag" items="${ tagList }">,'<c:out value="${ tag.name }"/>'</c:forEach>];
	catlist = ['?'<c:forEach var="cat" items="${ catList }">,'<c:out value="${ cat.name }"/>'</c:forEach>];
</script>