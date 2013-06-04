package controller;

import organizer.objects.types.User;

public interface MyChangeListener{
	public void stateChangedForUser(boolean state, User user);
}
