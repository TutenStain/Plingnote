package com.plingnote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * A note text fragment 
 * @author Julia Gustafsson
 *
 */
public class FragmentNoteText extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_notetext, container, false);
	
	}

	@Override
	public void onStart() {
		super.onStart();  
	}

	@Override
	public void onPause (){
		super.onPause();
	}

}