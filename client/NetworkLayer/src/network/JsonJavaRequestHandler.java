/**
 * 
 */
package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import network.objects.NetDateTimeAdapter;
import network.objects.Utils;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
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
public class JsonJavaRequestHandler extends RequestHandler {
	/** The JSON-Paser */
	private Gson gson = null;

	/** The HTTP connection for sending GET-request */
	private HttpURLConnection connection = null;
	/** Hostname of the backend where the application is placed */
	private String hostname = "";
	/** Port of the backend application */
	private int port = -1;

	/**
	 * Default construtor to connect to the server
	 * 
	 * @param hostname
	 *            name or literal IP of the hosting server
	 * @param port
	 *            port number for the application
	 */
	public JsonJavaRequestHandler(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		init();
	}

	/**
	 * Initialize the JSON-Parser. <br>
	 * The {@link NetDateTimeAdapter} is added for parsing C#-DateTime Objects
	 */
	private void init() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new NetDateTimeAdapter());
		builder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
		gson = builder.create();
	}

	/**
	 * Requests an given object by using its own ID. Look on
	 * {@link AbstractOrganizerObject#getID()} for more information.
	 * 
	 * @param obj
	 *            Instance of {@link AbstractOrganizerObject} thats ID is used
	 *            to request an element from the backend.
	 * @return filled instance of {@link AbstractOrganizerObject} or null, if no
	 *         element with the given ID exists.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractOrganizerObject> T requestObjectByOwnId(T obj) {
		String getCmd = Utils.buildGetByOwnIdCommand(obj);
		getCmd = Utils.addUserAuth(getCmd, authString);
		String json = sendGetToServer(getCmd);
		try {
			return (T) gson.fromJson(json, obj.getClass());
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Sends the GET-Command to the server and receives a JSON-String
	 * repesenting the answer.
	 * 
	 * @param request
	 * @return JSON-String representing the object or null, if there was an
	 *         exception during transmission.
	 */
	private String sendGetToServer(String request) {

		try {
			connection = (HttpURLConnection) (new URL("http://" + hostname
					+ ":" + port + "/OrganizerService.svc/" + request))
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


	/**
	 * Requests all objects in the backend equals to the type of the given one.
	 * 
	 * @param obj
	 *            Instance of {@link AbstractOrganizerObject} thats type is used
	 *            to request all elements from the backend.
	 * @return {@link List}<{@link AbstractOrganizerObject}> filled with
	 *         instances or an empty oneF, if no element exists.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractOrganizerObject> List<T> requestAllObjects(T obj) {
		try {
			String getCmd = Utils.buildGetAllCommand(obj);
			getCmd = Utils.addUserAuth(getCmd, authString);
			// TODO replace by better code
			getCmd = getCmd.replaceAll("&", "?");
			String json = sendGetToServer(getCmd);
			List<JsonElement> tmp = gson.fromJson(json,
					new TypeToken<List<JsonElement>>() {
					}.getType());

			if (tmp == null)
				return new ArrayList<T>();

			List<T> result = new ArrayList<T>();
			for (int i = 0; i < tmp.size(); i++) {
				result.add((T) gson.fromJson(tmp.get(i), obj.getClass()));
			}
			return result;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return new ArrayList<T>();
	}

	/**
	 * Requests the first object of the given object type by using its set
	 * property. Look on {@link AbstractOrganizerObject#getProperty()} for more
	 * information.
	 * 
	 * @param obj
	 *            Instance of {@link AbstractOrganizerObject} thats set property
	 *            is used.
	 * @return filled instance of {@link AbstractOrganizerObject} or null, if
	 *         such object does not exist.
	 */
	@Override
	public <T extends AbstractOrganizerObject> T requestObjectByProperty(T obj) {
		// requesting all and taking the first element
		List<T> result = requestAllObjectsByProperty(obj);
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * Requests all objects of the given object type by using its set property.
	 * Look on {@link AbstractOrganizerObject#getProperty()} for more
	 * information.
	 * 
	 * @param obj
	 *            Instance of {@link AbstractOrganizerObject} whose set property
	 *            is used.
	 * @return {@link List}<{@link AbstractOrganizerObject}> filled with
	 *         instances or an empty {@link List}<
	 *         {@link AbstractOrganizerObject}>, if such object does not exist.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractOrganizerObject> List<T> requestAllObjectsByProperty(
			T obj) {
		try {
			String getCmd = Utils.buildGetByPropertyCommand(obj);
			getCmd = Utils.addUserAuth(getCmd, authString);
			String json = sendGetToServer(getCmd);

			List<JsonElement> tmp = gson.fromJson(json,
					new TypeToken<List<JsonElement>>() {
					}.getType());
			if (tmp == null)
				return new ArrayList<T>();
			List<T> result = new ArrayList<T>();
			for (int i = 0; i < tmp.size(); i++) {
				result.add((T) gson.fromJson(tmp.get(i), obj.getClass()));
			}
			return result;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}

		return new ArrayList<T>();
	}

	/**
	 * Adds new {@link User} to the database. The password is hashed,
	 * ASCII-based and Base64 encoded before it is sent to the server. With the
	 * mail address being necessary for the login it must be defined by the
	 * given {@link User}, otherwise an {@link IllegalArgumentException} is
	 * thrown.
	 * 
	 * @param user
	 *            defines the main attributes of the user, that should be added
	 * @param password
	 *            plain text password for secure login
	 * @return the {@link User} containing its set ID or null, if such an
	 *         {@link User} already exits.
	 */
	@Override
	public User registerNewUser(User user, String password) {
		if (user.getMailAddress() == null || user.getMailAddress().isEmpty())
			throw new IllegalArgumentException(
					"The mail address must not be empty or null");
		try {
			String getCmd = Utils.buildAddCommand(user);
			String encPassword = Utils.encodeString(password);
			encPassword = Utils.parseStringToHTTP(encPassword);
			
			getCmd += "&password=" + encPassword + "";
			String json = sendGetToServer(getCmd);
			Integer id = gson.fromJson(json, int.class);
			if (id == null || id == 0)
				return null;
			user.setID(id);
			return user;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Adds an new object of {@link AbstractOrganizerObject} to the database.<br>
	 * <b>For adding an {@link User} use
	 * {@link JsonJavaRequestHandler#registerNewUser(User, String)} instead this
	 * method.</b>
	 * 
	 * @param obj
	 *            defines the main attributes of the object, that should be
	 *            added.
	 * @return the obj containing its set ID or null, if such an object already
	 *         exists.
	 */
	@Override
	public <T extends AbstractOrganizerObject> T addObject(T obj) {
		if (obj instanceof User)
			throw new UnsupportedOperationException(
					"User must be added by method \"registerNewUser\"");
		try {
			String getCmd = Utils.buildAddCommand(obj);
			getCmd = Utils.addUserAuth(getCmd, authString);
			String json = sendGetToServer(getCmd);
			Integer id = gson.fromJson(json, int.class);
			if (id == null || id == 0)
				return null;
			obj.setID(id);
			return obj;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Removes an {@link AbstractOrganizerObject} from the database by using its
	 * ID.
	 * 
	 * @param obj
	 *            the object, which should be removed by its id.
	 * @return true if the object was removed, false otherwise.
	 */
	@Override
	public <T extends AbstractOrganizerObject> boolean removeObjectByOwnId(T obj) {
		String getCmd = Utils.buildRemoveByOwnIdCommand(obj);
		getCmd = Utils.addUserAuth(getCmd, authString);
		String json = sendGetToServer(getCmd);
		try {
			return (boolean) gson.fromJson(json, boolean.class);
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * Requests a {@link User} for the given mail address and password. <br>
	 * The credentials will be stored to ensure an authentication by a
	 * combination of the mail address and password.
	 * 
	 * @param mail
	 *            plain text
	 * @param password
	 *            plain text password, which will be hashed, ASCII-based and
	 *            Base64 encoded for the transmission
	 * @return the {@link User} linked to the given credentials or null, if such
	 *         a user does not exist.
	 */
	@Override
	public User login(String mail, String password) {

		String cmd = Utils.buildLoginCommand(mail, password);
		String json = sendGetToServer(cmd);
		try {
			User user = (User) gson.fromJson(json, User.class);
			authString = generateAuthenticationString(user.getID(), mail,
					password);
			return user;
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Sends a confirmation of an {@link Invite} to the Server. As result the ID
	 * of the generated {@link CalendarEntry} is returned.
	 * 
	 * @param inviteId
	 *            the ID of the {@link Invite} that should be confirmed
	 * @return the ID of the created {@link CalendarEntry} in the own
	 *         {@link Calendar} or 0, if there was an Error.
	 */
	@Override
	public int acceptInvite(int inviteId) {
		String getCmd = Utils.buildAcceptCommand(inviteId);
		getCmd = Utils.addUserAuth(getCmd, authString);
		String json = sendGetToServer(getCmd);
		try {
			return (int) gson.fromJson(json, int.class);
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * Sends a declining of an {@link Invite} to the Server.
	 * 
	 * @param inviteId
	 *            the ID of the {@link Invite} that should be confirmed
	 * @return 1 if there was no error, otherwise 0
	 */	
	@Override
	public int declineInvite(int inviteId) {
		String getCmd = Utils.buildDeclineCommand(inviteId);
		getCmd = Utils.addUserAuth(getCmd, authString);
		String json = sendGetToServer(getCmd);
		try {
			return (int) gson.fromJson(json, int.class);
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	@Override
	public <T extends AbstractOrganizerObject> boolean updateObject(T obj) {
		if (obj instanceof User)
			throw new UnsupportedOperationException(
					"User must be added by method \"registerNewUser\"");
		try {
			String getCmd = Utils.buildAddCommand(obj);
			getCmd = Utils.addUserAuth(getCmd, authString);
			String json = sendGetToServer(getCmd);
			Integer id = gson.fromJson(json, int.class);
			if (id == null || id == 0)
				return false;
			obj.setID(id);
			return true;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
