package com.plingnote;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
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
	private int id = -1;
	private FragmentNoteText noteFragment;
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
		FrameLayout fragmentContainer = new FrameLayout(this);
		 noteFragment = new FragmentNoteText();
		fragmentContainer.setId(R.id.fragmentContainer);
		setContentView(fragmentContainer, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		try{
			if(getIntent().getExtras().getInt(IntentExtra.id.toString()) != -1){ //Maybe not necessary
				noteFragment.setArguments(getIntent().getExtras());
			}
		} catch(Exception e){	
			try{
				if(savedInstanceState.getInt(IntentExtra.id.toString()) != -1){
					Bundle args = new Bundle();
					args.putInt(IntentExtra.id.toString(), this.id);
					noteFragment.setArguments(args);
					this.id = getIntent().getExtras().getInt(IntentExtra.id.toString());
				}
			}catch(Exception o)
			{}	
		}	getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, noteFragment).commit(); 
	}  

	
	
	@Override
	public void onPause(){
		super.onPause();
		getSupportFragmentManager().beginTransaction().remove(noteFragment).commit(); 
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt(IntentExtra.id.toString(), id);
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
	
	/**
	 * If choosing to go back to home, the keyboard will be hided and call finish.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}