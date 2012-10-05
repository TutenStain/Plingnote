package com.plingnote;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.SearchRecentSuggestions;
import android.util.Log;

public class FragmentAppPreference extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.app_preferences);
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    // Set up a listener whenever a key changes
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	    // Unregister the listener whenever a key changes
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
			sharedPreferences.edit().putBoolean(key, false).commit();
		}
	}
}
