/**
 * 
 */
package data.model;

import java.util.ArrayList;
import java.util.List;

import data.objects.types.Room;
import data.objects.types.User;

/**
 * @author Steffen Baumann
 * @version 1.0 
 *
 */
public class MyModel{

	private int globalId = 1;
	
	private List<User> users = new ArrayList<User>();
	private List<Room> rooms = new ArrayList<Room>();
	
	/**
	 * 
	 */
	public MyModel() {
		
	}
	/**
	 * Erstellt sowohl User als auch Raum von x bis x+10
	 * x = globalId
	 */
	private void fillListWithDefaultValues() {
		String givenname = "Max";
		String surname = "Mustermann";
		String mail = "";
		String description = "Raum Nr. ";
		String location = "Default Ort Haus Nr. ";
		int seats = 2;
		for(int id = globalId; id < globalId+10; id++){
			mail = givenname + "." + surname +"@localhost.de";
			addUser(id, givenname, surname+id, mail);
			addRoom(id, description+id, location+id, seats*id);
		}
		globalId += 10;
	}

	private void addRoom(int id, String description, String location, int seats) {
		Room r = new Room();
		r.setDescription(description);
		r.setID(id);
		r.setLocation(location);
		r.setSeats(seats);
		System.out.println("Add Room Nr. " + id);
		rooms.add(r);
	}

	private void addUser(int id, String givenname, String surname, String mail) {
		User u = new User();
		u.setID(id);
		u.setGivenname(givenname);
		u.setSurname(surname);
		u.setMailAddress(mail);
		System.out.println("Add User Nr. " + id);
		users.add(u);
	}
	/**
	 * Fügt einfach noch mal 10 Elemente hinzu
	 */
	public void refreshData(){
		fillListWithDefaultValues();
	}
	
	public User[] getListUser(){
		return users.toArray(new User[0]);
	}
	
	public Room[] getListRoom(){
		return rooms.toArray(new Room[0]);
	}
}
