package de.ifgi.lodum.domain;

/**
 * Represents the price of the offered food. The min values is the student price and the max value the price for
 * others. The currency is always 'EUR'.
 * 
 * @author steffan
 *
 */
public class Price implements RDFRepresentable {
	
	private String min,max; 
	
	public Price(String min, String max) {
		
		this.min = min;
		this.max = max;
	}

	public String toTurtle() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("gr:hasPriceSpecification");
		builder.append("\n");
		builder.append(" [ a gr:UnitPriceSpecification ;");
		builder.append("\n");
		builder.append("gr:hasCurrency \"EUR\" ;");
		builder.append("\n");
		builder.append("gr:hasMaxCurrencyValue \""+max+"\" ;");
		builder.append("\n");
		builder.append("gr:hasMinCurrencyValue \""+min+"\"");
		builder.append("\n");
		builder.append("    ] ;");
		
		return builder.toString();
	}

}
