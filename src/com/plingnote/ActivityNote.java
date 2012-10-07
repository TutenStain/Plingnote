package com.plingnote;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * A note fragment activity with a layout holding a fragment. 
 *
 * @author Julia Gustafsson
 *
 */
public class ActivityNote extends FragmentActivity {
	private int id = -1;
	private Location location= new Location(0.0,0.0);
	private String reminderString = "";
	private String imagePath = "";
	private FragmentSnotebar newFragment = null;
	private Fragment anotherFragment = null;
	
	/**
	 * Set content view and try to fetch id from saved instance or intent,
	 * Decide which fragment to be shown 'anotherFragment' or new fragment and if new fragment should be instanced and added again. 
	 * Fetching values from database.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		try{
			id =savedInstanceState.getInt(IntentExtra.id.toString());
		}
		catch(Exception eka)
		{
		}
		try {   
			anotherFragment = getSupportFragmentManager().getFragment(savedInstanceState,"anotherFragment");
		}catch(Exception e){
			if(newFragment ==null){
				try{
					if(this.id == -1){
					if(getIntent().getExtras().getInt(IntentExtra.id.toString()) != -1){ 
						id = getIntent().getExtras().getInt(IntentExtra.id.toString());
					}
					}
				}catch(Exception ek)
				{
				}
				FragmentSnotebar newFragment = new FragmentSnotebar();
				newFragment.setRetainInstance(true);
				if(this.id != -1){
					Bundle args = new Bundle();
					args.putInt(IntentExtra.id.toString(), this.id);
					newFragment.setArguments(args);
				}
				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.attach(newFragment);
				fragmentTransaction.add(R.id.fragmentcontainer, newFragment);
				fragmentTransaction.commit();
			}
		}
		
		//Fetching values from database
		try{
			reminderString = DatabaseHandler.getInstance(this).getNote(this.id).getAlarm();
		}catch(Exception e){	
		}
		try{
			imagePath = DatabaseHandler.getInstance(this).getNote(this.id).getImagePath();
		}catch(Exception e){	
		}
		try{
			location = DatabaseHandler.getInstance(this).getNote(this.id).getLocation();
		}catch(Exception e){	
		}
	}	   
	
	
	/**
	 *Set the view noteText text to the latest inserted note's text if is during editing.
	 *Sets the curser of the view noteText to the bottom of the text.
	 */
	@Override
	public void onStart(){
		super.onStart();
		EditText noteText = (EditText)findViewById(R.id.notetext);
		Rect rec = Utils.getScreenPixels(this);
		int height = rec.height();
		int widht = rec.width();	
		getResources().getConfiguration();
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			int h = height/10;
			noteText.setLayoutParams(new LinearLayout.LayoutParams(widht,h*4));
		}
		else{
			int h = height/15;
			noteText.setLayoutParams(new LinearLayout.LayoutParams(widht,h*9));
		}	
		//If this class was opened with an intent or saved instances, the note text will get the text from the database
		if(this.id != -1){
			String txt = (DatabaseHandler.getInstance(this).getNote(this.id).getTitle());
			txt = txt +(DatabaseHandler.getInstance(this).getNote(this.id).getText());
			//The cursor position will be saved even if turning the phone horizontal. Doesn't work with just setText or setSelection(noteText.getText().length()) if turning phone horizontal.
			noteText.setText(""); 
			noteText.append(txt);
			noteText.invalidate(); 
		}
	}
	
	/**
	 * Remove snotebarFragment and replace with the param fragment.
	 * @param fragment
	 */
	public void replaceFragment(Fragment fragment){
		Log.d("replace", this.id +"");
		saveToDatabase();
		anotherFragment= fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, anotherFragment).commit();
	}	
	
	/**
	 * Update the database with the pluginfragment values by checking what kind of plguin it is.
	 * Makes a new snotebar and set id as argument.
	 * Remove the pluginfragment and the new one.
	 * @param fragment
	 */
	public void replaceFragmentBack(PluginFragment fragment){
		if(fragment.getKind().equals(NoteExtra.REMINDER)){
			DatabaseHandler.getInstance(this).updateNote(this.id,this.getTitleofNoteText(), this.getTextofNoteText(),location,this.imagePath,fragment.getValue());			
			reminderString = fragment.getValue();
		}
		if(fragment.getKind().equals(NoteExtra.IMAGE)){
			DatabaseHandler.getInstance(this).updateNote(this.id,this.getTitleofNoteText(), this.getTextofNoteText(),location,fragment.getValue(),this.reminderString);			
			imagePath = fragment.getValue();
		}
		if(fragment.getKind().equals(NoteExtra.LOCATION)){
			DatabaseHandler.getInstance(this).updateNote(this.id,this.getTitleofNoteText(), this.getTextofNoteText(),fragment.getLocation(),this.imagePath,this.reminderString);		
			location = fragment.getLocation();
		}	
		saveToDatabase();
		newFragment = new FragmentSnotebar();
		try{
			Bundle bundleToFrag = new Bundle();
			bundleToFrag.putInt(IntentExtra.id.toString(), this.id);
			newFragment.setArguments(bundleToFrag);
		} catch(Exception e){		        	
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, newFragment).commit();
		getSupportFragmentManager().beginTransaction().remove(anotherFragment);
		anotherFragment = null;

	}	
	
	@Override
	public void onPause(){
		super.onPause();
		saveToDatabase();
	}
	
	public int getId() {
		return id;
	}
	
	/**
	 * Get the text from the view noteText. If the id isn't -1the note's value will be updated in the database.
	 * If not the note will be inserted in the database and the id will be saved. 
	 */
	public void saveToDatabase(){
		if(this.id != -1){
			DatabaseHandler.getInstance(this).updateNote(this.id,this.getTitleofNoteText(), this.getTextofNoteText(), this.location,this.imagePath,this.reminderString);
		}
		//If this class not was opened with an intent or saved instance, it'd id is set to -1 and we are inserting the note in database.
		else if(this.id == -1){
			DatabaseHandler.getInstance(this).insertNote(this.getTitleofNoteText(), this.getTextofNoteText(), this.location,this.imagePath,this.reminderString);
			id = DatabaseHandler.getInstance(this).getLastId();
		}
	}
	/**
	 * Find the first line of the text in notetext in the layout and returning it as a string
	 * @return String
	 */
	public String getTitleofNoteText(){
		EditText noteText = (EditText) findViewById(R.id.notetext);
		if(noteText.getText().toString().length()>0){
			String txt = noteText.getText().toString();
			String[] txtLines = txt.split("\n");
			return txtLines[0]+"\n";
		}else
			return "";
	}

	/**
	 * Find the text(without the title) in notetext in the layout  as a string
	 * @return String
	 */
	public String getTextofNoteText(){
		EditText noteText = (EditText) findViewById(R.id.notetext);
		String txt = noteText.getText().toString();
		if(txt.length()-this.getTitleofNoteText().length() >0){
			String text =txt.substring(this.getTitleofNoteText().length(), txt.length());
			return text;
		}else
			return "";
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt(IntentExtra.id.toString(), this.id);
		//If anotherFragment isn't null is should be saved
		if(anotherFragment != null){
			getSupportFragmentManager().putFragment(savedInstanceState, "anotherFragment", anotherFragment);
		}
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