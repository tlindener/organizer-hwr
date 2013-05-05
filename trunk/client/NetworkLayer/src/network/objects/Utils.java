package network.objects;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Group;
import organizer.objects.types.Room;
import organizer.objects.types.User;

public class Utils {

	private static HashMap<Class<? extends AbstractOrganizerObject>, String> plurals;
	
	static {
		plurals = new HashMap<>();
		plurals.put(User.class, "User");
		plurals.put(Group.class, "Groups");
		plurals.put(Calendar.class, "Calendar");
		plurals.put(CalendarEntry.class, "CalendarEntries");
		plurals.put(Room.class, "Rooms");
	}

	public static <T extends AbstractOrganizerObject> String buildGetByPropertyCommand(
			T obj) throws IllegalArgumentException{
		String command = buildGetAllCommand(obj)+ obj.getProperty();
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
			T obj) throws IllegalArgumentException{
		String command = "Add" + obj.getClass().getSimpleName() + "?";
		Field[] fields = obj.getClass().getDeclaredFields();
		ArrayList<String> parameters = new ArrayList<>();
		
		for (Field f : fields){
			if(f.getModifiers() != Modifier.PRIVATE) continue;
			f.setAccessible(true);
			try {
				Object value = f.get(obj);
//				if(value == null){
//					//TODO Fehler anpassen
//					throw new IllegalArgumentException("Darf nicht null sein");
//				}
				if(value instanceof List<?>){
					continue;
				}else if(value instanceof String){
					parameters.add(f.getName() + "=" + "\"" + value + "\"");
				}else if(value instanceof Date){
					parameters.add(f.getName() + "=" + parseDateToNetDateTime((Date) value));
				}else {
					parameters.add(f.getName() + "=" + value);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			f.setAccessible(false);
		}
		
		for(int i = 0; i < parameters.size(); i++){
			command+=parameters.get(i);
			if(i < parameters.size() -1) command += "&";
		}
		
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
	private static String parseDateToNetDateTime(Date date){
		return "\\/Date("+date.getTime()+"+0000)\\/";
	}
}
