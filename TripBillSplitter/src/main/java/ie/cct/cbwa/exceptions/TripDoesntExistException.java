package ie.cct.cbwa.exceptions;

public class TripDoesntExistException extends RuntimeException {
	

	public TripDoesntExistException(String errorMessage) {
		super(errorMessage);
	}

	public TripDoesntExistException (String errorMessage, Throwable cause) {
		super(errorMessage, cause);

	}
	

}