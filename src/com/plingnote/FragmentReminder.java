package com.plingnote;

import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * A fragment representing a reminder/alarm that is using alarmmanager
 * @author Julia Gustafsson
 *
 */
public class FragmentReminder extends Fragment {
	private View view;

	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_alarm, container, false);		
		return view;		
	}
	/**
	 * Set on click listers to the button
	 */
	public void onStart(){
		super.onStart();
		Button okey = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {	    
				//To be implemented
			}
		});	
		okey.setOnClickListener(new View.OnClickListener() {
			//Save the time and the the alarm in the method savetime
			public void onClick(View v) {
				saveTime(v);	    
			}
		});
	}
	public void saveTime(View view){
		DatePicker datepicker = (DatePicker)this.view.findViewById(R.id.datePicker);
		TimePicker  timepicker = (TimePicker)this.view.findViewById(R.id.timePicker);
		Intent intent = new Intent(getActivity(), NoteNotification.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0,
				intent, PendingIntent.FLAG_ONE_SHOT);
		Calendar calendar =  Calendar.getInstance();
		calendar.set(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth(), timepicker.getCurrentHour(),timepicker.getCurrentMinute(), 0);
		AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);        
	}	 

}
