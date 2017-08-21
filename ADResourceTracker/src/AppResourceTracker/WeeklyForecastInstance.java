package AppResourceTracker;

public class WeeklyForecastInstance {
	
	private int empid;
	private int prjid;
	private String empname;
	private String prjname;
	private int prjhrs;
	private String eowdate;
	private String eowmonth;
	private int prjtotalhrs;
	
	public WeeklyForecastInstance(){
		
	}
	
	public WeeklyForecastInstance(int prjid, String prjname){
		this.prjid = prjid;
		this.prjname = prjname;
		
	}

	public WeeklyForecastInstance(int empid, String empname, int prjid, String prjname) {
		super();
		this.empid = empid;
		this.empname = empname;
		this.prjid = prjid;
		this.prjname = prjname;
	}
	
	public WeeklyForecastInstance(String empname, String prjname, String eowdate, int prjhrs, String eowmonth, int prjtotalhrs) {
		super();
		this.empname = empname;
		this.prjname = prjname;
		this.eowdate = eowdate;
		this.prjhrs = prjhrs;
		this.eowmonth = eowmonth;
		this.prjtotalhrs = prjtotalhrs;
	}
	
	public WeeklyForecastInstance(int empid, int prjid, String empname, String prjname, String eowdate, int prjhrs, String eowmonth, int prjtotalhrs) {
		super();
		this.empname = empname;
		this.prjname = prjname;
		this.eowdate = eowdate;
		this.prjhrs = prjhrs;
		this.eowmonth = eowmonth;
		this.prjtotalhrs = prjtotalhrs;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public int getPrjid() {
		return prjid;
	}

	public void setPrjid(int prjid) {
		this.prjid = prjid;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getPrjname() {
		return prjname;
	}

	public void setPrjname(String prjname) {
		this.prjname = prjname;
	}

	public double getPrjhrs() {
		return prjhrs;
	}

	public void setPrjhrs(int prjhrs) {
		this.prjhrs = prjhrs;
	}

	public int getPrjtotalhrs() {
		return prjtotalhrs;
	}

	public void setPrjtotalhrs(int prjtotalhrs) {
		this.prjtotalhrs = prjtotalhrs;
	}

	public String getEowdate() {
		return eowdate;
	}

	public void setEowdate(String eowdate) {
		this.eowdate = eowdate;
	}

	public String getEowmonth() {
		return eowmonth;
	}

	public void setEowmonth(String eowmonth) {
		this.eowmonth = eowmonth;
	}

	@Override
	public String toString() {
		return "Forcast [empid=" + empid + ", prjid=" + prjid + ", empname=" + empname + ", prjname=" + prjname + ", prjhrs=" + prjhrs + ", eowdate=" + eowdate + ", eowmonth=" + eowmonth + ", prjtotalhrs=" + prjtotalhrs + "]";
	}

}
