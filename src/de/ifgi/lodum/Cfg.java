package de.ifgi.lodum;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provides the connection to the property file.
 * 
 * @author steffan
 *
 */
public class Cfg {
	
	public final static String GRAPH;
	public final static String ENDPOINT;
	public final static String FROM;
	public final static String TO;
	
	static{
		
		Properties properties = new Properties();
		InputStream inputStream = null;
		inputStream = Cfg.class.getResourceAsStream("/cfg.properties");
		
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	try {
		inputStream.close();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
		     
		 GRAPH = properties.getProperty("graph");
		 ENDPOINT = properties.getProperty("endpoint");
		 FROM = properties.getProperty("from");
		 TO = properties.getProperty("to");
	}
	
	private Cfg() {}
	

}
