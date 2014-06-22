package de.ifgi.lodum.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.ifgi.lodum.domain.FoodAdditive;
import de.ifgi.lodum.domain.Menu;
import de.ifgi.lodum.domain.MenuItem;
import de.ifgi.lodum.domain.Price;

/**
 * A basic parser for cafeteria sites, which provides common methods for all cafeterias.
 * 
 * @author steffan
 * 
 */
abstract class AbstractCafeteriaParser {

	protected String menu1 = "_menu1";
	protected String menu2 = "_menu2";
	protected String menu3 = "_menu3";

	/**
	 * Returns a list of {@link Menu} for the given {@link CafeteriaTyp}.
	 * @param cafeteriaTyp
	 * @return a list of {@link Menu}
	 */
	public  List<Menu> getCafeteria(CafeteriaTyp cafeteriaTyp) {

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

		//the "main" cafeterias have montag, dienstag... as identifier
		
		menuList.add(new Menu(getMenuItemList(html, "Montag"), toStartDate(
				getDate(html, "Montag"), cafeteriaTyp), toEndDate(
				getDate(html, "Montag"), cafeteriaTyp), cafeteriaTyp.getURI()));
		menuList.add(new Menu(getMenuItemList(html, "Dienstag"), toStartDate(
				getDate(html, "Dienstag"), cafeteriaTyp), toEndDate(
				getDate(html, "Dienstag"), cafeteriaTyp), cafeteriaTyp.getURI()));
		menuList.add(new Menu(getMenuItemList(html, "Mittwoch"), toStartDate(
				getDate(html, "Mittwoch"), cafeteriaTyp), toEndDate(
				getDate(html, "Mittwoch"), cafeteriaTyp), cafeteriaTyp.getURI()));
		menuList.add(new Menu(getMenuItemList(html, "Donnerstag"), toStartDate(
				getDate(html, "Donnerstag"), cafeteriaTyp), toEndDate(
				getDate(html, "Donnerstag"), cafeteriaTyp), cafeteriaTyp
				.getURI()));
		menuList.add(new Menu(getMenuItemList(html, "Freitag"), toStartDate(
				getDate(html, "Freitag"), cafeteriaTyp), toEndDate(
				getDate(html, "Freitag"), cafeteriaTyp), cafeteriaTyp.getURI()));

		return menuList;

	}

