package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.Parking;
import edu.upenn.cit594.data.Property;

public class MarketValueAverager implements Averager{
	protected int zipCode;
	protected List<Property> properties = new ArrayList<Property>();
	protected Map<Integer, Integer> all_populations;
	
	private Map<Integer, Integer> results = new HashMap<> ();
	private Map<Integer, Integer> perCapitaResults = new HashMap<> ();

	
	public MarketValueAverager(List<Property> properties, Map<Integer, Integer> all_populations, int zipCode) {
		this.zipCode = zipCode;
		this.properties = properties;
		this.all_populations = all_populations;

	}
	

	/***
	 * Triggered by user input of 3 -> ZIP Code. 
	 * Returns the average market value for residences in that ZIP Code
	 * TTL Market Values for ALL Homes / # of Homes
	 * @return
	 */
	
	public int average() {
		if (results.containsKey(zipCode)) {
			return results.get(zipCode);
		}
		else {
			int result = calculateAverage();
			results.put(zipCode, result);
			return result;
		}
	}

	private int calculateAverage() {
		
		double ttlMarketValuesForAll = 0.0;
		int numOfHomes = 0;
		int averageValue = 0;
		
		//loop through the list
		for (int i = 0; i < properties.size(); i++) {
			
			if (properties.get(i).getZipCode() == zipCode) {
				
				ttlMarketValuesForAll += properties.get(i).getMarketValue();

				numOfHomes ++;
				
			}
		}
		
		double doubleAvr = ttlMarketValuesForAll / numOfHomes;
		averageValue = (int) Math.floor(doubleAvr);	
		return averageValue;

	}
	
	/***
	 * Triggered by user input of 5 -> ZIP Code
	 * Returns total market value per capita for that ZIP Code
	 * (TTL Market Value for all Residences in the ZIP)/ (Population of that ZIP)
	 * @param zipCode
	 * @return
	 */
	
	public int getMarketValuePerCapita() {
		if (perCapitaResults.containsKey(zipCode)) {
			return perCapitaResults.get(zipCode);
		}
		else {
			int result = calcMarketValuePerCapita();
			perCapitaResults.put(zipCode, result);
			return result;
		}
	}
	
	public int calcMarketValuePerCapita() {
		//System.out.println(zipCode);
		double ttlMarketValuesForAll = 0.0;
		int perCapita = 0;
		
		//loop through the list
		for (int i = 0; i < properties.size(); i++) {
			
			if (properties.get(i).getZipCode() == zipCode) {
				
				ttlMarketValuesForAll += properties.get(i).getMarketValue();
				
			}
		}
		if (ttlMarketValuesForAll == 0 || all_populations.get(zipCode) ==0  ) {
			return 0;
		}
		else {
			double perCapitaDouble = (double) (ttlMarketValuesForAll /all_populations.get(zipCode));
			perCapita = (int) Math.floor(perCapitaDouble);
			
		}
		

		
		return perCapita;
	}
	

	

}
