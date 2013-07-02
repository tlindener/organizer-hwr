package de.lindener.androidorganizer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.lindener.androidorganizer.preferences.SettingsActivity;

//import network.JsonJavaRequestHandler;
//import network.RequestHandler;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.User;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	String title = "";
	int adapterIndex = 1;
	ListView calendarEntryListView = null;
	OrganizerRequester requester = null;

	Handler servicehandler = new Handler() {
		public void receiveMessage(Message msg) {

			Gson gson = new Gson();
			switch (msg.what) {
			case 1: {

				Bundle b = msg.getData();
				Calendar calendar = gson.fromJson(b.getString("Calendar"),
						new TypeToken<Calendar>() {
						}.getType());
				List<CalendarEntry> calendarEntries = gson.fromJson(
						b.getString("CalendarEntries"),
						new TypeToken<List<CalendarEntry>>() {
						}.getType());

				CalendarEntryAdapter adapter = new CalendarEntryAdapter(
						getApplicationContext(), R.layout.calendarentry, 0,
						calendarEntries);

				calendarEntryListView = (ListView) findViewById(R.id.calendarView);

				calendarEntryListView
						.setOnItemClickListener(new OnItemClickListener() {
							public void onItemClick(AdapterView<?> arg0,
									View v, int position, long id) {

								CalendarEntry entry = (CalendarEntry) calendarEntryListView
										.getItemAtPosition(position);
								if (entry != null) {
									Intent intent = new Intent(
											getBaseContext(),
											CalendarEntryActivity.class);
									intent.putExtra(
											Constants.CALENDAR_ENTRY_ID,
											entry.getID());
									startActivity(intent);
								}
							}
						});

				View header = (View) getLayoutInflater().inflate(
						R.layout.calendarentry_listview_header, null);
				if (header != null) {
					if (calendarEntryListView.getHeaderViewsCount() == 0) {
						calendarEntryListView.addHeaderView(header);
					}
				}
				TextView title = (TextView) findViewById(R.id.calendarTitleTextView);
				if (title != null) {
					title.setText(calendar.getName());
				}
				calendarEntryListView.setAdapter(adapter);

				break;
			}

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		requester = new OrganizerRequester(servicehandler,
				getApplicationContext());
		requester.run();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent = new Intent(this, SettingsActivity.class);

			startActivity(intent);

			break;
		default:
			break;
		}

		return true;
	}

//	public void addCalendarEntry(String titel, String description,
//			String startday, String starttime, String endday, String endtime,
//			int calendarId, int ownerId, int roomId) {
//		CalendarEntry ce = new CalendarEntry();
//
//		ce.setID(adapterIndex++);
//
//		ce.setTitle(titel);
//		ce.setDescription(description);
//		ce.setStartDate(CalendarEntry.parseStringToDate(startday, starttime));
//		ce.setEndDate(CalendarEntry.parseStringToDate(endday, endtime));
//
//		ce.setCalendarId(calendarId);
//		ce.setOwnerId(ownerId);
//		ce.setRoomId(roomId);
//		calendarEntries.add(ce);
//	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		requestSettings();
//		requestData();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
//		requestSettings();
//		requestData();
	}
}
