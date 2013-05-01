package network;

import java.util.List;

import network.objects.ByProperty;

import organizer.objects.AbstractOrganizerObject;

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
	public abstract <T extends AbstractOrganizerObject> List<T> requestObjects(T obj, ByProperty by);
	public abstract <T extends AbstractOrganizerObject> T requestObjectByOwnId(T obj);
	public abstract <T extends AbstractOrganizerObject> List<T> requestAllObjects(T obj);
}
