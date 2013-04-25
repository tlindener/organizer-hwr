package run;

import java.util.ArrayList;
import java.util.List;

import network.JsonJavaRequestHandler;
import network.RequestHandler;

import organizer.objects.types.Kalendar;
import organizer.objects.types.Person;
import organizer.objects.types.Termin;

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
		Person p = s.requestObject(new Person());
		Kalendar k = s.requestObject(new Kalendar());
		Termin t = s.requestObject(new Termin());
//		... usw.
//		Wird ein ganz bestimmtes Objekt benötigt, werden zuerst Parameter gesetzt. Dadurch ist serverseitig erkennbar, welches Objeckt gesucht wird:
		Kalendar k_request = new Kalendar();
		k_request.setID(2);
//		Rückgabe des Kalendars, der die ID 2 hat
		Kalendar k_filled = s.requestObject(k_request);
		
//		Wird eine von Objekten benötigt, werden zuerst Parameter gesetzt. Dadurch ist serverseitig erkennbar, welches Objeckt gesucht wird:
		Termin t_request = new Termin();
		t_request.setBeschreibung("WI");
//		Rückgabe aller Termine, die als Beschreibung "WI" haben
		List<Termin> t_filled = s.requestAllObjects(t_request);
		
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
