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
	
	/**
	 * Adds a new {@link ProcessListener} to this {@link RequestHandler}. 
	 * @param processListener to be added
	 * @return true if the {@link ProcessListener} was added, otherwise false
	 * @throws IllegalArgumentException if the {@link ProcessListener} is null
	 */
	public boolean addProcessListener(ProcessListener processListener){
		if(processListener == null) throw new IllegalArgumentException("ProcessListener must not be null");
		return this.processListeners.add(processListener);
	}
	/**
	 * Returns all current registered {@link ProcessListener}s for this {@link RequestHandler}
	 * @return {@link List}<{@link ProcessListener}> containing the registered listeners
	 */
	public List<ProcessListener> getProcessListeners(){
		return this.processListeners;
	}	
	/**
	 * Removes a {@link ProcessListener} of this {@link RequestHandler}. 
	 * @param processListener to be removed
	 * @return true if the {@link ProcessListener} was removed, otherwise false
	 * @throws IllegalArgumentException if the {@link ProcessListener} is null
	 */
	public boolean removeProcessListener(ProcessListener processListener){
		if(processListener == null) throw new IllegalArgumentException("ProcessListener must not be null");
		return this.processListeners.remove(processListener);
	}
	/**
	 * Sends the update to each currently registered {@link ProcessListener}
	 * @param process value between 0 and 1 representing the current status of the process
	 */
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

	/**
	 * Marks an Invite as accepted on the server<br>
	 * For more information have a look on the implementing Class
	 */
	public abstract int acceptInvite(Invite invite);
	
	/**
	 * Marks an Invite as declined on the server<br>
	 * For more information have a look on the implementing Class
	 */
	public abstract int declineInvite(Invite invite);
	
	/**
	 * Updates an object<br>
	 * For more information have a look on the implementing Class
	 */
	public abstract <T extends AbstractOrganizerObject> boolean updateObject(T obj);
	
	/**
	 * Adds an User to a group <br>
	 * For more information have a look on the implementing Class
	 */
	public abstract <T extends AbstractOrganizerObject> boolean addUserToGroup(User user, Group group);
	
	/**
	 * Removes an user from a group <br>
	 * For more information have a look on the implementing Class
	 */
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
	
	/**
	 * Drops the database of the server. This method is only used by the JUnit-Tests<br>
	 * For more information have a look on the implementing Class
	 */
	public abstract boolean dropDatabase();
}
