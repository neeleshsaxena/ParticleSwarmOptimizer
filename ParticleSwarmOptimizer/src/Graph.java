
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Graph {

	Double V;
	Double [][] matrix;
	String[][] durationMatrix;
	int [][] secondsMatrix;
	List<Customer> customerList;
	
	public Api api;
	
	public Graph(Double V,List<Customer> list){
		
		api = new Api();
		this.V=V;
		customerList = list;
		
		matrix= new Double[(int) (V+1.0)][(int) (V+1)];
		durationMatrix= new String[(int) (V+1)][(int) (V+1)];
		secondsMatrix= new int[(int) (V+1)][(int) (V+1)];
		
		for(int i=0;i<list.size();i++){
			for(int j=i+1;j<list.size();j++){
				
				Container container = api.restApi(list.get(i).getLocation(), list.get(j).getLocation());
				
				//update the distance matrix
				matrix[i][j] = container.distance;
				matrix[j][i] = container.distance;
				
				
				
				//update the string duration matrix
				durationMatrix[i][j] = container.stringDuration;
				durationMatrix[j][i] = container.stringDuration;
				//System.out.print(" AND DURATION between them  is :"+container.stringDuration);
				System.out.println("********************************************************************");
				System.out.println("Source"+"\t\t"+"Destination"+"\t"+"Distance"+"\t"+"Duration");
				System.out.println(list.get(i).getName()+"\t\t"+list.get(j).getName()+"\t\t"+container.distance+"\t\t"+container.stringDuration);
				//update the seconds duration matrix
				secondsMatrix[i][j] = container.secondsDuration;
				secondsMatrix[j][i] = container.secondsDuration;
				System.out.println("");
			}
		}
		
	}
	
	
	
	
	public Double totalDistance(List<Integer> list){
		Double dist=matrix[0][list.get(0)];
		for(int i=0;i<list.size()-1;i++){
			dist += matrix[list.get(i)][list.get(i+1)];
		}
		return dist;
	}
	
	public int getSecondsMatrix(List<Integer> list){
		int dist=secondsMatrix[0][list.get(0)];
		for(int i=0;i<list.size()-1;i++){
			dist += secondsMatrix[list.get(i)][list.get(i+1)];
		}
		return dist;
	}
	
	public List<String> totalDuration(List<Integer> list){
		List<String> duration = new ArrayList<>();
		duration.add(durationMatrix[0][list.get(0)]);
		for(int i=0;i<list.size()-1;i++){
			duration.add(durationMatrix[list.get(i)][list.get(i+1)]);
		}
		
		return duration;
	}
	
	
	public void printDistance(){
		for(Customer cust:customerList){
			System.out.print("\t"+cust.getName());
		}
		System.out.println("");
		for(int i=0;i<matrix.length;i++){
			System.out.print(customerList.get(i).getName());
			for(int j=0;j<matrix[0].length;j++){
				if(matrix[i][j]==null){
					System.out.print("\t"+0);
				}else{
					System.out.print("\t"+matrix[i][j]);
				}
				
			}
			System.out.println("");
		}
		
	}
}
