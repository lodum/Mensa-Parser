package de.ifgi.lodum;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.ifgi.lodum.domain.Menu;
import de.ifgi.lodum.parser.CafeteriaParser;
import de.ifgi.lodum.parser.CafeteriaTyp;

public class MensaEtlBridge {

	/**
	 * Return a turtle string for parsed data of a cafeteriaTyp
	 * @param cafeteriaTyp The cafeteriaTyp for collecting the data
	 * @return turtle string
	 */
	public static String getTurtle(CafeteriaTyp cafeteriaTyp) {

			String turtle = getTurtleHeader();
			
			// Get all menus
			List<Menu> menus = CafeteriaParser.getCafeteria(cafeteriaTyp);
			
			for (Menu currentMenu : menus) {

				turtle += currentMenu.toTurtle() + "\n\n";

			}	
			
			return turtle;
	}
	
	/**
	 * Gets the overall turtle header
	 * @return turtle header string
	 */
	private static String getTurtleHeader() {
		
		String prefix = "@prefix gr: <http://purl.org/goodrelations/v1#> . \n"
				+ "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> . \n"
				+ "@prefix dc: <http://dublincore.org/2010/10/11/dcelements.rdf#> . \n\n";
		// get timestamp
				// SimpleDateFormat simpleDateFormat = new
				// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss");
				Date date = java.util.Calendar.getInstance().getTime();
				String timestamp = simpleDateFormat.format(date);

				
				// adding the metadata of the named graph
				// reset the string builder
				StringBuilder stringBuilder = new StringBuilder();
				
				stringBuilder.append(prefix);
				stringBuilder.append("<http://data.uni-muenster.de/context/food/>");
				stringBuilder.append("\n");
				stringBuilder
						.append("dc:creator \"Script: cafeteriaLodumIntegration.jar (grabs menus from the corresponding cafeteria homepage)"
								+ "\"^^xsd:string;");
				stringBuilder.append("\n");
				stringBuilder.append("dc:date \"" + timestamp + "\"^^xsd:dateTime.");
				stringBuilder.append("\n");
				
				
				// message extending:
				// msg = msg + food additives

				stringBuilder
						.append("<http://data.uni-muenster.de/context/food/food-additive10> gr:description \"contains traces of phenylalanine\"@en , \"enth��lt eine Phenylalaninquelle\"@de .");
				stringBuilder.append("\n");
				stringBuilder
						.append("<http://data.uni-muenster.de/context/food/food-additive12> gr:description \"contains alcohol\"@en , \"enth��lt Alkohol\"@de .");
				stringBuilder.append("\n");
				stringBuilder
						.append("<http://data.uni-muenster.de/context/food/food-additive1> gr:description \"contains artificial colouringcontains preservatives\"@en , \"mit Farbstoff\"@de .");
				stringBuilder.append("\n");
				stringBuilder
						.append("<http://data.uni-muenster.de/context/food/food-additive2> gr:description \"contains preservatives\"@en , \"mit Konservierungsstoff\"@de .");
				stringBuilder.append("\n");
				stringBuilder
						.append("<http://data.uni-muenster.de/context/food/food-additive3> gr:description \"contains anti-oxidaants\"@en , \"mit Antioxidationsmittel\"@de .");
				stringBuilder.append("\n");
				stringBuilder
						.append("<http://data.uni-muenster.de/context/food/food-additive4> gr:description \"contains flavour enhancers\"@en , \"mit Geschmacksverst��rker\"@de .");
				stringBuilder.append("\n");
				stringBuilder
						.append("<http://data.uni-muenster.de/context/food/food-additive5> gr:description \"geschwefelt\"@de , \"treated with sulphur\"@en .");
				stringBuilder.append("\n");
				stringBuilder
						.append("<http://data.uni-muenster.de/context/food/food-additive6> gr:description \"geschw��rzt\"@de , \"stained black\"@en .");
				stringBuilder.append("\n");
				stringBuilder
						.append("<http://data.uni-muenster.de/context/food/food-additive7> gr:description \"gewachst\"@de , \"waxed\"@en .");
				stringBuilder.append("\n");
				stringBuilder
						.append("<http://data.uni-muenster.de/context/food/food-additive8> gr:description \"contains phosphates\"@en , \"mit Phosphat\"@de .");
				stringBuilder.append("\n");
				stringBuilder
						.append("<http://data.uni-muenster.de/context/food/food-additive9> gr:description \"contains sweetener\"@en , \"mit S��ssungsmittel\"@de .");
				stringBuilder.append("\n\n");

		
		return stringBuilder.toString();
	}
}
