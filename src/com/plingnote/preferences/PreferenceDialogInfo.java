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

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * A custom class for displaying basic info with a
 * dismiss button. To be used in Preferences. This is just 
 * used to distinguish itself from the other preference classes,
 * just calls super.
 * @author Barnabas Sapan
 */
public class PreferenceDialogInfo extends DialogPreference{
	public PreferenceDialogInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPersistent(false);
    }
}
