package AppResourceTracker;

public class ScheduleTabInstance {
	String prjname;
	
	String empname;
	Double resneed;
	
	int wknumt;
	int hrsnumt;
	
	String tsdate;
	String tedate;
	
	int appid;
	String appname;
	

	public ScheduleTabInstance() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ScheduleTabInstance(String prjname) {
		super();
		this.prjname = prjname;
	}
	
	public ScheduleTabInstance(String empname, Double resneed) {
		super();
		this.empname = empname;
		this.resneed = resneed;
	}

	public ScheduleTabInstance(int wknumt, int hrsnumt) {
		super();
		this.wknumt = wknumt;
		this.hrsnumt = hrsnumt;
	}

	public ScheduleTabInstance(String tsdate, String tedate) {
		super();
		this.tsdate = tsdate;
		this.tedate = tedate;
	}

	public ScheduleTabInstance(int appid, String appname) {
		super();
		this.appid = appid;
		this.appname = appname;
	}

	public String getPrjname() {
		return prjname;
	}

	public void setPrjname(String prjname) {
		this.prjname = prjname;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public Double getResneed() {
		return resneed;
	}

	public void setResneed(Double resneed) {
		this.resneed = resneed;
	}

	public int getWknumt() {
		return wknumt;
	}

	public void setWknumt(int wknumt) {
		this.wknumt = wknumt;
	}

	public int getHrsnumt() {
		return hrsnumt;
	}

	public void setHrsnumt(int hrsnumt) {
		this.hrsnumt = hrsnumt;
	}

	public String getTsdate() {
		return tsdate;
	}

	public void setTsdate(String tsdate) {
		this.tsdate = tsdate;
	}

	public String getTedate() {
		return tedate;
	}

	public void setTedate(String tedate) {
		this.tedate = tedate;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}




}
