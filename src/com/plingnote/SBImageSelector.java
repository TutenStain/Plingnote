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

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;

/**
 * Fragment showing pictures in a horizontal scrollable gallery.
 * 
 * @author Linus Karlsson
 * 
 */
public class SBImageSelector extends Fragment implements PluginFragment{
	private String selectedImage;

	private Cursor cursor;

	/**
	 * The width of the gallery image
	 */
	public static final int IMAGE_WIDTH = 120;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.snotebar_image_browser, container,
				false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);



		// Set cursor pointing to SD Card
		cursor = getSDCursor();


		// Initialize the column index
		int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);

		// Create array of bitmaps with the siza of cursor
		Bitmap[] images = new Bitmap[cursor.getCount()];

		// Add images to array.
		for (int i = 0; i < this.cursor.getCount(); i++) {

			cursor.moveToPosition(i);
			images[i] = MediaStore.Images.Thumbnails.getThumbnail(getActivity()
					.getContentResolver(), cursor.getInt(columnIndex),
					MediaStore.Images.Thumbnails.MICRO_KIND, null);

		}

		// Create gallery using ImageAdapter.
		Gallery gallery = (Gallery) getView().findViewById(
				R.id.snotebar_image_browser);
		gallery.setAdapter(new SBImageAdapter(getActivity(), cursor, images));

		// Place first gallery image at far left
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);

		MarginLayoutParams mlp = (MarginLayoutParams) gallery.getLayoutParams();
		mlp.setMargins(-(metrics.widthPixels / 2 + IMAGE_WIDTH), mlp.topMargin,
				mlp.rightMargin, mlp.bottomMargin);

		// Get user click
		gallery.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {



				// The path to the selected image
				selectedImage = 
						getSelectedImagePath(position);
				replaceBackFragment();
			

				// Intent snoteBar = new Intent(getActivity(),
				// FragmentSnoteBar.class);
				// snoteBar.putExtra("MEDDELANDE",
				// getSelectedImagePath(position));

			}

		});
	}

	/**
	 * Get cursor pointing to SD Card
	 * 
	 * @return point to SD Card
	 */
	public Cursor getSDCursor() {
		String[] projection = { MediaStore.Images.Media._ID };

		return getActivity().getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
				null, MediaStore.Images.Media._ID);
	}

	/**
	 * The path to the selected image in the browser.
	 * 
	 * @param position
	 *            the selected image from image browser
	 * @return the path to the selected image
	 */
	public String getSelectedImagePath(int position) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor anotherCursor = getActivity().getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
				null, null);
		final int column = anotherCursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		anotherCursor.moveToPosition(position);

		return anotherCursor.getString(column);
	}

	public String getValue() {
		return selectedImage;
	}

	public Location getLocation() {
		return null;
	}

	public NoteExtra getKind() {
		return NoteExtra.IMAGE;
	}

	public void replaceBackFragment() {
		ActivityNote activityNote = (ActivityNote)getActivity();
		activityNote.replaceFragmentBack(this);
	}


}
