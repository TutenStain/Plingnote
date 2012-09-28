package com.plingnote;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class ActivityAppPreference extends PreferenceActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PreferenceManager prefManager = getPreferenceManager();
		prefManager.setSharedPreferencesName("appPreferences");

		addPreferencesFromResource(R.xml.app_preferences);
	}
}
