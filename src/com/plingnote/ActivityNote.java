package com.plingnote;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * A note fragment activity with a layout holding a fragment. 
 *
 * @author Julia Gustafsson
 *
 */
public class ActivityNote extends FragmentActivity {
	private static final int CONTENT_VIEW_ID = 101011;
	/**
	 * Makes a new framelayout and set the framelayout id. Set activity's layout. If the saved instance is null, the class makes a new Fragmentnotetext.
	 *  If an intent have put extras, the fragment gets those as arguments.
	 *  The fragment will be added to the framelayout.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout frame = new FrameLayout(this);
		frame.setId(CONTENT_VIEW_ID);
		setContentView(frame, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		if(savedInstanceState == null){
			FragmentNoteText newFragment = new FragmentNoteText();
			try{
				if(getIntent().getExtras().getInt("rowId") != -1){ //Maybe not necessary
					newFragment.setArguments(getIntent().getExtras());
				}
			} catch(Exception e){		        	
			}
			getSupportFragmentManager().beginTransaction().add(CONTENT_VIEW_ID, newFragment).commit(); 
		}   
	}

	
	@Override
	public void onPause(){
		super.onPause();
	}

	/**
	 * Makes the back button behave like the home button. Calling finish() if back button is pressed.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}

