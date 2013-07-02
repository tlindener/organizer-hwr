package de.lindener.androidorganizer;

import de.lindener.androidorganizer.viewmodels.CalendarEntryViewModel;
import de.lindener.androidorganizer.viewmodels.UserViewModel;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarEntryActivity extends Activity {

	String serviceAddress = null;
	String mailAddress = null;
	int servicePort = -1;
	String userPassword;
	int calendarEntryId = 0;
	CalendarAbstractionLayer layer = null;
	CalendarEntryViewModel model = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_entry);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		 calendarEntryId = getIntent().getIntExtra(Constants.CALENDAR_ENTRY_ID, 0);
		 if (calendarEntryId > 0) {
		 loadData();
		 }

	}

	private void loadData() {
		requestSettings();

		if (!mailAddress.isEmpty() || !serviceAddress.isEmpty()
				|| servicePort > 0 || !userPassword.isEmpty()) {
			layer = new CalendarAbstractionLayer(serviceAddress, servicePort,
					mailAddress, userPassword);

			if (calendarEntryId > 0) {
				getCalendarEntryViewModel();
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
						if(model.getRoom() != null)
						{
						room.setText(model.getRoom().getLocation());
						}
					}
					ListView invites = (ListView)findViewById(R.id.calendarEntryActivityInvitationsList);
					if(invites != null)
					{
						UserViewModelAdapter adapter = new UserViewModelAdapter(this,
								R.layout.userviewmodel_list_item, 0, model.getInvitees());

						invites.setAdapter(adapter);
					}
					

				}
			}
		}

	}

	private void getCalendarEntryViewModel() {
		if (layer != null) {
			model = layer.getCalendarEntry(calendarEntryId);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadData();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		loadData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar_entry, menu);
		return true;
	}

	private void requestSettings() {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		mailAddress = sharedPref.getString("preferences_edittext_mail_address",
				"");
		serviceAddress = sharedPref.getString(
				"preferences_edittext_server_address", "");
		servicePort = Integer.valueOf(sharedPref.getString(
				"preferences_edittext_port", "80"));
		userPassword = sharedPref
				.getString("preferences_edittext_password", "");

	}

}
