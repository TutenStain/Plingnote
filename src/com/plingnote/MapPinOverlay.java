package com.plingnote;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MapPinOverlay extends Overlay {
	private Drawable marker;
	private MapView map;
	private int level;
	private Timer timer;
	private GeoPoint geoPoint;

	public MapPinOverlay(Context context, MapView mapView, GeoPoint geoPoint) {
		this.map = mapView;
		this.geoPoint = geoPoint;
		this.marker = context.getResources().getDrawable(R.drawable.position_anim);
		this.timer = new Timer();
		this.timer.schedule(new TimerTask() {
			@Override
			public void run() {
				level = (level + 1) % 3;
				map.postInvalidate();
			}
		}, 750, 750);
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		Projection projection = mapView.getProjection();
		Point center = new Point();
		projection.toPixels(this.geoPoint, center);

		int width = marker.getIntrinsicWidth();
		int height = marker.getIntrinsicHeight();

		this.marker.setLevel(level);
		this.marker.setBounds(center.x - width / 2, center.y - height / 2, center.x + width / 2, center.y + height / 2);
		this.marker.draw(canvas);
	}
}
