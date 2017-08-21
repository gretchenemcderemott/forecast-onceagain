package AppResourceTracker;

import java.util.ArrayList;
import java.util.List;

public class CreateCalcRows {
	double percnum;
	double resource;
	int type;
	String empname;

	public CreateCalcRows() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateCalcRows(double percnum) {
		super();
		this.percnum = percnum;
	}

	public CreateCalcRows(double resource, int type) {
		super();
		this.resource = resource;
		this.type = type;
	}

	public CreateCalcRows(String empname) {
		super();
		this.empname = empname;
	}

	public double getPercnum() {
		return percnum;
	}

	public void setPercnum(double percnum) {
		this.percnum = percnum;
	}

	public double getResource() {
		return resource;
	}

	public void setResource(double resource) {
		this.resource = resource;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String toString() {
		return percnum + empname + " ";
	}


}
