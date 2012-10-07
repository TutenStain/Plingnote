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

public class ActivityMap extends MapActivity implements LocationListener {
	private final int DISABLE_GPS_WITH_ACCURACY_HIGHER_THEN = 200;
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
		mc.animateTo(new GeoPoint((int)(location.getLatitude() * 1E6), (int)(location.getLongitude() * 1E6)));
		
		List<Overlay> list = map.getOverlays();
		list.clear();
		list.add(new MapOverlayPin(this, map, new GeoPoint((int)(location.getLatitude() * 1E6), (int)(location.getLongitude() * 1E6))));
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
		this.location = location;
		
		//If we get a fix that is accurate enough, remove subscription from location updates
		if(location.getAccuracy() <= DISABLE_GPS_WITH_ACCURACY_HIGHER_THEN){
			locationManager.removeUpdates(this);
		}
	}

	public void onProviderDisabled(String provider) { }

	public void onProviderEnabled(String provider) { }

	public void onStatusChanged(String provider, int status, Bundle extras) { }
}