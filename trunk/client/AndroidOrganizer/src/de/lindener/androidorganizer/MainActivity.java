package de.lindener.androidorganizer;

import java.util.ArrayList;
import java.util.List;

//import network.JsonJavaRequestHandler;
//import network.RequestHandler;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.User;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {

// RequestHandler requestHandler = null;
	User user = null;
	String serviceAddress = null;
	String mailAdress = null;
	int servicePort = -1;
	String userPassword;
	 List<CalendarEntry> data = new ArrayList<CalendarEntry>();
int i = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		serviceAddress = "tobiasnotebook";
		servicePort = 80;
		mailAdress = "tobias.lindener@locahost.de";
		userPassword = "123456";
		
		
	//	CalendarAbstractionLayer layer = new CalendarAbstractionLayer(serviceAddress, servicePort, mailAdress, userPassword);
//		 layer.getCalendars().get(0).getCalendarEntries();
//		 

		 
			addCalendarEntry("Testtermin1", "Terminbeschreibung", "01.05.2013", "07:30", "01.05.2013", "08:00", 1, 1, 1);
			addCalendarEntry("Testtermin2", "Terminbeschreibung", "01.05.2013", "12:00", "01.05.2013", "13:00", 1, 1, 2);
			addCalendarEntry("Testtermin3", "Terminbeschreibung", "01.05.2013", "14:30", "01.05.2013", "16:30", 1, 1, 3);
			addCalendarEntry("Testtermin4", "Terminbeschreibung", "02.05.2013", "18:30", "02.05.2013", "18:45", 1, 1, 4);
			addCalendarEntry("Testtermin5", "Terminbeschreibung", "03.05.2013", "11:30", "03.05.2013", "13:30", 1, 1, 1);
			addCalendarEntry("Testtermin6", "Terminbeschreibung", "03.05.2013", "06:00", "03.05.2013", "06:15", 1, 1, 2);
			addCalendarEntry("Testtermin7", "Terminbeschreibung", "03.05.2013", "08:30", "03.05.2013", "10:30", 1, 1, 3);
			addCalendarEntry("Testtermin8", "Terminbeschreibung", "03.05.2013", "14:30", "03.05.2013", "15:30", 1, 1, 4);
			addCalendarEntry("Testtermin9", "Terminbeschreibung", "03.05.2013", "16:00", "03.05.2013", "16:30", 1, 1, 1);
			addCalendarEntry("Testtermin10", "Terminbeschreibung", "03.05.2013", "17:00", "03.05.2013", "18:30", 1, 1, 1);

		
		 CalendarEntryAdapter adapter = new CalendarEntryAdapter(this, R.layout.calendarentry, 0, data);
		 ListView view1 =  (ListView)findViewById(R.id.calendarView);
		 view1.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addCalendarEntry(String titel, String description, String startday, String starttime, String endday, String endtime, int calendarId, int ownerId, int roomId){
		CalendarEntry ce = new CalendarEntry();
		
		ce.setID(i++);
		
		ce.setTitle(titel);
		ce.setDescription(description);
		ce.setStartDate(CalendarEntry.parseStringToDate(startday, starttime));
		ce.setEndDate(CalendarEntry.parseStringToDate(endday, endtime));
		
		ce.setCalendarId(calendarId);
		ce.setOwnerId(ownerId);
		ce.setRoomId(roomId);
		data.add(ce);
	}


}
