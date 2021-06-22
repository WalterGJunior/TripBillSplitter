package ie.cct.cbwa.dto;


import java.util.List;

//This class is generating the SUMMARY as an Object 
public class SummaryResponse   {
	
	private String tripId;
	private int amount;
	private List<ExpenseResponse> expenses;
	
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public List<ExpenseResponse> getExpenses() {
		return expenses;
	}
	public void setExpenses(List<ExpenseResponse> expenses) {
		this.expenses = expenses;
	}
	
	

		


}
