package run;

import java.util.Date;
import java.util.List;

import network.JsonJavaRequestHandler;
import network.RequestHandler;
import network.objects.Utils;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Group;
import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

/**
 * Erste Testklasse
 * 
 * @author Steffen Baumann
 * @version 1.0 
 *
 */
public class Start {
	
	public Start() {
//		
//		
////		Schnittstelle für Jenny um Objekte zu erfragen
		RequestHandler requester = new JsonJavaRequestHandler("localhost", 48585);
		
		User user = new User();
		user.setMailAddress("steffen.baumann@localhost.de");
		user.setGivenname("Steffen");
		user.setSurname("Baumann");
		user.setPhoneNumber("666666666");
		
		User user2 = new User();
		user2.setMailAddress("tobias.lindener@localhost.de");
		user2.setGivenname("Tobias");
		user2.setSurname("Lindener");
		user2.setPhoneNumber("555555555");
		
		User user3 = new User();
		user3.setMailAddress("svenja.möhring@localhost.de");
		user3.setGivenname("Svenja");
		user3.setSurname("Möhring");
		user3.setPhoneNumber("777777777");
		
		System.out.println("AddUser");
		User u = requester.registerNewUser(user, "123456");
		System.out.println("UserID: " +u.getID());
		User u2 = requester.registerNewUser(user2, "123456");
		System.out.println("UserID: " +u2.getID());
		User u3 = requester.registerNewUser(user3, "123456");
		System.out.println("UserID: " +u3.getID());
		
		System.out.println("Log in " +u.getMailAddress());
		User loggedInUser = requester.login(u.getMailAddress(), "123456");
		
		Room room = new Room();
		room.setDescription("TestRaum1");
		room.setLocation("HierUndDa");
		room.setSeats(1478);
		
		System.out.println("AddRoom");
		Room r = requester.addObject(room);
		System.out.println("RoomID: " +r.getID());
		
		Calendar ca = new Calendar();
		ca.setOwnerId(loggedInUser.getID());
		ca.setDescription("Infos%20von%20undfuer" + loggedInUser.getGivenName());
		ca.setName(loggedInUser.getGivenName()+"sCalendar");

		System.out.println("AddCalendar");
		Calendar c = requester.addObject(ca);
		System.out.println("CalendarID: " +c.getID());
		
		System.out.println("Log in " +u2.getMailAddress());
		loggedInUser = requester.login(u2.getMailAddress(), "123456");
		
		Calendar ca2 = new Calendar();
		ca2.setOwnerId(loggedInUser.getID());
		ca2.setDescription("Infos%20von%20undfuer" + loggedInUser.getGivenName());
		ca2.setName(loggedInUser.getGivenName()+"sCalendar");
		
		Calendar c2 = requester.addObject(ca2);
		System.out.println("CalendarID: " +c2.getID());
		
		System.out.println("Log in " +u3.getMailAddress());
		loggedInUser = requester.login(u3.getMailAddress(), "123456");
		
		Calendar ca3 = new Calendar();
		ca3.setOwnerId(loggedInUser.getID());
		ca3.setDescription("Infos%20von%20undfuer" + loggedInUser.getGivenName());
		ca3.setName(loggedInUser.getGivenName()+"sCalendar");

		Calendar c3 = requester.addObject(ca3);
		System.out.println("CalendarID: " +c3.getID());
		
		System.out.println("Log in " +u.getMailAddress());
		loggedInUser = requester.login(u.getMailAddress(), "123456");	
		
		System.out.println("Request calendar of " + loggedInUser.getGivenName());
		List<Integer> caIDs = loggedInUser.getCalendarIds();
		int caID = -1;
		if(caIDs.isEmpty()){
			System.err.println("Error: No CalendarID was found in User-Object of " +loggedInUser.getGivenName());
			return;
		}else{
			caID = caIDs.get(0);
			System.out.println("Picked Calendar ID " + caID);
		}
		
		System.out.println("Request all rooms");
		List<Room> rooms = requester.requestAllObjects(new Room());
		if(rooms.isEmpty()){
			System.err.println("Error: No Room was found");
			return;
		}else{
			room = rooms.get(0);
			System.out.println("Rooms were found: Select room with ID " + room.getID());
		}
		
		CalendarEntry ce = new CalendarEntry();
		ce.setStartDate(new Date());
		ce.setDescription(loggedInUser.getGivenName() +"sTermin");
		ce.setTitle("Testterminvon%20" + loggedInUser.getGivenName());
		ce.setRoomId(room.getID());
		ce.setOwnerId(loggedInUser.getID());
		ce.setCalendarId(caID);
		ce.setEndDate(new Date());
		
		System.out.println("AddEntry");
		CalendarEntry ce1 = requester.addObject(ce);
		System.out.println("EntryId: " +ce1.getID());
		
		System.out.println("AddInvite for User 2");
		Invite i = new Invite();
		i.setCalendarEntryId(ce1.getID());
		i.setOwnerId(2);
		
		i = requester.addObject(i);
		System.out.println("InviteId: " +i.getID());
		
		System.out.println("AddInvite for User 3");
		Invite i2 = new Invite();
		i2.setCalendarEntryId(ce1.getID());
		i2.setOwnerId(3);
		
		i2 = requester.addObject(i2);
		System.out.println("InviteId: " +i2.getID());
		
		System.out.println("Log in " +u2.getMailAddress());
		loggedInUser = requester.login(u2.getMailAddress(), "123456");
		
		System.out.println("Accept Invites");
		for(int invite: loggedInUser.getInviteIds()){
			Invite tmp = new Invite();
			tmp.setID(invite);
			Invite in = requester.requestObjectByOwnId(tmp);
			if(in.isAccepted() != 1){
				int result = requester.acceptInvite(invite);
				System.out.println("Invite "+invite + "--> neue EntryID: " + result);
			}else{
				System.out.println("Already Confirmed / Declined");
			}
		}
		
		System.out.println("Remove Entries with RoomID=1 from current User");
		CalendarEntry room1Entry = new CalendarEntry();
		room1Entry.setRequestProperty(CalendarEntry.ROOM_ID, ""+1);
		List<CalendarEntry> entries = requester.requestAllObjectsByProperty(room1Entry);
		boolean result = false;
		for(CalendarEntry e: entries){
			System.out.println("TerminID "+e.getID());
			if(e.getOwnerId() == loggedInUser.getID()){
				result = requester.removeObjectByOwnId(e);
			}
		}
		System.out.println("Result remove CalendarEntry: " +result);
		
		System.out.println("Request Calendar of " + loggedInUser.getMailAddress());
		Calendar ca4 = new Calendar();
		if(ca4.getCalendarEntries().isEmpty()){
			System.out.println("No Elements in List");
		}
		else{
			ca4.setID(loggedInUser.getCalendarIds().get(0));
			ca4 = requester.requestObjectByOwnId(ca4);
			System.out.println("ID: "+ ca4.getID() + " | Name: "+ca4.getName());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Start();
	}

}
