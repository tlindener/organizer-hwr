package test.junit;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import network.RequestHandler;
import network.json.JsonJavaIISRequestHandler;
import network.json.JsonJavaRequestHandler;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Group;
import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

public class TestSuite {

	private static final String FAIL_ADD_NOTNULL_CAL = "ERROR: Calendar is null. It was not added.";
	private static final String FAIL_ADD_NOTNULL_ENT = "ERROR: CalendarEntry is null. It was not added.";
	private static final String FAIL_ADD_NOTNULL_USE = "ERROR: User is null. It was not added.";
	private static final String FAIL_ADD_NOTNULL_ROO = "ERROR: Room is null. It was not added.";
	private static final String FAIL_ADD_NOTNULL_GRO = "ERROR: Group is null. It was not added.";
	private static final String FAIL_ADD_NOTNULL_INV = "ERROR: Invite is null. It was not added.";
	
	private static final String FAIL_REQ_NOTNULL_CAL = "ERROR: Calendar is null. It was not requested.";
	private static final String FAIL_REQ_NOTNULL_ENT = "ERROR: CalendarEntry is null. It was not requested.";
	private static final String FAIL_REQ_NOTNULL_USE = "ERROR: User is null. It was not requested.";
	private static final String FAIL_REQ_NOTNULL_ROO = "ERROR: Room is null. It was not requested.";
	private static final String FAIL_REQ_NOTNULL_GRO = "ERROR: Group is null. It was not requested.";
	private static final String FAIL_REQ_NOTNULL_INV = "ERROR: Invite is null. It was not requested.";
	private static final String FAIL_REQ_NOTNULL_LIST = "ERROR: List is null. It was not requested. Major error in Requester.";
	
	private static final String FAIL_UPD_FALSE_CAL = "ERROR: Result is false. Calendar is not updated.";
	private static final String FAIL_UPD_FALSE_ENT = "ERROR: Result is false. CalendarEntry is not updated.";
	private static final String FAIL_UPD_FALSE_USE = "ERROR: Result is false. User is not updated.";
	private static final String FAIL_UPD_FALSE_ROO = "ERROR: Result is false. Room is not updated.";
	private static final String FAIL_UPD_FALSE_GRO = "ERROR: Result is false. Group is not updated.";
	private static final String FAIL_UPD_FALSE_INV = "ERROR: Result is false. Invite is not updated.";
	
	private static final String FAIL_REM_FALSE_CAL = "ERROR: Result is false. Calendar is not removed.";
	private static final String FAIL_REM_FALSE_ENT = "ERROR: Result is false. CalendarEntry is not removed.";
	private static final String FAIL_REM_FALSE_USE = "ERROR: Result is false. User is not removed.";
	private static final String FAIL_REM_FALSE_ROO = "ERROR: Result is false. Room is not removed.";
	private static final String FAIL_REM_FALSE_GRO = "ERROR: Result is false. Group is not removed.";
	private static final String FAIL_REM_FALSE_INV = "ERROR: Result is false. Invite is not removed.";
	
	private static final String FAIL_ACC = "ERROR: The invite was not accepted. The returned value was not 1";
	private static final String FAIL_DEC = "ERROR: The invite was not accepted. The returned value was not -1";
	
	private static final String FAIL_ACC_ANOTHER_USE = "ERROR: The invite was accepted, although you are not the owner.";
	private static final String FAIL_DEC_ANOTHER_USE = "ERROR: The invite was declined, although you are not the owner.";
	
//	private static final String FAIL_ADD_SEC_USE = "ERROR: You successfully added an user twice.";
	
	private static final String FAIL_ADD_FOR_ONESELF = "ERROR: You have not added an element for yourself! CLASSTYPE: ";
	private static final String FAIL_REQ_FOR_ONESELF = "ERROR! You have not requested an element for yourself! CLASSTYPE: ";
	private static final String FAIL_REM_FOR_ONESELF = "ERROR! You have not removed an element for yourself! CLASSTYPE: ";
	private static final String FAIL_UPD_FOR_ONESELF = "ERROR! You have not updated an element for yourself! CLASSTYPE: ";
	
