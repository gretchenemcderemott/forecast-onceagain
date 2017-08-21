<!DOCTYPE html>
<html>

<head>
	<title>Update Application</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add_app_style.css">	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 <script>
 
function setDate() {
	
	//initialize months
	var monthNames = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];
	
	//initialize/get start date value and weeks value
	var startDateInitial = $( "#datepicker" ).datepicker({ dateFormat: 'DD,MM,YYYY' }).val();
	var days = "";
	days = document.getElementById("weeks").value;
	
	//set function to return nothing if user does not input value in box
	if(startDateInitial == null || startDateInitial == ""){return;}
	if(days == null || days == ""){return;}
	days = parseInt(days) * 7;
	
	//split startdate string
	var startDateSplit = startDateInitial.split("/");
		var month = parseInt(startDateSplit[0]);
		var day = parseInt(startDateSplit[1]);
		var year = parseInt(startDateSplit[2]);
	 
	//convert int into date
	var startDate = new Date(year, month - 1, day);

	//set endDate equest to startDate and add given weeks to endDate
	var endDate = new Date(startDate);
	endDate.setDate(endDate.getDate() + days);

	//create string for month day and year 
	var sMonth = '' + (monthNames[startDate.getMonth()]),
 		sDay = '' + startDate.getDate(),
 		sYear = startDate.getFullYear();
 		
	var eMonth = '' + (monthNames[endDate.getMonth()]),
 		eDay = '' + endDate.getDate(),
 		eYear = endDate.getFullYear();

		startDate = [sMonth, sDay, sYear].join('/');
		endDate = [eMonth, eDay, eYear].join('/');

	//set/show input box start and end date
	$("#datepicker").val(startDate);
	$("#ed").val(endDate);
}
</script>
   
<script>
$( function() {
	$("#datepicker").datepicker();
} );
</script>
  
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>AD Resource Tracker</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Update Application</h3>
		
		<form action="ApplicationControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="applicationId" value="${THE_APPLICATION.appid }" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Application Name:</label></td>
						<td><input type = "text" name = "appname" id = "app" value="${THE_APPLICATION.appname }"></td>
					</tr>

					<tr>
						<td><label>Number of Developers:</label></td>
						<td><input type = "number" name = "devnum" id = "dev" value ="${THE_APPLICATION.devnum }"> </td>
					</tr>

					<tr>
						<td><label>Number of Weeks:</label></td>
						<td><input type = "number" name = "wknum" id = "weeks" onchange = "setDate();" value ="${THE_APPLICATION.wknum }"></td>
					</tr>
					
					<tr>
						<td><label>Number of Hours:</label></td>
						<td><input type = "number" name = "hrsnum" value ="${THE_APPLICATION.hrsnum }"></td>
					</tr>
					
					<tr>
						<td align ="right"><label>Target Start Date:</label></td>
						<td><input type = "text" name = "stdate" id = "datepicker" onchange = "setDate();" value ="${THE_APPLICATION.tsdate }"></td>
					</tr>
					
					<tr>
						<td><label>Target End Date:</label></td>
						<td><input type = "text" name = "enddate" id = "ed" onchange = "setDate();" value ="${THE_APPLICATION.enddate }" readonly> </td>
					</tr>
					
					<tr>
						<td><label>Project Name:</label></td>
						<td><input type = "text" name = "prjname" value ="${THE_APPLICATION.prjname }" ></td>
					</tr>
					
					<tr>
						<td><label>Inpacted Databases:</label></td>
						<td><textarea name="impdata" rows="3" cols="100">${THE_APPLICATION.impdata }</textarea></td>
					</tr>
					
					<tr>
						<td><label>Details:</label></td>
						<td><textarea name="details" rows="3" cols="100" >${THE_APPLICATION.details }</textarea></td>
					</tr>
					
					<tr>
						<td><label>Benefits:</label></td>
						<td><textarea name="benefits" rows="3" cols="100" >${THE_APPLICATION.benefits }</textarea></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="ApplicationControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>