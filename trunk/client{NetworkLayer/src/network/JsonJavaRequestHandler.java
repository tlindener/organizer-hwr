/**
 * 
 */
package network;

import java.util.ArrayList;
import java.util.List;

import organizer.objects.DataPusher;

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

	/**
	 * Verbindungsdetails für Socket hinterlegen
	 */
	public JsonJavaRequestHandler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Abfrage eines Objekte, das die Vorgaben des Eingabeobjekts erfüllen.
	 */
	@Override
	public <T extends DataPusher> T requestObject(T obj) {
		System.out.println("Verarbeite ID: " + obj.getID());
		//Abfrage an Server stellen
		//Objekt verändern
		obj.setData("Test");
		return obj;
	}

	/**
	 * Abfrage mehrerer Objekte, die die Vorgaben des Eingabeobjekts erfüllen.
	 */
	@Override
	public <T extends DataPusher> List<T> requestAllObjects(T obj) {
		
		System.out.println("Verarbeite ID: " + obj.getID());
		//Abfrage an Server stellen
		// Erstellen einer Liste von Objekten mit Input vom Server
		List<T> t = null;
		try {
			t = new ArrayList<>();
			for(int i = 0; i < 10; i++){
				@SuppressWarnings("unchecked")
				//Cast möglich - Ursache der Warnung: Wildcard in getClass<?>
				T object2 = (T) obj.getClass().newInstance();
				object2.setID(i);
				
				t.add(object2);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}

}
