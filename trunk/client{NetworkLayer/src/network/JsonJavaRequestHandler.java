/**
 * 
 */
package network;

import java.util.List;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Kalendar;
import organizer.objects.types.Termin;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Verbindungsschnittstelle um Daten abzufragen. Eingabeobjekte werden verändert
 * und zurückgeliefert. Die Verwendung der generischen Vaterklasse ermöglicht,
 * dass clientseitig keine Cast-Befehle nötig sind. Die Schnittstelle benötigt
 * Informationen zu den JSON Objects und der Java Objects um ein Mapping
 * vornehmen zu können
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public class JsonJavaRequestHandler extends RequestHandler {

	private GsonBuilder builder = null;
	
	/**
	 * Verbindungsdetails für Socket hinterlegen
	 */
	public JsonJavaRequestHandler() {
		init();
	}

	private void init() {
		builder = new GsonBuilder();
		//initialze Socket
	}

	/**
	 * Abfrage eines Objekte, das die Vorgaben des Eingabeobjekts erfüllen.
	 */
	@SuppressWarnings("unchecked")
	//unberechtet, da erst immer instance of Abfrage gestartet
	// Es gilt T = Cast und somit auch Cast = T
	// Im Grunde String t = (String) fillString(json, string);
	@Override
	public <T extends AbstractOrganizerObject> T requestObject(T obj) {
		
			
//		
		JsonObject json1 = new JsonObject();
		json1.addProperty("id", "3");
		json1.addProperty("beschreibung", "Aufstehen");
		obj = (T) builder.create().fromJson(json1, obj.getClass());
		
//		JsonObject json = sendRequestToServer();
//		if(obj instanceof Kalendar){
//			return (T) fillCalendar(json, (Kalendar) obj);
//		}else if(obj instanceof Termin){
//			return (T) fillTermin(json, (Termin) obj);
//		}
				
//		
//		System.out.println("Verarbeite ID: " + obj.getID());
//		Field[] fields = obj.getFields();
//		for(Field f: fields){
//			try {
//				f.setAccessible(true);
//				f.set(obj, "H");
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		}
		return obj;
	}
	
	private Termin fillTermin(JsonObject json, Termin obj) {
		
		return obj;
	}

	public Kalendar fillCalendar(JsonObject json, Kalendar obj){
		
		return obj;
	}
	
	private JsonObject sendRequestToServer() {
		return null;
		
	}

	/**
	 * Abfrage mehrerer Objekte, die die Vorgaben des Eingabeobjekts erfüllen.
	 */
	@Override
	public <T extends AbstractOrganizerObject> List<T> requestAllObjects(T obj) {
		
//		System.out.println("Verarbeite ID: " + obj.getID());
//		//Abfrage an Server stellen
//		// Erstellen einer Liste von Objekten mit Input vom Server
		List<T> t = null;
//		try {
//			t = new ArrayList<>();
//			for(int i = 0; i < 10; i++){
//				@SuppressWarnings("unchecked")
//				//Cast möglich - Ursache der Warnung: Wildcard in getClass<?>
//				T object2 = (T) obj.getClass().newInstance();
//				object2.setID(i);
//				
//				t.add(object2);
//			}
//		} catch (InstantiationException | IllegalAccessException e) {
//			e.printStackTrace();
//		}
		return t;
	}

}
