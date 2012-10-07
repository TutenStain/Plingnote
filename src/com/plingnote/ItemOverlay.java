package com.plingnote;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 Magnus Huttu
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

/**
 * 
 * @author Magnus Huttu
 *
 *	This is an object which you put on the map and it will 
 *	simply hover above a given position. This object will on click give a short
 *	dialog with two strings.
 *
 */
public class ItemOverlay extends ItemizedOverlay{

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context mContext = null;

	public ItemOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override  
	public int size() {
		return mOverlays.size();
	}

	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

/**
 * This enables the function to click on an object
 * which is of this class and get response with a dialog.
 * 
 * @Param index finds which object the user clicked.
 */
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle() + "-Options");
	  dialog.setMessage(item.getSnippet());
	  dialog.show();
	  return true;
	}

}