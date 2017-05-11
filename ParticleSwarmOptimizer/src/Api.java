import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;



public class Api {
	
	

	
	public Container restApi(String address1, String address2){
		 try {
			 	
			 	String origin = processString(address1);
			 	String destination = processString(address2);
			 	String semUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+origin+"&destinations="+destination+"&language=fr-FR&key=AIzaSyDwuQHjo83FawNmWnXXwU9aGapk-PHIDLQ";
				//URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=1050+Hyde+Park+Ave+Hyde+Park+MA+02136&destinations=671+E+Eighth+St,+South+Boston+MA+02127&language=fr-FR&key=AIzaSyDwuQHjo83FawNmWnXXwU9aGapk-PHIDLQ");
				URL url = new URL(semUrl);
			 	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}
				
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
				
				StringBuffer response = new StringBuffer();

				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					response.append(output);
				}
				JSONObject jsonRespRouteDistance= null;
				JSONObject jsonRespRouteDuration= null;
				try {
					jsonRespRouteDistance = new JSONObject(response.toString())
					        .getJSONArray("rows")
					        .getJSONObject(0)
					        .getJSONArray ("elements")
					        .getJSONObject(0)
					        .getJSONObject("distance");
					
					jsonRespRouteDuration = new JSONObject(response.toString())
					        .getJSONArray("rows")
					        .getJSONObject(0)
					        .getJSONArray ("elements")
					        .getJSONObject(0)
					        .getJSONObject("duration");
					
					
					String distance = jsonRespRouteDistance.get("text").toString().substring(0, 3);
					String duration = jsonRespRouteDuration.get("text").toString();
					String secondsDuration =  jsonRespRouteDuration.get("value").toString();
					
					Double dist = getDoubleValue(distance);
					
					Container container = new Container();
					container.distance= dist;
					container.stringDuration = duration;
					container.secondsDuration = Integer.valueOf(secondsDuration);
					
					return container;
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					conn.disconnect();
				}
			

			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			  }

			return null;
	}

	public String processString(String str){
		StringBuilder st = new StringBuilder();
		char[] arr = str.toCharArray();
		for(int i=0;i<arr.length;i++){
			if(arr[i]==32){
				st.append("+");
			}else{
				st.append(arr[i]);
			}
		}
		
		return st.toString();
	}
	
	
	public Double getDoubleValue(String distance){
		StringBuilder st = new StringBuilder();
		char[] arr = distance.toCharArray();
		for(int i=0;i<arr.length;i++){
			if(Character.isDigit(arr[i])){
				st.append(arr[i]);
			}else{
				st.append(".");
			}
		}
		double value = Double.parseDouble(st.toString());
		return value;
	}

}


