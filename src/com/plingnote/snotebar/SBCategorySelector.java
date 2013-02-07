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

package com.plingnote.snotebar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.plingnote.R;
import com.plingnote.main.ActivityNote;
import com.plingnote.utils.NoteExtra;

/**
 * Class displaying category icons in a fragment. Placed in SNotebar.
 * 
 * @author Linus Karlsson
 * @ModifiedBy Barnabas Sapan
 * 
 */
public class SBCategorySelector extends Fragment implements PluginableFragment {

	private NoteCategory noteCategory;
	GridView categoryGrid;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Create GridView
		categoryGrid = (GridView) getActivity().findViewById(
				R.id.gridview);


		// Set adapter
		categoryGrid.setAdapter(new CategoryAdapter());
		categoryGrid.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String clickedCategory = getCategoryDrawables().get(position);
				clickedCategory = clickedCategory.substring(0, 1).toUpperCase()
						+ clickedCategory.substring(1);
				noteCategory = NoteCategory.valueOf(clickedCategory);
				replaceBackFragment();
			}
		});



	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.category_selector, container, false);
	}

	/**
	 * Get an array with file names of all category images in drawable folder.
	 * 
	 * @return the file names of all catergories in drawable folder.
	 */
	public List<String> getCategoryDrawables() {
		Field[] fields = R.drawable.class.getFields();
		List<String> categoryDrawables = new ArrayList<String>();

		// Place all category images in the array.
		for (int i = 0; i < fields.length; i++) {

			// Iterate through NoteCategories and get the drawables that matches
			// the category names.
			for (NoteCategory category : NoteCategory.values()) {
				if (fields[i].getName().equals(category.toString()))
					categoryDrawables.add(fields[i].getName());
			}
		}

		return categoryDrawables;
	}

	/**
	 * Private class responsible for adding drawables into Image Views.
	 * 
	 * @author Linus Karlsson
	 * 
	 */
	private class CategoryAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return getCategoryDrawables().size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;

			if (convertView == null) {
				// Get screen size
				Display display = getActivity().getWindowManager()
						.getDefaultDisplay();

				// Set size to width of display.
				int size = display.getWidth() / (categoryGrid.getNumColumns() + 3); //7
				imageView = new ImageView(getActivity());
				imageView.setLayoutParams(new GridView.LayoutParams((int) size, (int) size));
				imageView.setScaleType(ImageView.ScaleType.FIT_XY); //CENTER_CsROP
				
				// Set image view to bitmap at current position.
				imageView.setImageResource(getActivity().getResources()
						.getIdentifier(getCategoryDrawables().get(position),
								"drawable", "com.plingnote"));
			} else {
				imageView = (ImageView) convertView;
			}

			return imageView;
		}
	}

	/**
	 * This class has not any string value
	 */
	@Override
	public String getValue() {
		return "";
	}

	@Override
	public NoteExtra getKind() {
		return NoteExtra.CATEGORY;
	}

	@Override
	public void replaceBackFragment() {
		ActivityNote activityNote = (ActivityNote) getActivity();
		activityNote.replaceFragmentBack(this);
	}

	/**
	 * Return the category
	 */
	@Override
	public NoteCategory getCategory() {
		return this.noteCategory;
	}

	/**
	 * This class has not any requestcode
	 */
	@Override
	public int getRequestCode() {
		return -1;
	}
}
