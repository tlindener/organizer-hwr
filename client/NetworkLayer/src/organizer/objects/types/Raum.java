package organizer.objects.types;

import organizer.objects.AbstractOrganizerObject;

public class Raum extends AbstractOrganizerObject {
	private int kapazitaet = -1;
	private String ort = null;
	/**
	 * @return the kapazitaet
	 */
	public int getKapazitaet() {
		return kapazitaet;
	}
	/**
	 * @param kapazitaet the kapazitaet to set
	 */
	public void setKapazitaet(int kapazitaet) {
		this.kapazitaet = kapazitaet;
	}
	/**
	 * @return the ort
	 */
	public String getOrt() {
		return ort;
	}
	/**
	 * @param ort the ort to set
	 */
	public void setOrt(String ort) {
		this.ort = ort;
	}
}
