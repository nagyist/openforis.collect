<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>

<html>
  <head>
  	<meta http-equiv="Pragma" content="no-cache">
    <script type="text/javascript" src="script/sessionping.js"></script>
        
	<script src="script/js/jquery/jquery.js"></script>
	<script src="script/js/jquery/jquery-ui.js"></script>
	<script src="script/js/jquery/jquery.dataTables.js"></script>
	<script src="script/js/jquery/jquery.blockUI.js"></script>
	<script src="script/js/bootstrap/js/bootstrap.min.js"></script>
	<script src="script/js/bootstrap-select/bootstrap-select.js"></script>
	<script src="script/js/datatables/DT_bootstrap.js"></script>
	
	<link href="script/js/jquery/jquery-ui.css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="script/js/datatables/DT_bootstrap.css">
	<link href="script/js/jquery/jquery.dataTables.css" rel="stylesheet" />
 	<link href="script/js/bootstrap/css/bootstrap.css" rel="stylesheet" />
	<link href="script/js/bootstrap-select/bootstrap-select.css" rel="stylesheet" />

<script type="text/javascript">

$.extend( $.fn.dataTableExt.oStdClasses, {
    "sWrapper": "dataTables_wrapper form-inline"
} );

$(document).ready(
		
		function() {
			// Mark the selects as "bootstratp-select" enabled 
			$('.selectpicker').selectpicker();
						  
		    $('#maint_tabs a[href="#general"]').tab('show');

		    $.ajax({
		    	url: "assets/outputJSON.txt",
		    	dataType: 'json',		    	
		    }).complete(function(jqXHR, textStatus) {
		    	
		    	var survey = $.parseJSON(jqXHR.responseText);
		    	var uiOptions = survey.uiOptions;
		    	var formSets = uiOptions.formSets;
		    	var formSet = formSets[0];
		    	var forms = formSet.forms;
		    	
		    	//<li><a href="#general" class="active" data-toggle="tab">General</a></li>
		    	$.each( forms, function( index, form){
		    		renderForm(form,survey, "form_tabs");
		    	});
		    	$('#form_tabs a:first').tab('show');
		    	
		    });
		    
		    var renderForm = function(form, survey, idContainer) {
		    	$("#"+idContainer).append('<li><a href="#form'+form.id+'" class="active" data-toggle="tab">'+form.labels[0].text+'</a></li>');
	    		$("#container"+idContainer).append('<div id="form'+form.id+'" class="tab-pane well well-large"><div id="fieldset_form'+form.id+'" class="form-horizontal"></div></div>');
	    		
	    		var mainFormSection = form.formSections[0];
	    		renderFormSection(form, mainFormSection, survey);
	    		
	    		$("#fieldset_form"+form.id ).sortable({placeholder: "ui-state-highlight",helper:'clone'});
	    		$("#fieldset_form"+form.id ).droppable({
	 		    	accept: ".draggableComponent",
	 		    	activeClass: "ui-state-default",
	 		    	hoverClass: "ui-state-hover",
	 		    	drop: function( event, ui ) {
	 		    		 alert("Dropped!!!");
	 		    	}
	 		    });
	    		var subForms = form.forms;
	    		if( subForms.length>0 ){
					var subformId = "subform"+form.id;
					$('#form'+form.id).append('<ul class="nav nav-tabs" id="'+subformId+'"></ul><div class="tab-content" id="container'+subformId+'"></div>' );
	    	
		    		for ( var i = 0; i < subForms.length; i++) {
						renderForm( subForms[i], survey, subformId);
					}
	    		}
	    		

		    };
		    
		    var renderFormSection = function(form, formSection, jsonSurvey) {
		    	$.each(formSection.children, function( index, component) {
	    			$("#fieldset_form" + form.id).append(generateComponent(component, jsonSurvey));
	    		});
		    };
		    
		    var generateComponent = function(component, jsonSurvey) {
		    	switch(component.modelObjectType) {
		    	case "Field":
		    		var node = getNodeById( component.attributeId, jsonSurvey);
		    		if( node != null){

		    			return '<div class="control-group sortable"><label class="control-label" for="component'+component.id+'">'+node.labels[0].text+'</label><div class="controls"><input type="text" id="component'+component.id+'"/></div></div>';
		    		}
		    	default:
		    		return "";
		    	}
		    };
		    
		    var getChildById =  function( id, entity  ){
		    	var children = entity.childDefinitions;
		    	for ( var i = 0; i < children.length; i++) {
					var child = children[i];
					
					if( child.id == id ){
						return child;
					}else if( child.nodeType == 'EntityDefinition'){
						var found = getChildById( id, child);
						if( found != null ){
							return found;
						}
					}
				}
		    	return null;
		    }
		    
		    var getNodeById= function( id, jsonSurvey ){
		    	var rootEntityDefinitions = jsonSurvey.schema.rootEntityDefinitions;
		    	
		    	for ( var i = 0; i < rootEntityDefinitions.length; i++) {
			
		    		var rootEntity = rootEntityDefinitions[i];
		    		if( id == rootEntity.id ){
		    			return rootEntity;
		    		}else{
		    			return getChildById( id, rootEntity );
		    		}
		    		
				}
		    	return null;
		    }
		    
		    
			$( ".draggableComponent" ).draggable({ opacity: 0.7, helper: "clone" , snap: true, connectToSortable: ".sortable",});
		    
// 		    $( ".droppableTarget" ).droppable({
// 		    	accept: ".draggableComponent",
// 		    	activeClass: "ui-state-default",
// 		    	hoverClass: "ui-state-hover",
// 		    	drop: function( event, ui ) {
// 		    		 alert("Dropped!!!");
// 		    	}
// 		    });
		    
		 }
);

