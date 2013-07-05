package controller;


import organizer.objects.types.Room;
import organizer.objects.types.User;
/**
 * Interface to handle changes of checkboxes that contain Users and Rooms.
 * 
 * @author Steffen Baumann
 */

public interface MyChangeListener{
	public void stateChangedForUser(boolean state, User user);
	public void stateChangedForRoom(boolean state, Room room);
}