package network.objects;

import java.lang.reflect.Field;
import java.util.ArrayList;

import java.util.List;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;

import organizer.objects.types.Room;
import organizer.objects.types.User;

/**
 * @author Steffen Baumann
 *
 */
public class TestData {
	List<CalendarEntry> calendarEntries = new ArrayList<>();
	List<Room> rooms = new ArrayList<>();
	List<User> userList = new ArrayList<>();
	List<Calendar> calendar = new ArrayList<>();
	
	public TestData(){
		
		addUser(1, "Mustermann", "Max", "0180/1234567", "max.mustermann@web.de");
		addUser(2, "Normalverbraucher", "Otto", "030/1234567", "otto.normalverbrauher@web.de");
		
		addRoom(1,"Ort","Raumname",25);
		addRoom(2,"Ort","Raumname",12);
		addRoom(3,"Ort","Raumname",5);
		addRoom(4,"Ort","Raumname",18);
		
		addCalendarEntry(1 ,"Testtermin", "Terminbeschreibung", "01.05.2013", "07:30", "01.05.2013", "08:00", 30.0 , 1, 1, 1);
		addCalendarEntry(2 ,"Testtermin", "Terminbeschreibung", "01.05.2013", "12:00", "01.05.2013", "13:00", 60.0 , 1, 1, 2);
		addCalendarEntry(3 ,"Testtermin", "Terminbeschreibung", "01.05.2013", "14:30", "01.05.2013", "16:30", 120.0, 1, 1, 3);
		addCalendarEntry(4 ,"Testtermin", "Terminbeschreibung", "02.05.2013", "18:30", "02.05.2013", "18:45", 15.0 , 1, 1, 4);
		addCalendarEntry(5 ,"Testtermin", "Terminbeschreibung", "03.05.2013", "11:30", "03.05.2013", "13:30", 120.0, 1, 1, 1);
		addCalendarEntry(6 ,"Testtermin", "Terminbeschreibung", "03.05.2013", "06:00", "03.05.2013", "06:15", 15.0 , 1, 1, 2);
		addCalendarEntry(7 ,"Testtermin", "Terminbeschreibung", "03.05.2013", "08:30", "03.05.2013", "10:30", 120.0, 1, 1, 3);
		addCalendarEntry(8 ,"Testtermin", "Terminbeschreibung", "03.05.2013", "14:30", "03.05.2013", "15:30", 60.0 , 1, 1, 4);
		addCalendarEntry(9 ,"Testtermin", "Terminbeschreibung", "03.05.2013", "16:00", "03.05.2013", "16:30", 30.0 , 1, 1, 1);
		addCalendarEntry(10,"Testtermin", "Terminbeschreibung", "03.05.2013", "17:00", "03.05.2013", "18:30", 90.0 , 1, 1, 1);

		addCalendarEntry(11,"Testtermin", "Terminbeschreibung", "01.05.2013", "07:30", "01.05.2013", "08:00", 30.0 , 2, 2, 1);
		addCalendarEntry(12,"Testtermin", "Terminbeschreibung", "01.05.2013", "12:00", "01.05.2013", "13:00", 60.0 , 2, 2, 2);
		addCalendarEntry(13,"Testtermin", "Terminbeschreibung", "01.05.2013", "14:30", "01.05.2013", "16:30", 120.0, 2, 2, 3);
		addCalendarEntry(14,"Testtermin", "Terminbeschreibung", "02.05.2013", "18:30", "02.05.2013", "18:45", 15.0 , 2, 2, 4);
		addCalendarEntry(15,"Testtermin", "Terminbeschreibung", "03.05.2013", "11:30", "03.05.2013", "13:30", 120.0, 2, 2, 1);
		addCalendarEntry(16,"Testtermin", "Terminbeschreibung", "03.05.2013", "06:00", "03.05.2013", "06:15", 15.0 , 2, 2, 2);
		addCalendarEntry(17,"Testtermin", "Terminbeschreibung", "03.05.2013", "08:30", "03.05.2013", "10:30", 120.0, 2, 2, 3);
		addCalendarEntry(18,"Testtermin", "Terminbeschreibung", "03.05.2013", "14:30", "03.05.2013", "15:30", 60.0 , 2, 2, 4);
		addCalendarEntry(19,"Testtermin", "Terminbeschreibung", "03.05.2013", "16:00", "03.05.2013", "16:30", 30.0 , 2, 2, 1);
		addCalendarEntry(20,"Testtermin", "Terminbeschreibung", "03.05.2013", "17:00", "03.05.2013", "18:30", 90.0 , 2, 2, 1);
		
		addCalendar(1, "Kalenderbezeichnung", 1, calendarEntries.subList(0, 10));
		addCalendar(2, "Kalenderbezeichnung", 2, calendarEntries.subList(10, 20));
	}
	
