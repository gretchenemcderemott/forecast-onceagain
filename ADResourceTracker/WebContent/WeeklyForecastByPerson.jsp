<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html>
<head>
	<title>WeeklyForecastTracker</title>
	<link type="text/css" rel="stylesheet" href="css/tablestyleswfbp.css">
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>AD Resource Tracker</h2>
		</div>
	</div>
	
	<div id="container">
		<div id="content">
			<h3>Weekly Forecast By Person</h3>
			
			
			<form class="form1" name="getprj" id="formid"  onsubmit="ApplicationControllerServlet" Method="get">   
				<input type="hidden" name="command" value="LOADFUNC" />
				
				<select id="wkfemployees" name="theEmpId" onchange="javascript:document.getprj.submit();">
					<option selected disabled>Select Employee</option>
					<c:forEach var="tempemps" items="${empsprjs}">
  					<option value="${tempemps.empid}" ${tempemps.empid == selectedModule ? 'selected':''}> <c:out value="${tempemps.empname}" /> </option>
  					</c:forEach>
				</select> 
				
				<select id="wkfprjs" name="thePrjId" onchange="javascript:document.getprj.submit();">
					<option selected disabled>Select Project </option>
					<c:forEach var="tempprjs" items="${thePrjs}">
  					<option value="${tempprjs.prjid}" ${tempprjs.prjid == selectedPrj ? 'selected':''}> <c:out value="${tempprjs.prjname}" /> </option>
  					</c:forEach>
				</select>			
				
			</form>
			

			
			<table class="gridview">
				<tr >
					<th>Employee Name</th>
    				<th>Project Name</th>
    				<th>Week Ending</th>
    				<th>Hours</th>
    				<th>Percolator Month</th>
    				<th>Monthly Total</th>
				</tr>
				
				 <c:forEach var="tempForecast" items="${theForecast}">				
					<tr align="center">
								<td> ${tempForecast.empname} </td> 
								<td> ${tempForecast.prjname} </td>
								<td> ${tempForecast.eowdate} </td>
								<td> <c:out value="${tempForecast.prjhrs == 0 ? '' : tempForecast.prjhrs}" /> </td>
								<td> ${tempForecast.eowmonth} </td> 
								<td> <c:out value="${tempForecast.prjtotalhrs == 0 ? '' : tempForecast.prjtotalhrs}" /> </td> 
					</tr>
				</c:forEach>
					
			</table>
			
			
		</div>
	</div>

	<div>
		<p>
			<a href="ApplicationControllerServlet">Back to Application List</a>
		</p>
	</div>

</body>
</html>
