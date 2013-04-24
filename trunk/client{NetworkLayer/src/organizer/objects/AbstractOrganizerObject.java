package organizer.objects;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

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
public abstract class AbstractOrganizerObject {
	
	private int id = -1;
	
	public void setID(int id){
		this.id = id;
	}
	public int getID(){
		return this.id;
	}
	@Override
	public String toString(){
		
		StringBuilder builder = new StringBuilder("{" + this.getClass().getSimpleName()+ " [");
		
		Field[] parentFields = this.getClass().getSuperclass().getDeclaredFields();
		builder = addFieldValuePairs(builder, parentFields);
		
		Field[] fields = this.getClass().getDeclaredFields();
		builder = addFieldValuePairs(builder, fields);
		
		builder.append("]}");
		return builder.toString();
	}
	
	private StringBuilder addFieldValuePairs(StringBuilder builder, Field[] fields){
		for(Field f: fields){
			try {
				f.setAccessible(true);
				String value = replaceReferences(f);
				builder.append(f.getName() + ":" + value + " | ");
				f.setAccessible(false);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return builder;
	}
	private String replaceReferences(Field field) throws IllegalArgumentException, IllegalAccessException{
		//Abfrage der Vaterklasse
		Class<?> superclass = field.getType().getSuperclass();
		//TODO Listen abfangen
		//nur bei Feld belegt && Vaterklasse vorhanden && Vaterklasse = AbstractOrganizerObject
		if(field.get(this) != null && superclass != null && superclass.equals(AbstractOrganizerObject.class)){
			return ""+((AbstractOrganizerObject)field.get(this)).getID();
		}		
		return ""+field.get(this);
	}
	
	
}
