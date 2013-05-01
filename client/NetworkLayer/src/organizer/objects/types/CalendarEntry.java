package organizer.objects.types;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import organizer.objects.AbstractOrganizerObject;

/**
 * Beispielobjekt Termin
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public class CalendarEntry extends AbstractOrganizerObject {

	private Date startDate = null;
	private Date endDate = null;
	private String title = "";
	private String description = "";

	private int ownerId = -1;
	private int calendarId = -1;
	private int roomId = -1;

	private double duration = -1.0;

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
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}
	
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
	
	public static Calendar convertDateToCalendar(Date date) {
		Calendar tmp = new GregorianCalendar();
		tmp.setTime(date);
		return tmp;
	}
	
	public Date setTimeToDate(Date date, String time){
		String[] splitt = time.split(":");
		Calendar cal = convertDateToCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitt[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(splitt[1]));
		return cal.getTime();
	}
	
}
