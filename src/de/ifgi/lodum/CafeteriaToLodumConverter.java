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

		try {
			List<Menu> menus = CafeteriaParser.getCafeteria(CafeteriaTyp.Aasee);

			for (Menu currentMenu : menus) {

				TripleStoreBridge.insertData(Cfg.ENDPOINT, Cfg.USERNAME, Cfg.PASSWORD,
						currentMenu.toTurtle(), Cfg.GRAPH, prefix);

			}
		} catch (Exception e) {
			String msg = "Cannot insert Mensa am Aasee data";
			System.err.println(msg);
			e.printStackTrace();
			MailSender.sentMessage(msg + e.getMessage());
		}

		try {
			List<Menu> menus2 = CafeteriaParser
					.getCafeteria(CafeteriaTyp.DaVinci);

			for (Menu currentMenu2 : menus2) {

				TripleStoreBridge.insertData(Cfg.ENDPOINT, Cfg.USERNAME, Cfg.PASSWORD,
						currentMenu2.toTurtle(), Cfg.GRAPH, prefix);

			}
		} catch (Exception e) {
			String msg = "Cannot insert Mensa DaVinci data";
			System.err.println(msg);
			e.printStackTrace();
			MailSender.sentMessage(msg + e.getMessage());
		}

		try {
			List<Menu> menus3 = CafeteriaParser.getCafeteria(CafeteriaTyp.Ring);

			for (Menu currentMenu3 : menus3) {

				TripleStoreBridge.insertData(Cfg.ENDPOINT, Cfg.USERNAME, Cfg.PASSWORD,
						currentMenu3.toTurtle(), Cfg.GRAPH, prefix);

			}
		} catch (Exception e) {
			String msg = "Cannot insert Mensa am Ring data";
			System.err.println(msg);
			e.printStackTrace();
			MailSender.sentMessage(msg + e.getMessage());
		}

		try {
			List<Menu> menus4 = CafeteriaParser
					.getCafeteria(CafeteriaTyp.Bispinghof);

			for (Menu currentMenu4 : menus4) {

				TripleStoreBridge.insertData(Cfg.ENDPOINT, Cfg.USERNAME, Cfg.PASSWORD,
						currentMenu4.toTurtle(), Cfg.GRAPH, prefix);

			}
		} catch (Exception e) {
			String msg = "Cannot insert Mensa Bispinghof data";
			System.err.println(msg);
			e.printStackTrace();
			MailSender.sentMessage(msg + e.getMessage());
		}
		
//		try {
//			List<Menu> menus5 = CafeteriaParser
//					.getCafeteria(CafeteriaTyp.Oeconomicum);
//
//			for (Menu currentMenu5 : menus5) {
//
//				System.out.println(currentMenu5.toTurtle());
//				
//				TripleStoreBridge.insertData(Cfg.ENDPOINT, Cfg.USERNAME, Cfg.PASSWORD,
//						currentMenu5.toTurtle(), Cfg.GRAPH, prefix);
//
//			}
//		} catch (Exception e) {
//			String msg = "Cannot insert Bistro Oeconomicum data";
//			System.err.println(msg);
//			e.printStackTrace();
//			MailSender.sentMessage(msg + e.getMessage());
//		}

	}

}
