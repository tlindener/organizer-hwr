package de.lindener.androidorganizer.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import de.lindener.androidorganizer.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
    
}