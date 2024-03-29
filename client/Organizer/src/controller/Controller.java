package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.management.MXBean;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import logik.DataPusher;
import logik.Model;

import organizer.networklayer.network.RequestHandler;
import organizer.networklayer.network.json.JsonJavaIISRequestHandler;
import organizer.networklayer.network.json.JsonJavaRequestHandler;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

import view.TerminBearbeiten;
import view.View;

/**
 * The Controller functions as the communication interface between the Model and
 * the Graphical User Interfaces (all classes contained in the package view;
 * afterwards referred to as View). Together with the Model and the View the
 * Controller follows the Model-View-Controller pattern. The Controller handels
 * all communication requests from the view and communicates therefore with the
 * model to answer the view-requests. Furthermore it pushes the data from the
 * server into the model once requested. It implements three listeners
 * (ActionListener, MouseListener and PropertyChangeListener) to process actions
 * taken by the user through the view and also a DataPusher to provide an
 * interface to windows.
 * 
 * @author JenniferBlumenthal
 * @version final version x.xxx
 */
public class Controller implements DataPusher, ActionListener, MouseListener,
		PropertyChangeListener, ListSelectionListener {

	/**
	 * @param args
	 * 
	 */

	private Model myModel;

	private Object[][] tabellenDaten;

	private View view;

	private RequestHandler myRequester;
	private Date aktDate;
	private String aktTermin;
	private int port;
	private String adresse;
	private String benutzername;
	private String passwort;
	private User aktUser;
	private organizer.objects.types.Calendar aktUserCa;
	private CalendarEntry aktEntry;
	private CalendarEntry neuCalEnt;
	private Invite aktin;

	/**
	 * Default Controller which creates an instance of the view class which
	 * creates all windows accept from the "Termin Bearbeiten" window. Sets the
	 * current date in the model and initially updates the data.
	 */
	public Controller() {

		myModel = new Model(aktDate);
		updateData();
		view = new View(this, this, this, this, this);
		view.createLogScreen();
		aktUser = new User();
		aktUserCa = new organizer.objects.types.Calendar();

	}

	/**
	 * Starts the programm.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Controller();
	}

	/**
	 * Handels the ActionEvents fired by different JButtons.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (view.getMyHauptmenue() != null
				&& e.getSource() == view.getMyHauptmenue().getBtnAbmelden()) {
			view.schliesseAlleFenster();
			view.createLogScreen();
			return;
		}
		if (view.getMyServereinstellungen() != null
				&& e.getSource() == view.getMyServereinstellungen()
						.getBtnSpeichern()) {
			speichereServereinstellungen();
			return;
		}
		if (view.getMyHauptmenue() != null
				&& e.getSource() == view.getMyHauptmenue()
						.getBtnTerminBearbeiten()) {
			bearbeiteTermin();
			return;
		}

		if (view.getMyHauptmenue() != null
				&& e.getSource() == view.getMyHauptmenue()
						.getBtnTerminEntfernen()) {
			entferneTermin();
			return;
		}
		if (view.getMyHauptmenue() != null
				&& e.getSource() == view.getMyHauptmenue()
						.getBtnRaumErstellen()) {
			view.createNeuerRaum();
			return;
		}
		if (view.getMyLogScreen() != null
				&& e.getSource() == view.getMyLogScreen()
						.getMntmServerkonfigurationen()) {
			view.createServereinstellungen();
			return;
		}
		if (view.getMyLogScreen() != null
				&& e.getSource() == view.getMyLogScreen().getBtnAnmelden()) {
			view.getMyLogScreen().dispose();
			meldeUserAn();
			return;
		}
		if (view.getMyLogScreen() != null
				&& e.getSource() == view.getMyLogScreen().getBtnRegistrieren()) {
			view.getMyLogScreen().dispose();
			view.createRegistrieren();
			return;
		}
		if (view.getMyRegistration() != null
				&& e.getSource() == view.getMyRegistration()
						.getBtnRegistrieren()) {
			registriereUser();
			return;
		}
		if (view.getMyRegistration() != null
				&& e.getSource() == view.getMyRegistration().getBtnAbbrechen()) {
			view.getMyRegistration().loescheInhalte();
			view.getMyRegistration().dispose();
			view.createLogScreen();
			return;
		}

		if (view.getMyTerminBearbeiten() != null
				&& e.getSource() == view.getMyTerminBearbeiten()
						.getBtnAbbrechen()) {
			view.getMyTerminBearbeiten().dispose();
			return;
		}

		if (view.getMyTerminBearbeiten() != null
				&& e.getSource().equals(
						view.getMyTerminBearbeiten().getBtnTerminEintragen())) {
			speichereTermin();
			return;
		}

		if (view.getMyNeuerRaum() != null
				&& e.getSource() == view.getMyNeuerRaum().getBtnSpeichern()) {
			speichereRaum();
			return;
		}
		if (view.getMyNeuerRaum() != null
				&& e.getSource() == view.getMyNeuerRaum().getBtnAbbrechen()) {
			view.getMyNeuerRaum().dispose();
			return;

		}
		if (view.getMyEinladungen() != null
				&& e.getSource() == view.getMyEinladungen().getBtnAbsagen()) {
			if (aktin != null) {
				aktin.setAccepted(-1);
				bearbeiteEinladung();
			}
			return;
		}
		if (view.getMyEinladungen() != null
				&& e.getSource() == view.getMyEinladungen().getBtnZusagen()) {
			if (aktin != null) {
				aktin.setAccepted(1);
				bearbeiteEinladung();
			}
			return;
		}

	}

	/**
	 * Handels the mouse events from different GUI-Elements if the mouse is
	 * clicked.
	 * 
	 * @param e
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource() == view.getMyHauptmenue().getPicLabel()) {
			if (myModel.getEinladungen().size() > 0) {

				befuelleEinladungen();
			} else
				JOptionPane.showMessageDialog(view.getMyHauptmenue(),
						"Sie haben derzeit keine neuen Einladungen");
		}

	}

	/**
	 * unused
	 * 
	 * @param arg0
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	/**
	 * unused
	 * 
	 * @param arg0
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	/**
	 * unused
	 * 
	 * @param arg0
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	/**
	 * unused
	 * 
	 * @param arg0
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	/**
	 * Handles all PropertyChangeEvents.
	 * 
	 * @param e
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e) {

		if (e.getOldValue() == null && e.getNewValue() != null) {
			//
			aktDate = new Date();

		}
		if (e.getOldValue() != null) {
			aktDate = view.getMyHauptmenue().getAktDateCali();
			view.getMyHauptmenue().repaint();
			connectServerModel();

		}

	}

	/**
	 * Handles the events that are triggered by the ListSelectionListener.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {

		if (e.getSource() == view.getMyHauptmenue().getListSelectionModel()) {
			befuelleMainFrame();
		}

	}

	/**
	 * Creates an array with 1400 rows and 3 columns. For every minute of the
	 * day the method proofs if there is a calendar entry. If yes it fills it
	 * into the array, as well as the duration.
	 */
	public void erstelleTabellenDaten() {
		tabellenDaten = new Object[1440][3];
		int myTimeHour = 0;
		int myTimeMinute = 0;
		String myTime = null;
		int i = 0;

		/*
		 * all times are generated
		 */
		for (int j = 1; j <= 60; j++) {
			if (myTimeMinute < 10) {
				myTime = myTimeHour + ":0" + myTimeMinute;
			} else
				myTime = myTimeHour + ":" + myTimeMinute;

			myTimeMinute = myTimeMinute + 1;

			if (j == 60) {
				if (myTimeHour < 23) {
					myTimeHour = myTimeHour + 1;
					myTimeMinute = 0;
					j = 0;
				} else
					j = 60;
			}

			/*
			 * Test
			 */
			String tmpBesch = myModel.returnBeschreibung(myTime);
			if (tmpBesch != null) {
				JButton jbtn = new JButton();
				jbtn.setText(myModel.returnBeschreibung(myTime));
				tabellenDaten[i][0] = myTime;
				tabellenDaten[i][1] = myModel.returnEndzeit(myTime);
				// tabellenDaten[i][2]= jbtn;
				tabellenDaten[i][2] = myModel.returnBeschreibung(myTime);

			} else if (myTimeMinute == 1 || myTimeMinute == 31) {

				tabellenDaten[i][0] = myTime;
				tabellenDaten[i][1] = "";
			}

			if (i < 1439)
				i = i + 1;
		}

		konvertiereBeschreibungsDaten();
	}

	/**
	 * Returns the array with the table data.
	 */
	@Override
	public Object[][] getBeschreibungen() {
		return tabellenDaten;
	}

	/**
	 * Converts the array with all minutes of the day. It returns
	 * 
	 * @return
	 */
	public Object[][] konvertiereBeschreibungsDaten() {
		Object[][] tabellenDatenKonv = new Object[48 * 2][3];
		int j = 0;
		for (int i = 0; i < 1439; i++) {
			if (tabellenDaten[i][1] != null) {

				tabellenDatenKonv[j][0] = tabellenDaten[i][0];
				tabellenDatenKonv[j][1] = tabellenDaten[i][1];
				tabellenDatenKonv[j][2] = tabellenDaten[i][2];
				if (j < 48) {
					j++;
				}
			}

		}
		tabellenDaten = tabellenDatenKonv;
		return tabellenDaten;
	}

	/**
	 * Writes all Data from the database into the model. For that it requests
	 * the data which is needed in the model and transforms it into the right
	 * form.
	 * 
	 * @throws ParseException
	 */
	public void befuelleModel() throws ParseException {
		if (myRequester != null) {
			myModel.getBeschreibungen().clear();
			myModel.getDauer().clear();
			myModel.getPersonen().clear();
			myModel.getDetails().clear();
			myModel.getRaeume().clear();
			myModel.getAnfangende().clear();

			List<Room> lr = myRequester.requestAllObjects(new Room());
			myModel.setAlleRaeume(lr);

			List<User> lp = myRequester.requestAllObjects(new User());
			myModel.setAllePersonen(lp);

			aktUser = myRequester.requestObjectByOwnId(aktUser);

			List<Integer> inviteIds = aktUser.getInviteIds();
			List<Invite> einladungen = new ArrayList<>();

			for (int i = 0; i < inviteIds.size(); i++) {
				Invite in = new Invite();
				in.setID(inviteIds.get(i));
				in = myRequester.requestObjectByOwnId(in);
				if (in != null) {
					einladungen.add(in);
				} else
					System.out
							.println("hier konnte kein invite abgefragt werden");
			}
			myModel.setEinladungen(einladungen);

			aktUserCa = myRequester.requestObjectByOwnId(aktUserCa);

			List<CalendarEntry> myCes = aktUserCa.getCalendarEntries();

			for (CalendarEntry myCe : myCes) {

				if (parseDatetoString(myCe.getStartDate()).equals(
						parseDatetoString(view.getMyHauptmenue().getCali()
								.getDate()))) {

					SimpleDateFormat format = new SimpleDateFormat("H:mm");
					String anfangZeit = format.format(myCe.getStartDate());
					String endZeit = format.format(myCe.getEndDate());

					myModel.setAktDate(aktDate);
					myModel.setBeschreibungen(anfangZeit, myCe.getTitle());
					myModel.setDauer(anfangZeit, myCe.getDuration());

					ArrayList<User> invitees = (ArrayList<User>) getInvitesOfEntry(myCe
							.getInviteIds());

					myModel.setPersonen(anfangZeit, invitees);

					myModel.setKalendarentries(anfangZeit, myCe.getID());

					myModel.setPersonen(anfangZeit, rufeEingeladeneab(myCe));

					myModel.setDetails(anfangZeit, myCe.getDescription());
					myModel.setAnfangEnde(anfangZeit, endZeit);

					Room r = new Room();
					r.setID(myCe.getRoomId());
					Room tmp = myRequester.requestObjectByOwnId(r);
					if (tmp != null) {
						myModel.setRaeume(anfangZeit, tmp);
					} else {
						myModel.setRaeume(anfangZeit, null);
					}
				}
			}
		}
	}

	/**
	 * Requests all invitees to a particular calendarEntry that is submitted.
	 * 
	 * @param ce
	 * @return eingeladene
	 */
	public ArrayList<User> rufeEingeladeneab(CalendarEntry ce) {
		List<Invite> invites = myRequester.requestFollowingObjectsByOwnId(
				ce.getInviteIds(), new Invite());
		List<User> eingeladene = new ArrayList<User>();
		for (Invite invite : invites) {
			if (invite == null) {
				
			} else {
				User requestUser = new User();
				requestUser.setID(invite.getOwnerId());
				requestUser = myRequester.requestObjectByOwnId(requestUser);
				if (requestUser != null) {
					eingeladene.add(requestUser);
				} else {
					User us = new User();
					us.setGivenname("klappt nicht....");
					eingeladene.add(us);
				}
			}
		}
		return (ArrayList<User>) eingeladene;
	}

	/**
	 * Hier werden die User von den Invites abgefragt XXX Hier ist die Methode
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

	/**
	 * Transforms a given Date into a string without the time variables.
	 * 
	 * @param date
	 * @return datum
	 */

	public String parseDatetoString(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd zzz yyyy");
		String datum = format.format(date);
		return datum;
	}

	/**
	 * Transforms a given String into a Date with the submitted time ("zeit").
	 * 
	 * @param zeit
	 * @return date
	 */
	public Date parseStringtoDate(String zeit) {

		Date date = aktDate;
		String datetimeStr = date.toString();
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd " + zeit
				+ ":ss zzz yyyy");

		datetimeStr = format.format(date);
		try {
			format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			date = format.parse(datetimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Updates the accept status of an invite.
	 */
	public void bearbeiteEinladung() {
		if (aktin.isAccepted() == 1) {
			myRequester.acceptInvite(aktin);
		}
		if (aktin.isAccepted() == -1) {
			myRequester.declineInvite(aktin);
		}
		updateData();
		view.getMyHauptmenue().repaint();
		befuelleMainFrame();
		view.getMyEinladungen().dispose();
	}

	/**
	 * Fills the invite handling window with an unaccepted invite from the
	 * model.
	 */
	public void befuelleEinladungen() {

		updateData();
		List<Invite> einl = myModel.getEinladungen();
		einl = getUnacceptedInvites(einl);

		if (einl.isEmpty()) {
			aktin = null;
			JOptionPane.showMessageDialog(view.getMyHauptmenue(),
					"Sie haben derzeit keine neuen Einladungen");
			return;
		}
		view.createEinladungen();
		aktin = einl.get(0);
		int id = aktin.getCalendarEntryId();
		CalendarEntry ce = new CalendarEntry();
		ce.setID(id);
		ce = myRequester.requestObjectByOwnId(ce);
		if (ce != null) {
			view.getMyEinladungen().getTxtBeschreibung().setText(ce.getTitle());
			view.getMyEinladungen().getTxtADetails()
					.setText(ce.getDescription());
			Room room = new Room();
			room.setID(ce.getRoomId());
			room = myRequester.requestObjectByOwnId(room);
			if (room != null)
				view.getMyEinladungen()
						.getTxtRaum()
						.setText(
								room.getDescription() + " ; "
										+ room.getLocation());

			User einladener = new User();
			einladener.setID(ce.getOwnerId());
			einladener = myRequester.requestObjectByOwnId(einladener);
			if (einladener != null)
				view.getMyEinladungen()
						.getTxtEinladener()
						.setText(
								einladener.getGivenName() + " "
										+ einladener.getSurname());

			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			String anfang = format.format(ce.getStartDate());
			String ende = format.format(ce.getEndDate());
			view.getMyEinladungen().getTxtVon().setText(anfang);
			view.getMyEinladungen().getTxtBis().setText(ende);

			boolean status = false;

			List<CalendarEntry> myCes = aktUserCa.getCalendarEntries();

			for (CalendarEntry myCe : myCes) {

				if (myCe.getStartDate().equals(ce.getStartDate())) {
					status = true;
				} else
					status = false;
			}

			if (status == true)
				view.getMyEinladungen().getCanStatus().setBackground(Color.RED);
			else
				view.getMyEinladungen().getCanStatus()
						.setBackground(Color.GREEN);

		}
	}

	/**
	 * Fills the main frame with all information and updates data after the
	 * mouse has been clicked.
	 * 
	 */
	public void befuelleMainFrame() {
		JTable zwTab = view.getMyHauptmenue().getTable_1();

		if (zwTab.getSelectedRow() != -1) {
			aktTermin = (String) view.getMyHauptmenue().getTable_1()
					.getValueAt(zwTab.getSelectedRow(), 0);
		}
		String details = (String) myModel.returnDetail(aktTermin);
		List<User> eingeladene = new ArrayList<User>();
		eingeladene = myModel.returnEingeladene(aktTermin);
		String raum = returnStringOfObject(myModel.returnRaum(aktTermin));

		int myEinl = getUnacceptedInvites(myModel.getEinladungen()).size();

		view.befuelleHauptmenue(zwTab, details, eingeladene, raum, myEinl);

	}

	/**
	 * Creates a new window to edit an entry with the right settings. If there
	 * is no current entry chosen (a table row selected) the window will be
	 * opened without any attributes as an empty window. Otherwise the window is
	 * opened with the attributes to this entry that are stored in the model.
	 */
	public void bearbeiteTermin() {

		aktDate = view.getMyHauptmenue().getAktDateCali();
		String startZeit = aktTermin;
		boolean containsTermin = myModel.getKalendarentries().containsKey(
				aktTermin);
		if (aktTermin == null || containsTermin == false) {

			if (containsTermin == false) {
				view.createTerminBearbeiten(startZeit, "", "", "", "", null,
						null);
			} else {
				view.createTerminBearbeiten();
			}
		} else {

			String details = myModel.returnDetail(aktTermin);
			String endZeit = myModel.returnEndzeit(aktTermin);
			String beschreibung = myModel.returnBeschreibung(aktTermin);
			Room r = myModel.returnRaum(aktTermin);
			String raum = returnStringOfObject(r);
			Room[] raeume = new Room[] { r };
			ArrayList<User> eing = myModel.returnEingeladene(aktTermin);
			User[] personen = eing.toArray(new User[eing.size()]);
			view.createTerminBearbeiten(details, endZeit, startZeit,
					beschreibung, raum, raeume, personen);

		}
	}

	/**
	 * Fills the Model with new data and generates new table data.
	 */
	public void updateData() {

		try {
			befuelleModel();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		erstelleTabellenDaten();

	}

	/**
	 * Saves a new created room and submits it to the database.
	 */
	public void speichereRaum() {
		view.getMyNeuerRaum().pruefeFelder();
		Room tmpRaum = new Room();
		String name = view.getMyNeuerRaum().getTxtBeschreibung().getText();
		String lage = view.getMyNeuerRaum().getTxtALage().getText();
		int sitze = Integer.parseInt(view.getMyNeuerRaum().getTxtSitze()
				.getText());

		tmpRaum.setDescription(name);
		tmpRaum.setLocation(lage);
		tmpRaum.setSeats(sitze);

		Room erstRaum = myRequester.addObject(tmpRaum);
		if (erstRaum != null && view.getMyTerminBearbeiten() != null) {
			view.getMyTerminBearbeiten()
					.getTxtRaum()
					.setText(
							erstRaum.getLocation() + "; "
									+ erstRaum.getDescription());
			view.getMyNeuerRaum().setVisible(false);
			updateData();
			view.getMyTerminBearbeiten().getLstRaum()
					.setListData(pushRoomList());
			neuCalEnt.setRoomId(erstRaum.getID());
			view.getMyTerminBearbeiten().setSelectedRoom(erstRaum);
			view.getMyNeuerRaum().loescheInhalte();
			view.getMyTerminBearbeiten().repaint();

			view.getMyTerminBearbeiten().setVisible(true);
		}

		else
			JOptionPane.showMessageDialog(view.getMyNeuerRaum(),
					"Ihr Raum konnte nicht hinzugef�gt werden");

	}

	/**
	 * Saves a new created calendar entry and submits it to the database.
	 */
	public void speichereTermin() {

		neuCalEnt = new CalendarEntry();

		boolean pruefefeld = view.getMyTerminBearbeiten().pruefeFelder();

		if (pruefefeld == false) {
			if (view.getMyTerminBearbeiten().getSelectedRoom().getID() == -1) {
				view.createNeuerRaum();
				return;
			} else {
				neuCalEnt.setRoomId(view.getMyTerminBearbeiten()
						.getSelectedRoom().getID());
			}

			String startzeit = view.getMyTerminBearbeiten().getStartUhrzeit()
					.getText();
			String endzeit = view.getMyTerminBearbeiten().getEndUhrzeit()
					.getText();

			Date startDate = parseStringtoDate(startzeit);
			Date endDate = parseStringtoDate(endzeit);

			neuCalEnt.setCalendarId(aktUserCa.getID());
			neuCalEnt.setDescription(view.getMyTerminBearbeiten()
					.getTxtADetails().getText());

			neuCalEnt.setEndDate(endDate);
			neuCalEnt.setOwnerId(aktUser.getID());

			neuCalEnt.setStartDate(startDate);
			neuCalEnt.setTitle(view.getMyTerminBearbeiten().getBeschreibung()
					.getText());

			boolean status = true;
			CalendarEntry entry = new CalendarEntry();
			int id = 0;

			boolean hasid = myModel.getKalendarentries().containsKey(startzeit);
			if (hasid == true) {
				id = myModel.returnKalendarentryId(startzeit);
				neuCalEnt.setID(id);
				int i = JOptionPane
						.showConfirmDialog(
								view.getMyTerminBearbeiten(),
								"Der Eintrag besteht bereits. Wollen Sie den bestehenden Termin �ndern (der bestehende Termin wird dabei �berschreiben)? Wenn Sie Nein w�hlen, w�hlen Sie bitte eine neue Startzeit aus.",
								"Termin bereits vorhanden",
								JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION)
					status = myRequester.updateObject(neuCalEnt);
				else {
					view.getMyTerminBearbeiten().getStartUhrzeit().setText("");
					return;
				}
			} else {
				entry = myRequester.addObject(neuCalEnt);
			}

			if (entry == null || status == false) {
				if (entry == null)

					JOptionPane.showMessageDialog(view.getMyTerminBearbeiten(),
							"Termin konnte nicht eingetragen werden",
							"Termin konnte nicht eingetragen werden",
							JOptionPane.INFORMATION_MESSAGE);

				if (status == false)
					JOptionPane.showMessageDialog(view.getMyTerminBearbeiten(),
							"Termin konnte nicht geupdatet werden",
							"Termin konnte nicht geupdatet werden",
							JOptionPane.INFORMATION_MESSAGE);
			} else {

				JOptionPane.showMessageDialog(view.getMyTerminBearbeiten(),
						"Termin wurde eingetragen", "Termin wurde eingetragen",
						JOptionPane.INFORMATION_MESSAGE);
			}

			if (status == true) {
				versendeEinladungen(neuCalEnt.getID());
			} else {
				versendeEinladungen(entry.getID());
			}

			view.getMyTerminBearbeiten().dispose();
			updateData();

		} else {
			if (pruefefeld == true) {
				return;
			}
			JOptionPane.showMessageDialog(view.getMyTerminBearbeiten(),
					"Termin konnte nicht eingetragen werden",
					"Termin konnte nicht eingetragen werden",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Deletes a calendar entry.
	 */
	public void entferneTermin() {
		if (aktTermin != null) {
			if (myModel.getKalendarentries().containsKey(aktTermin)) {
				CalendarEntry delCe = new CalendarEntry();
				delCe.setID(myModel.returnKalendarentryId(aktTermin));
				boolean geloescht = myRequester.removeObjectByOwnId(delCe);
				if (geloescht == false)
					JOptionPane
							.showMessageDialog(
									view.getMyHauptmenue(),
									"Der Termin konnte nicht gel�scht werden! Bitte versuchen Sie es zu einem sp�teren Zeitpunkt erneut");

				updateData();
			}
		} else
			JOptionPane.showMessageDialog(view.getMyHauptmenue(),
					"Bitte w�hlen Sie einen Termin aus!");
	}

	/**
	 * Saves entered server settings. Per default the settings "Serveradresse"
	 * as "localhost" and port as 48585 are implemented. If the user changes
	 * these settings the new settings will be saved and a new Requester will be
	 * created to access the server via the networklayer.
	 */
	public void speichereServereinstellungen() {

		boolean fehler = view.getMyServereinstellungen().pruefeFelder();
		if (!fehler) {
			port = Integer.parseInt(view.getMyServereinstellungen()
					.getTxtPort().getText());
			adresse = view.getMyServereinstellungen().getTxtAdresse().getText();

			myRequester = new JsonJavaIISRequestHandler(adresse, port);
			view.getMyServereinstellungen().dispose();
			if (myRequester == null) {
				JOptionPane.showMessageDialog(null,
						"Bitte pr�fen Sie ob der Server in Betrieb ist");
			}
		} else {
			JOptionPane
					.showMessageDialog(
							null,
							"Bitte geben Sie g�ltige Servereinstellungen ein. "
									+ "Bedenken Sie, dass sie wenn Sie die Standardeinstellungen �ndern es zu Verbindungsproblemen kommen kann!"
									+ "Standardeinstellungen adresse: �localhost� und port:�80� ein");
		}
	}

	/**
	 * Submits the invitation to the server.
	 * 
	 * @param calendarEntryId
	 */
	public void versendeEinladungen(int calendarEntryId) {
		CalendarEntry entry = new CalendarEntry();
		entry.setID(calendarEntryId);
		entry = myRequester.requestObjectByOwnId(entry);
		List<Integer> listInviteIds = new ArrayList<>();
		if (entry == null) {

		} else {
			listInviteIds = entry.getInviteIds();
		}

		HashMap<User, Integer> invitedUsers = new HashMap<User, Integer>();

		for (int inviteId : listInviteIds) {
			Invite in = new Invite();
			in.setID(inviteId);
			Invite vorhanden = myRequester.requestObjectByOwnId(in);
			if (vorhanden != null) {
				User requestUser = new User();
				requestUser.setID(vorhanden.getOwnerId());
				User user = myRequester.requestObjectByOwnId(requestUser);
				if (user != null) {
					invitedUsers.put(user, inviteId);
				}
			}
		}

		for (User user : view.getMyTerminBearbeiten().getSelectedUsers()) {
			if (!invitedUsers.containsKey(user)) {
				Invite newInvite = new Invite();
				newInvite.setOwnerId(user.getID());
				newInvite.setCalendarEntryId(calendarEntryId);
				Invite tmpin = myRequester.addObject(newInvite);
				if (tmpin == null) {
					JOptionPane.showMessageDialog(view.getMyTerminBearbeiten(),
							"Die Einladungen konnten nicht versandt werden!");
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Nutzer " + invitedUsers.remove(user)
								+ " wurde entfernt");
			}
		}

		for (User user : invitedUsers.keySet()) {
			Invite removeInvite = new Invite();
			removeInvite.setID(invitedUsers.get(user));
			boolean tmpin = myRequester.removeObjectByOwnId(removeInvite);
			if (tmpin == false) {
				JOptionPane.showMessageDialog(view.getMyTerminBearbeiten(),
						"Die Einladung konnte nicht gel�scht werden!");
			}
		}
	}

	/**
	 * Connects the Server and the Model with setting the current Date of the
	 * Model and afterwards updating the Model data.
	 * 
	 */
	public void connectServerModel() {
		myModel.setAktDate(aktDate);
		updateData();
	}

	/**
	 * Pushes the list of users from the model. Sorts before so that the list
	 * includes all users except from the current logged-in user.
	 */
	@Override
	/**
	 * Pushes an array from type User with the data from the Model.
	 */
	public User[] pushUserList() {
		User[] alleUser = myModel.getAllePersonen();
		User[] sortUser = new User[alleUser.length - 1];
		int i = 0;
		for (User u : alleUser) {

			if (u.getID() != aktUser.getID()) {
				sortUser[i] = u;
				i++;
			}

		}
		return sortUser;
	}

	/**
	 * Pushes a list with all rooms from the model.
	 */
	@Override
	/**
	 * Pushes an array from type Room with the data from the Model.
	 */
	public Room[] pushRoomList() {
		return myModel.getAlleRaeume();
	}

	/**
	 * Registers a user with all the entered information through adding a new
	 * user. If this was successful a user ID is returned (not visible for
	 * user), otherwise a window will open and announce that the registration
	 * was not successful. Furthermore the calendar for the user is created
	 * after successful adding of the user.
	 */
	public void registriereUser() {

		char[] passwort1 = view.getMyRegistration().getTxtPasswort()
				.getPassword();
		char[] passwort2 = view.getMyRegistration().getTxtPasswortBest()
				.getPassword();

		String email = view.getMyRegistration().getTxtEmailadresse().getText();
		String nachname = view.getMyRegistration().getTxtNachname().getText();
		String vorname = view.getMyRegistration().getTxtVorname().getText();
		String telefonnr = view.getMyRegistration().getTxtTelefon().getText();
		/*
		 * When all fields are filled with values the data will be processed.
		 */
		if (!email.isEmpty() && !nachname.isEmpty() && !vorname.isEmpty()
				&& passwort1.length != 0 && passwort2.length != 0) {

			String pwd1 = new String(passwort1);
			String pwd2 = new String(passwort2);

			if (pwd1.equals(pwd2)) {

				passwort = pwd1;
				/*
				 * If there is no Requester to communicate with the Networklayer
				 * the method in which it is creates is invoked.
				 */
				if (myRequester == null) {
					speichereServereinstellungen();
				}
				aktUser.setMailAddress(email);
				aktUser.setSurname(nachname);
				aktUser.setGivenname(vorname);
				if (!telefonnr.isEmpty())
					aktUser.setPhoneNumber(telefonnr);

				User utmp = myRequester.registerNewUser(aktUser, passwort);

				if (utmp == null) {
					JOptionPane
							.showMessageDialog(
									view.getMyRegistration(),
									"Sie konnten nicht registriert werden. Bitte stellen Sie sicher, dass Sie nicht bereits registriert sind!");
					return;
				} else {
					JOptionPane.showMessageDialog(view.getMyRegistration(),
							"Sie wurden erfolgreich registriert");
					aktUser = utmp;
					aktUserCa = null;
					aktUserCa = new Calendar();
					aktUserCa.setOwnerId(aktUser.getID());
					aktUserCa.setDescription("pers�nlicher Kalendar von "
							+ aktUser.getGivenName());
					aktUserCa.setName("Kalendar von " + aktUser.getGivenName());
					myRequester.login(aktUser.getMailAddress(), passwort);
					Calendar tmp = myRequester.addObject(aktUserCa);

					if (tmp == null) {
						JOptionPane.showMessageDialog(view.getMyRegistration(),
								"Es konnte kein Kalendar hinzugef�gt werden");

					}
				}
				view.getMyRegistration().loescheInhalte();
				view.getMyRegistration().dispose();
				view.createLogScreen();

			} else {
				JOptionPane.showMessageDialog(view.getMyLogScreen(),
						"Das Passwort stimmt nicht �berein!",
						"Passwort falsch", JOptionPane.INFORMATION_MESSAGE);
				view.getMyRegistration().getTxtPasswort().setText("");
				view.getMyRegistration().getTxtPasswortBest().setText("");
				return;
			}
		} else {
			view.getMyRegistration().pruefeVollst�ndigkeit();
		}

	}

	/**
	 * Logs in a user through collecting the entered email ID and the password.
	 * Requests through the networklayer to log in this user on the server and
	 * requests the calendar registered to the User. If the request was
	 * successful the users calendar is requested and saved local. Otherwise an
	 * error message is opened.
	 */
	public void meldeUserAn() {

		speichereServereinstellungen();

		benutzername = view.getMyLogScreen().getTextField().getText();
		char[] tmppasswort = view.getMyLogScreen().getPasswort().getPassword();

		passwort = "";
		for (int i = 0; i < tmppasswort.length; i++) {
			passwort = passwort + tmppasswort[i];
		}

		if (benutzername.equals("") || passwort.equals("")) {
			JOptionPane
					.showMessageDialog(
							view.getMyLogScreen(),
							"Bitte geben Sie einen g�ltigen Benutzernamen und ein g�ltiges Passwort ein",
							"Benutzername oder Passwort falsch",
							JOptionPane.INFORMATION_MESSAGE);
			view.createLogScreen();
			return;
		} else {

			User tmpu = myRequester.login(benutzername, passwort);

			if (tmpu != null) {

				aktUser = tmpu;
				List<Integer> calendarIDs = aktUser.getCalendarIds();
				if (!calendarIDs.isEmpty()) {
					aktUserCa.setID(calendarIDs.get(0));
				} else {
					JOptionPane
							.showMessageDialog(
									view.getMyLogScreen(),
									"Es ist noch kein Kalendar f�r Sie erstellt worden",
									"Verbindungsfehler",
									JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				Calendar tmpCal = myRequester.requestObjectByOwnId(aktUserCa);

				if (tmpCal != null) {
					aktUserCa = tmpCal;
					view.createHauptmenue();
					connectServerModel();

				} else {
					JOptionPane
							.showMessageDialog(
									view.getMyLogScreen(),
									"Es konnte keine Verbindung zum Server hergestellt werden, bitte starten Sie das Programm neu",
									"Verbindungsfehler",
									JOptionPane.INFORMATION_MESSAGE);
					return;

				}
				befuelleMainFrame();
				view.getMyLogScreen().dispose();
			} else {
				JOptionPane
						.showMessageDialog(
								view.getMyLogScreen(),
								"Leider sind Sie noch nicht registriert. Bitte Registrieren Sie sich um die MyOrganizer Funktionen nutzen zu k�nnen.",
								"User nicht vorhanden",
								JOptionPane.INFORMATION_MESSAGE);
				view.createLogScreen();
				return;
			}

		}
	}

	/**
	 * Returns the unaccepted Invites of the current user.
	 * 
	 * @param einl
	 * @return sort Einl
	 */
	private List<Invite> getUnacceptedInvites(List<Invite> einl) {
		List<Invite> sortEinl = new ArrayList<>();
		for (Invite in : einl) {
			if (in.isAccepted() == 0) {
				sortEinl.add(in);
			}
		}
		return sortEinl;
	}

	/**
	 * Returns the name of a room as String.
	 * 
	 * @param obj
	 * @return description
	 */
	private String returnStringOfObject(Object obj) {
		if (obj == null) {
			return "";
		}
		if (obj instanceof Room) {
			return ((Room) obj).getDescription();
		}
		return obj.toString();
	}

}
