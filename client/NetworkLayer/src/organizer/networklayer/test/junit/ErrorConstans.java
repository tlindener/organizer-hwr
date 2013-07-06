package organizer.networklayer.test.junit;

/**
 * This interfaces defines the error messages used in the assert methods for the JUnit test
 * 
 * @author Steffen Baumann
 * @version 1.0 
 *
 */
public interface ErrorConstans {
	
	public static final String NULL_CALENDAR = "Calendar is null.";
	public static final String NULL_ENTRY = "CalendarEntry is null.";
	public static final String NULL_ROOM = "Room is null.";
	public static final String NULL_GROUP = "Group is null.";
	public static final String NULL_INVITE = "Invite is null.";
	public static final String NULL_USER = "User is null.";
	
	public static final String NOTNULL_CALENDAR = "Calendar is not null.";
	public static final String NOTNULL_ENTRY = "CalendarEntry is not null.";
	public static final String NOTNULL_ROOM = "Room is not null.";
	public static final String NOTNULL_GROUP = "Group is not null.";
	public static final String NOTNULL_INVITE = "Invite is not null.";
	public static final String NOTNULL_USER = "User is not null.";
	
	public static final String ERROR_TYPE_ADD = "ADD ERROR: ";
	public static final String ERROR_TYPE_REQ = "REQUEST ERROR: ";
	public static final String ERROR_TYPE_LOGIN = "LOGIN ERROR: ";
	
	public static final String FALSE_CALENDAR = "Calendar - result is false.";
	public static final String FALSE_ENTRY = "CalendarEntry - result is false.";
	public static final String FALSE_ROOM = "Room - result is false.";
	public static final String FALSE_GROUP = "Group - result is false.";
	public static final String FALSE_INVITE = "Invite - result is false.";
	public static final String FALSE_USER = "User - result is false.";
	
	public static final String TRUE_CALENDAR = "Calendar - result is true.";
	public static final String TRUE_ENTRY = "CalendarEntry - result is true.";
	public static final String TRUE_ROOM = "Room - result is true.";
	public static final String TRUE_GROUP = "Group - result is true.";
	public static final String TRUE_INVITE = "Invite - result is true.";
	public static final String TRUE_USER = "User - result is true.";
	
	public static final String ERROR_TYPE_REM = "REMOVE ERROR: ";
	public static final String ERROR_TYPE_UPD = "UPDATE ERROR: ";
	
	public static final String LIST_FILLED = "List is filled.";
	public static final String LIST_EMPTY = "List is empty.";
	public static final String NULL_LIST = "List is null.";
	
	public static final String ERROR_TYPE_LIST = "LIST ERROR: ";
	
	public static final String USER_GROUP_FAIL = "User-Group-Action failed. Result is false";
	public static final String USER_GROUP_SUCCESS = "User-Group-Action succeded. Result is true";
	
	public static final String ERROR_FORBIDDEN = "[FORBIDDEN ACTION] ";
	public static final String ERROR_FORBIDDEN_TWICE = "[SAME ACTION FORBIDDEN] ";
	
	public static final String FAIL_EXCE_UNTHROWN = "ERROR: An expected Exception was not thrown";
	
}
