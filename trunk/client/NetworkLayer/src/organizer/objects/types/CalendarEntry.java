package organizer.objects.types;

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
public class CalendarEntry extends AbstractOrganizerObject{

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
	 * @param startDate the startDate to set
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
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
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
	 * @param description the description to set
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
	 * @param ownerId the ownerId to set
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
	 * @param calendarId the calendarId to set
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
	 * @param raumId the raumId to set
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
	 * @param duration the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}
		
}
