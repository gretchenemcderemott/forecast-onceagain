<html>
<head>
	<title>View Application</title>	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link type="text/css" rel="stylesheet" href="css/secondlistcss.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
	
	<div id="container">
			<input type="hidden" name="command" value="LIST2" />
			<input type="hidden" name="applicationId" value="${THE_APPLICATION.appid }" />
			
			<table class="secondtable">
				<tbody>
					
					<tr class="secondtr">
						<td><label>Target Start Date:</label></td>
						<td><input type = "text" name = "stdate" id = "datepicker" value ="${THE_APPLICATION.tsdate}" readonly></td>
					</tr>
					
					<tr class="secondtr">
						<td><label>Target End Date:</label></td>
						<td><input type = "text" name = "enddate" id = "ed" value ="${THE_APPLICATION.enddate }" readonly> </td>
					</tr>
					
					<tr class="secondtr">
						<td><label>Project Name:</label></td>
						<td><input type = "text" name = "prjname" value ="${THE_APPLICATION.prjname }" readonly></td>
					</tr>
					
					<tr class="secondtr">
						<td><label>Inpacted Databases:</label></td>
						<td><textarea name="impdata" rows="3" cols="100" readonly>${THE_APPLICATION.impdata }</textarea></td>
					</tr>
					
					<tr class="secondtr">
						<td><label>Details:</label></td>
						<td><textarea name="details" rows="3" cols="100" readonly>${THE_APPLICATION.details }</textarea></td>
					</tr>
					
					<tr class="secondtr">
						<td><label>Benefits:</label></td>
						<td><textarea name="benefits" rows="3" cols="100" readonly>${THE_APPLICATION.benefits }</textarea></td>
					</tr>
					
				</tbody>
			</table>
		
		<div style="clear: both;"></div>
		
	</div>
	
</body>

</html>