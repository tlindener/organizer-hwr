package run;

import java.util.Date;
import java.util.List;

import network.JsonJavaRequestHandler;
import network.RequestHandler;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Group;
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
		
		
//		Schnittstelle für Jenny um Objekte zu erfragen
		RequestHandler requester = new JsonJavaRequestHandler("localhost", 48585);
		
		List<Calendar> ces = requester.requestAllObjects(new Calendar());
		for(Calendar ce: ces){
			System.out.println(ce);
		}
		
		System.out.println("CalendarEntries");
		CalendarEntry requestEntry = new CalendarEntry();
		requestEntry.setOwnerId(1);
		requestEntry.setRequestProperty(CalendarEntry.OWNER_ID, ""+requestEntry.getOwnerId());
		List<CalendarEntry> es = requester.requestAllObjectsByProperty(requestEntry);
		for(CalendarEntry e:es){
			System.out.println(e);
		}
		
		//Gruppe Abfragen mit
		System.out.println("Groups");
		Group requestGroup = new Group();
		requestGroup.setRequestProperty(Group.USER_ID, "1");
		List<Group> gs = requester.requestAllObjectsByProperty(requestGroup);
		for(Group g:gs){
			System.out.println(g);
		}

		//Add Objects
		User user = new User();
		user.setGivenname("Steffen");
		user.setSurname("Baumann");
		user.setMailAddress("test@gmx.de");
		user.setPhoneNumber("030/1234567");
		
		Calendar calendar = new Calendar();
		calendar.setDescription("privater Test");
		calendar.setName("Steffens Kalender");
		calendar.setOwnerId(1);
		
		Room room = new Room();
		room.setDescription("Raum1");
		room.setLocation("Berlin");
		room.setSeats(17);
				
		CalendarEntry calendarEntry = new CalendarEntry();
		calendarEntry.setCalendarId(1);
		calendarEntry.setStartDate(new Date());
		calendarEntry.setDescription("Test");
		calendarEntry.setTitle("TestTermin");
		calendarEntry.setRoomId(1);
		calendarEntry.setOwnerId(1);
		calendarEntry.setEndDate(new Date());
		calendarEntry.setDuration(600.0);
		
		Group group = new Group();
		group.setDescription("Testgruppe");
		
		System.out.println("Add");
		try{
			requester.addObject(user);
		}catch(UnsupportedOperationException ex){
			System.err.println("GEPLANTE EXCEPTION");
			ex.printStackTrace();
		}
		
		System.out.println("Register user");
		user = requester.registerNewUser(user,  "123456789");
		System.out.println(user);
		
		System.out.println("Add");
		group = requester.addObject(group);
		System.out.println(group);
		room = requester.addObject(room);
		System.out.println(room);
		calendar = requester.addObject(calendar);
		System.out.println(calendar);
		calendarEntry = requester.addObject(calendarEntry);
		System.out.println(calendarEntry);
		
		
		
		
		
//		Calendar c = new Calendar();
//		c.setID(1);
//		c = requester.requestObjectByOwnId(c);
//		System.out.println(c);
//		
//		CalendarEntry ce = new CalendarEntry();
//		ce.setID(1);
//		ce = requester.requestObjectByOwnId(ce);
//		System.out.println(c);
//		
//		
//		CalendarEntry ce_add = new CalendarEntry();
//		ce_add.setCalendarId(1);
//		ce_add.setDescription("Test");
//		ce_add.setDuration(30.0);
//		ce_add.setOwnerId(1);
//		ce_add.setRoomId(1);
//		ce_add.setTitle("Testtitel");
//		ce_add.setStartDate(new Date());
//		ce_add.setEndDate(new Date());
//		
//		requester.addElement(ce_add);
		
//		CalendarEntry request_ce = new CalendarEntry();
//		request_ce.setID(1);
//
//		CalendarEntry ce = requester.requestObjectByOwnId(request_ce);
//		System.out.println(ce);
//		
//		List<Room> rooms = requester.requestAllObjects(new Room());
//		System.out.println(rooms);
//		
//		List<User> user = requester.requestObjects(new User(), new ByProperty("id",2));
//		System.out.println(user);
//		
//		List<CalendarEntry> entries = requester.requestObjects(new CalendarEntry(), new ByProperty("title","Testtermin"));
//		System.out.println(entries);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Start();
	}

}
