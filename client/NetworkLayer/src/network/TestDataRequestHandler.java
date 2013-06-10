package network;

import java.util.ArrayList;
import java.util.List;

import network.objects.TestData;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Room;
import organizer.objects.types.User;

public class TestDataRequestHandler extends RequestHandler {

	TestData data = null;

	public TestDataRequestHandler() {
		data = new TestData();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	@Override
	public <T extends AbstractOrganizerObject> T requestObjectByProperty(T obj) {

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractOrganizerObject> T requestObjectByOwnId(T obj) {
		Object result = null;
		if (obj instanceof CalendarEntry) {
			result = data.getCalendarEntryById(obj.getID());
		}
		if (obj instanceof Calendar) {
			result = data.getCalendarById(obj.getID());
		}
		if (obj instanceof Room) {
			result = data.getRoomById(obj.getID());
		}
		if (obj instanceof User) {
			result = data.getUserById(obj.getID());
		}
		if (result != null) {
			return (T) result;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractOrganizerObject> List<T> requestAllObjects(T obj) {
		Object result = null;

		if (obj instanceof CalendarEntry) {
			result = data.getAllCalendarEntries();
		}
		if (obj instanceof Calendar) {
			result = data.getAllCalendar();
		}
		if (obj instanceof Room) {
			result = data.getAllRooms();
		}
		if (obj instanceof User) {
			result = data.getAllUser();
		}
		if (result != null) {
			return (List<T>) result;
		} else {
			return new ArrayList<T>();
		}
	}

	@Override
	public <T extends AbstractOrganizerObject> List<T> requestAllObjectsByProperty(
			T obj) {
		Object result = null;
		if (obj instanceof CalendarEntry) {
			result = data.getAllCalendarEntriesByProperty(obj.getProperty());
		}
		if (obj instanceof Calendar) {
			result = data.getAllCalendarByProperty(obj.getProperty());
		}
		if (obj instanceof Room) {
			result = data.getAllRoomsByProperty(obj.getProperty());
		}
		if (obj instanceof User) {
			result = data.getAllUserByProperty(obj.getProperty());
		}
		if (result != null) {
			return (List<T>) result;
		} else {
			return new ArrayList<T>();
		}
	}

	@Override
	public <T extends AbstractOrganizerObject> T addObject(T obj)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User registerNewUser(User user, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends AbstractOrganizerObject> boolean removeObjectByOwnId(T obj)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User login(String mail, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int acceptInvite(int inviteId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int declineInvite(int inviteId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends AbstractOrganizerObject> boolean updateObject(T obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
