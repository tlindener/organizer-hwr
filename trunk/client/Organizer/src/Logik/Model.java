package Logik;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class Model {

	/**
	 * @param args
	 */
	private HashMap Kalenderdaten;
	
	private HashMap <String, String> beschreibungen;
	private HashMap <String, String> details;
	private HashMap <String, List> personen;
	private HashMap <String, String>raeume;
	private HashMap <String, Double>dauer;
	private Date aktDate;
	
	
	

	public HashMap getKalenderdaten() {
		return Kalenderdaten;
	}

	public void setKalenderdaten(HashMap kalenderdaten) {
		Kalenderdaten = kalenderdaten;
	}

	public HashMap getBeschreibungen() {
		return beschreibungen;
	}
/**
 * <String Uhrzeit im Format xx:xx, String Beschreibung>
 * @param beschreibungen
 */
	public void setBeschreibungen(String zeit, String beschreibung) {
		beschreibungen.put(zeit, beschreibung);
	}

	public HashMap getDetails() {
		return details;
	}
/**
 * <String Uhrzeit im Format xx:xx, String Detail>
 * @param details
 */
	public void setDetails(String zeit, String detail) {
		details.put(zeit,detail);
	}

	public HashMap getPersonen() {
		return personen;
	}
/**
 * <String Uhrzeit im Format xx:xx, List Personen>
 * @param personen
 */
	public void setPersonen(String zeit, List teilnehmer) {
		personen.put(zeit, teilnehmer);
	}

	public HashMap getRaeume() {
		return raeume;
	}
/**
 * <String Uhrzeit im Format xx:xx, String Raum>
 * @param raeume
 */
	public void setRaeume(String zeit, String raum) {
		raeume.put(zeit, raum);
	}

	public HashMap getDauer() {
		return dauer;
	}
/**
 * <String Uhrzeit im Format xx:xx, Double als Dauer in Minuten>
 * @param dauer
 */
	public void setDauer(String zeit, double duration) {
		dauer.put(zeit, duration);
	}

	public Date getAktDate() {
		return aktDate;
	}

	public void setAktDate(Date aktDate) {
		this.aktDate = aktDate;
	}

	public Model(Date date)
	{
		aktDate=date;
		createBeschreibung();
		createDetails();
		createDauer();
		createPersonen();
		createRaeume();
		
	}
	
	public <String, Double> HashMap createDauer()
	{
		dauer = new HashMap();
		dauer.put("8:15", 60.0);
		dauer.put("12:00", 130.0);	
		dauer.put("17:00", 30.0);
		return dauer;	
	}
	
	public HashMap createBeschreibung()
	{
		
		beschreibungen = new HashMap();
		beschreibungen.put("8:15", "Aufstehen");
		beschreibungen.put("12:00", "Mittag");	
		beschreibungen.put("17:00", "Feierabend");
		return beschreibungen;	
	}
	public HashMap createDetails()
	{
		details = new HashMap();
		details.put("8:15", "Jetzt aber wirklich aufstehen!");
		details.put("12:00", "Lecker essen ist angesagt!!!!");	
		return details;	
	}
	
	public HashMap createPersonen()
	{
		personen = new HashMap();
		String p1="Herr Meier";
		String p2="Herr Müller";
		String p3="Frau Sorge";
		String p4="Frau Graf";
		List l1= new ArrayList<String>();
		List l2= new ArrayList<String>();
		l1.add(p1);
		l1.add(p3);
		l1.add(p4);
		l2.add(p4);
		l2.add(p2);
		personen.put("8:15", l1);
		personen.put("17:00", l2);
		return personen;	
	}
	
	public HashMap createRaeume()
	{
		raeume = new HashMap();
		raeume.put("8:15", "Bett");
		raeume.put("12:00", "Kantine");
		return raeume;
	}
	
	public String returnRaum(String zeit)
	{
		if(raeume.get(zeit)!=null)
		{
			
		return raeume.get(zeit);
		}
		else
			{
				return "kein Raum zugeteilt";
			}
		
	}
	
	public List returnPersonen(String zeit)
	{
		List lreturn=new ArrayList<String>();
		lreturn = personen.get(zeit);
		return lreturn;
	}
	public Object returnDetail(String zeit)
	
	{
		if(details.get(zeit)!=null)
		{
			
		return details.get(zeit);
		}
		else
			{
						return "Es besteht zu der Ausgewählten Zeit kein Termin oder es wurden keine Details hinzugefügt";
			}
	}
	
	public Object returnBeschreibung(String zeit)
	{
		return beschreibungen.get(zeit);
	}
	public Object returnDauer(String zeit)
	{
		if(dauer.containsKey(zeit))
		{
		return dauer.get(zeit);
		}
		else
		return null;
	}

}
