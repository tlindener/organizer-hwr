package de.lindener.androidorganizer;

import java.util.List;

import organizer.objects.types.CalendarEntry;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CalendarEntryAdapter extends ArrayAdapter<CalendarEntry> {

	
	Context context;
	int layoutResourceId;
	List<CalendarEntry> data = null;

	
	public CalendarEntryAdapter(Context context, int resource,
			int textViewResourceId, List<CalendarEntry> objects) {
		super(context, resource, textViewResourceId, objects);
	
		this.layoutResourceId = resource;
		this.context = context;
		this.data = objects;
		
		
		
	}



	

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CalendarEntryHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new CalendarEntryHolder();
            holder.startDate = (TextView)row.findViewById(R.id.calendarEntryStartDate);
            holder.endDate = (TextView)row.findViewById(R.id.calendarEntryEndDate);
            holder.entryTitle = (TextView)row.findViewById(R.id.calendarEntryTitle);
            
            row.setTag(holder);
        }
        else
        {
            holder = (CalendarEntryHolder)row.getTag();
        }
        
        CalendarEntry entry = data.get(position);
        holder.startDate.setText("Beginn: " + entry.getStartHour()+ ":" + entry.getStartMinute());
        holder.endDate.setText("Ende: " +entry.getEndHour() +":"+entry.getEndMinute());        
        holder.entryTitle.setText(entry.getTitle());
       
        
        return row;
    }
    
    static class CalendarEntryHolder
    {
        TextView startDate;
        TextView endDate;
        TextView entryTitle;
    }

    
}
