package organizer.objects.types;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import organizer.objects.AbstractOrganizerObject;

/**
 * This class represents an entry from the database with all attributes.
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public class CalendarEntry extends AbstractOrganizerObject {

	public static final String OWNER_ID = "OwnerId";
	public static final String ROOM_ID = "RoomId";
	
	/** start date of this entry */
	private Date startDate = null;
	/** end date of this entry */
	private Date endDate = null;
	/** title of this entry */
	private String title = "";
	/** description of this entry */
	private String description = "";
	/** the user id owning this entry */
	private int ownerId = -1;
	/** the calendar id containing this entry*/
	private int calendarId = -1;
	/** the room id for this entry*/
	private int roomId = -1;
	/** list of invited users for this entry*/			
	private List<User> invitees = new ArrayList<User>();

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the start hour of this {@link CalendarEntry}
	 */
	public int getStartHour() {
		return convertDateToCalendar(startDate).get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * @return the end hour of this {@link CalendarEntry}
	 */
	public int getEndHour() {
		return convertDateToCalendar(endDate).get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * @return the start minute of this {@link CalendarEntry}
	 */
	public int getStartMinute() {
		return convertDateToCalendar(startDate).get(Calendar.MINUTE);
	}

	/**
	 * @return the end minute of this {@link CalendarEntry}
	 */
	public int getEndMinute() {
		return convertDateToCalendar(endDate).get(Calendar.MINUTE);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the ownerId
	 */
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId
	 *            the ownerId to set
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the calendarId
	 */
	public int getCalendarId() {
		return calendarId;
	}

	/**
	 * @param calendarId
	 *            the calendarId to set
	 */
	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
	}

	/**
	 * @return the raumId
	 */
	public int getRoomId() {
		return roomId;
	}

	/**
	 * @param raumId
	 *            the raumId to set
	 */
	public void setRoomId(int raumId) {
		this.roomId = raumId;
	}

	/**
	 * @return the duration in minutes
	 */
	public long getDuration() {
		return ((endDate.getTime() - startDate.getTime()) / 1000 / 60);
	}
	/**
	 * Parses the String to a {@link Date}
	 * @param day in the format dd.mm.yyyy
	 * @param time in the format hh:mm
	 * @return the equivalent {@link Date}
	 */
	public static Date parseStringToDate(String day, String time){
		Date date = new Date();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
		try {
			date = df.parse(day + " " + time);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return date;
	}
	/**
	 * Converts a {@link Date} to {@link Calendar}
	 * @param date
	 * @return the {@link Calendar} representing the {@link Date}
	 */
	public static Calendar convertDateToCalendar(Date date) {
		Calendar tmp = new GregorianCalendar();
		tmp.setTime(date);
		return tmp;
	}
	
	/**
	 * Sets the given time to the given {@link Date}
	 * @param date to update
	 * @param time to set to the date
	 * @return modified {@link Date}
	 */
	public Date setTimeToDate(Date date, String time){
		String[] splitt = time.split(":");
		Calendar cal = convertDateToCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitt[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(splitt[1]));
		return cal.getTime();
	}

	/**
	 * @return the invitees
	 */
	public List<User> getInvitees() {
		return invitees;
	}

	/**
	 * @param invitees the invitees to set
	 */
	public void setInvitees(List<User> invitees) {
		this.invitees = invitees;
	}

	@Override
	public String[] getProperty() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	
}
