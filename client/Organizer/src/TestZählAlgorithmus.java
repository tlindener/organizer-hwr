import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TestZählAlgorithmus {
	

	
	private static GregorianCalendar cal; 
	
	public static void main (String[] args0)
	{
		konvertiereBeschreibungsDaten();
//		zaehle();
	}
	public static Object[][] konvertiereBeschreibungsDaten()
	{
		Object[][] beschreibungsDaten = new Object[1440][2];
		Object[][] beschreibungsDatenKonv= new Object[48][2];
		int j=0;
		for (int i=0;i<1439;i++)
		{
		if(beschreibungsDaten[i][1]!=null)
		{
			System.out.println("hier"+i);
			beschreibungsDatenKonv[j][0]=beschreibungsDaten[i][0];
			beschreibungsDatenKonv[j][1]=beschreibungsDaten[i][1];
			if(j<48)
			{
			j++;
			}
					}
		System.out.println(i);
		}
		beschreibungsDaten=beschreibungsDatenKonv;
		return beschreibungsDaten;
	}
	public static void zaehle()
	{
//		beschreibungsDaten=new Object[][]{
//		{"0.00", myModel.getBeschreibung("0.00")},
		
		Object[][] stunden=new Object[1440][2];
		cal= new GregorianCalendar();
		int myTimeHour;
		myTimeHour=cal.get(Calendar.HOUR_OF_DAY);
		int myTimeMinute;
		myTimeMinute = cal.get(Calendar.MINUTE);
		
		
		for(int i=1440; i>0; i-=15)
		{
			
			String myTime= myTimeHour+":"+myTimeMinute;
			stunden[i-1][0]=myTime;
			
			if(i%60==0)
			{
				stunden[i-1][1]="Volle Stunde";
			}
			else
			{
				stunden[i-1][1]="Es muss ein Termin vorhanden sein damit dies angegeben wird";
			}
		}
		
		for (Object[] row:stunden)
		{
			for (Object element:row)
			{
				System.out.print(element + " ");
				
			}
			System.out.println();
		}
		
		
	}
	
}
