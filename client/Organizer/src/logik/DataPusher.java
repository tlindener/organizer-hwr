package logik;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import organizer.objects.types.Room;
import organizer.objects.types.User;


/**
 * Interface to push data from model to view if required.
 * 
 * @author JenniferBlumenthal
 *
 */

public interface DataPusher {
	public Object[][] getBeschreibungen();
	public User[] pushUserList();
	public Room[] pushRoomList();
}
