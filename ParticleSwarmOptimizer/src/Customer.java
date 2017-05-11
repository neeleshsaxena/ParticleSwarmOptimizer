
public class Customer {
	
	private int id;
	private String name;
	
	private String location;
	private String latitude;
	private String longitude;
	
	public Customer(){
		
	}
	
	public Customer(int id,String name,String location, String latitude,String longitude){
		this.id = id;
		this.name=name;
		
		this.location=location;
		this.latitude= latitude;
		this.longitude= longitude;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", location=" + location + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}

	
	

}
