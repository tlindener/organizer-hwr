package Logik;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public interface DataPusher {
//	gibt ein Objekt zur�ck in dem jeder Uhrzeit (0-23.30, halbst�ndliche Einteilung) eine Beschreibung zugeordnet ist
	public Object[][] getBeschreibungen();
	public String getDetails(String aktZeit);
	public List getPersonen(String zeit);
	public Object getDauer(String zeit);
	public void setDauer();
//	gibt die Details zur jeweiligen Beschreibung zur�ck
	

}
