import java.util.Calendar;
import java.util.Date;


public interface DataPusher {
//	gibt ein Objekt zur�ck in dem jeder Uhrzeit (0-23.30, halbst�ndliche Einteilung) eine Beschreibung zugeordnet ist
	public Object[][] getBeschreibungen();
	public String getDetails(String aktZeit);
//	gibt die Details zur jeweiligen Beschreibung zur�ck
	

}
