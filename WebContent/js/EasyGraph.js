var EasyGraph ={
	nb_vertices : 0,
	nb_edges : 0
};

EasyGraph.Vertex = function (params){

	EasyGraph.nb_vertices +=1;

	this.ax = 0; this.ay = 0;
	this.vx = 0; this.vy = 0;

	if(params == undefined)
		params = new Object();	

	this.x = (params.x != undefined) ? params.x : 0;
	this.y = (params.y != undefined) ? params.y : 0;
	this.name = (params.name != undefined) ? params.name : "Vertex"+EasyGraph.nb_vertices;
	this.radius = (params.radius != undefined) ? params.radius : 30;
	this.x = (params.x != undefined) ? params.fixed : false;
	this.bg_color = (params.bg_color != undefined) ? params.bg_color : "white";
	this.border_color = (params.border_color != undefined) ? params.border_color : "black";
	
	this.graph = (params.graph != undefined) ? params.graph : EasyGraph.current_graph;

	this.id = (params.id != undefined) ? params.id : null;	

	if(params.id == null)	
		params.id = this.graph.addVertex(this);
	else
		this.graph.addVertex(this,params.id);

	this.edgeList = new Array();

	this.circle = new Kinetic.Circle({
		draggable:true,
		x: this.x,
		y: this.y,
		radius: this.radius,
		stroke: 'black',
		fill: this.bg_color,
		strokeWidth: 1,
		stroke: this.border_color
	});

	this.label = new Kinetic.Text({
		x: this.x,
		y: this.y,
		text: this.name,
		width: 160,
		align: 'center',
		fontSize: 15,
		fontFamily: 'Calibri',
		fill: 'black'
	});

	this.label.offsetX(this.label.width()/2);
	this.label.offsetY(this.label.height()/2);

	this.graph.layer.add(this.circle);
	this.graph.layer.add(this.label);

	this.circle.on('dragmove',function(){
		this.moveTo(this.circle.position().x,this.circle.position().y);
	}.bind(this));

	this.circle.on('dragstart',function(){
		this.fixed = true;
	}.bind(this));

	this.circle.on('dragend',function(){
		this.fixed = false;
	}.bind(this));
	
	this.circle.on('click',function(){
		if(this.id.indexOf("ws") != -1){
			window.location.href = "/WSTP/TagWSInput?wsId="+this.id.substring(2,this.id.length);
		}else if(this.id.indexOf("tag") != -1){
			window.location.href = "/WSTP/Tag?tagId="+this.id.substring(3,this.id.length);
		}
		
	}.bind(this));

	this.label.on('click',function(){
		if(this.id.indexOf("ws") != -1){
			window.location.href = "/WSTP/TagWSInput?wsId="+this.id.substring(2,this.id.length);
		}else if(this.id.indexOf("tag") != -1){
			window.location.href = "/WSTP/Tag?tagId="+this.id.substring(3,this.id.length);
		}
	}.bind(this));

	
	this.moveTo = function(x, y){
		this.x = x;
		this.y = y;
		this.circle.setPosition({x:x,y:y});
		this.label.setPosition({x:x,y:y});
		for(k in this.edgeList){
			edge = this.edgeList[k];
			edge.updateLine();
		}
	}
};



EasyGraph.Edge = function(params){

	EasyGraph.nb_edges +=1;

	this.graph = (params.graph != undefined) ? params.graph : EasyGraph.current_graph;
	this.spring_l = (params.spring_l != undefined) ? params.spring_l : 50;

	if(params.idvertex1 != undefined){
		this.vertex1 = this.graph.vertexList[params.idvertex1];
		this.vertex2 = this.graph.vertexList[params.idvertex2];
	}
	else{
		this.vertex1 = params.vertex1;
		this.vertex2 = params.vertex2;
	}

	this.id = (params.id != undefined) ? params.id : null;	

	if(params.id == null)	
		params.id = this.graph.addEdge(this);
	else
		this.graph.addEdge(this,params.id);
	
	this.vertex1.edgeList.push(this);
	this.vertex2.edgeList.push(this);

	this.line = new Kinetic.Line({
		x: 0,
		y: 0,
		points: [this.vertex1.x, this.vertex1.y, this.vertex2.x, this.vertex2.y],
		stroke: 'black',
		tension: 1,
		strokeWidth:1
	});

	this.updateLine = function(){
		this.line.setPoints( [this.vertex1.x, this.vertex1.y, this.vertex2.x, this.vertex2.y]);
	}

	this.graph.layer.add(this.line);
	this.line.moveToBottom();
};

