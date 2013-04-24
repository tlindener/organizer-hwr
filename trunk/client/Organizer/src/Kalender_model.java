import java.util.HashMap;


public class Kalender_model {

	/**
	 * @param args
	 */
	private HashMap beschreibungen;
	private HashMap details;
	
	
	public Kalender_model()
	{
		setBeschreibung();
		setDetails();
	}
	
	public HashMap setBeschreibung()
	{

/*
 * Kommunikation mit Server um Daten abzufragen
 * die Parameter beschreibung, details, personen und raum werden vom Server übergeben
 */
//		String name = "beschreibungen" + datum;
		
		beschreibungen = new HashMap();
		beschreibungen.put("8.00", "Aufstehen");
		beschreibungen.put("12.00", "Mittag");	
		beschreibungen.put("17.00", "Feierabend");
		return beschreibungen;	
	}
	public HashMap setDetails()
	{
		details = new HashMap();
		details.put("8.00", "Jetzt aber wirklich aufstehen!");
		details.put("12.00", "Lecker essen ist angesagt!!!!");	
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

}