	private static final String FAIL_ADD_FOR_ANOTHER_USER = "ERROR: You successfully added an element to another user: ";
	private static final String FAIL_REQ_FOR_ANOTHER_USER = "ERROR: You successfully requested an element of another user: ";
	private static final String FAIL_REM_FOR_ANOTHER_USER = "ERROR: You successfully removed an element of another user: ";
	private static final String FAIL_UPD_FOR_ANOTHER_USER = "ERROR: You successfully updated an element of another user: ";
	
	private static final String FAIL_LOGIN_USE = "ERROR: The login was not successful";
	private static final String FAIL_LIST_EMPTY = "ERROR: The given list is not empty.";
	private static final String FAIL_NOT_EQUALS = "ERROR: The Objects are not equal";
	private static final String FAIL_LIST_FULL = "ERROR: The given list is emtpy";
	
	private static final String FAIL_EXCE_UNTHROWN = "ERROR: An expected Exception was not thrown";
	private static final String FAIL_ADD_TO_GRO = "ERROR: User was not added to the Group";
	private static final String FAIL_ADD_TO_GRO_TWICE = "ERROR: User was successfully added to the Group a second time";
	private static final String FAIL_REM_FR_GRO = "ERROR: User was not removed from the Group";
	
//	private static final String FAIL_LIST_EMPTY = "ERROR: You successfully added an user twice.";
//	private static final String FAIL_LIST_EMPTY = "ERROR: You successfully added an user twice.";
//	
//	"No equal entries"
	
	
//	private static final String FAIL_ADD_FOR_ANOTHER_USER = "ERROR: You successfully added an element to another user: ";
//	private static final String FAIL_REQ_FOR_ANOTHER_USER = "ERROR: You successfully requested an element of another user: ";
//	private static final String FAIL_REM_FOR_ANOTHER_USER = "ERROR: You successfully removed an element of another user: ";
//	private static final String FAIL_UPD_FOR_ANOTHER_USER = "ERROR: You successfully updated an element of another user: ";
	
	
//	private static final String FAIL_CAL_OTHER_USE = "ERROR: Request Calendar of another User was successful";
//	private static final String FAIL_ENT_OTHER_USE = "ERROR: Request Entry of another User was successful";
//	private static final String FAIL_INV_OTHER_USE = "ERROR: Request Invite of another User was successful";

	
	private static RequestHandler requester = null;
	private final String defaultPassword = "1234";
	
	@BeforeClass
	public static void setUp() throws Exception {
		requester = new JsonJavaIISRequestHandler("localhost", 80);
//		requester = new JsonJavaRequestHandler("localhost", 48585);
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		
	}
	
	@Before
	public void setUpEachTest() throws Exception {
	
	}
	
	@After
	public void tearDownEachTest() throws Exception {
		boolean result = requester.dropDatabase();
		System.err.println("TearDown Result: " + result);
		assertTrue(result);
	}

	@Ignore
	public void testRequestObjectByProperty() {
		fail("Not yet implemented");
	}

