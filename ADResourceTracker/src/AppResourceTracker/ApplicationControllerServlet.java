package AppResourceTracker;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class ApplicationControllerServlet
 */
@WebServlet("/ApplicationControllerServlet")
public class ApplicationControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ApplicationDbUtil applicationDbUtil;
	
	@Resource(name="jdbc/web_application_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our db util ... and pass in the conn pool / datasource
		try {
			applicationDbUtil = new ApplicationDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
    public ApplicationControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//list applications in MVC fashion
		
		try{
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			//if the command is missing, then default to listing application
			if(theCommand == null){
				theCommand = "LIST";
			}
			
			//route to the appropriate method
			switch(theCommand){
			
				case "LIST":
					listProjects(request, response);
				break;
				
				case "LOADPRJAPPS":
					loadPrjApps(request, response);
				break;
				
				case "LOADEXTENDEDLIST":
					loadExtendedList(request, response);
				break;
				
				case "LISTEMPSPRJS":
					listEmpsPrjs(request, response);
				break;
				
				case "LOADFUNC":
					loadFunc(request, response);
				break;
				
				case "LOADFUNCSCHED":
					loadFuncSched(request, response);
					break;
				
				case "LISTPRJSSCHED":
					listProjectsSched(request, response);
				break;
				
				default:
					listProjects(request, response);
			}
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	private void listProjects(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		
		List<ApplicationInstance> projects = applicationDbUtil.getProjects();
		
		//add projects to the request as an attribute
		request.setAttribute("projects", projects);
	
		//place projects in request attribute
		HttpSession session = request.getSession(true);
		
		//place projects in session;
		session.setAttribute("projects", projects);

		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/listapplications.jsp");
		dispatcher.forward(request, response);	
		
	}
	

	private void loadPrjApps(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//read emp and prj id from form data
		String thePrjId = request.getParameter("thePrjId");
		request.setAttribute("selectedModule", request.getParameter("thePrjId"));
		
		//get application from db
		List<ApplicationInstance> thePrjApps = applicationDbUtil.getPrjApps(thePrjId);
		
		//place application in request attribute
		//request.setAttribute("theForecast", theForecast);
		
		//place application in request attribute
		HttpSession session=request.getSession(false);
		session.setAttribute("thePrjApps", thePrjApps);
		
		loadTotals(request, response);
		
		//send to jsp page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/listapplications.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void loadTotals(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//read emp and prj id from form data
		String thePrjId = request.getParameter("thePrjId");
		request.setAttribute("selectedModule", request.getParameter("thePrjId"));
		
		//get application from db
		List<ApplicationInstance> theTotals = applicationDbUtil.getTotals(thePrjId);
		
		//place application in request attribute
		//request.setAttribute("theForecast", theForecast);
		
		//place application in request attribute
		HttpSession session=request.getSession(false);
		session.setAttribute("theTotals", theTotals);
		
		//send to jsp page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/listapplications.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void loadExtendedList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//read prj id from form data
		String theAppId = request.getParameter("theAppId");
		String thePrjId = request.getParameter("thePrjId");
		request.setAttribute("selectedModule", request.getParameter("thePrjId"));
		
		//get application from db
		ApplicationInstance theExtendedApps = applicationDbUtil.getExtendedApp(theAppId, thePrjId);
		
		//place application in request attribute
		//request.setAttribute("theExtendedApps", theExtendedApps);
		
		//place application in request attribute
		HttpSession session=request.getSession(false);
		session.setAttribute("theExtendedApps", theExtendedApps);
		
		
		//send to jsp page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/listapplications.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void listEmpsPrjs(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		// get applications from db util
		List<WeeklyForecastInstance> empsprjs = applicationDbUtil.getEmpsPrjs();

		//add applications to the request as an attribute
		request.setAttribute("empsprjs", empsprjs);

		//place application in request attribute
		HttpSession session = request.getSession(true);
		session.setAttribute("empsprjs", empsprjs);

		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WeeklyForecastByPerson.jsp");
		dispatcher.forward(request, response);	
	
	}
	
	private void loadFunc(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try{
			// read parameters
			String theEmpId = request.getParameter("theEmpId");
			String thePrjId = request.getParameter("thePrjId");
			String empsprj = null;
			
			//if the prj is missing, then default to listing emps
			if(theEmpId != null && thePrjId == null){
				empsprj = "LOADPRJS";
			} else if(theEmpId != null && thePrjId != null){
				empsprj = "LOADFORECAST";
			}
			
			//route to the appropriate method
			switch(empsprj){
				
				case "LOADPRJS":
					loadPrjs(request, response);
					break;
				
				case "LOADFORECAST":
					loadForecast(request, response);
					break;
					
				case "LOADFUNC":
					loadFunc(request, response);
					break;	
					
				case "LISTEMPSPRJS":
					listEmpsPrjs(request, response);
				break;
				
				default:
					listEmpsPrjs(request, response);
			}
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	private void loadPrjs(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//read emp and prj id from form data
		String theEmpId = request.getParameter("theEmpId");
		request.setAttribute("selectedModule", request.getParameter("theEmpId"));
		
		//get prjs from db
		List<WeeklyForecastInstance> thePrjs = applicationDbUtil.getPrjs(theEmpId);
		
		//place prjs in request attribute
		request.setAttribute("thePrjs", thePrjs);
		
		
		//place prjs in request attribute
		//HttpSession session=request.getSession(false);
		//session.setAttribute("thePrjs", thePrjs);
		
		//send to jsp page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WeeklyForecastByPerson.jsp");
		dispatcher.forward(request, response);
			
	}
	
	private void loadForecast(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//read emp and prj id from form data
		String theEmpId = request.getParameter("theEmpId");
		request.setAttribute("selectedModule", request.getParameter("theEmpId"));
		String thePrjId = request.getParameter("thePrjId");
		//request.setAttribute("selectedPrj", request.getParameter("thePrjId"));
		
		//get application from db
		List<WeeklyForecastInstance> theForecast = applicationDbUtil.getForecast(theEmpId, thePrjId);
		
		//place application in request attribute
		//request.setAttribute("theForecast", theForecast);
		
		//place application in request attribute
		HttpSession session=request.getSession(false);
		session.setAttribute("theForecast", theForecast);
		
		loadPrjs(request, response);
		//send to jsp page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WeeklyForecastByPerson.jsp");
		dispatcher.forward(request, response);	
		
	}
	
	//schedule tab
	//first query for top table view
	private void loadFirstQuery(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//read project name from form data
		String thePrjId = request.getParameter("thePrjId");
		request.setAttribute("selectedModule", request.getParameter("thePrjId"));
		String theAppId = request.getParameter("theAppId");
		
		//get application from db
		ScheduleTabInstance theFirstQ = applicationDbUtil.getFirstQ(thePrjId);
		
		//place application in request attribute
		//request.setAttribute("THE_FIRSTQ", theFirstQ);
		
		//place application in request attribute
		HttpSession session=request.getSession(false);
		session.setAttribute("THE_FIRSTQ", theFirstQ);
		
		loadSecondQ(request, response);
		//send to jsp page(use the following to send to a separate /update_application_form.jsp page)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/scheduletab.jsp");
		dispatcher.forward(request, response);	
		
	}	
	
	//second query for top table view
	private void loadSecondQ(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//read prj id from form data
		String thePrjId = request.getParameter("thePrjId");
		request.setAttribute("selectedModule", request.getParameter("thePrjId"));
		String theAppId = request.getParameter("theAppId");
		
		//get prjs from db
		List<ScheduleTabInstance> theSecondQ = applicationDbUtil.getSecondQ(thePrjId);
		
		//place prjs in request attribute
		//request.setAttribute("theSecondQ", theSecondQ);
		
		
		//place prjs in request attribute
		HttpSession session=request.getSession(false);
		session.setAttribute("theSecondQ", theSecondQ);
		
		loadThirdQ(request, response);
		//send to jsp page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/scheduletab.jsp");
		dispatcher.forward(request, response);
			
	}
	
	private void loadThirdQ(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//read project name from form data
		String thePrjId = request.getParameter("thePrjId");
		request.setAttribute("selectedModule", request.getParameter("thePrjId"));
		String theAppId = request.getParameter("theAppId");
		
		//get application from db
		ScheduleTabInstance theThirdQ = applicationDbUtil.getThirdQ(thePrjId);
		
		//place application in request attribute
		//request.setAttribute("THE_THIRDQ", theThirdQ);
		
		//place application in request attribute
		HttpSession session=request.getSession(false);
		session.setAttribute("THE_THIRDQ", theThirdQ);
		
		loadFourthQ(request, response);
		//send to jsp page(use the following to send to a separate /update_application_form.jsp page)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/scheduletab.jsp");
		dispatcher.forward(request, response);	
	}
		
		//fourth query from 1st table view
		private void loadFourthQ(HttpServletRequest request, HttpServletResponse response) throws Exception{
			//read project name from form data
			String thePrjId = request.getParameter("thePrjId");
			request.setAttribute("selectedModule", request.getParameter("thePrjId"));
			String theAppId = request.getParameter("theAppId");
			
			//get application from db
			ScheduleTabInstance theFourthQ = applicationDbUtil.getFourthQ(theAppId);
			
			//place application in request attribute
			//request.setAttribute("THE_FOURTHQ", theFourthQ);
			
			//place application in request attribute
			HttpSession session=request.getSession(false);
			session.setAttribute("THE_FOURTHQ", theFourthQ);
			
			loadColumns(request,response);
			//send to jsp page(use the following to send to a separate /update_application_form.jsp page)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/scheduletab.jsp");
			dispatcher.forward(request, response);	
		
	}
		
		
	//drop down list functions for schedule tab
		private void listProjectsSched(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
			
			List<ApplicationInstance> projectsSched = applicationDbUtil.getProjects();
			
			//add projects to the request as an attribute
			request.setAttribute("projectsSched", projectsSched);
		
			//place projects in request attribute
			HttpSession session = request.getSession(true);
			
			//place projects in session;
			session.setAttribute("projectsSched", projectsSched);

			// send to JSP page (view)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/scheduletab.jsp");
			dispatcher.forward(request, response);	
			
		}
		
		private void loadFuncSched(HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			try{
				// read parameters
				String thePrjId = request.getParameter("thePrjId");
				String theAppId = request.getParameter("theAppId");
				String appsprj = null;
				
				//if the app is missing, then default to listing prjs
				if(thePrjId != null && theAppId == null){
					appsprj = "LOADAPPS";
				} else if(theAppId != null && theAppId != null){
					appsprj = "LOADFIRSTQ";
				}
				
				//route to the appropriate method
				switch(appsprj){
					
					case "LOADFIRSTQ":
						loadFirstQuery(request, response);
						break;
					
					case "LOADFUNCSCHED":
						loadFuncSched(request, response);
						break;	
						
					case "LISTPRJSSCHED":
						listProjectsSched(request, response);
					break;
					
					case "LOADAPPS":
						loadApps(request, response);
					break;
					
					default:
						listProjectsSched(request, response);
				}
			}
			catch (Exception exc) {
				throw new ServletException(exc);
			}
		}
		
		private void loadApps(HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			//read emp and prj id from form data
			String thePrjId = request.getParameter("thePrjId");
			request.setAttribute("selectedModule", request.getParameter("thePrjId"));
			
			//get prjs from db
			List<ScheduleTabInstance> theApps = applicationDbUtil.getApps(thePrjId);
			
			//place prjs in request attribute
			request.setAttribute("theApps", theApps);
			
			
			//place prjs in request attribute
			//HttpSession session=request.getSession(false);
			//session.setAttribute("thePrjs", thePrjs);
			
			//send to jsp page
			RequestDispatcher dispatcher = request.getRequestDispatcher("/scheduletab.jsp");
			dispatcher.forward(request, response);
				
		}

		private void loadColumns(HttpServletRequest request, HttpServletResponse response) throws Exception{
			//read project name from form data
			String thePrjId = request.getParameter("thePrjId");
			request.setAttribute("selectedModule", request.getParameter("thePrjId"));
			String theAppId = request.getParameter("theAppId");
			
			//get prjs from db
			List<BuildYearMonthInstance> theMonths = applicationDbUtil.getMonths();
			List<BuildYearMonthInstance> thePercNums = applicationDbUtil.getPercnums(thePrjId);
			List<BuildYearMonthInstance> theResneeds = applicationDbUtil.getResneeds(thePrjId);
			List<CreateCalcRows> thePercNumsResource = applicationDbUtil.getPercnumsResource(thePrjId);
			List<BuildYearMonthInstance> theTotals = applicationDbUtil.getTotalsScheduleTab(thePrjId);
			List<GetTeamsInstance> theTeam = applicationDbUtil.getTeam(thePrjId);
			
			//column headers
			List<String> theTeamString = new ArrayList<String>();
			for (GetTeamsInstance x : theTeam) {
				theTeamString.add(x != null ? x.toString() : null);
			}
			    
			List<String> percHeader = new ArrayList<>();
			percHeader.add("PercNumofWeeks");
			
			List<String> calcHeaders = new ArrayList<>();
			calcHeaders.add("Pase");
			calcHeaders.add("DBA");
			calcHeaders.add("Dev Total");
			calcHeaders.add("DM Total");
			calcHeaders.add("Team Lead Total");
			calcHeaders.add("Scrum Master/PM Total");
			calcHeaders.add("Pase Total");
			calcHeaders.add("DBA Total");
			calcHeaders.add("Dev Only Total");
			calcHeaders.add("Org Total");
			calcHeaders.add("Total");
			
			List<String> columnHeaders = new ArrayList<>(percHeader.size() + theTeam.size() + calcHeaders.size());
			columnHeaders.addAll(percHeader);
			columnHeaders.addAll(theTeamString);
			columnHeaders.addAll(calcHeaders);
			
			//resource columns
			double[][] resneedsarray = new double[thePercNumsResource.size()][theResneeds.size()];
			double percnum;
			double resneeds;
			
			double devnumtotal = 0;
			double wknumtotal = 0;
			double devstotal = 0;
			double dmtotal = 0;
			double teamleadtotal = 0;
			double smpmtotal = 0;
			double pasetotal = 0;
			double dbastotal = 0;
			double resNeeds = 0;
			
			for(BuildYearMonthInstance x : theTotals){
				devnumtotal = x.devnumtotal;
				wknumtotal = x.wknumtotal;
				devstotal = x.devstotal;
				dmtotal = x.dmtotal;
				teamleadtotal = x.teamleadtotal;
				smpmtotal = x.smpmtotal;
				pasetotal = x.pasetotal;
				dbastotal = x.dbastotal;
			}
			
			for(int i=0; i<thePercNumsResource.size(); i++){
				percnum = thePercNumsResource.get(i).percnum;	
				for(int j=0; j<theResneeds.size(); j++){
					resneeds = theResneeds.get(j).resneeds;
					resNeeds = (((devstotal/wknumtotal)/devnumtotal)*percnum)*resneeds;
		    		  DecimalFormat resNeedsFormat = new DecimalFormat("###.#");
		    		  String resNeedsStr = resNeedsFormat.format(resNeeds);
		    		  double resneedsDbl = Double.parseDouble(resNeedsStr);
					resneedsarray[i][j] = resneedsDbl;
				}	

			}
			
			//teamlead column
			double theteamlead = 0;
			double[] theTeamLead = new double[thePercNumsResource.size()];
			for(int i=0; i<thePercNumsResource.size(); i++){
				percnum = thePercNumsResource.get(i).percnum;
				theteamlead = ((teamleadtotal/wknumtotal)/devnumtotal) * percnum;
	    		  DecimalFormat teamleadFormat = new DecimalFormat("###.#");
	    		  String teamleadStr = teamleadFormat.format(theteamlead);
	    		  double teamleadDbl = Double.parseDouble(teamleadStr);
				theTeamLead[i] = teamleadDbl;
			}
			
			//dm column
			double thedm;
			double[] theDM = new double[thePercNumsResource.size()];
			for(int i=0; i<thePercNumsResource.size(); i++){
				percnum = thePercNumsResource.get(i).percnum;
				thedm = ((dmtotal/wknumtotal) * percnum)/2;
			    		  DecimalFormat thedmFormat = new DecimalFormat("###.#");
			    		  String thedmStr = thedmFormat.format(thedm);
			    		  double thedmDbl = Double.parseDouble(thedmStr);
				theDM[i] = thedmDbl;
			}
			
			//smpm column
			double thesmpm;
			double[] theSMPM = new double[thePercNumsResource.size()];
			for(int i=0; i<thePercNumsResource.size(); i++){
				percnum = thePercNumsResource.get(i).percnum;
				thesmpm = ((smpmtotal/wknumtotal) * percnum);
	    		  DecimalFormat thesmpmFormat = new DecimalFormat("###.#");
	    		  String thesmpmStr = thesmpmFormat.format(thesmpm);
	    		  double thesmpmDbl = Double.parseDouble(thesmpmStr);
				theSMPM[i] = thesmpmDbl;
			}
			
			//pase column
			double thepase;
			double[] thePase = new double[thePercNumsResource.size()];
			for(int i=0; i<thePercNumsResource.size(); i++){
				percnum = thePercNumsResource.get(i).percnum;
				thepase = ((pasetotal/wknumtotal) * percnum);
	    		  DecimalFormat thepaseFormat = new DecimalFormat("###.#");
	    		  String thepaseStr = thepaseFormat.format(thepase);
	    		  double thepaseDbl = Double.parseDouble(thepaseStr);
				thePase[i] = thepaseDbl;
			}
			
			//dba column
			double thedba = 0;
			double[] theDBA = new double[thePercNumsResource.size()];
			for(int i=0; i<thePercNumsResource.size(); i++){
				percnum = thePercNumsResource.get(i).percnum;
				thedba = ((dbastotal/wknumtotal) * percnum);
	    		  DecimalFormat thedbaFormat = new DecimalFormat("###.#");
	    		  String thedbaStr = thedbaFormat.format(thedba);
	    		  double thedbaDbl = Double.parseDouble(thedbaStr);
				theDBA[i] = thedbaDbl;
			}
			
			//devtotal column
			int sumrows;
			double[] theDevsTotal = new double[thePercNumsResource.size()];
			for (int i = 0; i < thePercNumsResource.size(); i++){   
				sumrows=0;
				for (int j = 0; j < resneedsarray[0].length; j++){                
					sumrows += resneedsarray[i][j]; 
		    		  DecimalFormat sumrowsFormat = new DecimalFormat("###.#");
		    		  String sumrowsStr = sumrowsFormat.format(sumrows);
		    		  double sumrowsDbl = Double.parseDouble(sumrowsStr);
					theDevsTotal[i] = sumrowsDbl;
				}
	        } 
			
			//dmtotal column
			double dmrow;
			double[] theDMTotal = new double[thePercNumsResource.size()];
			for (int i = 0; i < thePercNumsResource.size(); i++){   
				dmrow = theDM[i];
	    		  DecimalFormat dmrowFormat = new DecimalFormat("###.#");
	    		  String dmrowStr = dmrowFormat.format(dmrow);
	    		  double dmrowDbl = Double.parseDouble(dmrowStr);
				theDMTotal[i] = dmrowDbl;
	        }
			
			//teamleadtotal column
			double teamleadtotalrow;
			double[] theTeamLeadTotal = new double[thePercNumsResource.size()];
			for (int i = 0; i < thePercNumsResource.size(); i++){   
				teamleadtotalrow = theTeamLead[i];
	    		  DecimalFormat teamleadtotalrowFormat = new DecimalFormat("###.#");
	    		  String teamleadtotalrowStr = teamleadtotalrowFormat.format(teamleadtotalrow);
	    		  double teamleadtotalrowDbl = Double.parseDouble(teamleadtotalrowStr);
				theTeamLeadTotal[i] = teamleadtotalrowDbl;
	        }
			
			//smpmtotal column
			double smpmtotalrow;
			double[] theSMPMTotal = new double[thePercNumsResource.size()];
			for (int i = 0; i < thePercNumsResource.size(); i++){   
				smpmtotalrow = theSMPM[i];
	    		  DecimalFormat smpmtotalrowFormat = new DecimalFormat("###.#");
	    		  String smpmtotalrowStr = smpmtotalrowFormat.format(smpmtotalrow);
	    		  double smpmtotalrowDbl = Double.parseDouble(smpmtotalrowStr);
				theSMPMTotal[i] = smpmtotalrowDbl;
	        }
			
			//pasetotal column
			double pasetotalrow;
			double[] thePaseTotal = new double[thePercNumsResource.size()];
			for (int i = 0; i < thePercNumsResource.size(); i++){   
				pasetotalrow = thePase[i];
	    		  DecimalFormat pasetotalrowFormat = new DecimalFormat("###.#");
	    		  String pasetotalrowStr = pasetotalrowFormat.format(pasetotalrow);
	    		  double pasetotalrowDbl = Double.parseDouble(pasetotalrowStr);
				thePaseTotal[i] = pasetotalrowDbl;
	        }
			
			//dbatotal column
			double dbatotalrow;
			double[] theDBATotal = new double[thePercNumsResource.size()];
			for (int i = 0; i < thePercNumsResource.size(); i++){   
				dbatotalrow = theDBA[i];
	    		  DecimalFormat dbatotalrowFormat = new DecimalFormat("###.#");
	    		  String dbatotalrowStr = dbatotalrowFormat.format(dbatotalrow);
	    		  double dbatotalrowDbl = Double.parseDouble(dbatotalrowStr);
				theDBATotal[i] = dbatotalrowDbl;
	        }
			
			//devonlytotal column
			double devonlytotal;
			double dev_devtotal;
			double dev_dmtotal;
			double dev_teamleadtotal;
			double[] theDevOnlyTotal = new double[thePercNumsResource.size()];
			for (int i = 0; i < thePercNumsResource.size(); i++){   
				dev_devtotal = theDevsTotal[i];
				dev_dmtotal = theDMTotal[i];
				dev_teamleadtotal = theTeamLeadTotal[i];
				devonlytotal = dev_devtotal + dev_dmtotal + dev_teamleadtotal;
	    		  DecimalFormat devonlytotalFormat = new DecimalFormat("###.#");
	    		  String devonlytotalStr = devonlytotalFormat.format(devonlytotal);
	    		  double devonlytotalDbl = Double.parseDouble(devonlytotalStr);
				theDevOnlyTotal[i] = devonlytotalDbl;
	        }
			
			//orgtotal column
			double orgtotal;
			double org_devtotal;
			double org_dmtotal;
			double org_teamleadtotal;
			double[] theOrgTotal = new double[thePercNumsResource.size()];
			for (int i = 0; i < thePercNumsResource.size(); i++){   
				org_devtotal = theDevsTotal[i];
				org_dmtotal = theDMTotal[i];
				org_teamleadtotal = theTeamLeadTotal[i];
				orgtotal = org_devtotal + org_dmtotal + org_teamleadtotal;
	    		  DecimalFormat orgtotalFormat = new DecimalFormat("###.#");
	    		  String orgtotalStr = orgtotalFormat.format(orgtotal);
	    		  double orgtotalDbl = Double.parseDouble(orgtotalStr);
				theOrgTotal[i] = orgtotalDbl;
	        }
			
			//totalcol column
			double totalcol;
			double tc_devtotal;
			double tc_dmtotal;
			double tc_teamleadtotal;
			double tc_smpmtotal;
			double tc_pasetotal;
			double tc_dbatotal;
			double[] theTotalCol = new double[thePercNumsResource.size()];
			for (int i = 0; i < thePercNumsResource.size(); i++){   
				tc_devtotal = theDevsTotal[i];
				tc_dmtotal = theDMTotal[i];
				tc_teamleadtotal = theTeamLeadTotal[i];
				tc_smpmtotal = theSMPMTotal[i];
				tc_pasetotal = thePaseTotal[i];
				tc_dbatotal = theDBATotal[i];
				totalcol = tc_devtotal + tc_dmtotal + tc_teamleadtotal + tc_smpmtotal + tc_pasetotal + tc_dbatotal;
	    		  DecimalFormat totalcolFormat = new DecimalFormat("###.#");
	    		  String totalcolStr = totalcolFormat.format(totalcol);
	    		  double totalcolDbl = Double.parseDouble(totalcolStr);
				theTotalCol[i] = totalcolDbl;
	        }
			
			
			//totals row
	        double[] totalResneedsArray = new double[resneedsarray[0].length];
	        double sum=0;
	        for(int j=0; j<resneedsarray[0].length; j++){
	            for(int i=0; i<resneedsarray.length; i++){
	                sum+=resneedsarray[i][j]; 
	            }
	            totalResneedsArray[j] = sum;
	            sum=0;
	        }
	        
	        class GetTotals{
	        	double getColumntotalArray(double[] array){
	        		double sumarray = 0;
	        		for(double x : array){
	        			sumarray += x;
	        		}
	        		
		    		  DecimalFormat sumarrayFormat = new DecimalFormat("###.#");
		    		  String sumarrayStr = sumarrayFormat.format(sumarray);
		    		  double sumarrayDbl = Double.parseDouble(sumarrayStr);
		    		  
	        		return sumarrayDbl;
	        	}
	        }
	        
	    	GetTotals obj = new GetTotals();
	    		double sumteamlead = obj.getColumntotalArray(theTeamLead);
	    	GetTotals obj1 = new GetTotals();
	    		double sumdm = obj1.getColumntotalArray(theDM);
	    	GetTotals obj2 = new GetTotals();
	    		double sumsmpm = obj2.getColumntotalArray(theSMPM);
	    	GetTotals obj3 = new GetTotals();
    			double sumpase = obj3.getColumntotalArray(thePase);
	    	GetTotals obj4 = new GetTotals();
    			double sumdba = obj4.getColumntotalArray(theDBA);
	    	GetTotals obj5 = new GetTotals();
    			double sumdevstotal = obj5.getColumntotalArray(theDevsTotal);
	    	GetTotals obj6 = new GetTotals();
    			double sumdmtotal = obj6.getColumntotalArray(theDMTotal);
	    	GetTotals obj7 = new GetTotals();
    			double sumteamleadtotal = obj7.getColumntotalArray(theTeamLeadTotal);
	    	GetTotals obj8 = new GetTotals();
    			double sumsmpmtotal = obj8.getColumntotalArray(theSMPMTotal);
	    	GetTotals obj9 = new GetTotals();
    			double sumpasetotal = obj9.getColumntotalArray(thePaseTotal);
	    	GetTotals obj10 = new GetTotals();
    			double sumdbatotal = obj10.getColumntotalArray(theDBATotal);
	    	GetTotals obj11 = new GetTotals();
    			double sumdevonlytotal = obj11.getColumntotalArray(theDevOnlyTotal);
	    	GetTotals obj12 = new GetTotals();
    			double sumorgtotal = obj12.getColumntotalArray(theOrgTotal);
	    	GetTotals obj13 = new GetTotals();
    			double sumtotalcol = obj13.getColumntotalArray(theTotalCol);
    			
    		double[] totalsrow = new double[14];
    			totalsrow[0] = sumteamlead;
    			totalsrow[1] = sumdm;
    			totalsrow[2] = sumsmpm;
    			totalsrow[3] = sumpase;
    			totalsrow[4] = sumdba;
    			totalsrow[5] = sumdevstotal;
    			totalsrow[6] = sumdmtotal;
    			totalsrow[7] = sumteamleadtotal;
    			totalsrow[8] = sumsmpmtotal;
    			totalsrow[9] = sumpasetotal;
    			totalsrow[10] = sumdbatotal;
    			totalsrow[11] = sumdevonlytotal;
    			totalsrow[12] = sumorgtotal;
    			totalsrow[13] = sumtotalcol;
    			
    		      double[] fulltotalrow = new double[totalResneedsArray.length + totalsrow.length];
    		      int count = 0;
    		      
    		      for(int i = 0; i<totalResneedsArray.length; i++) { 
    		    	  fulltotalrow[i] = totalResneedsArray[i];
    		         count++;
    		      } 
    		      for(int j=0; j<totalsrow.length; j++) { 
    		    	  fulltotalrow[count++] = totalsrow[j];
    		      } 
    		      
    		 
	    	GetTotals obj14 = new GetTotals();
				double sumresneedsarray = obj14.getColumntotalArray(totalResneedsArray);
				double devorgtotal = sumresneedsarray + sumteamlead + sumdm;
    			
			
			HttpSession session=request.getSession(false);
			
			//place prjs in request attribute
			session.setAttribute("theMonths", theMonths);
			session.setAttribute("thePercNums", thePercNums);
			session.setAttribute("resneedsarray", resneedsarray);
			session.setAttribute("theTeamLead", theTeamLead);
			session.setAttribute("theDM", theDM);
			session.setAttribute("theSMPM", theSMPM);
			session.setAttribute("thePase", thePase);
			session.setAttribute("theDBA", theDBA);
			session.setAttribute("theDevsTotal", theDevsTotal); 
			session.setAttribute("theDMTotal", theDMTotal);
			session.setAttribute("theTeamLeadTotal", theTeamLeadTotal);
			session.setAttribute("theSMPMTotal", theSMPMTotal);
			session.setAttribute("thePaseTotal", thePaseTotal);
			session.setAttribute("theDBATotal", theDBATotal); 
			session.setAttribute("theDevOnlyTotal", theDevOnlyTotal);          
			session.setAttribute("theOrgTotal", theOrgTotal); 
			session.setAttribute("theTotalCol", theTotalCol);   
			session.setAttribute("columnHeaders", columnHeaders); 
			session.setAttribute("fulltotalrow", fulltotalrow); 
			session.setAttribute("DEVORGTOTAL", devorgtotal);
			
			loadApps(request,response);
			
			//send to jsp page
			RequestDispatcher dispatcher = request.getRequestDispatcher("/scheduletab.jsp");
			dispatcher.forward(request, response);
				
		}
		


}
