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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;

/**
 * Fragment showing pictures in a horizontal scrollable gallery.
 * 
 * @author Linus Karlsson
 * 
 */
public class SBImageSelector extends Fragment {

	private Cursor cursor;

	private int column;

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

		Gallery gallery = (Gallery) getView().findViewById(
				R.id.snotebar_image_browser);
		gallery.setAdapter(new SBImageAdapter(getActivity(), cursor, column));

		// Initialize the column index
		column = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);

		// Get user click
		gallery.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				// Intent snoteBar = new Intent(getActivity(), FragmentSnoteBar.class);
				// snoteBar.putExtra("MEDDELANDE", getSelectedImagePath(position));
			}

		});
	}

	/**
	 * Get cursor pointing to SD Card
	 * 
	 * @return point to SD Card
	 */
	public Cursor getSDCursor() {
		String[] projection = { MediaStore.Images.Thumbnails._ID };

		return getActivity().getContentResolver().query(
				MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection,
				null, null, MediaStore.Images.Thumbnails.IMAGE_ID);
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
		cursor = getActivity().getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
				null, null);
		column = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToPosition(position);

		return cursor.getString(column);
	}
}
