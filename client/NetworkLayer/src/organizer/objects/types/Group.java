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
	private List<Integer> memberIds = new ArrayList<Integer>();
	/** the name of the group*/
	private String name = "";
	
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
	public List<Integer> getMemberIds() {
		return memberIds;
	}

	/**
	 * @param members the user IDs to set
	 */
	public void setMemberIds(List<Integer> members) {
		this.memberIds = members;
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

	@Override
	public String[] getProperty() throws IllegalArgumentException {
//		JDK 1.6
		if(byProperty.equals(USER_ID)){
			return new String[]{USER_ID, byValue};
		}
		throw new IllegalArgumentException(byProperty + " is an unkown Property for " + this.getClass().getSimpleName());
		
//		JDK 1.7
//		switch(byProperty){
//		case USER_ID: return new String[]{USER_ID, byValue};
//		default: throw new IllegalArgumentException("No available Property for " + getClass().getSimpleName());
//		}
		
	}
}
