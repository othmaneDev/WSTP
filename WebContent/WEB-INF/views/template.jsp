<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="stage.wstp.model.entities.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en"><head>
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
	    span.moreBtn{
	    color:#030404;
        horizontal-align:right;
        font-weight:bold;
	    }
	    span.moreBtn:hover{
	    text-decoration:underline;
	    }
	    a.cat
        { 
         color:#030404;
         text-decoration:none; 
        
         } 
        a.cat:hover 
        { color: blue ; 
         }
		html,body {
			margin:0;
			padding:0;
			height:100%;
		}

		#footer {
			width:100%;
			height:80px;
			position:absolute;
			bottom:0;
		}
		
		body{
			padding-top: 20px;
			
		}
		
		.downHeader{
			padding-top: 40px;
		}
		
		canvas{
			border-radius:10px;
		    -moz-box-shadow: 0 0 5px black;
		    -webkit-box-shadow: 0 0 5px black;
		    box-shadow: 0 0 5px black;
		}
		
		.jumbotron{
		    -moz-box-shadow: 0 0 5px black;
		    -webkit-box-shadow: 0 0 5px black;
		    box-shadow: 0 0 5px black;
		}
		.heightIndex{
			height:352px;
		}
		.reduceHeight{
			height:80px;
		}
		
		.centerfy{
        	text-align: center;
        }
        
        .aSpace a{
        	margin-bottom:10px;
        	margin-right:10px;
        }
            
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
		.bordered-shadow{
		    -moz-box-shadow: 0px 4px 12px 0px rgba(0, 0, 0, 0.5);
		    -webkit-box-shadow: 0px 4px 12px 0px rgba(0, 0, 0, 0.5);
		    -o-box-shadow: 0px 4px 12px 0px rgba(0, 0, 0, 0.5);
		    box-shadow: 0px 4px 12px 0px rgba(0, 0, 0, 0.5);
		}
		
		.wsTag{
			margin-bottom:5px;
			margin-right:5px;
		}
		
		.wsFiche{
			margin-bottom:10px;
		}
		
		
		.vertical-al{
			vertical-align:middle;
		}
	</style>


  </head>

  <body>
    <!-- Static navbar -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="Index"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="AddWSInput"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>  Add a new WebService</a></li>
			<li><a href="WSList"><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>  View WebService list</a></li>
			<li><a href="SearchByTagInput"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>  Search WebServices</a></li>
            <li>
             <c:if test="${empty sessionScope.user }">
		       	<form class="navbar-form" role="search" action="FindUser" method="GET">
				  <div class="form-group">
				    <input type="text" class="form-control" name="userName" placeholder="Pseudo">
				  </div>
				    <button type="submit" class="btn btn-default">Log in  <span class="glyphicon glyphicon-log-in"></span></button>
		    	</form>
				
		     </c:if>
		     <c:if test="${!empty sessionScope.user }">
		     	<form  class="navbar-form navbar-right" role="form" action="Disconnect" method="GET">
				  	<button type="submit" class="btn btn-default"><b>${ sessionScope.user.name }     </b> Log out <span class="glyphicon glyphicon-log-out"></button>
		    	</form>
		    </c:if>
		    </li>
		    <c:if test="${empty sessionScope.user }">
		    <li>
		    	<form class="navbar-form">
		    		<button type="button" class="btn btn-default" data-toggle="modal" data-target="#inscription">Sign in  <span class="glyphicon glyphicon-pencil"></span></button>
		   		</form>
		    </li>
		    </c:if>
          </ul> 
        </div><!--/.nav-collapse -->
      </div><!-- div container -->
    </nav><!-- div nav -->


    <div class="container">
    	<c:if test="${!empty errorMSTemplate}">
			<div class ="row downHeader">
				<div class="alert alert-danger centerfy" role="alert"><b><c:out value="${ errorMSTemplate }"/></b></div>
			</div>
		</c:if>
    	<div class="row">
		<jsp:include page="${content}"></jsp:include>
		
    	</div>
    	<!-- Formulaire de d'enregistrement -->
    	<div class="modal fade" id="inscription" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">Sign in</h4>
		      </div>
		      <div class="modal-body">
		        <form role="search" action="SignIn" method="GET">
				  <div class="form-group">
	    			<label for="userNameInsc">Username : </label><input type="text" class="form-control" id="userNameInsc" name="userNameInsc" placeholder="Pseudo"><br/>
				    For add or tag a web service you need to answer once to some questions.<br/>
	   				<b>Answer to the following questions :</b> <br/><br/>
				    <ul>
						<li> Q1: What is a Web Service?<br/>
							<label>a World Wide Web <input type="radio" name="q1" value="0"></label><br/>
							<label>a function that can be accessed by other programs over the web  <input type="radio" name="q1" value="1"></label><br/>
						    <label>a Web page <input type="radio" name="q1" value="0"></label><br/>
						</li><br/><br/>
						<li> Q2: What is an UDDI?<br/>
							<label> Universal description, discovery and integration <input type="radio" name="q2" value="1"></label><br/>
							<label> Usage design, discovery and installation		 <input type="radio" name="q2" value="0"></label><br/>
						</li><br/><br/>
						<li> Q3: What is SOAP?<br/>
							<label>Simple Object Access Protocol    <input type="radio" name="q3" value="1"></label><br/>
							<label>Simple Operable Access Protocol  <input type="radio" name="q3" value="0"></label><br/>
						</li><br/><br/>
					</ul>
				  </div>
				    <button type="submit" class="btn btn-default">Register  <span class="glyphicon glyphicon-log-in"></span></button>
		    	</form>
		      </div>
		      <!-- <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		      </div>-->
		    </div>
		  </div>
		</div><!-- Fin de formulaire -->
		
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
   	 <script type="text/javascript" src="js/jquery.canvasjs.min.js"></script>
   	 
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
