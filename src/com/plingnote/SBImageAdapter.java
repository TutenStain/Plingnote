/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 Linus Karlsson
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

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Class handling how images in the Snotebar will be shown.
 * 
 * @author Linus Karlsson
 * 
 */
public class SBImageAdapter extends BaseAdapter {

	private Context context;
	private Cursor cursor;
	private String[] images;

	public SBImageAdapter(Context context, Cursor cursor, String[] images) {
		this.context = context;
		this.cursor = cursor;
		this.images = images;
	}

	public int getCount() {
		return cursor.getCount();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView view = new ImageView(context.getApplicationContext());
		
		view.setTag(images[position]);
		new SBLoadImage(view).execute();
		
		return view;
	}
	


}
