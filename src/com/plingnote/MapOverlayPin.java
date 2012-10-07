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

/**
 * Displays an animated pin for the current location
 * @author Barnabas Sapan
 */

public class MapOverlayPin extends Overlay {
	private Drawable marker;
	private MapView map;
	private int level;
	private Timer timer;
	private GeoPoint geoPoint;

	public MapOverlayPin(Context context, MapView mapView, GeoPoint geoPoint) {
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
