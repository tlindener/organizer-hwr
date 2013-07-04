package de.lindener.androidorganizer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.lindener.androidorganizer.preferences.PreferenceData;
import de.lindener.androidorganizer.preferences.SettingsActivity;
import de.lindener.androidorganizer.viewmodels.CalendarViewModel;

//import network.JsonJavaRequestHandler;
//import network.RequestHandler;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.User;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main Android Activity which displays a users calendar
 * @author TobiasLindener
 *
 */
public class MainActivity extends Activity {

	int adapterIndex = 1;
	CalendarAbstractionLayer layer = null;
	ListView calendarEntryListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		PreferenceData data = requestSettings();
		if (data != null) {
			new requestCalendarTask(data).execute();
		}

	}

	/**
	 * AsyncTask to request calendar
	 * @author TobiasLindener
	 *
	 */
	class requestCalendarTask extends AsyncTask<Void, Void, CalendarViewModel> {
		PreferenceData prefs = null;

		requestCalendarTask(PreferenceData preferences) {
			this.prefs = preferences;

		}

		@Override
		protected void onPostExecute(CalendarViewModel result) {
			super.onPostExecute(result);
			setCalendarData(result);

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected CalendarViewModel doInBackground(Void... arg0) {
			CalendarViewModel calendar = new CalendarViewModel();
			List<CalendarEntry> calendarEntries = new ArrayList<CalendarEntry>();
			List<Calendar> calendarList = new ArrayList<Calendar>();

			layer = new CalendarAbstractionLayer(prefs.getServerAddress(),
					prefs.getServerPort(), prefs.getMailAddress(),
					prefs.getPassword());

			if (layer != null) {
				Log.w("AsyncTask", "layer != null");

				calendarList = layer.getCalendars();

				if (!calendarList.isEmpty()) {
					Log.w("AsyncTask", "calendarList not empty");
					calendar.setCalendar(calendarList.get(0));
				}
				if (calendar.getCalendar() != null) {

					calendarEntries.clear();
					Date today = new Date();
					today.setDate(today.getDate() - 1);
					for (CalendarEntry entry : calendar.getCalendar()
							.getCalendarEntries()) {
						if (entry.getStartDate().after(today)) {
							calendarEntries.add(entry);
						}
					}
					calendar.setCalendarEntries(calendarEntries);
				}

			}
			return calendar;
		}
	}

	/**
	 * Returns the settings saved in Android sharedPreferences
	 * @return
	 */
	private PreferenceData requestSettings() {
		PreferenceData prefs = new PreferenceData();

		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		prefs.setMailAddress(sharedPref.getString(
				"preferences_edittext_mail_address", ""));
		prefs.setServerAddress(sharedPref.getString(
				"preferences_edittext_server_address", ""));
		prefs.setServerPort(Integer.valueOf(sharedPref.getString(
				"preferences_edittext_port", "80")));
		prefs.setPassword(sharedPref.getString("preferences_edittext_password",
				""));

		if (!prefs.getMailAddress().isEmpty()
				|| !prefs.getServerAddress().isEmpty()
				|| prefs.getServerPort() > 0 || !prefs.getPassword().isEmpty()) {
			return prefs;

		}
		return null;
	}

	private void setCalendarData(CalendarViewModel calendar) {

		CalendarEntryAdapter adapter = new CalendarEntryAdapter(this,
				R.layout.calendarentry, 0, calendar.getCalendarEntries());

		calendarEntryListView = (ListView) findViewById(R.id.calendarView);

		calendarEntryListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {

				CalendarEntry entry = (CalendarEntry) calendarEntryListView
						.getItemAtPosition(position);
				if (entry != null) {
					Intent intent = new Intent(getBaseContext(),
							CalendarEntryActivity.class);
					intent.putExtra(Constants.CALENDAR_ENTRY_ID, entry.getID());
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
			if (calendar.getCalendar() != null) {
				title.setText(calendar.getCalendar().getName());
			}

		}
		calendarEntryListView.setAdapter(adapter);
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
		case R.id.action_settings: {
			Intent intent = new Intent(this, SettingsActivity.class);

			startActivity(intent);

			break;
		}
		case R.id.action_refresh: {
			PreferenceData data = requestSettings();
			if (data != null) {
				new requestCalendarTask(data).execute();
			}
			break;
		}
		default:
			break;
		}

		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		PreferenceData data = requestSettings();
		if (data != null) {
			new requestCalendarTask(data).execute();
		}

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		PreferenceData data = requestSettings();
		if (data != null) {
			new requestCalendarTask(data).execute();
		}
	}
}
