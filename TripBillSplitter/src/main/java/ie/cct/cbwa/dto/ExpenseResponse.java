package ie.cct.cbwa.dto;


//This class is generating the EXPENSES as an Object 
public class ExpenseResponse implements Comparable<ExpenseResponse> {

	
	private String username;
	private Integer amount;
	

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	//This method is comparing two values to sort it on the Summary
    public int compareTo(ExpenseResponse compareResponses) {
        int compareAmount=((ExpenseResponse)compareResponses).getAmount();
        /* For Descending order do like this */
        return this.amount-compareAmount;

    }

    @Override
    public String toString() {
        return "[ username=" + username + ", amount=" + amount + "]";
    }   
}