	@Test
	public void testRequestObjectByOwnId() {
//		Create default data and returns the users to act with
		User[] userList = createDefaultData(4,4,2);
//		Login user 2 to request calendar
		User loggedInUser2 = login(userList[1].getMailAddress());
//		request calendar of user 2
		Calendar calendarUser2 = requestCalendar(loggedInUser2.getCalendarIds().get(0));
		assertNotNull(FAIL_REQ_NOTNULL_CAL, calendarUser2);
//		login user 1 to test functionalities
		User loggedInUser = login(userList[0].getMailAddress());
//		Should have invites and at least one calendar. Therefore not groups
		assertFalse(FAIL_LIST_EMPTY, loggedInUser.getCalendarIds().isEmpty());
		assertFalse(FAIL_LIST_EMPTY, loggedInUser.getInviteIds().isEmpty());
		assertTrue(FAIL_LIST_FULL, loggedInUser.getGroupIds().isEmpty());
		
//		User can only have on Calendar at the moment
//		request this calendar
		Calendar calendarUser1 = requestCalendar(loggedInUser.getCalendarIds().get(0));
		assertNotNull(FAIL_REQ_NOTNULL_CAL, calendarUser1);
		
//		Request entries by their id and compare to entries in calendar
		for(CalendarEntry entry: calendarUser1.getCalendarEntries()){
			CalendarEntry requestedEntry = requestCalendarEntry(entry.getID());
			assertNotNull(FAIL_REQ_NOTNULL_ENT, requestedEntry);
//			equals method won't work - the hashcode is different
			assertEquals("No equal entries", requestedEntry.toString(), entry.toString());
		}
		
		//TODO Invites abfragen geht nicht
//		Request invites and check access to the mentioned entry
		for(int id: loggedInUser.getInviteIds()){
//			Invite invite = requestInvite(id);
//			assertNotNull(FAIL_REQ_NOTNULL_INV, invite);
//			CalendarEntry entry = requestCalendarEntry(invite.getCalendarEntryId());
//			assertNotNull(entry);
		}
//		request a group by id
		Group group = requestGroup(1);
		assertNotNull(FAIL_REQ_NOTNULL_GRO, group);
//		request a group by id
		Room room = requestRoom(1);
		assertNotNull(FAIL_REQ_NOTNULL_ROO, room);
		
//		request own user object completely
		User userRequest = requestUser(userList[0].getID());
		assertNotNull(FAIL_REQ_NOTNULL_USE, userRequest);
		assertFalse(FAIL_LIST_FULL, userRequest.getCalendarIds().isEmpty());
		assertFalse(FAIL_LIST_FULL, userRequest.getInviteIds().isEmpty());
		assertTrue(FAIL_LIST_EMPTY, userRequest.getGroupIds().isEmpty());
		
//		Request another user by its id should be an anonymous one (no lists contained)
//		TODO Anonymous User-Object ?
//		User userRequest = requestUser(userList[1].getID());
//		assertNotNull(FAIL_REQ_NOTNULL_USE, userRequest);
//		assertTrue(FAIL_LIST_EMPTY, userRequest.getCalendarIds().isEmpty());
//		assertTrue(FAIL_LIST_EMPTY, userRequest.getInviteIds().isEmpty());
//		assertFalse(FAIL_LIST_FULL, userRequest.getGroupIds().isEmpty());
		

//		request another users calendar (calendar from the top of this method is used)
		assertNull(FAIL_REQ_FOR_ANOTHER_USER+"Calendar", requestCalendar(calendarUser2.getID()));
//		request another users invite
		assertNull(FAIL_REQ_FOR_ANOTHER_USER+"Invite", requestInvite(userList[1].getInviteIds().get(0)));
//		request an entry of another user you are not invited to
		CalendarEntry noInvite = calendarUser2.getCalendarEntries().get(calendarUser2.getCalendarEntries().size()-1);
		assertNull(FAIL_REQ_FOR_ANOTHER_USER+"CalendarEntry", requestCalendarEntry(noInvite.getID()));
		
	}

	@Ignore
	public void testRequestFollowingObjectsByOwnId() {
		fail("Not yet implemented");
	}

