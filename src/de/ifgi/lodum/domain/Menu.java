package de.ifgi.lodum.domain;

import java.util.List;

/**
 * A menu encapsulates the offered food for one day (or in some cases the whole week). 
 * Usually 3 menu items per day are available. It also includes the offering date.
 * 
 * 
 * @author steffan
 * 
 */
public class Menu implements RDFRepresentable{
	
	private static int menuCounter = 0;

	private String offeringStart;
	private String offeringEnd;
	private String mensaURI;

	private List<MenuItem> menuItems;

	public Menu(List<MenuItem> menuItems, String offeringStart, String offeringEnd, String mensaURI) {
		
		this.menuItems = menuItems;
		this.offeringStart = offeringStart;
		this.offeringEnd = offeringEnd;
		
		this.mensaURI = mensaURI;
	}
		
	public String toTurtle() {

		StringBuilder stringBuilder = new StringBuilder();
		
		for(MenuItem currentMenuItem : menuItems){
			
			stringBuilder.append(mensaURI+" gr:offers "+"<http://data.uni-muenster.de/context/food/offering/menu/"+offeringStart.substring(0, 10)+"-"+menuCounter+"> .");
			stringBuilder.append("\n");
			stringBuilder.append("<http://data.uni-muenster.de/context/food/offering/menu/"+offeringStart.substring(0, 10)+"-"+menuCounter+"> a gr:Offering ;");
			menuCounter++;
			stringBuilder.append("\n");
			//stringBuilder.append("gr:offers \""+mensaURI+"\";");
			//stringBuilder.append("gr:offers "+mensaURI+";"); Falsche Reihenfolge, umdrehen: 
			stringBuilder.append("\n");
			stringBuilder.append("gr:availabilityStarts \""+offeringStart+"\"^^xsd:dateTime;");
			stringBuilder.append("\n");
			stringBuilder.append("gr:availabilityEnds \""+offeringEnd+"\"^^xsd:dateTime;");
			stringBuilder.append("\n");
			
			stringBuilder.append(currentMenuItem.toTurtle());
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
}
