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
public class Gruppe extends AbstractOrganizerObject {
	private String bezeichnung = null;
	private List<Person> mitglieder = null;
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
	 * @return the mitglieder
	 */
	public List<Person> getMitglieder() {
		return mitglieder;
	}
	/**
	 * @param mitglieder the mitglieder to set
	 */
	public void setMitglieder(List<Person> mitglieder) {
		this.mitglieder = mitglieder;
	}
}
