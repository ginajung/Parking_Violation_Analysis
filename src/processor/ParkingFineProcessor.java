package edu.upenn.cit594.processor;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.upenn.cit594.data.Parking;
import edu.upenn.cit594.datamanagement.ParkingCSVReader;
import edu.upenn.cit594.datamanagement.ParkingReader;
import edu.upenn.cit594.datamanagement.PopulationReader;

/**
 *		Step 2. Total parking fines per capita for each ZIP Code

 */

public class ParkingFineProcessor {

	protected int zip_code;
	protected List<Parking> all_violations;
	protected Map<Integer,Integer> all_populations;
	private Map<Integer, String> results = new HashMap<> ();
	
	
	public ParkingFineProcessor(List<Parking> violations, Map<Integer,Integer> populations, int zip_code) {
		
		this.all_violations = violations;
		this.all_populations = populations;
		this.zip_code = zip_code;
	
	
	}

	// the total aggregate fines divided by the population of that ZIP Code
	// print with trailing zero, DecimalFormat is used to return String!
	
	public String caculateTotalFinePerCapita() {
		if (results.containsKey(1)) {
			return results.get(1);
		}
		else {
			String result = getTotalFinePerCapita();
			results.put(1, result);
			return result;
		}
	}
	
	public String getTotalFinePerCapita() {
	
	double total_fine=0;
	double t_fine_capita =0;
	
	for (Parking pr : all_violations) {
		
		if(pr.getZip_code() == zip_code) {
			total_fine += pr.getFine();
		}
		
	}
	
	    // total fine from zip-code / population from zip.
	  
	  t_fine_capita = (double) (total_fine /all_populations.get(zip_code));
	  

	  //Truncated 4 decimal digit.
	  
	  DecimalFormat df = new DecimalFormat("0.0000");
	  df.setRoundingMode(RoundingMode.DOWN);
	  

	  return df.format(t_fine_capita);
	
}
	
	
	
	
	
	// FOR TEST
	
//	public static void main(String[] args) {
//
//		PopulationReader pop = new PopulationReader("population.txt");
//	    ParkingReader park_r = new ParkingCSVReader("parking.csv");
//	    ParkingFineProcessor pr_processor ;
//	    List<Parking> all_violations = park_r.getAllParkingViolations();
//	    Map<Integer,Integer> all_populations = pop.getAllPopulation();
//   
//	    for(Integer k : all_populations.keySet()) {
//			
//			pr_processor = new ParkingFineProcessor (all_violations, all_populations,k);
//			
//		System.out.println (k + "\t" + pr_processor.caculateTotalFinePerCapita());
//		}
//		
//	
//	}

}
