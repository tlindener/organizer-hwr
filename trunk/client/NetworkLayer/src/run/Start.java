package run;

import java.util.Date;

import network.JsonJavaRequestHandler;
import network.RequestHandler;

import organizer.objects.types.CalendarEntry;

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
				
		CalendarEntry c = new CalendarEntry();
		c.setStartDate(new Date());
		c.setStartDate(new Date());
		s.requestObject(c);
		
////		Test zur Ausgabe von Objekten über die Konsole - Auslesen per Reflections
//		Person p1 = new Person();
//		p1.setID(1);
//		Kalendar k1 = new Kalendar();
//		k1.setID(1);
//		List<Kalendar> listK = new ArrayList<Kalendar>();
//		Termin t1 = new Termin();
//		t1.setID(1);
//		List<Termin> listT = new ArrayList<Termin>();
//		
//		listK.add(k1);
//		listT.add(t1);
//		
//		t1.setBesitzer(p1);
//		p1.setKalendar(listK);
//		k1.setTermine(listT);
//		
//		System.out.println(p1);
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Start();
	}

}
