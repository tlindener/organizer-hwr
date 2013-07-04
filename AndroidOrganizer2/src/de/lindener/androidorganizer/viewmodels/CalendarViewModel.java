package de.lindener.androidorganizer.viewmodels;

import java.util.ArrayList;
import java.util.List;

import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;

import android.widget.CalendarView;

/**
 * Model to easily bind data to views
 * @author TobiasLindener
 */
public class CalendarViewModel {
	
	Calendar calendar;
	List<CalendarEntry> calendarEntries;
	
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public void setCalendarEntries(List<CalendarEntry> calendarEntries) {
		this.calendarEntries = calendarEntries;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public List<CalendarEntry> getCalendarEntries() {
		return calendarEntries;
	}

	public CalendarViewModel()
	{
		calendarEntries = new ArrayList<CalendarEntry>();
	}

}
