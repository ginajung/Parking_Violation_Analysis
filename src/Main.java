package edu.upenn.cit594;


import java.util.List;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.ParkingCSVReader;
import edu.upenn.cit594.datamanagement.ParkingJSONReader;
import edu.upenn.cit594.datamanagement.ParkingReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyFileReader;
import edu.upenn.cit594.ui.CommandLineUserInterface;
import edu.upenn.cit594.logging.Logging;

public class Main {

	public static void main(String[] args) {
				
		//ERROR : the number of arguments is incorrect 
		if (args.length < 1 || args.length != 5) {
			System.out.println(" Error: The number of arguments is incorrect");
			System.exit(0);
		}
		
		//ERROR : the format of file is incorrect other than "csv, json", 
		if (!args[0].equals("json") && !args[0].equals("csv") )  {
			
			System.out.println(args[0] + " Error: The input file format is incorrect");
			System.exit(0);
		}
		
		// ERROR : FileNotExist exceptions  in Readers.
		
		//[0] csv or json 
		//[1] parking.csv or parking.json 
		//[2] properties.csv 
		//[3] population.txt
		//[4] log.txt
		
		String parking_filename ; 
		String property_file = args[2];
		String population_file = args[3];
		String log_file = args[4];
	    
		//System.out.println (args[1] + "," + property_file +","+ population_file +","+ log_file);
		System.out.println("Loading... might take up to 1 min.");
		//instantiate log
		Logging logger = new Logging(log_file);
		//log current time -> single whitespace -> each of the run time argument (separated by a single whitespace)
		long time = System.currentTimeMillis();
		String currentTime = Long.toString(time);
		String firstLog = currentTime + " " + args[0] + " " + args[1] + " " + args[2] + " " + args[3]+" "+ args[4] ; 
	
		logger.log(firstLog);
		
		
		ParkingReader park_reader =null;
		

		currentTime = Long.toString(System.currentTimeMillis());
		logger.log(currentTime + " " + population_file);
		PopulationReader pop_reader = new PopulationReader(population_file);
		

		currentTime = Long.toString(System.currentTimeMillis());
		logger.log(currentTime + " " + property_file);	
		PropertyFileReader prop_reader = new PropertyFileReader(property_file);
	
		//  CONSIDERED TO PROCESS ONCE and REUSE it 
		List<Property> all_properties = prop_reader.getAllProperties();
		
		
	//if csv is chosen
		if(args[0].equals("csv")) {
			parking_filename = args[1] ;
			currentTime = Long.toString(System.currentTimeMillis());
			logger.log(currentTime + " " + parking_filename);
			park_reader = new ParkingCSVReader(parking_filename);	
		}
	
	//if JSON is chosen
		if(args[0].equals("json")) {
			parking_filename =args[1] ;
			currentTime = Long.toString(System.currentTimeMillis());
			logger.log(currentTime  + " " + parking_filename);
			park_reader = new ParkingJSONReader(parking_filename);
	}
		
	// for handling output , taking readers, properties, and logger as parameters
		
		CommandLineUserInterface ui = new CommandLineUserInterface(park_reader, pop_reader, all_properties, logger); 
		
		ui.start();
			    
		}
}
