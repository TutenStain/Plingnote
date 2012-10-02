package com.plingnote;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;

/**
 * The activity that handles the settings menu
 */
public class ActivityAppPreference extends PreferenceActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.menu_settings);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		PreferenceManager prefManager = getPreferenceManager();
		prefManager.setSharedPreferencesName("appPreferences");

		addPreferencesFromResource(R.xml.app_preferences);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, ActivityMain.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
