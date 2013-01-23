package de.ifgi.lodum.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.ifgi.lodum.domain.Menu;
import de.ifgi.lodum.domain.MenuItem;
import de.ifgi.lodum.domain.Price;

/**
 * The cafeteria 'Bispinghof' is a special case. Therefore most of the methods from the {@link AbstractCafeteriaParser} are useless.
 * 
 * @author steffan
 *
 */
final class BispinghofParser extends AbstractCafeteriaParser{
	
	@Override
	public List<Menu> getCafeteria(CafeteriaTyp cafeteriaTyp) {
			
		//In fact the bispinghof mensa is so different from the others that I have to implement it from scratch.
		WebFile webFile = null;

		try {
			webFile = new WebFile(cafeteriaTyp.getUrl());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Object content = webFile.getContent();

		String html = (String) content;

		List<Menu> menuList = new ArrayList<Menu>();
		
		for(Date currentDate : getDates(html)){
			

		menuList.add(new Menu(getMenuItemList(html), toStartDate(
				currentDate, cafeteriaTyp), toEndDate(
				currentDate, cafeteriaTyp), cafeteriaTyp.getURI()));
		}

		return menuList;

	}
	
	/**
	 * Returns the {@link MenuItem} for the three menus.
	 * 
	 * @param s the html page
	 * @return a {@link List} of {@link MenuItem}
	 */
	private List<MenuItem> getMenuItemList(String s) {

		List<MenuItem> menuItems = new ArrayList<MenuItem>();

		String dish1 = getDish(s, "menu1_menu");
		String dish2 = getDish(s, "menu2_menu");
		String dish3 = getDish(s, "menu3_menu");

		menuItems.add(new MenuItem(removeDigits(dish1), getPrice(s, "menu1_preis"),
				getFoodAdditives(dish1)));
		menuItems.add(new MenuItem(removeDigits(dish2), getPrice(s, "menu2_preis"),
				getFoodAdditives(dish2)));
		menuItems.add(new MenuItem(removeDigits(dish3), getPrice(s, "menu3_preis"),
				getFoodAdditives(dish3)));

		return menuItems;

	}
	
	/**
	 * Returns the {@link Price} for a menu.
	 * @param s the html page
	 * @param menu the menu, e.g. 'menu2_preis'
	 * @return the {@link Price}
	 */
	private Price getPrice(String s,String menu){
		
		int beginIndex = s.indexOf(menu);
		
		int endIndex = s.substring(beginIndex).indexOf("<td");

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
	
	/**
	 * Returns a {@link List} of {@link Date} were the dishes are offered.
	 * @param s the html page
	 * @return a {@link List} of {@link Date}
	 */
	private List<Date> getDates(String s){
		
		List<Date> dates = new ArrayList<Date>();
		
		int beginIndex = s.indexOf("Wochenspeiseplan");
		int endIndex = s.indexOf("-", beginIndex);
		int endIndex2 = s.indexOf("</h3", beginIndex);

		String startDay = s.substring(beginIndex + 17, endIndex-1);
		String endDay = s.substring(endIndex+2,endIndex2);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		
		Date startDate = null;
		Date endDate = null;
		
		try {
			startDate = simpleDateFormat.parse(startDay);
			endDate =  simpleDateFormat.parse(endDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		dates.add(startDate);
		
		while(endDate.after(startDate)){
			
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			c.add(Calendar.DATE, 1);  // number of days to add
			startDate = c.getTime();
			
			dates.add(startDate);
		}
		return dates;
		
	}
	
}
