/**
 * 
 */
package network;

import java.util.ArrayList;
import java.util.List;

import organizer.objects.DataPusher;

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

	/**
	 * Verbindungsdetails f�r Socket hinterlegen
	 */
	public JsonJavaRequestHandler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Abfrage eines Objekte, das die Vorgaben des Eingabeobjekts erf�llen.
	 */
	@Override
	public <T extends DataPusher> T requestObject(T obj) {
		System.out.println("Verarbeite ID: " + obj.getID());
		//Abfrage an Server stellen
		//Objekt ver�ndern
		obj.setData("Test");
		return obj;
	}

	/**
	 * Abfrage mehrerer Objekte, die die Vorgaben des Eingabeobjekts erf�llen.
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
				//Cast m�glich - Ursache der Warnung: Wildcard in getClass<?>
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
