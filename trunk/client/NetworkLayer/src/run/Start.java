package run;

import java.util.Date;
import java.util.List;

import network.JsonJavaRequestHandler;
import network.RequestHandler;
import network.objects.ByProperty;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
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
		
		Calendar c = new Calendar();
		c.setID(1);
		c = requester.requestObjectByOwnId(c);
		System.out.println(c);
		
		CalendarEntry ce = new CalendarEntry();
		ce.setID(1);
		ce = requester.requestObjectByOwnId(ce);
		System.out.println(c);
		
		
		CalendarEntry ce_add = new CalendarEntry();
		ce_add.setCalendarId(1);
		ce_add.setDescription("Test");
		ce_add.setDuration(30.0);
		ce_add.setOwnerId(1);
		ce_add.setRoomId(1);
		ce_add.setTitle("Testtitel");
		ce_add.setStartDate(new Date());
		ce_add.setEndDate(new Date());
		
		requester.addElement(ce_add);
		
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
