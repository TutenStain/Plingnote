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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * A class representing a noticfication that open a specifik note. With sound and led on.
 * @author Julia Gustafsson
 */
public class NoteNotification extends BroadcastReceiver  {
	private NotificationManager notificationManager;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		//Start activity with the right id.
		Intent i = new Intent();
		i.setClassName("com.plingnote", "com.plingnote.ActivityNote");
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		//Setaction is needed to be set to something to make the intent open the right note.
		i.setAction(Long.toString(System.currentTimeMillis()));    
		
		try{			
			i.putExtra(IntentExtra.id.toString(), intent.getExtras().getInt(IntentExtra.id.toString()));
			i.putExtra(IntentExtra.reminderDone.toString(), true);
			i.putExtra(IntentExtra.justId.toString(), true);	
		}catch(Exception e){  	
			Log.i("Catched Exception", "Intent is not send to the class");
		}
		
		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		//Set the from which application the notifcation is
		CharSequence from = "Plingnote";
		
		//Sets the message by by the title of the note the notification open
		CharSequence message = "Open your note : " + DatabaseHandler.getInstance(context).getNote(intent.getExtras().getInt(IntentExtra.id.toString())).getTitle() + " !";
		PendingIntent contentIntent = PendingIntent.getActivity(context, intent.getExtras().getInt(IntentExtra.requestCode.toString()), i,  PendingIntent.FLAG_ONE_SHOT);
		
		//Getting the curren time
		Calendar calender = Calendar.getInstance();
	    calender.setTimeInMillis(System.currentTimeMillis());
	    calender.add(Calendar.MILLISECOND, 1000);
	    long timeInMillised = calender.getTimeInMillis();
		Notification notification = new Notification(R.drawable.plingnote_icon2,"Check your note!",timeInMillised);
		notification.setLatestEventInfo(context, from, message, contentIntent);
		
		//Sets sound and led settings
		notification.defaults |= Notification.DEFAULT_SOUND; 
		notification.flags |= Notification.FLAG_AUTO_CANCEL|Notification.FLAG_SHOW_LIGHTS; 
		notification.ledARGB = 0xFFff00ff;
		notification.ledOnMS = 1000; 
		notification.ledOffMS = 1000;  
		notificationManager.notify(0, notification);
	}   
}