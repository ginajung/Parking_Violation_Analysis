package edu.upenn.cit594.ui;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import edu.upenn.cit594.data.Parking;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.ParkingReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyFileReader;
import edu.upenn.cit594.processor.MarketValueAverager;
import edu.upenn.cit594.processor.ParkingFineProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.TotalLivableAreaAverager;
import edu.upenn.cit594.logging.Logging;




public class CommandLineUserInterface {
	
	// processor gets json or csv to construct processor..

	protected ParkingReader park_reader;
	protected ParkingFineProcessor park_processor;
	protected PopulationReader pop_reader;
	protected PopulationProcessor pop_processor;
//	protected PropertyFileReader prop_reader;
	protected MarketValueAverager mva_processor;
	protected TotalLivableAreaAverager tlav_processor; 
	protected Logging logger;
	
	protected List<Parking> all_violations;
	protected Map<Integer,Integer> all_populations;
	protected List<Property> all_properties;
	protected Scanner in;
	protected int zip_code;
	protected String currentTime;
	
	
	
	public CommandLineUserInterface(ParkingReader park_reader, PopulationReader pop_reader, List<Property> all_properties, Logging log) {
		this.park_reader = park_reader;
		this.pop_reader = pop_reader;
		this.all_properties = all_properties;
		this.logger = log;
		
		//write current time -> single whitespace -> each runtime arguments
		
		//long start = System.currentTimeMillis();
				
		pop_processor = new PopulationProcessor (pop_reader);
		all_populations = pop_reader.getAllPopulation();
		all_violations = park_reader.getAllParkingViolations();		
	
		
		in = new Scanner(System.in);
		
//		//logging user input
//		currentTime = Long.toString(System.currentTimeMillis());
//		logger.log(currentTime + in + "\n");

		//long end = System.currentTimeMillis();
		//System.out.println("Time: " + (end-start) + "ms");
		
	}
	
//	 1,     show the total population for all ZIP Codes, (Step #1) 
//	 2, 	show the total parking fines per capita for each ZIP Code, (Step #2)
//	 3, 	show the average market value for a specified ZIP Code, (Step #3)
//	 4, 	show the average total livable area for a specified ZIP Code, (Step #4)
//	 5,	    show the total residential market value per capita for a specified ZIP Code, (Step #5)
//	 6, 	show the results of your custom feature, (Step #6) 

	
	public void start() {
		
		System.out.println("Enter 0 to exit,  ");
		System.out.println("Enter 1 to print the total population for all ZIP Codes,  ");
		System.out.println("Enter 2 to print total parking fines per capita for each ZIP Code, ");
		System.out.println("Enter 3 to print the average market value for a specified ZIP Code,  ");
		System.out.println("Enter 4 to print the average total livable area for a specified ZIP Code,  ");
		System.out.println("Enter 5 to print the total residential market value per capita for a specified ZIP Code,  ");
		System.out.println("Enter 6 to print the results of your custom feature : ");
		
		// CHECK for valid input (integer 0~6)
		
		//logging user input
		int choice = in.nextInt();
		
		currentTime = Long.toString(System.currentTimeMillis());
		String tempLog = currentTime + " " + String.valueOf(choice);
		logger.log(tempLog);
		
		
	if (choice == 0) {
		System.exit(0);
	}
	else if (choice == 1) {
		// do STEP 1
		System.out.println(pop_processor.caculateTotalPopulation());
		start();
	}
	else if (choice == 2) {
		// do STEP 2 : Enter 2 to print total parking fines per capita for each ZIP Code
			
		for(Integer k : all_populations.keySet()) {
		
			park_processor = new ParkingFineProcessor (all_violations, all_populations,k);
			
		System.out.println (k + "\t" + park_processor.caculateTotalFinePerCapita());
		}
		
		start();
	}
	
	else if (choice == 3) {
		// do STEP 3 : show the average market value for a specified ZIP Code, (Step #3)
		System.out.print("Enter Zip_Code that you would like to check: ");
		
		// CHECK for valid zipcode
		zip_code = in.nextInt();
		
		logZip(zip_code);
		
		mva_processor = new MarketValueAverager (all_properties, all_populations, zip_code);	
		System.out.println ( mva_processor.average());
		start();
	}
	
	else if (choice ==4) {
		// do STEP 4 : show the average total livable area for a specified ZIP Code, (Step #4)
		System.out.print("Enter Zip_Code that you would like to check: ");
		zip_code = in.nextInt();
		
		logZip(zip_code);
		
		tlav_processor = new TotalLivableAreaAverager (all_properties, zip_code);
		System.out.println ( tlav_processor.average());
		start();
	}
	else if (choice ==5) {
		// do STEP 5 :  show the total residential market value per capita for a specified ZIP Code, (Step #5)
		System.out.print("Enter Zip_Code that you would like to check: ");
		
		zip_code = in.nextInt();
		logZip(zip_code);
		
		mva_processor = new MarketValueAverager (all_properties, all_populations, zip_code);	
		
		System.out.println (mva_processor.getMarketValuePerCapita());
		start();
	}
	else if (choice ==6) {
		// do STEP 6 : print MarketValueAverages for the most parkingfinePerCapita and the least parkingfinePerCapita 
		
		System.out.println(doStep6());
		start();
		
		
	}
	else {
		System.out.println("Error: Your input is incorrect. Please start again.");
	}
	
	in.close();
	}
	
	protected void logZip(int zip_code) {
		currentTime = Long.toString(System.currentTimeMillis());
		logger.log(currentTime + " " + Integer.toString(zip_code));
	}
	
	
	/***
	 * print MarketValueAverages for the most parkingfinePerCapita and the least parkingfinePerCapita
	 */
	//Gina: I have added memoization to below, but limited the output to only least parkingfinePerCapita for the sake of Return type.
	//Could you put below codes into separate class?
	
	private Map<Integer, String> results = new HashMap<> ();
	
	public String doStep6() {
		if (results.containsKey(1)) {
			return results.get(1);
		}
		else {
			String result = calcStep6();
			results.put(1, result);
			return result;
		}
	}
	protected String calcStep6() {
		
		double min_parkingFine = Double.POSITIVE_INFINITY;
		double max_parkingFine = 0.0 ;
		double cur_parkingFine =0.0;
		int min_zip =0;
		int max_zip =0;
		
		for(Integer k : all_populations.keySet()) {
			
			park_processor = new ParkingFineProcessor (all_violations, all_populations,k);
				cur_parkingFine = Double.parseDouble(park_processor.caculateTotalFinePerCapita()); 
			if(cur_parkingFine < min_parkingFine) {
				min_parkingFine = cur_parkingFine;
				min_zip = k;
			}
			if (cur_parkingFine > max_parkingFine) {
				max_parkingFine = cur_parkingFine;
				max_zip = k;
			}
			
		}
		

		
		mva_processor = new MarketValueAverager (all_properties, all_populations, max_zip);	
		int max_mVA = mva_processor.average();
		//System.out.println (min_parkingFine +":"+ min_zip + ":" + min_mVA);
		String result_max = "Zip Code of " +  Integer.toString(max_zip) + 
				" has the maximun parking fine of " + Double.toString(max_parkingFine) 
				+ " with market value avr. of " + Integer.toString(max_mVA);
	
		return result_max;
	}
	
}
