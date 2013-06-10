package organizer.objects;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

//import network.RequestHandler;

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

	/** the property of the object*/
	protected String byProperty = null;
	/** the value of the objects property*/
	protected String byValue = null;
	/** the id of the object */
	private int id = -1;
	/**
	 * @param id the id to set
	 */
	public void setID(int id) {
		this.id = id;
	}
	/**
	 * @return id
	 */
	public int getID() {
		return this.id;
	}

	@Override
	public String toString() {
		// Name of the current class
		StringBuilder builder = new StringBuilder("{"
				+ this.getClass().getSimpleName() + "");

		// Attributes of the parent class
		Field[] parentFields = this.getClass().getSuperclass()
				.getDeclaredFields();
		builder = addFieldValuePairs(builder, parentFields);
		// Attributes of the current class
		Field[] fields = this.getClass().getDeclaredFields();
		builder = addFieldValuePairs(builder, fields);

		builder.append("}");
		return builder.toString();
	}
	/**
	 * Adds the {@link Field} - value pairs to the given {@link StringBuilder}
	 * @param builder the pairs are added to
	 * @param fields to be added
	 * @return the modified builder
	 */
	private StringBuilder addFieldValuePairs(StringBuilder builder,
			Field[] fields) {
		for (Field f : fields) {
			if (f.getModifiers() != Modifier.PRIVATE)
				continue;
			try {
				f.setAccessible(true);
				String value = checkType(f);
				builder.append("(" + f.getName() + ":" + value + ")");
				f.setAccessible(false);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return builder;
	}
	/**
	 * Checks the type of a {@link Field} and convert it in a well formed syntax
	 * @param field
	 * @return String representation of the given {@link Field}
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String checkType(Field field) throws IllegalArgumentException,
			IllegalAccessException {
		// null is returned every time
		if (field.get(this) == null) {
			return null;
		}
		// primitive types or Strings are also returned
		Class<?> type = field.getType();
		if (type.isPrimitive() || type.equals(String.class)) {
			return "" + field.get(this);
		}
		// Collections (like List<?>) will be encapsulated by "<[" and "]>"
		if (Collection.class.isAssignableFrom(type)) {
			return "<" + field.get(this) + ">";
			// elements of AbstractOrganizerObject will be replaced by their IDs
		} else if (AbstractOrganizerObject.class.isAssignableFrom(type)) {
			return ""
					+ AbstractOrganizerObject.class.cast(field.get(this))
							.getID();
			// an different class is used
		} else {
			return type.getSimpleName();
		}
	}

	/**
	 * Returns the set property
	 * 
	 * @return the property specification for GET-Commands or throws an
	 *         {@link IllegalArgumentException} if such a set property does not
	 *         exist.
	 * @throws IllegalArgumentException
	 */
	public abstract String[] getProperty() throws IllegalArgumentException;

	/**
	 * Sets the given property and value
	 * @param property to set
	 * @param value to set
	 */
	public void setRequestProperty(String property, String value) {
		this.byValue = value;
		this.byProperty = property;
	}

}
