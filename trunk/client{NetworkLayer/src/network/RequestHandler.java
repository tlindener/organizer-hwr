package network;

import java.util.List;

import organizer.objects.AbstractOrganizerObject;

/**
 * Schnittstelle zum Abfragen von Objekten. Der Type des Eingabeobjekts
 * entspricht dem Ausgabeobjekt - Java Generics. Dadurch wird ein Cast-Befehl
 * auf Seiten des Aufrufers vermieden.
 * 
 * Der RequestHandler stellt eine Anfrage gemäß des Eingabeobjekts. Über Input
 * wird das Eingabeobjekt verändert und zurückgegeben.
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public abstract class RequestHandler {
	public abstract <T extends AbstractOrganizerObject> T requestObject(T obj);
	public abstract <T extends AbstractOrganizerObject> List<T> requestAllObjects(T obj);
}
