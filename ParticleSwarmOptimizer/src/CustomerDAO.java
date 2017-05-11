import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerDAO {

	
	
	
	 public List<Customer> getCustomer(int number,Customer depot)  {
		Random random = new Random();
		 
		List<Customer> customerList = new ArrayList<>();
		customerList.add(depot);
		 Connection connection = null;
		 connection = JDBCMySQLConnection.getConnection();
		 for(int i=0;i<number;i++){
			 
			 //generate random number to fetch from the database;
			 int id = random.nextInt(292493 -0) + 0;
			 

			 ResultSet rs = null;
		        
		       
		        Customer customer= null;
		        String query = "SELECT * FROM customer WHERE id=?";
		        
		        try {           
		        	PreparedStatement ps=connection.prepareStatement(query);
		        	ps.setInt(1, id);
		        	rs = ps.executeQuery();
		        	
		            
		             
		            if (rs.next()) {
		            	customer = new Customer();
		            	customer.setId(rs.getInt("id"));
		            	customer.setName(rs.getString("name"));
		            	customer.setLocation(rs.getString("location"));
		            	customer.setLongitude(rs.getString("longitude"));
		            	customer.setLatitude(rs.getString("latitude"));
		            	
		            	customerList.add(customer);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } 
		 }
		 
	      	//close connection after you have fetched all the objects
	            if (connection != null) {
	                try {
	                    connection.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	            
	        
	        return customerList;
	    }
	
	
	
}
