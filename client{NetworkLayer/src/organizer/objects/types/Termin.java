package organizer.objects.types;

import organizer.objects.DataPusher;

/**
 * Beispielobjekt Termin
 * 
 * @author Steffen Baumann
 * @version 1.0 
 *
 */
public class Termin extends DataPusher{

	@Override
	public void setData(String... data) {
		for(String d: data){
			System.out.println("Termin: " + d);
		}
	}

	@Override
	public String[] getData() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Rückgabe des Klassennamens über Aufruf von {@link DataPusher#toString()}
	 * und hinzufügen von weiteren Elementen.
	 */
	@Override
	public String toString() {
		String represent = super.toString()+" - ID: " + getID();
		return represent;
	}
}
