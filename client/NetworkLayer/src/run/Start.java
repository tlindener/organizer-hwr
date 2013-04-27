package run;

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
		
//		try {
//			Date d = new Date();
//			Long t = new Long("1366968215630");
//			d.setTime(t);
//			
//			Calendar ca = new GregorianCalendar();
//			ca.setTimeInMillis(t);
//			
//			System.out.println(ca.get(Calendar.HOUR_OF_DAY));
////			XMLGregorianCalendar xmlGregCa = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCa);
//			XMLGregorianCalendar xmlGregCa = DatatypeFactory.newInstance().newXMLGregorianCalendar("1366968215630+0200");
////			xmlGregCa.getXMLSchemaType();
//			System.out.println(xmlGregCa.getYear());
//		} catch (DatatypeConfigurationException e) {
//			e.printStackTrace();
//		}
		
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
		c = s.requestObject(c);
		System.out.println(c);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Start();
	}

}
