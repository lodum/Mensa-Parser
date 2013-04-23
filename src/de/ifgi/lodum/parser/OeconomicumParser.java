package de.ifgi.lodum.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.ifgi.lodum.domain.Price;

public class OeconomicumParser extends AbstractCafeteriaParser {

	
	protected Price getPrice(String s, String menu, String weekday) {

		//we dont need the weekday here...
		int beginIndex = s.indexOf("Preise Stud. ");
		
		int endIndex = s.substring(beginIndex).indexOf("<br");

		String price1 = s.substring(beginIndex, beginIndex + endIndex);

		Pattern p = Pattern.compile("\\d,\\d+");
		Matcher m = p.matcher(price1);

		String first, second;

		m.find();
		first = m.group();

		m.find();
		second = m.group();

		Price price = new Price(first, second);

		return price;

	}
	
}
