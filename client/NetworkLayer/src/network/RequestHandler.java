package network;

import java.util.List;

import network.objects.Utils;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.User;

/**
 * Abstract interface to request objects from a source, that must be specified by the exact implementation.
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public abstract class RequestHandler {
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
	 * Generates an authentication String containing of mail and password.
	 * Therefore the mail address and the password are encoded by
	 * {@link Utils#encodeString(String)} and joined.
	 * 
	 * @param mail
	 * @param password
	 * @return the generated String
	 */
	protected String generateAuthenticationString(String mail, String password) {
		return Utils.encodeString(mail) + Utils.encodeString(password);
	}
}
