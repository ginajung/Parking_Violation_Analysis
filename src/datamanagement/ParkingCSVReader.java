package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.Parking;

 
public class ParkingCSVReader  implements ParkingReader{

	protected String filename;
	
	
	public ParkingCSVReader(String name) {
		
		filename= name;	
	}
	
	// save all parking_violation into list
	
	public List<Parking> getAllParkingViolations() {
		
		List<Parking> parking_violations =new ArrayList<>();
		
		Scanner in =null;
		
		try {
			in = new Scanner (new File(filename));
			
			in.useDelimiter(",");
			;
			while (in.hasNextLine()) {
				
				String parking_in = in.nextLine();
				String[] violation_info = parking_in.split(",");


			//"date" : YYYY-MM-DD T hh:mm:ss Z format.2013-04-03T15:15:00Z
				// *  "fine" :36
				// *  "violation": METER EXPIRED CC
				// *  "plate_id":"1322731",
				// *  "state":"PA"
				// *  "ticket_number":2905938,
				// *  "zip_code":"19104",  if it's unknown (empty), assign 0.
				
				String date = violation_info[0];
				int fine = Integer.parseInt(violation_info[1]);
				String violation = violation_info[2];
				int plate_id = Integer.parseInt(violation_info[3]);
				String state = violation_info[4];
				int ticket_number = Integer.parseInt(violation_info[5]);
				int zip_code=0;
				
				// if zip_code data missing replaced with 0
				if (violation_info.length != 7 ) {
					zip_code = 0;
				} else {
					zip_code = Integer.parseInt(violation_info[6]);
				}
				
				parking_violations.add(new Parking( date,  fine,  violation,  plate_id,  state, ticket_number, zip_code ));
			}
			
				in.close();
			
			}
			catch (FileNotFoundException e) {
				System.out.println("Filename you enterted is incorrect") ; 
				e.printStackTrace();
			}catch (IllegalStateException e) {
				e.printStackTrace();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
		
		return parking_violations;
		
	}

	
	// FOR TEST FUNCTION 
	public static void main(String[] args) {

		ParkingCSVReader cr = new ParkingCSVReader("parking.csv");

		List<Parking> viols = new ArrayList<>();

		viols = cr.getAllParkingViolations();

		System.out.println(viols.size());

	}
	
}

