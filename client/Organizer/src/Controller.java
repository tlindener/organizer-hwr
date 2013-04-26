import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


public class Controller implements DataPusher, ActionListener{

	/**
	 * @param args
	 * 
	 */
	

	private Model myModel;
	private window_Hauptmenue myHauptmenue;
	private Object[][] beschreibungsDaten;
	private window_TerminBearbeiten myTerminBearbeiten;
	
	public Controller() {
		myModel =new Model();
		myHauptmenue= new window_Hauptmenue(this, this);
		myTerminBearbeiten=new window_TerminBearbeiten(this, this);
		
		
	}
	
	public static void main(String[] args) {
		new Controller();
	}
	
	@Override
	public Object[][] getBeschreibungen()
	{
		beschreibungsDaten=new Object[][]{
				{"0.00", myModel.getBeschreibung("0.00")},
				{"0.30", myModel.getBeschreibung("0.30")},
				{"1.00", myModel.getBeschreibung("1.00")},
				{"1.30", myModel.getBeschreibung("1.30")},
				{"2.00", myModel.getBeschreibung("2.00")},
				{"2.30", myModel.getBeschreibung("2.30")},
				{"3.00", myModel.getBeschreibung("3.00")},
				{"3.30", myModel.getBeschreibung("3.30")},
				{"4.00", myModel.getBeschreibung("4.00")},
				{"4.30", myModel.getBeschreibung("4.30")},
				{"5.00", myModel.getBeschreibung("5.00")},
				{"5.30", myModel.getBeschreibung("5.30")},
				{"6.00", myModel.getBeschreibung("6.00")},
				{"6.30", myModel.getBeschreibung("6.30")},
				{"7.00", myModel.getBeschreibung("7.00")},
				{"7.30", myModel.getBeschreibung("7.30")},
				{"8.00", myModel.getBeschreibung("8.00")},
				{"8.30", myModel.getBeschreibung("8.30")},
				{"9.00", myModel.getBeschreibung("9.00")},
				{"9.30", myModel.getBeschreibung("9.30")},
				{"10.00", myModel.getBeschreibung("10.00")},
				{"10.30", myModel.getBeschreibung("10.30")},
				{"11.00", myModel.getBeschreibung("11.00")},
				{"11.30", myModel.getBeschreibung("11.30")},
				{"12.00", myModel.getBeschreibung("12.00")},
				{"12.30", myModel.getBeschreibung("12.30")},
				{"13.00", myModel.getBeschreibung("13.00")},
				{"13.30", myModel.getBeschreibung("13.30")},
				{"14.00", myModel.getBeschreibung("14.00")},
				{"14.30", myModel.getBeschreibung("14.30")},
				{"15.00", myModel.getBeschreibung("15.00")},
				{"15.30", myModel.getBeschreibung("15.30")},
				{"16.00", myModel.getBeschreibung("16.00")},
				{"16.30", myModel.getBeschreibung("16.30")},
				{"17.00", myModel.getBeschreibung("17.00")},
				{"17.30", myModel.getBeschreibung("17.30")},
				{"18.00", myModel.getBeschreibung("18.00")},
				{"18.30", myModel.getBeschreibung("18.30")},
				{"19.00", myModel.getBeschreibung("19.00")},
				{"19.30", myModel.getBeschreibung("19.30")},
				{"20.00", myModel.getBeschreibung("20.00")},
				{"20.30", myModel.getBeschreibung("20.30")},
				{"21.00", myModel.getBeschreibung("21.00")},
				{"21.30", myModel.getBeschreibung("21.30")},
				{"22.00", myModel.getBeschreibung("22.00")},
				{"22.30", myModel.getBeschreibung("22.30")},
				{"23.00", myModel.getBeschreibung("23.00")},
				{"23.30", myModel.getBeschreibung("23.30")},
				};
		
		return beschreibungsDaten;
	}
	public String getDetails(String aktZeit)
	{
		String details= null;
		details= (String) myModel.getDetails(aktZeit);
		return details;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==myHauptmenue.getBtnTerminBearbeiten())
		{
			myHauptmenue.getAktDateCali();
			myTerminBearbeiten.setVisible(true);
		}
		if(e.getSource()==myHauptmenue.getBtnAbmelden())
		{
			myHauptmenue.dispose();
		}
		if(e.getSource()==myHauptmenue.getBtnTerminEntfernen())
		{
		/*
		 * Über Model Verbindung zum Server 
		 * Übergabe des Termines
		 * Löschen des Termines durch Netzwerklayer
		 */
		}
		if(e.getSource()==myTerminBearbeiten.getBtnTerminEintragen())
		{
		/*
		 * Über Model Verbindung zum Server 
		 * Übergabe des Termines und Speicherung in Datenbank
		 * 
		 */
		}
		else
		{}
		
	}
	
}
