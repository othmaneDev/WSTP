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

    <title>Web Service Tagging Platform - WebServices Statistic</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<style type="text/css">
	
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
      </div><!-- div container -->
    </nav><!-- div nav -->


    <div class="container">
    	<div class="row">
		    <div class="page-header centerfy">
		    	<h1>WebService's Statistic</h1>
			</div>
			<div id="chartContainer" style="height: ${hauteurStats}; width: 100%;"></div>
		</div>
	</div> <!-- /container -->
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
   	<script type="text/javascript" src="js/jquery.canvasjs.min.js"></script>
   	 
   	<script type="text/javascript">
		window.onload = function () {
			var chart = new CanvasJS.Chart("chartContainer", {
				theme: "theme2",//theme1
				title:{
					text: "${ titleStats}"              
				},
				axisX:{ 
					title: "Category",
					titleFontColor : "steelBlue",
				},
				 
				axisY:{ 
					title: "Number of WebServices",
					titleFontColor : "steelBlue",
				},
				animationEnabled: true,   // change to true
				data: [              
				{
					type: "column",
					color: "steelBlue",
					dataPoints:[
					            <c:forEach var="popularWebService" items="${ popularWebServiceList }">
					            	{ label:"${ popularWebService.getNomCategory() }", y:${popularWebService.getNombreWebServices()}},
					            </c:forEach>
					           ]
				}
				]
			});
			chart.render();
		}
	</script>
</body>
</html>


