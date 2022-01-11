package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class PopulationReader{

	protected String filename;
	
	public PopulationReader(String name) {
		
		filename=name;	
	}
	
	// OPTION FOR MAP Data Structure

	public Map<Integer,Integer> getAllPopulation() {
	
		Map<Integer,Integer> all_Populations =new TreeMap<>();
		Scanner in =null;
	
    try {
    	in = new Scanner (new File(filename));
    	
			while (in.hasNextLine()) {
				
			String[] temp= in.nextLine().split(" ");
			// whitespace separated. 1. zip_code  2. population
			
			int zip_code = Integer.parseInt(temp[0]);
			int population = Integer.parseInt(temp[1]);
				
			all_Populations.put(zip_code,population);
							
			}
			
			in.close();
	 
	}

    
	catch (FileNotFoundException e) {
		// 
		System.out.println("Filename you enterted is incorrect") ; 
		e.printStackTrace();
	}catch (IOException e) {
		   e.printStackTrace();
	   }
    
    // sort population by zip_code (key)
    
    
    
    
	return all_Populations;
	
	}
	
	
	
// FOR TEST FUNCTION 
//	  public static void main(String[] args) {
//
//			PopulationReader pr = new PopulationReader("population.txt");
//
//			Map<Integer, Integer> all_pop = new TreeMap<>();
//			all_pop = pr.getAllPopulation();
//			
//			
//			for(Integer k : all_pop.keySet()) {
//			System.out.println(k +":" + all_pop.get(k) );
//			}
//			
//			
//		}
	   
	
}
