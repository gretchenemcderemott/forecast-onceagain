package AppResourceTracker;

import java.text.DecimalFormat;

public class ApplicationInstance {
	
	private int appid;
	private String appname;
	private int prjid;
	private String prjname;
	private int devnum;
	private int wknum;
	private int hrsnum;
	private int dev;
	private int dm;
	private int teamlead;
	private int smpm;
	private int pase;
	private int dbas;
	private int devtotal;
	
	private int devnumtotal;
	private int wknumtotal;
	private int hrsnumtotal;
	private int devstotal;
	private int dmtotal;
	private int teamleadtotal;
	private int smpmtotal;
	private int pasetotal;
	private int dbastotal;
	private int devtotals;
	private int devonly;
	
	private String impdb;
	private String details;
	private String benefits; 
	private String tsdate;
	private String tedate;
	
	
	public ApplicationInstance() {
		
	}
	
	public ApplicationInstance(int prjid, String prjname) {
		this.prjid = prjid;
		this.prjname = prjname;
		
	}
	
	//inital table on listapplications.jsp page
	public ApplicationInstance(int prjid, int appid, String appname, int devnum, int wknum, int hrsnum, int dev, int dm, int teamlead, int smpm, int pase, int dbas, int devtotal){
		this.prjid = prjid;
		this.appid = appid;
		this.appname = appname;
		this.devnum = devnum;
		this.wknum = wknum;
		this.hrsnum = hrsnum;
		this.dev = dev;
		this.dm = dm;
		this.teamlead = teamlead;
		this.smpm = smpm;
		this.pase = pase;
		this.dbas = dbas;
		this.devtotal = devtotal;
	}
	
	//totals row on listapplications.jsp
	public ApplicationInstance(int prjid, int devnumtotal, int wknumtotal, int hrsnumtotal, int devstotal, int dmtotal, int teamleadtotal, int smpmtotal, int pasetotal, int dbastotal, int devtotals, int devonly){
		this.prjid = prjid;
		this.devnumtotal = devnumtotal;
		this.wknumtotal = wknumtotal;
		this.hrsnumtotal = hrsnumtotal;
		this.devstotal = devstotal;
		this.dmtotal = dmtotal;
		this.teamleadtotal = teamleadtotal;
		this.smpmtotal = smpmtotal;
		this.pasetotal = pasetotal;
		this.dbastotal = dbastotal;
		this.devtotals = devtotals;
		this.devonly = devonly;
	}
	
	public ApplicationInstance(int appid, int prjid, String prjname, String impdb, String details, String benefits, String tsdate, String tedate){
		this.appid = appid;
		this.prjid = prjid;
		this.prjname = prjname;
		this.appid = appid;
		this.impdb = impdb;
		this.details = details;
		this.benefits = benefits;
		this.tsdate = tsdate;
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

	public int getPrjid() {
		return prjid;
	}

	public void setPrjid(int prjid) {
		this.prjid = prjid;
	}

	public String getPrjname() {
		return prjname;
	}

	public void setPrjname(String prjname) {
		this.prjname = prjname;
	}

	public int getDevnum() {
		return devnum;
	}

	public void setDevnum(int devnum) {
		this.devnum = devnum;
	}

	public int getWknum() {
		return wknum;
	}

	public void setWknum(int wknum) {
		this.wknum = wknum;
	}

	public int getHrsnum() {
		return hrsnum;
	}

	public void setHrsnum(int hrsnum) {
		this.hrsnum = hrsnum;
	}

	public int getDev() {
		return dev;
	}

	public void setDev(int dev) {
		this.dev = dev;
	}

	public int getDm() {
		return dm;
	}

	public void setDm(int dm) {
		this.dm = dm;
	}

	public int getTeamlead() {
		return teamlead;
	}

	public void setTeamlead(int teamlead) {
		this.teamlead = teamlead;
	}

	public int getSmpm() {
		return smpm;
	}

	public void setSmpm(int smpm) {
		this.smpm = smpm;
	}

	public int getPase() {
		return pase;
	}

	public void setPase(int pase) {
		this.pase = pase;
	}

	public int getDbas() {
		return dbas;
	}

	public void setDbas(int dbas) {
		this.dbas = dbas;
	}

	public int getDevtotal() {
		return devtotal;
	}

	public void setDevtotal(int devtotal) {
		this.devtotal = devtotal;
	}

	public int getDevstotal() {
		return devstotal;
	}

	public void setDevstotal(int devstotal) {
		this.devstotal = devstotal;
	}

	public int getDmtotal() {
		return dmtotal;
	}

	public void setDmtotal(int dmtotal) {
		this.dmtotal = dmtotal;
	}

	public int getTeamleadtotal() {
		return teamleadtotal;
	}

	public void setTeamleadtotal(int teamleadtotal) {
		this.teamleadtotal = teamleadtotal;
	}

	public int getSmpmtotal() {
		return smpmtotal;
	}

	public void setSmpmtotal(int smpmtotal) {
		this.smpmtotal = smpmtotal;
	}

	public int getPasetotal() {
		return pasetotal;
	}

	public void setPasetotal(int pasetotal) {
		this.pasetotal = pasetotal;
	}

	public int getDbastotal() {
		return dbastotal;
	}

	public void setDbastotal(int dbastotal) {
		this.dbastotal = dbastotal;
	}

	public int getDevtotals() {
		return devtotals;
	}

	public void setDevtotals(int devtotals) {
		this.devtotals = devtotals;
	}

	public int getDevonly() {
		return devonly;
	}

	public void setDevonly(int devonly) {
		this.devonly = devonly;
	}

	public String getImpdb() {
		return impdb;
	}

	public void setImpdb(String impdata) {
		this.impdb = impdb;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getBenefits() {
		return benefits;
	}

	public void setBenefits(String benefits) {
		this.benefits = benefits;
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

	public int getDevnumtotal() {
		return devnumtotal;
	}

	public void setDevnumtotal(int devnumtotal) {
		this.devnumtotal = devnumtotal;
	}

	public int getWknumtotal() {
		return wknumtotal;
	}

	public void setWknumtotal(int wknumtotal) {
		this.wknumtotal = wknumtotal;
	}

	public int getHrsnumtotal() {
		return hrsnumtotal;
	}

	public void setHrsnumtotal(int hrsnumtotal) {
		this.hrsnumtotal = hrsnumtotal;
	}




	
}
