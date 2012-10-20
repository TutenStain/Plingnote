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

package com.plingnote;

import android.app.AlertDialog;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * A custom class for displaying basic info with a
 * dismiss button. To be used in Preferences.
 * @author Barnabas Sapan
 */
public class PreferenceDialogInfo extends DialogPreference{
	private String dialogeMessage;
	
	public PreferenceDialogInfo(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.dialogeMessage = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "dialogMessage");
		setPersistent(false);
		setDialogLayoutResource(R.layout.preference_dialog_info);
	}
	
	@Override
	protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
		builder.setPositiveButton(null, null);
		builder.setNegativeButton(null, null);
		super.onPrepareDialogBuilder(builder);  
	}
	
	@Override
	public void onBindDialogView(View view){
		Button okButon = (Button)view.findViewById(R.id.info_ok_button);
		okButon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});
		
		TextView textView = (TextView)view.findViewById(R.id.info_textview);
		textView.setText(dialogeMessage);

		super.onBindDialogView(view);
	}

}
