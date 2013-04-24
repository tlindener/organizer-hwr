package organizer.objects.types;

import java.util.List;

import organizer.objects.AbstractOrganizerObject;

public class Kalendar extends AbstractOrganizerObject{
	private String bezeichnung = null;
	private List<Termin> termine = null;
	private Person besitzer = null;
	/**
	 * @return the bezeichnung
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}
	/**
	 * @param bezeichnung the bezeichnung to set
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	/**
	 * @return the termine
	 */
	public List<Termin> getTermine() {
		return termine;
	}
	/**
	 * @param termine the termine to set
	 */
	public void setTermine(List<Termin> termine) {
		this.termine = termine;
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
	
}
