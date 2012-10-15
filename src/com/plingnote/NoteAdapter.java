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

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A configured adapter for viewing note objects in a list.
 * 
 * @author Linus Karlsson
 * 
 */
public class NoteAdapter extends ArrayAdapter<Note> {
	private List<Note> notes;
	private Context context;

	public NoteAdapter(Context context, int textViewResourceId, List<Note> notes) {
		super(context, textViewResourceId, notes);
		this.notes = notes;
		this.context = context;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		// If view is empty, render it.
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item, null);
		}

		/**
		 * Reference to the current Note object.
		 */
		Note n = notes.get(position);

		if (n != null) {
			// Create textviews in defined XML files.
			TextView title = (TextView) view.findViewById(R.id.title);
			TextView text = (TextView) view.findViewById(R.id.text);
			TextView date = (TextView) view.findViewById(R.id.date);
			LinearLayout iconField = (LinearLayout) view
					.findViewById(R.id.icons);

			// If the views exists, assign text to it.
			if (title != null) {
				title.setText(n.getTitle());
			}

			if (text != null) {
				text.setText(n.getText());
			}

			if (date != null) {
				String dateFormat = ListDateHandler.customDateFormat(n
						.getDate());
				date.setText(dateFormat);
			}

			if (iconField != null) {
				iconField.removeAllViews(); // Clear the view to erase previous
											// images
				addItemIcons(n, iconField);
			}
		}

		return view;
	}

	/**
	 * Add icons to the bottom right corner of the list item.
	 * 
	 * @param n
	 *            a note object
	 */
	public void addItemIcons(Note n, LinearLayout iconField) {

		// If the note has an image attached to it, add image icon
		if (!n.getImagePath().equals("")) {
			ImageView imageIcon = new ImageView(context);
			imageIcon.setImageResource(R.drawable.ic_image_icon);
			imageIcon.setLayoutParams(new ViewGroup.LayoutParams(28, 28));

			iconField.addView(imageIcon);
		}

		// If the note has an alarm set, add alarm icon
		if (!n.getAlarm().equals("")) {
			ImageView alarmIcon = new ImageView(context);
			alarmIcon.setImageResource(R.drawable.ic_alarm_icon);
			alarmIcon.setLayoutParams(new ViewGroup.LayoutParams(28, 28));

			iconField.addView(alarmIcon);
		}

	}

}
