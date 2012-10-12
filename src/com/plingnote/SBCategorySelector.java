package com.plingnote;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
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
				imageView = new ImageView(getActivity());
			} else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(getActivity().getResources()
					.getIdentifier(getCategoryDrawables()[position],
							"drawable", "com.plingnote"));
			return imageView;
		}

		/**
		 * Get an array with file names of all category images in drawable
		 * folder.
		 * 
		 * @return the file names of all catergories in drawable folder.
		 */
		public String[] getCategoryDrawables() {
			Field[] fields = R.drawable.class.getFields();
			String[] categoryDrawables = new String[fields.length];

			// Place all category images in the array.
			for (int i = 0; i < categoryDrawables.length; i++) {

				// TODO compare names with category names in util.
				categoryDrawables[i] = fields[i].getName();
			}

			return categoryDrawables;
		}

	}

}
