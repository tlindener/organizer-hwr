package view.renderer;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.listener.MyChangeListener;

import data.objects.AbstractOrganizerObject;
import data.objects.types.Room;
import data.objects.types.User;

public class MyCheckBoxListRenderer implements
		ListCellRenderer<AbstractOrganizerObject>, ChangeListener {
	/**
	 * Contains all User and their linked JCheckBoxes
	 */
	private HashMap<User, JCheckBox> mapUser = new HashMap<User, JCheckBox>();
	/**
	 * Contains all Rooms and their linked JCheckBoxes
	 */
	private HashMap<Room, JCheckBox> mapRoom = new HashMap<Room, JCheckBox>();
	/**
	 * Listener for the state changes of the checkboxes
	 */
	private MyChangeListener listener = null;

	public MyCheckBoxListRenderer(MyChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public Component getListCellRendererComponent(
			JList<? extends AbstractOrganizerObject> list,
			AbstractOrganizerObject value, int index, boolean isSelected,
			boolean cellHasFocus) {

		JCheckBox box = null;
		// Unterscheidung zwischen User und Room
		if (value instanceof User) {
			// Wenn der User bereits hinzugefügt wurde, wird die dazugehörige
			// Checkbox aus der Map genommen
			if (mapUser.containsKey(value)) {
				box = mapUser.get(value);
			} else {
				// Wenn der User nicht vorhanden ist, wird eine neue Checkbox
				// angelegt
				User user = (User) value;
				box = new JCheckBox(user.getGivenName() + " "
						+ user.getSurname());
				mapUser.put(user, box);
				// Reagiert auf die Veränderungen der Checkbox --> diese werden
				// weitergeleitet an den MyChangeListener
				box.addChangeListener(this);
			}
		}
		// Wenn der Room bereits hinzugefügt wurde, wird die dazugehörige
		// Checkbox aus der Map genommen
		if (value instanceof Room) {
			if (mapRoom.containsKey(value)) {
				box = mapRoom.get(value);
			} else {
				// Wenn der Room nicht vorhanden ist, wird eine neue Checkbox
				// angelegt
				Room room = (Room) value;
				box = new JCheckBox(room.getLocation() + " "
						+ room.getDescription());
				mapRoom.put(room, box);
				// Reagiert auf die Veränderungen der Checkbox --> diese werden
				// weitergeleitet an den MyChangeListener
				box.addChangeListener(this);
			}
		}
//		Sollte nie der Fall sein, aber sicher ist sicher
		if (box == null) {
			throw new NullPointerException("JCheckBox for "
					+ value.getClass().getName() + " ID: " + value.getID()
					+ " is null");
		}
//		der Status des Listenitems wird auf die CheckBox übertragen --> Hintergrundfarben werden angepasst
		if (isSelected) {
			box.setSelected(true); // --> löst die Methode stateChanged aus, wenn der Wert vorher false war
			box.setBackground(list.getSelectionBackground());
		} else {
			box.setSelected(false); // --> löst die Methode stateChanged aus, wenn der Wert vorher true war
			box.setBackground(list.getBackground());
		}
		return box;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
//		Welche Checkbox hat das Event ausgelöst?
		JCheckBox box = (JCheckBox) e.getSource();
//		zu welcher der beiden Maps gehört die CheckBox?
		if (mapRoom.containsValue(box)) {
//			Was ist der Key zu diesem Map-Value?
			Room r = getKeyByValue(mapRoom, box);
//			Weiterleiten von Status und Element an den MyChangeListener mit der richtigen Methode
			listener.stateChangedForRoom(box.isSelected(), r);
//			Andere überprüfung nicht mehr nötig - Methodenabbruch mit return;
			return;
		}
		if (mapUser.containsValue(box)) {
//			Was ist der Key zu diesem Map-Value?
			User u = getKeyByValue(mapUser, box);
//			Weiterleiten von Status und Element an den MyChangeListener mit der richtigen Methode
			listener.stateChangedForUser(box.isSelected(), u);
//			Methodenabbruch durch return --> hier nicht zwingend nötig, sieht aber schöner aus :D
			return;
		}
	}
	/**
	 * Searchs for the key that is related to the given value in the given map. This is just for one-to-one relations
	 * @param map to search in
	 * @param value of the key in the map
	 * @return the key of the given value or null if no value exists 
	 */
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}
}