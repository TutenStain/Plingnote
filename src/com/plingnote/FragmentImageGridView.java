package com.plingnote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class FragmentImageGridView extends Fragment implements AdapterView.OnItemClickListener {

	public FragmentImageGridView(){}
	
	private ImageAdapter imgA = new ImageAdapter(getActivity());

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_imageview, container, false);
		final GridView mGridView = (GridView) v.findViewById(R.id.gridview);
		return v;
	}
	
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        final Intent i = new Intent(getActivity(), ActivityNote.class);
        i.putExtra("resId", position);
        startActivity(i);
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