	@Test
	public void testRequestAllObjects() {
		User[] userList = createDefaultData(2, 2, 1);
		
		User loggedInUser = login(userList[0].getMailAddress());
		assertNotNull(FAIL_LOGIN_USE, loggedInUser);
		
		List<Group> groups = requester.requestAllObjects(new Group());
		assertNotNull(FAIL_REQ_NOTNULL_LIST, groups);
		
//		assertFalse(FAIL_LIST_FULL, groups.isEmpty());
		
		List<Room> rooms =requester.requestAllObjects(new Room());
		assertNotNull(FAIL_REQ_NOTNULL_LIST, rooms);
		assertFalse(FAIL_LIST_FULL, rooms.isEmpty());
		
		List<User> user =requester.requestAllObjects(new User());
		assertNotNull(FAIL_REQ_NOTNULL_LIST, user);
		assertFalse(FAIL_LIST_FULL, user.isEmpty());
		for(User u: user){
			assertTrue(FAIL_LIST_EMPTY, u.getCalendarIds().isEmpty());
			assertTrue(FAIL_LIST_EMPTY, u.getInviteIds().isEmpty());
			assertTrue(FAIL_LIST_FULL, u.getGroupIds().isEmpty());
		}
		
		try {
			requester.requestAllObjects(new CalendarEntry());
			fail(FAIL_EXCE_UNTHROWN);
		} catch (UnsupportedOperationException ex) {
		}
		try {
			requester.requestAllObjects(new Calendar());
			fail(FAIL_EXCE_UNTHROWN);
		} catch (UnsupportedOperationException ex) {
		}

		try {
			requester.requestAllObjects(new Invite());
			fail(FAIL_EXCE_UNTHROWN);
		} catch (UnsupportedOperationException ex) {
		}
		
		Group group = requestGroup(1);
		assertNotNull(FAIL_REQ_NOTNULL_GRO, group);
		
		boolean result = requester.addUserToGroup(loggedInUser, group);
		assertTrue(FAIL_ADD_TO_GRO, result);
		
		loggedInUser = login(userList[1].getMailAddress());
		List<User> user2 =requester.requestAllObjects(new User());
		assertNotNull(FAIL_REQ_NOTNULL_LIST, user2);
		assertFalse(FAIL_LIST_FULL, user2.isEmpty());
		for(User u: user2){
			assertTrue(FAIL_LIST_EMPTY, u.getCalendarIds().isEmpty());
			assertTrue(FAIL_LIST_EMPTY, u.getInviteIds().isEmpty());
//			assertFalse(FAIL_LIST_EMPTY, u.getGroupIds().isEmpty());
		}
	}

	
	@Ignore
	public void testRequestAllObjectsByProperty() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAddObject() {

		User user1 = addUser("Steffen", "Baumann");
		assertNotNull(FAIL_ADD_NOTNULL_USE, user1);
		User user2 = addUser("Tobias", "Lindener");
		assertNotNull(FAIL_ADD_NOTNULL_USE, user2);
//		>>>>>CALENDAR
		login(user1);
//		Add calendar to oneself
		Calendar calendarUser1ToUser1 = addCalendar(user1.getGivenName(), user1.getID());
		assertNotNull(FAIL_ADD_NOTNULL_CAL, calendarUser1ToUser1);
		
//		Add calendar to another user should fail
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "Calendar", addCalendar(user1.getGivenName(), user2.getID()));
		
		login(user2);
//		Add calendar to oneself
		Calendar calendarUser2ToUser2 = addCalendar(user2.getGivenName(), user2.getID());
		assertNotNull(FAIL_ADD_NOTNULL_CAL, calendarUser2ToUser2);
		
//		Add calendar to another user should fail		
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "Calendar", addCalendar(user2.getGivenName(), user1.getID()));
		
//		CALENDAR<<<<<
		
//		>>>>>ROOM
		login(user1);
		Room room1 = addRoom("Created for JUnit test", "Testroom 1", 12);
		assertNotNull(FAIL_ADD_NOTNULL_ROO, room1);
		
		login(user2);
		Room room2 = addRoom("Created for JUnit test", "Testroom 2", 24);
		assertNotNull(FAIL_ADD_NOTNULL_ROO, room2);
		
//		ROOM<<<<<
		
//		>>>>>ENTRIES

		login(user1);
//		Add entry to own calendar
		CalendarEntry entryUser1ToUser1NoRoom = addCalendarEntry(user1.getGivenName(), calendarUser1ToUser1.getID(), user1.getID(), 0);
		assertNotNull(FAIL_ADD_NOTNULL_ENT, entryUser1ToUser1NoRoom);
		
//		Add entry to another user should fail
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "CalendarEntry", addCalendarEntry(user1.getGivenName(), calendarUser1ToUser1.getID(), user2.getID(), 0));
//		Add entry to another user's calendar should fail		
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "CalendarEntry", addCalendarEntry(user1.getGivenName(), calendarUser2ToUser2.getID(), user1.getID(), 0));
//		Add entry to another user's calendar and user itself should fail
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "CalendarEntry", addCalendarEntry(user1.getGivenName(), calendarUser2ToUser2.getID(), user2.getID(), 0));
		
//		Add entry to own calendar with room created by oneself
		CalendarEntry entryUser1ToUser1WithRoom1 = addCalendarEntry(user1.getGivenName(), calendarUser1ToUser1.getID(), user1.getID(), room1.getID());
		assertNotNull(FAIL_ADD_NOTNULL_ENT, entryUser1ToUser1WithRoom1);
