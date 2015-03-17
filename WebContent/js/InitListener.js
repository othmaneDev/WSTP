$(document).ready(function(){
	
	$("#formCheck").submit(function(event){
		if(CheckTags.checkTagsSyntaxe($("#tags").val())){
			return;
		}else{
			$('#errorSyntax').modal('show');
			event.preventDefault();
		}
		
	});
	

  
	if(!$('#myCanvas').tagcanvas({
		textColour: '#000000',
		outlineColour:'#cccccc',
	    depth: 0.2,
	    maxSpeed:0.02,
	    wheelZoom:false,
	    weight:true,
	    dragControl:true
	  },'tagsList')) {
	$('#myCanvasContainer').hide();
	  }
    
});