package com.plingbar;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.plingbar.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MotionEvent;

public class FragmentMapView extends MapActivity {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Drawable drawable;
    private ItemOverlay itemizedoverlay;
    List<Overlay> mapOverlays;
    MapView mapView;
    MapController mapController;

    @Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_fragment_mapview);
	    mapView = (MapView) findViewById(R.id.mapview);
	    mapOverlays = mapView.getOverlays();
	    mapController = mapView.getController();

		drawable = this.getResources().getDrawable(R.drawable.map_note);
	    itemizedoverlay = new ItemOverlay(drawable, this);
	    
	    setMapAtStartUp(mapView);

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fragment_mapview, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
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
