package view.listener;

import data.objects.types.Room;
import data.objects.types.User;

/**
 * Provides the methods to handle the JList changes
 * 
 * @version 1.0 
 *
 */
public interface MyChangeListener{
	public void stateChangedForUser(boolean state, User user);
	public void stateChangedForRoom(boolean state, Room room);
}
