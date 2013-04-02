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
import java.util.Date;

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
import com.plingnote.utils.Utils;

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
	private Button timeButton = null;
	private Button dateButton = null;
	private int noteId = -1;

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
		timeButton = (Button) this.view.findViewById(R.id.pick_time_button);
		dateButton = (Button) this.view.findViewById(R.id.pick_date_button);
		final Calendar c = Calendar.getInstance();
		
		//Get this notes id from the ActivityNote
		ActivityNote activityNote = (ActivityNote)getActivity();
		noteId = activityNote.getId();
		
		//Get the notes date as a string form from the database 
		String d = DatabaseHandler.getInstance(getActivity()).getNote(noteId).getAlarm();
		
		//Get the current hour and minute
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		//Set the time button text, either to the current time if no alarm is set for this note
		//or to the already set alarm
		if(!d.equals("")) {
			Date date = Utils.parseDateFromDB(d);			
			timeButton.setText(Utils.getFormatedTime(date.getHours()) + ":" + Utils.getFormatedTime(date.getMinutes()));
		} else {
			timeButton.setText(Utils.getFormatedTime(hour) + ":" + Utils.getFormatedTime(minute));
		}
		
		//Always set the current set time to the current time
		hourSet = hour;
		minutesSet = minute;

		//Add onclick to the time button
		timeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DialogFragment newFragment = new FragmentTimePicker();
				newFragment.show(getChildFragmentManager(), "timePicker");
				changedValues = true;
			}
		});

		//Get the current year, month and day
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		//Set the date button text, either to the current time if no alarm is set for this note
		//or to the already set alarm
		if(!d.equals("")) {
			Date date = Utils.parseDateFromDB(d);
			dateButton.setText((date.getYear() + 1900) + "-" + Utils.getFormatedTime(date.getMonth()) + "-" + Utils.getFormatedTime(date.getDay()));
		} else {
			dateButton.setText(year + "-" + Utils.getFormatedTime(month) + "-" + Utils.getFormatedTime(day));
		}
		
		//Always set the current set date to the current date
		yearSet = year;
		monthSet = month;
		daySet = day;
		
		//Add onclick to the date button
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

		//Add onclick to the cancel button
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {	  			
				replaceBackFragment();
			}
		});	

		//Add onclick to the confirm button
		okey.setOnClickListener(new View.OnClickListener() {			
			//Save the time and the the alarm in the method saveTime
			public void onClick(View v) {
				if(changedValues) //Only add a reminder if the user have changed the time or the date
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
		dateButton.setText(year + "-" + Utils.getFormatedTime(month) + "-" + Utils.getFormatedTime(day));
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		hourSet = hourOfDay;
		minutesSet = minute;
		timeButton.setText(Utils.getFormatedTime(hourOfDay) + ":" + Utils.getFormatedTime(minute));
	}

	/**
	 * Make an intent to broadcastreciever notenotification and save the time in datepicker and timepicker and set alarm by alarmmanager.
	 */
	public void saveTime(){
		Intent intent = new Intent(getActivity(), NoteNotification.class);
		intent.putExtra(IntentExtra.id.toString(), noteId); 	
		requestCode = DatabaseHandler.getInstance(getActivity()).getHighestRequest() + 1;
		intent.putExtra(IntentExtra.requestCode.toString(), requestCode); 
		pendingIntent = PendingIntent.getBroadcast(getActivity(), requestCode, intent, PendingIntent.FLAG_ONE_SHOT);

		Calendar calendar = Calendar.getInstance();
		calendar.set(yearSet, monthSet,	daySet, hourSet, minutesSet, 0);
		this.value = calendar.getTime().toString();

		AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);    
		Toast.makeText(getActivity(), "Reminder set at " + value, Toast.LENGTH_LONG).show();	
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
	 * Call replaceBackFragment in the activity/fragment that hosts this fragment
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
