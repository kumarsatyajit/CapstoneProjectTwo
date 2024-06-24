package automation.UserExceptions;

@SuppressWarnings("serial")
public class InvalidBrowserException extends Exception {

	public InvalidBrowserException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidBrowserException(String message) {
		super(message);
	}

}
