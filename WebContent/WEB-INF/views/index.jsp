<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-header centerfy">
    	<h1>Web Service Tagging Platform</h1>
</div>

<c:if test="${!empty error}">
	<div class ="row">
		<div class="alert alert-danger centerfy" role="alert"><b><c:out value="${ error }"/></b></div>
	</div>
</c:if>

<div class="row">

	<div class="col-md-5 jumbotron col-sm-6">
			<div class ="col-md-12">
				<h3>Latest Web Services in Activity :</h3>
			</div>
			
			<div class="aSpace col-md-12">
				<jsp:include page="/WEB-INF/views/historique.jsp"></jsp:include>
			</div>
	</div>
	
	<div class="col-md-5 col-md-offset-2 col-sm-6 jumbotron">
		<jsp:include page="/WEB-INF/views/cloudTags.jsp"></jsp:include>
	</div>
</div>

<footer>
    <div class="navbar-inverse navbar-fixed-bottom">
        <div class="container">
            <div class="navbar-collapse collapse" id="footer-body">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="StatisticTag">Tag's Statistic</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-left">
                    <li><a href="StatisticWebService">WebService's Statistic</a></li>
                </ul>
            </div>
          	<div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#footer-body">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>

        </div>
    </div>
</footer>	