package AppResourceTracker;

import java.util.ArrayList;

public class TableHeaders {
	ArrayList<String> headers;

	public ArrayList<String> getHeaders() {
		return headers;
	}

	public void setHeaders(ArrayList<String> headers) {
		this.headers = headers;
	}

	public TableHeaders(ArrayList<String> mainHeaders) {
		super();
		this.headers = mainHeaders;
	}

}
