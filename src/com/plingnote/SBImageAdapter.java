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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
		ImageView view = (ImageView) convertView;

		if (view == null) {
			view = new ImageView(context.getApplicationContext());
			view.setImageBitmap(shrinkImage(images[position],
					SBImageSelector.IMAGE_WIDTH));

		} else {
			view.setImageBitmap(shrinkImage(images[position],
					SBImageSelector.IMAGE_WIDTH));
		}

		return view;
	}

	/**
	 * Method used to shrink an image to given size. Inspiration taken from
	 * http: //stackoverflow.com/questions/477572/android-strange-out-of-memory-
	 * issue -while-loading-an-image-to-a-bitmap-object
	 * 
	 * @param s
	 *            the filepath of the image
	 * @return created bitmap
	 */
	private Bitmap shrinkImage(String s, int size) {

		// Decode image
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(s, options);

		// Find appropriate scale
		int scale = 1;
		while (options.outWidth / scale / 2 >= size
				&& options.outHeight / scale / 2 >= size) {
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options secondOptions = new BitmapFactory.Options();
		secondOptions.inSampleSize = scale;

		return BitmapFactory.decodeFile(s, secondOptions);

	}

}