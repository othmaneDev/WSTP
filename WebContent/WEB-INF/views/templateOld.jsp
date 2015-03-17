<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="stage.wstp.model.entities.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr"><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Web Service Tagging Platform</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

    <!-- Custom styles for this template -->
    <!-- <link href="style.css" rel="stylesheet">-->
	<style type="text/css">
	
		.btn-file {
	    position: relative;
	    overflow: hidden;
		}
		
		.btn-file input[type=file] {
		    position: absolute;
		    top: 0;
		    right: 0;
		    min-width: 100%;
		    min-height: 100%;
		    font-size: 100px;
		    text-align: right;
		    filter: alpha(opacity=0);
		    opacity: 0;
		    outline: none;
		    background: white;
		    cursor: inherit;
		    display: block;
		}
		
	</style>


  </head>

  <body>

    <!-- Static navbar -->
    <div class="navbar navbar-default navbar-static-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">WS Tagging Platform</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="Index">Home</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Menu <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="AddWSInput">Add a new WebService</a></li>
				<li><a href="WSList">View WebService list</a></li>
				<li><a href="SearchByTagInput">Search WebServices</a></li>
              </ul>
            </li>
          </ul>
          
          	 <c:if test="${empty sessionScope.utilisateur }">
		       <form role="form" action="FindOrCreateUser" method="GET" enctype="multipart/form-data">
				  <div class="form-group col-md-2 col-lg-2">
				    <input type="text" class="form-control" id="utName" name="utName" placeholder="Pseudo">
				  </div>
				    <button type="submit" class="btn btn-default">Connection</button>
		    	</form>
		    </c:if>
          
           
           <c:if test="${!empty sessionScope.utilisateur }">
		       <span>${ sessionScope.utilisateur.name }</span>
		       <form role="form" action="Deconnect" method="GET" enctype="multipart/form-data">
				  <button type="submit" class="btn btn-default">deconnection</button>
		    	</form>
		    </c:if>
          
          
        </div><!--/.nav-collapse -->
        
      </div>
      
    </div>


    <div class="container">
    <c:set var="TagList" value="${nuageTags}" scope="request" />
	<jsp:include page="${content}"></jsp:include>
    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	
	<script src="js/jquery.tagcanvas.js" type="text/javascript"></script>
  	<script src="js/CheckTags.js" type="text/javascript"></script>
	<script src="js/InitListener.js" type="text/javascript"></script>
	<script src="js/EasySuggest.js" type="text/javascript"></script>
   	
   	<script type="text/javascript">
   
   $(document).on('change', '.btn-file :file', function() {
	    var input = $(this),
	        numFiles = input.get(0).files ? input.get(0).files.length : 1,
	        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
	    input.trigger('fileselect', [numFiles, label]);
	});
	
	$(document).ready( function() {
	    $('.btn-file :file').on('fileselect', function(event, numFiles, label) {
	        $('#hrest_name').html(label);
	    });
	});
   </script>

</body></html>
