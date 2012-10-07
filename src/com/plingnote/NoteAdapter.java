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
import android.widget.TextView;

/**
 * A configured adapter for viewing note objects in a list.
 * @author Linus Karlsson
 *
 */
public class NoteAdapter extends ArrayAdapter<Note> {
	private List<Note> notes;

	public NoteAdapter(Context context, int textViewResourceId, List<Note> notes) {
		super(context, textViewResourceId, notes);
		this.notes = notes;
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

			// If the views exists, assign text to it.
			if (title != null) {
				title.setText(n.getTitle());
			}

			if (text != null) {
				text.setText(n.getText());
			}
		}

		return view;
	}

}
