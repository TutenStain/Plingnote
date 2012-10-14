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

import java.lang.reflect.Field;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Class displaying category icons in a fragment. Placed in SNotebar.
 * 
 * @author Linus Karlsson
 * 
 */
public class SBCategorySelector extends Fragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Create GridView
		GridView categoryGrid = (GridView) getActivity().findViewById(
				R.id.gridview);

		// Set adapter
		categoryGrid.setAdapter(new CategoryAdapter());
		categoryGrid.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String clickedCategory = getCategoryDrawables()[position];
				System.out.println(clickedCategory);
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
	public String[] getCategoryDrawables() {
		Field[] fields = R.drawable.class.getFields();
		String[] categoryDrawables = new String[fields.length];

		// Place all category images in the array.
		for (int i = 0; i < categoryDrawables.length; i++) {

			// Iterate through NoteCategories and get the drawables that matches
			// the category names.
			for (NoteCategory category : NoteCategory.values()) {
				if (fields[i].getName().equals(category.toString())) {
					categoryDrawables[i] = fields[i].getName();
				}
			}
		}

		return categoryDrawables;
	}

	/**
	 * 
	 * @author Linus Karlsson
	 * 
	 */
	private class CategoryAdapter extends BaseAdapter {

		public int getCount() {
			return getCategoryDrawables().length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;

			if (convertView == null) {
				float size = TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_DIP, 60, Resources.getSystem()
								.getDisplayMetrics());
				imageView = new ImageView(getActivity());
				imageView.setLayoutParams(new GridView.LayoutParams((int) size,
						(int) size));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			} else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(getActivity().getResources()
					.getIdentifier(getCategoryDrawables()[position],
							"drawable", "com.plingnote"));
			return imageView;
		}

	}

}
