/**
 * 
 */
package organizer.objects.types;

import java.util.ArrayList;
import java.util.List;

import organizer.objects.AbstractOrganizerObject;

/**
 * @author Steffen Baumann
 *
 */
public class User extends AbstractOrganizerObject {
	
	private String surname = "";
	private String givenname = "";
	private String mailAddress = "";
	private String phoneNumber = "";
	
	private List<Integer> groupIds = new ArrayList<>();
	private List<Integer> calendarIds = new ArrayList<>();
	
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the givenname
	 */
	public String getGivenname() {
		return givenname;
	}
	/**
	 * @param givenname the givenname to set
	 */
	public void setGivenname(String givenname) {
		this.givenname = givenname;
	}
	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}
	/**
	 * @param mailAddress the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the groupIds
	 */
	public List<Integer> getGroupIds() {
		return groupIds;
	}
	/**
	 * @param groupIds the groupIds to set
	 */
	public void setGroupIds(List<Integer> groupIds) {
		this.groupIds = groupIds;
	}
	/**
	 * @return the calendarIds
	 */
	public List<Integer> getCalendarIds() {
		return calendarIds;
	}
	/**
	 * @param calendarIds the calendarIds to set
	 */
	public void setCalendarIds(List<Integer> calendarIds) {
		this.calendarIds = calendarIds;
	}

}
