package organizer.objects.types;

import organizer.objects.AbstractOrganizerObject;

public class Room extends AbstractOrganizerObject {
	private String description = "";
	private String location = "";
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
}
