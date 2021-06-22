package ie.cct.cbwa.model;

import java.util.ArrayList;
import java.util.List;

public class Trip {
	
	private String tripId;
	private List<Expense> expenses;
	private boolean active;
	
	

	public Trip(String tripId) {
		this.tripId = tripId;
		this.active = true;
		this.expenses = new ArrayList<Expense>();
	}
	
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	
	public List<Expense> getExpenses() {
		return expenses;
	}
	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Integer getTotal() {
		return expenses.stream()
				.map(e -> e.getAmount())
				.reduce(0, Integer::sum);
	}

}
