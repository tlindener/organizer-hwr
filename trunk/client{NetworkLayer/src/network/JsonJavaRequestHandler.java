/**
 * 
 */
package network;

import java.util.List;

import organizer.objects.AbstractOrganizerObject;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Verbindungsschnittstelle um Daten abzufragen. Eingabeobjekte werden ver�ndert
 * und zur�ckgeliefert. Die Verwendung der generischen Vaterklasse erm�glicht,
 * dass clientseitig keine Cast-Befehle n�tig sind. Die Schnittstelle ben�tigt
 * Informationen zu den JSON Objects und der Java Objects um ein Mapping
 * vornehmen zu k�nnen
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public class JsonJavaRequestHandler extends RequestHandler {

	private GsonBuilder builder = null;
	
	/**
	 * Verbindungsdetails f�r Socket hinterlegen
	 */
	public JsonJavaRequestHandler() {
		init();
	}

	private void init() {
		builder = new GsonBuilder();
		
	}

	/**
	 * Abfrage eines Objekte, das die Vorgaben des Eingabeobjekts erf�llen.
	 */
	@Override
	public <T extends AbstractOrganizerObject> T requestObject(T obj) {
		
			
//		
		JsonObject json = sendRequestToServer();
		
////	used to emulate server stream json object	
//		json.addProperty("id", "3");
//		json.addProperty("beschreibung", "Aufstehen");
		
		return fillObjectFromJson(json, obj);
	}
	
	@SuppressWarnings("unchecked")
	//unrealistic because of created object must be a type of T
	private <T extends AbstractOrganizerObject> T fillObjectFromJson(JsonObject json, T obj) {
		return (T) builder.create().fromJson(json, obj.getClass());
	}
//
//	public Kalendar fillCalendar(JsonObject json, Kalendar obj){
//		
//		return obj;
//	}
	
	private JsonObject sendRequestToServer() {
		return null;
		
	}

	/**
	 * Abfrage mehrerer Objekte, die die Vorgaben des Eingabeobjekts erf�llen.
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
//				//Cast m�glich - Ursache der Warnung: Wildcard in getClass<?>
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
