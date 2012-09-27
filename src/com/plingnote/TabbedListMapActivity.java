package com.plingnote;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class TabbedListMapActivity extends MapActivity implements OnTabChangeListener{

	private TabHost tabHost;
	private ListView listView;
	private MapView mapView;
	
	public void onCreate(){
		setContentView(R.layout.activity_main);

		tabHost = (TabHost) findViewById(R.id.main);
		// setup must be called if you are not inflating the tabhost from XML
		tabHost.setup();
		tabHost.setOnTabChangedListener((OnTabChangeListener) this);

		
		// setup list view
		listView = (ListView) findViewById(R.id.list);
		listView.setEmptyView((TextView) findViewById(R.id.empty));

		// create some dummy coordinates to add to the list
		List<GeoPoint> pointsList = new ArrayList<GeoPoint>();
		pointsList.add(new GeoPoint((int)(32.864*1E6), (int)(-117.2353*1E6)));
		pointsList.add(new GeoPoint((int)(37.441*1E6), (int)(-122.1419*1E6)));
		listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, pointsList));
		
		// setup map view
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.postInvalidate();
		
		// add views to tab host
		tabHost.addTab(tabHost.newTabSpec("List").setIndicator("List").setContent(new TabContentFactory() {
			public View createTabContent(String arg0) {
				return listView;
			}
		}));
		tabHost.addTab(tabHost.newTabSpec("Map").setIndicator("Map").setContent(new TabContentFactory() {
			public View createTabContent(String arg0) {
				return mapView;
			}
		}));
		
        tabHost.setCurrentTab(1);
        tabHost.setCurrentTab(0);

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
