package network;

import java.util.List;

import network.objects.Utils;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.User;

/**
 * Schnittstelle zum Abfragen von Objekten. Der Type des Eingabeobjekts
 * entspricht dem Ausgabeobjekt - Java Generics. Dadurch wird ein Cast-Befehl
 * auf Seiten des Aufrufers vermieden.
 * 
 * Der RequestHandler stellt eine Anfrage gem�� des Eingabeobjekts. �ber Input
 * wird das Eingabeobjekt ver�ndert und zur�ckgegeben.
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public abstract class RequestHandler {
	
	protected String authString = "";
	
	public abstract <T extends AbstractOrganizerObject> T requestObjectByProperty(T obj);
	public abstract <T extends AbstractOrganizerObject> T requestObjectByOwnId(T obj);
	public abstract <T extends AbstractOrganizerObject> List<T> requestAllObjects(T obj);
	public abstract <T extends AbstractOrganizerObject> List<T> requestAllObjectsByProperty(T obj);
	public abstract <T extends AbstractOrganizerObject> T addObject(T obj) throws UnsupportedOperationException;
	public abstract User registerNewUser(User user, String password);
	public abstract <T extends AbstractOrganizerObject> boolean removeObjectByOwnId(T obj) throws UnsupportedOperationException;
	public abstract User login(String mail, String password);
	
	protected String generateAuthentificationString(String mail, String password){
		return Utils.hashString(mail) + Utils.hashString(password);
	}
}
