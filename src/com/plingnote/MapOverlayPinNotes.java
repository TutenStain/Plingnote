package com.plingnote;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MapOverlayPinNotes extends Overlay {
	private Drawable marker;
	private MapView map;
	private Context context;
	private List <Note> notes = new ArrayList<Note>(); 

	public MapOverlayPinNotes(Context context, MapView mapView) {
		this.map = mapView;
		this.context = context;
		this.marker = context.getResources().getDrawable(R.drawable.position_anim);
		this.notes = DatabaseHandler.getInstance(context).getNoteList();
		
		//Test for inserting a pin near Stockholm
		//DatabaseHandler.getInstance(context).insertNote("Jul", "Snart", new Location(18.105469, 59.245662), null, null);
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		Projection projection = mapView.getProjection();
		Point center = new Point();
		
		for(Note note : notes){
			if(note.getLocation().getLatitude() != 0 && note.getLocation().getLongitude() != 0) {
				GeoPoint geoPoint = new GeoPoint((int)(note.getLocation().getLatitude() * 1E6), (int)(note.getLocation().getLongitude() * 1E6));
				projection.toPixels(geoPoint, center);
				int width = marker.getIntrinsicWidth();
				int height = marker.getIntrinsicHeight();
				this.marker.setLevel(1);
				this.marker.setBounds(center.x - width / 2, center.y - height / 2, center.x + width / 2, center.y + height / 2);
				this.marker.draw(canvas);
			}
		}		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
		}
		
		return false;
	}
}
