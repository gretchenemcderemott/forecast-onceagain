package AppResourceTracker;

import java.util.Collection;

public class BuildYearMonthInstance {
	String yearmonth;
	int percnum;
	double resneeds;
	
	double devnumtotal; 
	double wknumtotal;
	double hrsnumtotal;
	double devstotal;
	double dmtotal;
	double teamleadtotal;
	double smpmtotal;
	double pasetotal;
	double dbastotal;
	double devtotals;
	double devonly;
	
	
	
	public double getResneeds() {
		return resneeds;
	}

	public void setResneeds(double resneeds) {
		this.resneeds = resneeds;
	}

	public BuildYearMonthInstance() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BuildYearMonthInstance(String yearmonth) {
		super();
		this.yearmonth = yearmonth;
	}
	
	public BuildYearMonthInstance(int percnum) {
		super();
		this.percnum = percnum;
	}
	
	public BuildYearMonthInstance(double resneeds) {
		super();
		this.resneeds = resneeds;
	}

	public BuildYearMonthInstance(int percnum, double resneeds) {
		super();
		this.percnum = percnum;
		this.resneeds = resneeds;
	}

	public BuildYearMonthInstance(double resneeds, double devnumtotal, double wknumtotal, double hrsnumtotal,
			double devstotal, double dmtotal, double teamleadtotal, double smpmtotal, double pasetotal,
			double dbastotal, double devtotals, double devonly) {
		super();
		this.resneeds = resneeds;
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

	public String getYearmonth() {
		return yearmonth;
	}

	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
	}
	
	public int getPercnum() {
		return percnum;
	}

	public void setPercnum(int percnum) {
		this.percnum = percnum;
	}

	public double getDevnumtotal() {
		return devnumtotal;
	}

	public void setDevnumtotal(double devnumtotal) {
		this.devnumtotal = devnumtotal;
	}

	public double getWknumtotal() {
		return wknumtotal;
	}

	public void setWknumtotal(double wknumtotal) {
		this.wknumtotal = wknumtotal;
	}

	public double getHrsnumtotal() {
		return hrsnumtotal;
	}

	public void setHrsnumtotal(double hrsnumtotal) {
		this.hrsnumtotal = hrsnumtotal;
	}

	public double getDevstotal() {
		return devstotal;
	}

	public void setDevstotal(double devstotal) {
		this.devstotal = devstotal;
	}

	public double getDmtotal() {
		return dmtotal;
	}

	public void setDmtotal(double dmtotal) {
		this.dmtotal = dmtotal;
	}

	public double getTeamleadtotal() {
		return teamleadtotal;
	}

	public void setTeamleadtotal(double teamleadtotal) {
		this.teamleadtotal = teamleadtotal;
	}

	public double getSmpmtotal() {
		return smpmtotal;
	}

	public void setSmpmtotal(double smpmtotal) {
		this.smpmtotal = smpmtotal;
	}

	public double getPasetotal() {
		return pasetotal;
	}

	public void setPasetotal(double pasetotal) {
		this.pasetotal = pasetotal;
	}

	public double getDbastotal() {
		return dbastotal;
	}

	public void setDbastotal(double dbastotal) {
		this.dbastotal = dbastotal;
	}

	public double getDevtotals() {
		return devtotals;
	}

	public void setDevtotals(double devtotals) {
		this.devtotals = devtotals;
	}

	public double getDevonly() {
		return devonly;
	}

	public void setDevonly(double devonly) {
		this.devonly = devonly;
	}

	public String toString() {
		return yearmonth + percnum + resneeds;
	}
	

}
