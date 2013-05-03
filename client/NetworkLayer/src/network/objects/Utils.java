package network.objects;

import java.lang.reflect.Field;
import java.util.HashMap;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Group;
import organizer.objects.types.Room;
import organizer.objects.types.User;

public class Utils {

	private static HashMap<Class<? extends AbstractOrganizerObject>, String> plurals;
	private static HashMap<String, String> property;
	
	static {
		plurals = new HashMap<>();
		plurals.put(User.class, "User");
		plurals.put(Group.class, "Groups");
		plurals.put(Calendar.class, "Calendar");
		plurals.put(CalendarEntry.class, "CalendarEntries");
		plurals.put(Room.class, "Rooms");

		property = new HashMap<>();
		property.put("roomid", "RoomId");
		property.put("ownerid", "OwnerId");
		property.put("calendarid", "CalendarId");
	}

	public static <T extends AbstractOrganizerObject> String buildGetCommand(
			T obj, ByProperty by) throws IllegalArgumentException{
		
		if (!isFieldOf(obj, by.getFieldName())) {
			throw new IllegalArgumentException("ByProperty \""
					+ by.getFieldName() + "\" is not a field of "
					+ obj.getClass().getSimpleName());
		}
		if (property.get(by.getFieldName()) == null) {
			throw new IllegalArgumentException("No ByProperty for "
					+ by.getFieldName());
		}

		String fieldName = property.get(by.getFieldName());
		
		String command = "Get" + obj.getClass().getSimpleName() + "By"
				+ fieldName + "?" + fieldName + "=";

		if (by.getValue() instanceof String) {
			command += "\"" + by.getValue() + "\"";
		} else {
			command += by.getValue();
		}
		return command;
	}

	public static <T extends AbstractOrganizerObject> String buildGetByOwnIdCommand(
			T obj) {
		String className = obj.getClass().getSimpleName();
		String command = "Get" + className + "ById?" + className + "Id="
				+ obj.getID();
		return command;
	}

	public static <T extends AbstractOrganizerObject> String buildGetAllCommand(
			T obj) throws IllegalArgumentException{
		
		if (plurals.get(obj.getClass()) == null){
			throw new IllegalArgumentException("No plural for class " +obj.getClass().getSimpleName());
		}
		String command = "GetAll" + plurals.get(obj.getClass());
		return command;
	}
	
	public static <T extends AbstractOrganizerObject> String buildAddCommand(
			T obj){
		String command = "Add" + obj.getClass().getSimpleName()+"?";
		return command;
	}

	public static <T extends AbstractOrganizerObject> boolean isFieldOf(T obj,
			String fieldName) {
		Field[] fields = obj.getClass().getDeclaredFields();
		Field[] superFields =obj.getClass().getSuperclass().getDeclaredFields();
		return (iterateThroughFields(fields, fieldName) || iterateThroughFields(superFields, fieldName));

	}
	private static boolean iterateThroughFields(Field[] fields, String fieldName){
		for (Field f : fields) {
			if (f.getName().equalsIgnoreCase(fieldName)){
				return true;
			}
		}
		return false;
	}
	
}
