package com.plingnote;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import com.plingnote.android.apis.Locations;
import com.plingnote.android.apis.Locations.Entry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentMapView extends Fragment {
		private View mMapViewContainer;

		private MapController mMapController;

		/**
		 * Create a new instance of FragmentMapView, initialized to
		 * show the location Entry at 'index'.
		 */
		public FragmentMapView newInstance(int index) {
			FragmentMapView f = new FragmentMapView();

			// Supply index input as an argument.
			Bundle args = new Bundle();
			args.putInt("index", index);
			f.setArguments(args);

			return f;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView( inflater, container, savedInstanceState );

			/*	We've tied the MapView's lifecycle to the MapActivity (FragmentLayout
				extends FragmentActivity which extends MapActivity (in the
				android-support-v4-googlemaps library)).
				
				Unfortunately we have to grab a reference to it here, which means
				we have to have a reference to FragmentLayout (a _specific_ 
				implementation of FragmentActivity).  This is a hack.
				
				We could "inject" (or pass in) these via FragmentMapView's constructor,
				but it seems to break the general usage pattern of Fragments (i.e.
				pass in a Bundle of arguments, each of which is Parcelable / Serializable).
			
				I chose to do call up to FragmentLayout to do this, but injecting
				the references into the constructor might be better.  Its up to you.
			 * 
			 */
			ActivityMain mapActivity = (ActivityMain) getActivity();
			mMapViewContainer = mapActivity.mMapViewContainer;
			MapView mMapView = mapActivity.mMapView;
			if( null != mMapView ) {
				int index = getArguments().getInt( "index" );
				Entry e = Locations.ENTRIES[index];

				mMapView.setBuiltInZoomControls( true );
				mMapView.setSatellite( true );

				mMapController = mMapView.getController();
				mMapController.animateTo( new GeoPoint( (int)(e.lat * 1.0e6), (int)(e.lng * 1.0e6) ) );
				mMapController.setZoom( e.zoomLevel );
			}

			return mMapViewContainer;
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();

			// The way MainActivity creates this fragment, it will call onCreateView()
			// each time we start (or navigate back to) this map.  To prevent the
			// "You are only allowed to have a single MapView in a MapActivity" message,
			// we only inflate the map's XML layout once.  When we try to add it a second
			// time, we get "IllegalStateException: The specified child already has a 
			// parent. You must call removeView() on the child's parent first."
			// So, here we remove the view from MainActivity's parent layout
			// so we can re-add it later when onCreateView() is called.
			// TODO: change this once the map doesn't go away (i.e. on Tablets)
			ViewGroup parentViewGroup = (ViewGroup) mMapViewContainer.getParent();
			if( null != parentViewGroup ) {
				parentViewGroup.removeView( mMapViewContainer );
			}
		}
	}