$.extend( $.fn.dataTableExt.oStdClasses, {
    "sWrapper": "dataTables_wrapper form-inline"
} );

</script>


    <style type="text/css" media="screen"> 
		html, body	{ height:100%; }
		body {
			font-family: verdana, serif, monospace;
			font-size: 1.0em;
			margin:0 5 5 5;			
			padding:0 10 10 10;
			text-align:left;
		    background-color: #ffffff;
		}
		fieldset {
			width: 80%;
			padding-top: 10px;
		}
		legend {
			font-weight: bold;
			font-size: 0.8em;
		}
		.login td {
			font-size: small;
			font-family: verdana, serif, monospace;
			line-height: 1em;
			font-size: 0.7em;
			padding-top: 10;
		}
		input.button {
			font-family: verdana, serif, monospace;
			line-height: 1em;
			font-size: 1em;
			padding: 3 8 5 8;
			background-color: #DDDDDD;
    		border: 1px outset #DDDDDD;
    		color: #000000;
    		border-style: outset;
		}

		.login input {
			height: 20;
			border: 1px solid #DDDDDD;
			font-family: verdana, serif, monospace;
			line-height: 1em;
			font-size: 1em;
		}
		
		div.error {
			font-weight: bold;
			font-size: 0.8em;
			color: red;
		}
		
		div.warn {
			font-weight: bold;
			font-size: 0.8em;
			color: blue;
		}
		
		
		.sortable { list-style-type: none; margin: 0; padding: 0; width: 60%; }
		
		.ui-state-highlight { height: 1.5em; line-height: 1.2em; }
			
		
/* 		label { */
/* 			margin-top: 10px; */
/* 		} */
		
		.form-horizontal .control-label {
			text-align: left;
			padding-top: 0;
		}
		
	</style>
    <title>OpenForis Collect</title>
  </head>

  <body>


    <div class="container-fluid">
	    <div class="row-fluid">
		    <div class="span2 well well-large">
		    
		    <div id="inputTextDraggable" class="ui-widget-content draggableComponent well">
		    	<label>Input text</label>
				<input type="text" class="draggable" placeholder="Drop me!" />
			</div>

		    </div>
		    <div class="span10 ">
		    <!--Body content-->
		    <div class="droppableTarget well well-large " style="width:100%; height:80%" id="canvas">
				<ul class="nav nav-tabs" id="form_tabs"></ul>
				<form class="form-horizontal">
				 	<div class="tab-content" id="containerform_tabs"></div>		
				 </form>    
		    </div>
		    	
		    </div>
	    </div>
    </div>

</body>
</html>
