package view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;

import logik.DataPusher;



public class View {
	
	private ActionListener myAl;
	private MouseListener myMl;
	private PropertyChangeListener myPCL;
	private DataPusher myDP;
	
	private Hauptmenue myHauptmenue;
	private LogScreen myLogScreen;
	private RegisterUser myRegistration;
	private Servereinstellungen myServereinstellungen;
	private NeuerRaum myNeuerRaum;
	private Einladungen myEinladungen;
	
	public View(ActionListener al, MouseListener ml, PropertyChangeListener pl,DataPusher dp)
	{
		myAl=al;
		myMl=ml;
		myPCL=pl;
		myDP=dp;
		
		myHauptmenue = new Hauptmenue(myDP, myAl,myMl, myPCL);
		myHauptmenue.setVisible(false);
		myLogScreen = new LogScreen(myAl);
		myLogScreen.setVisible(true);
		myRegistration = new RegisterUser(myAl);
		myRegistration.setVisible(false);
		myServereinstellungen = new Servereinstellungen(myAl);
		myServereinstellungen.setVisible(false);
		myNeuerRaum = new NeuerRaum(myAl);
		myNeuerRaum.setVisible(false);
		myEinladungen = new Einladungen(myAl);
		myEinladungen.setVisible(false);
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

	public void setMyServereinstellungen(Servereinstellungen myServereinstellungen) {
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
	
}
