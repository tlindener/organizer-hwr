package run;

import java.util.List;

import network.JsonJavaRequestHandler;
import network.RequestHandler;

import organizer.objects.types.Calendar;
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
	
		Termin t = new Termin();
		t.setID(3);
		
		Calendar c = s.requestObject(new Calendar());
		t = s.requestObject(t);
		
		List<Termin> ts = s.requestAllObjects(t);
				
		for(Termin t1: ts){
			System.out.println(t1);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Start();
	}

}
