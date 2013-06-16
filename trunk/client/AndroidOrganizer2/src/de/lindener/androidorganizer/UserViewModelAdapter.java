package de.lindener.androidorganizer;

import java.util.List;

import de.lindener.androidorganizer.viewmodels.UserViewModel;

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

public class UserViewModelAdapter extends ArrayAdapter<UserViewModel> {

	
	Context context;
	int layoutResourceId;
	List<UserViewModel> data = null;

	
	public UserViewModelAdapter(Context context, int resource,
			int textViewResourceId, List<UserViewModel> objects) {
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
            holder.givenName = (TextView)row.findViewById(R.id.userViewModelGivenName);
            holder.surname = (TextView)row.findViewById(R.id.userViewModelSurname);
            holder.mailAddress = (TextView)row.findViewById(R.id.userViewModelMailAddress);
            holder.phoneNumber = (TextView)row.findViewById(R.id.userViewModelPhoneNumber);
            row.setTag(holder);
        }
        else
        {
            holder = (CalendarEntryHolder)row.getTag();
        }
        
        UserViewModel entry = data.get(position);
        holder.givenName.setText(entry.getGivenName());
        holder.surname.setText(entry.getSurname());     
        holder.mailAddress.setText(entry.getMailAddress());
        holder.phoneNumber.setText(entry.getPhoneNumber());
       
        
        return row;
    }
    
    static class CalendarEntryHolder
    {
    	TextView givenName;        
    	TextView surname;         
    	TextView mailAddress;
    	TextView phoneNumber;
        
       
    }

    
}
