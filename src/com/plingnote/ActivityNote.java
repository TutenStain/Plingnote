package com.plingnote;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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

}

