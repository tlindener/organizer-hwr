package test.junit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Date;

import network.RequestHandler;
import network.json.JsonJavaIISRequestHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Group;
import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

public class TestSuite {

	private RequestHandler requester = null;
	private final String defaultPassword = "1234";
	
	@Before
	public void setUp() throws Exception {
		requester = new JsonJavaIISRequestHandler("localhost", 80);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testRequestObjectByProperty() {
		fail("Not yet implemented");
	}

	@Test
	public void testRequestObjectByOwnId() {
		fail("Not yet implemented");
	}

	@Test
	public void testRequestFollowingObjectsByOwnId() {
		fail("Not yet implemented");
	}

	@Test
	public void testRequestAllObjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testRequestAllObjectsByProperty() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAddObject() {

		
		
		User user1 = (User) addUser("Steffen", "Baumann");
		User user2 = (User) addUser("Tobias", "Lindener");
		
//		>>>>>CALENDAR
		
		
		login(user1);
//		Add calendar to oneself
		Calendar calendarUser1ToUser1 = addCalendar(user1.getGivenName(), user1.getID());
		assertNotNull(calendarUser1ToUser1);
		
//		Add calendar to another user should fail
		assertNull(addCalendar(user1.getGivenName(), user2.getID()));
		
		login(user2);
//		Add calendar to oneself
		Calendar calendarUser2ToUser2 = addCalendar(user2.getGivenName(), user2.getID());
		assertNotNull(calendarUser2ToUser2);
		
//		Add calendar to another user should fail		
		assertNull(addCalendar(user2.getGivenName(), user1.getID()));
		
//		CALENDAR<<<<<
		
//		>>>>>ROOM
		login(user1);
		Room room1 = addRoom("Created for JUnit test", "Testroom 1", 12);
		assertNotNull(room1);
		
		login(user2);
		Room room2 = addRoom("Created for JUnit test", "Testroom 2", 24);
		assertNotNull(room2);
		
//		ROOM<<<<<
		
//		>>>>>ENTRIES

		login(user1);
//		Add entry to own calendar
		CalendarEntry entryUser1ToUser1NoRoom = addCalendarEntry(user1.getGivenName(), calendarUser1ToUser1.getID(), user1.getID(), 0);
		assertNotNull(entryUser1ToUser1NoRoom);
		
//		Add entry to another user should fail
		assertNull(addCalendarEntry(user1.getGivenName(), calendarUser1ToUser1.getID(), user2.getID(), 0));
//		Add entry to another user's calendar should fail		
		assertNull(addCalendarEntry(user1.getGivenName(), calendarUser2ToUser2.getID(), user1.getID(), 0));
//		Add entry to another user's calendar and user itself should fail
		assertNull(addCalendarEntry(user1.getGivenName(), calendarUser2ToUser2.getID(), user2.getID(), 0));
		
//		Add entry to own calendar with room created by oneself
		CalendarEntry entryUser1ToUser1WithRoom1 = addCalendarEntry(user1.getGivenName(), calendarUser1ToUser1.getID(), user1.getID(), room1.getID());
		assertNotNull(entryUser1ToUser1WithRoom1);
//		Add entry to own calendar with room created by another user
		CalendarEntry entryUser1ToUser1WithRoom2 = addCalendarEntry(user1.getGivenName(), calendarUser1ToUser1.getID(), user1.getID(), room2.getID());
		assertNotNull(entryUser1ToUser1WithRoom2);
		
//		Add entry to another user should fail
		assertNull(addCalendarEntry(user1.getGivenName(), calendarUser1ToUser1.getID(), user2.getID(), room1.getID()));
//		Add entry to another user's calendar should fail		
		assertNull(addCalendarEntry(user1.getGivenName(), calendarUser2ToUser2.getID(), user1.getID(), room1.getID()));
//		Add entry to another user's calendar and user itself should fail
		assertNull(addCalendarEntry(user1.getGivenName(), calendarUser2ToUser2.getID(), user2.getID(), room1.getID()));
		
		login(user2);
//		Add entry to own calendar
		CalendarEntry entryUser2ToUser2NoRoom = addCalendarEntry(user2.getGivenName(), calendarUser2ToUser2.getID(), user2.getID(), 0);
		assertNotNull(entryUser2ToUser2NoRoom);
		
//		Add entry to another user should fail
		assertNull(addCalendarEntry(user2.getGivenName(), calendarUser2ToUser2.getID(), user1.getID(), 0));
//		Add entry to another user's calendar should fail
		assertNull(addCalendarEntry(user2.getGivenName(), calendarUser1ToUser1.getID(), user2.getID(), 0));
//		Add entry to another user's calendar and user itself should fail
		assertNull(addCalendarEntry(user2.getGivenName(), calendarUser1ToUser1.getID(), user1.getID(), 0));
		
//		Add entry to own calendar with room created by oneself
		CalendarEntry entryUser2ToUser2WithRoom2 = addCalendarEntry(user2.getGivenName(), calendarUser2ToUser2.getID(), user2.getID(), room2.getID());
		assertNotNull(entryUser2ToUser2WithRoom2);
//		Add entry to own calendar with room created by another user
		CalendarEntry entryUser2ToUser2WithRoom1 = addCalendarEntry(user2.getGivenName(), calendarUser2ToUser2.getID(), user2.getID(), room1.getID());
		assertNotNull(entryUser2ToUser2WithRoom1);
		
//		Add entry to another user should fail
		assertNull(addCalendarEntry(user2.getGivenName(), calendarUser2ToUser2.getID(), user1.getID(), room1.getID()));
//		Add entry to another user's calendar should fail
		assertNull(addCalendarEntry(user2.getGivenName(), calendarUser1ToUser1.getID(), user2.getID(), room1.getID()));
//		Add entry to another user's calendar and user itself should fail
		assertNull(addCalendarEntry(user2.getGivenName(), calendarUser1ToUser1.getID(), user1.getID(), room1.getID()));
		
//		ENTIRES<<<<<
		
//		>>>>>INVITES
		
		login(user1);
//		Add invite with no room set
		Invite inviteUser2ToUser1sEntryNoRoom = addInvite(entryUser1ToUser1NoRoom.getID(), user2.getID());
		assertNotNull(inviteUser2ToUser1sEntryNoRoom);
//		Add invite with room set
		Invite inviteUser2ToUser1sEntryWithRoom1 = addInvite(entryUser1ToUser1WithRoom1.getID(), user2.getID());
		assertNotNull(inviteUser2ToUser1sEntryWithRoom1);
//		Add second invite with room set
		Invite inviteUser2ToUser1sEntryWithRoom2 = addInvite(entryUser1ToUser1WithRoom2.getID(), user2.getID());
		assertNotNull(inviteUser2ToUser1sEntryWithRoom2);
		
//		Add invite for other user to its own calendar entry	without room		
		assertNull(addInvite(entryUser2ToUser2NoRoom.getID(), user2.getID()));
//		Add invite for other user to its own calendar entry	with room
		assertNull(addInvite(entryUser2ToUser2WithRoom1.getID(), user2.getID()));
//		Add own invite for other users calendar entry
		assertNull(addInvite(entryUser2ToUser2WithRoom2.getID(), user1.getID()));
		
		login(user2);
//		Add invite with no room set
		Invite inviteUser1ToUser2sEntryNoRoom = addInvite(entryUser2ToUser2NoRoom.getID(), user1.getID());
		assertNotNull(inviteUser1ToUser2sEntryNoRoom);
//		Add invite with room set
		Invite inviteUser1ToUser2sEntryWithRoom1 = addInvite(entryUser2ToUser2WithRoom1.getID(), user1.getID());
		assertNotNull(inviteUser1ToUser2sEntryWithRoom1);
//		Add second invite with room set
		Invite inviteUser1ToUser2sEntryWithRoom2 = addInvite(entryUser2ToUser2WithRoom2.getID(), user2.getID());
		assertNotNull(inviteUser1ToUser2sEntryWithRoom2);
		
//		Add invite for other user to its own calendar entry	without room		
		assertNull(addInvite(entryUser1ToUser1NoRoom.getID(), user1.getID()));
//		Add invite for other user to its own calendar entry	with room
		assertNull(addInvite(entryUser1ToUser1WithRoom1.getID(), user1.getID()));
//		Add own invite for other users calendar entry
		assertNull(addInvite(entryUser1ToUser1WithRoom2.getID(), user2.getID()));
		
//		INVITES<<<<<
		
//		>>>>>GROUPS
		login(user1);
		Group group1 = addGroup(user1.getGivenName()+"'s group");
		assertNotNull(group1);
		
		login(user2);
		Group group2 = addGroup(user2.getGivenName()+"'s group");
		assertNotNull(group2);
//		GROUPS<<<<<
		
	}

	private void login(User userToLogin){
		assertNotNull(requester.login(userToLogin.getMailAddress(), defaultPassword));
	}
	
	@Ignore
	public void testRegisterNewUser() {
		User user1 = (User) addUser("Steffen", "Baumann");
		assertNotNull(user1);
		User user2 = (User) addUser("Steffen", "Baumann");
		assertNull(user2);
		User user3 = (User) addUser("Svenja", "Moehring");
		assertNotNull(user3);
	}

	@Test
	public void testRemoveObjectByOwnId() {
		fail("Not yet implemented");
	}

	@Test
	public void testLogin() {
		
		
		
		fail("Not yet implemented");
	}

	@Test
	public void testAcceptInvite() {
		
		
		fail("Not yet implemented");
	}

	@Test
	public void testDeclineInvite() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUserToGroup() {
		fail("Not yet implemented");
	}

	@Test
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
	
	
	
}
