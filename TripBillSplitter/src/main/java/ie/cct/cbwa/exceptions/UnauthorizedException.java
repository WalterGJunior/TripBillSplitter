package ie.cct.cbwa.exceptions;

public class UnauthorizedException extends RuntimeException {
	

	public UnauthorizedException(String errorMessage) {
		super(errorMessage);
	}

	public UnauthorizedException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);

	}
	
	
	
		

}
