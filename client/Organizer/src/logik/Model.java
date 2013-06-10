package logik;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import organizer.objects.types.Room;
import organizer.objects.types.User;

/**
 * The Model handels data input from Server (via Networklayer), stores and organizes the data in hashmaps and lists.
 * The attributes of the calendarentries are stored in the hashmaps and are connected via the key "time".
 * Time is a unique value, because every hours:minutes combination can have only one calendarentry.
 * Provides various methods for Controller e.g. getting / setting hashmaps, get elements out of hashmap. 
 * 
 * @author Jenny
 * @version 1.0
 *
 */

public class Model {


	private HashMap Kalenderdaten;
	
	private HashMap <String, String> beschreibungen;
	private HashMap <String, String> anfangende;
	private HashMap <String, String> details;
	private HashMap <String, List <User>> eingeladene;
	private HashMap <String, String> raeume;
	private HashMap <String, Double> dauer;
	private List<User> allePersonen;
	private List<Room> alleRaeume;
	private Date aktDate;
	
	
/**
 * Default constructor which creates a new Model.
 * Initializes the aktDate as the current date and creates the hashmaps for the submitted date.
 *  
 * @param date
 */
	public Model(Date date)
	{
		aktDate=date;
		createBeschreibung();
		createDetails();
		createDauer();
		createEingeladene();
		createRaeume();
		createAnfangende();
		createAlleRaeume();
		createAllePersonen();
		
	}
	
	/**
	 * Creates the list "alleRaeume" which contains all rooms which are in the database.
	 * @return alleRaueme
	 */
	public List createAlleRaeume()
	{
		alleRaeume =new ArrayList();
		return alleRaeume;
	}
	
	/**
	 * Creates the list "allePersonen" which contains all persons which are in the database.
	 * @return allePersonen
	 */
	public List createAllePersonen()
	{
		allePersonen=new ArrayList();
		return allePersonen;
	}
	
	/**
	 * Creates the hashmap which contains the durations of the calendarentries for the defined date.
	 * @return dauer;
	 */
	public <String, Double> HashMap createDauer()
	{
		dauer = new HashMap();
		return dauer;	
	}
	
	/**
	 * Creates the hashmap which contains the start- and enddates of the calendarentries for the defined date.
	 * @return anfangende;
	 */
	public <String> HashMap createAnfangende()
	{
		anfangende = new HashMap();
		return anfangende;	
	}
	
	/**
	 * Creates the hashmap which contains the description of the calendarentries for the defined date.
	 * @return beschreibungen;
	 */
	public HashMap createBeschreibung()
	{
		beschreibungen = new HashMap();
		return beschreibungen;	
	}
	
	/**
	 * Creates the hashmap which contains the details of the calendarentries for the defined date.
	 * @return details;
	 */
	public HashMap createDetails()
	{
		details = new HashMap();
		return details;	
	}
	
	/**
	 * Creates the hashmap which contains the invitees of the calendarentries for the defined date.
	 * @return eingeladene;
	 */
	public HashMap createEingeladene()
	{
		eingeladene = new HashMap();
		return eingeladene;	
	}
	
	/**
	 * Creates the hashmap which contains the rooms of the calendarentries for the defined date.
	 * @return raeume;
	 */
	public HashMap createRaeume()
	{
		raeume = new HashMap();
		return raeume;
	}
	
	
	/**
	 * Returns the room of the calendarentrie at the submitted time. 
	 * @param time
	 * @return room or String "kein Raum zugeteilt" if there is no room for the calendarentrie
	 */
	public String returnRaum(String time)
	{
		if(raeume.get(time)!=null)
		{
			
		return raeume.get(time);
		}
		else
			{
				return "kein Raum zugeteilt";
			}
		
	}
	
	/**
	 * Returns the endtime of the calendarentrie at the submitted time.
	 * @param time
	 * @return endtime or null if there is no endtime
	 */
	public String returnEndzeit(String time)
	{
		if(anfangende.get(time)!=null)
		{
			
		return anfangende.get(time);
		}
		return null;
	}
	
	/**
	 * Returns the a list of invitees for the calendarentrie at the submitted time.
	 * @param time
	 * @return list of invitees
	 */
	public List returnEingeladene(String time)
	{
		List lreturn=new ArrayList<String>();
		lreturn = eingeladene.get(time);
		return lreturn;
	}

	/**
	 * Returns the detail of the calendarentrie at the submitted time.
	 * @param time
	 * @return detail or a default string that there are no details available at the specified time
	 */
	
