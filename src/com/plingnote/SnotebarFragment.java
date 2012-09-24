package com.plingnote;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A fragment class representing a "snotebar" a horizontal scroll bar with note settings.
 * @author Julia Gustafsson
 *
 */
public class SnotebarFragment extends Fragment {

	
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_snotebar, container, false);
	}
	  @Override
	    public void onStart() {
	        super.onStart();            
	  }
	               
}
	

