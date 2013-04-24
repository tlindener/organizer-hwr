package organizer.objects.types;

import organizer.objects.DataPusher;

public class Calendar extends DataPusher{

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
