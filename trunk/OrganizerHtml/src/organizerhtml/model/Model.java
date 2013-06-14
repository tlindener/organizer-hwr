package organizerhtml.model;

import java.util.Date;
import java.util.List;

public class Model {

	private Date date;
	private String beschreibung = "Beschreibung";
	private String raum = "Raum";
	private List<String> pers;

	/**
	 * @return the pers
	 */
	public List<String> getPers() {
		return pers;
	}

	/**
	 * @param pers
	 *            the pers to set
	 */
	public void setPers(List<String> pers) {
		this.pers = pers;
	}

	/**
	 * @return the raum
	 */
	public String getRaum() {
		return raum;
	}

	/**
	 * @param raum
	 *            the raum to set
	 */
	public void setRaum(String raum) {
		this.raum = raum;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the beschreibung
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	/**
	 * @param beschreibung
	 *            the beschreibung to set
	 */
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

}
