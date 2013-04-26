package organizer.objects.types;

import java.util.ArrayList;
import java.util.List;

import organizer.objects.AbstractOrganizerObject;

public class Calendar extends AbstractOrganizerObject{
	private int ownerId = -1;
	private String description = "";
	private List<CalendarEntry> calendarEntries = new ArrayList<>();
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
	 * @return the calendarEntries
	 */
	public List<CalendarEntry> getCalendarEntries() {
		return calendarEntries;
	}
	/**
	 * @param calendarEntries the calendarEntries to set
	 */
	public void setCalendarEntries(List<CalendarEntry> calendarEntries) {
		this.calendarEntries = calendarEntries;
	}
}
