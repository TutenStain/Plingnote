package com.plingnote;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Class handling the pictures to be filled in HorizontalScrollView.
 * 
 * @author Linus Karlsson
 * 
 */
public class PictureViewer extends LinearLayout {

	/**
	 * Content of the application's current state.
	 */
	private Context context;

	/**
	 * List of paths to pictures.
	 */
	private List<String> pictures;

	public PictureViewer(Context context) {
		super(context);
		this.context = context;
		pictures = new ArrayList<String>();
	}

	/**
	 * Add picture to list.
	 * 
	 * @param picture
	 */
	public void add(String picture) {
		// Add picture to the list.
		pictures.add(picture);
		// Display the picture to the user.
		addView(getImageView(pictures.size() - 1));
	}

	/**
	 * Display the pictures as thumbnails to the user.
	 * 
	 * @param listSize
	 *            size of the list before a picture was added.
	 * @return
	 */
	public ImageView getImageView(int listSize) {

		/**
		 * Size of displayed thumbnails.
		 */
		int picWidth = 200;
		int picHeight = 200;

		Bitmap bitmap = null;

		/**
		 * Create bitmaps of all the pictures.
		 */
		if (listSize < pictures.size()) {
			bitmap = shrink(pictures.get(listSize), picWidth, picHeight);
		}

		ImageView imageView = new ImageView(context);

		// Set bitmap as view content.
		imageView.setImageBitmap(bitmap);
		// Set how the pictures will be scaled.
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		// Size of the pictures.
		imageView.setLayoutParams(new LayoutParams(picWidth, picHeight));

		return imageView;
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
