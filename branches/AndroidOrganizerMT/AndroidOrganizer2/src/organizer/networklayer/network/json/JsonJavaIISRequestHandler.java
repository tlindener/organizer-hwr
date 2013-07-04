/**
 * 
 */
package organizer.networklayer.network.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Interface to communicate with the server by using the REST/JSON API. Objects
 * will be requested and filled by using the parameter objects set values (ID or
 * Property). It is required, that the names of attributes in the implemented
 * instances are named like the ones in the server objects. Just then the
 * JSON-Parser will work. </br> Because of the abstract parent class and the
 * usage of Java generic
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public class JsonJavaIISRequestHandler extends JsonJavaRequestHandler {
	
	
	public JsonJavaIISRequestHandler(String hostname, int port) {
		super(hostname, port);
	}

	/**
	 * Sends the GET-Command to the server and receives a JSON-String
	 * repesenting the answer.
	 * 
	 * @param request
	 * @return JSON-String representing the object or null, if there was an
	 *         exception during transmission.
	 */
	@Override
	public String sendGetToServer(String request) {
		try {
			super.connection = (HttpURLConnection) (new URL("http://" + hostname
					+ ":" + port + "/Organizer/OrganizerService.svc/" + request))
					.openConnection();
			connection.setRequestProperty("Accept-Charset", super.charset);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + super.charset );
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String jsonString = reader.readLine();
			connection.disconnect();
			if(jsonString == null) return null;
			byte[] array = jsonString.getBytes();
			return new String(array, super.charset);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
