package com.plingnote.map;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.plingnote.R;
import com.plingnote.database.DatabaseHandler;
import com.plingnote.database.Note;
import com.plingnote.listview.ListDateHandler;

public class MarkerBaloon implements InfoWindowAdapter {
	private View view = null;
	private LayoutInflater inflater;
	private Context context;
	private final HashMap<String, Integer> markerToNoteID;
	private DatabaseHandler db;

	public MarkerBaloon(Context context, final HashMap<String, Integer> markerToNoteID){
		this.context = context;
		db = DatabaseHandler.getInstance(context);
		this.markerToNoteID = markerToNoteID;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.marker_baloon, null);
	}


	@Override
	public View getInfoContents(Marker marker) {
		Note note = db.getNote(markerToNoteID.get(marker.getId()));

		TextView txtTitle = ((TextView) view.findViewById(R.id.title));
		if(!marker.getTitle().equals("")){
			txtTitle.setText(marker.getTitle());
		}else
			txtTitle.setText("(no title)");
		
		TextView txtType = ((TextView) view.findViewById(R.id.text));
		if(!marker.getSnippet().equals(""))
			txtType.setText(marker.getSnippet());
		else
			txtType.setText("(empty note)");

		TextView location = ((TextView) view.findViewById(R.id.place));
		db.getNote(markerToNoteID.get(marker.getId()));
		location.setText("(" + ListDateHandler.customDateFormat(note.getDate()) + ")");

		LinearLayout iconField = (LinearLayout) view.findViewById(R.id.icons);
		iconField.removeAllViews();

		if(!note.getImagePath().equals("")){
			ImageView imageIcon = new ImageView(this.context);
			imageIcon.setImageResource(R.drawable.ic_image_icon);
			imageIcon.setLayoutParams(new ViewGroup.LayoutParams(34, 34));
			iconField.addView(imageIcon);
		}

		if(!note.getAlarm().equals("")) {
			ImageView alarmIcon = new ImageView(this.context);
			alarmIcon.setImageResource(R.drawable.ic_alarm_icon);
			alarmIcon.setLayoutParams(new ViewGroup.LayoutParams(34, 34));
			iconField.addView(alarmIcon);
		}

		return view;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}

}
