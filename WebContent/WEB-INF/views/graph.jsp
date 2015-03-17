<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="js/kinetic-v5.1.0.min.js"></script>
<script src="js/EasyGraph.js"></script>

   	<div id="kinetic" style="height: 500px;  width: 800px;"></div>

	<!-- <button onclick="monGraph.gridArrange();">GridArrange</button>
	<button onclick="monGraph.playWithPhysics(60);">Play with Physics</button>
	<button onclick="monGraph.stopPhysics();">Stop Physics</button>
-->

	<script>
		monGraph = new EasyGraph.Graph("kinetic","Graphe1");
		<c:forEach var="ws" items="${ wsListGraph }">
		new EasyGraph.Vertex({name:"${ws.name}", id:'ws${ws.idWebService}'});
		</c:forEach>
		
		
		<c:forEach var="tag" items="${ tagListGraph }">
		
		new EasyGraph.Vertex({name:"${tag.name}", id:'tag${tag.idTag}',bg_color:'#AAF0D1',border_color:'#AAF0D1',radius:20});
		//new EasyGraph.Edge({idvertex1:'tag${tag.idTag}',idvertex2:'tag0' ,spring_l:200});
		
		</c:forEach>
	
		
		
		<c:forEach var="wsta" items="${ wstaListGraph }">
		
		new EasyGraph.Edge({idvertex1:'tag${wsta.tag.idTag}',idvertex2:'ws${wsta.webService.idWebService}' ,spring_l:50});
		</c:forEach>
		
		monGraph.gridArrange();
	</script>
