package de.ifgi.lodum.sparql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The {@link TripleStoreBridge} provides a connection to a SPARQL endpoint.
 * 
 * @author steffan
 * 
 */
public class TripleStoreBridge {

	private TripleStoreBridge() {
	}

	/**
	 * Inserts triples into a triple store.
	 * 
	 * @param endpoint the URL to the endpoint
	 * @param msg the triples
	 * @param graph the target graph
	 * @param prefix any kind of prefixes for the triples
	 * 
	 * @throws IOException
	 */
	public static void insertData(String endpoint, String msg, String graph,
			String prefix) throws IOException {
		
		//get timestamp 
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = java.util.Calendar.getInstance().getTime();	
		String timestamp = simpleDateFormat.format(date);
		
		
		//message extending:
		//msg = msg + food additives
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("<http://data.uni-muenster.de/context/food/food-additive10> gr:description \"contains traces of phenylalanine\"@en, \"enthält eine Phenylalaninquelle\"@de .");
		stringBuilder.append("\n");
		stringBuilder.append("<http://data.uni-muenster.de/context/food/food-additive12> gr:description \"contains alcohol\"@en, \"enthält Alkohol\"@de .");
		stringBuilder.append("\n");
		stringBuilder.append("<http://data.uni-muenster.de/context/food/food-additive1> gr:description \"contains artificial colouringcontains preservatives\"@en, \"mit Farbstoff\"@de .");
		stringBuilder.append("\n");
		stringBuilder.append("<http://data.uni-muenster.de/context/food/food-additive2> gr:description \"contains preservatives\"@en, \"mit Konservierungsstoff\"@de .");
		stringBuilder.append("\n");
		stringBuilder.append("<http://data.uni-muenster.de/context/food/food-additive3> gr:description \"contains anti-oxidaants\"@en, \"mit Antioxidationsmittel\"@de .");
		stringBuilder.append("\n");
		stringBuilder.append("<http://data.uni-muenster.de/context/food/food-additive4> gr:description \"contains flavour enhancers\"@en, \"mit Geschmacksverstärker\"@de .");
		stringBuilder.append("\n");
		stringBuilder.append("<http://data.uni-muenster.de/context/food/food-additive5> gr:description \"geschwefelt\"@de, \"treated with sulphur\"@en .");
		stringBuilder.append("\n");
		stringBuilder.append("<http://data.uni-muenster.de/context/food/food-additive6> gr:description \"geschwärzt\"@de, \"stained black\"@en .");
		stringBuilder.append("\n");
		stringBuilder.append("<http://data.uni-muenster.de/context/food/food-additive7> gr:description \"gewachst\"@de, \"waxed\"@en .");
		stringBuilder.append("\n");
		stringBuilder.append("<http://data.uni-muenster.de/context/food/food-additive8> gr:description \"contains phosphates\"@en, \"mit Phosphat\"@de .");
		stringBuilder.append("\n");
		stringBuilder.append("<http://data.uni-muenster.de/context/food/food-additive9> gr:description \"contains sweetener\"@en, \"mit Süssungsmittel\"@de .");
		stringBuilder.append("\n");
		stringBuilder.append(msg);
		msg = stringBuilder.toString();
		
		
		
		
		//adding the metadata of the named graph
		prefix = prefix + "PREFIX dc: <http://dublincore.org/2010/10/11/dcelements.rdf#> \n";
		//reset the string builder
		stringBuilder = new StringBuilder();
		stringBuilder.append(graph);
		stringBuilder.append("\n");
		stringBuilder.append("dc:creator \"Script: cafeteriaLodumIntegration.jar (grabs menus from the corresponding cafeteria homepage)"+"\"^^xsd:string;");
		stringBuilder.append("\n");
		stringBuilder.append("dc:date \""+timestamp+"\"^^xsd:dateTime.");
		stringBuilder.append("\n");
		String metadata = stringBuilder.toString();
		
		
		//deleting the old timestamp
		
		stringBuilder = new StringBuilder();
		stringBuilder.append("DELETE WHERE {\n");
		stringBuilder.append("GRAPH " + graph + " {\n");
		stringBuilder.append(graph + " dc:date ?timestamp \n } \n } ;");
		String deleteTimestamp = stringBuilder.toString();
				
				
		msg = "update= "+ prefix + deleteTimestamp + "INSERT DATA{ GRAPH " + graph + " \n {" + metadata + msg
				+ "}}";
		
		
		//Print the sparql request (for debugging purposes)
		System.out.println(msg);
		
		URL queryURL;
		URLConnection urlConn;

		queryURL = new URL(endpoint);
		urlConn = queryURL.openConnection();

		urlConn.setDoOutput(true);
		urlConn.setDoInput(true);
		urlConn.setUseCaches(false);

		OutputStream outputStream = urlConn.getOutputStream();
		
		
		OutputStreamWriter writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
	     
	    writer.write(msg);
		
	    writer.flush();
	    writer.close();
	    
	    
	    BufferedReader in = new BufferedReader(new 
	    		InputStreamReader(urlConn.getInputStream()));

	    		String aLine = "";
	    		while ((aLine = in.readLine()) != null) {
	    			System.out.println(aLine); 

	    		}
	    		
	    		in.close();
	    
	}
}
