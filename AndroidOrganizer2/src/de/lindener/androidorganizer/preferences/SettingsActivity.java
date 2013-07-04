package de.lindener.androidorganizer.preferences;

import android.app.Activity;
import android.os.Bundle;
/**
 * Activity to display settings fragment
 * @author TobiasLindener
 *
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
