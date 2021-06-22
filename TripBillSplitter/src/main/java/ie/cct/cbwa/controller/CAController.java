package ie.cct.cbwa.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ie.cct.cbwa.Util.JWTIssuer;
import ie.cct.cbwa.dto.ExpenseResponse;
import ie.cct.cbwa.dto.SummaryResponse;
import ie.cct.cbwa.exceptions.TripClosedException;
import ie.cct.cbwa.exceptions.TripDoesntExistException;
import ie.cct.cbwa.exceptions.UnauthorizedException;
import ie.cct.cbwa.model.Expense;
import ie.cct.cbwa.model.Trip;
import ie.cct.cbwa.model.Users;
import io.jsonwebtoken.Claims;

@RestController
public class CAController {

	private Map<String, Trip> trips;
	
	private List<Users> users = List.of( // List of users
			new Users("Amilcar", "pass111"),
			new Users("David", "pass222"),
			new Users("Greg", "pass333"));

	public CAController() {
		trips = new HashMap<>();
	}

	// Login
	@GetMapping("/login")
	@CrossOrigin(origins="*")//Method to validate the login
	public String login(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password) {
		
		return users.stream() // Veryfing if the user exist in the List of user
				.filter(u -> u.getUsername().equalsIgnoreCase(username)
						&& u.getPassword().equalsIgnoreCase(password))
				//if the user exist it will generate a TOKEN
				.map(u -> JWTIssuer.createJWT(username, "TripBillSplitter", username, 86400000))
				.findFirst()// otherwise it will throw an Exception 401
				.orElseThrow(() -> new UnauthorizedException("User not found"));

	}
	
	//add Expenses
	@CrossOrigin(origins="*")
	@PostMapping("/{trip}/expense") // Method to add expenses
	public Trip addExpense(@PathVariable("trip") String trip,
			@RequestHeader(name = "Authorization", required = true) String token,
			@RequestBody(required = true) Expense expense) {

		
		Claims claims = JWTIssuer.decodeJWT(token.split(" ")[1]);
		String subClaim = claims.get("sub", String.class);
		
		Users user = getUsers().stream() // Validating if the user exist in the Array of users
				.filter(u -> u.getUsername().equalsIgnoreCase(subClaim))
				.findFirst()// TODO return 401 in this exception
				.orElseThrow(() -> new UnauthorizedException("Unknown user"));
				
		
		
		expense.setTripId(trip); //Setting the tripID when add a new expense
		expense.setUsers(user); // setting the user the did this expense


		if (trips.get(trip) == null) { // If trip is NULL(Trip doens't exist) it will create a new trip 
			trips.put(trip, new Trip(trip));
			
		} else if(!trips.get(trip).isActive()) { // Verifying if the trip is Active.  
			                                   // Otherwise won't be possible to add more expense in the Trip.
			throw new TripClosedException("Trip are closed. Expenses cannot be added");
		}

		trips.get(trip).getExpenses().add(expense);
		// return to the trip with the expenses added and validated
		return trips.get(trip);
	}
	// Trip List
	@GetMapping("/{trip}")
	@CrossOrigin(origins="*") // 
	public List<Expense> trip(@PathVariable("trip") String trip) {
		if (Objects.nonNull(trips.get(trip)) ) {// Its is validating if the trip is not NULL
			return trips.get(trip).getExpenses(); // if trip is not NULL it will return the item from this trip
		}
		return Collections.emptyList();
	}
	// Close trip
	@PostMapping("/{trip}/close")
	public void closeTrip(@PathVariable("trip") String trip) {
		
		if (Objects.nonNull(trips.get(trip))) {// if trip is not NULL
			trips.get(trip).setActive(false);// it will set the trip with status FALSE
			
		} else { // Otherwise it will shows the exception
			throw new TripClosedException("Trip doesn't exist");
		}
		
	}
	// Summary trip
	@GetMapping("/{trip}/summary")
	@CrossOrigin(origins="*")
	public SummaryResponse getSummary(@PathVariable("trip") String trip) {
		
		if (Objects.nonNull(trips.get(trip))) {
			Trip t = trips.get(trip);
			
			Map<Users, Integer> ammounts = new HashMap<>();
			// Pedro - 25+25 = 50
			// Greg - 200
			// Jose - 200 
			
			t.getExpenses().forEach(e -> {
				if (ammounts.get(e.getUsers()) == null) { 
					ammounts.put(e.getUsers(), e.getAmount());
				} else {
					Integer total = Integer.sum(ammounts.get(e.getUsers()), e.getAmount());
					ammounts.put(e.getUsers(), total);
				}
			});
			
			List<ExpenseResponse> expenses = new ArrayList<>();
			// (Pedro, 50)
			// (Greg, 100)
			// (Jose, 200)
			
			
			for (Users key : ammounts.keySet()) {
				ExpenseResponse response = new ExpenseResponse();
				response.setUsername(key.getUsername());
				response.setAmount(ammounts.get(key).intValue());
				expenses.add(response);				
			}

			//Algorithms that is organizing the list for descending order
			List<ExpenseResponse> expenseSorted = expenses;
			Collections.sort(expenseSorted);
			
			//Generating the final summary list 
			SummaryResponse response = new SummaryResponse();
			response.setTripId(trip);
			response.setAmount(t.getTotal());
			response.setExpenses(expenseSorted);
			
			return response;
		} else {// If the trip do not exist it will trown an exception
			throw new TripDoesntExistException("Trip doesn't exist");
		}
	}
	// Method to get user
	public List<Users> getUsers() {
		return users;
	}
	
	// Method to avoid problems with CORS 
	// https://stackoverflow.com/questions/35091524/spring-cors-no-access-control-allow-origin-header-is-present
	@Bean 
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**").allowedOrigins("*");
	        }
	    };
	}
	

}
