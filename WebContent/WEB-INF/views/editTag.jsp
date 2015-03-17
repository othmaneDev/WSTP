<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="editTag" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	      <h4 class="modal-title" id="myModalLabel">Tag: ${ tag.name }</h4>
	    </div>
	    <div class="modal-body">
		    <b>Description:</b> 
		    
		    <form role="form" action="EditTag" method="GET">
				<input type="hidden" name="tagId" value="${tag.idTag}">
				<textarea class="form-control" rows="3" name="tagDescription"> ${ tag.description } </textarea>
				<button type="submit" class="btn btn-default">Save <span class="glyphicon glyphicon-save" aria-hidden="true"></span></button>
			</form>
	    </div>
	  </div>
  	</div>
</div>