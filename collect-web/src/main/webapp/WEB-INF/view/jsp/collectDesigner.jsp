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
	
<!-- 	<link href="script/js/jquery/jquery-ui.css" rel="stylesheet" /> -->
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
			overflow: hidden;
			text-align:center;
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
		
		label {
			margin-top: 10px;
		}
		
	</style>
    <title>OpenForis Collect</title>
  </head>

  <body>
  	<form>
    <table width="100%"  height="100%">
    	<tr height="102">
    		<td colspan="3" align="center">
    			<img alt="Open Foris Collect" src="images/banner.jpg">
    		</td>
    	</tr>
    	<tr height="50">
    		<td colspan="3" align="center">
    		
	    		<div class="container">
					<ul class="nav nav-tabs" id="maint_tabs">
					    <li><a href="#general" class="active" data-toggle="tab">General</a></li>
					    <li><a href="#coordinates" data-toggle="tab">Coordinates</a></li>
					    <li><a href="#species" data-toggle="tab">Species lists</a></li>
					    <li><a href="#advanced" data-toggle="tab">Advanced</a></li>
				    </ul>
				    
				    <div class="tab-content">
						<div id="general" class="tab-pane active well well-large">
							<fieldset>
								<table width="100%">
									<tr class="rowForm">
										<td  colspan="100%" align="left">										
											<label>Survey Name</label>
											<input type="text" id="survey_name" name="survey_name" placeholder="Type something…">										
										</td>
									</tr>
									<tr class="rowForm">
										<td>										
											<label>Default language</label>											
											<select	class="selectpicker show-tick show-menu-arrow " id="survey_language" name="survey_language">		
												<option value="EN" selected='true'>English</option>							
												<option value="FR">French</option>
											</select>
										</td>
										<td>										
											<label>Alt. language</label>											
											<select	class="selectpicker show-tick show-menu-arrow " id="survey_language_alt" name="survey_language_alt">		
												<option value="EN" selected='true'>English</option>							
												<option value="FR">French</option>
											</select>
										</td>
									</tr>
									<tr class="rowForm">
										<td  colspan="100%" align="left">										
											<label>Form bundles <div style="float: right; display :inline;">
												<button type="submit" class="btn">
	  												<i class="icon-plus icon-white"></i>
												</button>
											</div></label>
											
											
											<div style="border : solid 1px #000000; padding : 4px; width : 100%; margin:5px; margin-top : 10px; border-radius: 5px;  height : 250px; overflow : auto; ">
											<div id=""><i class="icon-book"> </i>Cluster </div>
											<div id=""><i class="icon-book"> </i>Household survey </div>
											
											</div>
										</td>
									</tr>
								</table>
							</fieldset>
						</div>
						
						<div id="coordinates" class="tab-pane well well-large">
							<fieldset>
								<table width="100%">
									<tr class="rowForm">
										<td  colspan="100%" align="left">										
											<label>Spatial Reference System <div style="float: right; display :inline;">
												<button id="add_srs" class="btn" type="button" data-toggle="modal" data-target="#edit_srs">
	  												<i class="icon-plus "></i>
												</button>
											</div></label>
											<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered datatableCollect"  id="srs_list">
												<thead>
													<tr>
														<th>level1_code</th>
														<th>level2_code</th>
														<th>level3_code</th>
														<th>location</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>110_110</td>
														<td></td>
														<td></td>
														<td>-324650,123540</td>
													</tr>
												</tbody>
											</table>										
										</td>
									</tr>
	
									<tr class="rowForm">
										<td  colspan="100%" align="left">										
											<label>Sampling design <div style="float: right; display :inline;">
												<button type="submit" class="btn">
	  												<i class="icon-upload"></i>
												</button>
											</div></label>
										<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered datatableCollect"  id="example">
	<thead>
		<tr>
			<th>level1_code</th>
			<th>level2_code</th>
			<th>level3_code</th>
			<th>location</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
			<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		<tr>
			<td>110_110</td>
			<td></td>
			<td></td>
			<td>-324650,123540</td>
		</tr>
		</tbody></table></div>
											
										</td>
									</tr>
								</table>
							</fieldset>
						</div>
						
						<div id="species" class="tab-pane well well-large">
							Species
						</div>
						
						<div id="advanced" class="tab-pane well well-large">
							Advanced
						</div>
					</div>
			    </div>
			   
    		</td>
    	</tr>

  
 		 <tr>
    		<td colspan="3" align="center">
    			<button type="submit" class="btn"><i class="icon-ok icon-white"></i>Save</button>
    		</td>
    	</tr>
    	
    	<tr>
    		<td colspan="3" align="center">
    			<img  src="images/footer.jpg">
    		</td>
    	</tr>
    </table>
    </form>

	<div id="edit_srs" class="modal hide fade" style="width:400px;">

		<div class="modal-header">
			<h5>Spatial Reference Systems</h5>
		</div>

		<div class="modal-body" >
			<fieldset style="align:left;">
				<table style="width:100%;" >
				
					<tr><td>
						<label>ID</label> 
						<input type="text" id="srs_name" name="srs_name" class="input-large">
					</td></tr>
					<tr><td>		
						<label>Name</label>
						<input type="text" id="srs_name" name="srs_name" class="input-large">
					</td></tr>	
					<tr><td>
						<label>Well Known Text (WKT)</label> 
						<textarea id="srs_wkt"></textarea>
					</td></tr>
				
				</table>
				
			</fieldset>
			
		</div>
		<div class="modal-footer" style="align:center">
			<a href="#" class="btn btn-success" ><i class="icon-ok icon-white"></i> Apply</a>
			<a href="#" class="btn btn-warning" data-dismiss="modal" aria-hidden="true" ><i class="icon-remove icon-white"></i> Cancel</a>
		</div>
	</div>
	
		<div id="import_sampling" class="modal hide fade" style="width:400px;">

		<div class="modal-header">
			<h5>Import sampling design</h5>
		</div>

		<div class="modal-body" >
			<fieldset style="align:left;">
				<table style="width:100%;" >
				
					<tr><td>
						<label>Source file (CSV)</label> 
						<input type="text" id="srs_name" name="srs_name" class="input-large">
					</td></tr>
					<tr><td>		
						<label>Name</label>
						<input type="text" id="srs_name" name="srs_name" class="input-large">
					</td></tr>	
					<tr><td>
						<label>Well Known Text (WKT)</label> 
						<textarea id="srs_wkt"></textarea>
					</td></tr>
				
				</table>
				
			</fieldset>
			
		</div>
		<div class="modal-footer" style="align:center">
			<a href="#" class="btn btn-success" ><i class="icon-ok icon-white"></i> Apply</a>
			<a href="#" class="btn btn-warning" data-dismiss="modal" aria-hidden="true" ><i class="icon-remove icon-white"></i> Cancel</a>
		</div>
	</div>


</body>
</html>
