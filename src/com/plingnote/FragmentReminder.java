/**
* This file is part of Plingnote.
* Copyright (C) 2012 Julia Gustafsson
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

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * A fragment representing a reminder/alarm that is using alarmmanager
 * @author Julia Gustafsson
 *
 */
public class FragmentReminder extends Fragment implements PluginableFragment{
	private View view;
	private String value = "";
	PendingIntent pendingIntent;

	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			this.view = inflater.inflate(R.layout.fragment_reminder_landscape, container, false);		
		else
			this.view = inflater.inflate(R.layout.fragment_reminder, container, false);		

		return this.view;		
	}
	
	/**
	 * Set on click listers to the button
	 */
	public void onStart(){
		super.onStart();
		
		ImageButton okey = (ImageButton) this.view.findViewById(R.id.ok);
		ImageButton cancel = (ImageButton) this.view.findViewById(R.id.cancel);

		cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {	  			
				replaceBackFragment();
			}
		});	
		
		okey.setOnClickListener(new View.OnClickListener() {
			
			//Save the time and the the alarm in the method savetime
			public void onClick(View v) {
				saveTime(v);
				replaceBackFragment();
			}
		});
	}

	/**
	 * Make an intent to broadcastreciever notenotification and save the time in datepicker and timepicker and set alarm by alarmmanager.
	 * @param view
	 */
	public void saveTime(View view){
		DatePicker datepicker = (DatePicker)this.view.findViewById(R.id.datePicker);
		TimePicker  timepicker = (TimePicker)this.view.findViewById(R.id.timePicker);	
		
		Intent intent = new Intent(getActivity(), NoteNotification.class);
		ActivityNote activityNote = (ActivityNote)getActivity();
		intent.putExtra(IntentExtra.id.toString(),activityNote.getId()); 
		pendingIntent = PendingIntent.getBroadcast(getActivity(), 0,intent, PendingIntent.FLAG_ONE_SHOT);
		
		Calendar calendar =  Calendar.getInstance();
		calendar.set(datepicker.getYear(), datepicker.getMonth(),
				datepicker.getDayOfMonth(), timepicker.getCurrentHour(), timepicker.getCurrentMinute(), 0);
		this.value = calendar.getTime()+"";
		
		AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);    
		Toast.makeText(getActivity(), "Reminder set : " + value, Toast.LENGTH_LONG).show();	
		}

	/**
	 * Return the value of this appliation
	 */
	public String getValue() {
		return this.value;
	}


	/**
	 * Return which kind of note extra this fragment is
	 */
	public NoteExtra getKind() {
		return NoteExtra.REMINDER;
	}

	/**
	 * Call replacebackfragment in the activity/fragment that host this fragment
	 */
	public void replaceBackFragment() {
		ActivityNote activityNote = (ActivityNote)getActivity();
		activityNote.replaceFragmentBack(this);
	}

	/**
	 * Return null, because this fragment doesn't have a category
	 */
	public NoteCategory getCategory() {
		return null;
	}	 
}
