package de.lindener.androidorganizer;

import java.util.ArrayList;
import java.util.List;

//import network.JsonJavaRequestHandler;
//import network.RequestHandler;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.User;

import android.os.Bundle;
import android.os.StrictMode;
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
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		serviceAddress = "tobiasnotebook";
		servicePort = 80;
		mailAdress = "tobias.lindener@locahost.de";
		userPassword = "123456";
		
		
		CalendarAbstractionLayer layer = new CalendarAbstractionLayer(serviceAddress, servicePort, mailAdress, userPassword);
		if(!layer.getCalendars().isEmpty()) 
		layer.getCalendars().get(0).getCalendarEntries();
		 
		
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
	
}