	public String returnDetail(String time)
		{
		if(details.get(time)!=null)
		{
			
		return details.get(time);
		}
		else
			{
						return "Es besteht zu der Ausgewählten Zeit kein Termin oder es wurden keine Details hinzugefügt";
			}
	}
	
	/**
	 * Returns the description of the calendarentrie at the submitted time.
	 * @param time
	 * @return beschreibung 
	 */
	public String returnBeschreibung(String time)
	{
		return beschreibungen.get(time);
	}
	
	/**
	 * Returns the duration of the calendarentrie at the submitted time.
	 * 
	 * @param time
	 * @return dauer or null if there is no duration at the specified time
	 */
	public Double returnDauer(String time)
	{
		if(dauer.containsKey(time))
		{
		return dauer.get(time);
		}
		else
		return null;
	}
	
	/**
	 * Returns the hashmap calendardata.
	 * 
	 * @return kalendardaten
	 */
	public HashMap getKalenderdaten() {
		return Kalenderdaten;
	}

	/**
	 * Sets the hashmap Kalendardaten
	 * @param kalenderdaten
	 */
	public void setKalenderdaten(HashMap kalenderdaten) {
		Kalenderdaten = kalenderdaten;
	}

	public HashMap getBeschreibungen() {
		return beschreibungen;
	}
/**
 * Puts the submitted description with the submitted time as key into the hashmap descriptions.
 * 
 * @param time, beschreibung
 */
	public void setBeschreibungen(String time, String beschreibung) {
		beschreibungen.put(time, beschreibung);
	}

	/**
	 * Returns the details hasmap.
	 * 
	 * @return details
	 */
	public HashMap getDetails() {
		return details;
	}
/**
 * Puts the submitted detail with the submitted time as key into the hashmap details.
 * 
 * @param detail, time
 */
	public void setDetails(String time, String detail) {
		details.put(time,detail);
	}
/**
 * Returns the invitee hasmap.
 * 
 * @return eingeladene
 */
	public HashMap getPersonen() {
		return eingeladene;  
	}
	
/**
 * Puts the submitted invitee list with the submitted time as key into the hashmap eingeladene.
 * 
 * @param personen, time
 */
	public void setPersonen(String time, List teilnehmer) {
		eingeladene.put(time, teilnehmer);
	}

	/**
	 * Returns the rooms hashmap.
	 * 
	 * @return raeume
	 */
	public HashMap getRaeume() {
		return raeume;
	}
	
	/**
	 * Puts the submitted room with the submitted time as key into the hashmap rooms.
	 * @param time, room
	 */
	public void setRaeume(String time, String room) {
		raeume.put(time, room);
	}

	public HashMap getDauer() {
		return dauer;
	}
/**
 * Puts the submitted duration with the submitted time as key into the hashmap durations.
 * @param time, duration
 */
	public void setDauer(String time, double duration) {
		dauer.put(time, duration);
	}
	
	/**
	 * Returns the hashmap startend.
	 * 
	 * @return anfangende
	 */
	public HashMap getAnfangende() {
		return anfangende;
	}

	/**
	 * Puts the submitted endtime with the submitted starttime as key into the hashmap startend.
	 * 
	 * @param anfang, ende
	 */
	public void setAnfangEnde(String anfang, String ende) {

	anfangende.put(anfang, ende);
	}

	/**
	 * Returns the current Date from the model.
	 * 
	 * @return aktDate
	 */
	public Date getAktDate() {
		return aktDate;
	}

	/**
	 * Replaces the current date with the submitted current date. 
	 * 
	 * @param aktDate
	 */
	public void setAktDate(Date aktDate) {
		this.aktDate = aktDate;
	}

	/**
	 * Returns the list with all persons.
	 * 
	 * @return allePersonen
	 */
	public User[] getAllePersonen() {
		return allePersonen.toArray(new User[0]);
	}

	
	/**
	 * Replaces the list with all persons with the submitted list.
	 * 
	 * @param personen
	 */
	public void setAllePersonen(List personen) {
		allePersonen=personen;
	}

	/**
	 * Returns the list with all available rooms.
	 * 
	 * @return alleRaueme
	 */
	public Room[] getAlleRaeume() {
		return  alleRaeume.toArray(new Room[0]);
	}

	
/**
 * Replaces the list of all rooms with the submitted list.
 * 
 * @param raeume
 */
	public void setAlleRaeume(List raeume) {
		alleRaeume=raeume;
	}

}
