package controller;


import organizer.objects.types.Room;
import organizer.objects.types.User;

public interface MyChangeListener{
	public void stateChangedForUser(boolean state, User user);
	public void stateChangedForRoom(boolean state, Room room);
}