EasyGraph.Graph = function(container,name){

	EasyGraph.current_graph = this;
	this.name = name;
	this.nvertices = 0;
	this.nedges = 0;
	self = this;
	this.vertexList = {};
	this.edgeList = {};
	


	this.addVertex = function(vertex,id){
		this.nvertices++;
		if(id == undefined)
			this.vertexList[this.nvertices] = vertex;
		else
			this.vertexList[id] = vertex;
	}

	this.addEdge = function(edge,id){
		this.nedges++;		
		if(id == undefined)
			this.edgeList[this.nedges] = edge;
		else
			this.edgeList[id] = edge;
	}

	width =parseInt( document.getElementById(container).style.width,10);
	height = parseInt(document.getElementById(container).style.height,10);


	this.stage = new Kinetic.Stage({
		container: container,
		width: width,
		height: height
	});

	this.layer = new Kinetic.Layer();
	this.stage.add(this.layer);


	this.autoMove = function(){
	
		friction = 0.03;
		spring = 0.01;
		repulsion = 1000;

		for( k in this.vertexList){
			vertex = this.vertexList[k];

			if(!vertex.fixed){

				vertex.ax = -vertex.vx*friction;
				vertex.ay = -vertex.vy*friction;
	
				for(k2 in vertex.edgeList){
					edge = vertex.edgeList[k2];
					if(vertex == edge.vertex1){
						vertex2 = edge.vertex2;
					}
					else{
						vertex2 = edge.vertex1;
					}
	
					dist = Math.sqrt(Math.pow((vertex.x - vertex2.x),2)+Math.pow((vertex.y - vertex2.y),2));
	
					if(dist > 0){
						vertex.ax+=spring*(dist-edge.spring_l)*(vertex2.x-vertex.x)/dist; 
						vertex.ay+=spring*(dist-edge.spring_l)*(vertex2.y-vertex.y)/dist; 
					}
				}
	
	
				for(k3 in this.vertexList){
					vertex2 = this.vertexList[k3];
	
					dist = Math.sqrt(Math.pow((vertex.x - vertex2.x),2)+Math.pow((vertex.y - vertex2.y),2));
	
					if(dist > 0){
						vertex.ax+=-(repulsion/(dist*dist))*((vertex2.x-vertex.x)/dist);
						vertex.ay+=-(repulsion/(dist*dist))*((vertex2.y-vertex.y)/dist);
					}
				}
	
	
				vertex.vx += vertex.ax;
				vertex.vy += vertex.ay;
				x = vertex.x + vertex.vx; y = vertex.y + vertex.vy;
	
				/*
				if(x < vertex.radius){
					x=vertex.radius;
					vertex.vx = - vertex.vx;
				}

				if(x > (this.stage.width() - vertex.radius)){
					x=this.stage.width() - vertex.radius;
					vertex.vx = - vertex.vx;
				}

				if(y < vertex.radius){
					y=vertex.radius;
					vertex.vy = - vertex.vy;
				}

				if(y > (this.stage.height() - vertex.radius)){
					y=this.stage.height() - vertex.radius;
					vertex.vy = - vertex.vy;
				}
				*/
				vertex.moveTo(x , y);
			}
		}

	}

	this.autoMoveNDraw = function(){
		this.autoMove();
		this.layer.draw();		
	}

	this.autoArrange = function(iters){
		for(i=0;i<iters;i++){
			this.autoMove();
		}
		this.layer.draw();
	}

	this.interval = null;

	this.playWithPhysics = function(fps){
		this.interval = setInterval(this.autoMoveNDraw.bind(this),1000/fps);
	}

	this.stopPhysics = function(){
		clearInterval(this.interval);
	}

	this.gridArrange = function(){

		l =Math.floor(Math.sqrt(this.nvertices));
		i = 0;
		x = 100;
		y = 100;

		for(k in this.vertexList){
			vertex = this.vertexList[k];			
			vertex.moveTo(x,y);
			this.layer.draw();
			x+=100;
			i++;
			if(i%l == 0){
				x=100;
				y+=100;
			}
		}
	}

};
