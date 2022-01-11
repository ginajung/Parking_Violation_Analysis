package edu.upenn.cit594.data;


public class Parking {

	
	private final String date;
	private final int fine;
	private final String violation;
	private final int plate_id;
	private final String state;
	private final int ticket_number;
	private final int zip_code;
	
	
	/***
	 * Constructor for the parking. 
	 * @param keep all data points for later use.
	 */
	
	public Parking (String date, int fine, String violation, int plate_id, String state,int ticket_number,int zip_code ) {
		this.date = date;
		this.fine = fine;
		this.violation = violation;
		this.plate_id =plate_id;
		this.state=state;
		this.ticket_number=ticket_number;
		this.zip_code =zip_code;

	}

	
	
	public String getDate() {
		return date;
	}

	public int getFine() {
		return fine;
	}
	
	public String getViolation() {
		return violation;
	}

	public int getPlate_id() {
		return plate_id;
	}

	public String getState() {
		return state;
	}

	public int getTicket_number() {
		return ticket_number;
	}

	public int getZip_code() {
		return zip_code;
	}

	

	
}
	
