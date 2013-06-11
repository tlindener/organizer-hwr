package network.objects;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import network.RequestHandler;

import org.apache.commons.codec.binary.Base64;
import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Group;
import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

/**
 * this class provides several methods for generating Strings or modifying them.
 * In general it uses the given {@link AbstractOrganizerObject} to generate the
 * GET-Commands.
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public class Utils {
	/**
	 * HashMap containing the plurals of the class names.
	 */
	private static HashMap<Class<? extends AbstractOrganizerObject>, String> plurals;
	/**
	 * Static constructor creates and fills the {@link #plurals}
	 */
	static {
		plurals = new HashMap<Class<? extends AbstractOrganizerObject>, String>();
		plurals.put(User.class, "User");
		plurals.put(Group.class, "Groups");
		plurals.put(Calendar.class, "Calendar");
		plurals.put(CalendarEntry.class, "CalendarEntries");
		plurals.put(Room.class, "Rooms");
		plurals.put(Invite.class, "Invites");
	}

	/**
	 * Creates the Login-Command from the mail address and password
	 * 
	 * @param mail
	 *            user to login
	 * @param password
	 *            linked to the mail address
	 * @return the GET-Command for a login
	 */
	public static String getCompleteLoginCommand(String mail, String password) {
		String cmd = "Login";
		cmd += getParameterString(getParameterMailAddress(mail),
				getParameterPassword(password));
		return cmd;
	}

	/**
	 * Encodes a String in 3 steps:
	 * <ol>
	 * <li>Create the SHA-512 hash value
	 * <li>Create an ASCII String from hash
	 * <li>Encode the ASCII String with Base64
	 * </ol>
	 * 
	 * @param string
	 *            to encode
	 * @return the encoded String
	 */
	public static String encodeString(String string) {
		try {
			MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
			byte[] output = sha512.digest(string.getBytes());
			string = new String(output, "ASCII");
			string = Base64.encodeBase64String(string.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		string = parseStringToHTTP(string);
		return string;
	}

	/**
	 * Returns the mail address represented as a HTTP parameter
	 * 
	 * @param mail
	 *            - value of the parameter
	 * @return the HTTP parameter
	 */
	public static String getParameterMailAddress(String mail) {
		return "MailAddress=" + mail;
	}

	/**
	 * Returns the password represented as a HTTP parameter. The password will
	 * be encoded by using {@link #encodeString(String)}
	 * 
	 * @param password
	 *            - value of the parameter
	 * @return the HTTP parameter
	 */
	public static String getParameterPassword(String password) {
		return "Password=" + encodeString(password);
	}

	/**
	 * Returns the OBJECTNAME_Id represented as a HTTP parameter. Where
	 * OBJECTNAME_ is the name of the class represented by the given object.
	 * 
	 * @param obj
	 *            - {@link AbstractOrganizerObject} the
	 *            {@link AbstractOrganizerObject#getID()} is invoked on.
	 * @return the HTTP parameter
	 */
	public static <T extends AbstractOrganizerObject> String getParameterOwnId(
			T obj) {
		return obj.getClass().getSimpleName() + "Id=" + obj.getID();
	}

	/**
	 * Returns the HTTP parameter of the {@link RequestHandler#authString}.
	 * 
	 * @param authString
	 *            of the current logged in user
	 * @return GET-Command with authentication
	 */
	public static String getParameterUserAuth(String authString) {
		return "userAuth=" + authString;
	}

	/**
	 * Creates the whole command for requesting objects by a set property <br>
	 * <b> PARAMETERS ARE ADDED BY THIs METHOD</b>. That is why you should not
	 * add them by using the {@link #getParameterString(String...)} method.
	 * 
	 * @param obj
	 *            the command has to built for
	 * @return GET-Command
	 * @throws IllegalArgumentException
	 */
	public static <T extends AbstractOrganizerObject> String getCompleteByPropertyCommand(
			T obj, String authString) throws IllegalArgumentException {
		String command = makeGetAllCommand(obj)
				+ convertPropertyValueArray(obj.getProperty());
		command += "&" + getParameterUserAuth(authString);
		return command;
	}

	/**
	 * Creates the parameters for the
	 * {@link #getCompleteByPropertyCommand(AbstractOrganizerObject, String)}
	 * method
	 * 
	 * @param propertyValuePair
	 *            of the object to search for
	 * @return the HTTP representation of the property value pair
	 */
	private static String convertPropertyValueArray(String[] propertyValuePair) {
		return "By" + propertyValuePair[0] + "?" + propertyValuePair[0] + "="
				+ propertyValuePair[1];
	}

	/**
	 * Creates the command for requesting a single object by its ID
	 * 
	 * @param obj
	 *            which is requested containing its ID
	 * @return GET-Command
	 */
	public static <T extends AbstractOrganizerObject> String makeGetByOwnIdCommand(
			T obj) {
		String className = obj.getClass().getSimpleName();
		String command = "Get" + className + "ById";
		return command;
	}

	/**
	 * Creates the command for removing a single object by its ID
	 * 
	 * @param obj
	 *            to remove containing its ID
	 * @return GET-Command
	 */
	public static <T extends AbstractOrganizerObject> String makeRemoveByOwnIdCommand(
			T obj) {
		String className = obj.getClass().getSimpleName();
		String command = "Remove" + className;
		return command;
	}

	/**
	 * Creates the command for requesting all objects of the given type
	 * 
	 * @param obj
	 *            whose type is requested
	 * @return GET-Command
	 * @throws IllegalArgumentException
	 */
	public static <T extends AbstractOrganizerObject> String makeGetAllCommand(
			T obj) throws IllegalArgumentException {

		if (plurals.get(obj.getClass()) == null) {
			throw new IllegalArgumentException("No plural for class "
					+ obj.getClass().getSimpleName());
		}
		String command = "GetAll" + plurals.get(obj.getClass());
		return command;
	}

	/**
	 * Creates the command to add a single object
	 * 
	 * @param obj
	 *            with full set attributes
	 * @return GET-Command
	 * @throws IllegalArgumentException
	 */
	public static <T extends AbstractOrganizerObject> String makeAddCommand(
			T obj) throws IllegalArgumentException {
		String command = "Add" + obj.getClass().getSimpleName();
		return command;
	}

	/**
	 * Creates the command to update a single object
	 * 
	 * @param obj
	 *            with full set attributes
	 * @return GET-Command
	 */
	public static <T extends AbstractOrganizerObject> String makeUpdateCommand(
			T obj) {
		String command = "Update" + obj.getClass().getSimpleName();
		return command;
	}

	/**
	 * Creates a {@link ArrayList}<{@link String}> containing all private fields
	 * of the given {@link AbstractOrganizerObject} represented as HTTP
	 * parameter
	 * 
	 * @param obj
	 *            with full set attributes
	 * @return a List of HTTP parameters
	 */
	public static <T extends AbstractOrganizerObject> ArrayList<String> getParameterStringList(
			T obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		ArrayList<String> parameters = new ArrayList<String>();
		for (Field f : fields) {
			if (f.getModifiers() != Modifier.PRIVATE)
				continue;
			f.setAccessible(true);
			try {
				Object value = f.get(obj);
				if (f.getName().equals("accepted")) {
					continue;
				}
				// if(value == null){
				// parameters.add(f.getName() + "=");
				// // throw new
				// IllegalArgumentException("Darf nicht null sein");
				// }
				if (value instanceof List<?>) {
					continue;
				} else if (value instanceof String) {
					parameters.add(f.getName() + "="
							+ parseStringToHTTP((String) value));
				} else if (value instanceof Date) {
					parameters.add(f.getName() + "="
							+ parseDateToNetDateTime((Date) value));
				} else {
					parameters.add(f.getName() + "=" + value);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			f.setAccessible(false);
		}
		return parameters;
	}

	/**
	 * Combines the given {@link String}(s) to a single HTTP parameter String by
	 * using <b>?</b> at the beginning and <b>&</b> between the parameters
	 * 
	 * @param parameters
	 * @return
	 */
	public static String getParameterString(String... parameters) {
		String parameterStr = "?";
		for (int i = 0; i < parameters.length; i++) {
			parameterStr += parameters[i];
			if (i < parameters.length - 1)
				parameterStr += "&";
		}
		return parameterStr;
	}

	/**
	 * Replaces characters by their HTTP equivalent
	 * 
	 * @param value
	 *            to parse to a HTTP-String
	 * @return the
	 */
	public static String parseStringToHTTP(String value) {
		value = value.replaceAll(" ", "%20");
		value = value.replaceAll("[+]", "%2B");
		return value;
	}

	/**
	 * Creates the command to accept an {@link Invite}
	 * 
	 * @return GET-Command
	 */
	public static String makeAcceptCommand() {
		return "AcceptInvite";
	}

	/**
	 * Creates the command to decline an {@link Invite}
	 * 
	 * @return GET-Command
	 */
	public static String makeDeclineCommand() {
		return "DeclineInvite";
	}

	/**
	 * Creates the command to add an {@link User} to a {@link Group}
	 * 
	 * @return GET-Command
	 */
	public static String makeAddUserToGroupCommand() {
		return "AddUserToGroup";
	}

	/**
	 * Creates the command to remove an {@link User} from a {@link Group}
	 * 
	 * @return GET-Command
	 */
	public static String makeRemoveUserFromGroupCommand() {
		return "RemoveUserToGroup";
	}

	/**
	 * Parses the Java-Date into a JSON value representing an C#-DateTime object
	 * 
	 * @param date
	 * @return String representation of C#-DateTime
	 */
	public static String parseDateToNetDateTime(Date date) {
		// 2008-11-01T19:35:00.0000000-07:00
		SimpleDateFormat formatted = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		return parseStringToHTTP(formatted.format(date));
	}

	public static void main(String[] args) {
		
		System.out.println(parseStringToHTTP("Das ist ein Test"));
		System.out.println(encodeString("Test"));
		System.out.println(parseDateToNetDateTime(new Date()));
	}
}