<%@page import="stage.wstp.others.PopularTag"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div>
	<h3 class="col-md-10"><c:out value="${ title_cloud }"/> <span class="glyphicon glyphicon-info-sign" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="you can interact with the tag cloud"></span> : </h3>
</div>
<div id="myCanvasContainer">
	<canvas width="<c:out value="${ width_canvas_cloud }"/>px" height="<c:out value="${ height_canvas_cloud }"/>px" id="myCanvas">
  		<p>Anything in here will be replaced on browsers that support the canvas element</p>
 	</canvas>
 </div>
 <div id="tagsList" style="display:none;">
  <ul>
    <li>
    	<c:set var="sumTagPopular" value="0" />
    	<c:forEach var="popularTag" items="${ TagList }">
    		<c:set var="sumTagPopular" value="${sumTagPopular + popularTag.getNombreOccur() }" />
    	</c:forEach>
    	
    	<c:forEach var="populartag" items="${ TagList }">
			<a style="font-size: <c:out value="${ 300 * populartag.getNombreOccur()/sumTagPopular }"/>pt" href="Tag?tagId=<c:out value="${ populartag.getTag().idTag }"/>"><c:out value="${ populartag.getTag().name }"/></a>
		</c:forEach>
	</li>
  </ul>
</div>