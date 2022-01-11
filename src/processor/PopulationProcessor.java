package edu.upenn.cit594.processor;


import java.util.HashMap;
import java.util.Map;

import edu.upenn.cit594.datamanagement.PopulationReader;


/***
 * Triggered by user input of 1 
 * Returns the total population for all ZIP codes 
 * 
 * @return
 */


public class PopulationProcessor {
	
	protected PopulationReader pop_reader;
	protected Map<Integer, Integer> populations;
	private Map<Integer, Integer> results = new HashMap<> ();
	
public PopulationProcessor(PopulationReader reader) {
		
		this.pop_reader = reader;
		populations =pop_reader.getAllPopulation();	
		
	}

public int caculateTotalPopulation() {
	if (results.containsKey(1)) {
		return results.get(1);
	}
	else {
		int result = getTotalPopulation();
		results.put(1, result);
		return result;
	}
}


private int getTotalPopulation() {
	
	int total_population=0;
	
	for (Integer ppl : populations.keySet()) {
		total_population = total_population + populations.get(ppl);
	}
	
	//System.out.println(total_population);	
	return total_population;
	
}


/**
 * For test function
 * @param args
 */
//public static void main(String[] args) {
//
//	PopulationReader pr = new PopulationReader("population.txt");
//    PopulationProcessor ppr = new PopulationProcessor(pr);
//    
//	ppr.caculateTotalPopulation();
//
//	System.out.println(ppr.caculateTotalPopulation());
//
//}


}
