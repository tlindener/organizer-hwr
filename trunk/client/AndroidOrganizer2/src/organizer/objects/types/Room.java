package organizer.objects.types;

import java.util.List;

import organizer.objects.AbstractOrganizerObject;
/**
 * This class represents a room from the database with all attributes.
 * @author Steffen Baumann
 * @version 1.0 
 *
 */
public class Room extends AbstractOrganizerObject {
	/** the description of the room*/
	private String description = "";
	/** the location of the room*/
	private String location = "";
	/** the number of seats of the room*/
	private int seats = -1;

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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the seats
	 */
	public int getSeats() {
		return seats;
	}

	/**
	 * @param seats
	 *            the seats to set
	 */
	public void setSeats(int seats) {
		this.seats = seats;
	}
	
	@Override
	public String[] getProperty() throws IllegalArgumentException {
		throw new IllegalArgumentException("No available Property for " + getClass().getSimpleName());
	}
}
