/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 Barnabas Sapan
 * 
 * Plingnote is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.plingnote.preferences;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

/**
 * The activity that handles the settings menu.
 * This just basically replaces the current view 
 * with the preference fragment.
 * 
 * @author Barnabas Sapan
 */
public class ActivityAppPreference extends PreferenceActivity {	

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Display the fragment as the main content
        getFragmentManager().beginTransaction().replace(android.R.id.content, new FragmentAppPreference()).commit();
    }
	
	/**
	 * Make sure that once the back button is pressed
	 * that we finish this activity.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
