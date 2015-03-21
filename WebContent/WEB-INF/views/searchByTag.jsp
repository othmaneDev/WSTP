<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-header centerfy">
	<h1>List of categories related to the tags input</h1>
</div>
<div class="row">
<c:forEach var="multiMapEntry" items="${multiMap}">
<div class="col-md-4  bordered-shadow  wsFiche">
    <h4><span class="label label-default"><c:out value="${multiMapEntry.key}"/></span></h4>
    <c:set var="i" value="1" />
    <c:forEach var="currentValue" items="${multiMapEntry.value}">
    <c:if test="${i<3}">
    <span><a class="cat" href="WS?wsId=${currentValue.WS.idWebService}">${currentValue.WS.name}</a></span>&nbsp;&nbsp;
    <c:set var="i" value="${i+1 }" />
    </c:if>
    </c:forEach>
    <c:if test="${2<i}">
    <span  class="moreBtn"  data-toggle="modal" data-target="#ListOfWebServices${multiMapEntry.key}">MORE</span>
    </c:if>
    <div class="modal fade" id="ListOfWebServices${multiMapEntry.key}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">Liste of All Webservices related to the category</h4>
		      </div>
		      <div class="modal-body">
		       <c:forEach var="currentValue" items="${multiMapEntry.value}">
    <span><a class="cat" href="WS?wsId=${currentValue.WS.idWebService}">${currentValue.WS.name}</a></span>&nbsp;&nbsp;
   
    </c:forEach>
		       </div>
		      </div>
		      </div>
		      </div>
    </div>
</c:forEach>
</div>
