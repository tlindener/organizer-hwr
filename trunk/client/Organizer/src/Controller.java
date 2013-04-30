import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import network.JsonJavaRequestHandler;
import network.RequestHandler;

public class Controller implements DataPusher, ActionListener {

	/**
	 * @param args
	 * 
	 */

	private Model myModel;
	private window_Hauptmenue myHauptmenue;
	private Object[][] beschreibungsDaten;
	private window_TerminBearbeiten myTerminBearbeiten;
	private RequestHandler myRequester;
	private Date aktDateCon;

	public Controller() {
		myRequester = new JsonJavaRequestHandler();
		myModel = new Model();
		myHauptmenue = new window_Hauptmenue(this, this);
		myTerminBearbeiten = new window_TerminBearbeiten(this, this);
		erstelleDOBeschreibungen();
		
	}

	public static void main(String[] args) {
		new Controller();
	}

	public void pushKalender() {

	}
/*
 * DO= DatenObjekt
 * @me: vllt eher in Model???
 */
	public void erstelleDOBeschreibungen()
	{
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

			if (myModel.getBeschreibung(myTime) != null) {
				beschreibungsDaten[i][0] = myTime;
				beschreibungsDaten[i][1] = myModel.getBeschreibung(myTime);

			} else if (myTimeMinute==1) {
				System.out.println(myTimeMinute);
				beschreibungsDaten[i][0] = myTime;
				beschreibungsDaten[i][1] = "Volle Stunde " + myTimeHour;
			}
			
				if (i<1439)
			i = i + 1;
		}
		for (Object[] row:beschreibungsDaten)
		{
			for (Object element:row)
			{
				System.out.print(element + " ");
				
			}
			System.out.println();
		}
		
		konvertiereBeschreibungsDaten();
	}
	@Override
	public Object[][] getBeschreibungen() {
		return beschreibungsDaten;
	}

	public Object[][] getBeschreibungenStatisch() {
		beschreibungsDaten = new Object[][] {
				{ "0.00", myModel.getBeschreibung("0.00") },
				{ "0.30", myModel.getBeschreibung("0.30") },
				{ "1.00", myModel.getBeschreibung("1.00") },
				{ "1.30", myModel.getBeschreibung("1.30") },
				{ "2.00", myModel.getBeschreibung("2.00") },
				{ "2.30", myModel.getBeschreibung("2.30") },
				{ "3.00", myModel.getBeschreibung("3.00") },
				{ "3.30", myModel.getBeschreibung("3.30") },
				{ "4.00", myModel.getBeschreibung("4.00") },
				{ "4.30", myModel.getBeschreibung("4.30") },
				{ "5.00", myModel.getBeschreibung("5.00") },
				{ "5.30", myModel.getBeschreibung("5.30") },
				{ "6.00", myModel.getBeschreibung("6.00") },
				{ "6.30", myModel.getBeschreibung("6.30") },
				{ "7.00", myModel.getBeschreibung("7.00") },
				{ "7.30", myModel.getBeschreibung("7.30") },
				{ "8.00", myModel.getBeschreibung("8.00") },
				{ "8.30", myModel.getBeschreibung("8.30") },
				{ "9.00", myModel.getBeschreibung("9.00") },
				{ "9.30", myModel.getBeschreibung("9.30") },
				{ "10.00", myModel.getBeschreibung("10.00") },
				{ "10.30", myModel.getBeschreibung("10.30") },
				{ "11.00", myModel.getBeschreibung("11.00") },
				{ "11.30", myModel.getBeschreibung("11.30") },
				{ "12.00", myModel.getBeschreibung("12.00") },
				{ "12.30", myModel.getBeschreibung("12.30") },
				{ "13.00", myModel.getBeschreibung("13.00") },
				{ "13.30", myModel.getBeschreibung("13.30") },
				{ "14.00", myModel.getBeschreibung("14.00") },
				{ "14.30", myModel.getBeschreibung("14.30") },
				{ "15.00", myModel.getBeschreibung("15.00") },
				{ "15.30", myModel.getBeschreibung("15.30") },
				{ "16.00", myModel.getBeschreibung("16.00") },
				{ "16.30", myModel.getBeschreibung("16.30") },
				{ "17.00", myModel.getBeschreibung("17.00") },
				{ "17.30", myModel.getBeschreibung("17.30") },
				{ "18.00", myModel.getBeschreibung("18.00") },
				{ "18.30", myModel.getBeschreibung("18.30") },
				{ "19.00", myModel.getBeschreibung("19.00") },
				{ "19.30", myModel.getBeschreibung("19.30") },
				{ "20.00", myModel.getBeschreibung("20.00") },
				{ "20.30", myModel.getBeschreibung("20.30") },
				{ "21.00", myModel.getBeschreibung("21.00") },
				{ "21.30", myModel.getBeschreibung("21.30") },
				{ "22.00", myModel.getBeschreibung("22.00") },
				{ "22.30", myModel.getBeschreibung("22.30") },
				{ "23.00", myModel.getBeschreibung("23.00") },
				{ "23.30", myModel.getBeschreibung("23.30") }, };
		return beschreibungsDaten;
	}

	public Object[][] konvertiereBeschreibungsDaten() {
		Object[][] beschreibungsDatenKonv = new Object[48][2];
		int j = 0;
		for (int i = 0; i < 1439; i++) {
			if (beschreibungsDaten[i][1] != null) {
				
				beschreibungsDatenKonv[j][0] = beschreibungsDaten[i][0];
				beschreibungsDatenKonv[j][1] = beschreibungsDaten[i][1];
				if (j < 48) {
					j++;
				}
			}
			
		}
		beschreibungsDaten = beschreibungsDatenKonv;
		return beschreibungsDaten;
	}

	public String getDetails(String aktZeit) {
		String details = null;
		details = (String) myModel.getDetails(aktZeit);
		return details;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == myHauptmenue.getBtnTerminBearbeiten()) {
			myHauptmenue.getAktDateCali();
			myTerminBearbeiten.setVisible(true);
		}
		if (e.getSource() == myHauptmenue.getBtnAbmelden()) {
			myHauptmenue.dispose();
		}
		if (e.getSource() == myHauptmenue.getBtnTerminEntfernen()) {
			/*
			 * �ber Model Verbindung zum Server �bergabe des Termines L�schen
			 * des Termines durch Netzwerklayer
			 */
		}
		if (e.getSource() == myTerminBearbeiten.getBtnTerminEintragen()) {
			/*
			 * �ber Model Verbindung zum Server �bergabe des Termines und
			 * Speicherung in Datenbank
			 */
		} else {
		}

	}

}
