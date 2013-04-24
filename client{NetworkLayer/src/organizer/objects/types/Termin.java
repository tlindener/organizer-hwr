package organizer.objects.types;

import organizer.objects.AbstractOrganizerObject;

/**
 * Beispielobjekt Termin
 * 
 * @author Steffen Baumann
 * @version 1.0 
 *
 */
public class Termin extends AbstractOrganizerObject{

	private String startZeit = null;
	private String endZeit = null;
	private String startDatum = null;
	private String endDatum = null;
	private String beschreibung = null;
	
	private Raum raum = null;
	
	private Person besitzer = null;
	
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
	 * @return the besitzer
	 */
	public Person getBesitzer() {
		return besitzer;
	}

	/**
	 * @param besitzer the besitzer to set
	 */
	public void setBesitzer(Person besitzer) {
		this.besitzer = besitzer;
	}

//	/**
//	 * Rückgabe des Klassennamens über Aufruf von {@link DataPusher#toString()}
//	 * und hinzufügen von weiteren Elementen.
//	 */
//	@Override
//	public String toString() {
////		StringBuilder builder = new StringBuilder("\t" + super.toString());
////		builder.append(" ID: " + getID());
////		builder.append(" Beschriebung: " + getBeschreibung());
////		builder.append(" Preson: " + getBesitzer());
////		builder.append(" Start: " + getStartDatum() + " - "+getStartZeit());
////		builder.append(" Ende: " + getEndDatum() + " - "+getEndZeit());
//		
//		return builder.toString();
//	}
}
