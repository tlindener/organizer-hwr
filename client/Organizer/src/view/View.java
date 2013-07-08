package view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;

import organizer.objects.types.Room;
import organizer.objects.types.User;

import logik.DataPusher;

/**
 * The view class has the purpose to create all windows and provides some
 * special methods to update or create windows specially.
 * 
 * @author Jennifer Blumenthal
 * 
 */
public class View {

	private ActionListener myAl;
	private MouseListener myMl;
	private PropertyChangeListener myPCL;
	private DataPusher myDP;
	private ListSelectionListener myLs;

	private Hauptmenue myHauptmenue;
	private LogScreen myLogScreen;
	private RegisterUser myRegistration;
	private Servereinstellungen myServereinstellungen;
	private NeuerRaum myNeuerRaum;
	private Einladungen myEinladungen;
	private TerminBearbeiten myTerminBearbeiten;

	/**
	 * Default constructor in which all Listeners are initialized and 
	 * the serversettings window is created.
	 * 
	 * @param al
	 * @param ml
	 * @param pl
	 * @param dp
	 * @param ls
	 */

	public View(ActionListener al, MouseListener ml, PropertyChangeListener pl,
			DataPusher dp, ListSelectionListener ls) {
		myAl = al;
		myMl = ml;
		myPCL = pl;
		myDP = dp;
		myLs = ls;
		myServereinstellungen = new Servereinstellungen(myAl);

	}

	/**
	 * Creates a new window from type Hauptmenue
	 */
	public void createHauptmenue() {
		myHauptmenue = new Hauptmenue(myDP, myAl, myMl, myPCL, myLs);
		myHauptmenue.setVisible(true);
	}

	/**
	 * Creates a new window from type NeuerRaum.
	 */
	public void createNeuerRaum() {
		myNeuerRaum = new NeuerRaum(myAl);
		myNeuerRaum.setVisible(true);
	}

	/**
	 * Creates a new window from type Registrieren.
	 */
	public void createRegistrieren() {
		myRegistration = new RegisterUser(myAl);
		myRegistration.setVisible(true);
	}

	/**
	 * Creates a new window from type Einladungen.
	 */

	public void createEinladungen() {
		myEinladungen = new Einladungen(myAl);
		myEinladungen.setVisible(false);
	}

	/**
	 * Creates a new window from type LogScreen.
	 */
	public void createLogScreen() {
		myLogScreen = new LogScreen(myAl);
		myLogScreen.setVisible(true);
	}

	/**
	 * Creates a new window from type Servereinstellungen.
	 */
	public void createServereinstellungen() {
		
		myServereinstellungen.setVisible(true);
	}

	/**
	 * Inserts all submitted information (from model) into the fields.
	 * 
	 * @param table
	 * @param details
	 * @param eingeladene
	 * @param raum
	 * @return myHauptmenue
	 */
	public Hauptmenue befuelleHauptmenue(JTable table, String details,
			List<User> eingeladene, String raum) {

		myHauptmenue.getBtnTerminBearbeiten().setText("Termin bearbeiten");
		myHauptmenue.getTextArea().setText(details);

		if (eingeladene != null) {
			myHauptmenue.getListModel().removeAllElements();

			for (User element : eingeladene) {
				System.out.println(element);
				myHauptmenue.getListModel().addElement(
						element.getGivenName() + " " + element.getSurname());
				myHauptmenue.repaint();
			}
		} else {
			myHauptmenue.getListModel().removeAllElements();
		}

		myHauptmenue.getTxtRaum().setText(raum);
		return myHauptmenue;
	}

	/**
	 * Creates the window TerminBearbeiten and fills it with the submitted
	 * values.
	 * 
	 * @param details
	 * @param endZeit
	 * @param startZeit
	 * @param beschreibung
	 * @param raum
	 * @param raeume
	 * @param personen
	 */
	public void createTerminBearbeiten(String details, String endZeit,
			String startZeit, String beschreibung, String raum, Room[] raeume,
			User[] personen) {
		myTerminBearbeiten = new TerminBearbeiten(myDP, myAl, myMl);
		if (beschreibung.equals("")) {
			myTerminBearbeiten.setButtonText("Erstellen");
		} else {
			myTerminBearbeiten.setButtonText("Termin Speichern");
		}

		myTerminBearbeiten.openFrameWithValues(startZeit, endZeit,
				beschreibung, details, raeume, personen, raum);

	}

	/**
	 * Creates a window TerminBearbeiten without any values.
	 */
	public void createTerminBearbeiten() {
		myTerminBearbeiten = new TerminBearbeiten(myDP, myAl, myMl);
		myTerminBearbeiten.setButtonText("Erstellen");
		myTerminBearbeiten.openEmptyFrame();

	}

	/**
	 * Closes all windows.
	 */
	public void schliesseAlleFenster()
	{
		if(myHauptmenue!=null)
			myHauptmenue.dispose();
		if(myLogScreen!=null)
			myLogScreen.dispose();
		if(myRegistration!=null)	
			myRegistration.dispose();
		if(myServereinstellungen!=null)	
			myServereinstellungen.dispose();
		if(myNeuerRaum!=null)	
			myNeuerRaum.dispose();
		if(myEinladungen!=null)	
			myEinladungen.dispose();
		if(myTerminBearbeiten!=null)	
			myTerminBearbeiten.dispose();

	}

	public Hauptmenue getMyHauptmenue() {
		return myHauptmenue;
	}

	public void setMyHauptmenue(Hauptmenue myHauptmenue) {
		this.myHauptmenue = myHauptmenue;
	}

	public LogScreen getMyLogScreen() {
		return myLogScreen;
	}

	public void setMyLogScreen(LogScreen myLogScreen) {
		this.myLogScreen = myLogScreen;
	}

	public RegisterUser getMyRegistration() {
		return myRegistration;
	}

	public void setMyRegistration(RegisterUser myRegistration) {
		this.myRegistration = myRegistration;
	}

	public Servereinstellungen getMyServereinstellungen() {
		return myServereinstellungen;
	}

	public void setMyServereinstellungen(
			Servereinstellungen myServereinstellungen) {
		this.myServereinstellungen = myServereinstellungen;
	}

	public NeuerRaum getMyNeuerRaum() {
		return myNeuerRaum;
	}

	public void setMyNeuerRaum(NeuerRaum myNeuerRaum) {
		this.myNeuerRaum = myNeuerRaum;
	}

	public Einladungen getMyEinladungen() {
		return myEinladungen;
	}

	public void setMyEinladungen(Einladungen myEinladungen) {
		this.myEinladungen = myEinladungen;
	}

	public TerminBearbeiten getMyTerminBearbeiten() {
		return myTerminBearbeiten;
	}

	public void setMyTerminBearbeiten(TerminBearbeiten myTerminBearbeiten) {
		this.myTerminBearbeiten = myTerminBearbeiten;
	}

}
