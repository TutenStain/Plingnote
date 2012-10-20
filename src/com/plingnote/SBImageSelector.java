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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * Fragment showing pictures in a gridview
 * 
 * @author First version: Linus Karlsson; Second version with asynchronous loading: Barnabas Sapan
 * 
 */

public class SBImageSelector extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
	@SuppressWarnings("unused")
	private String selectedImage;
	private SimpleCursorAdapter adapter;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_snotebar_image_selector, container, false); 
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


		GridView gridView = (GridView) getActivity().findViewById(R.id.gridview);


		adapter = new SimpleCursorAdapter(
				getActivity().getApplicationContext(), R.layout.fragment_snotebar_grid_image,
				null, new String[] { MediaStore.Images.Thumbnails.DATA }, new int[] { R.id.img }, 0);

		gridView.setAdapter(adapter);


		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Cursor cursor = ((SimpleCursorAdapter) adapter).getCursor();
				cursor.moveToPosition(position);
				final int column = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				selectedImage = cursor.getString(column);
				
				//replaceBackFragment();
			}

		});
		getLoaderManager().initLoader(0, null, this);


	}

	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;


		return new CursorLoader(getActivity(), uri, null, null, null, null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}


	/**
	 * Return the imagepath
	 */
	public String getValue() {
		return selectedImage;
	}

	/**
	 * The kind of this fragment
	 */
	public NoteExtra getKind() {
		return NoteExtra.IMAGE;
	}

	/**
	 * Replace this fragment
	 */
	public void replaceBackFragment() {
		ActivityNote activityNote = (ActivityNote)getActivity();
		//activityNote.replaceFragmentBack(this);
	}

	/**
	 * This class has not any category
	 */
	public NoteCategory getCategory() {
		return null;
	}


}

