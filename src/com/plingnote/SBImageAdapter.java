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
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
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
	private int column;

	public SBImageAdapter(Context context, Cursor cursor, int column) {
		this.context = context;
		this.cursor = cursor;
		this.column = column;
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
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(context);
			cursor.moveToPosition(position);
			int imageID = cursor.getInt(column);
			imageView.setImageURI(Uri.withAppendedPath(
					MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, ""
							+ imageID));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new Gallery.LayoutParams(
					SBImageSelector.IMAGE_WIDTH, SBImageSelector.IMAGE_WIDTH));
		} else {
			imageView = (ImageView) convertView;
		}

		return imageView;
	}

}
