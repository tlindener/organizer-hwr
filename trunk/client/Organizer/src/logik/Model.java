package logik;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

/**
 * The Model handles data input from the server (via Networklayer), stores and
 * organizes the data in hashmaps and lists. The attributes of the
 * calendarentries are stored in the hashmaps and are connected via the key
 * "time". Time is a unique value, because every HH:mm-combination can only be 
 * connected to one calendarentry. 
 * Provides various methods for Controller e.g.getting / setting hashmaps, 
 * getting elements out of hashmap (named as return-methods).
 * 
 * @author Jennifer Blumenthal
 * @version 2.0
 * 
 */

public class Model {

	private HashMap<String, Integer> kalenderentries;
	private HashMap<String, String> beschreibungen;
	private HashMap<String, String> anfangende;
	private HashMap<String, String> details;
	private HashMap<String, ArrayList<User>> eingeladene;
	private HashMap<String, Room> raeume;
	private HashMap<String, Double> dauer;
	private List<User> allePersonen;
	private List<Room> alleRaeume;
	private List<Invite> einladungen;
	private Date aktDate;

	/**
	 * Default constructor which creates a new Model. Initializes the aktDate as
	 * the submitted date and creates the hashmaps for the submitted date.
	 * 
	 * @param date
	 */
	public Model(Date date) {
		aktDate = date;
		createBeschreibung();
		createDetails();
		createDauer();
		createEingeladene();
		createRaeume();
		createAnfangende();
		createAlleRaeume();
		createAllePersonen();
		createEinladungen();
		createKalendarentries();

	}

	/**
	 * Creates the list "alleRaeume" which contains all rooms which are in the
	 * database.
	 * 
	 * @return alleRaueme
	 */
	public List<Room> createAlleRaeume() {
		alleRaeume = new ArrayList<Room>();
		return alleRaeume;
	}

	/**
	 * Creates the list "einladungen" which contains all invites for the logged-in
	 * person which are in the database.
	 * 
	 * @return einladungen
	 */
	public List<Invite> createEinladungen() {
		einladungen = new ArrayList<Invite>();
		return einladungen;
	}

	/**
	 * Creates the list "allePersonen" which contains all persons which are in
	 * the database.
	 * 
	 * @return allePersonen
	 */
	public List<User> createAllePersonen() {
		allePersonen = new ArrayList<User>();
		return allePersonen;
	}

	/**
	 * Creates the list "kalendarentries" which contains all calendarentries for the particular user.
	 * @return kalendarentries
	 */
	public HashMap<String,Integer> createKalendarentries() {
		kalenderentries = new HashMap<String,Integer>();
		return kalenderentries;
	}

	/**
	 * Creates the hashmap "dauer" which contains the durations of the calendarentries
	 * for the defined date.
	 * 
	 * @return dauer;
	 */
	public  HashMap<String, Double> createDauer() {
		dauer = new HashMap<String,Double>();
		return dauer;
	}

	/**
	 * Creates the hashmap "anfangende" which contains the start- and enddates of the
	 * calendarentries for the defined date.
	 * 
	 * @return anfangende;
	 */
	public HashMap<String,String> createAnfangende() {
		anfangende = new HashMap<String,String>();
		return anfangende;
	}

	/**
	 * Creates the hashmap "beschreibungen" which contains the description of the calendarentries
	 * for the defined date.
	 * 
	 * @return beschreibungen;
	 */
	public HashMap<String,String> createBeschreibung() {
		beschreibungen = new HashMap<String,String>();
		return beschreibungen;
	}

	/**
	 * Creates the hashmap "details" which contains the details of the calendarentries for
	 * the defined date.
	 * 
	 * @return details;
	 */
	public HashMap<String,String> createDetails() {
		details = new HashMap<String,String>();
		return details;
	}

	/**
	 * Creates the hashmap "eingeladene" which contains the invitees of the calendarentries
	 * for the defined date.
	 * 
	 * @return eingeladene;
	 */
	public HashMap<String, ArrayList<User>> createEingeladene() {
		eingeladene = new HashMap<String, ArrayList<User>>();
		return eingeladene;
	}

	/**
	 * Creates the hashmap "raeume" which contains the rooms of the calendarentries for
	 * the defined date.
	 * 
	 * @return raeume;
	 */
	public HashMap <String,Room>createRaeume() {
		raeume = new HashMap<String,Room>();
		return raeume;
	}

