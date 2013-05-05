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
	private HashMap <String, String> anfangende;
	private HashMap <String, String> details;
	private HashMap <String, List> personen;
	private HashMap <String, String> raeume;
	private HashMap <String, Double> dauer;
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
	
	public HashMap getAnfangende() {
		return anfangende;
	}
/**
 * <String Uhrzeit im Format xx:xx, Double als Dauer in Minuten>
 * @param dauer
 */
	public void setAnfangEnde(String anfang, String ende) {

			anfangende.put(anfang, ende);
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
		createAnfangende();
		
	}
	
	public <String, Double> HashMap createDauer()
	{
		dauer = new HashMap();
		
		return dauer;	
	}
	public <String> HashMap createAnfangende()
	{
		anfangende = new HashMap();
		return anfangende;	
	}
	
	public HashMap createBeschreibung()
	{
		
		beschreibungen = new HashMap();
		return beschreibungen;	
	}
	public HashMap createDetails()
	{
		details = new HashMap();
		return details;	
	}
	
	public HashMap createPersonen()
	{
		personen = new HashMap();
		return personen;	
	}
	
	public HashMap createRaeume()
	{
		raeume = new HashMap();
		
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
	public String returnEndzeit(String zeit)
	{
		if(anfangende.get(zeit)!=null)
		{
			
		return anfangende.get(zeit);
		}
		return null;
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
