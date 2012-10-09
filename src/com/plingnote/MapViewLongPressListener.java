package com.plingnote;

import java.util.Timer;

import android.os.Handler;

import com.google.android.maps.GeoPoint;

public class MapViewLongPressListener {
	static final int LONGPRESS_THRESHOLD = 500;
	private GeoPoint lastMapCenter;
	private Timer longpressTimer = new Timer();
	final Handler handler = new Handler();
	OnMapViewLongpressListener l;
	
	
}
