package de.lindener.androidorganizer;

import android.os.Handler;

public class OrganizerRequester implements Runnable {

	Handler serviceHandler = null;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	public OrganizerRequester(Handler serviceHandler)
	{
		this.serviceHandler = serviceHandler;
		
	}

}
