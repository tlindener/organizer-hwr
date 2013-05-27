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
		
		User u = requester.registerNewUser(user, "123456");
		User u2 = requester.registerNewUser(user2, "123456");
		User u3 = requester.registerNewUser(user3, "123456");
		
		
		System.out.println("Log in " +u.getID());
		User loggedInUser = requester.login(u.getMailAddress(), "123456");
		
		Calendar ca = new Calendar();
		ca.setOwnerId(loggedInUser.getID());
		ca.setDescription("Infos%20von%20undfuer" + loggedInUser.getGivenName());
		ca.setName(loggedInUser.getGivenName()+"sCalendar");

		Calendar c = requester.addObject(ca);
		
		System.out.println("Log in " +u2.getID());
		loggedInUser = requester.login(u2.getMailAddress(), "123456");
		
		Calendar ca2 = new Calendar();
		ca2.setOwnerId(loggedInUser.getID());
		ca2.setDescription("Infos%20von%20undfuer" + loggedInUser.getGivenName());
		ca2.setName(loggedInUser.getGivenName()+"sCalendar");
		
		Calendar c2 = requester.addObject(ca2);
		
		System.out.println("Log in " +u3.getID());
		loggedInUser = requester.login(u3.getMailAddress(), "123456");
		
		Calendar ca3 = new Calendar();
		ca3.setOwnerId(loggedInUser.getID());
		ca3.setDescription("Infos%20von%20undfuer" + loggedInUser.getGivenName());
		ca3.setName(loggedInUser.getGivenName()+"sCalendar");

		Calendar c3 = requester.addObject(ca3);
		
		System.out.println("Log in " +u.getID());
		loggedInUser = requester.login(u.getMailAddress(), "123456");	
		
		CalendarEntry ce = new CalendarEntry();
		ce.setStartDate(new Date());
		ce.setDescription(loggedInUser.getGivenName() +"sTermin");
		ce.setTitle("Testterminvon%20" + loggedInUser.getGivenName());
		ce.setRoomId(1);
		ce.setOwnerId(loggedInUser.getID());
		ce.setCalendarId(c.getID());
		ce.setEndDate(new Date());
		
		CalendarEntry ce1 = requester.addObject(ce);
		
		Invite i = new Invite();
		i.setCalendarEntryId(ce1.getID());
		i.setOwnerId(u2.getID());
		
		i = requester.addObject(i);
		
		Invite i2 = new Invite();
		i2.setCalendarEntryId(ce1.getID());
		i2.setOwnerId(u3.getID());
		
		i2 = requester.addObject(i2);
		
		System.out.println("Log in user 2");
		loggedInUser = requester.login(u2.getMailAddress(), "123456");
		
		System.out.println("Accept Invites");
		for(int invite: loggedInUser.getInviteIds()){
			requester.acceptInvite(invite);
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
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Start();
	}

}
