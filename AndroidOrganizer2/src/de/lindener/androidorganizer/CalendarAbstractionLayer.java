package de.lindener.androidorganizer;

import java.util.ArrayList;
import java.util.List;

import network.json.JsonJavaIISRequestHandler;

import de.lindener.androidorganizer.viewmodels.CalendarEntryViewModel;
import de.lindener.androidorganizer.viewmodels.UserViewModel;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

public class CalendarAbstractionLayer {

	private organizer.objects.types.User user;
	private String address;
	private int port;
	private String mail;
	private String password;
	private JsonJavaIISRequestHandler requestHandler = null;

	public CalendarAbstractionLayer(String serviceAddress, int servicePort,
			String mailAdress, String userPassword) {
		this.address = serviceAddress;
		this.port = servicePort;
		this.mail = mailAdress;
		this.password = userPassword;
	}
	
	

	public List<Calendar> getCalendars() {
		if (user == null) {
			connectToServiceAndLogin(address, port, mail, password);
		}		
		List<Calendar> calendars = new ArrayList<Calendar>();
		if (user != null) {
			List<Integer> calendarIds = user.getCalendarIds();
			if (!calendarIds.isEmpty()) {
				for (int i = 0; i < calendarIds.size(); i++) {
					Calendar tmpCalendar = new Calendar();
					tmpCalendar.setID(calendarIds.get(i));
					calendars.add(requestHandler
							.requestObjectByOwnId(tmpCalendar));
				}
			}
		}
		return calendars;
	}

	public CalendarEntryViewModel getCalendarEntry(int calendarEntryId) {
		if (user == null) {
			connectToServiceAndLogin(address, port, mail, password);
		}
		
		CalendarEntry serverEntry = new CalendarEntry();
		serverEntry.setID(calendarEntryId);

		serverEntry = requestHandler.requestObjectByOwnId(serverEntry);

		CalendarEntryViewModel entry = new CalendarEntryViewModel();
		if (serverEntry != null) {
			entry.setDescription(serverEntry.getDescription());
			entry.setEndDate(serverEntry.getEndDate());
			entry.setStartDate(serverEntry.getStartDate());
			entry.setTitle(serverEntry.getTitle());
			entry.setRoom(requestRoom(serverEntry.getRoomId()));

			List<UserViewModel> inviteList = new ArrayList<UserViewModel>();

			for (User u : getInvitesOfEntry(serverEntry.getInviteIds())) {
				inviteList.add(convertToUserViewModel(u));
			}
			entry.setInvitees(inviteList);
		}
		return entry;

	}

	/**
	 * Hier werden die User von den Invites abgefragt XXX Hier ist die Methode
	 * 
	 * @param inviteIds
	 * @return
	 */
	private List<User> getInvitesOfEntry(List<Integer> inviteIds) {
		if (user == null) {
			connectToServiceAndLogin(address, port, mail, password);
		}
		
		ArrayList<User> user = new ArrayList<User>();
		List<Invite> invites = requestHandler.requestFollowingObjectsByOwnId(
				inviteIds, new Invite());
		for (Invite invite : invites) {
			if (invite != null) {
				User requestUser = new User();
				requestUser.setID(invite.getOwnerId());
				requestUser = requestHandler.requestObjectByOwnId(requestUser);
				if (requestUser != null) {
					user.add(requestUser);
				}
			}
		}
		return user;
	}

	private Room requestRoom(int roomId) {
		if (user == null) {
			connectToServiceAndLogin(address, port, mail, password);
		}
		
		Room room = new Room();
		room.setID(roomId);
		room = requestHandler.requestObjectByOwnId(room);
		return room;
	}

	private UserViewModel convertToUserViewModel(User user) {
		UserViewModel userViewModel = new UserViewModel();
		userViewModel.setGivenName(user.getGivenName());
		userViewModel.setMailAddress(user.getMailAddress());
		userViewModel.setPhoneNumber(user.getPhoneNumber());
		userViewModel.setSurname(user.getSurname());
		return userViewModel;
	}

	private boolean connectToServiceAndLogin(String address, int port,
			String mail, String password) {
		requestHandler = new JsonJavaIISRequestHandler(address, port);

		if (requestHandler != null) {

			user = requestHandler.login(mail, password);

			if (user != null) {

				return true;
			}
		}
		return false;
	}
}
