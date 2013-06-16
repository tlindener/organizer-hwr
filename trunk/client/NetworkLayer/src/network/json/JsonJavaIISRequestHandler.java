/**
 * 
 */
package network.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import network.RequestHandler;
import network.utilities.NetDateTimeAdapter;
import network.utilities.ParseUtils;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Group;
import organizer.objects.types.Invite;
import organizer.objects.types.User;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

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
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String jsonString = reader.readLine();
			connection.disconnect();
			return jsonString;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
