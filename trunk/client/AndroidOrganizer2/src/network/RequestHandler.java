package network;

import java.util.ArrayList;
import java.util.List;

import network.listener.ProcessListener;
import network.utilities.ParseUtils;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Group;
import organizer.objects.types.Invite;
import organizer.objects.types.User;

/**
 * Abstract interface to request objects from a source, that must be specified by the exact implementation.
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public abstract class RequestHandler{
	
	private List<ProcessListener> processListeners = new ArrayList<ProcessListener>();
	
	public void addProcessListener(ProcessListener processListener){
		if(processListener == null) throw new IllegalArgumentException("ProcessListener must not be null");
		this.processListeners.add(processListener);
	}
	
	public List<ProcessListener> getProcessListeners(){
		return this.processListeners;
	}	
	
	public void removeProcessListener(ProcessListener processListener){
		this.processListeners.remove(processListener);
	}
	
	protected void fireProcessUpdate(double process){
		for(ProcessListener listener : processListeners){
			listener.getCurrentProcessState(process);
		}
	}
	
	/**
	 * String for the authentication of the user
	 */
	protected String authString = "";

	/**
	 * Requests the first object of a list by its set property <br>
	 * For more information have a look on the implementing Class
	 */
	public abstract <T extends AbstractOrganizerObject> T requestObjectByProperty(
			T obj);

	/**
	 * Requests an Object by its id <br>
	 * For more information have a look on the implementing Class
	 */
	public abstract <T extends AbstractOrganizerObject> T requestObjectByOwnId(
			T obj);

	public abstract <T extends AbstractOrganizerObject> List<T> requestFollowingObjectsByOwnId(List<Integer> ids, T obj);
	
	/**
	 * Requests a list of objects <br>
	 * For more information have a look on the implementing Class
	 */
	public abstract <T extends AbstractOrganizerObject> List<T> requestAllObjects(
			T obj);

	/**
	 * Requests a list of objects by the set property <br>
	 * For more information have a look on the implementing Class
	 */
	public abstract <T extends AbstractOrganizerObject> List<T> requestAllObjectsByProperty(
			T obj);

	/**
	 * Adds an object <br>
	 * For more information have a look on the implementing Class
	 */
	public abstract <T extends AbstractOrganizerObject> T addObject(T obj)
			throws UnsupportedOperationException;

	/**
	 * Register a new user <br>
	 * For more information have a look on the implementing Class
	 */
	public abstract User registerNewUser(User user, String password);

	/**
	 * Removes an object by its own ID <br>
	 * For more information have a look on the implementing Class
	 */
	public abstract <T extends AbstractOrganizerObject> boolean removeObjectByOwnId(
			T obj) throws UnsupportedOperationException;

	/**
	 * Requests an user for the given credentials <br>
	 * For more information have a look on the implementing Class
	 */
	public abstract User login(String mail, String password);

	public abstract int acceptInvite(Invite invite);
	
	public abstract int declineInvite(Invite invite);
	
	public abstract <T extends AbstractOrganizerObject> boolean updateObject(T obj);
	
	public abstract <T extends AbstractOrganizerObject> boolean addUserToGroup(User user, Group group);
	
	public abstract <T extends AbstractOrganizerObject> boolean removeUserFromGroup(User user, Group group);
	
	/**
	 * Generates an authentication String containing of mail and password.
	 * Therefore the mail address and the password are encoded by
	 * {@link ParseUtils#hashString(String)} and joined.
	 * @param id 
	 * 
	 * @param mail
	 * @param password
	 * @return the generated String
	 */
	protected String generateAuthenticationString(int id, String mail, String password) {
		return ParseUtils.parseStringToHTTP(id+"_"+ParseUtils.hashString(mail + ParseUtils.hashString(password)));
	}
}
