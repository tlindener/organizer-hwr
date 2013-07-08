package organizer.networklayer.test.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import organizer.networklayer.network.RequestHandler;
import organizer.networklayer.network.json.JsonJavaIISRequestHandler;
import organizer.networklayer.network.json.JsonJavaRequestHandler;
import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Group;
import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

/**
 * JUnit Test class for integration test of NetworkLayer and server back end.
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public class TestSuite implements ErrorConstans {
	/** default {@link RequestHandler} for the test */
	private static RequestHandler requester = null;
	/** default password for the created {@link User} */
	private final String defaultPassword = "1234";

	/**
	 * Set ups the {@link RequestHandler}
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		requester = new JsonJavaIISRequestHandler("localhost", 80);
		// requester = new JsonJavaRequestHandler("localhost", 48585);
	}

	@AfterClass
	public static void tearDown() throws Exception {

	}

	@Before
	public void setUpEachTest() throws Exception {

	}

	/**
	 * Deletes the database to test the next method with default values
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDownEachTest() throws Exception {
		boolean result = requester.dropDatabase();
		// System.err.println("TearDown Result: " + result);
		assertTrue(result);
	}

	@Ignore
	public void testRequestObjectByProperty() {
		fail("Not yet implemented");
	}

	/**
	 * Tests
	 * {@link RequestHandler#requestObjectByProperty(AbstractOrganizerObject)}
	 */
	@Test
	public void testRequestObjectByOwnId() {
		// Create default data and returns the users to act with
		User[] userList = createDefaultData(4, 4, 2);

		// Login user 2 to request calendar
		User loggedUser2 = login(userList[1].getMailAddress());
		// request calendar of user 2
		Calendar calendarUser2 = requestCalendar(loggedUser2.getCalendarIds()
				.get(0));
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendarUser2);

		// login user 1 to test functionalities
		User loggedInUser = login(userList[0].getMailAddress());
		// Should have invites and at least one calendar. Therefore not groups
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, loggedInUser.getCalendarIds()
				.isEmpty());
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, loggedInUser.getInviteIds()
				.isEmpty());
		assertTrue(ERROR_TYPE_LIST + LIST_FILLED, loggedInUser.getGroupIds()
				.isEmpty());

		// User can only have on Calendar at the moment
		// request this calendar
		Calendar calendarUser1 = requestCalendar(loggedInUser.getCalendarIds()
				.get(0));
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendarUser1);

		// Request entries by their id and compare to entries in calendar
		for (CalendarEntry entry : calendarUser1.getCalendarEntries()) {
			// request own entry by id
			CalendarEntry requestedEntry = requestCalendarEntry(entry.getID());
			assertNotNull(ERROR_TYPE_REQ + NULL_ENTRY, requestedEntry);
			// equals method won't work - the hashcode is different
			assertEquals("No equal entries", requestedEntry.toString(),
					entry.toString());
			if (!entry.getInviteIds().isEmpty()) {
				for (int id : entry.getInviteIds()) {
					Invite in = requestInvite(id);
					assertNotNull(ERROR_TYPE_REQ + NULL_INVITE, in);
					assertTrue(in.getCalendarEntryId() == entry.getID());
					assertTrue(in.getOwnerId() != 0);
				}

			}

		}

		// Request invites and check access to the mentioned entry
		for (int id : loggedInUser.getInviteIds()) {
			// request own invites
			Invite invite = requestInvite(id);
			assertNotNull(ERROR_TYPE_REQ + NULL_INVITE, invite);
			// request entry you are invited to
			CalendarEntry entry = requestCalendarEntry(invite
					.getCalendarEntryId());
			assertNotNull(ERROR_TYPE_REQ + NULL_ENTRY, entry);
		}
		// request a group by id
		Group group = requestGroup(1);
		assertNotNull(ERROR_TYPE_REQ + NULL_GROUP, group);
		// request a group by id
		Room room = requestRoom(1);
		assertNotNull(ERROR_TYPE_REQ + NULL_ROOM, room);

		// request own user object completely
		User userRequest = requestUser(userList[0].getID());
		assertNotNull(ERROR_TYPE_REQ + NULL_USER, userRequest);
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, userRequest.getCalendarIds()
				.isEmpty());
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, userRequest.getInviteIds()
				.isEmpty());
		assertTrue(ERROR_TYPE_LIST + LIST_FILLED, userRequest.getGroupIds()
				.isEmpty());

		// Request another user by its id
		User userRequest2 = requestUser(userList[1].getID());
		assertNotNull(ERROR_TYPE_REQ + NULL_USER, userRequest2);
		assertTrue(ERROR_TYPE_LIST + LIST_FILLED, userRequest2.getCalendarIds()
				.isEmpty());
		assertTrue(ERROR_TYPE_LIST + LIST_FILLED, userRequest2.getInviteIds()
				.isEmpty());
		assertTrue(ERROR_TYPE_LIST + LIST_FILLED, userRequest2.getGroupIds()
				.isEmpty());

		// request another user's calendar (calendar from the top of this method
		// is used)
		assertNull(ERROR_TYPE_REQ + ERROR_FORBIDDEN + NOTNULL_CALENDAR,
				requestCalendar(calendarUser2.getID()));
		// request another user's invite you have not send (User 1 invites just
		// User 2)
		assertNull(ERROR_TYPE_REQ + ERROR_FORBIDDEN + NOTNULL_INVITE,
				requestInvite(userList[2].getInviteIds().get(0)));
		// request another user's entry you are not invited to (User 2 invites
		// User 3)
		CalendarEntry noInvite = calendarUser2.getCalendarEntries().get(0);
		assertNull(ERROR_TYPE_REQ + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				requestCalendarEntry(noInvite.getID()));

	}

	@Ignore
	public void testRequestFollowingObjectsByOwnId() {
		// This method uses the requestObjectByOwnID() -->
		// testRequestObjectByOwnId() must be successful
		fail("Not yet implemented");
	}

	/**
	 * Tests {@link RequestHandler#requestAllObjects(AbstractOrganizerObject)}
	 */
	@Test
	public void testRequestAllObjects() {
		User[] userList = createDefaultData(2, 2, 1);

		User loggedInUser = login(userList[0].getMailAddress());

		List<Group> groups = requester.requestAllObjects(new Group());
		assertNotNull(ERROR_TYPE_LIST + NULL_LIST, groups);
		assertFalse(ERROR_TYPE_LIST + LIST_FILLED, groups.isEmpty());

		List<Room> rooms = requester.requestAllObjects(new Room());
		assertNotNull(ERROR_TYPE_LIST + NULL_LIST, rooms);
		assertFalse(ERROR_TYPE_LIST + LIST_FILLED, rooms.isEmpty());

		List<User> user = requester.requestAllObjects(new User());
		assertNotNull(ERROR_TYPE_LIST + NULL_LIST, user);
		assertFalse(ERROR_TYPE_LIST + LIST_FILLED, user.isEmpty());
		for (User u : user) {
			assertTrue(ERROR_TYPE_LIST + LIST_EMPTY, u.getCalendarIds()
					.isEmpty());
			assertTrue(ERROR_TYPE_LIST + LIST_EMPTY, u.getInviteIds().isEmpty());
			assertTrue(ERROR_TYPE_LIST + LIST_FILLED, u.getGroupIds().isEmpty());
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
		assertNotNull(ERROR_TYPE_REQ + NULL_GROUP, group);

		boolean resultFirstUserToGroup = requester.addUserToGroup(loggedInUser,
				group);
		assertTrue(ERROR_TYPE_ADD + USER_GROUP_FAIL, resultFirstUserToGroup);

		loggedInUser = login(userList[1].getMailAddress());
		boolean resultSecondUserToGroup = requester.addUserToGroup(
				loggedInUser, group);
		assertTrue(ERROR_TYPE_ADD + USER_GROUP_FAIL, resultSecondUserToGroup);

		loggedInUser = login(userList[0].getMailAddress());
		List<User> user2 = requester.requestAllObjects(new User());
		assertNotNull(ERROR_TYPE_LIST + NULL_LIST, user2);
		assertFalse(ERROR_TYPE_LIST + LIST_FILLED, user2.isEmpty());

		for (int i = 0; i < user2.size(); i++) {
			assertTrue(ERROR_TYPE_LIST + LIST_EMPTY, user2.get(i)
					.getCalendarIds().isEmpty());
			assertTrue(ERROR_TYPE_LIST + LIST_EMPTY, user2.get(i)
					.getInviteIds().isEmpty());
			if (i == 0 || i == 1) {
				assertFalse(ERROR_TYPE_LIST + LIST_FILLED, user2.get(i)
						.getGroupIds().isEmpty());
			} else {
				assertTrue(ERROR_TYPE_LIST + LIST_EMPTY, user2.get(i)
						.getGroupIds().isEmpty());
			}
		}
	}

	/**
	 * Tests {@link RequestHandler#requestAllObjectsByProperty()}
	 */
	@Test
	public void testRequestAllObjectsByProperty() {
		User[] userList = createDefaultData(2, 2, 1);
		User loggedInUser = login(userList[0].getMailAddress());

		CalendarEntry requestEntry = new CalendarEntry();
		requestEntry.setRequestProperty(CalendarEntry.OWNER_ID, ""
				+ loggedInUser.getID());
		List<CalendarEntry> entries = requester
				.requestAllObjectsByProperty(requestEntry);
		for (CalendarEntry entry : entries) {
			assertNotNull(entry);
			assertTrue(entry.getOwnerId() == loggedInUser.getID());
		}
		assertTrue(entries.size() == 4);

		CalendarEntry requestEntry2 = new CalendarEntry();
		requestEntry2.setRequestProperty(CalendarEntry.ROOM_ID, "" + 1);
		List<CalendarEntry> entries2 = requester
				.requestAllObjectsByProperty(requestEntry2);
		for (CalendarEntry entry : entries2) {
			assertNotNull(entry);
			assertTrue(entry.getOwnerId() == 1);
		}
		assertTrue(entries2.size() == 2);

		List<Group> groups = requester.requestAllObjects(new Group());
		assertNotNull(groups);
		assertFalse(groups.isEmpty());

		boolean result = requester.addUserToGroup(loggedInUser, groups.get(0));
		assertTrue(ERROR_TYPE_ADD + USER_GROUP_FAIL, result);

		Group requestGroup = new Group();
		requestGroup.setRequestProperty(Group.USER_ID,
				"" + loggedInUser.getID());
		List<Group> groups2 = requester
				.requestAllObjectsByProperty(requestGroup);
		assertNotNull(groups);
		assertFalse(groups.isEmpty());
		for (Group group : groups2) {
			assertNotNull(group);
			assertTrue(group.getMemberIds().contains(1));
		}
		assertTrue(groups2.size() == 1);
		assertTrue(groups2.get(0).getID() == groups.get(0).getID());
	}

	/**
	 * Tests {@link RequestHandler#addObject(AbstractOrganizerObject)}
	 */
	@Test
	public void testAddObject() {

		User user1 = addUser("Steffen", "Baumann");
		assertNotNull(ERROR_TYPE_ADD + NULL_USER, user1);
		User user2 = addUser("Tobias", "Lindener");
		assertNotNull(ERROR_TYPE_ADD + NULL_USER, user2);
		// >>>>>CALENDAR
		login(user1);
		// Add calendar to oneself
		Calendar calendarUser1ToUser1 = addCalendar(user1.getGivenName(),
				user1.getID());
		assertNotNull(ERROR_TYPE_ADD + NULL_CALENDAR, calendarUser1ToUser1);

		// Add calendar to another user should fail
		assertNull(ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_CALENDAR,
				addCalendar(user1.getGivenName(), user2.getID()));

		login(user2);
		// Add calendar to oneself
		Calendar calendarUser2ToUser2 = addCalendar(user2.getGivenName(),
				user2.getID());
		assertNotNull(ERROR_TYPE_ADD + NULL_CALENDAR, calendarUser2ToUser2);

		// Add calendar to another user should fail
		assertNull(ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_CALENDAR,
				addCalendar(user2.getGivenName(), user1.getID()));

		// CALENDAR<<<<<

		// >>>>>ROOM
		login(user1);
		Room room1 = addRoom("Created for JUnit test", "Testroom 1", 12);
		assertNotNull(ERROR_TYPE_ADD + NULL_ROOM, room1);

		login(user2);
		Room room2 = addRoom("Created for JUnit test", "Testroom 2", 24);
		assertNotNull(ERROR_TYPE_ADD + NULL_ROOM, room2);

		// ROOM<<<<<

		// >>>>>ENTRIES

		login(user1);
		// Add entry to own calendar
		CalendarEntry entryUser1ToUser1NoRoom = addCalendarEntry(
				user1.getGivenName(), calendarUser1ToUser1.getID(),
				user1.getID(), 0);
		assertNotNull(ERROR_TYPE_ADD + NULL_ENTRY, entryUser1ToUser1NoRoom);

		// Add entry to another user should fail
		assertNull(
				ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				addCalendarEntry(user1.getGivenName(),
						calendarUser1ToUser1.getID(), user2.getID(), 0));
		// Add entry to another user's calendar should fail
		assertNull(
				ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				addCalendarEntry(user1.getGivenName(),
						calendarUser2ToUser2.getID(), user1.getID(), 0));
		// Add entry to another user's calendar and user itself should fail
		assertNull(
				ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				addCalendarEntry(user1.getGivenName(),
						calendarUser2ToUser2.getID(), user2.getID(), 0));

		// Add entry to own calendar with room created by oneself
		CalendarEntry entryUser1ToUser1WithRoom1 = addCalendarEntry(
				user1.getGivenName(), calendarUser1ToUser1.getID(),
				user1.getID(), room1.getID());
		assertNotNull(ERROR_TYPE_ADD + NULL_ENTRY, entryUser1ToUser1WithRoom1);
		// Add entry to own calendar with room created by another user
		CalendarEntry entryUser1ToUser1WithRoom2 = addCalendarEntry(
				user1.getGivenName(), calendarUser1ToUser1.getID(),
				user1.getID(), room2.getID());
		assertNotNull(ERROR_TYPE_ADD + NULL_ENTRY, entryUser1ToUser1WithRoom2);

		// Add entry to another user should fail
		assertNull(
				ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				addCalendarEntry(user1.getGivenName(),
						calendarUser1ToUser1.getID(), user2.getID(),
						room1.getID()));
		// Add entry to another user's calendar should fail
		assertNull(
				ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				addCalendarEntry(user1.getGivenName(),
						calendarUser2ToUser2.getID(), user1.getID(),
						room1.getID()));
		// Add entry to another user's calendar and user itself should fail
		assertNull(
				ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				addCalendarEntry(user1.getGivenName(),
						calendarUser2ToUser2.getID(), user2.getID(),
						room1.getID()));

		login(user2);
		// Add entry to own calendar
		CalendarEntry entryUser2ToUser2NoRoom = addCalendarEntry(
				user2.getGivenName(), calendarUser2ToUser2.getID(),
				user2.getID(), 0);
		assertNotNull(ERROR_TYPE_ADD + NULL_ENTRY, entryUser2ToUser2NoRoom);

		// Add entry to another user should fail
		assertNull(
				ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				addCalendarEntry(user2.getGivenName(),
						calendarUser2ToUser2.getID(), user1.getID(), 0));
		// Add entry to another user's calendar should fail
		assertNull(
				ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				addCalendarEntry(user2.getGivenName(),
						calendarUser1ToUser1.getID(), user2.getID(), 0));
		// Add entry to another user's calendar and user itself should fail
		assertNull(
				ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				addCalendarEntry(user2.getGivenName(),
						calendarUser1ToUser1.getID(), user1.getID(), 0));

		// Add entry to own calendar with room created by oneself
		CalendarEntry entryUser2ToUser2WithRoom2 = addCalendarEntry(
				user2.getGivenName(), calendarUser2ToUser2.getID(),
				user2.getID(), room2.getID());
		assertNotNull(ERROR_TYPE_ADD + NULL_ENTRY, entryUser2ToUser2WithRoom2);
		// Add entry to own calendar with room created by another user
		CalendarEntry entryUser2ToUser2WithRoom1 = addCalendarEntry(
				user2.getGivenName(), calendarUser2ToUser2.getID(),
				user2.getID(), room1.getID());
		assertNotNull(ERROR_TYPE_ADD + NULL_ENTRY, entryUser2ToUser2WithRoom1);

		// Add entry to another user should fail
		assertNull(
				ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				addCalendarEntry(user2.getGivenName(),
						calendarUser2ToUser2.getID(), user1.getID(),
						room1.getID()));
		// Add entry to another user's calendar should fail
		assertNull(
				ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				addCalendarEntry(user2.getGivenName(),
						calendarUser1ToUser1.getID(), user2.getID(),
						room1.getID()));
		// Add entry to another user's calendar and user itself should fail
		assertNull(
				ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_ENTRY,
				addCalendarEntry(user2.getGivenName(),
						calendarUser1ToUser1.getID(), user1.getID(),
						room1.getID()));

		// ENTIRES<<<<<

		// >>>>>INVITES

		login(user1);
		// Add invite with no room set
		Invite inviteUser2ToUser1sEntryNoRoom = addInvite(
				entryUser1ToUser1NoRoom.getID(), user2.getID());
		assertNotNull(ERROR_TYPE_ADD + NULL_INVITE,
				inviteUser2ToUser1sEntryNoRoom);
		// Add invite with room set
		Invite inviteUser2ToUser1sEntryWithRoom1 = addInvite(
				entryUser1ToUser1WithRoom1.getID(), user2.getID());
		assertNotNull(ERROR_TYPE_ADD + NULL_INVITE,
				inviteUser2ToUser1sEntryWithRoom1);
		// Add second invite with room set
		Invite inviteUser2ToUser1sEntryWithRoom2 = addInvite(
				entryUser1ToUser1WithRoom2.getID(), user2.getID());
		assertNotNull(ERROR_TYPE_ADD + NULL_INVITE,
				inviteUser2ToUser1sEntryWithRoom2);

		// Add invite for other user to its own calendar entry without room
		assertNull(ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_INVITE,
				addInvite(entryUser2ToUser2NoRoom.getID(), user2.getID()));
		// Add invite for other user to its own calendar entry with room
		assertNull(ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_INVITE,
				addInvite(entryUser2ToUser2WithRoom1.getID(), user2.getID()));
		// Add own invite for other users calendar entry
		assertNull(ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_INVITE,
				addInvite(entryUser2ToUser2WithRoom2.getID(), user1.getID()));

		login(user2);
		// Add invite with no room set
		Invite inviteUser1ToUser2sEntryNoRoom = addInvite(
				entryUser2ToUser2NoRoom.getID(), user1.getID());
		assertNotNull(ERROR_TYPE_ADD + NULL_INVITE,
				inviteUser1ToUser2sEntryNoRoom);
		// Add invite with room set
		Invite inviteUser1ToUser2sEntryWithRoom1 = addInvite(
				entryUser2ToUser2WithRoom1.getID(), user1.getID());
		assertNotNull(ERROR_TYPE_ADD + NULL_INVITE,
				inviteUser1ToUser2sEntryWithRoom1);
		// Add second invite with room set
		Invite inviteUser1ToUser2sEntryWithRoom2 = addInvite(
				entryUser2ToUser2WithRoom2.getID(), user2.getID());
		assertNotNull(ERROR_TYPE_ADD + NULL_INVITE,
				inviteUser1ToUser2sEntryWithRoom2);

		// Add invite for other user to its own calendar entry without room
		assertNull(ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_INVITE,
				addInvite(entryUser1ToUser1NoRoom.getID(), user1.getID()));
		// Add invite for other user to its own calendar entry with room
		assertNull(ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_INVITE,
				addInvite(entryUser1ToUser1WithRoom1.getID(), user1.getID()));
		// Add own invite for other users calendar entry
		assertNull(ERROR_TYPE_ADD + ERROR_FORBIDDEN + NOTNULL_INVITE,
				addInvite(entryUser1ToUser1WithRoom2.getID(), user2.getID()));

		// INVITES<<<<<

		// >>>>>GROUPS
		login(user1);
		Group group1 = addGroup(user1.getGivenName() + "'s group");
		assertNotNull(ERROR_TYPE_ADD + NULL_GROUP, group1);

		login(user2);
		Group group2 = addGroup(user2.getGivenName() + "'s group");
		assertNotNull(ERROR_TYPE_ADD + NULL_GROUP, group2);
		// GROUPS<<<<<

	}

	/**
	 * Creates default test data
	 */
	private User[] createDefaultData(int calendarEntryWithoutRoomPerUser,
			int calendarEntryWithRoomPerUser, int inviteSentFromUser) {

		User user1 = addUser("Steffen", "Baumann");
		assertNotNull(ERROR_TYPE_ADD + NULL_USER, user1);
		User user2 = addUser("Tobias", "Lindener");
		assertNotNull(ERROR_TYPE_ADD + NULL_USER, user2);
		User user3 = addUser("Svenja", "Moehring");
		assertNotNull(ERROR_TYPE_ADD + NULL_USER, user2);
		User[] userList = new User[] { user1, user2, user3 };

		for (int i = 0; i < userList.length; i++) {

			login(userList[i]);
			// Add calendar to oneself
			Calendar calendarUser1ToUser1 = addCalendar(
					userList[i].getGivenName(), userList[i].getID());
			assertNotNull(ERROR_TYPE_ADD + NULL_CALENDAR, calendarUser1ToUser1);
			userList[i] = login(userList[i].getMailAddress());

			Room room1 = addRoom("Created for JUnit test", "Testroom "
					+ (i + 1), 5 * (i + 1));
			assertNotNull(ERROR_TYPE_ADD + NULL_ROOM, room1);

			User invite = null;
			if (i == userList.length - 1) {
				invite = userList[0];
			} else {
				invite = userList[i + 1];
			}

			addEntriesAndInvites(userList[i], invite,
					calendarEntryWithRoomPerUser, inviteSentFromUser,
					room1.getID());
			addEntriesAndInvites(userList[i], invite,
					calendarEntryWithoutRoomPerUser, inviteSentFromUser, 0);

			Group group1 = addGroup(userList[i].getGivenName() + "'s group");
			assertNotNull(ERROR_TYPE_ADD + NULL_GROUP, group1);

		}

		for (int i = 0; i < userList.length; i++) {
			userList[i] = login(userList[i].getMailAddress());
		}
		return userList;
	}

	/**
	 * Creates {@link CalendarEntry}s and {@link Invite}s
	 */
	private void addEntriesAndInvites(User owner, User invitee,
			int calendarEntryCount, int inviteCount, int roomId) {
		for (int i = 0; i < calendarEntryCount; i++) {

			CalendarEntry entry = addDefaultEntry(owner, i + ". entry", roomId);
			assertNotNull(ERROR_TYPE_ADD + NULL_ENTRY, entry);

			if (i < inviteCount) {
				Invite inviteForOtherUser = addInvite(entry.getID(),
						invitee.getID());
				assertNotNull(ERROR_TYPE_ADD + NULL_INVITE, inviteForOtherUser);
			}
		}
	}

	/**
	 * Login a {@link User} by the object
	 * 
	 * @param userToLogin
	 *            - user to login
	 */
	private void login(User userToLogin) {
		login(userToLogin.getMailAddress());
	}

	/**
	 * Tests {@link RequestHandler#login(String, String)}
	 */
	@Test
	public void testLogin() {
		User user = addUser("Steffen", "Baumann");
		//User must not be null - register has to return an user
		assertNotNull(ERROR_TYPE_ADD + NULL_USER, user);
		login(user);
	}
	/**
	 * Login a {@link User} by the mail address <br>
	 * Repeating calls during test cause outsourcing in extra method
	 * @param mailAddress
	 *            - address of the user
	 */
	private User login(String mailAddress) {
		User user = requester.login(mailAddress, defaultPassword);
		//User must not be null - login has to return an user
		assertNotNull(ERROR_TYPE_LOGIN + NULL_USER, user);
		return user;
	}
	/**
	 * Tests {@link RequestHandler#registerNewUser(User, String)}
	 */
	@Test
	public void testRegisterNewUser() {
		User user1 = addUser("Steffen", "Baumann");
		assertNotNull(ERROR_TYPE_ADD + NULL_USER, user1);
		User user2 = addUser("Steffen", "Baumann");
		assertNull(user2);
		User user3 = addUser("Tobias", "Lindener");
		assertNotNull(ERROR_TYPE_ADD + NULL_USER, user3);
	}
	/**
	 * Adds a user to the database
	 * Repeating calls during test cause outsourcing in extra method
	 * @param givenName
	 * @param surname
	 * @return added {@link User}
	 */
	private User addUser(String givenName, String surname) {
		//Creating a new user with given name surname and mail address
		User user = new User();
		user.setGivenname(givenName);
		user.setSurname(surname);
		user.setMailAddress(givenName + "." + surname + "@localhost.de");
		//Register new user and return User-Object with ID if succeeded, otherwise null
		return requester.registerNewUser(user, defaultPassword);
	}
	/**
	 * Tests {@link RequestHandler#removeObjectByOwnId(AbstractOrganizerObject)}
	 */
	@Test
	public void testRemoveObjectByOwnId() {
		User[] testUser = createDefaultData(3, 3, 2);

		// Login User 2
		User loggedInUser = login(testUser[1].getMailAddress());
		// Request Calendar of User 2
		Calendar calendarUser2 = requestCalendar(loggedInUser.getCalendarIds()
				.get(0));
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendarUser2);

		// Login User 1
		loggedInUser = login(testUser[0].getMailAddress());
		// Request Calendar
		Calendar calendar = requestCalendar(loggedInUser.getCalendarIds()
				.get(0));
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendar);
		// Request Invites for each CalendarEntry
		for (CalendarEntry entry : calendar.getCalendarEntries()) {
			List<Invite> invites = requester.requestFollowingObjectsByOwnId(
					entry.getInviteIds(), new Invite());
			assertNotNull(ERROR_TYPE_LIST + NULL_LIST);
			for (Invite invite : invites) {
				assertNotNull(ERROR_TYPE_REQ + NULL_INVITE, invite);
				boolean resultRemoveInvite = removeInvite(invite.getID());
				assertTrue(ERROR_TYPE_REM + FALSE_INVITE, resultRemoveInvite);
			}
			// Remove Entry without Invites
			boolean resultRemoveEntry = removeCalendarEntry(entry.getID());
			assertTrue(ERROR_TYPE_REM + FALSE_ENTRY, resultRemoveEntry);
		}

		// Login User 2
		loggedInUser = login(testUser[1].getMailAddress());
		// Check whether still invites exist
		assertTrue(ERROR_TYPE_LIST + LIST_FILLED, loggedInUser.getInviteIds()
				.isEmpty());

		// Login User 1
		loggedInUser = login(testUser[0].getMailAddress());
		// Remove Group one is not added to
		Group group = requestGroup(1);
		boolean resultRemoveGroup = removeGroup(group.getID());
		assertFalse(ERROR_TYPE_REM + TRUE_GROUP, resultRemoveGroup);

		// Add User to Group
		boolean resultAddToGroup = requester
				.addUserToGroup(loggedInUser, group);
		assertTrue(ERROR_TYPE_ADD + USER_GROUP_FAIL, resultAddToGroup);

		// Remove Group one is added to
		boolean resultRemoveGroup2 = removeGroup(group.getID());
		assertTrue(ERROR_TYPE_REM + FALSE_GROUP, resultRemoveGroup2);

		// Remove own got Invites - should fail
		for (int id : loggedInUser.getInviteIds()) {
			boolean result = removeInvite(id);
			assertFalse(ERROR_TYPE_REM + TRUE_INVITE, result);
		}

		// Remove own Calendar
		for (int id : loggedInUser.getCalendarIds()) {
			boolean result = removeCalendar(id);
			assertTrue(ERROR_TYPE_REM + FALSE_CALENDAR, result);
		}

		// Remove Room
		boolean resultRemoveRoom = removeRoom(1);
		assertTrue(ERROR_TYPE_REM + FALSE_ROOM, resultRemoveRoom);

		// Remove other user's Invites - User 3, because User 2 has no invites
		// anymore
		for (int id : testUser[2].getInviteIds()) {
			boolean result = removeInvite(id);
			assertFalse(ERROR_TYPE_REM + ERROR_FORBIDDEN + TRUE_INVITE, result);
		}
		;

		// Remove other user's Entry
		assertFalse(ERROR_TYPE_REM + ERROR_FORBIDDEN + TRUE_ENTRY,
				removeCalendarEntry(7));

		// Remove other user's Calendar
		assertFalse(ERROR_TYPE_REM + ERROR_FORBIDDEN + TRUE_CALENDAR,
				removeCalendar(calendarUser2.getID()));

		// Remove other user
		assertFalse(ERROR_TYPE_REM + ERROR_FORBIDDEN + TRUE_USER,
				removeUser(testUser[1].getID()));

		// Login User 2
		loggedInUser = login(testUser[1].getMailAddress());
		// Request Calendar
		Calendar calendar2 = requestCalendar(loggedInUser.getCalendarIds().get(
				0));
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendar2);
		// remove entries of this calendar
		for (CalendarEntry entry : calendar2.getCalendarEntries()) {
			// Remove Entry with Invites
			boolean resultRemoveEntry = removeCalendarEntry(entry.getID());
			assertTrue(ERROR_TYPE_REM + FALSE_ENTRY, resultRemoveEntry);
		}

		// Login as user 3
		loggedInUser = login(testUser[2].getMailAddress());
		// Remove User 3
		boolean resultRemoveUser = removeUser(loggedInUser.getID());
		assertTrue(ERROR_TYPE_REM + FALSE_USER, resultRemoveUser);
	}




	
	/**
	 * Tests {@link RequestHandler#acceptInvite(Invite)}
	 */
	@Test
	public void testAcceptInvite() {

		User[] testUser = createDefaultData(3, 3, 2);
		// Login User 1
		User loggedInUser = login(testUser[0].getMailAddress());
		// Request Calendar User 1
		Calendar calendar = requestCalendar(loggedInUser.getCalendarIds()
				.get(0));
		// Calendar should not be null
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendar);
		// save list length
		int entrySize = calendar.getCalendarEntries().size();
		// request all invites of user
		List<Invite> invites = requester.requestFollowingObjectsByOwnId(
				loggedInUser.getInviteIds(), new Invite());
		// List is not null
		assertNotNull(ERROR_TYPE_LIST + NULL_LIST, invites);
		// List is not empty
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, invites.isEmpty());
		int acceptResult = 0;
		//
		for (Invite in : invites) {
			// Invite not null
			assertNotNull(ERROR_TYPE_REQ + NULL_INVITE, in);
			// invite not accepted yet
			assertTrue(in.isAccepted() == 0);
			// accept invite
			acceptResult = requester.acceptInvite(in);
			// new CalendarEntry is added - id is != 0
			assertTrue(ERROR_TYPE_ADD + NULL_ENTRY, acceptResult != 0);
		}
		// request calendar again - to update new CalendarEntries
		calendar = requestCalendar(loggedInUser.getCalendarIds().get(0));
		// should not be null
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendar);
		// compare value of list length
		int entrySize2 = calendar.getCalendarEntries().size();
		// new length should be higher
		assertTrue(ERROR_TYPE_ADD + NULL_ENTRY, entrySize < entrySize2);
		// all invites were accepted --> invites.size + oldEntry.size ==
		// newEntry.size
		assertTrue(ERROR_TYPE_ADD + NULL_ENTRY, (entrySize + loggedInUser
				.getInviteIds().size()) == entrySize2);

		// last generated Entry in list contained?
		boolean contained = false;
		for (CalendarEntry entry : calendar.getCalendarEntries()) {
			// entry must not be null
			assertNotNull(ERROR_TYPE_REQ + NULL_ENTRY, entry);
			// last accepted entry?
			if (entry.getID() == acceptResult) {
				contained = true;
				break;
			}
		}
		// one entry has to have the accepted invite's entry
		assertTrue(ERROR_TYPE_ADD + NULL_ENTRY, contained);

		// double accpeting an invite should fail
		List<Invite> invites2 = requester.requestFollowingObjectsByOwnId(
				loggedInUser.getInviteIds(), new Invite());
		// List is not null
		assertNotNull(ERROR_TYPE_LIST + NULL_LIST, invites2);
		// List is not empty
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, invites2.isEmpty());
		for (Invite in : invites2) {
			// is not null
			assertNotNull(ERROR_TYPE_REQ + NULL_INVITE, in);
			// is already accepted
			assertTrue(in.isAccepted() == 1);
			// second accept
			acceptResult = requester.acceptInvite(in);
			// should fail
			assertTrue(acceptResult == 0);
			// decline should be possible
			acceptResult = requester.declineInvite(in);
			assertTrue(acceptResult == 1);
		}
		//
		//
		// Login User2
		loggedInUser = login(testUser[1].getMailAddress());
		// Request Calendar of User 2
		Calendar calendarUser2 = requestCalendar(loggedInUser.getCalendarIds()
				.get(0));
		// Calendar is not null
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendarUser2);
		// Request Invites of User 2
		List<Invite> invitesUser2 = requester.requestFollowingObjectsByOwnId(
				loggedInUser.getInviteIds(), new Invite());

		// Login User3
		loggedInUser = login(testUser[2].getMailAddress());
		// Accept other Users Invites
		for (Invite in : invitesUser2) {
			// Invite is not null
			assertNotNull(ERROR_TYPE_REQ + NULL_INVITE, in);
			// is not accepted yet
			assertTrue(in.isAccepted() == 0);
			// accpet the invite
			acceptResult = requester.acceptInvite(in);
			// should fail with user 3 not owner of user 2's invites
			assertTrue(ERROR_TYPE_ADD + NULL_ENTRY, acceptResult == 0);
		}
	}

	/**
	 * Tests {@link RequestHandler#declineInvite(Invite)}
	 */
	@Test
	public void testDeclineInvite() {
		User[] testUser = createDefaultData(3, 3, 2);
		// Login User 1
		User loggedInUser = login(testUser[0].getMailAddress());
		// Request Calendar User 1
		Calendar calendar = requestCalendar(loggedInUser.getCalendarIds()
				.get(0));
		// Calendar should not be null
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendar);
		// save list length
		int entrySize = calendar.getCalendarEntries().size();
		// request all invites of user
		List<Invite> invites = requester.requestFollowingObjectsByOwnId(
				loggedInUser.getInviteIds(), new Invite());
		// List is not null
		assertNotNull(ERROR_TYPE_LIST + NULL_LIST, invites);
		// List is not empty
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, invites.isEmpty());
		int declineResult = 0;
		//
		for (Invite in : invites) {
			// Invite not null
			assertNotNull(ERROR_TYPE_REQ + NULL_INVITE, in);
			// invite not accepted yet
			assertTrue(in.isAccepted() == 0);
			// decline invite
			declineResult = requester.declineInvite(in);
			// new CalendarEntry is added - id is != 0
			assertTrue(ERROR_TYPE_ADD + NULL_ENTRY, declineResult == 1);
		}
		// request calendar again - to update new CalendarEntries
		calendar = requestCalendar(loggedInUser.getCalendarIds().get(0));
		// should not be null
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendar);
		// compare value of list length
		int entrySize2 = calendar.getCalendarEntries().size();
		// new length should be the same
		assertTrue(ERROR_TYPE_ADD + NULL_ENTRY, entrySize == entrySize2);

		// double declining an invite should fail
		List<Invite> invites2 = requester.requestFollowingObjectsByOwnId(
				loggedInUser.getInviteIds(), new Invite());
		// List is not null
		assertNotNull(ERROR_TYPE_LIST + NULL_LIST, invites2);
		// List is not empty
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, invites2.isEmpty());
		for (Invite in : invites2) {
			// is not null
			assertNotNull(ERROR_TYPE_REQ + NULL_INVITE, in);
			// is already accepted
			assertTrue(in.isAccepted() == -1);
			// second accept
			declineResult = requester.declineInvite(in);
			// should fail
			assertTrue(declineResult == 0);
			declineResult = requester.acceptInvite(in);
			assertTrue(declineResult != 0);
		}
		// request calendar again - to update new CalendarEntries
		calendar = requestCalendar(loggedInUser.getCalendarIds().get(0));
		// should not be null
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendar);
		// compare value of list length
		int entrySize3 = calendar.getCalendarEntries().size();
		// new length should be higher
		assertTrue(ERROR_TYPE_ADD + NULL_ENTRY, entrySize2 < entrySize3);
		// all invites were accepted --> invites.size + oldEntry.size ==
		// newEntry.size
		assertTrue(ERROR_TYPE_ADD + NULL_ENTRY, (entrySize2 + loggedInUser
				.getInviteIds().size()) == entrySize3);

		//
		// Login User2
		loggedInUser = login(testUser[1].getMailAddress());
		// Request Calendar of User 2
		Calendar calendarUser2 = requestCalendar(loggedInUser.getCalendarIds()
				.get(0));
		// Calendar is not null
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendarUser2);
		// Request Invites of User 2
		List<Invite> invitesUser2 = requester.requestFollowingObjectsByOwnId(
				loggedInUser.getInviteIds(), new Invite());

		// Login User3
		loggedInUser = login(testUser[2].getMailAddress());
		// Accept other Users Invites
		for (Invite in : invitesUser2) {
			// Invite is not null
			assertNotNull(ERROR_TYPE_REQ + NULL_INVITE, in);
			// is not declined yet
			assertTrue(in.isAccepted() == 0);
			// decline the invite
			declineResult = requester.declineInvite(in);
			// should fail with user 3 not owner of user 2's invites
			assertTrue(ERROR_TYPE_ADD + NULL_ENTRY, declineResult == 0);
		}
	}

	/**
	 * Tests {@link RequestHandler#updateObject(AbstractOrganizerObject)}
	 */
	@Test
	public void testUpdateObject() {

		User[] testUser = createDefaultData(3, 3, 2);

		User loggedInUser = login(testUser[1].getMailAddress());
		Calendar calendarUser2 = requestCalendar(loggedInUser.getCalendarIds()
				.get(0));
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendarUser2);

		loggedInUser = login(testUser[0].getMailAddress());

		Calendar calendarUser1 = requestCalendar(loggedInUser.getCalendarIds()
				.get(0));
		assertNotNull(ERROR_TYPE_REQ + NULL_CALENDAR, calendarUser1);
		for (CalendarEntry entry : calendarUser1.getCalendarEntries()) {
			// Update Entry without Invites
			boolean resultUpdateEntry = updateCalendarEntry(entry);
			assertTrue(ERROR_TYPE_UPD + FALSE_ENTRY, resultUpdateEntry);
		}

		// Update Group one is not added to
		Group group = requestGroup(1);
		boolean resultUpdateGroup = updateGroup(group.getID());
		assertFalse(ERROR_TYPE_UPD + TRUE_GROUP, resultUpdateGroup);

		// Add User to Group
		boolean resultAddToGroup = requester
				.addUserToGroup(loggedInUser, group);
		assertTrue(ERROR_TYPE_ADD + USER_GROUP_FAIL, resultAddToGroup);

		// Update Group one is added to
		boolean resultUpdateGroup2 = updateGroup(group.getID());
		assertTrue(ERROR_TYPE_UPD + FALSE_GROUP, resultUpdateGroup2);

		// Update own Calendar
		for (int id : loggedInUser.getCalendarIds()) {
			boolean result = updateCalendar(id);
			assertTrue(ERROR_TYPE_UPD + FALSE_CALENDAR, result);
		}

		// Update Room
		boolean resultUpdateRoom = updateRoom(1);
		assertTrue(ERROR_TYPE_UPD + FALSE_ROOM, resultUpdateRoom);

		// Update other user's Entry
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, calendarUser2
				.getCalendarEntries().isEmpty());
		assertFalse(ERROR_TYPE_UPD + ERROR_FORBIDDEN + TRUE_ENTRY,
				updateCalendarEntry(calendarUser2.getCalendarEntries().get(0)));

		// Update other user's Calendar
		assertFalse(ERROR_TYPE_UPD + ERROR_FORBIDDEN + TRUE_CALENDAR,
				updateCalendar(calendarUser2.getID()));

		// Update other user
		assertFalse(ERROR_TYPE_UPD + ERROR_FORBIDDEN + TRUE_USER,
				updateUser(testUser[2].getID()));

		// Login as user 3
		loggedInUser = login(testUser[2].getMailAddress());
		// Update User 3
		boolean resultUpdateUser = updateUser(loggedInUser.getID());
		assertTrue(ERROR_TYPE_UPD + FALSE_USER, resultUpdateUser);

		// Login user 2
		loggedInUser = login(testUser[1].getMailAddress());
		// Update User 2 to same Mailaddress should fail
		resultUpdateUser = updateUser(loggedInUser.getID());
		assertFalse(ERROR_TYPE_UPD + TRUE_USER, resultUpdateUser);
	}

	/**
	 * Tests {@link RequestHandler#addUserToGroup(User, Group)}
	 */
	@Test
	public void testAddUserToGroup() {
		User[] userList = createDefaultData(2, 2, 1);

		User loggedInUser = login(userList[0].getMailAddress());
		List<Integer> ids = Arrays.asList(1, 2);
		List<Group> groups = requester.requestFollowingObjectsByOwnId(ids,
				new Group());
		assertNotNull(ERROR_TYPE_LIST + NULL_LIST, groups);
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, groups.isEmpty());

		for (Group group : groups) {
			boolean result = requester.addUserToGroup(loggedInUser, group);
			assertTrue(ERROR_TYPE_ADD + USER_GROUP_FAIL, result);
		}

		for (Group group : groups) {
			boolean result = requester.addUserToGroup(loggedInUser, group);
			assertFalse(ERROR_TYPE_ADD + ERROR_FORBIDDEN_TWICE
					+ USER_GROUP_SUCCESS, result);
		}

		for (Group group : groups) {
			boolean result = requester.addUserToGroup(userList[1], group);
			assertFalse(ERROR_TYPE_ADD + ERROR_FORBIDDEN + USER_GROUP_SUCCESS,
					result);
		}
	}

	/**
	 * Tests {@link RequestHandler#removeUserFromGroup(User, Group)}
	 */
	@Test
	public void testRemoveUserFromGroup() {
		User[] userList = createDefaultData(2, 2, 1);
		// Login User 1
		User loggedInUser = login(userList[0].getMailAddress());
		// Request all groups
		List<Group> groups = requester.requestAllObjects(new Group());
		// List must not to be null
		assertNotNull(ERROR_TYPE_LIST + NULL_LIST, groups);
		// List should not be empty
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, groups.isEmpty());
		for (Group group : groups) {
			// MemberList should be empty with no user was added up to now
			assertTrue(ERROR_TYPE_LIST + LIST_FILLED, group.getMemberIds()
					.isEmpty());
		}

		// Add all user to all groups
		for (User user : userList) {
			loggedInUser = login(user.getMailAddress());
			for (Group group : groups) {
				// Adds the User to the current Group
				boolean result = requester.addUserToGroup(loggedInUser, group);
				assertTrue(ERROR_TYPE_ADD + USER_GROUP_FAIL, result);
			}
		}

		// Login User 1
		loggedInUser = login(userList[0].getMailAddress());
		// Request all groups
		List<Group> groups2 = requester.requestAllObjects(new Group());
		// List must not to be null
		assertNotNull(ERROR_TYPE_LIST + NULL_LIST, groups2);
		// List should be filled with users now
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, groups2.isEmpty());

		for (Group group : groups2) {
			boolean resultRemoveOtherUser = requester.removeUserFromGroup(
					userList[2], group);
			assertFalse(ERROR_TYPE_REM + ERROR_FORBIDDEN + USER_GROUP_SUCCESS,
					resultRemoveOtherUser);

			boolean resultRemoveOwnUser = requester.removeUserFromGroup(
					loggedInUser, group);
			assertTrue(ERROR_TYPE_REM + USER_GROUP_FAIL, resultRemoveOwnUser);
		}

		groups2 = requester.requestAllObjects(new Group());
		// List must not to be null
		assertNotNull(ERROR_TYPE_LIST + NULL_LIST, groups2);
		// List should be still filled with users
		assertFalse(ERROR_TYPE_LIST + LIST_EMPTY, groups2.isEmpty());
		for (Group group : groups2) {
			boolean resultUserStillInGroup = group.getMemberIds().contains(
					loggedInUser.getID());
			assertFalse(resultUserStillInGroup);
		}

	}

	// ####################################
	// ##......######..####......####..####
	// ##..####..##..##..####..####..##..##
	// ##..####..##......####..####......##
	// ##..####..##..##..####..####..##..##
	// ##......####..##..####..####..##..##
	// ####################################



	/**
	 * Adds a room to the database
	 * 
	 * @param location
	 * @param description
	 * @param seats
	 * @return added {@link Room}
	 */
	private Room addRoom(String location, String description, int seats) {
		Room room = new Room();
		room.setLocation(location);
		room.setSeats(seats);
		room.setDescription(description);
		return requester.addObject(room);
	}

	/**
	 * Adds a calendarentry to the database
	 * 
	 * @param userName
	 * @param calendarId
	 * @param ownerId
	 * @param roomId
	 * @return added {@link CalendarEntry}
	 */
	private CalendarEntry addCalendarEntry(String userName, int calendarId,
			int ownerId, int roomId) {
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

	/**
	 * Adds a calendar to the database
	 * 
	 * @param userName
	 * @param ownerId
	 * @return added {@link Calendar}
	 */
	private Calendar addCalendar(String userName, int ownerId) {
		Calendar calendar = new Calendar();
		calendar.setDescription("Test calendar - JUnit for " + userName);
		calendar.setName(userName + "'s Calendar");
		calendar.setOwnerId(ownerId);
		return requester.addObject(calendar);
	}

	/**
	 * Adds an invite to the database
	 * 
	 * @param calendarEntryId
	 * @param ownerId
	 * @return added {@link Invite}
	 */
	private Invite addInvite(int calendarEntryId, int ownerId) {
		Invite invite = new Invite();
		invite.setCalendarEntryId(calendarEntryId);
		invite.setOwnerId(ownerId);
		return requester.addObject(invite);
	}

	/**
	 * Adds a group to the database
	 * 
	 * @param description
	 * @return added {@link Group}
	 */
	private Group addGroup(String description) {
		Group group = new Group();
		group.setName("Testgroup");
		group.setDescription(description);
		return requester.addObject(group);
	}

	/**
	 * Adds a defualt calendar entry to the database
	 * 
	 * @param user
	 * @param suffix
	 * @param roomId
	 * @return added {@link CalendarEntry}
	 */
	private CalendarEntry addDefaultEntry(User user, String suffix, int roomId) {
		CalendarEntry calendarEntry = new CalendarEntry();
		calendarEntry.setTitle("DefaultEntry - " + suffix);
		calendarEntry
				.setDescription("!\"$%&/()[]*+'#,;:=><ÄÖÜäöüß Entry automated generated for "
						+ user.getGivenName() + " " + user.getSurname());
		calendarEntry.setStartDate(new Date());
		calendarEntry.setEndDate(new Date());
		calendarEntry.setCalendarId(user.getCalendarIds().get(0));
		calendarEntry.setOwnerId(user.getID());
		calendarEntry.setRoomId(roomId);
		return requester.addObject(calendarEntry);
	}

	/**
	 * Requests a calendar by its id
	 * @param id
	 * @return requested {@link Calendar}
	 */
	private Calendar requestCalendar(int id) {
		Calendar calendar = new Calendar();
		calendar.setID(id);
		return requester.requestObjectByOwnId(calendar);
	}

	/**
	 * Requests a calendar entry by its id
	 * @param id
	 * @return requested {@link CalendarEntry}
	 */
	private CalendarEntry requestCalendarEntry(int id) {
		CalendarEntry calendarEntry = new CalendarEntry();
		calendarEntry.setID(id);
		return requester.requestObjectByOwnId(calendarEntry);
	}

	/**
	 * Requests an invite by its id
	 * @param id
	 * @return requested {@link Invite}
	 */
	private Invite requestInvite(int id) {
		Invite invite = new Invite();
		invite.setID(id);
		return requester.requestObjectByOwnId(invite);
	}

	/**
	 * Requests an user by its id
	 * @param id
	 * @return requested {@link User}
	 */
	private User requestUser(int id) {
		User user = new User();
		user.setID(id);
		return requester.requestObjectByOwnId(user);
	}

	/**
	 * Requests a room by its id
	 * @param id
	 * @return requested {@link Room}
	 */
	private Room requestRoom(int id) {
		Room room = new Room();
		room.setID(id);
		return requester.requestObjectByOwnId(room);
	}

	/**
	 * Requests a group by its id
	 * @param id
	 * @return requested {@link Group}
	 */
	private Group requestGroup(int id) {
		Group group = new Group();
		group.setID(id);
		return requester.requestObjectByOwnId(group);
	}
	/**
	 * Removes a calendar by its id
	 * @param id
	 * @return true if successful otherwise false
	 */
	private boolean removeCalendar(int id) {
		Calendar calendar = new Calendar();
		calendar.setID(id);
		return requester.removeObjectByOwnId(calendar);
	}

	/**
	 * Removes a calendar entry by its id
	 * @param id
	 * @return true if successful otherwise false
	 */
	private boolean removeCalendarEntry(int id) {
		CalendarEntry calendarEntry = new CalendarEntry();
		calendarEntry.setID(id);
		return requester.removeObjectByOwnId(calendarEntry);
	}

	/**
	 * Removes an invite by its id
	 * @param id
	 * @return true if successful otherwise false
	 */
	private boolean removeInvite(int id) {
		Invite invite = new Invite();
		invite.setID(id);
		return requester.removeObjectByOwnId(invite);
	}

	/**
	 * Removes an user by its id
	 * @param id
	 * @return true if successful otherwise false
	 */
	private boolean removeUser(int id) {
		User user = new User();
		user.setID(id);
		return requester.removeObjectByOwnId(user);
	}

	/**
	 * Removes a room by its id
	 * @param id
	 * @return true if successful otherwise false
	 */
	private boolean removeRoom(int id) {
		Room room = new Room();
		room.setID(id);
		return requester.removeObjectByOwnId(room);
	}

	/**
	 * Removes a group by its id
	 * @param id
	 * @return true if successful otherwise false
	 */
	private boolean removeGroup(int id) {
		Group group = new Group();
		group.setID(id);
		return requester.removeObjectByOwnId(group);
	}

	/**
	 * Removes an calendar by its id
	 * @param id
	 * @return true if successful otherwise false
	 */
	private boolean updateCalendar(int id) {
		Calendar calendar = new Calendar();
		calendar.setID(id);
		calendar.setDescription("Updated Calendar");
		calendar.setName("Update Test");
		return requester.updateObject(calendar);
	}
	/**
	 * Removes an calendar entry
	 * @param id
	 * @return true if successful otherwise false
	 */
	private boolean updateCalendarEntry(CalendarEntry entry) {
		entry.setStartDate(new Date());
		entry.setDescription("Updated CalendarEntry");
		entry.setRoomId(2);
		entry.setEndDate(new Date());
		entry.setTitle("Update Test");
		return requester.updateObject(entry);
	}
	/**
	 * Updates an user by its id
	 * @param id
	 * @return true if successful otherwise false
	 */
	private boolean updateUser(int id) {
		User user = new User();
		user.setID(id);
		user.setGivenname("Update");
		user.setSurname("Test");
		user.setMailAddress("updateUser@localhost.de");
		user.setPassword(defaultPassword);
		return requester.updateObject(user);
	}
	/**
	 * Updates a room by its id
	 * @param id
	 * @return true if successful otherwise false
	 */
	private boolean updateRoom(int id) {
		Room room = new Room();
		room.setID(id);
		room.setDescription("Updated Room");
		room.setLocation("UPD47E");
		room.setSeats(123);
		return requester.updateObject(room);
	}
	/**
	 * Updates a group by its id
	 * @param id
	 * @return true if successful otherwise false
	 */
	private boolean updateGroup(int id) {
		Group group = new Group();
		group.setID(id);
		group.setDescription("Updated Group");
		group.setName("Update Test");
		return requester.updateObject(group);
	}
}
