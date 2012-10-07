/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 Barnabas sapan
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

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

/**
 * The map activity
 * @author Barnabas Sapan
 */
public class ActivityMap extends MapActivity implements LocationListener {
	private final int DISABLE_GPS_WITH_ACCURACY_HIGHER_THEN = 100;
	private final int GPS_SIGNIFICANTLY_BETTER_DATA_IF = 1000 * 60 * 2;
	
	private MapController mc;
	private MapView map;
	private LocationManager locationManager;
	private String provider;
	private Location location;
	private boolean isWantingAFix = false;
	private Criteria criteria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_mapview);
		map = (MapView) findViewById(R.id.mapview);
		mc = map.getController();

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		//Get the last known location from the network so we 
		//quickly can zoom to the users position 
		location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		//Zoom as soon as possible
		zoomToCurrentPosition();		

		//Criteria for an accurate fix, presumably GPS
		criteria = new Criteria();
		criteria.setCostAllowed(true);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void click(View view) {
		isWantingAFix = true;

		provider = locationManager.getBestProvider(criteria, false);
		locationManager.requestLocationUpdates(provider, 400, 1, this);

		zoomToCurrentPosition();
	}

	private void zoomToCurrentPosition(){
		GeoPoint point = new GeoPoint((int)(location.getLatitude() * 1E6), (int)(location.getLongitude() * 1E6));
		mc.animateTo(point);

		List<Overlay> list = map.getOverlays();
		list.clear();
		list.add(new MapOverlayPin(this, map, point));
		list.add(new MapOverlayGPSAccuracy(point, location));

		map.invalidate();

		mc.setZoom(19);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(isWantingAFix)
			locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(isWantingAFix)
			locationManager.removeUpdates(this);
	}


	public void onLocationChanged(Location location) {
		if (isBetterLocation(this.location, location)) {
			this.location = location;
			zoomToCurrentPosition();
		}

		//If we get a fix that is accurate enough, remove subscription from location updates
		if(location.getAccuracy() <= DISABLE_GPS_WITH_ACCURACY_HIGHER_THEN){
			locationManager.removeUpdates(this);
		}
	}

	public void onProviderDisabled(String provider) { }

	public void onProviderEnabled(String provider) { }

	public void onStatusChanged(String provider, int status, Bundle extras) { }

	//Taken from Android Developers Guide examples 
	//(http://developer.android.com/guide/topics/location/strategies.html)
	/** Determines whether one Location reading is better than the current Location fix
	 * @param location  The new Location that you want to evaluate
	 * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	 */
	private boolean isBetterLocation(Location location, Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > GPS_SIGNIFICANTLY_BETTER_DATA_IF;
		boolean isSignificantlyOlder = timeDelta < -GPS_SIGNIFICANTLY_BETTER_DATA_IF;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}
}