package com.plingnote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentImageGridView extends Fragment{

	public final static int[] imageResIds = new int[]{
		R.drawable.category_banking, R.drawable.category_chat, R.drawable.category_fun,
		R.drawable.category_lunch, R.drawable.category_meeting, R.drawable.category_shop,
		R.drawable.category_write
	};



	public FragmentImageGridView(){}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_imageview, container, false);
	}
	


	/**
	 * Taken from following page, top answer.
	 * http://stackoverflow.com/questions/
	 * 4825214/optimal-use-of-bitmapfactory-options-insamplesize-for-speed
	 * 
	 * @param picture
	 *            the path to the picture
	 * @param w
	 *            width of choice
	 * @param h
	 *            height of choice
	 * @return created bitmap
	 */
	public Bitmap shrink(String picture, int w, int h) {
		BitmapFactory.Options options = new BitmapFactory.Options();

		// Set so decoder returns null.
		options.inJustDecodeBounds = true;

		// Decode file into bitmap.
		Bitmap bitmap = BitmapFactory.decodeFile(picture, options);

		int width = (int) Math.ceil(options.outWidth / (float) w);
		int height = (int) Math.ceil(options.outHeight / (float) h);

		if (width > 1 && height > 1) {
			if (width > height) {
				options.inSampleSize = width;
			} else {
				options.inSampleSize = height;
			}
		}

		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(picture, options);

		return bitmap;
	}
	
}
