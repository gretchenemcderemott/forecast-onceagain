package AppResourceTracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ApplicationDbUtil {

	private DataSource dataSource;

	public ApplicationDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public ApplicationDbUtil() {
		// TODO Auto-generated constructor stub
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		//check if objects are not null and close
		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();   // doesn't really close it ... just puts back in connection pool
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public List<ApplicationInstance> getProjects() throws Exception {
		
		List<ApplicationInstance> projects = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			// create sql statement
			String sql = "select * from projects_table order by prj_name";
			
			myStmt = myConn.createStatement();
			
			// execute query
			myRs = myStmt.executeQuery(sql);
			
			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int prjid = myRs.getInt("prj_id");
				String prjname = myRs.getString("prj_name");
				
				// create new application object
				ApplicationInstance tempProjects = new ApplicationInstance(prjid, prjname);
				
				// add it to the list of applications
				projects.add(tempProjects);				
			}
			
			return projects;		
		}
		finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}		
	}
	
	public List<ApplicationInstance> getPrjApps(String thePrjID) throws Exception {

		List<ApplicationInstance> prjApps = new ArrayList<>();
		ApplicationInstance thePrjApps = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int thePrj;
		
		
		try {
			//convert application id to int
			thePrj = Integer.parseInt(thePrjID);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected student
			String sql = "with prjinfo as (select prj_id from projects_table where prj_id=?), " +
							"prjname as (select prj_id, prj_name from projects_table where prj_id=?), " +
							"prjapps as (select prj_id, app_id from prjs_apps_table where prj_id=?), " +
							"appname as (select app_id, app_name from apps_table ), " +
							"appinfo as (select * from app_info_table) " +

							"select prjinfo.prj_id prjid, prjname.prj_name prjname, prjapps.app_id appid, appname.app_name appname, appinfo.dev_num devnum, appinfo.wknum wknum, appinfo.hrs_num hrsnum, appinfo.dev dev, appinfo.dm dm, appinfo.teamlead teamlead, appinfo.smpm smpm, appinfo.pase pase, appinfo.dbas dbas, appinfo.devtotal devtotal " +

							"from prjinfo " +

							"full outer join prjname " +
								"on prjname.prj_id = prjinfo.prj_id " +
							"full outer join prjapps " +
								"on prjinfo.prj_id = prjapps.prj_id " +
							"left join appname " +
								"on prjapps.app_id = appname.app_id " +
							"left join appinfo " + 
								"on appname.app_id = appinfo.app_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, thePrj);
			myStmt.setInt(2, thePrj);
			myStmt.setInt(3, thePrj);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				Integer appid = myRs.getInt("appid");
				String appname = myRs.getString("appname");
				Integer devnum = myRs.getInt("devnum");
				Integer wknum = myRs.getInt("wknum");
				Integer hrsnum = myRs.getInt("hrsnum");
				Integer dev = myRs.getInt("dev");
				Integer dm = myRs.getInt("dm");
				Integer teamlead = myRs.getInt("teamlead");
				Integer smpm = myRs.getInt("smpm");
				Integer pase = myRs.getInt("pase");
				Integer dbas = myRs.getInt("dbas");
				Integer devtotal = myRs.getInt("devtotal");
				 
				// use the applicationId during construction
				thePrjApps = new ApplicationInstance(thePrj, appid, appname, devnum, wknum, hrsnum, dev, dm, teamlead, smpm, pase, dbas, devtotal);
				
				//add to forecasts
				prjApps.add(thePrjApps);	
			}				
			
			return prjApps;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<ApplicationInstance> getTotals(String thePrjID) throws Exception {

		List<ApplicationInstance> prjTotals = new ArrayList<>();
		ApplicationInstance thePrjTotals = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int thePrj;
		
		
		try {
			//convert application id to int
			thePrj = Integer.parseInt(thePrjID);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected student
			String sql = "with temp as (select prj_id, app_id from prjs_apps_table where prj_id=?), " +
							"appinfo as (select * from app_info_table) " +
  
							"select sum(dev_num) devnumtotal, sum(wknum) wknumtotal, sum(hrs_num) hrsnumtotal, SUM(dev) as devstotal, SUM(dm) as dmtotal, SUM(teamlead) as teamleadtotal, SUM(smpm) as smpmtotal, SUM(pase) as pasetotal, SUM(dbas) as dbastotal, SUM(devtotal) as devtotals, SUM(dev) + SUM(dm) + SUM(teamlead) as devonly " +

							"from temp " +
							
							"left join appinfo " +
								"on temp.app_id = appinfo.app_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, thePrj);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				Integer devnumtotal = myRs.getInt("devnumtotal");
				Integer wknumtotal = myRs.getInt("wknumtotal");
				Integer hrsnumtotal = myRs.getInt("hrsnumtotal");
				Integer devstotal = myRs.getInt("devstotal");
				Integer dmtotal = myRs.getInt("dmtotal");
				Integer teamleadtotal = myRs.getInt("teamleadtotal");
				Integer smpmtotal = myRs.getInt("smpmtotal");
				Integer pasetotal = myRs.getInt("pasetotal");
				Integer dbastotal = myRs.getInt("dbastotal");
				Integer devtotals = myRs.getInt("devtotals");
				Integer devonly = myRs.getInt("devonly");
				 
				// use the applicationId during construction
				thePrjTotals = new ApplicationInstance(thePrj, devnumtotal, wknumtotal, hrsnumtotal, devstotal, dmtotal, teamleadtotal, smpmtotal, pasetotal, dbastotal, devtotals, devonly);
				
				//add to forecasts
				prjTotals.add(thePrjTotals);	
			}				
			
			return prjTotals;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public ApplicationInstance getExtendedApp(String theAppId, String thePrjId) throws Exception {

		ApplicationInstance theExtendedApp = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int appID;
		
		try {
			//convert application id to int
			appID = Integer.parseInt(theAppId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected student
			String sql = "with appinfo as (select app_id, tsdate, tedate, imp_db, details, benefits  from app_info_table where app_id=?), " +
							"appname as (select app_id, app_name from apps_table where app_id=?), " +
							"prjinfo as (select prj_id, app_id from prjs_apps_table where app_id=? ), " +
							"prjname as (select prj_id, prj_name from projects_table) " +
  
							"select prjname.prj_id prjid, prjname.prj_name prjname, appinfo.app_id appid, appinfo.imp_db impdb, appinfo.details details, appinfo.benefits benefits, appinfo.tsdate tsdate, appinfo.tedate tedate " +

							"from appinfo " +
  
							"full outer join appname " +
								"on appinfo.app_id = appname.app_id " +
							"full outer join prjinfo " +
								"on prjinfo.app_id = appinfo.app_id " +
							"left join prjname " +
								"on prjinfo.prj_id = prjname.prj_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, appID);
			myStmt.setInt(2, appID);
			myStmt.setInt(3, appID);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// retrieve data from result set row
			if (myRs.next()) {
				Integer prjid = myRs.getInt("prjid");
				String prjname = myRs.getString("prjname");
				String impdb = myRs.getString("impdb");
				String details = myRs.getString("details");
				String benefits = myRs.getString("benefits");
				String tsdate = myRs.getString("tsdate");
				String tedate = myRs.getString("tedate");
				
				// use the applicationId during construction
				theExtendedApp = new ApplicationInstance(appID, prjid, prjname, impdb, details, benefits, tsdate, tedate);
				
			}
			else {
				throw new Exception("Could not find application name: " + appID);
			}				
			
			return theExtendedApp;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<WeeklyForecastInstance> getEmpsPrjs() throws Exception {
		
		List<WeeklyForecastInstance> empsprjs = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		//CallableStatement cs = null;
		//PreparedStatement prepareCall = null;

		
		try {
			// get a connection
			 myConn = dataSource.getConnection();
			 myStmt = myConn.createStatement();
			 myRs = myStmt.executeQuery("select employee_table.emp_id, employee_table.emp_name, projects_table.prj_id, projects_table.prj_name " +
					 						"from (select g.*, row_number() over (order by emp_id) as seqnum " +
					 						"from employee_table g " +
					 						") employee_table full outer join " +
					 						"(select g.*, row_number() over (order by prj_id) as seqnum " +
					 						"from projects_table g " +
					 						") projects_table " +
					 						"on employee_table.seqnum = projects_table.seqnum");

			while (myRs.next()) {
				
				// retrieve data from result set row
				int empid = myRs.getInt("emp_id");
				String empname = myRs.getString("emp_name");
				int prjid = myRs.getInt("prj_id");
				String prjname = myRs.getString("prj_name");
				
				// create new application object
				WeeklyForecastInstance tempEmpsPrjs = new WeeklyForecastInstance(empid, empname, prjid, prjname);
				
				// add it to the list of applications
				empsprjs.add(tempEmpsPrjs);	
			}
			
			return empsprjs;		
		}
		finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}		
	}
	
	public List<WeeklyForecastInstance> getPrjs(String theEmpID) throws Exception {

		List<WeeklyForecastInstance> projects = new ArrayList<>();
		WeeklyForecastInstance theProjects = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int theEmp;
		
		try {
			//convert empid to int
			theEmp = Integer.parseInt(theEmpID);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected prjs
			String sql = "select distinct projects_table.prj_id as prj_id, projects_table.prj_name as prj_name " +
							"from (select perculator_hours.* from perculator_hours where perculator_hours.emp_id=?) " + 
							"perculator_hours left join " + 
							"(select projects_table.* from projects_table) " +
							"projects_table " +
							"on perculator_hours.prj_id = projects_table.prj_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, theEmp);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				Integer prjid = myRs.getInt("prj_id");
				String prjname = myRs.getString("prj_name");
				
				// use the applicationId during construction
				theProjects = new WeeklyForecastInstance(prjid, prjname);
				
				//add to forecasts
				projects.add(theProjects);	
			}				
			
			return projects;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<WeeklyForecastInstance> getForecast(String theEmpID, String thePrjID) throws Exception {

		List<WeeklyForecastInstance> forecasts = new ArrayList<>();
		WeeklyForecastInstance theForecast = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int theEmp;
		int thePrj;
		
		
		try {
			//convert application id to int
			theEmp = Integer.parseInt(theEmpID);
			thePrj = Integer.parseInt(thePrjID);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected student
			String sql = "with temp as (select emp_name as x, ROW_NUMBER() OVER (ORDER BY emp_name) rn from employee_table where emp_id=?), " + 
					 						"eowl as (select week_id, weeknum, eow_date, eow_month, ROW_NUMBER() OVER (ORDER BY weeknum asc) rn from endofweek_table), " +
					 						"perchrs as (select prj_id, week_id, prj_hrs, ROW_NUMBER() OVER (ORDER BY week_id asc) rn from perculator_hours where emp_id=? and prj_id=?), " +
					 						"prjname as (select prj_id, prj_name, ROW_NUMBER() OVER (ORDER BY prj_id) rn from projects_table where prj_id=?), " +
					 						"perchrsum as (select ym_id, sum(prj_hrs) as prhrs, ROW_NUMBER() OVER (ORDER BY ym_id) rn from perculator_hours where emp_id=? and prj_id=? group by ym_id order by ym_id asc), " +
					 						"perchrsmaxrow as (select week_id, weeknum, eow_date, eow_month, ym_id, ROW_NUMBER() OVER (ORDER BY eow_month asc) rn from endofweek_table where (weeknum) in (select max(weeknum) from endofweek_table group by eow_month)) " +
  
					 						"select (select x from temp ) Employee_Name, prjname.prj_name Project_Name, eowl.eow_date Week_end_date, perchrs.prj_hrs Hours, eowl.eow_month Week_End_Date_Month, perchrsum.prhrs Monthly_Total " +
  
					 						"from temp " +
  
					 						"full outer join eowl " +
  
					 							"on temp.rn = eowl.rn " + 
  
					 						"full outer join perchrs " +
  
					 							"on perchrs.week_id = eowl.week_id " +    
    
					 						"full outer join prjname " +
  
					 							"on perchrs.prj_id = prjname.prj_id " +
    
					 						"full outer join perchrsmaxrow " +
  
					 							"on perchrsmaxrow.week_id = eowl.week_id " +
    
					 						"full outer join perchrsum " +
  
					 							"on perchrsum.ym_id = perchrsmaxrow.ym_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, theEmp);
			myStmt.setInt(2, theEmp);
			myStmt.setInt(3, thePrj);
			myStmt.setInt(4, thePrj);
			myStmt.setInt(5, theEmp);
			myStmt.setInt(6, thePrj);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				String empname = myRs.getString("Employee_Name");
				String prjname = myRs.getString("Project_Name");
				String eowdate = myRs.getString("Week_end_date");
				Integer prjhrs = myRs.getInt("Hours");
				String eowmonth = myRs.getString("Week_End_Date_Month");
				Integer prjtotalhrs = myRs.getInt("Monthly_Total");
				 
				// use the applicationId during construction
				theForecast = new WeeklyForecastInstance(theEmp, thePrj, empname, prjname, eowdate, prjhrs, eowmonth, prjtotalhrs);
				
				//add to forecasts
				forecasts.add(theForecast);	
			}				
			
			return forecasts;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	//schedule tab
	//1st query for 1st section 4 total
	public ScheduleTabInstance getFirstQ(String thePrjId) throws Exception {
		
		ScheduleTabInstance theFirstQ = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int prjID;
		
		try {
			//convert application id to int
			prjID = Integer.parseInt(thePrjId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get firstQ
			String sql = "with prjname as (select prj_id, prj_name from projects_table where prj_id=?) " +
 
							"select prjname.prj_name prjname " +
							"from prjname";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, prjID);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// retrieve data from result set row
			if (myRs.next()) {
				String prjname = myRs.getString("prjname");
				
				
				theFirstQ = new ScheduleTabInstance(prjname);
				
			}
			else {
				throw new Exception("Could not find project name: " + prjID);
			}				
			
			return theFirstQ;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	
	}
	
	//2nd query
	public List<ScheduleTabInstance> getSecondQ(String thePrjId) throws Exception {

		List<ScheduleTabInstance> secondQ = new ArrayList<>();
		ScheduleTabInstance theSecondQ = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int thePrj;
		
		try {
			//convert empid to int
			thePrj = Integer.parseInt(thePrjId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected prjs
			String sql = "with resinfo as (select emp_id, prj_id, res_need from forecast_resources where prj_id=? ), " +
							"empname as (select emp_id, emp_name from employee_table order by emp_name) " +
 
							"select resinfo.prj_id prjid, resinfo.emp_id rempid, empname.emp_name empname, resinfo.res_need resneed " + 
							"from resinfo " +
							"left join empname " +
								"on resinfo.emp_id = empname.emp_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, thePrj);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				String empname = myRs.getString("empname");
				Double resneed = myRs.getDouble("resneed");
				
				// use the applicationId during construction
				theSecondQ = new ScheduleTabInstance(empname, resneed);
				
				//add to forecasts
				secondQ.add(theSecondQ);	
			}				
			
			return secondQ;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	//3rd query for 1st section 4 total
	public ScheduleTabInstance getThirdQ(String thePrjId) throws Exception {
		
		ScheduleTabInstance theThirdQ = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int prjID;
		
		try {
			//convert application id to int
			prjID = Integer.parseInt(thePrjId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get firstQ
			String sql = "with  prjapps as (select  prj_id, app_id from prjs_apps_table where prj_id=?), " +
							"appinfo as (select  app_id, wknum, hrs_num, tsdate, tedate from app_info_table) " +
      
							"select sum(appinfo.wknum) wknumt, sum(appinfo.hrs_num) hrsnumt " +
							"from prjapps " +
							"left join appinfo " +
								"on prjapps.app_id = appinfo.app_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, prjID);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// retrieve data from result set row
			if (myRs.next()) {
				Integer wknumt = myRs.getInt("wknumt");
				Integer hrsnumt = myRs.getInt("hrsnumt");
				
				theThirdQ = new ScheduleTabInstance(wknumt, hrsnumt);
				
			}
			else {
				throw new Exception("Could not find project name: " + prjID);
			}				
			
			return theThirdQ;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	
	}
	
	//4th query for 1st section 4 total
	public ScheduleTabInstance getFourthQ(String theAppId) throws Exception {
		
		ScheduleTabInstance theFourthQ = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int appID;
		
		try {
			//convert application id to int
			appID = Integer.parseInt(theAppId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get firstQ
			String sql = "with  appinfo as (select  app_id, tsdate, tedate from app_info_table where app_id=?) " +
      
						"select appinfo.app_id appid, appinfo.tsdate tsdate, appinfo.tedate tedate " +
						"from appinfo";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, appID);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// retrieve data from result set row
			if (myRs.next()) {
				String tsdate = myRs.getString("tsdate");
				String tedate = myRs.getString("tedate");
				
				theFourthQ = new ScheduleTabInstance(tsdate, tedate);
				
			}
			else {
				throw new Exception("Could not find project name: " + appID);
			}				
			
			return theFourthQ;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	
	}
	
	public List<ScheduleTabInstance> getApps(String thePrjId) throws Exception {

		List<ScheduleTabInstance> apps = new ArrayList<>();
		ScheduleTabInstance theApps = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int thePrj;
		
		try {
			//convert empid to int
			thePrj = Integer.parseInt(thePrjId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected prjs
			String sql = "with  prjinfo as (select  prj_id, app_id from prjs_apps_table where prj_id=?), " +
							"appinfo as(select app_id, app_name from apps_table) " +
      
							"select prjinfo.app_id appid, prjinfo.prj_id prjid, appinfo.app_name appname " +
							"from prjinfo " +
							"left join appinfo " +
								"on prjinfo.app_id = appinfo.app_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, thePrj);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				Integer appid = myRs.getInt("appid");
				String appname = myRs.getString("appname");
				
				// use the applicationId during construction
				theApps = new ScheduleTabInstance(appid, appname);
				
				//add to forecasts
				apps.add(theApps);	
			}				
			
			return apps;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<ScheduleTableBuildInstance> getHeaders(String thePrjId) throws Exception {

		List<ScheduleTableBuildInstance> team = new ArrayList<>();
		ScheduleTableBuildInstance theTeam = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int thePrj;
		
		try {
			//convert empid to int
			thePrj = Integer.parseInt(thePrjId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected prjs
			String sql = "with prjinfo as (select distinct prj_id, emp_id, role_id from perculator_hours where prj_id=?), " +
					"empname as (select emp_id, emp_name from employee_table order by emp_name asc),  " +
					"rolename as(select role_id, role_name  from prj_roles order by " + 
						"case " +
							"when role_name = 'Developer' then 1 " + 
							"when role_name = 'Team Lead' then 2 " +
							"when role_name = 'Delivery Manager' then 3 " +
							"when role_name = 'PM Scrum Master' then 4 " +
						"else 5 " +
						"end ) " +

					"select prjinfo.prj_id prj_id, empname.emp_name emprole, rolename.role_name rolename " +
					"from prjinfo " +
					"left join empname " +
					"on prjinfo.emp_id = empname.emp_id " +
					"left join rolename " +
					"on prjinfo.role_id = rolename.role_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, thePrj);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				String emprole = myRs.getString("emprole");
				
				// use the applicationId during construction
				theTeam = new ScheduleTableBuildInstance(emprole);
				
				//add to forecasts
				team.add(theTeam);	
			}				
			
			return team;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<BuildYearMonthInstance> getMonths() throws Exception {
		List<BuildYearMonthInstance> months = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			// create sql statement
			String sql = "select ym_id, year_month from year_months order by ym_id";
			
			myStmt = myConn.createStatement();
			
			// execute query
			myRs = myStmt.executeQuery(sql);
			
			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				Integer ymid = myRs.getInt("ym_id");
				String yearmonth = myRs.getString("year_month");
				
				// create new application object
				BuildYearMonthInstance tempMonths = new BuildYearMonthInstance(yearmonth);
				
				// add it to the list of applications
				months.add(tempMonths);				
			}
			
			return months;		
		}
		finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}	
	
	}
	
	public List<BuildYearMonthInstance> getPercnums(String thePrjId) throws Exception {
		List<BuildYearMonthInstance> percnums = new ArrayList<>();
		BuildYearMonthInstance thePercnums = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int thePrj;
		
		try {
			//convert empid to int
			thePrj = Integer.parseInt(thePrjId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected prjs
			String sql = "with prjinfo as(select ym_id, count(ym_id) as percnum from(select distinct week_id, ym_id from perculator_hours where prj_id=?) as test group by ym_id order by ym_id), " +
							"yearsmonth as(select ym_id, year_month from year_months order by ym_id) " +
 
							"select percnum " +
							"from prjinfo " + 
							"right join yearsmonth " +
							"on prjinfo.ym_id = yearsmonth.ym_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, thePrj);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				Integer percnum = myRs.getInt("percnum");
				
				// use the applicationId during construction
				thePercnums = new BuildYearMonthInstance(percnum);
				
				//add to forecasts
				percnums.add(thePercnums);
			}				
			
			return percnums;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<BuildYearMonthInstance> getResneeds(String thePrjId) throws Exception {
		List<BuildYearMonthInstance> resneeds = new ArrayList<>();
		BuildYearMonthInstance theResneeds = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int thePrj;
		
		try {
			//convert empid to int
			thePrj = Integer.parseInt(thePrjId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected prjs
			String sql = "with resneed as (select res_id as resid, emp_id as empid, prj_id as prjid, res_need as resneed from forecast_resources where prj_id=?), " +
							"empname as (select emp_id, emp_name from employee_table order by emp_name) " +
 
							"select resneed " +
							"from resneed " +
							"left join empname " +
							"on resneed.empid = empname.emp_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, thePrj);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				Double resource = myRs.getDouble("resneed");
				
				// use the applicationId during construction
				theResneeds = new BuildYearMonthInstance(resource);
				
				//add to forecasts
				resneeds.add(theResneeds);
			}				
			
			return resneeds;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<CreateCalcRows> getPercnumresneeds(String thePrjId) throws Exception {
		List<CreateCalcRows> percnumresneeds = new ArrayList<>();
		CreateCalcRows thePercnumresneeds = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int thePrj;
		
		try {
			//convert empid to int
			thePrj = Integer.parseInt(thePrjId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected prjs
			String sql = "with prjinfo as(select ym_id, count(ym_id) as percnum from(select distinct week_id, ym_id from perculator_hours where prj_id=?) group by ym_id order by ym_id), " +
							"yearsmonth as(select ym_id, year_month from year_months order by ym_id) " +

							"select percnum " +
							"from prjinfo " + 
							"right join yearsmonth " +
							"on prjinfo.ym_id = yearsmonth.ym_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, thePrj);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				Double percnum = myRs.getDouble("percnum");
				
				// use the applicationId during construction
				thePercnumresneeds = new CreateCalcRows(percnum);
				
				//add to forecasts
				percnumresneeds.add(thePercnumresneeds);
			}				
			
			return percnumresneeds;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<CreateCalcRows> getPercnumsResource(String thePrjId) throws Exception {
		List<CreateCalcRows> percnums = new ArrayList<>();
		CreateCalcRows thePercnums = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int thePrj;
		
		try {
			//convert empid to int
			thePrj = Integer.parseInt(thePrjId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected prjs
			String sql = "with prjinfo as(select ym_id, count(ym_id) as percnum from(select distinct week_id, ym_id from perculator_hours where prj_id=?) as test group by ym_id order by ym_id), " +
							"yearsmonth as(select ym_id, year_month from year_months order by ym_id) " +
 
							"select percnum " +
							"from prjinfo " + 
							"right join yearsmonth " +
							"on prjinfo.ym_id = yearsmonth.ym_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, thePrj);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				Double percnum = myRs.getDouble("percnum");
				
				// use the applicationId during construction
				thePercnums = new CreateCalcRows(percnum);
				
				//add to forecasts
				percnums.add(thePercnums);
			}				
			
			return percnums;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<BuildYearMonthInstance> getTotalsScheduleTab(String thePrjID) throws Exception {

		List<BuildYearMonthInstance> prjTotals = new ArrayList<>();
		BuildYearMonthInstance thePrjTotals = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int thePrj;
		
		
		try {
			//convert application id to int
			thePrj = Integer.parseInt(thePrjID);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected student
			String sql = "with temp as (select prj_id, app_id from prjs_apps_table where prj_id=?), " +
							"appinfo as (select * from app_info_table) " +
  
							"select sum(dev_num) devnumtotal, sum(wknum) wknumtotal, sum(hrs_num) hrsnumtotal, SUM(dev) as devstotal, SUM(dm) as dmtotal, SUM(teamlead) as teamleadtotal, SUM(smpm) as smpmtotal, SUM(pase) as pasetotal, SUM(dbas) as dbastotal, SUM(devtotal) as devtotals, SUM(dev) + SUM(dm) + SUM(teamlead) as devonly " +

							"from temp " +
							
							"left join appinfo " +
								"on temp.app_id = appinfo.app_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, thePrj);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				Double devnumtotal = myRs.getDouble("devnumtotal");
				Double wknumtotal = myRs.getDouble("wknumtotal");
				Double hrsnumtotal = myRs.getDouble("hrsnumtotal");
				Double devstotal = myRs.getDouble("devstotal");
				Double dmtotal = myRs.getDouble("dmtotal");
				Double teamleadtotal = myRs.getDouble("teamleadtotal");
				Double smpmtotal = myRs.getDouble("smpmtotal");
				Double pasetotal = myRs.getDouble("pasetotal");
				Double dbastotal = myRs.getDouble("dbastotal");
				Double devtotals = myRs.getDouble("devtotals");
				Double devonly = myRs.getDouble("devonly");
				 
				// use the applicationId during construction
				thePrjTotals = new BuildYearMonthInstance(thePrj, devnumtotal, wknumtotal, hrsnumtotal, devstotal, dmtotal, teamleadtotal, smpmtotal, pasetotal, dbastotal, devtotals, devonly);
				
				//add to forecasts
				prjTotals.add(thePrjTotals);	
			}				
			
			return prjTotals;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<GetTeamsInstance> getTeam(String thePrjID) throws Exception {
		
		List<GetTeamsInstance> team = new ArrayList<>();
		GetTeamsInstance theTeam = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int thePrj;
		
		
		try {
			//convert application id to int
			thePrj = Integer.parseInt(thePrjID);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected student
			String sql = "with prjinfo as (select distinct prj_id, emp_id, role_id from perculator_hours where prj_id=?), " +
							"empname as (select emp_id, emp_name from employee_table order by emp_name), " +
							"rolename as(select role_id, role_name  from prj_roles order by " +
							"case " + 
								"when role_name = 'Developer' then 1 " + 
								"when role_name = 'Team Lead' then 2 " +
								"when role_name = 'Delivery Manager' then 3 " +
								"when role_name = 'PM Scrum Master' then 4 " +
							"else 5 " +
							"end) " +

							"select empname.emp_name emprole " + 
							"from prjinfo " +
							"left join empname " +
							"on prjinfo.emp_id = empname.emp_id " +
							"left join rolename " +
							"on prjinfo.role_id = rolename.role_id";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, thePrj);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// process result set
			while (myRs.next()) {
				String empname = myRs.getString("emprole");
				 
				// use the applicationId during construction
				theTeam = new GetTeamsInstance(empname);
				
				//add to forecasts
				team.add(theTeam);	
			}				
			
			return team;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
}



