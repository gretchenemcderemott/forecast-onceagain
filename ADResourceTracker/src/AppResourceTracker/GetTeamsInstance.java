package AppResourceTracker;

public class GetTeamsInstance {
	String empname;

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public GetTeamsInstance(String empname) {
		super();
		this.empname = empname;
	}

	public GetTeamsInstance() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return empname;
	}
	

}
