
public class Address {

	
	private int id;
	private String location;
	private String latitude;
	private String longitude;
	
	public Address(int id,String location,String latitude,String longitude){
		this.id = id;
		this.location=location;
		this.latitude=latitude;
		this.longitude=longitude;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	
	
}
