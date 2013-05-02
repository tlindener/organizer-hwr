package Controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListModel;

import Logik.DataPusher;
import Logik.Model;
import View.window_Hauptmenue;
import View.window_TerminBearbeiten;

import network.JsonJavaRequestHandler;
import network.RequestHandler;

public class Controller implements DataPusher, ActionListener, MouseListener,PropertyChangeListener {

	/**
	 * @param args
	 * 
	 */

	private Model myModel;
	private window_Hauptmenue myHauptmenue;
	private Object[][] beschreibungsDaten;
	private Object[][] terminDauer;
	private window_TerminBearbeiten myTerminBearbeiten;
	private RequestHandler myRequester;
	private Date aktDate;
	private int start=0;

	public Controller() {
		myRequester = new JsonJavaRequestHandler();
		myModel = new Model(aktDate);
		erstelleDOBeschreibungen();
		myHauptmenue = new window_Hauptmenue(this, this,this,this);
		myTerminBearbeiten = new window_TerminBearbeiten(this, this);
	}

	public static void main(String[] args) {
		new Controller();
	}

	public void getKalenderEntries() {
		/*
		 * hier wird ein Kalender einer bestimmten ID abgefragt und die einzelnen Listen werden an das Modell übergeben
		 * sollte jedes Mal aufgerufen werden wenn Cali verändert wird
		 */
//		myRequester.requestObjectByOwnId(obj)

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

			} else if (myTimeMinute == 1) {

				beschreibungsDaten[i][0] = myTime;
				beschreibungsDaten[i][1] = "";
			}

