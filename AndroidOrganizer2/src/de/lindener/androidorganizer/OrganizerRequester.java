package de.lindener.androidorganizer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class OrganizerRequester implements Runnable {

	Handler serviceHandler = null;
	Context context = null;
	
	String serviceAddress = null;
	String mailAddress = null;
	int servicePort = -1;
	String userPassword;
	CalendarAbstractionLayer layer = null;
	List<CalendarEntry> calendarEntries = new ArrayList<CalendarEntry>();
	List<Calendar> calendarList = new ArrayList<Calendar>();
	Calendar calendar = null;
	
	private void requestSettings() {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(context);
		mailAddress = sharedPref.getString("preferences_edittext_mail_address",
				"");
		serviceAddress = sharedPref.getString(
				"preferences_edittext_server_address", "");
		servicePort = Integer.valueOf(sharedPref.getString(
				"preferences_edittext_port", "80"));
		userPassword = sharedPref
				.getString("preferences_edittext_password", "");

	}
	
	@Override
	public void run() {
	
		layer = new CalendarAbstractionLayer(serviceAddress, servicePort, mailAddress, userPassword);
		
		
	}
	
	private void requestCalendarEntries() {

		if (!mailAddress.isEmpty() || !serviceAddress.isEmpty()
				|| servicePort > 0 || !userPassword.isEmpty()) {
			layer = new CalendarAbstractionLayer(serviceAddress, servicePort,
					mailAddress, userPassword);
		}
		if (layer != null) {
			calendarList = layer.getCalendars();
			if (!calendarList.isEmpty()) {
				calendar = calendarList.get(0);
			}
		}
		if (calendar != null) {

			calendarEntries.clear();
			Date today = new Date();
			today.setDate(today.getDate() -1);
			for (CalendarEntry entry : calendar.getCalendarEntries()) {
				if (entry.getStartDate().after(today)) {
					calendarEntries.add(entry);
				}
			}
		}
		Message msg = serviceHandler.obtainMessage(1);
		Bundle b = new Bundle();
		Gson gson = new Gson();
		b.putString("Calendar", gson.toJson(calendar));
		b.putString("CalendarEntries", gson.toJson(calendarEntries));
		msg.setData(b);
		serviceHandler.dispatchMessage(msg);
		
	}
	public OrganizerRequester(Handler serviceHandler,Context context)
	{	
		this.context = context;
		this.serviceHandler = serviceHandler;
		requestSettings();
	}

}
