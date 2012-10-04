package com.plingnote;

public class FragmentListViewSearch extends FragmentListView {
	private String searchQuery;
	
	@Override
	public void refreshNotes(){
		clearNotes();
		
		for (Note n : DatabaseHandler.getInstance(getActivity()).search(this.searchQuery)) {
			addNote(n);
		}
	}
	
	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}
}
