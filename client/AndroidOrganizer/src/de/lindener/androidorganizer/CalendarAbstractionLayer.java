package de.lindener.androidorganizer;

import java.util.ArrayList;
import java.util.List;

//import network.JsonJavaRequestHandler;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.User;

public class CalendarAbstractionLayer {

	private User user;
	private String address;
	private int port;
	private String mail;
	private String password;
//	private JsonJavaRequestHandler requestHandler;

	public CalendarAbstractionLayer(String serviceAddress, int servicePort,
			String mailAdress, String userPassword) {
		this.address = serviceAddress;
		this.port = servicePort;
		this.mail = mailAdress;
		this.password = userPassword;

		if (user == null) {
			connectToServiceAndLogin(address, port, mail, password);
		}

	}

	public List<Calendar> getCalendars() {

		List<Calendar> calendars = new ArrayList<Calendar>();
		List<Integer> calendarIds = user.getCalendarIds();
		if (!calendarIds.isEmpty()) {
			for (int i = 0; i < calendarIds.size(); i++) {
				Calendar tmpCalendar = new Calendar();
				tmpCalendar.setID(calendarIds.get(i));
//				calendars.add(requestHandler.requestObjectByOwnId(tmpCalendar));
			}
		}
		return calendars;
	}

	private boolean connectToServiceAndLogin(String address, int port,
			String mail, String password) {
//		requestHandler = new JsonJavaRequestHandler(address, port);

//		user = requestHandler.login(mail, password);
		if (user != null) {
			return true;
		}
		return false;
	}

}
