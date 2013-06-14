package organizerhtml.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import network.RequestHandler;
import network.json.JsonJavaRequestHandler;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Room;
import organizer.objects.types.User;
import organizerhtml.model.Model;

public class Controller {

	/*
	 * vorherige Variablen
	 */
	private String username;
	private String password;
	private ScheduleModel eventModel = new DefaultScheduleModel();
	private ScheduleEvent event = new DefaultScheduleEvent();
	private RequestHandler myRequester;
	@SuppressWarnings("unused")
	private User aktUser;
	private Calendar aktUserCa;

	/*
	 * "unused", da die HTML direkt auf das Modell zugreifen soll und keine
	 * redundanten Informationen zwischenspeichern. Die Variablen sind trotzdem
	 * notwendig, da die Beanmethoden diese benötigen. Hier wird die Grundlage
	 * für die Model-Schnittstelle gelegt.
	 */

	@SuppressWarnings("unused")
	private Date date;
	@SuppressWarnings("unused")
	private String beschreibung = "Beschreibung";
	@SuppressWarnings("unused")
	private String raum = "Raum";
	@SuppressWarnings("unused")
	private List<String> pers;
	Model myModel = new Model();

	/**
	 * Die Methode "login" übermittelt die Korrektheit der Benutzerdaten
	 * 
	 * @return
	 */
	public String login() {
		setupNetworkConnection();
		try {
			// XXX hier die Benutzerüberprüfung anhand des NetworkLayour.
			if (!username.equals("") && !password.equals("")) {
				// System.out.println(getUsername());
				// System.out.println(getPassword());
				// User tmpu = myRequester.login(username, password);
				// if (tmpu != null) {
				// aktUser = tmpu;
				// aktUserCa = new Calendar();
				// aktUserCa.setID(1);
				// }
				// organizer.objects.types.Calendar tmpCal = myRequester
				// .requestObjectByOwnId(aktUserCa);
				//
				// // null Abfrage
				// if (tmpCal != null) {
				// aktUserCa = tmpCal;
				// }
				// updateEventModel();
				return "Kalender";
			}
		} catch (NullPointerException e) {
		}
		return "Main";
	}

	/*
	 * folgende Methoden regeln die Befüllung des Datenmodells
	 */
	private void setupNetworkConnection() {
		myRequester = new JsonJavaRequestHandler("localhost", 48585);
	}
	
	private void fillMyModel(){
		//XXX UNBEDINGT UPDATEN
		myModel.setBeschreibung("");
		myModel.setPers(null);
		myModel.setRaum("");
	}

	/*
	 * folgende Methoden werden benötigt,um die Daten innerhalb des "Scheduler"
	 * zu aktualisieren.
	 */

	private void updateEventModel() {
		eventModel.clear();
		for (CalendarEntry c : aktUserCa.getCalendarEntries()) {
			eventModel.addEvent(new DefaultScheduleEvent(c.getTitle(), c
					.getStartDate(), c.getEndDate()));
			System.out.println(c.getTitle() + " " + c.getStartDate() + " "
					+ c.getEndDate());
		}
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public void addEvent(ActionEvent actionEvent) {
		if (event.getId() == null)
			eventModel.addEvent(event);
		else
			eventModel.updateEvent(event);

		event = new DefaultScheduleEvent();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

	/*
	 * Username und Password hat nichts im Datenmodell zu tun, deswegen wird es
	 * innerhalb des Controllers behalten.
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	/*
	 * Hier werden die Anfragen der Beans direkt auf das Datenmodell verwiesen.
	 */
	public Date getDate() {
		return myModel.getDate();
	}

	public void setDate(Date date) {
		myModel.setDate(date);
	}

	public void handleDateSelect(SelectEvent event) {
		myModel.setDate((Date) event.getObject());
	}

	public String getBeschreibung() {
		return myModel.getBeschreibung();
	}

	public String getRaum() {
		return myModel.getRaum();
	}

	public List<String> getPers() {
		return myModel.getPers();
	}

}
