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

	private String startZeit;
	private String endZeit;
	private String startDatum;
	private String endDatum;
	private String beschreibung;
	
	private Raum raum;
	
	/**
	 * @return the startZeit
	 */
	public String getStartZeit() {
		return startZeit;
	}

	/**
	 * @param startZeit the startZeit to set
	 */
	public void setStartZeit(String startZeit) {
		this.startZeit = startZeit;
	}

	/**
	 * @return the endZeit
	 */
	public String getEndZeit() {
		return endZeit;
	}

	/**
	 * @param endZeit the endZeit to set
	 */
	public void setEndZeit(String endZeit) {
		this.endZeit = endZeit;
	}

	/**
	 * @return the startDatum
	 */
	public String getStartDatum() {
		return startDatum;
	}

	/**
	 * @param startDatum the startDatum to set
	 */
	public void setStartDatum(String startDatum) {
		this.startDatum = startDatum;
	}

	/**
	 * @return the endDatum
	 */
	public String getEndDatum() {
		return endDatum;
	}

	/**
	 * @param endDatum the endDatum to set
	 */
	public void setEndDatum(String endDatum) {
		this.endDatum = endDatum;
	}

	/**
	 * @return the beschreibung
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	/**
	 * @param beschreibung the beschreibung to set
	 */
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	/**
	 * @return the raum
	 */
	public Raum getRaum() {
		return raum;
	}

	/**
	 * @param raum the raum to set
	 */
	public void setRaum(Raum raum) {
		this.raum = raum;
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
