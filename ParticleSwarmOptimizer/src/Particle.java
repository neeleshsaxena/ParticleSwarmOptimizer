import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Particle {

	


	List<Double> pBest;
	List<Double> x;
	List<Double> velocity;
	Double fitnessValue;
	
	public Particle(List<Double> p1X){
		velocity = new ArrayList<>();
		pBest = new ArrayList<>();
		x = new ArrayList<>(p1X);
		fitnessValue = Double.MAX_VALUE;
		//Double node = Double.valueOf(p1X);
		DecimalFormat df = new DecimalFormat("#.##");
		//initialize velocity array
		for(int i=0;i<p1X.size();i++){
			Random rand = new Random();
			double num = randomInRange(0, p1X.size());
			velocity.add(Double.valueOf(df.format(num)));
		}
		
		
	}

	
	public  double randomInRange(double min, double max) {
		Random random = new Random();
		  double range = max - min;
		  double scaled = random.nextDouble() * range;
		  double shifted = scaled + min;
		  return shifted; // == (rand.nextDouble() * (max-min)) + min;
		}
	
	
	@Override
	public String toString() {
		return "Particle [pBest=" + pBest + ", x=" + x + ", velocity=" + velocity + ", fitnessValue=" + fitnessValue
				+ "]";
	}
}
