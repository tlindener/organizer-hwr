/**
 * 
 */
package organizer.objects.types;

import java.util.List;

import organizer.objects.AbstractOrganizerObject;

/**
 * @author Steffen Baumann
 *
 */
public class Person extends AbstractOrganizerObject {
	private String nachname = null;
	private String vorname = null;
	private String mail = null;
	private String telefon = null;
	private List<Kalendar> kalendar = null;
	/**
	 * @return the nachname
	 */
	public String getNachname() {
		return nachname;
	}
	/**
	 * @param nachname the nachname to set
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	/**
	 * @return the vorname
	 */
	public String getVorname() {
		return vorname;
	}
	/**
	 * @param vorname the vorname to set
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * @return the telefon
	 */
	public String getTelefon() {
		return telefon;
	}
	/**
	 * @param telefon the telefon to set
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	/**
	 * @return the kalendar
	 */
	public List<Kalendar> getKalendar() {
		return kalendar;
	}
	/**
	 * @param kalendar the kalendar to set
	 */
	public void setKalendar(List<Kalendar> kalendar) {
		this.kalendar = kalendar;
	}
}
