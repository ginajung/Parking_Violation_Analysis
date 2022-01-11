package edu.upenn.cit594.datamanagement;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.logging.Logging;


public class PropertyFileReader {
	protected String filename;
	
	public PropertyFileReader(String filename) {
		this.filename = filename;
	}
	
	public List<Property> getAllProperties() {
		List<Property> properties = new ArrayList<Property>();
		
		BufferedReader bf = null;
		try {
			
			bf = new BufferedReader(new FileReader(filename));
			
			//System.out.println("Reading in the files");
			String headers = bf.readLine();
			//System.out.println(headers);
			
			int [] filteredCol = matchHeaders(headers);
			
			String line;
			while (( line = bf.readLine()) != null) {
				
				String[] lineComponents = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				
				String zipCode = lineComponents[filteredCol[0]].trim();
				zipCode = zipCode.substring(0, Math.min(zipCode.length(), 5));
				String marketValues = lineComponents[filteredCol[1]].trim();
				String totalLivableArea = lineComponents[filteredCol[2]].trim();
				
				double marketValuesInt = 0.0;
				double totalLivableAreaInt = 0.0;
				int zipCodeInt = 0;
				
				try {
					zipCodeInt = Integer.parseInt(zipCode);
					
				} catch (Exception e) {
					zipCodeInt = 0;
				}
				try {
					marketValuesInt = Double.parseDouble(marketValues);
					
				} catch (Exception e) {
					marketValuesInt = 0;
				}
				
				try {
					totalLivableAreaInt = Double.parseDouble(totalLivableArea);
					
				} catch (Exception e) {
					totalLivableArea = null;
				}
//				System.out.println ( "maarketValues, totalLivableArea, zipCode ");
//				
//				System.out.print (marketValuesInt + " , ");
//
//				System.out.print (totalLivableAreaInt  + " , ");
//				
//				System.out.print (zipCode + " , ");
//				
//				System.out.println();

				Property thisProp = new Property (marketValuesInt, totalLivableAreaInt, zipCodeInt);
				properties.add(thisProp);
				

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bf != null)
					bf.close();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		
			
		return properties;
		
	}
	

	private int[] matchHeaders(String line) {
		int [] columnNum = new int[3];
		String[] headers = line.split(",");
		
		for (int i = 0; i < headers.length; i++) {
			//System.out.println("current "+ i + "th header is " + headers[i]);
			if (headers[i].contentEquals("zip_code")) {
				//System.out.println("zip code is in column : "+ i);
				columnNum[0] = i;
			}
			else if (headers[i].contentEquals("market_value")) {
				//System.out.println("market_values is in column : "+ i);
				columnNum[1] = i;
			}
			else if (headers[i].contentEquals("total_livable_area")) {
				//System.out.println("total_livable_area is in column : "+ i);
				columnNum[2] = i;
			}
			
		}
		return columnNum;
	}
	
//
	public static void main(String[] args) {

		PropertyFileReader fr = new PropertyFileReader("properties.csv");

		List<Property> prop = new ArrayList<>();
		//System.out.println("Will call get all properties");
		
		prop = fr.getAllProperties();
	
		System.out.println(prop.size());
		
		int counter = 0;
		double totalMarketValue =0;
		double totalLivableArea =0;
		
		for (Property pro : prop) {
			if (pro.getZipCode() == 19102) {
				//System.out.println(pro.getZipCode() +":"+ pro.getMarketValue() +":"+ pro.getTotalLivableArea() );
				counter++;
				totalMarketValue +=pro.getMarketValue();
				totalLivableArea += pro.getTotalLivableArea();
				
			}
			
		}
		System.out.println(counter + ":"+ totalMarketValue + ":"+totalLivableArea);

	}
	

}
