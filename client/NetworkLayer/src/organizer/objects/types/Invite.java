/**
 * 
 */
package organizer.objects.types;

import organizer.objects.AbstractOrganizerObject;

/**
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public class Invite extends AbstractOrganizerObject {

	public static final String OWNER_ID = "OwnerId";
	public static final String CALENDAR_ENTRY_ID = "CalendarEntryId";
	
	private int userId = 0;
	private int accepted = 0;
	private int calendarEntryId = 0;

	/**
	 * @return the ownerId
	 */
	public int getOwnerId() {
		return userId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(int ownerId) {
		this.userId = ownerId;
	}

	/**
	 * @return the accepted
	 */
	public int isAccepted() {
		return accepted;
	}

	/**
	 * @param accepted the accepted to set
	 */
	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	/**
	 * @return the calendarEntryId
	 */
	public int getCalendarEntryId() {
		return calendarEntryId;
	}

	/**
	 * @param calendarEntryId the calendarEntryId to set
	 */
	public void setCalendarEntryId(int calendarEntryId) {
		this.calendarEntryId = calendarEntryId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see organizer.objects.AbstractOrganizerObject#getProperty()
	 */
	@Override
	public String[] getProperty() throws IllegalArgumentException {
			throw new IllegalArgumentException(byProperty + " is an unkown Property for " + this.getClass().getSimpleName());
	}

}
