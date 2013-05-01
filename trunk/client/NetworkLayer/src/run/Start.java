package run;

import java.util.Date;
import java.util.List;

import network.JsonJavaRequestHandler;
import network.RequestHandler;
import network.objects.ByProperty;

import organizer.objects.types.CalendarEntry;
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
		RequestHandler s = new JsonJavaRequestHandler();
		
//		Person p = new Person();
//		p.setID(1);
//		Person p_filled = s.requestObject(p);
		
//		Person p = s.requestObject(new Person());
//		Calendar k = s.requestObject(new Calendar());
//		CalendarEntry t = s.requestObject(new CalendarEntry());
////		... usw.
////		Wird ein ganz bestimmtes Objekt benötigt, werden zuerst Parameter gesetzt. Dadurch ist serverseitig erkennbar, welches Objeckt gesucht wird:
//		Calendar k_request = new Calendar();
//		k_request.setID(2);
//		k_request.setDescription("Steffen's Kalendar");
////		Rückgabe des Kalendars, der die ID 2 hat
//		Calendar k_filled = s.requestObject(k_request);
////		Wird ein Liste von Objekten benötigt, werden zuerst Parameter gesetzt. Dadurch ist serverseitig erkennbar, welches Objeckt gesucht wird:
//		CalendarEntry t_request = new CalendarEntry();
//		t_request.setDescription("WI");
//		System.out.println(s.requestObject(t_request));
////		Rückgabe aller Termine, die als Beschreibung "WI" haben
//		List<CalendarEntry> t_filled = s.requestAllObjects(t_request);
				
//		User u = new User();
//		u.setID(1);
//		u = s.requestObject(u);
		
		CalendarEntry c = new CalendarEntry();
		
		c = s.requestObjectByOwnId(c);
		c = s.requestObject(c, new ByProperty("roomid", 1));
		
		User u = new User();
		
		u = s.requestObject(u, new ByProperty("roomId",1));
		
		
//		List<CalendarEntry> cs = s.requestAllObjects(c);
//		for(CalendarEntry e: cs){
//			System.out.println(e);
//		}
//		CalendarEntry e = new CalendarEntry();
//		e.setStartDate(new Date());
//		
//		e.setStartDate(e.setTimeToDate(e.getStartDate(), "03:45"));
//		System.out.println(e.getStartDate());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Start();
	}

}
