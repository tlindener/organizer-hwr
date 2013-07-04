package de.lindener.androidorganizer;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class CalendarEntryActivityCreate extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_entry_activity_create);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar_entry_activity_create, menu);
		return true;
	}

	public void openStartDateFragment(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			TextView tv = (TextView) this.getActivity().findViewById(
					R.id.calendarEntryActivityCreateStartDate);
	
			tv.setText(		tv.getText() +"   "+hourOfDay+":"+minute);
			
		}
	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			TextView tv = (TextView) this.getActivity().findViewById(
					R.id.calendarEntryActivityCreateStartDate);
			tv.setText(day + "-" + month + "-" + year);
			
			DialogFragment newFragment = new TimePickerFragment();
			newFragment.show(getFragmentManager(), "timePicker");
		}
	}

}
