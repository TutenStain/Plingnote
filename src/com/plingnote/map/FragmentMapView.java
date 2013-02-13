/**
 * This file is part of Plingnote.
 * Copyright (C) 2013 Barnabas Sapan
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

package com.plingnote.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.plingnote.database.DatabaseHandler;
import com.plingnote.database.DatabaseUpdate;
import com.plingnote.database.Note;
import com.plingnote.main.ActivityNote;
import com.plingnote.utils.IntentExtra;

/**
 * A fragment containing the mapview.
 * @author Barnabas Sapan
 */
public class FragmentMapView extends SupportMapFragment implements Observer, OnMapLongClickListener, OnInfoWindowClickListener {
	private GoogleMap map;
	private List <Note> notes = new ArrayList<Note>(); 
	private HashMap<String, Integer> markerToNoteID = new HashMap<String, Integer>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setUpMapIfNeeded();

		//Add ourself as observers to get notified when things
		//change in the DB to be able to react accordingly
		DatabaseHandler.getInstance(getActivity()).addObserver(this);

		//Do a initial refresh of the markers. Show them on the map.
		this.refresh();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (map == null) {
			// Try to obtain the map from the SupportMapFragment.
			map = getMap();
			// Check if we were successful in obtaining the map.
			if (map != null)
				setUpMap();
			else {
				Toast.makeText(getActivity(), "Map not avaiable", Toast.LENGTH_SHORT).show();
				Log.e("Plingnote", "Map is null, probably not ready yet?");
			}
		}
	}

	private void setUpMap(){
		LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE); 
		Location zoom = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
		if(zoom != null) //Just to make sure that in the rare case lastKnownLocation is not null
			this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(zoom.getLatitude(), zoom.getLongitude()), 12));
		this.map.setMyLocationEnabled(true);
		this.map.setOnMapLongClickListener(this);
		this.map.setOnInfoWindowClickListener(this);
		this.map.setInfoWindowAdapter(new MarkerBaloon(getActivity(), markerToNoteID));

		/*
		 * Code for centering the view around the markers
		 * 
		// Pan to see all markers in view.
		// Cannot zoom to bounds until the map has a size.
		final View mapView = getView();
		if (mapView.getViewTreeObserver().isAlive()) {
			mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					Builder bounds = new LatLngBounds.Builder();

					notes = DatabaseHandler.getInstance(getActivity()).getNoteList();

					//Add all notes with a valid location to the map
					for(Note note : notes){
						if(note.getLocation().getLatitude() != 0 && note.getLocation().getLongitude() != 0) {
							bounds.include(new LatLng(note.getLocation().getLatitude(), note.getLocation().getLongitude()));
						}
					}
					mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

					map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50));
				}
			});
		}*/
	}

	@Override
	public void onLowMemory(){
		//Show a dialog probably
	}

	@Override
	public void onMapLongClick(LatLng pos) {
		//Do the reverse geocoding in the background
		new ReverseGeocodingTask(getActivity()).execute(pos);		
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Intent editNote = new Intent(getActivity(), ActivityNote.class);
		editNote.putExtra(IntentExtra.id.toString(), markerToNoteID.get(marker.getId()));
		editNote.putExtra(IntentExtra.justId.toString(), true);

		//Start the edit view.
		getActivity().startActivity(editNote);
	}

	/**
	 * Refreshes the note markers from the database
	 */
	public void refresh(){
		map.clear();
		this.notes.clear();		
		this.notes = DatabaseHandler.getInstance(getActivity()).getNoteList();

		//Add all notes with a valid location to the map
		for(Note note : notes){
			if(note.getLocation().getLatitude() != 0 && note.getLocation().getLongitude() != 0) {
				//As a title add the note id
				MarkerOptions op = new MarkerOptions();
				op.title(note.getTitle());
				op.snippet(note.getText());
				op.position(new LatLng(note.getLocation().getLatitude(), note.getLocation().getLongitude()));
				op.icon(BitmapDescriptorFactory.defaultMarker(20)); //the hue of the marker
				Marker marker = map.addMarker(op);
				markerToNoteID.put(marker.getId(), note.getId());
			}
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		if(observable instanceof DatabaseHandler) {
			if((DatabaseUpdate)data == DatabaseUpdate.UPDATED_LOCATION 
					|| (DatabaseUpdate)data == DatabaseUpdate.NEW_NOTE 
					|| (DatabaseUpdate)data == DatabaseUpdate.UPDATED_NOTE 
					|| (DatabaseUpdate)data == DatabaseUpdate.DELETED_NOTE) {
				this.refresh();
			}
		}
	}

	//From http://wptrafficanalyzer.in/blog/android-reverse-geocoding-at-touched-location-in-google-map-android-api-v2/
	private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String>{
		Context mContext;
		double latitude;
		double longitude;

		public ReverseGeocodingTask(Context context){
			super();
			mContext = context;
		}

		// Finding address using reverse geocoding
		@Override
		protected String doInBackground(LatLng... params) {
			Geocoder geocoder = new Geocoder(mContext);
			latitude = params[0].latitude;
			longitude = params[0].longitude;

			List<Address> addresses = null;
			String addressText="";

			try {
				addresses = geocoder.getFromLocation(latitude, longitude, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(addresses != null && addresses.size() > 0){
				Address address = addresses.get(0);

				addressText = String.format("%s, %s, %s",
						address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
								address.getLocality(),
								address.getCountryName());
			}

			return addressText;
		}

		@Override
		protected void onPostExecute(String addressText) {			
			Intent intent = new Intent(getActivity(), ActivityNote.class);
			intent.putExtra(IntentExtra.longitude.toString(), longitude);
			intent.putExtra(IntentExtra.latitude.toString(), latitude);
			intent.putExtra(IntentExtra.city.toString(), addressText);
			intent.putExtra(IntentExtra.id.toString(), -1);
			getActivity().startActivity(intent);
		}
	}
}
