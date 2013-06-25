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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.border.Border;

import logik.DataPusher;
import logik.Model;
import network.json.JsonJavaRequestHandler;
import network.RequestHandler;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

import view.windowEinladungen;
import view.windowNeuerRaum;
import view.window_Hauptmenue;
import view.window_LogScreen;
import view.window_RegisterUser;
import view.window_Servereinstellungen;
import view.window_TerminBearbeiten;

public class Controller implements DataPusher, ActionListener, MouseListener,
		PropertyChangeListener {

	/**
	 * @param args
	 * 
	 */

	private Model myModel;

	private Object[][] tabellenDaten;
	private Object[][] terminDauer;

	private window_TerminBearbeiten editEntry;

	private window_Hauptmenue myHauptmenue;
	private window_LogScreen myLogScreen;
	private window_Servereinstellungen myServereinstellungen;
	private window_RegisterUser myRegistration;
	private windowNeuerRaum myNeuerRaum;
	private windowEinladungen myEinladungen;

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

	/*
	 * XXX Gleich mal ein Post am Anfang hier: Es ist sehr schwer deinen Code
	 * nachzuvollziehen. Selbst Kommentare helfen da nur teilweise. An einigen
	 * Stellen machst du Dinge doppelt, anstatt daf�r eine Methode
	 * bereitzustellen. Das solltest du unbedingt �ndern. Alleine schon wegen
	 * der Wartbarkeit. Passt man etwas an einer Stelle, kann der Fehler in
	 * deinem Quellcode an anderer Stelle immer noch da sein.
	 * 
	 * Des Weiteren solltest du dir was Fenster angeht einen anderen Stil
	 * angew�hnen. Das Fenster, dass du brauchst, wird erst erzeugt, wenn du es
	 * brauchst. Dadurch umgehst du zum einen das Problem mit dem Datenrefresh
	 * und zum anderen wird der Speicher nicht so voll gehauen. Das bedeutet,
	 * was unten im Konstruktor steht, solltest du dringend �ndern. (Bei dem
	 * ganzen setVisible(true) und setVisible(false) wei� man gar nicht, wo man
	 * suchen soll) Eine weitere interessante Sache k�nnten JDialogs als
	 * Vaterklasse sein anstatt JFrames. Dadurch wird das Fenster im Hintergrund
	 * blockiert (solange nicht anders gesagt). Du w�rdest dein Hauptmenue also
	 * weiterhin als JFrame haben und den Rest nur als JDialog - w�re mein
	 * Vorschlag.
	 * 
	 * Au�erdem werden Java-Klassen immer gro�geschrieben und haben keine '_'.
	 * Das solltest du bei deinen Views �ndern. Du kannst sie ja WindowSomeName
	 * nennen.
	 */

	public Controller() {

		myModel = new Model(aktDate);
		updateData();
		myHauptmenue = new window_Hauptmenue(this, this, this, this);
		myHauptmenue.setVisible(false);
		myLogScreen = new window_LogScreen(this);
		myLogScreen.setVisible(true);
		myRegistration = new window_RegisterUser(this);
		myRegistration.setVisible(false);
		myServereinstellungen = new window_Servereinstellungen(this);
		myServereinstellungen.setVisible(false);
		myNeuerRaum = new windowNeuerRaum(this);
		myNeuerRaum.setVisible(false);
		myEinladungen = new windowEinladungen(this);
		myEinladungen.setVisible(false);
		aktUser = new User();
		aktUserCa = new organizer.objects.types.Calendar();

	}

	public static void main(String[] args) {
		new Controller();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == myLogScreen.getMntmServerkonfigurationen()) {
			myServereinstellungen.setVisible(true);
		}

		/*
		 * XXX wenn du auf leere Strings untersuchen willst, solltest du eher
		 * String toCheck = ""; if(toCheck.isEmpty()){ ...wenn der String leer
		 * ist... } nutzen.
		 */
		if (e.getSource() == myServereinstellungen.getBtnSpeichern()) {
			speichereServereinstellungen();

		}
		if (e.getSource() == myHauptmenue.getBtnTerminBearbeiten()) {
			bearbeiteTermin();

		}
		if (e.getSource() == myHauptmenue.getBtnAbmelden()) {
			myHauptmenue.dispose();
		}
		if (e.getSource() == myHauptmenue.getBtnTerminEntfernen()) {
			entferneTermin();
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

		if (editEntry != null
				&& e.getSource().equals(editEntry.getBtnTerminEintragen())) {
			// �berpr�fung auf richtiges Format!!
			speichereTermin();
		}

		if (e.getSource() == myNeuerRaum.getBtnSpeichern()) {
			speichereRaum();
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

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		// System.out.println(e.getPropertyName());
		if (e.getOldValue() == null && e.getNewValue() != null) {
			//
			aktDate = new Date();

		}
		if (e.getOldValue() != null) {
			aktDate = myHauptmenue.getAktDateCali();
			updateData();
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
			// List<Invite> invites = new ArrayList<Invite>();

			// for (int id : inviteIds) {
			// Invite request = new Invite();
			// request.setID(id);
			// Invite tmp = myRequester.requestObjectByOwnId(request);
			// if (tmp == null) {
			// // ERROR
			// } else {
			// invites.add(tmp);
			// }
			// }
			// myModel.setEinladungen(invites);

			// List<Invite> li= myRequester.requestAllObjects(inv);
			// myModel.setEinladungen(li);

			aktUserCa = myRequester.requestObjectByOwnId(aktUserCa);

			List<CalendarEntry> myCes = aktUserCa.getCalendarEntries();
			List<User> eingeladene = new ArrayList();
			for (CalendarEntry myCe : myCes) {

				if (parseDatetoString(myCe.getStartDate()).equals(
						parseDatetoString(myHauptmenue.getCali().getDate()))) {
					// Methode?
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					String anfangZeit = format.format(myCe.getStartDate());
					String endZeit = format.format(myCe.getEndDate());
					myModel.setAktDate(aktDate);
					myModel.setBeschreibungen(anfangZeit, myCe.getTitle());
					myModel.setDauer(anfangZeit, myCe.getDuration());
					 myModel.setPersonen(anfangZeit, myCe.getInvitees());
					myModel.setKalendarentries(anfangZeit, myCe.getID());

					List<Invite> invites = myRequester
							.requestFollowingObjectsByOwnId(
									myCe.getInviteIds(), new Invite());
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
								// Fehler bei der Abfrage, sollte nicht
								// vorkommen...
							}
						}
					}
					myModel.setPersonen(anfangZeit, eingeladene);

					// for (User u : myCe.getInvitees()) {
					// System.out.println("Eingeladen im Model: "
					// + u.getGivenName());
					// }
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

	public void bearbeiteEinladung() {
		myRequester.acceptInvite(aktin);
		updateData();
		myEinladungen.setVisible(false);
	}

	public void befuelleEinladungen() {
		List<Invite> einl = myModel.getEinladungen();
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

	public void befuelleMainFrame(MouseEvent e) {
		/*
		 * Details f�r Termin werden eingef�llt leere Details werden in myModel
		 * umgangen (Alternativtext)
		 */
		myHauptmenue.getBtnTerminBearbeiten().setText("Termin bearbeiten");
		JTable zwTab = (JTable) e.getSource();
		aktTermin = (String) myHauptmenue.getTable_1().getValueAt(
				zwTab.getSelectedRow(), 0);
		zwTab.getSelectedRow();
		String details = (String) myModel.returnDetail(aktTermin);
		myHauptmenue.getTextArea().setText(details);
		/*
		 * Terminteilnehmer werden in die JList eingef�gt
		 */
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
		/*
		 * der Raum zu dem Termin wird eingef�gt
		 */
		myHauptmenue.getTxtRaum().setText(myModel.returnRaum(aktTermin));

	}

	/**
	 * Fills the Model with new data and generates new table data.
	 */
	public void updateData() {

		try {
			befuelleModel();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		erstelleTabellenDaten();

	}

	/**
	 * Saves a new created room and submitts it to the database.
	 */
	public void speichereRaum() {
		// �berpr�fung ob Vollst�ndig!
		Room tmpRaum = new Room();
		String name = myNeuerRaum.getTxtBeschreibung().getText();
		String lage = myNeuerRaum.getTxtALage().getText();
		int sitze = Integer.parseInt(myNeuerRaum.getTxtSitze().getText());

		tmpRaum.setDescription(name);
		tmpRaum.setLocation(lage);
		tmpRaum.setSeats(sitze);

		Room erstRaum = myRequester.addObject(tmpRaum);
		if (erstRaum != null) {
			editEntry.getTxtRaum().setText(erstRaum.getDescription());
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
			System.out.println("Ihr Raum konnte nicht hinzugef�gt werden");

	}

	/**
	 * Saves a new created calendar entry and submitts it to the database.
	 */
	public void speichereTermin() {
		neuCalEnt = new CalendarEntry();

		String startzeit = editEntry.getStartUhrzeit().getText();
		String endzeit = editEntry.getEndUhrzeit().getText();

		Date startDate = parseStringtoDate(startzeit);
		Date endDate = parseStringtoDate(endzeit);

		neuCalEnt.setCalendarId(aktUserCa.getID());
		neuCalEnt.setDescription(editEntry.getTxtADetails().getText());
		// neuCalEnt.setInvitees(editEntry.getSelectedUsers());

		neuCalEnt.setEndDate(endDate);
		neuCalEnt.setOwnerId(aktUser.getID());

		// String raum = editEntry.getTxtRaum().getText();

		if (editEntry.getSelectedRoom().getID() == -1) {
			// �ffne Fenster neuer Raum eingeben
			myNeuerRaum.setVisible(true);
			return;

		} else {
			neuCalEnt.setRoomId(editEntry.getSelectedRoom().getID());
		}

		neuCalEnt.setStartDate(startDate);
		neuCalEnt.setTitle(editEntry.getBeschreibung().getText());
		boolean status = false;
		CalendarEntry obj = new CalendarEntry();
		if (aktTermin != null) {
			neuCalEnt.setID(myModel.returnKalendarentryId(aktTermin));
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

			else
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
		 * removen von Invites ?? abgleich der Listen... vorher pr�fen ob
		 * tats�chlich der alte Termin genutzt wird
		 */
		Invite in = new Invite();
		for (User user : editEntry.getSelectedUsers()) {
			in.setCalendarEntryId(obj.getID());
			in.setOwnerId(user.getID());
			Invite tmpin = myRequester.addObject(in);
			if (tmpin == null) {
				System.out
						.println("Die Einladungen konnten nicht versandt werden");
			}
		}

		editEntry.setVisible(false);

		updateData();

	}

	/*
	 * Termin wird nicht gel�scht... mir ist nicht klar warum!!
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
									"Der Termin konnte nicht gel�scht werden! Bitte versuchen Sie es zu einem sp�teren Zeitpunkt erneut");

				updateData();
			}
		} else
			JOptionPane.showMessageDialog(myHauptmenue,
					"Bitte w�hlen Sie einen Termin aus!");
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
					"Bitte w�hlen Sie g�ltige Servereinstellungen",
					"Ung�ltige Servereinstellungen",
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

	@Override
	/**
	 * Pushes an array from type User with the data from the Model.
	 */
	public User[] pushUserList() {
		return myModel.getAllePersonen();
	}

	@Override
	/**
	 * Pushes an array from type Room with the data from the Model.
	 */
	public Room[] pushRoomList() {
		return myModel.getAlleRaeume();
	}

	public void registriereUser() {

		char[] passwort1 = myRegistration.getTxtPasswort().getPassword();
		char[] passwort2 = myRegistration.getTxtPasswortBest().getPassword();

		if (!myRegistration.getTxtEmailadresse().getText().isEmpty()
				&& !myRegistration.getTxtNachname().getText().isEmpty()
				&& !myRegistration.getTxtVorname().getText().isEmpty()
				&& passwort1.length != 0 && passwort2.length != 0) {

			String pwd1 = new String(passwort1);
			String pwd2 = new String(passwort2);

			if (pwd1.equals(pwd2)) {

				passwort = pwd1;
				if (myRequester == null) {
					pruefeServereinstellungen();
				}
				aktUser.setMailAddress(myRegistration.getTxtEmailadresse()
						.getText());
				aktUser.setSurname(myRegistration.getTxtNachname().getText());
				aktUser.setGivenname(myRegistration.getTxtVorname().getText());

				User utmp = myRequester.registerNewUser(aktUser, passwort);

				if (utmp == null) {
					// JOptionPane
					System.out.println("Konnte nicht registrieren");

				} else {

					aktUser = utmp;
					System.out.println("User ID: " + aktUser.getID());
					aktUserCa = new organizer.objects.types.Calendar();
					aktUserCa.setOwnerId(aktUser.getID());
					aktUserCa.setDescription("pers�nlicher Kalendar von "
							+ aktUser.getGivenName());
					aktUserCa.setName("Kalendar von " + aktUser.getGivenName());
					myRequester.login(aktUser.getMailAddress(), passwort);
					Object tmp = myRequester.addObject(aktUserCa);

					if (tmp == null) {
						// Fenster
						System.out
								.println("Es konnte kein Kalendar hinzugef�gt werden");

					} else {
						aktUserCa = (organizer.objects.types.Calendar) tmp;
						List<Integer> l = aktUser.getCalendarIds();
						l.add(aktUserCa.getID());
						aktUser.setCalendarIds(l);

					}
				}

				myRegistration.setVisible(false);
				myLogScreen.setVisible(true);

			} else {
				JOptionPane.showMessageDialog(myLogScreen,
						"Das Passwort stimmt nicht �berein!",
						"Passwort falsch", JOptionPane.INFORMATION_MESSAGE);
				myRegistration.getTxtPasswort().setText("");
				myRegistration.getTxtPasswortBest().setText("");
				return;
			}
		} else {

			myRegistration.pruefeVollst�ndigkeit();
		}

	}

	public void speichereServereinstellungen() {
		if (myServereinstellungen.getTxtPort().getText().equals("")
				|| myServereinstellungen.getTxtAdresse().getText().equals("")) {

			JOptionPane.showMessageDialog(null,
					"Bitte w�hlen Sie g�ltige Servereinstellungen",
					"Ung�ltige Servereinstellungen",
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

	public void bearbeiteTermin() {
		editEntry = new window_TerminBearbeiten(this, this);
		aktDate = myHauptmenue.getAktDateCali();
		String startZeit = aktTermin;
		boolean containsTermin=myModel.getKalendarentries().containsKey(aktTermin);
		if (aktTermin == null
				||containsTermin==false) {
			editEntry.setButtonText("Erstellen");
			
			if(containsTermin==false)
				editEntry.openFrameWithValues(startZeit, "", "",
						"", null, null,"");	
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

	public void meldeUserAn() {
		/*
		 * Authentifizierung mit Server SPeicherung der ID Daten in das Modell
		 * Speicherung der Daten in das Modell
		 */
		//

		if (pruefeServereinstellungen() == 0) {
			return;
		}

		benutzername = myLogScreen.getTextField().getText();
		char[] tmppasswort = myLogScreen.getPasswort().getPassword();
		/*
		 * XXX Vorschlag passwort = new String(tmppasswort); dann brauchst du
		 * keine for-Schleife --> wie weiter oben schon erw�hnt. (PS: schon
		 * wieder der gleiche Quellcode)
		 */
		passwort = "";
		for (int i = 0; i < tmppasswort.length; i++) {
			passwort = passwort + tmppasswort[i];
		}

		if (benutzername.equals("") || passwort.equals("")) {
			JOptionPane
					.showMessageDialog(
							myLogScreen,
							"Bitte geben Sie einen g�ltigen Benutzernamen und ein g�ltiges Passwort ein",
							"Benutzername oder Passwort falsch",
							JOptionPane.INFORMATION_MESSAGE);
		} else {
			// new Calendar();
			// /*
			// * Abfrage der Id f�r den Benutzernamen
			// */
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
									"Es ist noch kein Kalendar f�r Sie erstellt worden",
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
								"Leider sind Sie noch nicht registriert. Bitte Registrieren Sie sich um die MyOrganizer Funktionen nutzen zu k�nnen.",
								"User nicht vorhanden",
								JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}
}