	/**
	 * Converts a {@link Date} and time to the good relations format.
	 * @param date the date, e.g. 12.12.2012
	 * @param cafeteriaTyp the cafeteria for the time
	 * @return the converted date
	 */
	protected  String toStartDate(Date date, CafeteriaTyp cafeteriaTyp) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date) + "T" + cafeteriaTyp.getStartTime() + ":00Z";
	}

	/**
	 * Converts a {@link Date} and time to the good relations format.
	 * @param date the date
	 * @param cafeteriaTyp the cafeteria for the time
	 * @return the converted date
	 */
	protected static String toEndDate(Date date, CafeteriaTyp cafeteriaTyp) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date) + "T" + cafeteriaTyp.getEndTime() + ":00Z";
	}

	/**
	 * Extracts the {@link Date} for a offering. 
	 * @param s the htlm page
	 * @param weekday a day, e.g. 'Dienstag'
	 * @return the {@link Date}
	 */
	protected  Date getDate(String s, String weekday) {

		int beginIndex = s.indexOf(weekday);
		int endIndex = s.indexOf("</h3>", beginIndex);

		String day = s.substring(beginIndex + weekday.length() + 1, endIndex);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
		try {
			date = simpleDateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;

	}

	/**
	 * Returns a {@link List} of {@link MenuItem} for a specific weekday.
	 * @param s the html page
	 * @param weekday a weekday, e.g. 'Mittwoch'
	 * @return a {@link List} of {@link MenuItem}
	 */
	protected  List<MenuItem> getMenuItemList(String s, String weekday) {

		weekday = weekday.toLowerCase();

		List<MenuItem> menuItems = new ArrayList<MenuItem>();

		String dish1 = getDish(s, weekday + menu1);
		String dish2 = getDish(s, weekday + menu2);
		String dish3 = getDish(s, weekday + menu3);

		menuItems.add(new MenuItem(removeDigits(dish1), getPrice(s, menu1, weekday),
				getFoodAdditives(dish1)));
		menuItems.add(new MenuItem(removeDigits(dish2), getPrice(s, menu2, weekday),
				getFoodAdditives(dish2)));
		menuItems.add(new MenuItem(removeDigits(dish3), getPrice(s, menu3, weekday),
				getFoodAdditives(dish3)));

		return menuItems;

	}

	/**
	 * Returns the {@link Price} for a specific menu on a specific weekday.
	 * @param s the html page
	 * @param menu a menu, e.g. 'Men� I'
	 * @param weekday a weekday, e.g. 'Freitag'
	 * @return the {@link Price}
	 */
	protected Price getPrice(String s, String menu, String weekday) {

		//we dont need the weekday here...
		int beginIndex = -1;
		
		if (menu.equals(menu1)) {
			beginIndex = s.indexOf("Menü I");
		}
		if (menu.equals(menu2)) {
			beginIndex = s.indexOf("Menü II");
		}
		if (menu.equals(menu3)) {
			beginIndex = s.indexOf("Menü III");
		}
		
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

	/**
	 * Extracts the {@link FoodAdditive} from a dish.
	 * @param dish a {@link String} describing the dish. 
	 * @return a {@link List} of {@link FoodAdditive}
	 */
	protected  List<FoodAdditive> getFoodAdditives(String dish) {

		List<FoodAdditive> foodAdditives = new ArrayList<FoodAdditive>();

		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(dish);
		while (m.find()) {
			foodAdditives.add(new FoodAdditive(Integer.valueOf(m.group())));
		}

		return foodAdditives;
	}

	/**
	 * Removes numbers from a dish. This is useful to remove the {@link FoodAdditive} numbers.
	 * @param s the {@link String}
	 * @return a {@link String} without digits.
	 */
	protected  String removeDigits(String s) {

		// In case something like "3 Stck. N�rnberger Rostbratw�rstchen" is
		// offered
		if (s.length() > 4) {
			String first = s.substring(0, 4);

			return first.replaceAll(",", "").replaceAll("\"", "'")
					+ s.substring(4).replaceAll("[\\d]", "")
							.replaceAll(",", "").replaceAll("\"", "'").trim();
		}

		return s.replaceAll("[\\d]", "").replaceAll(",", "").replaceAll("\"", "'").trim();

	}

	/**
	 * Extracts the dish for a specific menu.
	 * @param s the html page
	 * @param menu the menu, e.g. _menu3
	 * @return the dish for a menu.
	 */
	protected  String getDish(String s, String menu) {

		int beginIndex = s.indexOf(menu);
		int endIndex = s.indexOf("</td>", beginIndex);

		String dish = s.substring(beginIndex + menu.length()+2, endIndex);
		
		return dish.replaceAll("\\<.*?>",""); // returns HTML tags from the text, which somethimes happens.
	}

	/**
	 * Copied from http://nadeausoftware.com/node/73#Code
	 * 
	 * discussion, which helped to fix problems with Umlaute
	 * http://www.ureader.de/msg/123014139.aspx
	 * 
	 * @author steffan
	 */
	static class WebFile {

		private Object content = null;

		/**
		 * Open a web file.
		 * 
		 * @param urlString
		 * @throws java.net.MalformedURLException
		 * @throws java.io.IOException
		 */
		public WebFile(String urlString) throws java.net.MalformedURLException,
				java.io.IOException {

			URL oracle = new URL(urlString);
			URLConnection yc = oracle.openConnection();
			
			//In order to grab the data we have to fake a client.
			yc.setRequestProperty("User-Agent",
					"Opera/9.80 (Macintosh; Intel Mac OS X; U; en) Presto/2.2.15 Version/10.00");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuilder out = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				out.append(inputLine);
				// System.out.println(inputLine);
			}
			in.close();

			content = out.toString();
		}

		/**
		 * Get the content.
		 * 
		 * @return the content - should be a string in our case
		 */
		public Object getContent() {
			return content;
		}

	}
}
