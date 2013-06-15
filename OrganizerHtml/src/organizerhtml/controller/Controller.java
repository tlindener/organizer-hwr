package organizerhtml.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	private organizer.objects.types.Calendar aktUserCa;
	Model myModel = new Model();

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

	public Controller() {
		aktUserCa = new Calendar();
		aktUserCa.setID(1);
		Date startDate = new Date();
		Date endDate = new Date();
		endDate.setHours(startDate.getHours() + 2);
		List<CalendarEntry> entries = new ArrayList<CalendarEntry>();
		CalendarEntry ce = new CalendarEntry();
		ce.setStartDate(startDate);
		ce.setDescription("Test");
		ce.setTitle("Testtermin");
		ce.setRoomId(1);
		ce.setOwnerId(1);
		ce.setCalendarId(1);
		ce.setEndDate(endDate);
		entries.add(ce);
		aktUserCa.setCalendarEntries(entries);
		if (!aktUserCa.getCalendarEntries().isEmpty()) {
			System.out.println("alle einträge");
			for (CalendarEntry e : entries) {
				System.out.println(e.getStartDate());
				System.out.println(e.getEndDate());
				System.out.println(e.getTitle());
			}
		}
		updateEventModel();
	}

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
				// XXX hier hängt er sich auf !
				// User tmpu = myRequester.login(username, password);
				// if (tmpu != null) {
				// aktUser = tmpu;
				//
				// aktUserCa = new Calendar();
				// aktUserCa.setID(1);
				// Calendar tmpCal = myRequester
				// .requestObjectByOwnId(aktUserCa);
				// System.out.println("hier");
				// // null Abfrage
				// if (tmpCal != null) {
				// aktUserCa = tmpCal;
				// }
				// }
				// updateEventModel();
				return "Kalender";
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return "Main";
	}

	/*
	 * folgende Methoden regeln die Befüllung des Datenmodells
	 */
	private void setupNetworkConnection() {
		myRequester = new JsonJavaRequestHandler("localhost", 48585);
	}

	private void fillMyModel(ScheduleEvent event) {
		// XXX hier fehlt nur noch der Zugang zu dem Calender Object. Danach
		// ließt er sich den rest alleine raus.
		SimpleDateFormat format = new SimpleDateFormat("DDDYY");
		CalendarEntry temp = (CalendarEntry) event.getData();
		String room = null;
		// XXX room ID ist im CalenderEntry mit drin. Aber wie komm ich an den
		// Wert?
		// room=myRequester.requestObjectByOwnId(new
		// Room().setID(temp.getID()));
		List<String> pers = new ArrayList<String>();
		for (User u : temp.getInvitees()) {
			pers.add(u.getGivenName());
		}
		myModel.setBeschreibung(temp.getDescription());
		myModel.setPers(pers);
		myModel.setRaum(room);
	}

	/*
	 * folgende Methoden werden benötigt,um die Daten innerhalb des "Scheduler"
	 * zu aktualisieren.
	 */

	private void updateEventModel() {
		eventModel.clear();
		for (CalendarEntry c : aktUserCa.getCalendarEntries()) {
			// eventModel.addEvent(new DefaultScheduleEvent(c.getTitle(), c
			// .getStartDate(), c.getEndDate()));
			eventModel.addEvent(new DefaultScheduleEvent(c.getTitle(), c
					.getStartDate(), c.getEndDate(), c));
			System.out.println(c.getTitle() + " / " + c.getStartDate() + " / "
					+ c.getEndDate());
		}
	}

	public void onDateSelect(SelectEvent selectEvent) {
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject());
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
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
		System.out.println(event.getTitle());
		fillMyModel(event);
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

	public String getPers() {
		StringBuilder sb = new StringBuilder();
		for (String s : myModel.getPers()) {
			sb.append(s + "\n");
		}
		return sb.toString();
	}

}
