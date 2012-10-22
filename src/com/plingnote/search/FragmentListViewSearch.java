/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 Barnabas Sapan
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

package com.plingnote.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plingnote.R;
import com.plingnote.database.DatabaseHandler;
import com.plingnote.database.Note;
import com.plingnote.listview.FragmentListView;

/**
 * The search list view fragment. This fragment is responsible
 * of displaying the search results. Extends the
 * FragmentListView class, so it is based heavily on it.
 * 
 * @author Barnabas Sapan
 */
public class FragmentListViewSearch extends FragmentListView {
	private String searchQuery;
	
	/**
	 * Refresh the notes in the view. This is where
	 * the actually search query will be processed in
	 * the database.
	 */
	@Override
	public void refreshNotes(){
		clearNotes();
		
		for (Note n : DatabaseHandler.getInstance(getActivity()).search(this.searchQuery)) {
			addNote(n);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_listview_search, container, false);
	}
	
	/**
	 * Sets the search query
	 * @param searchQuery the string to search for in the database
	 */
	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}
}
