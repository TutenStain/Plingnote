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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

/**
 * A class representing a noticfication that open a specifik note. With sound and led on.
 * @author Julia Gustafsson
 */
public class NoteNotification extends BroadcastReceiver  {
	private NotificationManager notificationManager;
	private static int numUnreadMessages = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		buildNotification(context,intent);
	}
	
	public void buildNotification(Context context, Intent intent){
		
		this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		//Cancel all previous notifications
		//notificationManager.cancelAll();
		
		//The number of unread messagesincreases
		numUnreadMessages++;
		
		//Start activity with the right id.
		Intent i = new Intent();
		//i.setClassName("com.plingnote", "com.plingnote.ActivityNote");
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

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		
		// Adds the back stack
		stackBuilder.addParentStack(ActivityNote.class);
		
		// Adds the Intent to the top of the stack
		stackBuilder.addNextIntent(i);

		//Set the from which application the notifcation is
		CharSequence from = "Plingnote";

		//Sets the message by by the title of the note the notification open
		CharSequence message = "Open your note : " + DatabaseHandler.getInstance(context).getNote(intent.getExtras().getInt(IntentExtra.id.toString())).getTitle() + " !";
		PendingIntent contentIntent = PendingIntent.getActivity(context, intent.getExtras().getInt(IntentExtra.requestCode.toString()), i,  PendingIntent.FLAG_ONE_SHOT);
		CharSequence ticker = "Check your note!";

		Resources res = context.getResources();
		Notification.Builder builder = new Notification.Builder(context);

		//Sets all the settings of the notifications builder
		builder.setContentIntent(contentIntent)
			.setSmallIcon(R.drawable.plingnote_icon2)
			.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.plingnote_icon2))
			.setTicker(ticker)
			.setWhen(System.currentTimeMillis())
			.setAutoCancel(true)
			.setContentTitle(from)
			.setNumber(numUnreadMessages)
			.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | 
					Notification.FLAG_SHOW_LIGHTS |Notification.FLAG_AUTO_CANCEL)				
			.setLights(0xFFff00ff, 1000, 1000);

		//If it's only 1 "unread" the user will se another text showing that notes title
		if(numUnreadMessages == 1)
			builder.setContentText(message);
		else{
			
			//If it's more than 1 unread note the user will se a text that says how many "unread" notes the user has
			message = "You have " + numUnreadMessages + " reminders fired";
			builder.setContentText(message);
		}

		Notification notification = builder.getNotification();
		notificationManager.notify(0, notification);
	}
	
	public static int getnumUnreadMessages() {
		return numUnreadMessages;
	}

	public static void setnumUnreadMessages(int numMessages) {
		NoteNotification.numUnreadMessages = numMessages;
		
	}   
}