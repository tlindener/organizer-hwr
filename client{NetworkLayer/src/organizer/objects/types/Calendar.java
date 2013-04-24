package organizer.objects.types;

import organizer.objects.DataPusher;

public class Calendar extends DataPusher{

	/**
	 * R�ckgabe des Klassennamens �ber Aufruf von {@link DataPusher#toString()}
	 * und hinzuf�gen von weiteren Elementen.
	 */
	@Override
	public String toString() {
		String represent = super.toString()+" - ID: " + getID();
		return represent;
	}
}
