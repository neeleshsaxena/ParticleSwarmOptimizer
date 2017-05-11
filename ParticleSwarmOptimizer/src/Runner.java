import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Runner {
	
	public static List<Customer> getList(){
		
		List<Customer> list = new ArrayList<>();
		Customer depot = new Customer(0,"Depot","78 Westland Avenue, Boston MA","42.342701","-71.092201");
		Customer c1 = new Customer(1,"Neelesh","1050 Hyde Park Ave, Hyde Park, MA 02136","42.26332","-71.12126");
		
		
		Customer c2 = new Customer(2,"Robben","671 E Eighth St, South Boston, MA 02127","42.33134","-71.03441");
		
		//Address ad3 = new Address(3,"109 Sanborn Ave, West Roxbury, MA 02132","42.28289","-71.1667");
		Customer c3 = new Customer(3,"Hazard","109 Sanborn Ave, West Roxbury, MA 02132","42.28289","-71.1667");
		
		//Address ad4 = new Address(4,"5 South St, Brighton, MA 02135","42.34034","-71.15597");
		Customer c4 = new Customer(4,"Dybala","5 South St, Brighton, MA 02135","42.34034","-71.15597");
		
		Customer c5 = new Customer(5,"Costa","90 Florida St, Dorchester, MA  02124","42.28913","-71.05872");
		
		Customer c6 = new Customer(6,"Terry","68 Paul Gore St, Jamaica Plain, MA  02130","42.31963","-71.10779");
		
		Customer c7 = new Customer(7,"Boatang","344 E Eighth St, South Boston, MA  02127","42.33136","-71.04727");
		
		Customer c8 = new Customer(8,"Cahill","42-44 Middle St, South Boston, MA  02127","42.33165","-71.05562");
		
		Customer c9 = new Customer(9,"Azpi","89A Grew Ave, Roslindale, MA  02131","42.27531","-71.12209");
		
		Customer c10 = new Customer(10,"Willian","35 Elmore St, Roxbury, MA  02119","42.32023","-71.09154");
		list.add(depot);list.add(c1);list.add(c2);list.add(c3);list.add(c4);
		list.add(c5);list.add(c6);list.add(c7);list.add(c8);list.add(c9);list.add(c10);
		
		return list;
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Enter the number of customer you want to choose");
		Scanner in = new Scanner(System.in);
		int numberOfCustomer = in.nextInt();
		System.out.println("Enter the number of Particles");
		int particles = in.nextInt();
		System.out.println("Enter the number of Iterations");
		int iterationCount = in.nextInt();
		System.out.println("********************************************************************");
		
		System.out.println("Retrive the customers from the Database");
		//fetch the customer list from the database
		
		
		CustomerDAO cDao = new CustomerDAO();
		Customer depot = new Customer(0,"Depot","78 Westland Avenue, Boston MA","42.342701","-71.092201");
		
		List<Customer> customerList = cDao.getCustomer(numberOfCustomer,depot);
		//trial
		//List<Customer> customerList = getList();
		
		System.out.println("The choosen Customers are");
		for(Customer cust:customerList){
			System.out.println(cust);
		}
		
		System.out.println("********************************************************************");
		System.out.println("Retrieving distances, duration between customers from Google Distance Matrix Api and populating Graph's adjacency matrix");
		System.out.println("********************************************************************");
		
		
		Graph graph = new Graph((double) numberOfCustomer,customerList);
		System.out.println("Print the distance matrix");
		System.out.println("********************************************************************");
		graph.printDistance();
		Swarm swarm = new Swarm(particles,graph,customerList,iterationCount);
		swarm.swarmIteration();
		List<Double> swarmSequence = swarm.gBest;
		List<Integer> actualSequence = swarm.getSequence(swarmSequence);
		Double totalDistance = graph.totalDistance(actualSequence);
		
		//Queue of customers in order
		Queue<Customer> queue = new LinkedList<>();
		//List of finalcustomer list
		List<Customer> finalList = new ArrayList<>();
		finalList.add(depot);
		for(int index:actualSequence){
			queue.add(customerList.get(index));
			finalList.add(customerList.get(index));
		}
		
		System.out.println("********************************************************************");
		System.out.println("The Gbest value is: "+swarm.gBestValue);
		System.out.println("The order of customers being serviced is: ");
		System.out.print("Depot");
		List<String> totalDuration = graph.totalDuration(actualSequence);
		while(!queue.isEmpty()){
			System.out.print("-> "+queue.poll().getName()+"\t");
		}
		System.out.println(" ");
		for(int i=0;i<totalDuration.size();i++){
			System.out.print(totalDuration.get(i)+"-> "+"\t");
		}
		
		System.out.println("");
		System.out.println("The total distance that will be covered is: "+totalDistance+" miles");
		GraphStream graphStream = new GraphStream();
		graphStream.getGraph(finalList);
		
		
	}

}
