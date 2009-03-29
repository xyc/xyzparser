package parser;
public class ErrorMessage {
	private int errorLine,errorColumn;
	private String errorMessage;
	
	public ErrorMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorMessage(int errorLine, int errorColumn, String errorMessage) {
		super();
		this.errorLine = errorLine;
		this.errorColumn = errorColumn;
		this.errorMessage = errorMessage;
	}

	public int getErrorLine() {
		return errorLine;
	}

	public void setErrorLine(int errorLine) {
		this.errorLine = errorLine;
	}

	public int getErrorColumn() {
		return errorColumn;
	}

	public void setErrorColumn(int errorColumn) {
		this.errorColumn = errorColumn;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
