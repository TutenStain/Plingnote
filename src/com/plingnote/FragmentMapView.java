package com.plingnote;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.plingnote.R;
import com.plingnote.R.drawable;
import com.plingnote.R.id;
import com.plingnote.R.layout;
import com.plingnote.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class FragmentMapView extends Fragment {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Drawable drawable;
    private ItemOverlay itemizedoverlay;
    private View fragmentView;
    List<Overlay> mapOverlays;
    MapView mapView;
    MapController mapController;
    ActivityMain ac;
 
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_mapview, container, false);
        mapView = (MapView)fragmentView.findViewById(R.id.mapview);
	    mapOverlays = mapView.getOverlays();
	    mapController = mapView.getController();
	    ac = new ActivityMain();

		drawable = this.getResources().getDrawable(R.drawable.map_note);
	    itemizedoverlay = new ItemOverlay(drawable, ac);
	    
	    setMapAtStartUp(mapView);
		return container;

	}

	
	private void setMapAtStartUp(MapView mapView){
        mapView.setSatellite(true);
        mapView.setBuiltInZoomControls(true);
        mapController.setZoom(17);
        mapController.setCenter(new GeoPoint(57706715, 11976419));
	}
	
	public void createItemizedOverlay(GeoPoint point){
	    OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
	    
	    itemizedoverlay.addOverlay(overlayitem);
	    mapOverlays.add(itemizedoverlay);
	}
}
