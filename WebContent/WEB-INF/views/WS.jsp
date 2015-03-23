<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


 <h2><a href="WS?wsId=<c:out value="${ ws.idWebService }"/>"><c:out value="${ ws.name }"/></a></h2>
  <hr/>
  	<div class="row">
    		<div class="col-md-12"><h4><b>Category:</b> <a class="btn btn-primary" href="Category?catId=${ ws.category.idCategory }"><c:out value="${ ws.category.name }"/> <span class="glyphicon glyphicon-hand-up" aria-hidden="true"></span></a></h4></div>
	</div>
	
	<div class="row">
		<div class="col-md-12"><h4><b>Description: </b></h4> <p class="text-justify"><c:out value="${ ws.description }"/></p></div>
		<c:if test="${! empty ws.fileName}"> 
		<div class="col-md-12"><h4><a target="_blank" href="Ressources/<c:out value="${ ws.fileName }"/>">Web service's description</a></h4></div>
		</c:if>
	</div>
	
	<div class="row">
		<div class="col-md-12"><h4><b>URL:</b> <a href="http://<c:out value="${ ws.url }"/>"><c:out value="${ ws.url }"/></a></h4></div>
	</div>
	
	<div class="row">
		<div class="col-md-12"><h4><b>Popularity:</b> <c:out value="${ ws.popularity }"/></h4></div>
	</div>
	<div class="row col-md-12">
		
		<c:if test="${ ws.isTagExist() == true}">
			<h4><b>Tag list:</b></h4>
			
			<c:forEach var="wstaOT" items="${ ws.getWSTASOnlyTag() }"> 
				<a class="btn btn-primary wsTag" href="Tag?tagId=${ wstaOT.tag.idTag }"><c:out value="${ wstaOT.tag.name }"/>:<c:out value="${ wstaOT.weight }"/></a>
			</c:forEach>
		</c:if>
		
		<c:if test="${ ws.isMetaTagExist() == true}">
			<h4><b>Tag with meta list:</b></h4>
			
			<c:forEach var="wstaOMT" items="${ ws.getWSTASOnlyMetaTag() }"> 
				<a class="btn btn-primary wsTag" href="Tag?tagId=${ wstaOMT.tag.idTag }"><c:out value="${ wstaOMT.tag.name }"/>:<c:out value="${ wstaOMT.weight }"/></a>
			</c:forEach>
		</c:if>
		
		
		<div class="row col-md-12">
			<div class="wsTag"><a  class="btn btn-default" href="TagWSInput?wsId=<c:out value="${ ws.idWebService}"/>">Edit WSTag <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a></div>
	  	</div>
  	</div>

