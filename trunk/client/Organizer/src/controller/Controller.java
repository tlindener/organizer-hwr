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
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import logik.DataPusher;
import logik.Model;
import network.json.JsonJavaRequestHandler;
import network.RequestHandler;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

import view.Einladungen;
import view.NeuerRaum;
import view.Hauptmenue;
import view.LogScreen;
import view.RegisterUser;
import view.Servereinstellungen;
import view.TerminBearbeiten;

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
		PropertyChangeListener {

	/**
	 * @param args
	 * 
	 */

	private Model myModel;

	private Object[][] tabellenDaten;

	private TerminBearbeiten editEntry;

	private Hauptmenue myHauptmenue;
	private LogScreen myLogScreen;
	private Servereinstellungen myServereinstellungen;
	private RegisterUser myRegistration;
	private NeuerRaum myNeuerRaum;
	private Einladungen myEinladungen;

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
	 * Default Controller which creates instances of all windows accept from the
	 * "Termin Bearbeiten" window. Sets the current date in the model and
	 * initially updates the data.
	 */
	public Controller() {

		myModel = new Model(aktDate);
		updateData();
		myHauptmenue = new Hauptmenue(this, this, this, this);
		myHauptmenue.setVisible(false);
		myLogScreen = new LogScreen(this);
		myLogScreen.setVisible(true);
		myRegistration = new RegisterUser(this);
		myRegistration.setVisible(false);
		myServereinstellungen = new Servereinstellungen(this);
		myServereinstellungen.setVisible(false);
		myNeuerRaum = new NeuerRaum(this);
		myNeuerRaum.setVisible(false);
		myEinladungen = new Einladungen(this);
		myEinladungen.setVisible(false);
		aktUser = new User();
		aktUserCa = new organizer.objects.types.Calendar();

	}

	/**
	 * Starts the programm.
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
		if (e.getSource() == myLogScreen.getMntmServerkonfigurationen()) {
			myServereinstellungen.setVisible(true);
		}

		
		if (e.getSource() == myServereinstellungen.getBtnSpeichern()) {
			speichereServereinstellungen();

		}
		if (e.getSource() == myHauptmenue.getBtnTerminBearbeiten()) {
			bearbeiteTermin();

		}
		if (e.getSource() == myHauptmenue.getBtnAbmelden()) {
			myHauptmenue.setVisible(false);
			myLogScreen.setVisible(true);

		}
		if (e.getSource() == myHauptmenue.getBtnTerminEntfernen()) {
			entferneTermin();
		}
		if (e.getSource() == myHauptmenue.getBtnRaumErstellen()) {
			myNeuerRaum.setVisible(true);
		}
		if (e.getSource() == myLogScreen.getBtnAnmelden()) {
			meldeUserAn();
		}
		if (e.getSource() == myLogScreen.getBtnRegistrieren()) {
			myRegistration.setVisible(true);

		}
		if (e.getSource() == myRegistration.getBtnRegistrieren()) {
			registriereUser();
		}
		if (e.getSource() == myRegistration.getBtnAbbrechen()) {
			myRegistration.loescheInhalte();
			abbrechen(myRegistration, myLogScreen);
			

		}

		if (editEntry != null && e.getSource() == editEntry.getBtnAbbrechen()) {
			abbrechen(editEntry, myHauptmenue);
		}

		if (editEntry != null
				&& e.getSource().equals(editEntry.getBtnTerminEintragen())) {
			// Überprüfung auf richtiges Format!!
			speichereTermin();
		}

		if (e.getSource() == myNeuerRaum.getBtnSpeichern()) {
			speichereRaum();
		}
		if (e.getSource() == myNeuerRaum.getBtnAbbrechen()) {
			myNeuerRaum.setVisible(false);

		}
		if (e.getSource() == myEinladungen.getBtnAbsagen()) {
			aktin.setAccepted(-1);
			bearbeiteEinladung();
		}
		if (e.getSource() == myEinladungen.getBtnZusagen()) {
			aktin.setAccepted(1);
			bearbeiteEinladung();
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

		if (e.getSource() == myHauptmenue.getTable_1()) {
			befuelleMainFrame(e);
		}
		if (e.getSource() == myHauptmenue.getPicLabel()) {
			if (myModel.getEinladungen().size() > 0) {
				myEinladungen.setVisible(true);
				befuelleEinladungen();
			} else
				JOptionPane.showMessageDialog(myHauptmenue,
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
	 * unused
	 * 
	 * @param e
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		// System.out.println(e.getPropertyName());
		if (e.getOldValue() == null && e.getNewValue() != null) {
			//
			aktDate = new Date();

		}
		if (e.getOldValue() != null) {
			aktDate = myHauptmenue.getAktDateCali();
			myHauptmenue.repaint();
			connectServerModel();

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
			// Invite inv= new Invite();
			// inv.setOwnerId(aktUser.getID());

			List<Integer> inviteIds = aktUser.getInviteIds();
			List<Invite> einladungen = new ArrayList();
			for (int i : inviteIds) {
				Invite in = new Invite();
				in.setID(inviteIds.get(i - 1));
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
						parseDatetoString(myHauptmenue.getCali().getDate()))) {
					// Methode?
					SimpleDateFormat format = new SimpleDateFormat("H:mm");
					String anfangZeit = format.format(myCe.getStartDate());
					String endZeit = format.format(myCe.getEndDate());
					System.out.println("Anfangszeit: " + anfangZeit);
					myModel.setAktDate(aktDate);
					myModel.setBeschreibungen(anfangZeit, myCe.getTitle());
					System.out.println("Beschreibung: "
							+ myModel.returnBeschreibung(anfangZeit));
					myModel.setDauer(anfangZeit, myCe.getDuration());

					List<User> invitees = getInvitesOfEntry(myCe.getInviteIds());

					myModel.setPersonen(anfangZeit, invitees);

					myModel.setKalendarentries(anfangZeit, myCe.getID());

					myModel.setPersonen(anfangZeit, rufeEingeladeneab(myCe));

					myModel.setDetails(anfangZeit, myCe.getDescription());
					myModel.setAnfangEnde(anfangZeit, endZeit);

					Room r = new Room();
					r.setID(myCe.getRoomId());
					Room tmp = myRequester.requestObjectByOwnId(r);
					if (tmp != null) {
						myModel.setRaeume(anfangZeit, tmp.getDescription());
					} else {
						myModel.setRaeume(anfangZeit, "");
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
	public ArrayList<User> rufeEingeladeneab(CalendarEntry ce)
	{
		List<Invite> invites = myRequester
				.requestFollowingObjectsByOwnId(
						ce.getInviteIds(), new Invite());
		List<User> eingeladene = new ArrayList<User>();
		for (Invite invite : invites) {
			if (invite == null) {
				// Fehler bei der Abfrage, ID exitiert entweder
				// nicht oder es gab einen ParsingError (sollte hier
				// nicht vorkommen, aber um sicher zu gehen)
			} else {
				User requestUser = new User();
				requestUser.setID(invite.getOwnerId());
				requestUser = myRequester
						.requestObjectByOwnId(requestUser);
				if (requestUser != null) {
					eingeladene.add(requestUser);
				} else {
					// TESTf
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
		myRequester.acceptInvite(aktin);
		updateData();
		myEinladungen.setVisible(false);
	}

	/**
	 * Fills the invite handling window with an unaccepted invite from the
	 * model.
	 */
	public void befuelleEinladungen() {

		List<Invite> einl = myModel.getEinladungen();

		List<Invite> sortEinl = new ArrayList();
		for (Invite in : einl) {
			if (in.isAccepted() == 0) {
				sortEinl.add(in);
			}
		}
		einl = sortEinl;
		aktin = einl.get(0);
		int id = aktin.getCalendarEntryId();
		CalendarEntry ce = new CalendarEntry();
		ce.setID(id);
		ce = myRequester.requestObjectByOwnId(ce);
		if (ce != null) {
			myEinladungen.getTxtBeschreibung().setText(ce.getTitle());
			myEinladungen.getTxtADetails().setText(ce.getDescription());
			Room room = new Room();
			room.setID(ce.getRoomId());
			room = myRequester.requestObjectByOwnId(room);
			if (room != null)
				myEinladungen.getTxtRaum().setText(
						room.getDescription() + " ; " + room.getLocation());

			User einladener = new User();
			einladener.setID(ce.getOwnerId());
			einladener = myRequester.requestObjectByOwnId(einladener);
			if (einladener != null)
				myEinladungen.getTxtEinladener().setText(
						einladener.getGivenName() + " "
								+ einladener.getSurname());

			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			String anfang = format.format(ce.getStartDate());
			String ende = format.format(ce.getEndDate());
			myEinladungen.getTxtVon().setText(anfang);
			myEinladungen.getTxtBis().setText(ende);

			boolean status = false;

			List<CalendarEntry> myCes = aktUserCa.getCalendarEntries();

			for (CalendarEntry myCe : myCes) {

				if (myCe.getStartDate().equals(ce.getStartDate())) {
					status = true;
				} else
					status = false;
			}

			if (status == true)
				myEinladungen.getCanStatus().setBackground(Color.RED);
			else
				myEinladungen.getCanStatus().setBackground(Color.GREEN);

		}
	}

	/**
	 * Fills the main frame with all information and updates data after the
	 * mouse has been clicked.
	 * 
	 * @param e
	 */
	public void befuelleMainFrame(MouseEvent e) {
		
		myHauptmenue.getBtnTerminBearbeiten().setText("Termin bearbeiten");
		JTable zwTab = (JTable) e.getSource();
		aktTermin = (String) myHauptmenue.getTable_1().getValueAt(
				zwTab.getSelectedRow(), 0);
		
		zwTab.getSelectedRow();
		String details = (String) myModel.returnDetail(aktTermin);
		myHauptmenue.getTextArea().setText(details);
		
		List<User> myList = new ArrayList<User>();

		myList = myModel.returnEingeladene(aktTermin);

		if (myList != null) {
			myHauptmenue.getListModel().removeAllElements();

			for (User element : myList) {
				System.out.println(element);
				myHauptmenue.getListModel().addElement(
						element.getGivenName() + " " + element.getSurname());
				myHauptmenue.repaint();
			}
		} else {
			myHauptmenue.getListModel().removeAllElements();
		}
		
		myHauptmenue.getTxtRaum().setText(myModel.returnRaum(aktTermin));

	}

	/**
	 * Method to handle cancel option. Returns to the previously opened window
	 * through setting the current window invisible and the prior window
	 * visible.
	 * 
	 * @param aktframe
	 * @param vorframe
	 */
	public void abbrechen(JFrame aktframe, JFrame vorframe) {
		aktframe.setVisible(false);
		vorframe.setVisible(true);
	}

	/**
	 * Fills the Model with new data and generates new table data.
	 */
	public void updateData() {

		try {
			System.out.println("Datum: " + aktDate);
			befuelleModel();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		erstelleTabellenDaten();

	}

	/**
	 * Saves a new created room and submits it to the database.
	 */
	public void speichereRaum() {
		myNeuerRaum.pruefeFelder();
		Room tmpRaum = new Room();
		String name = myNeuerRaum.getTxtBeschreibung().getText();
		String lage = myNeuerRaum.getTxtALage().getText();
		int sitze = Integer.parseInt(myNeuerRaum.getTxtSitze().getText());

		tmpRaum.setDescription(name);
		tmpRaum.setLocation(lage);
		tmpRaum.setSeats(sitze);

		Room erstRaum = myRequester.addObject(tmpRaum);
		if (erstRaum != null) {
			editEntry.getTxtRaum().setText(
					erstRaum.getLocation() + "; " + erstRaum.getDescription());
			editEntry.stateChangedForRoom(true, erstRaum);
			myNeuerRaum.setVisible(false);
			updateData();
			editEntry.getLstRaum().setListData(pushRoomList());
			neuCalEnt.setRoomId(erstRaum.getID());
			// editEntry.repaint();
			editEntry.setVisible(true);
		}
		// JOptionPane
		else
			System.out.println("Ihr Raum konnte nicht hinzugefügt werden");

	}

	/**
	 * Saves a new created calendar entry and submits it to the database.
	 */
	public void speichereTermin() {
		neuCalEnt = new CalendarEntry();
		
		boolean pruefefeld = 	editEntry.pruefeFelder();

		if (pruefefeld == false) {
			if (editEntry.getSelectedRoom().getID() == -1) {
				myNeuerRaum.setVisible(true);
				return;
			} else {
				neuCalEnt.setRoomId(editEntry.getSelectedRoom().getID());
			}

			String startzeit = editEntry.getStartUhrzeit().getText();
			String endzeit = editEntry.getEndUhrzeit().getText();
			
			Date startDate = parseStringtoDate(startzeit);
			Date endDate = parseStringtoDate(endzeit);

			neuCalEnt.setCalendarId(aktUserCa.getID());
			neuCalEnt.setDescription(editEntry.getTxtADetails().getText());

			neuCalEnt.setEndDate(endDate);
			neuCalEnt.setOwnerId(aktUser.getID());

			neuCalEnt.setStartDate(startDate);
			neuCalEnt.setTitle(editEntry.getBeschreibung().getText());

			boolean status = true;
			CalendarEntry obj = new CalendarEntry();
			int id = 0;

				boolean hasid = myModel.getKalendarentries().containsKey(startzeit);
				if (hasid == true) {
					id = myModel.returnKalendarentryId(startzeit);
					neuCalEnt.setID(id);
					JOptionPane.showMessageDialog(editEntry,"Der Eintrag besteht bereits. Wollen Sie den bestehenden Termin ändern?", "Termin bereits vorhanden", JOptionPane.OK_CANCEL_OPTION);
					
					status = myRequester.updateObject(neuCalEnt);
				} else {
					obj = myRequester.addObject(neuCalEnt);
				}


			if (obj == null || status == false) {
				if (obj == null)

					JOptionPane.showMessageDialog(editEntry,
							"Termin konnte nicht eingetragen werden",
							"Termin konnte nicht eingetragen werden",
							JOptionPane.INFORMATION_MESSAGE);

				if (status == false)
					JOptionPane.showMessageDialog(editEntry,
							"Termin konnte nicht geupdatet werden",
							"Termin konnte nicht geupdatet werden",
							JOptionPane.INFORMATION_MESSAGE);
			} else {

				JOptionPane.showMessageDialog(editEntry,
						"Termin wurde eingetragen", "Termin wurde eingetragen",
						JOptionPane.INFORMATION_MESSAGE);
			}

			/*
			 * removen von Invites ?? abgleich der Listen... vorher prüfen ob
			 * tatsächlich der alte Termin genutzt wird
			 */
			// Wurde geupdatet?
			
			if (status==true) 				
				versendeEinladungen(id);
			 else 
				versendeEinladungen(obj.getID());
			
			editEntry.setVisible(false);

			updateData();
			myHauptmenue.repaint();
		} else {
			JOptionPane.showMessageDialog(editEntry,
					"Termin konnte nicht eingetragen werden",
					"Termin konnte nicht eingetragen werden",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Submits the invitation to the server.
	 * @param id
	 */
	public void versendeEinladungen(int id)
	{
		Invite in = new Invite();
	
		for (User user : editEntry.getSelectedUsers()) {
			in.setCalendarEntryId(id);
			in.setOwnerId(user.getID());
			Invite vorhanden=myRequester.requestObjectByOwnId(in);
			if(vorhanden==null)
			{
			Invite tmpin = myRequester.addObject(in);
			if (tmpin == null) {
				JOptionPane.showMessageDialog(editEntry, "Die Einladungen konnten nicht versandt werden!");
			}
			}
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
				System.out.println(delCe.getID());
				boolean geloescht = myRequester.removeObjectByOwnId(delCe);
				System.out.println(geloescht);
				if (geloescht == false)
					JOptionPane
							.showMessageDialog(
									myHauptmenue,
									"Der Termin konnte nicht gelöscht werden! Bitte versuchen Sie es zu einem späteren Zeitpunkt erneut");

				updateData();
			}
		} else
			JOptionPane.showMessageDialog(myHauptmenue,
					"Bitte wählen Sie einen Termin aus!");
	}

	/**
	 * Connects the Server and the Model with setting the current Date of the
	 * Model and afterwards updating the Model data.
	 * 
	 */
	public void connectServerModel() {
		myModel.setAktDate(aktDate);

		updateData();
		myHauptmenue.setVisible(true);

	}

	/**
	 * Proofs if the server settings are complete or if fields are missing.
	 * Creates a connection to the server over a new Requester with valid port
	 * and host data.
	 * 
	 * @return 1 for sucessfull connection and 0 for unsucessful connection
	 */
	public int pruefeServereinstellungen() {

		if (myServereinstellungen.getTxtPort().getText().isEmpty()
				|| myServereinstellungen.getTxtAdresse().getText().isEmpty()) {
			JOptionPane.showMessageDialog(myLogScreen,
					"Bitte wählen Sie gültige Servereinstellungen",
					"Ungültige Servereinstellungen",
					JOptionPane.INFORMATION_MESSAGE);
			myHauptmenue.setVisible(false);
			myServereinstellungen.setVisible(true);
			return 0;
		} else {
			port = Integer.parseInt(myServereinstellungen.getTxtPort()
					.getText());
			adresse = myServereinstellungen.getTxtAdresse().getText();
			myRequester = new JsonJavaRequestHandler(adresse, port);
			return 1;
		}
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
	 * user. If this was sucessful a user ID is returned (not visible for user),
	 * otherwise a window will open and announce that the registration was not
	 * sucessful. Furthermore the calendar for the user is created after
	 * sucessful adding of the user.
	 */
	public void registriereUser() {

		char[] passwort1 = myRegistration.getTxtPasswort().getPassword();
		char[] passwort2 = myRegistration.getTxtPasswortBest().getPassword();

		String email = myRegistration.getTxtEmailadresse().getText();
		String nachname = myRegistration.getTxtNachname().getText();
		String vorname = myRegistration.getTxtVorname().getText();
		String telefonnr = myRegistration.getTxtTelefon().getText();

		if (!email.isEmpty() && !nachname.isEmpty() && !vorname.isEmpty()
				&& passwort1.length != 0 && passwort2.length != 0) {

			String pwd1 = new String(passwort1);
			String pwd2 = new String(passwort2);

			if (pwd1.equals(pwd2)) {

				passwort = pwd1;
				if (myRequester == null) {
					pruefeServereinstellungen();
				}
				aktUser.setMailAddress(email);
				aktUser.setSurname(nachname);
				aktUser.setGivenname(vorname);
				if (!telefonnr.isEmpty())
					aktUser.setPhoneNumber(telefonnr);

				User utmp = myRequester.registerNewUser(aktUser, passwort);

				if (utmp == null) {

					JOptionPane.showMessageDialog(myRegistration,"Sie konnten nicht registriert werden. Bitte versuchen Sie es später erneut!");


				} else {

					aktUser = utmp;
					System.out.println("User ID: " + aktUser.getID());
					aktUserCa = new organizer.objects.types.Calendar();
					aktUserCa.setOwnerId(aktUser.getID());
					aktUserCa.setDescription("persönlicher Kalendar von "
							+ aktUser.getGivenName());
					aktUserCa.setName("Kalendar von " + aktUser.getGivenName());
					myRequester.login(aktUser.getMailAddress(), passwort);
					Object tmp = myRequester.addObject(aktUserCa);

					if (tmp == null) {
						// Fenster
						System.out
								.println("Es konnte kein Kalendar hinzugefügt werden");

					} else {
						aktUserCa = (organizer.objects.types.Calendar) tmp;
						List<Integer> l = aktUser.getCalendarIds();
						l.add(aktUserCa.getID());
						aktUser.setCalendarIds(l);

					}
				}
				myRegistration.loescheInhalte();
				myRegistration.setVisible(false);
				myLogScreen.setVisible(true);

			} else {
				JOptionPane.showMessageDialog(myLogScreen,
						"Das Passwort stimmt nicht überein!",
						"Passwort falsch", JOptionPane.INFORMATION_MESSAGE);
				myRegistration.getTxtPasswort().setText("");
				myRegistration.getTxtPasswortBest().setText("");
				return;
			}
		} else {

			myRegistration.pruefeVollständigkeit();
		}

	}

	/**
	 * Saves entered server settings. Per default the settings "Serveradresse"
	 * as "localhost" and port as 48585 are implemented. If the user changes
	 * these settings the new settings will be saved and a new Requester will be
	 * created to access the server via the networklayer.
	 */
	public void speichereServereinstellungen() {
		if (myServereinstellungen.getTxtPort().getText().equals("")
				|| myServereinstellungen.getTxtAdresse().getText().equals("")) {

			JOptionPane.showMessageDialog(null,
					"Bitte wählen Sie gültige Servereinstellungen",
					"Ungültige Servereinstellungen",
					JOptionPane.INFORMATION_MESSAGE);
			myServereinstellungen.setVisible(true);
		} else {

			port = Integer.parseInt(myServereinstellungen.getTxtPort()
					.getText());
			adresse = myServereinstellungen.getTxtAdresse().getText();
			myServereinstellungen.setVisible(false);
			myRequester = new JsonJavaRequestHandler(adresse, port);

		}
	}

	/**
	 * Creates a new window to edit an entry with the right settings. If there
	 * is no current entry chosen (a table row selected) the window will be
	 * opened without any attributes as an empty window. Otherwise the window is
	 * opened with the attributes to this entry that are stored in the model.
	 */
	public void bearbeiteTermin() {
		editEntry = new TerminBearbeiten(this, this, this);
		aktDate = myHauptmenue.getAktDateCali();
		String startZeit = aktTermin;
		boolean containsTermin = myModel.getKalendarentries().containsKey(
				aktTermin);
		if (aktTermin == null || containsTermin == false) {
			editEntry.setButtonText("Erstellen");

			if (containsTermin == false)
				editEntry.openFrameWithValues(startZeit,"", "", "", null,
						null, "");
			else
				editEntry.openEmptyFrame();
		} else {

			editEntry.setButtonText("Termin Speichern");
			String details = myModel.returnDetail(aktTermin);
			String endZeit = myModel.returnEndzeit(aktTermin);
			String beschreibung = myModel.returnBeschreibung(aktTermin);
			String raum = myModel.returnRaum(aktTermin);
			Room[] raeume = myModel.getAlleRaeume();
			User[] allePersonen = myModel.getAllePersonen();
			System.out.println(allePersonen[0]);
			editEntry.openFrameWithValues(startZeit, endZeit, beschreibung,
					details, raeume, allePersonen, raum);

		}
	}

	/**
	 * Logs in a user through collecting the entered email ID and the password.
	 * Requests through the networklayer to log in this user on the server and
	 * requests the calendar registered to this ID. If the request was successful
	 * the users calendar is requested and saved local. Otherwise an error
	 * message is opened.
	 */
	public void meldeUserAn() {

		if (pruefeServereinstellungen() == 0) {
			return;
		}

		benutzername = myLogScreen.getTextField().getText();
		char[] tmppasswort = myLogScreen.getPasswort().getPassword();

		passwort = "";
		for (int i = 0; i < tmppasswort.length; i++) {
			passwort = passwort + tmppasswort[i];
		}

		if (benutzername.equals("") || passwort.equals("")) {
			JOptionPane
					.showMessageDialog(
							myLogScreen,
							"Bitte geben Sie einen gültigen Benutzernamen und ein gültiges Passwort ein",
							"Benutzername oder Passwort falsch",
							JOptionPane.INFORMATION_MESSAGE);
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
									myLogScreen,
									"Es ist noch kein Kalendar für Sie erstellt worden",
									"Verbindungsfehler",
									JOptionPane.INFORMATION_MESSAGE);
				}

				Calendar tmpCal = myRequester.requestObjectByOwnId(aktUserCa);

				// // null Abfrage
				if (tmpCal != null) {
					aktUserCa = tmpCal;
					connectServerModel();

				} else {
					JOptionPane
							.showMessageDialog(
									myLogScreen,
									"Es konnte keine Verbindung zum Server hergestellt werden, bitte starten Sie das Programm neu",
									"Verbindungsfehler",
									JOptionPane.INFORMATION_MESSAGE);

				}
				//
				int myEinl = myModel.getEinladungen().size();
				myHauptmenue.getLblAnzahlEinladungen().setText(
						Integer.toString(myEinl));
				myHauptmenue.getLblAnzahlEinladungen().setForeground(Color.RED);
				myLogScreen.setVisible(false);
				myHauptmenue.setVisible(true);
			} else {
				JOptionPane
						.showMessageDialog(
								myLogScreen,
								"Leider sind Sie noch nicht registriert. Bitte Registrieren Sie sich um die MyOrganizer Funktionen nutzen zu können.",
								"User nicht vorhanden",
								JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}
}
