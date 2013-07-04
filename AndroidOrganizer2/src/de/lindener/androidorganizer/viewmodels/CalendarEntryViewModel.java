package de.lindener.androidorganizer.viewmodels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import organizer.objects.types.Room;

/**
 * Model to easily bind data to views
 * @author TobiasLindener
 *
 */
public class CalendarEntryViewModel {

	public List<UserViewModel> getInvitees() {
		return invitees;
	}

	public void setInvitees(List<UserViewModel> invitees) {
		this.invitees = invitees;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	List<UserViewModel> invitees = null;
	Date startDate = null;
	Date endDate = null;
	String description = "";
	String title = "";
	Room room = null;
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public CalendarEntryViewModel()
	{
		invitees = new ArrayList<UserViewModel>();
	}
}
