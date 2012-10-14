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

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Class taking care of the loading of the pictures from device.
 * @author Linus Karlsson
 *
 */
public class SBLoadImage extends AsyncTask<String, Void, Bitmap> {
	private String filePath;
	private final WeakReference<ImageView> reference;

	@Override
	protected Bitmap doInBackground(String... params) {
		return shrinkImage(filePath, SBImageSelector.IMAGE_WIDTH);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (reference != null) {
			ImageView imageView = reference.get();
			if (imageView != null) {
				imageView.setImageBitmap(result);
			}
		}
	}

	public SBLoadImage(ImageView imageView) {
		reference = new WeakReference<ImageView>(imageView);
		filePath = imageView.getTag().toString();
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
