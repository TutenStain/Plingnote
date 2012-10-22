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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * A helper class for image related things.
 * @author Barnabas Sapan
 */
public class ImageHelper {
	private static Bitmap map = null;
	
	/**
	 * Decodes and returns an optimized bitmap based on required 
	 * width and height.
	 * @param filePath the absolute filepath to the image
	 * @param reqWidth the required width
	 * @param reqHeight the required height
	 * @return the loaded and optimized bitmap
	 */
	public static Bitmap decodeSampledBitmapFromUri(String filePath, int reqWidth, int reqHeight) {	
		map = null;
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inInputShareable = true;
		options.inPurgeable = true;
		options.inTempStorage = new byte[16*1024];
		map = BitmapFactory.decodeFile(filePath, options);
		
		return map;
	}

	/**
	 * Calculates the sample size that is optimal for the supplied width and height
	 * @param options
	 * @param reqWidth The required width
	 * @param reqHeight The required height
	 * @return the optimal sample size
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	        if (width > height) {
	            inSampleSize = Math.round((float)height / (float)reqHeight);
	        } else {
	            inSampleSize = Math.round((float)width / (float)reqWidth);
	        }
	    }
	    return inSampleSize;
	}	
}
