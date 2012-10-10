package com.plingnote;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * A class representing a noticfication that open a specifik note. With sound and led on.
 * @author Julia Gustafsson
 *
 */
public class NoteNotification extends BroadcastReceiver  {
	NotificationManager notificationManager;
	@Override
	public void onReceive(Context context, Intent intent) {
		//Start activity with the right id.
		Intent i = new Intent();
		i.setClassName("com.plingnote", "com.plingnote.ActivityNote");
		//Setaction is needed to be set to something to make the intent open the right note.
		i.setAction(Long.toString(System.currentTimeMillis()));     
		try{
			i.putExtra(IntentExtra.id.toString(), intent.getExtras().getInt(IntentExtra.id.toString()));
			i.putExtra(IntentExtra.reminderDone.toString(), true);
		}catch(Exception e){  	
		}
		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		//Set the from which application the notifcation is
		CharSequence from = "Plingnote";
		//Sets the message by by the title of the note the notification open
		CharSequence message = "Open your note : " + DatabaseHandler.getInstance(context).getNote(intent.getExtras().getInt(IntentExtra.id.toString())).getTitle() + " !";
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, i, 0);
		Notification notification = new Notification(R.drawable.ic_launcher,"Check your note!", PendingIntent.FLAG_ONE_SHOT);
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