package de.ifgi.lodum.parser;

/**
 * Defines all supported cafeterias.
 * building uris replaced with organization uris of UniA-Z
 * 
 * @author steffan
 *
 */
public enum CafeteriaTyp {
	
	DaVinci("http://www.studentenwerk-muenster.de/de/essen-a-trinken/mensen/da-vinci", "<http://data.uni-muenster.de/context/uniaz/d46d2d2c4b3ea6341254a9649e38678f>","11:30","14:30"),
	Aasee("http://www.studentenwerk-muenster.de/de/essen-a-trinken/mensen/mensa-am-aasee", "<http://data.uni-muenster.de/context/uniaz/70ad738c960cc5e88e5e8d8ac1b5975e>","11:45","14:30"),
	Ring("http://www.studentenwerk-muenster.de/de/essen-a-trinken/mensen/mensa-am-ring", "<http://data.uni-muenster.de/context/uniaz/8ac770e149aa52077f85189c390e9571>","11:30","14:00"),
	Bispinghof("http://www.studentenwerk-muenster.de/de/essen-a-trinken/mensen/bispinghof", "<http://data.uni-muenster.de/context/uniaz/MensaBispinghof>","11:30","14:30");
	//No link to organization
	
	private String url;
	private String uri;
	private String startTime;
	private String endTime;
	
	private CafeteriaTyp(String url, String uri, String startTime, String endTime) {
		
		this.url = url;
		this.uri = uri;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public String getUrl(){
		
		return url;
	}
	
	public String getURI(){
		
		return uri;
	}
	
	public String getStartTime(){
		
		return startTime;
	}
	
	public String getEndTime(){
		
		return endTime;
	}
}
