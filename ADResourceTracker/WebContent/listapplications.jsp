<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Application Tracker App</title>
	<link type="text/css" rel="stylesheet" href="css/applicationscss.css">
	<link type="text/css" rel="stylesheet" href="css/secondlistcss.css">
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>AD Resource Tracker</h2>
		</div>
	</div>
	
		<div id="container">
			<div id="content">
				<form name="vs" action="ApplicationControllerServlet" method="GET" style="display:inline-block">
					<input type="hidden" name="command" value="LISTPRJSSCHED" />
					<input type = "button" class="view_forecast" name="viewschedule" value = "VIEW SCHEDULE" 
						onclick="javascript:document.vs.submit();" >
				</form>
				
				<form name="vf" action="ApplicationControllerServlet" method="GET" style="display:inline-block">
					<input type="hidden" name="command" value="LISTEMPSPRJS" />
						<input type = "button" class="view_forecast" name="viewforecast" value = "VIEW FORECAST" 
							onclick="javascript:document.vf.submit();">
				</form>
				
			<form class="prjs" name="getprj" id="prjformid"  onsubmit="ApplicationControllerServlet" Method="get">   
				<input type="hidden" name="command" value="LOADPRJAPPS" />
				
				<select id="listprjs" name="thePrjId" onchange="javascript:document.getprj.submit();">
					<option selected disabled>Select Project</option>
					<c:forEach var="tempprjs" items="${projects}">
  					<option value="${tempprjs.prjid}" ${tempprjs.prjid == selectedModule ? 'selected':''}> <c:out value="${tempprjs.prjname}" /> </option>
  					</c:forEach>
				</select> 		
			</form>
				
			<br />	
			
			<table class="gridview">
				<tr>
					<th>App Name</th>
    				<th>Dev Num</th>
    				<th>Num of Weeks</th>
    				<th>Num of Hours</th>
    				<th>Dev</th>
    				<th>DM</th>
    				<th>Team Lead</th>
    				<th>PM/Scrum Master</th>
    				<th>PASE</th>
    				<th>DBA</th>
    				<th>Dev Total</th>
				</tr>
				
   				<c:forEach var="tempPrjApps" items="${thePrjApps}"> 
				
					<!--  set up a link for each application -->
  					<c:url var="tempExtendedApp" value="ApplicationControllerServlet">
						<c:param name="command" value="LOADEXTENDEDLIST" />
						<c:param name="theAppId" value="${tempPrjApps.appid }" />
						<c:param name="thePrjId" value="${tempPrjApps.prjid }" />
					</c:url>				
					
					<!--  set up a link to delete an application -->
					<c:url var="deleteLink" value="ApplicationControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="thePrjId" value="${tempPrjApps.prjid }" />
					</c:url> 
					
	  				<tr>
						<td> <a href="${tempExtendedApp}">${tempPrjApps.appname} </a></td>
						<td> ${tempPrjApps.devnum} </td>
						<td> ${tempPrjApps.wknum} </td>
						<td> ${tempPrjApps.hrsnum} </td>
						<td> ${tempPrjApps.dev} </td>
						<td> ${tempPrjApps.dm} </td>
						<td> ${tempPrjApps.teamlead} </td>
						<td> ${tempPrjApps.smpm} </td>
						<td> ${tempPrjApps.pase} </td>
						<td> ${tempPrjApps.dbas} </td>
						<td> ${tempPrjApps.devtotal} </td>
					</tr>
					
				</c:forEach> 
				
				<c:forEach var="tempPrjTotals" items="${theTotals}">
				  	<tr>
    					<th>Total:</th>
    						<td>${tempPrjTotals.devnumtotal}</td>
    						<td>${tempPrjTotals.wknumtotal}</td>
    						<td>${tempPrjTotals.hrsnumtotal}</td>
    						<td>${tempPrjTotals.devstotal}</td>
    						<td>${tempPrjTotals.dmtotal}</td>
    						<td>${tempPrjTotals.teamleadtotal}</td>
    						<td>${tempPrjTotals.smpmtotal}</td>
    						<td>${tempPrjTotals.pasetotal}</td>
    						<td>${tempPrjTotals.dbastotal}</td>
    						<td>${tempPrjTotals.devtotals}</td>
  					</tr>
  					
  					<tr>
    					<th>Dev Only:</th>
    						<td>${tempPrjTotals.devonly}</td>
 					</tr>
 				</c:forEach>
			</table>
		</div>
	</div>
	
	
	<div id="secondtablecontainer">
		<form name="form1" action="ApplicationControllerServlet" method="GET">
			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="applicationId" value="${THE_APPLICATION.appid }" />
			
			<table class="secondtable">
				<tbody>	
					<tr>
						<td><label>Project Name: </label><input type = "text" name = "prjname" value ="${theExtendedApps.prjname }" onchange="javascript:document.form1.submit();"></td>
					</tr>
					
					<tr>
						<td><label>Impacted Databases: </label><br /><textarea name="impdata" rows="3" cols="100" onchange="javascript:document.form1.submit();">${theExtendedApps.impdb }</textarea></td>
					</tr>
					
					<tr>
						<td><label>Details: </label><br /><textarea name="details" rows="3" cols="100" onchange="javascript:document.form1.submit();">${theExtendedApps.details }</textarea></td>
					</tr>
					
					<tr>
						<td><label>Benefits: </label><br /><textarea name="benefits" rows="3" cols="100" onchange="javascript:document.form1.submit();">${theExtendedApps.benefits }</textarea></td>
					</tr>
					
					<tr>
						<td><label>Target Start Date: </label><input type = "text" name = "stdate" id = "datepicker" value ="${theExtendedApps.tsdate}" readonly ></td>
					</tr>
					
					<tr>
						<td><label>Target End Date: </label><input type = "text" name = "enddate" id = "ed" value ="${theExtendedApps.tedate }" readonly> </td>
					</tr>
				</tbody>	
				
			</table>
		
		<div style="clear: both;"></div>
		</form>
	</div>
  	
</body>
</html>
