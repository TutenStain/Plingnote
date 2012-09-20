package com.plingnote;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Class displaying the saved notes as a list. The user can click on a note to
 * view or edit it.
 * 
 * @author Linus Karlsson
 * 
 */
public class FragmentListView extends ListFragment {
	String[] groupies = { "Bar n' a bass", "Bolle", "Cristmas I A", "*PI",
			"Bolle" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_listview, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, groupies));
	}
}