package de.ifgi.lodum.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.ifgi.lodum.domain.Price;

/**
 * The adjusted parser for the 'Da Vinci' cafeteria.
 * 
 * @author steffan
 *
 */
final class DaVinciParser extends AbstractCafeteriaParser{
	
	@Override
	protected Price getPrice(String s, String menu, String weekday){
		
		//montag_menu3_preis
		int beginIndex = s.indexOf(weekday+menu+"_preis");
		
		int endIndex = s.substring(beginIndex).indexOf("<");

		String price1 = s.substring(beginIndex, beginIndex + endIndex);

		Pattern p = Pattern.compile("\\d,\\d+");
		Matcher m = p.matcher(price1);

		String first, second;

		if(!m.find()){
			//probably "tagespreis"
			return null;
		}
		first = m.group();

		m.find();
		second = m.group();

		Price price = new Price(first, second);

		return price;
		
	}
}
