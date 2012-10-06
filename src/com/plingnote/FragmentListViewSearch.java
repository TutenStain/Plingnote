package com.plingnote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * The search list view fragment. This fragment is responsible
 * of displaying the search results. Extends the
 * FragmentListView class, so it is based heavily on it.
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
