package run;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import junit.framework.TestSuite;

import network.RequestHandler;
import network.json.JsonJavaIISRequestHandler;
import network.json.JsonJavaRequestHandler;
import network.listener.ProcessListener;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

/**
 * Well, at least not the best programming style, but it works and does, what is
 * is required to do.
 * 
 * This class generates a console program with menu to create default data.
 * Those data can be used for first tests with the GUI. In the first programming
 * phase it was used for manual tests. This approach is replaced by the class
 * {@link TestSuite}, that contains JUnit - automated - tests.
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public class Start implements ProcessListener {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Start();
		// new Start("");
	}
	RequestHandler requester;
	final String[] mainmenu = new String[] {
			"CREATE default USER(with Calendar)", "LOGIN" };

	String[] loggedInUsermenu = new String[] { "CREATE default ENTRY",
			"CREATE default ROOM", "PRINT ENTRIES (by UserID)",
			"PRINT ENTRIES (by Calendar)", "PRINT ALL USER", "PRINT ROOMS",
			"UPDATE ENTRY", "INVITE one User", "CHANGE INVITE state",
			"REMOVE ENTRY from User", "LOGIN with other User" };

	private User loggedInUser = null;

	public Start() {

		requester = new JsonJavaIISRequestHandler("localhost", 80);
		requester.addProcessListener(this);
		handleMainMenu();

	}

	public boolean addObject(AbstractOrganizerObject object) {
		System.out.println("Add Object " + object.getClass().getSimpleName());
		AbstractOrganizerObject obj = requester.addObject(object);
		return obj != null ? true : false;
	}

	public boolean addUserWithCalendar(User user) {
		System.out.println("Add User \"" + user.getGivenName() + " "
				+ user.getSurname() + "\" with " + user.getMailAddress());
		User tmp = requester.registerNewUser(user, "123456");
		if (tmp == null) {
			System.err.println("ERROR USER WAS NOT REGISTERED");
			return false;
		}
		login(tmp.getMailAddress(), "123456");
		System.out.println("\tAdd Calendar for " + user.getMailAddress());
		Calendar cal = new Calendar();
		cal.setOwnerId(tmp.getID());
		cal.setDescription("Testcalendar created by TestRun V2");
		cal.setName(user.getGivenName() + "'s Calendar");
		Calendar tmp2 = requester.addObject(cal);
		boolean result = tmp != null && tmp2 != null;
		return result;
	}

	@Override
	public void getCurrentProcessState(double process) {
		System.out.println(">>>>>>>>>>>>>>>STATUS IS: " + process
				+ " <<<<<<<<<<<<<<<");
	}

	public boolean login(String mail, String pw) {
		System.out.println("Log in " + mail);
		loggedInUser = requester.login(mail, pw);
		if (loggedInUser == null) {
			return false;
		}
		return true;
	}

	public CalendarEntry makeDefaultEntryForLoggedInUser() {
		if (loggedInUser == null || loggedInUser.getCalendarIds().isEmpty()) {
			return null;
		}
		CalendarEntry ce = new CalendarEntry();
		ce.setStartDate(new Date());
		ce.setDescription(loggedInUser.getGivenName() + "s Termin");
		ce.setTitle("Testtermin von " + loggedInUser.getGivenName());
		ce.setOwnerId(loggedInUser.getID());
		ce.setCalendarId(loggedInUser.getCalendarIds().get(0));
		ce.setEndDate(new Date());
		return ce;
	}

	public Room makeDefaultRoom() {
		Room room = new Room();
		room.setDescription("Testroom default");
		room.setLocation("DE34AH3");
		room.setSeats(1234);
		return room;
	}

	public boolean removeCalendarEntry(int id) {
		CalendarEntry request = new CalendarEntry();
		request.setID(id);
		return requester.removeObjectByOwnId(request);
	}

	public boolean requestAllEntriesOfUserByCalendar(User user) {
		System.out.println("Get all CalendarEntries of user with mail "
				+ user.getMailAddress());
		if (user.getCalendarIds().isEmpty()) {
			System.err.println("ERROR: List of Calendar Ids is emtpy");
		} else {
			System.out.println("\tRequest Calendar");
			Calendar request = new Calendar();
			request.setID(user.getCalendarIds().get(0));
			Calendar cal = requester.requestObjectByOwnId(request);
			if (cal == null) {
				System.err.println("USER DOES NOT HAVE A CALENDAR");
				return false;
			}
			System.out.println("\tPrint Entries:");
			for (CalendarEntry entry : cal.getCalendarEntries()) {
				System.out.println("\t\t" + entry);
			}
			return true;
		}
		return false;
	}

	public boolean requestAllEntriesOfUserByUserID(User user) {
		System.out.println("Get all CalendarEntries of user with mail "
				+ user.getMailAddress());
		System.out.println("\tRequest Entries by property OwnerID="
				+ user.getID());
		CalendarEntry request = new CalendarEntry();
		request.setRequestProperty(CalendarEntry.OWNER_ID, "" + user.getID());
		List<CalendarEntry> entryList = requester
				.requestAllObjectsByProperty(request);
		if (entryList.isEmpty()) {
			System.out.println("\t\tno entries");
		} else {
			for (CalendarEntry entry : entryList) {
				System.out.println("\t\t" + entry);
			}
		}
		return true;
	}

	public boolean requestAllObjectsOfType(AbstractOrganizerObject obj) {
		System.out.println("Request all objects of tpye "
				+ obj.getClass().getSimpleName());
		List<AbstractOrganizerObject> list = requester.requestAllObjects(obj);
		if (list.isEmpty()) {
			System.out.println("\t\tno entries");
		} else {
			for (AbstractOrganizerObject element : list) {
				System.out.println("\t" + element);
			}
		}
		return true;
	}

	public boolean sendInvitesTo(int id, int calendarEntryId) {
		Invite in = new Invite();
		in.setCalendarEntryId(calendarEntryId);
		in.setOwnerId(id);
		if (!addObject(in)) {
			return false;
		}
		return true;
	}

	public boolean updateRoomForCalendarEntry(Room room, CalendarEntry ce) {
		System.out.println("Update CalendarEntry " + ce.getID()
				+ "'s roomId => " + room.getID());
		ce.setRoomId(room.getID());
		return requester.updateObject(ce);
	}

	private void createDefaultUsersWithCalendar() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter your login name: ");
		String name = scan.nextLine();
		scan.close();
		User user1 = new User();
		user1.setMailAddress(name);
		user1.setGivenname(name);
		user1.setSurname("Testuser");
		user1.setPhoneNumber("666666666");
		System.out.println(addUserWithCalendar(user1));
	}

	private void createEntryForUser() {
		CalendarEntry entry = makeDefaultEntryForLoggedInUser();
		if (entry == null) {
			System.err.println("ERROR");
			return;
		}
		if (!addObject(entry)) {
			System.err.println("ERROR");
			return;
		}
	}

	private void createRoom() {
		Room room = makeDefaultRoom();
		if (!addObject(room)) {
			System.err.println("ERROR");
			return;
		}
	}

	private void handleLoggedInUserMenu() {
		while (true) {
			showMenu(loggedInUsermenu);
			System.out.print("Enter an integer to chose an action: ");
			int chosen = requestInput(loggedInUsermenu.length);
			if (chosen == loggedInUsermenu.length) {
				System.out.println("Exit the user menu. Show main menu again.");
				return;
			} else {
				System.out.println("You chosed: " + loggedInUsermenu[chosen]);
				switch (chosen) {
				case 0:
					createEntryForUser();
					break;
				case 1:
					createRoom();
					break;
				case 2:
					if (!requestAllEntriesOfUserByUserID(loggedInUser)) {
						System.err
								.println("UNABLE TO DISPLAY ENTRIES BY USER ID");
					}
					break;
				case 3:
					if (!requestAllEntriesOfUserByCalendar(loggedInUser)) {
						System.err
								.println("UNABLE TO DISPLAY ENTRIES BY CALENDAR");
						return;
					}
					break;
				case 4:
					if (!requestAllObjectsOfType(new User())) {
						System.err.println("UNABLE TO DISPLAY ALL USER");
						return;
					}
					break;
				case 5:
					if (!requestAllObjectsOfType(new Room())) {
						System.err.println("UNABLE TO DISPLAY ALL ROOMS");
						return;
					}
					break;
				case 6:
					if (!manualUpdateRoomForEntry()) {
						System.err.println("UNABLE TO UPDATE ROOM FOR ENTRY");
					}
					break;
				case 7:
					if (!manualInvite()) {
						System.err.println("UNABLE TO SEND INVITES");
						return;
					}
					break;
				case 8:
					if (!manualInviteChange()) {

					}
					break;
				case 9:
					if (!manualRemoveEntry()) {
						System.err.println("UNABLE TO REMOVE ENTRY");
						return;
					}
					break;
				case 10:
					if (!manualLogin()) {
						System.err.println("UABLE TO LOGIN ERROR");
						return;
					}
					break;
				default:
					System.err
							.println("HARD ERROR in MAIN MENU ITEM SELECTION");
				}
			}
		}
	}

	private void handleMainMenu() {
		while (true) {
			showMenu(mainmenu);
			System.out.print("Enter an integer to chose an action: ");
			int chosen = requestInput(mainmenu.length);
			if (chosen == mainmenu.length) {
				System.out.println("Exit the main menu. Programm shuts down.");
				return;
			} else {
				System.out.println("You chosed: " + mainmenu[chosen]);
				switch (chosen) {
				case 0:
					createDefaultUsersWithCalendar();
					break;
				case 1:
					if (!manualLogin()) {
						System.err.println("UABLE TO LOGIN ERROR");
					} else {
						handleLoggedInUserMenu();
					}
					break;
				default:
					System.err
							.println("HARD ERROR in MAIN MENU ITEM SELECTION");
				}
			}
		}
	}

	private boolean manualInvite() {

		System.out
				.print("Enter the UserID you want to add(-1 to send invites): ");

		Scanner scan = new Scanner(System.in);
		int readIn = scan.nextInt();
		System.out.print("Enter the ID of the Entry you want to invite to: ");
		int entryId = scan.nextInt();
		scan.close();
		return sendInvitesTo(readIn, entryId);
	}

	private boolean manualInviteChange() {
		System.out.println("Invites of " + loggedInUser.getMailAddress());
		if (loggedInUser.getInviteIds().isEmpty()) {
			System.out.println("\tEmtpy");
			return true;
		}

		List<Invite> invites = requester.requestFollowingObjectsByOwnId(
				loggedInUser.getInviteIds(), new Invite());
		for (Invite in : invites) {
			if (in == null) {
				System.out
						.println("ERROR Request of invite was not successful");
			} else {
				System.out.println("\t" + in.getID() + " current state: "
						+ in.isAccepted());
			}
		}

		System.out.println("Set Invite Status");
		int goOn = -1;
		Scanner scan = new Scanner(System.in);
		do {

			System.out.print("Enter InviteID: ");
			int id = scan.nextInt();
			System.out.print("Enter accept(1) or decline(0): ");
			int state = scan.nextInt();
			Invite in = new Invite();
			in.setID(id);
			if (state == 1) {
				System.out.println("\t Invite " + id + " is accepted.");
				sendInviteStateFor(in, true);
			} else {
				System.out.println("\t Invite " + id + " is declined.");
				sendInviteStateFor(in, false);
			}
			System.out.print("To exit use -1, otherwise use another int: ");
			goOn = scan.nextInt();
		} while (goOn != -1);
		scan.close();
		return true;
	}

	private boolean manualLogin() {
		System.out.print("MailAddress for the Login: ");
		Scanner scan = new Scanner(System.in);
		String mail = scan.nextLine();
		scan.close();
		return login(mail, "123456");
	}

	private boolean manualRemoveEntry() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter EntryId to remove: ");
		int id = scan.nextInt();
		CalendarEntry entry = new CalendarEntry();
		entry.setID(id);
		boolean result = requester.removeObjectByOwnId(entry);
		scan.close();
		return result;
	}

	private boolean manualUpdateRoomForEntry() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a room id: ");
		int idRoom = scan.nextInt();
		Room r = new Room();
		r.setID(idRoom);
		r = requester.requestObjectByOwnId(r);
	
		System.out.print("Enter a entry id: ");
		int idEntry = scan.nextInt();
		CalendarEntry ce = new CalendarEntry();
		ce.setID(idEntry);
		ce = requester.requestObjectByOwnId(ce);
		scan.close();
		return updateRoomForCalendarEntry(r, ce);
	}

	private int requestInput(int maxValue) {
		do {
			Scanner scan = new Scanner(System.in);
			try {
				int chosen = scan.nextInt();
				if (chosen > maxValue && chosen < 0) {
					System.out
							.println("You did not enter one of the given integer");
					System.out.print("Repeat your input: ");
					continue;
				}
				scan.close();
				return chosen;
			} catch (InputMismatchException ex) {
				ex.printStackTrace();
			}
			System.out.println("You did not enter an integer.");
			System.out.print("Repeat your input: ");
		} while (true);
	}

	private boolean sendInviteStateFor(Invite invite, boolean state) {
		if (state) {
			requester.acceptInvite(invite);
		} else {
			requester.declineInvite(invite);
		}
		return state;
	}

	private void showMenu(String... menu) {
		System.out.println("Choose one of the following actions: ");
		for (int i = 0; i < menu.length; i++) {
			System.out.println(i + ".\t" + menu[i]);
		}
		System.out.println(menu.length + ".\tExit");
	}

}
