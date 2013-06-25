package test.junit;

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
	
//	public static final String FAIL_ADD_NOTNULL_CAL = "ERROR: Calendar is null. It was not added.";
//	public static final String FAIL_ADD_NOTNULL_ENT = "ERROR: CalendarEntry is null. It was not added.";
//	public static final String FAIL_ADD_NOTNULL_USE = "ERROR: User is null. It was not added.";
//	public static final String FAIL_ADD_NOTNULL_ROO = "ERROR: Room is null. It was not added.";
//	public static final String FAIL_ADD_NOTNULL_GRO = "ERROR: Group is null. It was not added.";
//	public static final String FAIL_ADD_NOTNULL_INV = "ERROR: Invite is null. It was not added.";
//	
//	public static final String FAIL_REQ_NOTNULL_CAL = "ERROR: Calendar is null. It was not requested.";
//	public static final String FAIL_REQ_NOTNULL_ENT = "ERROR: CalendarEntry is null. It was not requested.";
//	public static final String FAIL_REQ_NOTNULL_USE = "ERROR: User is null. It was not requested.";
//	public static final String FAIL_REQ_NOTNULL_ROO = "ERROR: Room is null. It was not requested.";
//	public static final String FAIL_REQ_NOTNULL_GRO = "ERROR: Group is null. It was not requested.";
//	public static final String FAIL_REQ_NOTNULL_INV = "ERROR: Invite is null. It was not requested.";
//	public static final String FAIL_REQ_NOTNULL_LIST = "ERROR: List is null. It was not requested. Major error in Requester.";
//	
//	public static final String FAIL_UPD_FALSE_CAL = "ERROR: Result is false. Calendar is not updated.";
//	public static final String FAIL_UPD_FALSE_ENT = "ERROR: Result is false. CalendarEntry is not updated.";
//	public static final String FAIL_UPD_FALSE_USE = "ERROR: Result is false. User is not updated.";
//	public static final String FAIL_UPD_FALSE_ROO = "ERROR: Result is false. Room is not updated.";
//	public static final String FAIL_UPD_FALSE_GRO = "ERROR: Result is false. Group is not updated.";
//	public static final String FAIL_UPD_FALSE_INV = "ERROR: Result is false. Invite is not updated.";
//	
//	public static final String FAIL_UPD_TRUE_GRO = "ERROR: Result is true. Group is updated.";
//	
//	public static final String FAIL_REM_FALSE_CAL = "ERROR: Result is false. Calendar is not removed.";
//	public static final String FAIL_REM_FALSE_ENT = "ERROR: Result is false. CalendarEntry is not removed.";
//	public static final String FAIL_REM_FALSE_USE = "ERROR: Result is false. User is not removed.";
//	public static final String FAIL_REM_FALSE_ROO = "ERROR: Result is false. Room is not removed.";
//	public static final String FAIL_REM_FALSE_GRO = "ERROR: Result is false. Group is not removed.";
//	public static final String FAIL_REM_FALSE_INV = "ERROR: Result is false. Invite is not removed.";
//	
//	public static final String FAIL_REM_TRUE_INV = "ERROR: Result is true. Invite is removed.";
//	public static final String FAIL_REM_TRUE_GRO = "ERROR: Result is true. Group is removed.";
//	
//	public static final String FAIL_ACC = "ERROR: The invite was not accepted. The returned value was not 1";
//	public static final String FAIL_DEC = "ERROR: The invite was not accepted. The returned value was not -1";
//	
//	public static final String FAIL_ACC_ANOTHER_USE = "ERROR: The invite was accepted, although you are not the owner.";
//	public static final String FAIL_DEC_ANOTHER_USE = "ERROR: The invite was declined, although you are not the owner.";
//	
////	public static final String FAIL_ADD_SEC_USE = "ERROR: You successfully added an user twice.";
//	
//	public static final String FAIL_ADD_FOR_ONESELF = "ERROR: You have not added an element for yourself! CLASSTYPE: ";
//	public static final String FAIL_REQ_FOR_ONESELF = "ERROR! You have not requested an element for yourself! CLASSTYPE: ";
//	public static final String FAIL_REM_FOR_ONESELF = "ERROR! You have not removed an element for yourself! CLASSTYPE: ";
//	public static final String FAIL_UPD_FOR_ONESELF = "ERROR! You have not updated an element for yourself! CLASSTYPE: ";
//	
//	public static final String FAIL_ADD_FOR_ANOTHER_USER = "ERROR: You successfully added an element to another user: ";
//	public static final String FAIL_REQ_FOR_ANOTHER_USER = "ERROR: You successfully requested an element of another user: ";
//	public static final String FAIL_REM_FOR_ANOTHER_USER = "ERROR: You successfully removed an element of another user: ";
//	public static final String FAIL_UPD_FOR_ANOTHER_USER = "ERROR: You successfully updated an element of another user: ";
//	
//	public static final String FAIL_LOGIN_USE = "ERROR: The login was not successful";
//	public static final String FAIL_LIST_EMPTY = "ERROR: The given list is not empty.";
//	public static final String FAIL_NOT_EQUALS = "ERROR: The Objects are not equal";
//	public static final String FAIL_LIST_FULL = "ERROR: The given list is emtpy";
//	

//	public static final String FAIL_ADD_TO_GRO = "ERROR: User was not added to the Group";
//	public static final String FAIL_ADD_TO_GRO_TWICE = "ERROR: User was successfully added to the Group a second time";
//	public static final String FAIL_REM_FR_GRO = "ERROR: User was not removed from the Group";
	
//	public static final String FAIL_LIST_EMPTY = "ERROR: You successfully added an user twice.";
//	public static final String FAIL_LIST_EMPTY = "ERROR: You successfully added an user twice.";
//	
//	"No equal entries"
	
	
//	public static final String FAIL_ADD_FOR_ANOTHER_USER = "ERROR: You successfully added an element to another user: ";
//	public static final String FAIL_REQ_FOR_ANOTHER_USER = "ERROR: You successfully requested an element of another user: ";
//	public static final String FAIL_REM_FOR_ANOTHER_USER = "ERROR: You successfully removed an element of another user: ";
//	public static final String FAIL_UPD_FOR_ANOTHER_USER = "ERROR: You successfully updated an element of another user: ";
	
	
//	public static final String FAIL_CAL_OTHER_USE = "ERROR: Request Calendar of another User was successful";
//	public static final String FAIL_ENT_OTHER_USE = "ERROR: Request Entry of another User was successful";
//	public static final String FAIL_INV_OTHER_USE = "ERROR: Request Invite of another User was successful";
}
