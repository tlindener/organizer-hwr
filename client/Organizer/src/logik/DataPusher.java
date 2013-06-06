package logik;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




public interface DataPusher {
//	gibt ein Objekt zurück in dem jeder Uhrzeit (0-23.30, halbstündliche Einteilung) eine Beschreibung zugeordnet ist
	public Object[][] getBeschreibungen();
	public Object getDauer(String zeit);
	public organizer.objects.types.User[] pushUserList();
	public organizer.objects.types.Room[] pushRoomList();
//	gibt die Details zur jeweiligen Beschreibung zurück
	

}
