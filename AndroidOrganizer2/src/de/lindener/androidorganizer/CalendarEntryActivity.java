package de.lindener.androidorganizer;

import de.lindener.androidorganizer.preferences.PreferenceData;
import de.lindener.androidorganizer.preferences.SettingsActivity;
import de.lindener.androidorganizer.viewmodels.CalendarEntryViewModel;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Activity to display calendar entry details
 * @author TobiasLindener
 *
 */
public class CalendarEntryActivity extends Activity {

	int calendarEntryId = 0;
	CalendarAbstractionLayer layer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_entry);

		calendarEntryId = getIntent().getIntExtra(Constants.CALENDAR_ENTRY_ID,
				0);
		PreferenceData data = requestSettings();
		new requestCalendarEntryTask(data, calendarEntryId).execute();
	}

	/**
	 * sets the data to specific textviews
	 * @param model
	 */
	@SuppressWarnings("deprecation")
	private void setCalendarEntryData(CalendarEntryViewModel model) {
		if (model != null) {
			TextView title = (TextView) findViewById(R.id.calendarEntryActivityTitle);
			if (title != null) {
				title.setText(model.getTitle());
			}
			TextView description = (TextView) findViewById(R.id.calendarEntryActivityDescription);
			if (description != null) {
				description.setText(model.getDescription());
			}
			TextView startDate = (TextView) findViewById(R.id.calendarEntryActivityStartDate);
			if (startDate != null) {
				startDate.setText(model.getStartDate().toLocaleString());
			}
			TextView endDate = (TextView) findViewById(R.id.calendarEntryActivityEndDate);
			if (endDate != null) {
				endDate.setText(model.getEndDate().toLocaleString());
			}
			TextView room = (TextView) findViewById(R.id.calendarEntryActivityRoomLocation);
			if (room != null) {
				if (model.getRoom() != null) {
					room.setText(model.getRoom().getLocation());
				}
			}
			ListView invites = (ListView) findViewById(R.id.calendarEntryActivityInvitationsList);
			if (invites != null) {
				UserViewModelAdapter adapter = new UserViewModelAdapter(this,
						R.layout.userviewmodel_list_item, 0,
						model.getInvitees());

				invites.setAdapter(adapter);
			}

		}

	}

	class requestCalendarEntryTask extends
			AsyncTask<Void, Void, CalendarEntryViewModel> {
		PreferenceData prefs = null;
		int calendarEntryId = 0;

		requestCalendarEntryTask(PreferenceData preferences, int calendarEntryId) {
			this.prefs = preferences;
			this.calendarEntryId = calendarEntryId;

		}

		@Override
		protected void onPostExecute(CalendarEntryViewModel result) {
			super.onPostExecute(result);
			setCalendarEntryData(result);

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected CalendarEntryViewModel doInBackground(Void... arg0) {

			layer = new CalendarAbstractionLayer(prefs.getServerAddress(),
					prefs.getServerPort(), prefs.getMailAddress(),
					prefs.getPassword());

			if (layer != null) {

				if (calendarEntryId > 0) {

					return layer.getCalendarEntry(calendarEntryId);

				}

			}
			return null;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		PreferenceData data = requestSettings();
		new requestCalendarEntryTask(data, calendarEntryId).execute();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		PreferenceData data = requestSettings();
		new requestCalendarEntryTask(data, calendarEntryId).execute();
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
				new requestCalendarEntryTask(data,calendarEntryId).execute();
			}
			break;
		}
		default:
			break;
		}

		return true;
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

}
