package com.plingnote;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * A note fragment activity with a layout holding a fragment. 
 *
 * @author Julia Gustafsson
 *
 */
public class ActivityNote extends FragmentActivity {

	/**
	 * Set the layout of the activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
	}
	
	@Override
	protected void onStart(){
		super.onStart();
	}

	@Override
	protected void onRestart(){
		super.onRestart();
	}

	@Override
	protected void onResume(){
		super.onResume();
	}


	@Override
	public void onPause(){
		super.onPause();

	}

	@Override
	public void onStop(){
		super.onStop();

	}

	@Override
	public void onDestroy(){
		super.onDestroy();

	} 
	
	/**
	 * Makes the back button behave like the home button. Calling finsih() if back button is pressed.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        finish();
	        Toast.makeText(this,""+ DatabaseHandler.getInstance(this.getBaseContext()).getNoteList().get(DatabaseHandler.getInstance(this).getNoteList().size()-1), Toast.LENGTH_LONG).show();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}

