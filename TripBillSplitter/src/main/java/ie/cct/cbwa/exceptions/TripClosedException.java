package ie.cct.cbwa.exceptions;

public class TripClosedException extends RuntimeException {
	

	public TripClosedException(String errorMessage) {
		super(errorMessage);
	}

	public TripClosedException (String errorMessage, Throwable cause) {
		super(errorMessage, cause);

	}
	

}
