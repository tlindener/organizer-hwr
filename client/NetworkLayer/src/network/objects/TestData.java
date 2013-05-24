package network.objects;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import java.util.List;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;

import organizer.objects.types.Room;
import organizer.objects.types.User;

/**
 * @author Steffen Baumann
 *
 */
public class TestData implements Comparator<AbstractOrganizerObject>{
	List<CalendarEntry> calendarEntries = new ArrayList<>();
	List<Room> rooms = new ArrayList<>();
	List<User> userList = new ArrayList<>();
	List<Calendar> calendar = new ArrayList<>();
	
	public TestData(){		

		addUser("Mustermann", "Max", "0180/1234567", "max.mustermann@web.de");
		addUser("Normalverbraucher", "Otto", "030/1234567", "otto.normalverbrauher@web.de");
		
		addRoom("Ort","Raumname",25);
		addRoom("Ort","Raumname",12);
		addRoom("Ort","Raumname",5);
		addRoom("Ort","Raumname",18);
		
		addCalendarEntry("Testtermin", "Terminbeschreibung", "01.05.2013", "07:30", "01.05.2013", "08:00", 1, 1, 1);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "01.05.2013", "12:00", "01.05.2013", "13:00", 1, 1, 2);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "01.05.2013", "14:30", "01.05.2013", "16:30", 1, 1, 3);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "02.05.2013", "18:30", "02.05.2013", "18:45", 1, 1, 4);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "03.05.2013", "11:30", "03.05.2013", "13:30", 1, 1, 1);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "03.05.2013", "06:00", "03.05.2013", "06:15", 1, 1, 2);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "03.05.2013", "08:30", "03.05.2013", "10:30", 1, 1, 3);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "03.05.2013", "14:30", "03.05.2013", "15:30", 1, 1, 4);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "03.05.2013", "16:00", "03.05.2013", "16:30", 1, 1, 1);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "03.05.2013", "17:00", "03.05.2013", "18:30", 1, 1, 1);

		addCalendarEntry("Testtermin", "Terminbeschreibung", "01.05.2013", "07:30", "01.05.2013", "08:00", 2, 2, 1);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "01.05.2013", "12:00", "01.05.2013", "13:00", 2, 2, 2);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "01.05.2013", "14:30", "01.05.2013", "16:30", 2, 2, 3);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "02.05.2013", "18:30", "02.05.2013", "18:45", 2, 2, 4);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "03.05.2013", "11:30", "03.05.2013", "13:30", 2, 2, 1);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "03.05.2013", "06:00", "03.05.2013", "06:15", 2, 2, 2);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "03.05.2013", "08:30", "03.05.2013", "10:30", 2, 2, 3);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "03.05.2013", "14:30", "03.05.2013", "15:30", 2, 2, 4);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "03.05.2013", "16:00", "03.05.2013", "16:30", 2, 2, 1);
		addCalendarEntry("Testtermin", "Terminbeschreibung", "03.05.2013", "17:00", "03.05.2013", "18:30", 2, 2, 1);
		
		addCalendar("Kalenderbezeichnung", 1, calendarEntries.subList(0, 10));
		addCalendar("Kalenderbezeichnung", 2, calendarEntries.subList(10, 20));
	}
	
	public void addCalendarEntry(String titel, String description, String startday, String starttime, String endday, String endtime, int calendarId, int ownerId, int roomId){
		CalendarEntry ce = new CalendarEntry();
		
		ce.setID(nextID(calendarEntries));
		
		ce.setTitle(titel);
		ce.setDescription(description);
		ce.setStartDate(CalendarEntry.parseStringToDate(startday, starttime));
		ce.setEndDate(CalendarEntry.parseStringToDate(endday, endtime));
		
		ce.setCalendarId(calendarId);
		ce.setOwnerId(ownerId);
		ce.setRoomId(roomId);
		calendarEntries.add(ce);
	}
	private <T extends AbstractOrganizerObject> int nextID(List<T> list) {
		if(list.isEmpty()) return 1;
		Collections.sort(list, this);
		return list.get(list.size()-1).getID()+1;
	}

	public void addRoom(String location, String description, int seats){
		Room room = new Room();
		room.setID(nextID(rooms));
		room.setLocation(location);
		room.setSeats(seats);
		room.setDescription(description);
		rooms.add(room);
	}
	public void addCalendar(String description, int ownerId, List<CalendarEntry> entries){
		Calendar ca = new Calendar();
		ca.setID(nextID(calendar));
		ca.setDescription(description);
		ca.setOwnerId(ownerId);
		ca.setCalendarEntries(entries);
		calendar.add(ca);
	}
	public void addUser(String surname, String givenname, String phone, String mail){
		User user = new User();
		user.setID(nextID(userList));
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

	public Object getAllCalendarEntriesByProperty(String[] property) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAllCalendarByProperty(String[] property) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAllRoomsByProperty(String[] property) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAllUserByProperty(String[] property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compare(AbstractOrganizerObject o1, AbstractOrganizerObject o2) {
		return o1.getID()-o2.getID();
	}
	
//	public List<User> getUserByProperty(String fieldName, Object value){
//		List<User> tmp = new ArrayList<>();
//		for(User u: userList){
//			try {
//				Field[] fields = u.getClass().getDeclaredFields();
//				Field[] superFields = u.getClass().getSuperclass().getDeclaredFields();	
//				if(iterateThroughFields(u, fields, fieldName, value)) tmp.add(u);
//				if(iterateThroughFields(u, superFields, fieldName, value)) tmp.add(u);
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			}
//		}
//		return tmp;
//	}
//	public List<Calendar> getCalendarByProperty(String fieldName, Object value){
//		List<Calendar> tmp = new ArrayList<>();
//		for(Calendar ca: calendar){
//			try {
//				Field[] fields = ca.getClass().getDeclaredFields();
//				Field[] superFields = ca.getClass().getSuperclass().getDeclaredFields();	
//				if(iterateThroughFields(ca, fields, fieldName, value)) tmp.add(ca);
//				if(iterateThroughFields(ca, superFields, fieldName, value)) tmp.add(ca);
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			}
//		}
//		return tmp;
//	}
//	public List<CalendarEntry> getCalendarEntriesByProperty(String fieldName, Object value){
//		List<CalendarEntry> tmp = new ArrayList<>();
//		for(CalendarEntry ca: calendarEntries){
//			try {
//				Field[] fields = ca.getClass().getDeclaredFields();
//				Field[] superFields = ca.getClass().getSuperclass().getDeclaredFields();	
//				if(iterateThroughFields(ca, fields, fieldName, value)) tmp.add(ca);
//				if(iterateThroughFields(ca, superFields, fieldName, value)) tmp.add(ca);
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			}
//		}
//		return tmp;
//	}
//	public List<Room> getRoomsByProperty(String fieldName, Object value){
//		List<Room> tmp = new ArrayList<>();
//		for(Room r: rooms){
//			try {
//				Field[] fields = r.getClass().getDeclaredFields();
//				Field[] superFields = r.getClass().getSuperclass().getDeclaredFields();	
//				if(iterateThroughFields(r, fields, fieldName, value)) tmp.add(r);
//				if(iterateThroughFields(r, superFields, fieldName, value)) tmp.add(r);
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			}
//		}
//		return tmp;
//	}
//	
//	private boolean iterateThroughFields(Object obj, Field[] fields,
//			String fieldName, Object value) throws IllegalArgumentException,
//			IllegalAccessException, SecurityException {
//		
//		for (Field f : fields) {
//			if (!f.getName().equals(fieldName))
//				continue;
//			f.setAccessible(true);
//			if (f.get(obj).equals(value)) {
//				f.setAccessible(false);
//				return true;
//			}
//			f.setAccessible(false);
//		}
//		return false;
//	}
	public static void main(String[] args) {
		TestData t = new TestData();
		List<Calendar> c = t.getAllCalendar();
		for(Calendar ca: c){
			System.out.println(ca.getID());
		}
	}
}
