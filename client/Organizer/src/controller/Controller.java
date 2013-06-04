package controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import com.toedter.calendar.JCalendar;

import logik.DataPusher;
import logik.Model;

import organizer.objects.types.CalendarEntry;

import organizer.objects.types.Room;
import organizer.objects.types.User;
import view.checklistitem;
import view.listRenderer;
import view.window_Hauptmenue;
import view.window_LogScreen;
import view.window_RegisterUser;
import view.window_Servereinstellungen;
import view.window_TerminBearbeiten;

import network.JsonJavaRequestHandler;
import network.RequestHandler;

public class Controller implements DataPusher, ActionListener, MouseListener,
		PropertyChangeListener {

	/**
	 * @param args
	 * 
	 */

	private Model myModel;

	private Object[][] beschreibungsDaten;
	private Object[][] terminDauer;

	private window_TerminBearbeiten myTerminBearbeiten;
	private window_Hauptmenue myHauptmenue;
	private window_LogScreen myLogScreen;
	private window_Servereinstellungen myServereinstellungen;
	private window_RegisterUser myRegistration;

	private RequestHandler myRequester;
	private Date aktDate;
	private String aktTermin;
	private int start = 0;
	private organizer.objects.types.Calendar steffensCal;
	private int port;
	private String adresse;
	private String benutzername;
	private String passwort;
	private User aktUser;
	private organizer.objects.types.Calendar aktUserCa;
	private CalendarEntry aktEntry;

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
		myTerminBearbeiten = new window_TerminBearbeiten(this, this);
		myTerminBearbeiten.setVisible(false);
		myLogScreen = new window_LogScreen(this);
		myLogScreen.setVisible(true);
		myRegistration = new window_RegisterUser(this);
		myRegistration.setVisible(false);
		myServereinstellungen = new window_Servereinstellungen(this);
		myServereinstellungen.setVisible(false);
		aktUser = new User();
		aktUserCa= new organizer.objects.types.Calendar();
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == myLogScreen.getMntmServerkonfigurationen()) {
			myServereinstellungen.setVisible(true);
		}
		if (e.getSource() == myServereinstellungen.getBtnSpeichern()) {
			if (myServereinstellungen.getTxtPort().getText().equals("")
					|| myServereinstellungen.getTxtAdresse().getText()
							.equals("")) {

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
		if (e.getSource() == myHauptmenue.getBtnTerminBearbeiten()) {
			aktDate=myHauptmenue.getAktDateCali();
			if(aktTermin==null)
			{
				myTerminBearbeiten.getStartUhrzeit().setText("8:00");
				myTerminBearbeiten.getEndUhrzeit().setText("9:00");
			}
			else
			{
				myTerminBearbeiten.getStartUhrzeit().setText(aktTermin);
				myTerminBearbeiten.getEndUhrzeit().setText(myModel.returnEndzeit(aktTermin));
				myTerminBearbeiten.getTxtADetails().setText((String) myModel.returnDetail(aktTermin));
				myTerminBearbeiten.getTxtBeschreibung().setText((String) myModel.returnBeschreibung(aktTermin));
				
			}
			
			checklistitem[] tmpcl= new checklistitem[100000];
			int j=0;
			for(int i=myModel.getAllePersonen().size(); i>0;i--)
			{
				User u= (User) myModel.getAllePersonen().get(i-1);
				tmpcl[j]=new checklistitem(u.getGivenName()+" "+ u.getSurname());
				j++;
			}
			JList tmplist=new JList(tmpcl);
			System.out.println(tmpcl[1]);
			tmplist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tmplist.setCellRenderer(new listRenderer());
			System.out.println(tmplist);
			myTerminBearbeiten.setLstRaum(tmplist);
			myTerminBearbeiten.setVisible(true);
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
		if (e.getSource() == myTerminBearbeiten.getBtnTerminEintragen()) {
			
			/*
			 * befüllen des Fensters mit den Daten Personen und Räume
			 */
			
			
			aktEntry=new CalendarEntry();
			aktEntry.setCalendarId(aktUser.getID());
			aktEntry.setDescription(myTerminBearbeiten.getTxtADetails().getText());
			aktEntry.setTitle(myTerminBearbeiten.getTxtBeschreibung().getText());
			
			/*
			 * Über Model Verbindung zum Server Übergabe des Termines und
			 * Speicherung in Datenbank
			 */
		}
		if (e.getSource() == myLogScreen.getBtnAnmelden()) {
			/*
			 * Authentifizierung mit Server SPeicherung der ID Daten in das
			 * Modell Speicherung der Daten in das Modell
			 */
//
		
			if(pruefeServereinstellungen()==0)
			{
				return;
			}
//
//			/*
//			 * hier muss eine KalenderID aus einem Personenobjekt übergeben
//			 * werden
//			 */
			
			benutzername = myLogScreen.getTextField().getText();
			char[] tmppasswort = myLogScreen.getPasswort().getPassword();
			passwort="";
			for (int i=0;i<tmppasswort.length;i++)
			{
				passwort=passwort+tmppasswort[i];
			}
			
			if (benutzername.equals("") || passwort.equals("")) {
				JOptionPane
						.showMessageDialog(
								myLogScreen,
								"Bitte geben Sie einen gültigen Benutzernamen und ein gültiges Passwort ein",
								"Benutzername oder Passwort falsch",
								JOptionPane.INFORMATION_MESSAGE);
			} else {
				steffensCal = new organizer.objects.types.Calendar();
//				/*
//				 * Abfrage der Id für den Benutzernamen
//				 */
				User tmpu=myRequester.login(benutzername, passwort);
				
				if (tmpu!= null) {
					
					aktUser = tmpu;
					List <Integer> calendarIDs= aktUser.getCalendarIds();
					if(!calendarIDs.isEmpty())
					{
						aktUserCa.setID(calendarIDs.get(0));
					}
					else
					{
						JOptionPane
						.showMessageDialog(
								myLogScreen,
								"Es ist noch kein Kalendar für Sie erstellt worden",
								"Verbindungsfehler",
								JOptionPane.INFORMATION_MESSAGE);
					}
					aktUserCa.setID(1);
					
					organizer.objects.types.Calendar tmpCal = myRequester.requestObjectByOwnId(aktUserCa);
					
//					// null Abfrage
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
		if (e.getSource() == myLogScreen.getBtnRegistrieren()) {
			myRegistration.setVisible(true);

		}
		if (e.getSource() == myRegistration.getBtnRegistrieren()) {
			
			char[] passwort1 = myRegistration.getTxtPasswort().getPassword();
			char[] passwort2 = myRegistration.getTxtPasswortBest().getPassword();
			String pwd1="";
			String pwd2="";
			for (int i=0;i<passwort1.length;i++)
			{
				pwd1=pwd1+passwort1[i];
			}
			for (int i=0;i<passwort2.length;i++)
			{
				pwd2=pwd2+passwort2[i];
			}
			
			 if(!myRegistration.getTxtEmailadresse().getText().equals("")||!myRegistration.getTxtNachname().getText().equals("")
			 ||!myRegistration.getTxtVorname().getText().equals("")||!pwd1.equals("")||!pwd2.equals("")){
					
				 if (pwd1.equals(pwd2)) {
				
				passwort=pwd1;
				if (myRequester == null) {
					pruefeServereinstellungen();
				}
			
//				aktUser= new User();
				aktUser.setMailAddress(myRegistration.getTxtEmailadresse()
						.getText());
				aktUser.setSurname(myRegistration.getTxtNachname().getText());
				aktUser.setGivenname(myRegistration.getTxtVorname().getText());
//				aktUser.setPhoneNumber("111");
				
				User utmp=myRequester.registerNewUser(aktUser, passwort);
				
				if(utmp==null)
				{
					System.out.println("Konnte nicht registrieren");
					
				}
				else
				{
					
					aktUser= utmp;
					aktUserCa = new organizer.objects.types.Calendar();
					aktUserCa.setOwnerId(aktUser.getID());
					aktUserCa.setDescription("persönlicher Kalendar von "+aktUser.getGivenName());
					aktUserCa.setName("Kalendar von "+aktUser.getGivenName() );
					myRequester.login(aktUser.getMailAddress(), passwort);
					Object tmp=myRequester.addObject(aktUserCa);
					
				if(tmp==null)
				{
					// Fenster
					System.out.println("Es konnte kein Kalendar hinzugefügt werden");
				
				}
				else
				{
					aktUserCa=(organizer.objects.types.Calendar) tmp;
					List l=aktUser.getCalendarIds();
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
			 }
			 else
			 {
			 JOptionPane
			 .showMessageDialog(
			 myLogScreen,
			 "Bitte füllen Sie alle Felder aus!",
			 "Felder frei",
			 JOptionPane.INFORMATION_MESSAGE);
			 
			 Color c= new Color(255,86,63);
			 Border redline = BorderFactory.createLineBorder(c);
			 if(myRegistration.getTxtEmailadresse().getText().equals(""))
			 {
				 myRegistration.getTxtEmailadresse().setBorder(redline); 
				myRegistration.repaint();
			 }
			 if(myRegistration.getTxtNachname().getText().equals(""))
			 {
				 myRegistration.getTxtNachname().setBorder(redline); 
					myRegistration.repaint();
			 }
			 if(myRegistration.getTxtVorname().getText().equals(""))
			 {
				 myRegistration.getTxtVorname().setBorder(redline); 
					myRegistration.repaint();
			 }
			 if(pwd1.equals(""))
			 {
				 myRegistration.getTxtPasswort().setBorder(redline); 
				 
					myRegistration.repaint();
			 }
			 if(pwd2.equals(""))
			 {
				 myRegistration.getTxtPasswortBest().setBorder(redline); 
					myRegistration.repaint(); 
			 }
//			 
//			 
			 }
			 
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource() == myHauptmenue.getTable_1()) {
			/*
			 * Details für Termin werden eingefüllt leere Details werden in
			 * myModel umgangen (Alternativtext)
			 */
			JTable zwTab = (JTable) e.getSource();
			aktTermin=(String) myHauptmenue.getTable_1()
					.getValueAt(zwTab.getSelectedRow(), 0);
			zwTab.getSelectedRow();
			String details = (String) myModel
					.returnDetail((String) myHauptmenue.getTable_1()
							.getValueAt(zwTab.getSelectedRow(), 0));
			myHauptmenue.getTextArea().setText(details);
			/*
			 * Terminteilnehmer werden in die JList eingefügt
			 */
			List myList = new ArrayList<String>();
			myList = myModel.returnEingeladene((String) myHauptmenue.getTable_1()
					.getValueAt(zwTab.getSelectedRow(), 0));
			if (myList != null) {
				myHauptmenue.getListModel().removeAllElements();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
			connectServerModel();
		}

	}

	public void befuelleModel() throws ParseException {
		myModel.getBeschreibungen().clear();
		myModel.getDauer().clear();
		myModel.getPersonen().clear();
		myModel.getDetails().clear();
		myModel.getRaeume().clear();
		myModel.getAnfangende().clear();
		
		myModel.getAllePersonen().clear();
		myModel.getAlleRaeume().clear();
		
//		CalendarEntry entry =new CalendarEntry();
//		entry.setCalendarId(aktUser.getCalendarIds().get(0));
//		entry.setRequestProperty(CalendarEntry.OWNER_ID, ""+1);
//		List <CalendarEntry> entries = myRequester.requestAllObjectsByProperty(entry);
//		
//		myModel.setAllePersonen(entries);
		List<Room> lr = myRequester.requestAllObjects(new Room());
		myModel.setAlleRaeume(lr);
		
		List <User> lp=myRequester.requestAllObjects(new User());
		myModel.setAllePersonen(lp);
		
	
		
		
		List myCes = steffensCal.getCalendarEntries();
		for (int i = myCes.size() - 1; i > 0; i--) {
			CalendarEntry myCe = (CalendarEntry) myCes.get(i);
			if (parseDate(myCe.getStartDate()).equals(
					parseDate(myHauptmenue.getCali().getDate()))) {
				String anfangZeit = "";
				int minuten = 0;
				int stunden = 0;
				stunden = myCe.getStartHour();
				minuten = myCe.getStartMinute();
				if (minuten < 10) {
					anfangZeit = stunden + ":0" + minuten;
				} else {
					anfangZeit = stunden + ":" + minuten;
				}
				String endZeit = "";
				stunden = myCe.getEndHour();
				minuten = myCe.getEndMinute();
				if (minuten < 10) {
					endZeit = stunden + ":0" + minuten;
				} else {
					endZeit = stunden + ":" + minuten;
				}
				myModel.setAktDate(aktDate);
				myModel.setBeschreibungen(anfangZeit, myCe.getDescription());
				myModel.setDauer(anfangZeit, myCe.getDuration());
				myModel.setPersonen(anfangZeit, myCe.getInvitees());
				myModel.setDetails(anfangZeit, myCe.getTitle());
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

	public String parseDate(Date date) {
		String datum = "";
		datum = date.toString().substring(0, 11)
				+ date.toString().substring(20);
		return datum;
	}

	public void updateData() {
		erstelleDOBeschreibungen();
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
		try {
			befuelleModel();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		updateData();
		myHauptmenue.setVisible(true);
		myHauptmenue.repaint();
		setDauer();
	}

	public int pruefeServereinstellungen() {
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
}
