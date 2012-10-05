package com.plingnote;

import android.os.Bundle;
import android.preference.PreferenceActivity;


/**
 * The activity that handles the settings menu.
 * This just basically replaces the current view 
 * with the preference fragment.
 */
public class ActivityAppPreference extends PreferenceActivity {	

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content
        getFragmentManager().beginTransaction().replace(android.R.id.content, new FragmentAppPreference()).commit();
    }
}
