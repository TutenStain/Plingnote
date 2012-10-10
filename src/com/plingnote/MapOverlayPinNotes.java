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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapOverlayPinNotes extends ItemizedOverlay<OverlayItem> {
	private Context context;
	private List <Note> notes = new ArrayList<Note>(); 
	private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();

	/**
	 * @param context the context
	 * @param marker the marker to display on the map
	 */
	public MapOverlayPinNotes(Context context, Drawable marker) {
		super(boundCenterBottom(marker));

		this.context = context;
		this.notes = DatabaseHandler.getInstance(context).getNoteList();
	
		//TEST Inserting notes if no notes with location exist in the database
		//DatabaseHandler.getInstance(context).insertNote("Jul", "Snart", new Location(18.105469, 59.245662), "", "");
		//DatabaseHandler.getInstance(context).insertNote("Jul2", "Snart2", new Location(12.513428, 57.710604), "", "");
		//DatabaseHandler.getInstance(context).insertNote("Jul3", "Snart3", new Location(12.282715, 57.516413), "", "");
				
		//Add all notes with a valid location to the map
		for(Note note : notes){
			if(note.getLocation().getLatitude() != 0 && note.getLocation().getLongitude() != 0) {
				GeoPoint point = new GeoPoint((int)(note.getLocation().getLatitude() * 1E6), (int)(note.getLocation().getLongitude() * 1E6));
				//Ad a title add the note id
				this.addOverlay(new OverlayItem(point, note.getId() + "", null));
			}
		}
		
		//Make sure that we always call populate even if there are no notes with location
		if(notes.isEmpty())
			populate();
			
	}
	
	@Override
	protected boolean onTap(int index) {
		OverlayItem item = overlays.get(index);
		
		Intent editNote = new Intent(context, ActivityNote.class);
		editNote.putExtra(IntentExtra.id.toString(), Integer.parseInt(item.getTitle()));

		//Start the edit view.
		context.startActivity(editNote);

		return true;
	}

	@Override
	protected OverlayItem createItem(int i) {
		 return overlays.get(i);
	}

	@Override
	public int size() {
		return overlays.size();
	}
	
	/**
	 * Add an overlay to this Overlay
	 * @param overlay the overlay to add
	 */
	private void addOverlay(OverlayItem overlay) {
	    overlays.add(overlay);
	    populate();
	}
}