	private void addCalendarEntry(int id, String titel, String description, String startday, String starttime, String endday, String endtime, double duration, int calendarId, int ownerId, int roomId){
		CalendarEntry ce = new CalendarEntry();
		ce.setID(id);
		
		ce.setTitle(titel);
		ce.setDescription(description);
		ce.setStartDate(CalendarEntry.parseStringToDate(startday, starttime));
		ce.setEndDate(CalendarEntry.parseStringToDate(endday, endtime));
		ce.setDuration(duration);
		
		ce.setCalendarId(calendarId);
		ce.setOwnerId(ownerId);
		ce.setRoomId(roomId);
		calendarEntries.add(ce);
	}
	private void addRoom(int id, String location, String description, int seats){
		Room room = new Room();
		room.setID(id);
		room.setLocation(location);
		room.setSeats(seats);
		room.setDescription(description);
		rooms.add(room);
	}
	private void addCalendar(int id, String description, int ownerId, List<CalendarEntry> entries){
		Calendar ca = new Calendar();
		ca.setID(id);
		ca.setDescription(description);
		ca.setOwnerId(ownerId);
		ca.setCalendarEntries(entries);
		calendar.add(ca);
	}
	private void addUser(int id, String surname, String givenname, String phone, String mail){
		User user = new User();
		user.setID(id);
		List<Integer> caIds = new ArrayList<Integer>();
		caIds.add(1);
		user.setCalendarIds(caIds);
		user.setGivenname(givenname);
		user.setSurname(surname);
		user.setPhoneNumber(phone);
		user.setMailAddress(mail);
		this.userList.add(user);
	}
	
	public Calendar getCalendarById(int id){
		for(Calendar ca: calendar){
			if(ca.getID() == id) return ca;
		}
		return null;
	}
	public CalendarEntry getCalendarEntryById(int id){
		for(CalendarEntry ce: calendarEntries){
			if(ce.getID() == id) return ce;
		}
		return null;
	}
	public User getUserById(int id){
		for(User u: userList){
			if(u.getID() == id) return u;
		}
		return null;
	}
	public Room getRoomById(int id){
		for(Room r: rooms){
			if(r.getID() == id) return r;
		}
		return null;
	}
	
	public List<Calendar> getAllCalendar(){
		return calendar;
	}
	public List<CalendarEntry> getAllCalendarEntries(){
		return calendarEntries;
	}
	public List<User> getAllUser(){
		return userList;
	}
	public List<Room> getAllRooms(){
		return rooms;
	}
	
	public List<User> getUserByProperty(String fieldName, Object value){
		List<User> tmp = new ArrayList<>();
		for(User u: userList){
			try {
				Field[] fields = u.getClass().getDeclaredFields();
				Field[] superFields = u.getClass().getSuperclass().getDeclaredFields();	
				if(iterateThroughFields(u, fields, fieldName, value)) tmp.add(u);
				if(iterateThroughFields(u, superFields, fieldName, value)) tmp.add(u);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return tmp;
	}
	public List<Calendar> getCalendarByProperty(String fieldName, Object value){
		List<Calendar> tmp = new ArrayList<>();
		for(Calendar ca: calendar){
			try {
				Field[] fields = ca.getClass().getDeclaredFields();
				Field[] superFields = ca.getClass().getSuperclass().getDeclaredFields();	
				if(iterateThroughFields(ca, fields, fieldName, value)) tmp.add(ca);
				if(iterateThroughFields(ca, superFields, fieldName, value)) tmp.add(ca);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return tmp;
	}
	public List<CalendarEntry> getCalendarEntriesByProperty(String fieldName, Object value){
		List<CalendarEntry> tmp = new ArrayList<>();
		for(CalendarEntry ca: calendarEntries){
			try {
				Field[] fields = ca.getClass().getDeclaredFields();
				Field[] superFields = ca.getClass().getSuperclass().getDeclaredFields();	
				if(iterateThroughFields(ca, fields, fieldName, value)) tmp.add(ca);
				if(iterateThroughFields(ca, superFields, fieldName, value)) tmp.add(ca);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return tmp;
	}
	public List<Room> getRoomsByProperty(String fieldName, Object value){
		List<Room> tmp = new ArrayList<>();
		for(Room r: rooms){
			try {
				Field[] fields = r.getClass().getDeclaredFields();
				Field[] superFields = r.getClass().getSuperclass().getDeclaredFields();	
				if(iterateThroughFields(r, fields, fieldName, value)) tmp.add(r);
				if(iterateThroughFields(r, superFields, fieldName, value)) tmp.add(r);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return tmp;
	}
	
	private boolean iterateThroughFields(Object obj, Field[] fields,
			String fieldName, Object value) throws IllegalArgumentException,
			IllegalAccessException, SecurityException {
		
		for (Field f : fields) {
			if (!f.getName().equals(fieldName))
				continue;
			f.setAccessible(true);
			if (f.get(obj).equals(value)) {
				f.setAccessible(false);
				return true;
			}
			f.setAccessible(false);
		}
		return false;
	}
	
}
