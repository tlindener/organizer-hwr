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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import logik.DataPusher;
import logik.Model;
import network.JsonJavaRequestHandler;
import network.RequestHandler;

import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Room;
import organizer.objects.types.User;

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

	private Object[][] beschreibungsDaten;
	private Object[][] terminDauer;

	private window_TerminBearbeiten editEntry;

	private window_Hauptmenue myHauptmenue;
	private window_LogScreen myLogScreen;
	private window_Servereinstellungen myServereinstellungen;
	private window_RegisterUser myRegistration;

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

	/*
	 * XXX Gleich mal ein Post am Anfang hier: Es ist sehr schwer deinen Code
	 * nachzuvollziehen. Selbst Kommentare helfen da nur teilweise. An einigen
	 * Stellen machst du Dinge doppelt, anstatt dafür eine Methode
	 * bereitzustellen. Das solltest du unbedingt ändern. Alleine schon wegen
	 * der Wartbarkeit. Passt man etwas an einer Stelle, kann der Fehler in
	 * deinem Quellcode an anderer Stelle immer noch da sein.
	 * 
	 * Des Weiteren solltest du dir was Fenster angeht einen anderen Stil
	 * angewöhnen. Das Fenster, dass du brauchst, wird erst erzeugt, wenn du es
	 * brauchst. Dadurch umgehst du zum einen das Problem mit dem Datenrefresh
	 * und zum anderen wird der Speicher nicht so voll gehauen. Das bedeutet,
	 * was unten im Konstruktor steht, solltest du dringend ändern. (Bei dem
	 * ganzen setVisible(true) und setVisible(false) weiß man gar nicht, wo man
	 * suchen soll) Eine weitere interessante Sache könnten JDialogs als
	 * Vaterklasse sein anstatt JFrames. Dadurch wird das Fenster im Hintergrund
	 * blockiert (solange nicht anders gesagt). Du würdest dein Hauptmenue also
	 * weiterhin als JFrame haben und den Rest nur als JDialog - wäre mein
	 * Vorschlag.
	 * 
	 * Außerdem werden Java-Klassen immer großgeschrieben und haben keine '_'.
	 * Das solltest du bei deinen Views ändern. Du kannst sie ja WindowSomeName
	 * nennen.
	 */

	public Controller() {
		/**
		 * Wenn Logscreen geöffnet wird müssen die VerbindungsDaten übermittelt
		 * werden (String--> Hostname, Int--> Port)
		 * 
		 */
		// myRequester = new JsonJavaRequestHandler("localhost", 48585);
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
		aktUser = new User();
		aktUserCa = new organizer.objects.types.Calendar();

	}

	public static void main(String[] args) {
		new Controller();
	}

	public void getKalenderEntries() {
		/*
		 * hier wird ein Kalender einer bestimmten ID abgefragt und die
		 * einzelnen Listen werden an das Modell übergeben sollte jedes Mal
		 * aufgerufen werden wenn Cali verändert wird
		 */
		// myRequester.requestObjectByOwnId(obj)

	}

	/*
	 * DO= DatenObjekt
	 * 
	 * @me: vllt eher in Model???
	 */
	public void erstelleDOBeschreibungen() {
		beschreibungsDaten = new Object[1440][2];
		int myTimeHour = 0;
		int myTimeMinute = 0;
		String myTime = null;
		int i = 0;
		/*
		 * Generierung aller Uhrzeiten
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

			if (myModel.returnBeschreibung(myTime) != null) {
				beschreibungsDaten[i][0] = myTime;
				beschreibungsDaten[i][1] = myModel.returnBeschreibung(myTime);

			} else if (myTimeMinute == 1 || myTimeMinute == 31) {

				beschreibungsDaten[i][0] = myTime;
				beschreibungsDaten[i][1] = "";
			}

			if (i < 1439)
				i = i + 1;
		}

		konvertiereBeschreibungsDaten();
	}

	public Object getDauer(String zeit) {
		if (myModel.returnDauer(zeit) != null) {
			return myModel.returnDauer(zeit);
		} else
			return null;

	}

	@Override
	public Object[][] getBeschreibungen() {
		return beschreibungsDaten;
	}

	public Object[][] konvertiereBeschreibungsDaten() {
		Object[][] beschreibungsDatenKonv = new Object[48 * 2][3];
		int j = 0;
		for (int i = 0; i < 1439; i++) {
			if (beschreibungsDaten[i][1] != null) {

				beschreibungsDatenKonv[j][0] = beschreibungsDaten[i][0];
				beschreibungsDatenKonv[j][2] = beschreibungsDaten[i][1];
				if (j < 48) {
					j++;
				}
			}

		}
		beschreibungsDaten = beschreibungsDatenKonv;
		return beschreibungsDaten;
	}

	/*
	 * Du solltest wirklich beginnen, für jede "Source" eine Methode zu
	 * schreiben, um die actionPerformed-Methode zu entlasten. Die Methoden
	 * sollten dann entsprechende Namen haben.
	 */

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
			/*
			 * Über Model Verbindung zum Server Übergabe des Termines Löschen
			 * des Termines durch Netzwerklayer
			 */
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
			// Überprüfung auf richtiges Format!!
			speichereTermin();

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource() == myHauptmenue.getTable_1()) {
			/*
			 * Details für Termin werden eingefüllt leere Details werden in
			 * myModel umgangen (Alternativtext)
			 */
			myHauptmenue.getBtnTerminBearbeiten().setText("Termin bearbeiten");
			JTable zwTab = (JTable) e.getSource();
			aktTermin = (String) myHauptmenue.getTable_1().getValueAt(
					zwTab.getSelectedRow(), 0);
			zwTab.getSelectedRow();
			String details = (String) myModel
					.returnDetail((String) myHauptmenue.getTable_1()
							.getValueAt(zwTab.getSelectedRow(), 0));
			myHauptmenue.getTextArea().setText(details);
			/*
			 * Terminteilnehmer werden in die JList eingefügt
			 */
			List<String> myList = new ArrayList<String>();

			myList = myModel.returnEingeladene((String) myHauptmenue
					.getTable_1().getValueAt(zwTab.getSelectedRow(), 0));
			if (myList != null) {
				myHauptmenue.getListModel().removeAllElements();
				/*
				 * XXX Du deckst hier mal wieder nicht das Element an der Stelle
				 * 0 ab... Du brauchst auch kein i - also solltest du die
				 * for-each-Schleife nutzen
				 * 
				 * for(String element: myList){
				 * myHauptmenue.getListModel().addElement(element); }
				 */
				for (int i = myList.size() - 1; i > 0; i--) {

					myHauptmenue.getListModel().addElement(myList.get(i));
				}
			} else {
				myHauptmenue.getListModel().removeAllElements();
			}
			/*
			 * der Raum zu dem Termin wird eingefügt
			 */
			myHauptmenue.getTextField().setText(
					myModel.returnRaum((String) myHauptmenue.getTable_1()
							.getValueAt(zwTab.getSelectedRow(), 0)));

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

			try {
				befuelleModel();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			connectServerModel();
			System.out.println(myModel.returnBeschreibung("5:30"));
		}

	}

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

			List<CalendarEntry> myCes = aktUserCa.getCalendarEntries();
			

			for (CalendarEntry myCe : myCes) {

				if (parseDate(myCe.getStartDate()).equals(
						parseDate(myHauptmenue.getCali().getDate()))) {
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");
					System.out.println("hier");
					String anfangZeit = format.format(myCe.getStartDate());
					String endZeit = format.format(myCe.getEndDate());
					myModel.setAktDate(aktDate);
					myModel.setBeschreibungen(anfangZeit, myCe.getTitle());
					myModel.setDauer(anfangZeit, myCe.getDuration());
					myModel.setPersonen(anfangZeit, myCe.getInvitees());
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

	public String parseDate(Date date) {
		String datum = "";
		datum = date.toString().substring(0, 11)
				+ date.toString().substring(20);
		return datum;
	}

	public Date parseStringtoDate(String zeit) {
		/*
		 * XXX Was machst du hier genau? Habe es getestet und bin verwundert,
		 * dass das klappt. Aber gut =)
		 * 
		 * PS: ich nutze einen ISO Standard um mit Tobi zu kommunizieren, das
		 * Pattern dafür sieht so aus "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" Ich
		 * Schätze, du müsstest nur das HH:mm mit 'zeit' ersetzen.
		 */

		Date date = aktDate;
		String datetimeStr = date.toString();
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd " + zeit
				+ ":ss zzz yyyy");

		datetimeStr = format.format(date);
		System.out.println("DatetimeStr: "+datetimeStr);
		try {
			format= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			date = format.parse(datetimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/*
	 * XXX Diese Methode ist ziemlich unnütz, da sie nur eine andere aufruft und
	 * selber nichts macht. Vielleicht solltest du überlegen, hier den anderen
	 * Aufruf mit
	 * 
	 * try { befuelleModel(); } catch (ParseException e1) {
	 * e1.printStackTrace(); }
	 * 
	 * auch reinzunehmen.
	 */

	public void updateData() {

		try {
			befuelleModel();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		erstelleDOBeschreibungen();

	}

	public void speichereTermin() {
		CalendarEntry neuCalEnt = new CalendarEntry();
		String startzeit = editEntry.getStartUhrzeit().getText();
		String endzeit = editEntry.getEndUhrzeit().getText();

		Date startDate = parseStringtoDate(startzeit);
		Date endDate = parseStringtoDate(endzeit);
	
		neuCalEnt.setCalendarId(aktUserCa.getID());
		neuCalEnt.setDescription(editEntry.getTxtADetails().getText());
		// neuCalEnt.setInvitees(invitees);
		neuCalEnt.setEndDate(endDate);
		neuCalEnt.setOwnerId(aktUser.getID());
		neuCalEnt.setRoomId(editEntry.getSelectedRoom().getID());
		neuCalEnt.setStartDate(startDate);
		neuCalEnt.setTitle(editEntry.getBeschreibung().getText());

		Object obj = myRequester.addObject(neuCalEnt);
		;
		if (obj == null) {
			JOptionPane.showMessageDialog(editEntry,
					"Termin konnte nicht eingetragen werden",
					"Termin konnte nicht eingetragen werden",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(editEntry,
					"Termin wurde eingetragen", "Termin wurde eingetragen",
					JOptionPane.INFORMATION_MESSAGE);
		}
		editEntry.setVisible(false);

		/*
		 * Ich schätze, dass hast du auskommentiert Hier sollte eigentlich dein
		 * Model neu befüllt werden? Das sollte passieren, damit was zu sehen
		 * ist. Mit dem Hinweis im window_Hauptmenue zum TableModel sollte das
		 * dann funktionieren.
		 */
		updateData();
		
	}

	/*
	 * fehlerhaft!!!
	 */
	public void setDauer() {
		for (int i = myHauptmenue.getTable_1().getRowCount() - 1; i > 0; i--) {
			if (myHauptmenue.getTable_1().getValueAt(i, 0) != null) {
				String zeit = (String) myHauptmenue.getTable_1().getValueAt(i,
						0);
				if (getDauer(zeit) != null) {
					myHauptmenue.getTable_1().setValueAt(
							myModel.returnEndzeit(zeit), i, 1);

				}
			}

		}
	}

	public void connectServerModel() {
		myModel.setAktDate(aktDate);
		/*
		 * Hier machst du den Aufruf schon wieder, nur andersrum - ändert das
		 * was?
		 */

		updateData();
		myHauptmenue.setVisible(true);
		myHauptmenue.repaint();

		setDauer();
	}

	public int pruefeServereinstellungen() {
		/*
		 * XXX nicht mit den GUI-Elementen arbeiten sondern mit deren Werten.
		 * leere String wieder mit isEmpty() abfragen.
		 */

		if (myServereinstellungen.getTxtPort().getText().equals("")
				|| myServereinstellungen.getTxtAdresse().getText().equals("")) {
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

	@Override
	public User[] pushUserList() {
		return myModel.getAllePersonen();
	}

	@Override
	public Room[] pushRoomList() {
		return myModel.getAlleRaeume();
	}

	public void registriereUser() {

		/*
		 * XXX Du kannst einen String aus einem char[] erzeugen, indem du es
		 * einfach mit übergibst:
		 * 
		 * char[] passwort1 = myRegistration.getTxtPasswort().getPassword();
		 * String pwd1 = new String(passwort1);
		 * 
		 * Außerdem kannst du einfach zwei Arrays miteinander vergleichen Dazu
		 * brauchst du nicht mal Strings:
		 * 
		 * if(passwort1.equals(passwort2)){ ...same here... }
		 * 
		 * Damit brauchst du nicht erst die beiden for-Schleifen...(die nebenbei
		 * gesagt auch wieder redundant sind.
		 */

		char[] passwort1 = myRegistration.getTxtPasswort().getPassword();
		char[] passwort2 = myRegistration.getTxtPasswortBest().getPassword();
		String pwd1 = "";
		String pwd2 = "";
		for (int i = 0; i < passwort1.length; i++) {
			pwd1 = pwd1 + passwort1[i];
		}
		for (int i = 0; i < passwort2.length; i++) {
			pwd2 = pwd2 + passwort2[i];
		}

		if (!myRegistration.getTxtEmailadresse().getText().equals("")
				|| !myRegistration.getTxtNachname().getText().equals("")
				|| !myRegistration.getTxtVorname().getText().equals("")
				|| !pwd1.equals("") || !pwd2.equals("")) {

			if (pwd1.equals(pwd2)) {

				passwort = pwd1;
				if (myRequester == null) {
					pruefeServereinstellungen();
				}

				// aktUser= new User();
				aktUser.setMailAddress(myRegistration.getTxtEmailadresse()
						.getText());
				aktUser.setSurname(myRegistration.getTxtNachname().getText());
				aktUser.setGivenname(myRegistration.getTxtVorname().getText());
				// aktUser.setPhoneNumber("111");

				User utmp = myRequester.registerNewUser(aktUser, passwort);

				if (utmp == null) {
					System.out.println("Konnte nicht registrieren");

				} else {

					aktUser = utmp;
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

				/*
				 * Feld schaffen für selber Name aussuchen
				 */

				myRegistration.setVisible(false);
				myLogScreen.setVisible(true);

			} else {
				JOptionPane.showMessageDialog(myLogScreen,
						"Das Passwort stimmt nicht überein!",
						"Passwort falsch", JOptionPane.INFORMATION_MESSAGE);
				myRegistration.getTxtPasswort().setText("");
				myRegistration.getTxtPasswortBest().setText("");
			}
		} else {
			JOptionPane.showMessageDialog(myLogScreen,
					"Bitte füllen Sie alle Felder aus!", "Felder frei",
					JOptionPane.INFORMATION_MESSAGE);

			Color c = new Color(255, 86, 63);
			Border redline = BorderFactory.createLineBorder(c);
			if (myRegistration.getTxtEmailadresse().getText().equals("")) {
				myRegistration.getTxtEmailadresse().setBorder(redline);
				myRegistration.repaint();
			}
			if (myRegistration.getTxtNachname().getText().equals("")) {
				myRegistration.getTxtNachname().setBorder(redline);
				myRegistration.repaint();
			}
			if (myRegistration.getTxtVorname().getText().equals("")) {
				myRegistration.getTxtVorname().setBorder(redline);
				myRegistration.repaint();
			}
			if (pwd1.equals("")) {
				myRegistration.getTxtPasswort().setBorder(redline);

				myRegistration.repaint();
			}
			if (pwd2.equals("")) {
				myRegistration.getTxtPasswortBest().setBorder(redline);
				myRegistration.repaint();
			}
			/*
			 * XXX Ein repaint hier unten würde es auch tun ;-)
			 */
		}

	}

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

	public void bearbeiteTermin() {
		editEntry = new window_TerminBearbeiten(this, this);
		aktDate = myHauptmenue.getAktDateCali();
		if (aktTermin == null) {
			editEntry.setButtonText("Erstellen");
			editEntry.openEmptyFrame();
		} else {
			editEntry.setButtonText("Termin Speichern");
			String details = myModel.returnDetail(aktTermin);
			String startZeit = aktTermin;
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
		//
		// /*
		// * hier muss eine KalenderID aus einem Personenobjekt übergeben
		// * werden
		// */

		benutzername = myLogScreen.getTextField().getText();
		char[] tmppasswort = myLogScreen.getPasswort().getPassword();
		/*
		 * XXX Vorschlag passwort = new String(tmppasswort); dann brauchst du
		 * keine for-Schleife --> wie weiter oben schon erwähnt. (PS: schon
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
							"Bitte geben Sie einen gültigen Benutzernamen und ein gültiges Passwort ein",
							"Benutzername oder Passwort falsch",
							JOptionPane.INFORMATION_MESSAGE);
		} else {
			new organizer.objects.types.Calendar();
			// /*
			// * Abfrage der Id für den Benutzernamen
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
									"Es ist noch kein Kalendar für Sie erstellt worden",
									"Verbindungsfehler",
									JOptionPane.INFORMATION_MESSAGE);
				}
				aktUserCa.setID(1);

				organizer.objects.types.Calendar tmpCal = myRequester
						.requestObjectByOwnId(aktUserCa);

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
