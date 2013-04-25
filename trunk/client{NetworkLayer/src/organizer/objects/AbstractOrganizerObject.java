package organizer.objects;

import java.lang.reflect.Field;
import java.util.Collection;

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
//		Name of the current class
		StringBuilder builder = new StringBuilder("{" + this.getClass().getSimpleName()+ "");
		
//		Attributes of the parent class
		Field[] parentFields = this.getClass().getSuperclass().getDeclaredFields();
		builder = addFieldValuePairs(builder, parentFields);
//		Attributes of the current class
		Field[] fields = this.getClass().getDeclaredFields();
		builder = addFieldValuePairs(builder, fields);

		builder.append("}");
		return builder.toString();
	}
	
	private StringBuilder addFieldValuePairs(StringBuilder builder, Field[] fields){
		for(Field f: fields){
			try {
				f.setAccessible(true);
				String value = checkType(f);
				builder.append("("+f.getName() + ":" + value + ")");
				f.setAccessible(false);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return builder;
	}
	private String checkType(Field field) throws IllegalArgumentException, IllegalAccessException{
		//null is returned every time
		if(field.get(this) == null){
			return null;
		}
		//primitive types or Strings are also returned 
		Class<?> type = field.getType();
		if(type.isPrimitive() || type.equals(String.class)){
			return "" + field.get(this);
		}
		//Collections (like List<?>) will be encapsulated by "<[" and "]>"
		if(Collection.class.isAssignableFrom(type)){
			return "<" + field.get(this) + ">";
		//elements of AbstractOrganizerObject will be replaced by their IDs
		}else if(AbstractOrganizerObject.class.isAssignableFrom(type)){
			return ""+AbstractOrganizerObject.class.cast(field.get(this)).getID();
		//an different class is used	
		}else{
			return "UNEXPECTED";
		}
	}
	
	
}
