package network.objects;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	public static String buildLoginCommand(String mail, String password) {
		String cmd = "Login?MailAddress=" + mail + "&Password="
				+ encodeString(password);
		return cmd;
	}
	/**
	 * Encodes a String in 3 steps:
	 * <ol>
	 * <li>Create the SHA-512 hash value
	 * <li>Create an ASCII String from hash
	 * <li>Encode the ASCII String with Base64
	 * </ol>
	 * @param string to encode
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
		return string;
	}
	/**
	 * Adds the {@link RequestHandler#authString} to the given command.
	 * 
	 * @param cmd to be sent
	 * @param authString of the current logged in user
	 * @return GET-Command with authentication
	 */
	public static String addUserAuth(String cmd, String authString) {
		return cmd + "&userAuth=" + authString;
	}
	/**
	 * Creates the command for requesting objects by a set prperty
	 * @param obj the command has to built for
	 * @return GET-Command
	 * @throws IllegalArgumentException
	 */
	public static <T extends AbstractOrganizerObject> String buildGetByPropertyCommand(
			T obj) throws IllegalArgumentException {
		String command = buildGetAllCommand(obj) + convertPropertyValueArray(obj.getProperty());
		return command;
	}
	
	private static String convertPropertyValueArray(String[] propertyValuePair){
		return "By"+propertyValuePair[0]+"?"+propertyValuePair[0]+"="+propertyValuePair[1];
	}
	
	/**
	 * Creates the command for requesting a single object by its ID
	 *
	 * @param obj which is requested containing its ID
	 * @return GET-Command
	 */
	public static <T extends AbstractOrganizerObject> String buildGetByOwnIdCommand(
			T obj) {
		String className = obj.getClass().getSimpleName();
		String command = "Get" + className + "ById?" + className + "Id="
				+ obj.getID();
		return command;
	}
	/**
	 * Creates the command for removing a single object by its ID
	 * @param obj to remove containing its ID
	 * @return GET-Command
	 */
	public static <T extends AbstractOrganizerObject> String buildRemoveByOwnIdCommand(
			T obj) {
		String className = obj.getClass().getSimpleName();
		String command = "remove" + className + "?" + className + "Id="
				+ obj.getID();
		return command;
	}
	/**
	 * Creates the command for requesting all objects of the given type
	 * @param obj whose type is requested
	 * @return GET-Command
	 * @throws IllegalArgumentException
	 */
	public static <T extends AbstractOrganizerObject> String buildGetAllCommand(
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
	 * @param obj with full set attributes
	 * @return GET-Command
	 * @throws IllegalArgumentException
	 */
	public static <T extends AbstractOrganizerObject> String buildAddCommand(
			T obj) throws IllegalArgumentException {
		String command = "Add" + obj.getClass().getSimpleName() + "?";
		
		Field[] fields = obj.getClass().getDeclaredFields();
		command += buildParameterString(fields, obj);
		
		return command;
	}
	
	public static <T extends AbstractOrganizerObject> String buildUpdateCommand(
			T obj) throws IllegalArgumentException {
		String command = "Update" + obj.getClass().getSimpleName() + "?";
		
		Field[] fields = obj.getClass().getDeclaredFields();
		command += buildParameterString(fields, obj);
		
		return command;
	}
	
	private static <T extends AbstractOrganizerObject> String buildParameterString(Field[] fields, T obj){
		String command = "";
		ArrayList<String> parameters = new ArrayList<String>();
		for (Field f : fields) {
			if (f.getModifiers() != Modifier.PRIVATE)
				continue;
			f.setAccessible(true);
			try {
				Object value = f.get(obj);
				if(f.getName().equals("accepted")){
					continue;
				}
//				 if(value == null){
//					 parameters.add(f.getName() + "=");
////					 throw new IllegalArgumentException("Darf nicht null sein");
//				 }
				if (value instanceof List<?>) {
					continue;
				} else if (value instanceof String) {
					parameters.add(f.getName() + "="
							+ parseStringToHTTP((String)value));
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

		for (int i = 0; i < parameters.size(); i++) {
			command += parameters.get(i);
			if (i < parameters.size() - 1)
				command += "&";
		}

		return command;
	}
	
	public static String parseStringToHTTP(String value) {
		value = value.replaceAll(" ", "%20");
		value = value.replaceAll("[+]", "%2B");
		return value;
	}

	public static String buildAcceptCommand(int inviteId){
		return "AcceptInvite?inviteId="+inviteId;
	}
	
	public static String buildDeclineCommand(int inviteId){
		return "DeclineInvite?inviteId="+inviteId;
	}
	
	
	/**
	 * Parses the Java-Date into a JSON value representing an C#-DateTime object
	 * @param date
	 * @return String representation of C#-DateTime
	 */
	public static String parseDateToNetDateTime(Date date) {
		
		SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		return parseStringToHTTP(formatted.format(date));
		
//		2008-11-01T19:35:00.0000000-07:00
	}
	
	public static void main(String[] args) {
		System.out.println(parseStringToHTTP("Das ist ein Test"));
		System.out.println(encodeString("Test"));
		System.out.println(parseDateToNetDateTime(new Date())); 
	}
}