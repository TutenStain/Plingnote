/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 David Grankvist
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

import java.util.List;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

/**
 * A service that runs in the bakground to retreive the user's location.
 * When the current location is updated, proximity alerts will be added to
 * positions stored in the database.
 * 
 * @author David Grankvist
 *
 */
public class LocationService extends Service implements LocationListener {
	private LocationManager locationManager;
	private String provider;
	private Criteria criteria;
	private static final long UPDATE_FREQUENCY_TIME = 1000; //milliseconds
	private static final float UPDATE_FREQUENCY_DISTANCE = 1; //meters
	private static final int ALERT_RADIUS = 100; //meters

	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		super.onStartCommand(intent, flags, startId);
		this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		this.criteria = new Criteria();
		this.criteria.setCostAllowed(true);
		this.criteria.setAltitudeRequired(false);
		this.criteria.setBearingRequired(false);
		this.criteria.setAccuracy(Criteria.ACCURACY_FINE);
		this.provider = this.locationManager.getBestProvider(this.criteria, false);
		this.locationManager.requestLocationUpdates(this.provider, 
				UPDATE_FREQUENCY_TIME, UPDATE_FREQUENCY_DISTANCE, this);
		return START_STICKY;
	}

	public void onLocationChanged(Location location){
		this.addAlerts();
	}
	
	/**
	 * This method adds proximity alerts to all locations in the database
	 * When the alert is triggered, an intent to start NoteNotification is fired
	 */
	private void addAlerts(){
		DatabaseHandler dbHandler = DatabaseHandler.getInstance(this);
		List<Note> nlist = dbHandler.getNoteList();
		for(Note n:nlist){
			com.plingnote.Location loc = n.getLocation();
			Intent intent = new Intent(this, NoteNotification.class);
			intent.putExtra(IntentExtra.id.toString(), n.getId());
			PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
			this.locationManager.addProximityAlert(loc.getLatitude(), 
					loc.getLongitude(), ALERT_RADIUS, -1, pIntent);
		}
	}

	public void onProviderDisabled(String provider) {

	}

	public void onProviderEnabled(String provider) {

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
