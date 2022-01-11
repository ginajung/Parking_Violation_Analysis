package edu.upenn.cit594.logging;

/**
 * USE SIGNLETON design pattern 
 * 
 * Logging
For each “flu tweet,” write its text and the name of the state in which it originated to the
log file that was specified when the program started. 
Be sure to write these to the log file and not to the screen! 
When writing to the log file,  you should write a single tweet per line in the format state, tab, tweet. 
For example:
Alabama I have the flu!

Each tweet should only be written to the log file once, but they can be written in any
order you’d like. If a log file with the same name already exists when the program starts,
its contents should be overwritten.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Logging {


	// making output file "log.txt" 
	
	private PrintWriter out;
	
	// 1. private constructor
	public Logging(String filename) {
		File f = new File(filename);

		try { 
			if (f.exists() && !f.isDirectory()) {
				out = new PrintWriter (new FileOutputStream(new File(filename), true));
			}
			else {
				out = new PrintWriter(new File(filename));
			}
			
			//out = new PrintWriter(new File(filename));

		}
		
		catch (Exception e) {}

		
		}
	
	// 2. singleton instance  
	private static Logging instance ;
	
	//3. singletone accessor method
	public static Logging getInstance(String filename) { 
		
		if(instance == null) instance = new Logging(filename);
		
		return instance;
				}
	
	//non-static method
	public void log(String msg) {
		out.println(msg);
		out.flush();
	}

	
}