	/**
	 * Returns the room for the calendarentrie at the submitted time.
	 * 
	 * @param time
	 * @return room or String "kein Raum zugeteilt" if there is no room for the
	 *         calendarentrie
	 */
	public Room returnRaum(String time) {
		if (raeume.get(time) != null) {

			return raeume.get(time);
		} else {
			return null;
		}

	}

	/**
	 * Returns the endtime of the calendarentrie at the submitted time.
	 * 
	 * @param time
	 * @return endtime or null if there is no endtime
	 */
	public String returnEndzeit(String time) {
		if (anfangende.get(time) != null) {

			return anfangende.get(time);
		}
		return null;
	}

	/**
	 * Returns the a list of invitees for the calendarentrie at the submitted
	 * time.
	 * 
	 * @param time
	 * @return list of invitees
	 */
	public ArrayList<User> returnEingeladene(String time) {
		ArrayList <User>lreturn = new ArrayList<User>();
		lreturn = eingeladene.get(time);
		return lreturn;
	}
	
	/**
	 * Returns the detail of the calendarentrie at the submitted time.
	 * 
	 * @param time
	 * @return detail or a default string that there are no details available at
	 *         the specified time
	 */
	public String returnDetail(String time) {
		if (details.get(time) != null) {

			return details.get(time);
		} else {
			return "Es besteht zu der Ausgewählten Zeit kein Termin oder es wurden keine Details hinzugefügt";
		}
	}

	/**
	 * Returns the description of the calendarentrie at the submitted time.
	 * 
	 * @param time
	 * @return beschreibung
	 */
	public String returnBeschreibung(String time) {
		return beschreibungen.get(time);
	}

	/**
	 * Returns the duration of the calendarentrie at the submitted time.
	 * 
	 * @param time
	 * @return dauer or null if there is no duration at the specified time
	 */
	public Double returnDauer(String time) {
		if (dauer.containsKey(time)) {
			return dauer.get(time);
		} else
			return null;
	}

	/**
	 * Returns the calendarentry-ID at the submitted time.
	 * 
	 * @param time
	 * @return ID if there is any entry else 0
	 */
	public int returnKalendarentryId(String time) {
		if (kalenderentries.containsKey(time))
			return kalenderentries.get(time);
		else
			return 0;
	}


	public HashMap<String, Integer> getKalendarentries() {
		return kalenderentries;
	}

	public HashMap<String,String> getDetails() {
		return details;
	}

	public HashMap<String,String> getBeschreibungen() {
		return beschreibungen;
	}

	public HashMap<String,Room> getRaeume() {
		return raeume;
	}

	public HashMap<String,ArrayList<User>> getPersonen() {
		return eingeladene;
	}

	public Date getAktDate() {
		return aktDate;
	}

	public HashMap<String,String> getAnfangende() {
		return anfangende;
	}

	public HashMap<String,Double> getDauer() {
		return dauer;
	}

	public User[] getAllePersonen() {
		return allePersonen.toArray(new User[0]);
	}

	public Room[] getAlleRaeume() {
		return alleRaeume.toArray(new Room[0]);
	}

	public List<Invite> getEinladungen() {
		return einladungen;
	}

	
	public void setPersonen(String time, ArrayList<User> teilnehmer) {
		eingeladene.put(time, teilnehmer);
	}

	public void setBeschreibungen(String time, String beschreibung) {
		beschreibungen.put(time, beschreibung);
	}

	public void setRaeume(String time, Room room) {
		raeume.put(time, room);
	}

	public void setDetails(String time, String detail) {
		details.put(time, detail);
	}

	public void setDauer(String time, double duration) {
		dauer.put(time, duration);
	}

	public void setAnfangEnde(String anfang, String ende) {

		anfangende.put(anfang, ende);
	}

	public void setKalendarentries(String time, int ceID) {
		kalenderentries.put(time, ceID);
	}

	public void setAktDate(Date aktDate) {
		this.aktDate = aktDate;
	}

	public void setAllePersonen(List<User> personen) {
		allePersonen = personen;
	}

	public void setAlleRaeume(List<Room> raeume) {
		alleRaeume = raeume;
	}

	public void setEinladungen(List<Invite> einladungen) {
		this.einladungen = einladungen;
	}

}
