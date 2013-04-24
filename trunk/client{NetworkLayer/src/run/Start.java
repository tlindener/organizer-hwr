package run;

import java.util.ArrayList;
import java.util.List;

import network.JsonJavaRequestHandler;
import network.RequestHandler;

import organizer.objects.AbstractOrganizerObject;
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
	
		RequestHandler s = new JsonJavaRequestHandler();
	

		
//		Kalendar c = s.requestObject(new Kalendar());
//		System.out.println(c);
		
		Termin t = new Termin();
		t.setID(3);
		Person p = new Person();
		p.setID(456);
		p.setVorname("Steffen");
		p.setMail("steffen@organizer.de");
		List<Kalendar> cs = new ArrayList<>();
		
		Kalendar c1 = new Kalendar();
		c1.setID(1);
		c1.setBesitzer(p);
		List<Termin> ts = new ArrayList<Termin>();
		ts.add(t);
		c1.setTermine(ts);
		Kalendar c2 = new Kalendar();
		c1.setID(2);
//		Kalendar c3 = new Kalendar();
//		c1.setID(3);
		cs.add(c1);
		cs.add(c2);
//		cs.add(c3);
		p.setKalendar(cs);
		t.setBesitzer(p);
		System.out.println(t);
		System.out.println(p);
//		t = s.requestObject(t);
//		System.out.println(t);
		
//		List<Termin> ts = s.requestAllObjects(t);
//				
//		for(Termin t1: ts){
//			System.out.println(t1);
//		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Start();
	}

}
