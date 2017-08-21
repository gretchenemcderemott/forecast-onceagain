<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Application Tracker App</title>
 	<link type="text/css" rel="stylesheet" href="css/scheduletabcss.css">
 
</head>

<body>

	<div id="wrapper">	
		<div id="header">
			<h2>AD Resource Tracker</h2>
		</div>		
	</div>
	
	<div id="container">
		<h3>Schedule Application Form</h3> 	
		<div id="container">
			<div id="content">
			
			<form class="form1" name="getprj" id="formid"  onsubmit="ApplicationControllerServlet" Method="get">   
				<input type="hidden" name="command" value="LOADFUNCSCHED" />
				
				<select id="schedprojects" name="thePrjId" onchange="javascript:document.getprj.submit();">
					<option selected disabled>Select Project</option>
					<c:forEach var="tempprjs" items="${projectsSched}">
  					<option value="${tempprjs.prjid}" ${tempprjs.prjid == selectedModule ? 'selected':''}> <c:out value="${tempprjs.prjname}" /> </option>
  					</c:forEach>
				</select> 
				
				<select id="schedapps" name="theAppId" onchange="javascript:document.getprj.submit();">
					<option selected disabled>Select Application </option>
					<c:forEach var="tempapps" items="${theApps}">
  					<option value="${tempapps.appid}" ${tempapps.appid == selectedApp ? 'selected':''}> <c:out value="${tempapps.appname}" /> </option>
  					</c:forEach>
				</select>
			</form>
			
			<br />
			
		
		
					<form action="ApplicationControllerServlet" method="GET">				
				<table style="width:800px;">
					<tr>
						<td>
							<label>Project Name:</label>
						</td>
						
						<td>
					 		<input type = "text" name = "prjname" id = "app" value="${THE_FIRSTQ.prjname }">
						</td>	
				
					
					<c:forEach var="tempSecondQ" items="${theSecondQ}">
					<tr>	
						<th>
							<label>Resource:</label>
						</th>

						<td>
							<input type="text" name="empname" value="${tempSecondQ.empname}" />
						</td>
						<td>
							<input type="text" name="resneed" value="${tempSecondQ.resneed}" />
						</td>
					</tr>
					</c:forEach>

					<tr>
						<td>
							<label>Number of Hours:</label>
						</td>
						<td>	
							<input type = "text" name = "hrsnumt" value ="${THE_THIRDQ.hrsnumt }">
						</td>
						<td>
							<label>Number of Weeks:</label>
						</td>
						<td>
							<input type = "text" name = "wknumt" id = "weeks" value ="${THE_THIRDQ.wknumt }">
						</td>
					</tr>
			
					<tr>
						<td>
							<label>Target Start Date:</label>
						</td>
						<td>
							<input type = "text" name = "stdate" class = "datepicker" id="datepicker" onchange = "setDate();" value ="${THE_FOURTHQ.tsdate }">
						</td>
						<td>
							<label>Target End Date:</label>
						</td>
						<td>
							<input type = "text" name = "enddate" class = "datepicker" id="ed" onchange = "setDate();" value ="${THE_FOURTHQ.tedate }" ><br /> 
						</td>
						<td></td>
