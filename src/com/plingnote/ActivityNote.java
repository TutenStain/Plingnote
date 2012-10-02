package com.plingnote;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
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
	FragmentNoteText noteFragment;
	/**
	 * Makes a new framelayout and set the framelayout id. Set activity's layout. If the saved instance is null, the class makes a new Fragmentnotetext.
	 *  If an intent have put extras, the fragment gets those as arguments.
	 *  The fragment will be added to the framelayout.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		   ActionBar actionBar = getActionBar();
		    actionBar.setDisplayHomeAsUpEnabled(true);
		FrameLayout frame = new FrameLayout(this);
		frame.setId(CONTENT_VIEW_ID);
		setContentView(frame, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		if(savedInstanceState == null){
			FragmentNoteText newFragment = new FragmentNoteText();
			try{
				if(getIntent().getExtras().getInt(Utils.QUERY_NOTE) != -1){ //Maybe not necessary
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

	    return true;
	}
	/**
	 * If choosing to go back to home, the keyboard will be hided and ActivityMain will be started.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
	            /*Intent intent = new Intent(this, ActivityMain.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);*/
				finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}

