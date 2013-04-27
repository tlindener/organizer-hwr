/**
 * 
 */
package network;

import java.util.List;

import organizer.objects.AbstractOrganizerObject;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
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

	// private FieldNamingStrategy cStrategy = new FieldNamingStrategy() {
	//
	// @Override
	// public String translateName(Field field) {
	// String firstChar = field.getName().substring(0, 1);
	// String replaceChar = firstChar.toLowerCase();
	// System.out.println(firstChar + " : " + replaceChar);
	// return field.getName().replaceFirst(firstChar, replaceChar);
	// }
	// };

	/**
	 * Verbindungsdetails für Socket hinterlegen
	 */
	public JsonJavaRequestHandler() {
		init();
	}

	private void init() {
		builder = new GsonBuilder();
		builder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
	}

	/**
	 * Abfrage eines Objekte, das die Vorgaben des Eingabeobjekts erfüllen.
	 */
	@Override
	public <T extends AbstractOrganizerObject> T requestObject(T obj) {

		//
//		JsonObject json = sendRequestToServer(obj);
		 JsonObject json = new JsonObject();
//		  used to emulate server stream json object
		json.addProperty("CalendarId", "1");
		json.addProperty("Description", "Description");
		json.addProperty("Duration", 180);
		json.addProperty("EndDate", "\\/Date(1366979015630+0200)\\/");
		json.addProperty("Id", 0);
		json.addProperty("OwnerId", 1);
		json.addProperty("RoomId", 0);
		json.addProperty("StartDate", "\\/Date(668767800000+0200)\\/");
		json.addProperty("Title", "Title");
		json.addProperty("Beschreibung", "Aufstehen");

		return fillObjectFromJson(json, obj);
	}

	@SuppressWarnings("unchecked")
	// unrealistic because of created object must be a type of T
	private <T extends AbstractOrganizerObject> T fillObjectFromJson(
			JsonObject json, T obj) {
		
		if(json.get("StartDate")!=null){
			json = parseDateTime(json, "StartDate");
		}
		if(json.get("EndDate")!=null){
			json = parseDateTime(json, "EndDate");
		}
		return (T) builder.create().fromJson(json, obj.getClass());
	}
	
	private JsonObject parseDateTime(JsonObject json, String fieldName){
		String originalValue = json.get(fieldName).toString();
		String longValue = originalValue.substring(originalValue.indexOf("(")+1, originalValue.indexOf("+"));
		long l = -1;
		try{
			l = Long.parseLong(longValue);
			
		}catch(NumberFormatException ex){
			ex.printStackTrace();
		}
		json.remove(fieldName);
		JsonElement elements = builder.create().toJsonTree(l);
		json.add(fieldName, elements);
		return json;
	}
	

	@SuppressWarnings("unused")
	private <T extends AbstractOrganizerObject> JsonObject sendRequestToServer(
			T obj) {
		String json = builder.create().toJson(obj);
		System.out.println("AUSGABE: " + json);
		return new JsonObject();
	}

	/**
	 * Abfrage mehrerer Objekte, die die Vorgaben des Eingabeobjekts erfüllen.
	 */
	@Override
	public <T extends AbstractOrganizerObject> List<T> requestAllObjects(T obj) {

		// System.out.println("Verarbeite ID: " + obj.getID());
		// //Abfrage an Server stellen
		// // Erstellen einer Liste von Objekten mit Input vom Server
		List<T> t = null;
		// try {
		// t = new ArrayList<>();
		// for(int i = 0; i < 10; i++){
		// @SuppressWarnings("unchecked")
		// //Cast möglich - Ursache der Warnung: Wildcard in getClass<?>
		// T object2 = (T) obj.getClass().newInstance();
		// object2.setID(i);
		//
		// t.add(object2);
		// }
		// } catch (InstantiationException | IllegalAccessException e) {
		// e.printStackTrace();
		// }
		return t;
	}

}
