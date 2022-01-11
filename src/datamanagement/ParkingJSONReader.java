package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.Parking;


/**
 * JSON format
 * {
 * "ticket_number":2905938,
 * "plate_id":"1322731",
 * "date":"2013-04-03T15:15:00Z",
 * "zip_code":"19104",
 * "violation":"METER EXPIRED CC",
 * "fine":36,
 * "state":"PA"
 * }
 */

public class ParkingJSONReader implements ParkingReader{
	
	
	protected String filename;
	
	public ParkingJSONReader(String name) {
		
		filename=name;	
	}
	
	   @Override
		public List<Parking> getAllParkingViolations(){
	
	//create parser
	
		 JSONParser parser = new JSONParser();
		 
		 List<Parking> parking_violations = new ArrayList<>();
	// open the file and get the array of JSON objects	
	
	try { 
		
		JSONArray j_violations = (JSONArray) parser.parse(new FileReader(filename));
    //use an iterator to iterate over each element of the array
   
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iter = j_violations.iterator();
	
	//iterate while there are more objects in array
	   while(iter.hasNext()) {
		   
		   JSONObject parking_violation =(JSONObject) iter.next();
		   
//		   System.out.println(" tn"+ parking_violation.get("ticket_number").getClass());  //-- long
//		   System.out.println("pl"+ parking_violation.get("plate_id").getClass());  //-- string
//		   System.out.println("fine"+ parking_violation.get("fine").getClass());   //-- long
//		   System.out.println("date"+ parking_violation.get("date").getClass());   //--string
//		   System.out.println("v"+ parking_violation.get("violation").getClass()); //-- string
//		   System.out.println("state"+ parking_violation.get("state").getClass());// -- string
//		   System.out.println("z"+ parking_violation.get("zip_code").getClass()); //-- string
		   
		   
		   Long tn = (Long) parking_violation.get("ticket_number");
		   int ticket_number =tn.intValue();
		   int plate_id = Integer.parseInt((String) parking_violation.get("plate_id"));
		   Long fn = (Long)parking_violation.get("fine");
		   int fine =fn.intValue(); 
		  
		   String date = (String) parking_violation.get("date");
		   String violation = (String) parking_violation.get("violation");
		   String state = (String) parking_violation.get("state");
		   
		   int zip_code=0;
		   
			if (parking_violation.get("zip_code").equals("")) {
				zip_code = 0;
			} else {
				zip_code = Integer.parseInt((String)parking_violation.get("zip_code"));
			}
	   
			//System.out.println(zip_code);
			
			parking_violations.add(new Parking( date,  fine,  violation,  plate_id,  state, ticket_number, zip_code ));
	   }
	}
	catch (FileNotFoundException e) {
		
		System.out.println("Filename you enterted is incorrect") ; 
		
		e.printStackTrace();
   } catch (IOException e) {
         e.printStackTrace();
   } catch (ParseException e) {
         e.printStackTrace();
   }

	return parking_violations;
		}
	   
	   
	   
	// FOR TEST FUNCTION 
//	   public static void main(String[] args) {
//
//			ParkingJSONReader jr = new ParkingJSONReader("parking.json");
//
//			List<Parking> viols = new ArrayList<>();
//
//			viols = jr.getAllParkingViolations();
//
//			System.out.println(viols.size());
//
//		}
	   
}
