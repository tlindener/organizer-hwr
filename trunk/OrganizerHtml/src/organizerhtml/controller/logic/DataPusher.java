package organizerhtml.controller.logic;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public interface DataPusher {
//	gibt ein Objekt zurück in dem jeder Uhrzeit (0-23.30, halbstündliche Einteilung) eine Beschreibung zugeordnet ist
	public Object[][] getBeschreibungen();
	public Object getDauer(String zeit);
	public void setDauer();
//	gibt die Details zur jeweiligen Beschreibung zurück
	

}
