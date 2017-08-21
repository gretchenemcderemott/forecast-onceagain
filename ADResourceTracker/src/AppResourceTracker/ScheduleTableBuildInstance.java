package AppResourceTracker;

import java.util.ArrayList;
import java.util.List;

public class ScheduleTableBuildInstance {
	String emprole;

	public ScheduleTableBuildInstance() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ScheduleTableBuildInstance(String emprole) {
		super();
		this.emprole = emprole;
	}

	public String getEmprole() {
		return emprole;
	}

	public void setEmprole(String emprole) {
		this.emprole = emprole;
		
	}
	
	
	@Override
	public String toString() {
		return emprole;
	}
}
