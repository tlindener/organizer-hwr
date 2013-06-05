package data;

import data.objects.types.Room;
import data.objects.types.User;

//nur für asynchrones Laden - nutzt du aber wohl nicht, was auch gut ist :D
public interface DataPusher {
	public User[] pushUserList();
	public Room[] pushRoomList();
}
