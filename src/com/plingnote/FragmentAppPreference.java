package com.plingnote;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.SearchRecentSuggestions;

/**
 * The application preference fragment that shows the settings view.
 * This class listens to changes in the settings view so this
 * class is also responsible for responding to the user requested
 * new settings. 
 */
public class FragmentAppPreference extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.app_preferences);
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    //Set up a listener whenever a key changes
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	    //Unregister the listener whenever a key changes
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	/**
	 * Gets called if the settings change
	 */
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Preference prefs = findPreference(key);
		
		if (key.equals("resetSearchHistory") && sharedPreferences.getBoolean(key, false)) {
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getActivity(), 
					SearchSuggestionsRecentProvider.AUTHORITY, SearchSuggestionsRecentProvider.MODE);
			suggestions.clearHistory();
			
			//Set the setting to false once we have cleared the search
			sharedPreferences.edit().putBoolean(key, false).commit();
		}
	}
}
