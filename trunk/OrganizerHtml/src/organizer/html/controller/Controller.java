package organizer.html.controller;

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

import organizer.html.model.Model;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

public class Controller {

	/*
	 * vorherige Variablen
	 */
	/** username */
	private String username;
	/** password */
	private String password;
	/** eventModel for the Schedular */
	private ScheduleModel eventModel = new DefaultScheduleModel();
	/** event for the Schedular */
	private ScheduleEvent event = new DefaultScheduleEvent();
	/** JSon eventhandler */
	private RequestHandler myRequester;
	@SuppressWarnings("unused")
	/** actual User object*/
	private User aktUser;
	/** actual UserCalendar object */
	private organizer.objects.types.Calendar aktUserCa;
	/** data model */
	Model myModel = new Model();

	/*
	 * "unused", da die HTML direkt auf das Modell zugreifen soll und keine
	 * redundanten Informationen zwischenspeichern. Die Variablen sind trotzdem
	 * notwendig, da die Beanmethoden diese benötigen. Hier wird die Grundlage
	 * für die Model-Schnittstelle gelegt.
	 */

	@SuppressWarnings("unused")
	/** actual date */
	private Date date;
	@SuppressWarnings("unused")
	/** actual description*/
	private String beschreibung = "Beschreibung";
	@SuppressWarnings("unused")
	/** actual room*/
	private String raum = "Raum";
	@SuppressWarnings("unused")
	/** actual list of person*/
	private List<String> pers;

	/**
	 * Class to transmit the In- and Output data from the HTML to the datamodel
	 * and back.
	 * 
	 * @author Marcel Hodan
	 */
	public Controller() {

	}

	/**
	 * The method "login" transmit the name of the routing page depending on the
	 * correctness of the credentials.
	 * 
	 * @return the pagename for routing
	 */
	public String login() {
		myRequester = new JsonJavaRequestHandler("localhost", 48585);
		try {
			if (!username.equals("") && !password.equals("")) {
				User tmpu = myRequester.login(username, password);
				if (tmpu != null) {
					aktUser = tmpu;
					aktUserCa = new Calendar();
					aktUserCa.setID(1);
					Calendar tmpCal = myRequester
							.requestObjectByOwnId(aktUserCa);
					// null Abfrage
					if (tmpCal != null) {
						aktUserCa = tmpCal;
					}
					updateEventModel();
					return "Kalender";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Main";
	}

	/*
	 * folgende Methoden regeln die Befüllung des Datenmodells
	 */

	/**
	 * receives the necessary data from a CalendarEntry to put it in the
	 * DataModel.
	 * 
	 * @param event
	 */
	private void fillMyModel(ScheduleEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("DDDYY");
		CalendarEntry temp = (CalendarEntry) event.getData();

		Room room = new Room();
		room.setID(temp.getRoomId());
		room = myRequester.requestObjectByOwnId(room);
		String roomString;
		if (room != null) {
			roomString = room.getLocation();
			myModel.setRaum(roomString);
		}
		List<String> pers = new ArrayList<String>();
		List<User> userList = getInvitesOfEntry(temp.getInviteIds());
		for (User user : userList) {
			pers.add(user.getGivenName());
		}
		System.out.println("-----------");
		for (String s : pers) {
			System.out.println(s);
		}
		System.out.println("-----------");
		myModel.setBeschreibung(temp.getDescription());
		myModel.setPers(pers);

	}

	/**
	 * Receives the User-Objects from Invitees
	 * 
	 * @param inviteIds
	 * @return
	 */
	private List<User> getInvitesOfEntry(List<Integer> inviteIds) {
		ArrayList<User> user = new ArrayList<User>();
		List<Invite> invites = myRequester.requestFollowingObjectsByOwnId(
				inviteIds, new Invite());
		for (Invite invite : invites) {
			if (invite != null) {
				User requestUser = new User();
				requestUser.setID(invite.getOwnerId());
				requestUser = myRequester.requestObjectByOwnId(requestUser);
				if (requestUser != null) {
					user.add(requestUser);
				}
			}
		}
		return user;
	}

	/*
	 * folgende Methoden werden benötigt,um die Daten innerhalb des "Scheduler"
	 * zu aktualisieren.
	 */
	/**
	 * receives the right CalenderEntry out of the actuall UserCalender.
	 */
	private void updateEventModel() {
		eventModel.clear();
		for (CalendarEntry c : aktUserCa.getCalendarEntries()) {
			eventModel.addEvent(new DefaultScheduleEvent(c.getTitle(), c
					.getStartDate(), c.getEndDate(), c));
		}
	}

	/**
	 * onDateSelect eventHandler for the Schedular.
	 * 
	 * @param selectEvent
	 */
	public void onDateSelect(SelectEvent selectEvent) {
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject());
	}

	/**
	 * 
	 * @return the event
	 */
	public ScheduleEvent getEvent() {
		return event;
	}

	/**
	 * 
	 * @param event
	 */
	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	/**
	 * 
	 * @return the eventmodel
	 * 
	 */
	public ScheduleModel getEventModel() {
		return eventModel;
	}

	/**
	 * 
	 * @param eventModel
	 */
	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void addEvent(ActionEvent actionEvent) {
		if (event.getId() == null)
			eventModel.addEvent(event);
		else
			eventModel.updateEvent(event);

		event = new DefaultScheduleEvent();
	}

	/**
	 * onEventSelect eventHandler for the Schedular.
	 * 
	 * @param selectEvent
	 */
	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
		fillMyModel(event);
	}

	/*
	 * Username und Password hat nichts im Datenmodell zu tun, deswegen wird es
	 * innerhalb des Controllers behalten.
	 */

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * Hier werden die Anfragen der Beans direkt auf das Datenmodell verwiesen.
	 */

	/**
	 * dataSelect eventHandler for the Calendar
	 * 
	 * @param event
	 */
	public void handleDateSelect(SelectEvent event) {
		myModel.setDate((Date) event.getObject());
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return myModel.getDate();
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		myModel.setDate(date);
	}

	/**
	 * @return the beschreibung
	 */
	public String getBeschreibung() {
		return myModel.getBeschreibung();
	}

	/**
	 * @return the raum
	 */
	public String getRaum() {
		return myModel.getRaum();
	}

	/**
	 * @return the pers
	 */
	public String getPers() {
		StringBuilder sb = new StringBuilder();
		for (String s : myModel.getPers()) {
			sb.append(s + ", ");
		}
		return sb.toString();
	}

}
