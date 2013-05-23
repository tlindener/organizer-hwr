package organizer.objects.types;

import java.util.ArrayList;
import java.util.List;

import organizer.objects.AbstractOrganizerObject;
/**
 * This class represents a calendar from the database with all attributes.
 * 
 * @author Steffen Baumann
 * @version 1.0 
 *
 */
public class Calendar extends AbstractOrganizerObject{
	/** the user IDs owning the calendar*/
	private int ownerId = -1;
	/** the name of the calendar*/
	private String name = "";
	/** the description of the calendar*/
	private String description = "";
	/** list of all entry IDs contained by the calendar*/
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	
	@Override
	public String getProperty() throws IllegalArgumentException {
		throw new IllegalArgumentException("No available Property for " + getClass().getSimpleName());
	}
}