//		Add entry to own calendar with room created by another user
		CalendarEntry entryUser1ToUser1WithRoom2 = addCalendarEntry(user1.getGivenName(), calendarUser1ToUser1.getID(), user1.getID(), room2.getID());
		assertNotNull(FAIL_ADD_NOTNULL_ENT, entryUser1ToUser1WithRoom2);
		
//		Add entry to another user should fail
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "CalendarEntry", addCalendarEntry(user1.getGivenName(), calendarUser1ToUser1.getID(), user2.getID(), room1.getID()));
//		Add entry to another user's calendar should fail		
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "CalendarEntry", addCalendarEntry(user1.getGivenName(), calendarUser2ToUser2.getID(), user1.getID(), room1.getID()));
//		Add entry to another user's calendar and user itself should fail
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "CalendarEntry", addCalendarEntry(user1.getGivenName(), calendarUser2ToUser2.getID(), user2.getID(), room1.getID()));
		
		login(user2);
//		Add entry to own calendar
		CalendarEntry entryUser2ToUser2NoRoom = addCalendarEntry(user2.getGivenName(), calendarUser2ToUser2.getID(), user2.getID(), 0);
		assertNotNull(FAIL_ADD_NOTNULL_ENT, entryUser2ToUser2NoRoom);
		
//		Add entry to another user should fail
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "CalendarEntry", addCalendarEntry(user2.getGivenName(), calendarUser2ToUser2.getID(), user1.getID(), 0));
//		Add entry to another user's calendar should fail
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "CalendarEntry", addCalendarEntry(user2.getGivenName(), calendarUser1ToUser1.getID(), user2.getID(), 0));
//		Add entry to another user's calendar and user itself should fail
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "CalendarEntry", addCalendarEntry(user2.getGivenName(), calendarUser1ToUser1.getID(), user1.getID(), 0));
		
//		Add entry to own calendar with room created by oneself
		CalendarEntry entryUser2ToUser2WithRoom2 = addCalendarEntry(user2.getGivenName(), calendarUser2ToUser2.getID(), user2.getID(), room2.getID());
		assertNotNull(FAIL_ADD_NOTNULL_ENT, entryUser2ToUser2WithRoom2);
//		Add entry to own calendar with room created by another user
		CalendarEntry entryUser2ToUser2WithRoom1 = addCalendarEntry(user2.getGivenName(), calendarUser2ToUser2.getID(), user2.getID(), room1.getID());
		assertNotNull(FAIL_ADD_NOTNULL_ENT, entryUser2ToUser2WithRoom1);
		
//		Add entry to another user should fail
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "CalendarEntry", addCalendarEntry(user2.getGivenName(), calendarUser2ToUser2.getID(), user1.getID(), room1.getID()));
//		Add entry to another user's calendar should fail
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "CalendarEntry", addCalendarEntry(user2.getGivenName(), calendarUser1ToUser1.getID(), user2.getID(), room1.getID()));
//		Add entry to another user's calendar and user itself should fail
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "CalendarEntry", addCalendarEntry(user2.getGivenName(), calendarUser1ToUser1.getID(), user1.getID(), room1.getID()));
		
//		ENTIRES<<<<<

		//		>>>>>INVITES
		
		login(user1);
//		Add invite with no room set
		Invite inviteUser2ToUser1sEntryNoRoom = addInvite(entryUser1ToUser1NoRoom.getID(), user2.getID());
		assertNotNull(FAIL_ADD_NOTNULL_INV, inviteUser2ToUser1sEntryNoRoom);
//		Add invite with room set
		Invite inviteUser2ToUser1sEntryWithRoom1 = addInvite(entryUser1ToUser1WithRoom1.getID(), user2.getID());
		assertNotNull(FAIL_ADD_NOTNULL_INV, inviteUser2ToUser1sEntryWithRoom1);
//		Add second invite with room set
		Invite inviteUser2ToUser1sEntryWithRoom2 = addInvite(entryUser1ToUser1WithRoom2.getID(), user2.getID());
		assertNotNull(FAIL_ADD_NOTNULL_INV, inviteUser2ToUser1sEntryWithRoom2);
		
