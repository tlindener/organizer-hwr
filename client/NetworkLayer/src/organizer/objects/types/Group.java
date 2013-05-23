/**
 * 
 */
package organizer.objects.types;

import java.util.ArrayList;
import java.util.List;

import organizer.objects.AbstractOrganizerObject;

/**
 * This class represents a group from the database with all attributes.
 * @author Steffen Baumann
 *
 */
public class Group extends AbstractOrganizerObject {
	
	public static final String USER_ID = "UserId";
	/** the description of the group*/
	private String description = "";
	/** list of all user IDs contained by the group*/
	private List<Integer> members = new ArrayList<Integer>();
	
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
	 * @return the users
	 */
	public List<Integer> getMembers() {
		return members;
	}

	/**
	 * @param users the users to set
	 */
	public void setMembers(List<Integer> members) {
		this.members = members;
	}

	@Override
	public String getProperty() throws IllegalArgumentException {
		switch(byProperty){
		case USER_ID: return "By"+USER_ID+"?"+USER_ID+"="+byValue;
		default: throw new IllegalArgumentException("No available Property for " + getClass().getSimpleName());
		}
		
	}
}
