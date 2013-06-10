package organizerhtml.beans;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import network.JsonJavaRequestHandler;
import network.RequestHandler;

import org.primefaces.event.SelectEvent;
import organizerhtml.model.database.objects.CalendarEntry;

public class CalenderBean {
	private Date date;
	private String username;
	private String password;
	private String beschreibung = "Beschreibung";
	private String raum = "Raum";
	private String[] personen = new String[] {};
	private List<String> pers;
	private String owner;

	// /*
	// * sonstige Variablen
	// */
	// private int port = 48585;
	// private String adresse= "localhost";
	// private RequestHandler myRequester;
	// private Object[][] terminDauer;
	// private String aktTermin;
	//
	// private organizer.objects.types.Calendar steffensCal;
	// private organizer.objects.types.User aktUser;
	// private organizer.objects.types.Calendar aktUserCa;
	// private CalendarEntry aktEntry;
	//
	// /**
	// * Die Methode "login" übermittelt die Korrektheit der Benutzerdaten
	// *
	// * @return
	// */
	// public String login() {
	// setupNetworkConnection();
	// if (verifyUser()) {
	//
	// return "home";
	// } else {
	// FacesContext context = FacesContext.getCurrentInstance();
	// context.addMessage("username", new FacesMessage(
	// "Invalid UserName and Password"));
	// return "login";
	// }
	// }
	//
	// private void setupNetworkConnection() {
	// myRequester = new JsonJavaRequestHandler(adresse, port);
	// }
	//
	// /**
	// * Diese Methode überprüft über die NetworkLayer-Schicht die Korrektheit
	// der
	// * Zugangsdaten. Der Zugriff wird über ein Boolean-Wert übermittelt.
	// *
	// * @return access granted ?
	// */
	// private boolean verifyUser() {
	// organizer.objects.types.User tmpu = myRequester.login(username,
	// password);
	//
	// if (tmpu != null) {
	// aktUser = tmpu;
	// List<Integer> calendarIDs = aktUser.getCalendarIds();
	// if (!calendarIDs.isEmpty()) {
	// aktUserCa.setID(calendarIDs.get(0));
	// }
	// aktUserCa.setID(1);
	//
	// organizer.objects.types.Calendar tmpCal = myRequester
	// .requestObjectByOwnId(aktUserCa);
	// // null Abfrage
	// if (tmpCal != null) {
	// aktUserCa = tmpCal;
	// }
	// }
	// return false;
	// }

	public void handleDateSelect(SelectEvent event) {
		Date date = (Date) event.getObject();
	}

	/*
	 * getters und setters
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getRaum() {
		return raum;
	}

	public void setRaum(String raum) {
		this.raum = raum;
	}

	public String[] getPersonen() {
		return personen;
	}

	public void setPersonen(String[] personen) {
		this.personen = personen;
	}

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
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

}