//		Add invite for other user to its own calendar entry	without room		
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "Invite", addInvite(entryUser2ToUser2NoRoom.getID(), user2.getID()));
//		Add invite for other user to its own calendar entry	with room
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "Invite", addInvite(entryUser2ToUser2WithRoom1.getID(), user2.getID()));
//		Add own invite for other users calendar entry
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "Invite", addInvite(entryUser2ToUser2WithRoom2.getID(), user1.getID()));
		
		login(user2);
//		Add invite with no room set
		Invite inviteUser1ToUser2sEntryNoRoom = addInvite(entryUser2ToUser2NoRoom.getID(), user1.getID());
		assertNotNull(FAIL_ADD_NOTNULL_INV, inviteUser1ToUser2sEntryNoRoom);
//		Add invite with room set
		Invite inviteUser1ToUser2sEntryWithRoom1 = addInvite(entryUser2ToUser2WithRoom1.getID(), user1.getID());
		assertNotNull(FAIL_ADD_NOTNULL_INV, inviteUser1ToUser2sEntryWithRoom1);
//		Add second invite with room set
		Invite inviteUser1ToUser2sEntryWithRoom2 = addInvite(entryUser2ToUser2WithRoom2.getID(), user2.getID());
		assertNotNull(FAIL_ADD_NOTNULL_INV, inviteUser1ToUser2sEntryWithRoom2);
		
//		Add invite for other user to its own calendar entry	without room		
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "Invite", addInvite(entryUser1ToUser1NoRoom.getID(), user1.getID()));
//		Add invite for other user to its own calendar entry	with room
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "Invite", addInvite(entryUser1ToUser1WithRoom1.getID(), user1.getID()));
//		Add own invite for other users calendar entry
		assertNull(FAIL_ADD_FOR_ANOTHER_USER + "Invite", addInvite(entryUser1ToUser1WithRoom2.getID(), user2.getID()));
		
//		INVITES<<<<<
		
//		>>>>>GROUPS
		login(user1);
		Group group1 = addGroup(user1.getGivenName()+"'s group");
		assertNotNull(FAIL_ADD_NOTNULL_GRO, group1);
		
		login(user2);
		Group group2 = addGroup(user2.getGivenName()+"'s group");
		assertNotNull(FAIL_ADD_NOTNULL_GRO, group2);
