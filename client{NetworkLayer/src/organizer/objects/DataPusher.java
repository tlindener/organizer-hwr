package organizer.objects;

import network.RequestHandler;

/**
 * Standardschnittstelle, die erfüllt werden muss, um über den
 * {@link RequestHandler} ausführbar zu sein. Dabei stellt sie die minimale
 * Anforderung dar, über die ein Input gesetzt werden kann.
 * 
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public abstract class DataPusher {
	
	private int id = -1;
	
	public void setID(int id){
		this.id = id;
	}
	public int getID(){
		return this.id;
	}
	
	public abstract void setData(String... data);
	public abstract String[] getData();
	@Override
	public String toString(){
		return this.getClass().getSimpleName();
	}
}
