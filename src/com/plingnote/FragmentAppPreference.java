package com.plingnote;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.SearchRecentSuggestions;

/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 Barnabas Sapan
 * 
 * Plingnote is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