<!--  						<td>	
							<input type="submit" value="Update(Db)" class="save" />
						</td> -->
					</tr>
			</table>
			
			<br />
			<table class="gridview">
				<tr>
					<th></th>
					<c:forEach items="${columnHeaders}" var="getcolumnheaders" varStatus="loop">
						<th><c:out value="${getcolumnheaders}"></c:out></th>
					</c:forEach>
				</tr> 			
    			<c:forEach items="${theMonths}" var="getmonths" varStatus="loop">
    				<c:set var="getpercnums" value="${thePercNums[loop.index]}" />
    				<c:set var="getresneedsarray" value="${resneedsarray[loop.index]}"  />
    				<c:set var="getteamleadarray" value="${theTeamLead[loop.index]}"  />
    				<c:set var="getdm" value="${theDM[loop.index]}"  />
    				<c:set var="getsmpm" value="${theSMPM[loop.index]}"  />
    				<c:set var="getpase" value="${thePase[loop.index]}"  />
    				<c:set var="getdba" value="${theDBA[loop.index]}"  />
    				<c:set var="getdevstotal" value="${theDevsTotal[loop.index]}"  /> 
    				<c:set var="getdmtotal" value="${theDMTotal[loop.index]}"  />
    				<c:set var="getteamleadtotal" value="${theTeamLeadTotal[loop.index]}"  />
    				<c:set var="thesmpmtotal" value="${theSMPMTotal[loop.index]}"  />
    				<c:set var="thepasetotal" value="${thePaseTotal[loop.index]}"  />
    				<c:set var="thedbatotal" value="${theDBATotal[loop.index]}"  /> 
    				<c:set var="thedevonlytotal" value="${theDevOnlyTotal[loop.index]}"  />
    				<c:set var="theorgtotal" value="${theOrgTotal[loop.index]}"  /> 
    				<c:set var="thetotalcol" value="${theTotalCol[loop.index]}"  />
    				<tr>
      					<td style="font-weight: bold"><c:out value="${getmonths.yearmonth}" /></td>
      					<td><c:out value="${getpercnums.percnum}" /></td>
      					<c:forEach items="${getresneedsarray}" var="resneedsCell" >
                			<td><c:out value="${resneedsCell == 0 ? '' : resneedsCell}" /></td>                    
            			</c:forEach>
            			<td><c:out value="${getteamleadarray == 0 ? '' : getteamleadarray}" /></td>
            			<td><c:out value="${getdm == 0 ? '' : getdm}" /></td>
            			<td><c:out value="${getsmpm == 0 ? '' : getsmpm}" /></td>
            			<td><c:out value="${getpase == 0 ? '' : getpase}" /></td>
            			<td><c:out value="${getdba == 0 ? '' : getdba}" /></td>
            			<td><c:out value="${getdevstotal == 0 ? '' : getdevstotal}" /></td>
            			<td><c:out value="${getdmtotal == 0 ? '' : getdmtotal}" /></td>
            			<td><c:out value="${getteamleadtotal == 0 ? '' : getteamleadtotal}" /></td>
            			<td><c:out value="${thesmpmtotal == 0 ? '' : thesmpmtotal}" /></td>
            			<td><c:out value="${thepasetotal == 0 ? '' : thepasetotal}" /></td>
            			<td><c:out value="${thedbatotal == 0 ? '' : thedbatotal}" /></td>	
            			<td><c:out value="${thedevonlytotal == 0 ? '' : thedevonlytotal}" /></td>	
            			<td><c:out value="${theorgtotal == 0 ? '' : theorgtotal}" /></td>	
            			<td><c:out value="${thetotalcol == 0 ? '' : thetotalcol}" /></td>	
    				</tr>
  				</c:forEach> 
  				
  				<tr>
  					<td>&nbsp;</td>
  				</tr>
  				<tr>
  					<td style="font-weight: bold">Total:</td>
  					<td></td>
  					<c:forEach items="${fulltotalrow}" var="getfulltotalrow" varStatus="loop">
  						<td><c:out value="${getfulltotalrow}"></c:out></td>
  					</c:forEach>
  				</tr>
  							 				
			</table>
			</form>
			
			<br /><br />
			<table class="devorgtable">
				<tr>
  					<th>Development Total</th>
  					<th>Organization Total</th>
  				</tr>  
  				
  				<tr>
  					<td>${DEVORGTOTAL}</td>
  					<td>${DEVORGTOTAL}</td>
  				</tr> 
			</table>
			
		</div>
	</div> 

		<div style="clear: both;"></div>
		
		<p>
			<a href="ApplicationControllerServlet">Back to List</a>
		</p>
</div>
</body>
</html>