//		GROUPS<<<<<
		
	}

	private User[] createDefaultData(int calendarEntryWithoutRoomPerUser, int calendarEntryWithRoomPerUser, int inviteSentFromUser){
			
		User user1 = addUser("Steffen", "Baumann");
		assertNotNull(FAIL_ADD_NOTNULL_USE, user1);
		User user2 = addUser("Tobias", "Lindener");
		assertNotNull(FAIL_ADD_NOTNULL_USE, user2);
		
		login(user1);
//		Add calendar to oneself
		Calendar calendarUser1ToUser1 = addCalendar(user1.getGivenName(), user1.getID());
		assertNotNull(FAIL_ADD_NOTNULL_CAL, calendarUser1ToUser1);
		user1 = login(user1.getMailAddress());
		
//		Add calendar to another user should fail
		assertNull(addCalendar(user1.getGivenName(), user2.getID()));
		
		Room room1 = addRoom("Created for JUnit test", "Testroom 1", 12);
		assertNotNull(FAIL_ADD_NOTNULL_ROO, room1);
		
		addEntriesAndInvites(user1, user2, calendarEntryWithRoomPerUser, inviteSentFromUser, room1.getID());
		addEntriesAndInvites(user1, user2, calendarEntryWithoutRoomPerUser, inviteSentFromUser, 0);
		
		Group group1 = addGroup(user1.getGivenName()+"'s group");
		assertNotNull(FAIL_ADD_NOTNULL_GRO, group1);
		
		login(user2);
		
//		Add calendar to oneself
		Calendar calendarUser2ToUser2 = addCalendar(user2.getGivenName(), user2.getID());
		assertNotNull(FAIL_ADD_NOTNULL_CAL, calendarUser2ToUser2);

		user2 = login(user2.getMailAddress());
		
		Room room2 = addRoom("Created for JUnit test", "Testroom 2", 24);
		assertNotNull(FAIL_ADD_NOTNULL_ROO, room2);
		
		addEntriesAndInvites(user2, user1, calendarEntryWithRoomPerUser, inviteSentFromUser, room1.getID());
		addEntriesAndInvites(user2, user1, calendarEntryWithoutRoomPerUser, inviteSentFromUser, 0);
		
		Group group2 = addGroup(user2.getGivenName()+"'s group");
		assertNotNull(FAIL_ADD_NOTNULL_GRO, group2);
		
		return new User[]{user1, user2};
		
	}
	
	private void addEntriesAndInvites(User owner, User invitee, int calendarEntryCount, int inviteCount, int roomId){
		for(int i = 0; i < calendarEntryCount; i++){
			
			CalendarEntry entry = addDefaultEntry(owner, i+". entry", roomId);
			assertNotNull(FAIL_ADD_NOTNULL_ENT, entry);
			
			if(i < inviteCount){
				Invite inviteForOtherUser = addInvite(entry.getID(), invitee.getID());
				assertNotNull(FAIL_ADD_NOTNULL_INV, inviteForOtherUser);
			}
		}
	}
	
	private void login(User userToLogin){
		login(userToLogin.getMailAddress());
	}
	private User login(String mailAddress){
		User user = requester.login(mailAddress, defaultPassword);
		assertNotNull(FAIL_LOGIN_USE, user);
		
		return user;
	}
	
	@Test
	public void testRegisterNewUser() {
		User user1 = addUser("Steffen", "Baumann");
		assertNotNull(FAIL_ADD_NOTNULL_USE, user1);
		User user2 = addUser("Steffen", "Baumann");
		assertNull(user2);
		User user3 = addUser("Svenja", "Moehring");
		assertNotNull(FAIL_ADD_NOTNULL_USE, user3);
	}

	@Ignore
	public void testRemoveObjectByOwnId() {
		User[] testUser = createDefaultData(2,2,1);
		login(testUser[0])	;
		
//		Calendar requestCalendarUser1 = new Calendar();
//		requestCalendarUser1.setID(user1.getID());
//		Calendar user1Calendar = requester.requestObjectByOwnId(requestCalendarUser1);
		
//		assertTrue(removeCalendarEntry());
		
		
		
		fail("Not yet implemented");
	}


	@Test
	public void testLogin() {
		User user = addUser("Steffen", "Baumann");
		assertNotNull(FAIL_ADD_NOTNULL_USE, user);
		login(user);
	}

	@Ignore
	public void testAcceptInvite() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testDeclineInvite() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testUpdateObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUserToGroup() {
		User[] userList = createDefaultData(2, 2, 1);
		
		User loggedInUser = login(userList[0].getMailAddress());
		List<Integer> ids = Arrays.asList(1,2);
		List<Group> groups = requester.requestFollowingObjectsByOwnId(ids, new Group());
		assertNotNull(FAIL_REQ_NOTNULL_LIST, groups);
		assertFalse(FAIL_LIST_FULL, groups.isEmpty());
		
		for(Group group: groups){
			boolean result = requester.addUserToGroup(loggedInUser, group);
			assertTrue(FAIL_ADD_TO_GRO, result);
		}
		
		for(Group group: groups){
			boolean result = requester.addUserToGroup(loggedInUser, group);
			assertFalse(FAIL_ADD_TO_GRO_TWICE, result);
		}
		
		for(Group group: groups){
			boolean result = requester.addUserToGroup(userList[1], group);
			assertFalse(FAIL_ADD_FOR_ANOTHER_USER + "User to Group", result);
		}
	}

	@Ignore
	public void testRemoveUserFromGroup() {
		fail("Not yet implemented");
	}
