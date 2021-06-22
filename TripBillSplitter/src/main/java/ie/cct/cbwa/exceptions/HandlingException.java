package ie.cct.cbwa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class HandlingException {

	/*
	 * In this class I am handling the exceptions
	 * 
	 *  USER not found
	 *  
	 *  Trip closed
	 *  
	 *  Trip doesn't exist
	 * 
	 * */
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e){
		
		return new ResponseEntity<String>("User or Password is incorrect, Please Try again!", HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(TripClosedException.class)
	public ResponseEntity<String> handleTripClosedException(TripClosedException e){
		
		return new ResponseEntity<String>("Trip is closed, and can not be added more expense", HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(TripDoesntExistException.class)
	public ResponseEntity<String> handleTripDoesntExistException(TripDoesntExistException e){
		
		return new ResponseEntity<String>("Trip doesn't exist", HttpStatus.UNAUTHORIZED);
	}
	
}
