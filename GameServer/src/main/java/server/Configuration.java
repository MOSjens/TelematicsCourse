package server;

import java.io.*;
import java.util.*;

/**
 * Class to read Configuration info from config file
 * 
 * @author IG4
 *
 */
public class Configuration {
	InputStream inputStream;
	public int amountRounds;
	public int categoryTimeout;
	public int questionTimeout;
	public int answerTimeout;
	
	public Configuration() {
			try {
				this.getPropValues();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
 
	public void getPropValues() throws IOException {
 
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			// get the property value and print it out
			amountRounds = Integer.parseInt(prop.getProperty("amountRounds"));
			amountRounds = Integer.parseInt(prop.getProperty("categoryTimeout"));
			amountRounds = Integer.parseInt(prop.getProperty("questionTImeout"));
			amountRounds = Integer.parseInt(prop.getProperty("answerTimeout"));
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	}
}
