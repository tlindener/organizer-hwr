import java.util.HashMap;


public class Model {

	/**
	 * @param args
	 */
	private HashMap beschreibungen;
	private HashMap details;
	private String aktDate=null;
	private HashMap dauer;
	
	
	public String getAktDatum() {
		return aktDate;
	}

	public void setAktDatum(String aktDatum) {
		this.aktDate = aktDatum;
	}

	public Model()
	{
		setBeschreibung();
		setDetails();
	}
	
	public HashMap setDauer()
	{

/*
 * Kommunikation mit Server um Daten abzufragen
 
 * für Netzwerkschicht: .put(Uhrzeit, Dauer)
 */
//		String name = "beschreibungen" + datum;
		
		dauer = new HashMap();
		dauer.put("8:00", 3.15);
		dauer.put("12:00", 1.00);	
		dauer.put("17:00", 5.00);
		return dauer;	
	}
	
	public HashMap setBeschreibung()
	{

/*
 * Kommunikation mit Server um Daten abzufragen
 
 * für Netzwerkschicht: .put(Uhrzeit, Beschreibung)
 */
//		String name = "beschreibungen" + datum;
		
		beschreibungen = new HashMap();
		beschreibungen.put("8:00","Aufstehen");
		beschreibungen.put("12:00", "Mittag");	
		beschreibungen.put("17:00", "Feierabend");
		return beschreibungen;	
	}
	public HashMap setDetails()
	{
		details = new HashMap();
		details.put("8:00", "Jetzt aber wirklich aufstehen!");
		details.put("12:00", "Lecker essen ist angesagt!!!!");	
		return details;	
	}
	public Object getDetails(String zeit)
	{
		if(details.get(zeit)!=null)
		{
			
		return details.get(zeit);
		}
		else
			{
			System.out.println(details.get(zeit)+","+ zeit);
			return "Es besteht zu der Ausgewählten Zeit kein Termin oder es wurden keine Details hinzugefügt";
			}
	}
	
	public Object getBeschreibung(String zeit)
	{
		return beschreibungen.get(zeit);
	}
	public Object getDauer(String zeit)
	{
		return dauer.get(zeit);
	}

}
