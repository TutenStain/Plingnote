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
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);       
		try{
			i.putExtra(IntentExtra.id.toString(), intent.getExtras().getInt(IntentExtra.id.toString()));
		}catch(Exception e){  	
		}
		//context.startActivity(i);
		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		//Set the from which application the notifcation is
		CharSequence from = "Plingnote";
		//Sets the message by by the title of the note the notification open
		CharSequence message = "Open your note : " + DatabaseHandler.getInstance(context).getNote(1).getTitle() + " !";
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, i, 0);
		Notification notification = new Notification(R.drawable.ic_launcher,"ut text", PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(context, from, message, contentIntent);
		//Sets sound and led settings
		notification.defaults |= Notification.DEFAULT_SOUND; 
		notification.flags |= Notification.FLAG_AUTO_CANCEL|Notification.FLAG_SHOW_LIGHTS; 
		notification.ledARGB = 0xFFff00ff;
		notification.ledOnMS = 100; 
		notification.ledOffMS = 100;  
		notificationManager.notify(0, notification);
	}   
}