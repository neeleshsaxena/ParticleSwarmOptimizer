
public class Order {

	private Customer customer;
	private String itemName;
	
	public Order(Customer customer,String itemName){
		this.customer = customer;
		this.itemName = itemName;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	
}
