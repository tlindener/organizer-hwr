package network.utilities;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import network.RequestHandler;

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
public class ParseUtils {
	/**
	 * HashMap containing the plurals of the class names.
	 */
	private static HashMap<Class<? extends AbstractOrganizerObject>, String> plurals;
	private static HashMap<Character, String> urlReplacments;
	
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
		
		
		urlReplacments = new HashMap<Character, String>();
		urlReplacments.put(' ', "%20");
		urlReplacments.put('!', "%21");
		urlReplacments.put('"', "%22");
		urlReplacments.put('#', "%23");
		urlReplacments.put('$', "%24");		
		urlReplacments.put('%', "%25");		
		urlReplacments.put('&', "%26");		
		urlReplacments.put('\'', "%27");		
		urlReplacments.put('(', "%28");
		urlReplacments.put(')', "%29");		
		urlReplacments.put('*', "%2A");
		urlReplacments.put('+', "%2B");
		urlReplacments.put(',', "%2C");	
		
		urlReplacments.put('/', "%2F");	
		
		urlReplacments.put(':', "%3A");
		urlReplacments.put(';', "%3B");
		urlReplacments.put('<', "%3C");			
		urlReplacments.put('=', "%3D");		
		urlReplacments.put('>', "%3E");			
		urlReplacments.put('?', "%3F");		
		urlReplacments.put('@', "%40");	
		
		urlReplacments.put('[', "%5B");
		urlReplacments.put('\\', "%5C");
		urlReplacments.put(']', "%5D");
		
		urlReplacments.put('�', "%C4");
		urlReplacments.put('�', "%D6");
		urlReplacments.put('�', "%DC");
		urlReplacments.put('�', "%DF");
		urlReplacments.put('�', "%E4");
		urlReplacments.put('�', "%F6");
		urlReplacments.put('�', "%FC");
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
	 * Hashs the given String by using the SHA-512 algorithm and converts it to
	 * an hexadecimal String
	 * 
	 * @param string
	 *            to hash
	 * @return hashed and converted String
	 */
	public static String hashString(String string) {
		try {
			MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
			byte[] output = sha512.digest(string.getBytes());

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < output.length; i++) {
				sb.append(Integer.toString((output[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			string = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
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
	 * be encoded by using {@link #hashString(String)}
	 * 
	 * @param password
	 *            - value of the parameter
	 * @return the HTTP parameter
	 */
	public static String getParameterPassword(String password) {
		return "Password=" + hashString(password);
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
					parameters
							.add(f.getName()
									+ "="
									+ parseStringToHTTP(parseDateToNetDateTime((Date) value)));
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

	@SuppressWarnings("unchecked")
	public static ArrayList<String> removeAttributeFromList(String attribute,
			ArrayList<String> parameterList) {
		ArrayList<String> clone = (ArrayList<String>) parameterList.clone();
		ArrayList<String> clone2 = (ArrayList<String>) parameterList.clone();
		for (String parameter : clone) {
			String tmp1 = parameter.toLowerCase();
			String tmp2 = attribute.toLowerCase();
			if (tmp1.startsWith(tmp2)) {
				clone2.remove(parameter);
			}
		}
		return clone2;
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
		
		String encoded;
		try {
			encoded = URLEncoder.encode(value, "UTF-8");
			return encoded;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
//		
//		StringBuilder encoded = new StringBuilder();
//		StringBuilder builder = new StringBuilder(value);
//		for(int i = 0; i < builder.length(); i++){
//			char tmp = builder.charAt(i);
//			if(urlReplacments.containsKey(tmp)){
//				encoded.append(urlReplacments.get(tmp));
//			}else{
//				encoded.append(tmp);
//			}
//		}
//		
//		return encoded.toString();
		
//		value = value.replaceAll(" ", "%20");
//		value = value.replaceAll("[+]", "%2B");
		
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
		return "RemoveUserFromGroup";
	}

	/**
	 * Parses the Java-Date into a JSON value representing an C#-DateTime object.
	 * Representation as ISO 8601 needs jre 1.7.
	 * 
	 * @param date
	 * @return String representation of C#-DateTime
	 */
	public static String parseDateToNetDateTime(Date date) {
		// 2008-11-01T19:35:00.0000000-07:00
		SimpleDateFormat formatted = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");
		return formatted.format(date);
	}

	/**
	 * Parses the ISO 8601 time String into a Java-Date object
	 * 
	 * @param time
	 * @return Date representation as Java-Date
	 */
	public static Date parseStringToDate(String time) {
		
		// 2008-11-01T19:35:00.0000000-07:00
		int lastIndexOfDoublePoint = time.lastIndexOf(':');
		StringBuilder b = new StringBuilder(time);
		time = b.replace(lastIndexOfDoublePoint, lastIndexOfDoublePoint+1, "").toString();
		
		// 2008-11-01T19:35:00.0000000-0700
		SimpleDateFormat formatted = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ");
		Date date = null;

		try {
			date = formatted.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static void main(String[] args) {

		System.out.println(hashString("Test"));
		System.out.println(parseDateToNetDateTime(new Date()));
		System.out
				.println(parseStringToDate("2008-11-01T19:35:00.0000000-07:00"));
	}
}