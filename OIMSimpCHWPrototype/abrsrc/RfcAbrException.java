package COM.ibm.eannounce.abr.sg.rfc;

public class RfcAbrException extends Exception {
	
	private static final long serialVersionUID = 8377162653668322905L;

	public RfcAbrException() {
		super();
	}

	public RfcAbrException(String message) {
		super(message);
	}

	public RfcAbrException(String message, Throwable cause) {
		super(message, cause);
	}

	public RfcAbrException(Throwable cause) {
		super(cause);
	}
	
}
