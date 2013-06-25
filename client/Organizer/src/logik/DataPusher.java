package logik;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import organizer.objects.types.Room;
import organizer.objects.types.User;




public interface DataPusher {
//	gibt ein Objekt zurück in dem jeder Uhrzeit (0-23.30, halbstündliche Einteilung) eine Beschreibung zugeordnet ist
	public Object[][] getBeschreibungen();
	public User[] pushUserList();
	public Room[] pushRoomList();
	

}
