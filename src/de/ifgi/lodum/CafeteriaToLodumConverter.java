package de.ifgi.lodum;

import java.util.List;

import de.ifgi.lodum.domain.Menu;
import de.ifgi.lodum.mail.MailSender;
import de.ifgi.lodum.parser.CafeteriaParser;
import de.ifgi.lodum.parser.CafeteriaTyp;
import de.ifgi.lodum.sparql.TripleStoreBridge;

public class CafeteriaToLodumConverter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String prefix = "PREFIX gr: <http://purl.org/goodrelations/v1#> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n";

		try{
		List<Menu> menus = CafeteriaParser.getCafeteria(CafeteriaTyp.Aasee);

		for (Menu currentMenu : menus) {
			
			TripleStoreBridge.insertData(Cfg.ENDPOINT, currentMenu.toTurtle(),
					Cfg.GRAPH, prefix);

		}
		}catch (Exception e) {
			String msg = "Cannot insert Aasee data";
			System.err.println(msg);
			e.printStackTrace();
			MailSender.sentMessage(msg+e.getMessage());
		}

		try{
		List<Menu> menus2 = CafeteriaParser.getCafeteria(CafeteriaTyp.DaVinci);

		for (Menu currentMenu2 : menus2) {

			TripleStoreBridge.insertData(Cfg.ENDPOINT, currentMenu2.toTurtle(),
					Cfg.GRAPH, prefix);

		}
		}
		catch (Exception e) {
			String msg = "Cannot insert DaVinci data";
			System.err.println(msg); 
			e.printStackTrace();
			MailSender.sentMessage(msg+e.getMessage());
		}

		
		try{
		List<Menu> menus3 = CafeteriaParser.getCafeteria(CafeteriaTyp.Ring);

		for (Menu currentMenu3 : menus3) {

			TripleStoreBridge.insertData(Cfg.ENDPOINT, currentMenu3.toTurtle(),
					Cfg.GRAPH, prefix);

		}
		}catch (Exception e) {
			String msg = "Cannot insert Ring data";
			System.err.println(msg);
			e.printStackTrace();
			MailSender.sentMessage(msg+e.getMessage());
		}

		try{
		List<Menu> menus4 = CafeteriaParser
				.getCafeteria(CafeteriaTyp.Bispinghof);

		for (Menu currentMenu4 : menus4) {

			TripleStoreBridge.insertData(Cfg.ENDPOINT, currentMenu4.toTurtle(),
					Cfg.GRAPH, prefix);

		}
		}catch (Exception e) {
			String msg = "Cannot insert Bispinghof data";
			System.err.println(msg);
			e.printStackTrace();
			MailSender.sentMessage(msg+e.getMessage());
		}

	}

}
