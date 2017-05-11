import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Swarm {

	int particles;
	//List<Particle> list;
	List<Double> gBest;
	Double gBestValue;
	int  iterationCount;
	double omega;
	double theta1;
	double beta1;
	double theta2;
	double beta2;
	List<Particle> particleList;
	
	Graph graph;
	List<Customer> customerList;
	
	public Swarm(int particles,Graph graph,List<Customer> customerList,int interationCount){
		this.particles=particles;
		this.graph = graph;
		this.customerList = customerList;
		this.iterationCount = interationCount;
		
		this.particleList = new ArrayList<>();
		gBest = new ArrayList<>();
		gBestValue = Double.MAX_VALUE;
		omega = 0.6;
		theta1= theta2 = 0.2;
		beta1 = 0.3;
		beta2 = 0.5;
		particleGenerator();
		
	}
	
	
	public  List<Double> knuthShuffle(List<Double> list) {
       
       
        for (int i = 0; i <list.size() ; i++) {
            // choose index uniformly in [0, i]
            int r = (int) (Math.random() * (i + 1));
            Double swap = list.get(r);
            list.set(r, list.get(i));
            list.set(i, swap);
        }
        return list;
    }
	
	public void particleGenerator(){
		//List<List<Double>> mainList = new ArrayList<>();
		//get the customer indices
		List<Double> customerIndices = new ArrayList<>();
		for(double i=0;i<customerList.size()-1;i++){
			customerIndices.add(i+1);
		}
		
		//ensure that the particle list contains the correct number of sequences and UNIQUE
		while(particleList.size()<particles){
			//shuffled the actual customer indices
			List<Double> newList = knuthShuffle(customerIndices);
			
			//if the particle list is empty,simply add
			if(particleList.isEmpty()){
				Particle p = new Particle(newList);
				particleList.add(p);
			}
			else{
				boolean flag=false;
				//ensure that no other sequence added in the particle list is the same
				for(int i=0;i<particleList.size();i++){
					if(particleList.get(i).x.equals(newList)){
						flag=true;
					}
				}
				
				//if flag is false, it means that no other list like this one is present
				if(!flag){
					Particle p = new Particle(newList);
					particleList.add(p);
				}
				
			}
				
		}
		
		
	}
	
	public void setGBest(){
		
		for(int i=0;i<particleList.size();i++){
		
			if(particleList.get(i).fitnessValue<gBestValue){
				System.out.println("GBest changed from "+gBestValue +" to "+particleList.get(i).fitnessValue);
				
				gBestValue = particleList.get(i).fitnessValue;
				gBest = particleList.get(i).x;
				
				List<Integer> actualSequence = getSequence(gBest);
				System.out.println("Optimal distance changed to "+ graph.totalDistance(actualSequence));
			}
		}
	
		
	}
	
	public List<Double> getNewVelocity(Particle p){
		
		List<Double> newVelocity = new ArrayList<Double>();
		List<Double> velocity  = p.velocity;
		List<Double> pBest = p.pBest;
		List<Double> x = p.x;
		DecimalFormat df = new DecimalFormat("#.##");
		for(int j=0;j<velocity.size();j++){
			Double vel = omega * (velocity.get(j)) + theta1 * beta1 * (pBest.get(j) - x.get(j)) + theta2 * beta2 *(gBest.get(j) - x.get(j));
			newVelocity.add(Double.valueOf(df.format(vel)));
		}
		
		return newVelocity;
		
	}
	
	public void reSetParticleVelocity(){
		
		//update the velocity for each particle
		for(int i=0;i<particleList.size();i++){
			
			List<Double> newVelocity = getNewVelocity(particleList.get(i));
			particleList.get(i).velocity = newVelocity;
			
		}
		
		
		
	}
	
	
	public List<Double> getNewX(Particle p){
		
		List<Double> newX = new ArrayList<>();
		List<Double> x = p.x;
		List<Double> velocity = p.velocity;
		int nodes = x.size();
		DecimalFormat df = new DecimalFormat("#.##");
		
		for(int i=0;i<x.size();i++){
			Double x1 = Double.valueOf(df.format(x.get(i))) + Double.valueOf(df.format(velocity.get(i)));
			if(x1>nodes){
				x1 = Double.valueOf(df.format(nodes));
			}
			newX.add(x1);
		}
		
		
		return newX;
	}
	
	
	public void reSetParticleX(){
		
		
		for(int i=0;i<particleList.size();i++){
			List<Double> newX = getNewX(particleList.get(i));
			particleList.get(i).x = newX;
		}
		
	}
	
	public List<Integer> getSequence(List<Double> x){
		
		//defining a new comparator
		Comparator<Map.Entry<Integer,Double>> comp = new Comparator<Map.Entry<Integer,Double>>(){

			@Override
			public int compare(Map.Entry<Integer, Double> arg0, Map.Entry<Integer, Double> arg1) {
				// TODO Auto-generated method stub
				Double val0 = arg0.getValue();
				Double val1 = arg1.getValue();
				//int val = val0.compareTo(val1);
				//return val;
				if(val0<val1){
					return -1;
				}else if(val0>val1){
					return 1;
				}else{
					return 0;
				}
			}
			
			
		};
		
		
		Map<Integer,Double> map = new HashMap<>();
		//insert the double values into the hashmap
		for(int i=1;i<=x.size();i++){
			map.put(i,x.get(i-1));
		}
		
		List<Map.Entry<Integer,Double>> mapList = new ArrayList<>(map.entrySet());
		Collections.sort(mapList,comp);
		
		List<Integer> positionXList = new ArrayList<>();
		for(Map.Entry<Integer, Double> entry:mapList){
			positionXList.add(entry.getKey());
		}
		
		return positionXList;
		
	}
	
	public void updateFitnessValue(){
		
		for(int i=0;i<particleList.size();i++){
			//get the particle's actual sequence after decoding based on customer rank
			System.out.println("Decoding the particle's actual sequence");
			System.out.println("Calculating the fitness value");
			List<Integer> actualSequence = getSequence(particleList.get(i).x);
			
			//get distance from distance matrix
			double distance  = graph.totalDistance(actualSequence);
			
			//get duration from matrix 
			int duration = graph.getSecondsMatrix(actualSequence);
			
			double fValue = distance+duration;
			
			if(fValue<particleList.get(i).fitnessValue){
				particleList.get(i).fitnessValue = fValue;
				particleList.get(i).pBest = particleList.get(i).x;
			}
			
		}
		
	}
	
	
	
	public void printXY(){
		
		int i=1;
		System.out.println("");
		for(Particle p:particleList){
			System.out.println("X vector for Particle "+i+" : "+p.x);
			System.out.println("Velocity vector for Particle "+i+" : "+p.velocity);
			System.out.println("PBest for Particle "+i+" : "+p.pBest);
			System.out.println("Fitness Value "+i+" : "+p.fitnessValue);
			i++;
			System.out.println("************************************************************");
		}
	}
	
	public void printFitnessValues(){
		System.out.println("************************************************************");
		int i=1;
		System.out.println("");
		for(Particle p:particleList){
			System.out.println("PBest for Particle after updating "+i+" : "+p.x);
			System.out.println("Fitness Value after updating "+i+" : "+p.fitnessValue);
			i++;
			System.out.println("**********************************************************");
		}
	}
	
	public void swarmIteration(){
		
		for(int i=0;i<iterationCount;i++){
			System.out.println("************************************************************");
			System.out.println("Iteration count: "+i);
			//print x and v vector
			printXY();
			
			//update the fitness value and pBest for each particle
			System.out.println("Update the fitness values");
			updateFitnessValue();
			printFitnessValues();
			
			//set the Gbest 
			setGBest();
			
			//reset velocity of the particle
			reSetParticleVelocity();
			System.out.println("Update particle velocity");
			
			//reset the x for each particle
			reSetParticleX();
			System.out.println("Update X vector");
			
		}
		
		
		
		
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*int nodes = 4;
		
		Address sourceAdd = new Address(0,"source","2123","2323");
		Customer depot = new Customer(0,"Depot",sourceAdd);
		
		Address ad1 = new Address(1,"1050 Hyde Park Ave, Hyde Park, MA 02136","42.26332","-71.12126");
		Customer c1 = new Customer(1,"Neelesh",ad1);
		
		Address ad2 = new Address(2,"671 E Eighth St, South Boston, MA 02127","42.33134","-71.03441");
		Customer c2 = new Customer(2,"Yash",ad2);
		
		Address ad3 = new Address(3,"109 Sanborn Ave, West Roxbury, MA 02132","42.28289","-71.1667");
		Customer c3 = new Customer(3,"Kunal",ad3);
		
		Address ad4 = new Address(4,"5 South St, Brighton, MA 02135","42.34034","-71.15597");
		Customer c4 = new Customer(4,"Manasi",ad4);
		
		List<Customer> list = new ArrayList<Customer>();
		list.add(depot);
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		
		Graph graph = new Graph(nodes,list);
		
		
		Swarm swarm = new Swarm(3,graph);
		List<Double> p1X = new ArrayList();
		p1X.add(3.0);p1X.add(2.0);p1X.add(1.0);p1X.add(4.0);
		Particle p1 = new Particle(p1X);
		
		
		
		List<Double> p2X = new ArrayList();
		p2X.add(2.0);p2X.add(1.0);p2X.add(3.0);p2X.add(4.0);
		Particle p2 = new Particle(p2X);
		
		
		List<Double> p3X = new ArrayList();
		p3X.add(4.0);p3X.add(3.0);p3X.add(2.0);p3X.add(1.0);
		Particle p3 = new Particle(p3X);
		
		List<Particle> particleList = new ArrayList<Particle>();
		particleList.add(p1);particleList.add(p2);particleList.add(p3);
		swarm.swarmIteration();
		
		List<Integer> sequence = swarm.getSequence(swarm.gBest);
		
		for(Integer i: sequence){
			System.out.println(i.intValue());
		}*/
		
		
		
	}

}
