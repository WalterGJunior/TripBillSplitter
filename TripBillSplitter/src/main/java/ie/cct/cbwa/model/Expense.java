package ie.cct.cbwa.model;

public class Expense {
	
	private String tripId;
	private Integer amount;
	private String description;
	private Users Users;	

	public Expense() {
		super();
	}
	
	public Expense(String name, Integer amount, Users Users) {
		super();
		this.tripId = name;
		this.amount = amount;
		this.Users = Users;
	}
	
	public Users getUsers() {
		return Users;
	}

	public void setUsers(Users users) {
		Users = users;
	}

	public String getTripId() {
		return tripId;
	}
	
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