//	####################################
//	##......######..####......####..####
//	##..####..##..##..####..####..##..##
//	##..####..##......####..####......##
//	##..####..##..##..####..####..##..##
//	##......####..##..####..####..##..##
//	####################################
	
	private User addUser(String givenName, String surname){
		User user = new User();
		user.setGivenname(givenName);
		user.setSurname(surname);
		user.setMailAddress(givenName+"."+surname+"@localhost.de");
		return requester.registerNewUser(user, defaultPassword);
	}
	
	private Room addRoom(String location, String description, int seats){
		Room room = new Room();
		room.setLocation(location);
		room.setSeats(seats);
		room.setDescription(description);
		return requester.addObject(room);
	}
	
	private CalendarEntry addCalendarEntry(String userName, int calendarId, int ownerId, int roomId){
		CalendarEntry calendarEntry = new CalendarEntry();
		calendarEntry.setTitle(userName + "'s Entry");
		calendarEntry.setDescription("Test entry - JUnit for " + userName);
		calendarEntry.setStartDate(new Date());
		calendarEntry.setEndDate(new Date());
		calendarEntry.setCalendarId(calendarId);
		calendarEntry.setOwnerId(ownerId);
		calendarEntry.setRoomId(roomId);
		return requester.addObject(calendarEntry);
	}
	
	private Calendar addCalendar(String userName, int ownerId){
		Calendar calendar = new Calendar();
		calendar.setDescription("Test calendar - JUnit for " + userName);
		calendar.setName(userName + "'s Calendar");
		calendar.setOwnerId(ownerId);
		return requester.addObject(calendar);
	}

	private Invite addInvite(int calendarEntryId, int ownerId){
		Invite invite = new Invite();
		invite.setCalendarEntryId(calendarEntryId);
		invite.setOwnerId(ownerId);
		return requester.addObject(invite);
	}
	
	private Group addGroup(String description){
		Group group = new Group();
		group.setDescription(description);
		return requester.addObject(group);
	}
	
	private CalendarEntry addDefaultEntry(User user, String suffix, int roomId){
		CalendarEntry calendarEntry = new CalendarEntry();
		calendarEntry.setTitle("DefaultEntry - " + suffix);
		calendarEntry.setDescription("Entry automated generated for "+user.getGivenName() + " " + user.getSurname());
		calendarEntry.setStartDate(new Date());
		calendarEntry.setEndDate(new Date());
		calendarEntry.setCalendarId(user.getCalendarIds().get(0));
		calendarEntry.setOwnerId(user.getID());
		calendarEntry.setRoomId(roomId);
		return requester.addObject(calendarEntry);
	}
	

	private Calendar requestCalendar(int id) {
		Calendar calendar = new Calendar();
		calendar.setID(id);
		return requester.requestObjectByOwnId(calendar);
	}
	

	private CalendarEntry requestCalendarEntry(int id) {
		CalendarEntry calendarEntry = new CalendarEntry();
		calendarEntry.setID(id);
		return requester.requestObjectByOwnId(calendarEntry);
	}
	
	private Invite requestInvite(int id){
		Invite invite = new Invite();
		invite.setID(id);
		return requester.requestObjectByOwnId(invite);
	}

	private User requestUser(int id) {
		User user = new User();
		user.setID(id);
		return requester.requestObjectByOwnId(user);
	}
	

	private Room requestRoom(int id) {
		Room room= new Room();
		room.setID(id);
		return requester.requestObjectByOwnId(room);
	}
	

	private Group requestGroup(int id) {
		Group group = new Group();
		group.setID(id);
		return requester.requestObjectByOwnId(group);
	}
	
	private boolean removeCalendar(int id) {
		Calendar calendar = new Calendar();
		calendar.setID(id);
		return requester.removeObjectByOwnId(calendar);
	}
	

	private boolean removeCalendarEntry(int id) {
		CalendarEntry calendarEntry = new CalendarEntry();
		calendarEntry.setID(id);
		return requester.removeObjectByOwnId(calendarEntry);
	}
	
	private boolean removeInvite(int id){
		Invite invite = new Invite();
		invite.setID(id);
		return requester.removeObjectByOwnId(invite);
	}

	private boolean removeUser(int id) {
		User user = new User();
		user.setID(id);
		return requester.removeObjectByOwnId(user);
	}
	

	private boolean removeRoom(int id) {
		Room room= new Room();
		room.setID(id);
		return requester.removeObjectByOwnId(room);
	}
	

	private boolean removeGroup(int id) {
		Group group = new Group();
		group.setID(id);
		return requester.removeObjectByOwnId(group);
	}
	
	private String buildErrorMessage(String message, Object obj){
		return message + obj.getClass().getSimpleName();
	}
	
}
