<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-header centerfy">
    	<h1>Search a web service By Tag </h1>
</div>

<div class="row">

	<div class="col-md-6 jumbotron col-sm-6 heightIndex">
			<div class ="col-md-12">
				<h3>Search By Tag :</h3>
			</div>
			<form id="formCheck" role="form" action="SearchByTag" method="GET">
			  <div class="form-group">
			    <label for="tags">Tags</label>
			    <input type="text" class="form-control" id="tags" placeholder="Tags" name="tags" onkeyup="EasySuggest.suggest(taglist,'tags','suggestions')">
			  	<u>Suggestions</u><strong><ul id="suggestions" class="list-inline"></ul></strong>
			  </div>
			  
			  <button type="submit" class="btn btn-default">Send <span class="glyphicon glyphicon-send" aria-hidden="true"></span></button>
			</form>
	</div>
	
	<div class="col-md-5 col-md-offset-1 col-sm-6 jumbotron heightIndex">
		<jsp:include page="/WEB-INF/views/cloudTags.jsp"></jsp:include>
	</div>
</div>
<div class="modal fade" id="errorSyntax" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header alert alert-danger">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Error Tags Syntaxe</h4>
      </div>
      <div class="modal-body">
        Syntax error! please check tags input and verify you 're write correctly with this syntax : Tag_Name:WeightTags (video:100) with space beetwen them and WeightTags is between 0 and 100.
        thank you!
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