/**
 * 
 */
package network.objects;

/**
 * @author Steffen Baumann
 * @version 1.0 
 *
 */
public class ByProperty {

	private String fieldName = "";
	private Object value = null;
	
	/**
	 * 
	 */
	public ByProperty(String fieldName, Object value) {
		this.fieldName = fieldName.toLowerCase();;
		this.value = value;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

}
