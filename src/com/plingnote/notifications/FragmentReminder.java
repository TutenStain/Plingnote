/**
* This file is part of Plingnote.
* Copyright (C) 2012 Julia Gustafsson, Barnabas Sapan
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

package com.plingnote.notifications;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.plingnote.R;
import com.plingnote.database.DatabaseHandler;
import com.plingnote.main.ActivityNote;
import com.plingnote.snotebar.NoteCategory;
import com.plingnote.snotebar.PluginableFragment;
import com.plingnote.utils.IntentExtra;
import com.plingnote.utils.NoteExtra;

/**
 * A fragment representing a reminder/alarm that is using alarmmanager
 * @author Julia Gustafsson
 * @Modifiedby Barnabas Sapan
 *
 */
public class FragmentReminder extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, PluginableFragment{
	private View view;
	private String value = "";
	private PendingIntent pendingIntent;
	private int requestCode;
	private boolean changedValues = false;
	private int yearSet, monthSet, daySet, hourSet, minutesSet;

	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			this.view = inflater.inflate(R.layout.fragment_reminder_landscape, container, false);		
		else
			this.view = inflater.inflate(R.layout.fragment_reminder, container, false);		

		return this.view;		
	}
	
	/**
	 * Attach onClick listeners to the buttons
	 */
	public void onStart(){
		super.onStart();

		final Button timeButton = (Button) this.view.findViewById(R.id.pick_time_button);
		final Calendar c = Calendar.getInstance(); 
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		timeButton.setText(hour + ":" + minute);
		timeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DialogFragment newFragment = new FragmentTimePicker();
			    newFragment.show(getChildFragmentManager(), "timePicker");
			    changedValues = true;
			}
		});
		
		Button dateButton = (Button) this.view.findViewById(R.id.pick_date_button);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		dateButton.setText(year + "-" + month + "-" + day);
		dateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				 DialogFragment newFragment = new FragmentDatePicker();
				 newFragment.show(getChildFragmentManager(), "datePicker");
				 changedValues = true;
			}
		});
		
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
				if(changedValues)
					saveTime();
				else
					Toast.makeText(getActivity(), "No reminder set, please change the time or date", Toast.LENGTH_LONG).show();
				
				replaceBackFragment();
			}
		});
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		yearSet = year;
		monthSet = month;
		daySet = day;
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		hourSet = hourOfDay;
		minutesSet = minute;
	}
	
	/**
	 * Make an intent to broadcastreciever notenotification and save the time in datepicker and timepicker and set alarm by alarmmanager.
	 * @param view
	 */
	public void saveTime(){
		Intent intent = new Intent(getActivity(), NoteNotification.class);
		ActivityNote activityNote = (ActivityNote)getActivity();
		intent.putExtra(IntentExtra.id.toString(), activityNote.getId()); 	
		requestCode = DatabaseHandler.getInstance(getActivity()).getHighestRequest() + 1;
		intent.putExtra(IntentExtra.requestCode.toString(),requestCode); 
		pendingIntent = PendingIntent.getBroadcast(getActivity(), requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(yearSet, monthSet,	daySet, hourSet, minutesSet, 0);
		this.value = calendar.getTime() + "";
		
		AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);    
		Toast.makeText(getActivity(), "Reminder set : " + value, Toast.LENGTH_LONG).show();	
	}

	/**
	 * Return the value of this application
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Return the value of this application
	 */
	public int getRequestCode() {
		return this.requestCode;
	}

	/**
	 * Return which kind of note extra this fragment is
	 */
	public NoteExtra getKind() {
		return NoteExtra.REMINDER;
	}

	/**
	 * Call replacebackfragment in the activity/fragment that hosts this fragment
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