			if (i < 1439)
				i = i + 1;
		}

		konvertiereBeschreibungsDaten();
	}

	
	public Object getDauer(String zeit)
	{
		if(myModel.returnDauer(zeit)!=null)
		{
		return myModel.returnDauer(zeit);
		}
		else
			return null;

	}
	
	
	
	@Override
	public Object[][] getBeschreibungen() {
		return beschreibungsDaten;
	}

	public Object[][] getBeschreibungenStatisch() {
		beschreibungsDaten = new Object[][] {
				{ "0.00", myModel.returnBeschreibung("0.00") },
				{ "0.30", myModel.returnBeschreibung("0.30") },
				{ "1.00", myModel.returnBeschreibung("1.00") },
				{ "1.30", myModel.returnBeschreibung("1.30") },
				{ "2.00", myModel.returnBeschreibung("2.00") },
				{ "2.30", myModel.returnBeschreibung("2.30") },
				{ "3.00", myModel.returnBeschreibung("3.00") },
				{ "3.30", myModel.returnBeschreibung("3.30") },
				{ "4.00", myModel.returnBeschreibung("4.00") },
				{ "4.30", myModel.returnBeschreibung("4.30") },
				{ "5.00", myModel.returnBeschreibung("5.00") },
				{ "5.30", myModel.returnBeschreibung("5.30") },
				{ "6.00", myModel.returnBeschreibung("6.00") },
				{ "6.30", myModel.returnBeschreibung("6.30") },
				{ "7.00", myModel.returnBeschreibung("7.00") },
				{ "7.30", myModel.returnBeschreibung("7.30") },
				{ "8.00", myModel.returnBeschreibung("8.00") },
				{ "8.30", myModel.returnBeschreibung("8.30") },
				{ "9.00", myModel.returnBeschreibung("9.00") },
				{ "9.30", myModel.returnBeschreibung("9.30") },
				{ "10.00", myModel.returnBeschreibung("10.00") },
				{ "10.30", myModel.returnBeschreibung("10.30") },
				{ "11.00", myModel.returnBeschreibung("11.00") },
				{ "11.30", myModel.returnBeschreibung("11.30") },
				{ "12.00", myModel.returnBeschreibung("12.00") },
				{ "12.30", myModel.returnBeschreibung("12.30") },
				{ "13.00", myModel.returnBeschreibung("13.00") },
				{ "13.30", myModel.returnBeschreibung("13.30") },
				{ "14.00", myModel.returnBeschreibung("14.00") },
				{ "14.30", myModel.returnBeschreibung("14.30") },
				{ "15.00", myModel.returnBeschreibung("15.00") },
				{ "15.30", myModel.returnBeschreibung("15.30") },
				{ "16.00", myModel.returnBeschreibung("16.00") },
				{ "16.30", myModel.returnBeschreibung("16.30") },
				{ "17.00", myModel.returnBeschreibung("17.00") },
				{ "17.30", myModel.returnBeschreibung("17.30") },
				{ "18.00", myModel.returnBeschreibung("18.00") },
				{ "18.30", myModel.returnBeschreibung("18.30") },
				{ "19.00", myModel.returnBeschreibung("19.00") },
				{ "19.30", myModel.returnBeschreibung("19.30") },
				{ "20.00", myModel.returnBeschreibung("20.00") },
				{ "20.30", myModel.returnBeschreibung("20.30") },
				{ "21.00", myModel.returnBeschreibung("21.00") },
				{ "21.30", myModel.returnBeschreibung("21.30") },
				{ "22.00", myModel.returnBeschreibung("22.00") },
				{ "22.30", myModel.returnBeschreibung("22.30") },
				{ "23.00", myModel.returnBeschreibung("23.00") },
				{ "23.30", myModel.returnBeschreibung("23.30") }, };
		return beschreibungsDaten;
	}

	public Object[][] konvertiereBeschreibungsDaten() {
		Object[][] beschreibungsDatenKonv = new Object[48][3];
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
	
	

	public String getDetails(String aktZeit) {
		String details = null;
		details = (String) myModel.returnDetail(aktZeit);
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
			 * Über Model Verbindung zum Server Übergabe des Termines Löschen
			 * des Termines durch Netzwerklayer
			 */
		}
		if (e.getSource() == myTerminBearbeiten.getBtnTerminEintragen()) {
			/*
			 * Über Model Verbindung zum Server Übergabe des Termines und
			 * Speicherung in Datenbank
			 */
		} else {
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
			if (e.getSource()==myHauptmenue.getTable_1())
			{
				/*
				 * Details für Termin werden eingefüllt
				 * leere Details werden in myModel umgangen (Alternativtext)
				 */
				JTable zwTab = (JTable)e.getSource();
				zwTab.getSelectedRow();
				String details=getDetails((String) myHauptmenue.getTable_1().getValueAt(zwTab.getSelectedRow(),0 ));
				myHauptmenue.getTextArea().setText(details);
				/*
				 * Terminteilnehmer werden in die JList eingefügt
				 */
				List myList = new ArrayList<String>();
				myList = myModel.returnPersonen((String)myHauptmenue.getTable_1().getValueAt(zwTab.getSelectedRow(),0 ));
				if (myList!=null)
				{
					myHauptmenue.getListModel().removeAllElements();
				for (int i=myList.size()-1;i>0;i--)
				{
					
					myHauptmenue.getListModel().addElement(myList.get(i));
				}
				}
				else
				{
					myHauptmenue.getListModel().removeAllElements();
				}
				/*
				 * der Raum zu dem Termin wird eingefügt
				 */
				myHauptmenue.getTextField().setText(myModel.returnRaum((String)myHauptmenue.getTable_1().getValueAt(zwTab.getSelectedRow(),0 )));
				
			
				
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
		
		if(start==0)
		{
		start=1;	
		}
		else
		{
			aktDate=myHauptmenue.getAktDateCali();
			myModel.setAktDate(aktDate);
			
		}
		
	}

	@Override
	public List getPersonen(String zeit) {
		
		return myModel.returnPersonen(zeit);
	}

	@Override
	public void setDauer() {
		// TODO Auto-generated method stub
		
	}

}
