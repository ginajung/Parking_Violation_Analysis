package edu.upenn.cit594.data;

public class Property {
	private double marketValue; /* estimated dollar value of home to calc property tax */
	private double totalLivableArea; /* size of the home in square feet */
	private int zipCode;
	
	/***
	 * Constructor for the property. 
	 * @param marketValue
	 * @param totalLivableArea
	 * @param zipCode
	 */
	public Property (double marketValue, double totalLivableArea, int zipCode) {
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		this.zipCode = zipCode;
	}
	
	public double getMarketValue() {
		return marketValue;
	}
	
	public double getTotalLivableArea() {
		return totalLivableArea;
	}
	public int getZipCode() {
		return zipCode;
	}

}
