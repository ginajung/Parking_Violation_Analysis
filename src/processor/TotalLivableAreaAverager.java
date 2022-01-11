package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.Property;

public class TotalLivableAreaAverager implements Averager {
	protected int zipCode;
	protected List<Property> properties = new ArrayList<Property>();
	private Map<Integer, Integer> results = new HashMap<> ();
	
	//add population list
	public TotalLivableAreaAverager(List<Property> properties, int zipCode) {

		this.zipCode = zipCode;
		this.properties= properties;
	}
	
	

	/***
	 * Triggered by user input of 4 -> ZIP Code.
	 * The sum of the total livable areas for all homes in the ZIP Code/ num of homes
	 * @param zipCode, population
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
	
	public int calculateAverage() {
	
		double ttlLivableArea = 0.0;
		int numOfHomes = 0;
		int avrTtlLivableArea = 0;
		
		//loop through the list
		for (int i = 0; i < properties.size(); i++) {
			if (properties.get(i).getZipCode() == zipCode) {
				
				// CHANGED : add ttlLivableArea
				ttlLivableArea += properties.get(i).getTotalLivableArea();
				numOfHomes ++;
				
			}
		}
		
		
		double doubleAvrTtlLivableArea = ttlLivableArea/numOfHomes;
//		System.out.println("ttl livable area is : " + ttlLivableArea);
//		System.out.println("ttl num of homes is : " + numOfHomes);
		
		
		
		if (doubleAvrTtlLivableArea == 0.0) {
			return 0;
		}
		else {
			avrTtlLivableArea = (int) Math.floor(doubleAvrTtlLivableArea);
		}

		//System.out.println(doubleAvrTtlLivableArea);
		
		return avrTtlLivableArea;

	}
	
	

}
