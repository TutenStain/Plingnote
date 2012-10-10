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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * A note fragment activity with a layout holding a fragment. 
 * @author Julia Gustafsson
 *
 */
public class ActivityNote extends FragmentActivity {
	private int id = -1;
	private Location location= new Location(0.0,0.0);
	private String reminderString = "";
	private String imagePath = "";
	private FragmentSnotebar snotebarFragment = null;
	private Fragment anotherFragment = null;
	private boolean deleteNote = false;

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
			if(snotebarFragment ==null){
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
		EditText noteTitle = (EditText)findViewById(R.id.notetitle);
		Rect rec = Utils.getScreenPixels(this);
		int height = rec.height();
		int widht = rec.width();	
		getResources().getConfiguration();
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			int h = height;
			noteTitle.setLayoutParams(new LinearLayout.LayoutParams(widht,h/7));
		}
		else{
			int h = height;
			noteTitle.setLayoutParams(new LinearLayout.LayoutParams(widht,h/11));
		}	
		
		noteTitle.setOnKeyListener(new View.OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        switch(keyCode) {
		            case KeyEvent.KEYCODE_ENTER:		    
		            	 EditText noteText = (EditText)findViewById(R.id.notetext);
		            	 noteText.setFocusable(true);
		            	 noteText.requestFocus();
		            	 return true;
		                
		            default:
		                break;
		        }
				return false;
		    }
		});
		EditText noteText = (EditText)findViewById(R.id.notetext);
		 rec = Utils.getScreenPixels(this);
		 height = rec.height();
		 widht = rec.width();	
		getResources().getConfiguration();
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			int h = height/3;
			noteText.setLayoutParams(new LinearLayout.LayoutParams(widht,h));
		}
		else{
			int h = height/2;
			noteText.setLayoutParams(new LinearLayout.LayoutParams(widht,h));
		}	
		//If this class was opened with an intent or saved instances, the note text will get the text from the database
		if(this.id != -1){
			String title = (DatabaseHandler.getInstance(this).getNote(this.id).getTitle());
			noteTitle.setText(title);
			
			String txt = (DatabaseHandler.getInstance(this).getNote(this.id).getText());
			//The cursor position will be saved even if turning the phone horizontal. Doesn't work with just setText or setSelection(noteText.getText().length()) if turning phone horizontal.
			noteText.setText(""); 
			noteText.append(txt);
			noteTitle.invalidate(); 
			noteText.invalidate(); 
		}
	}

	/**
	 * Remove snotebarFragment and replace with the param fragment.
	 * @param fragment
	 */
	public void replaceFragment(Fragment fragment){
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
		if(fragment.getValue() != null){
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
		}
		changeToSnotebarFragment();

	}	
	/**
	 * if the boolean deletenote is false the information will be saved to the database.
	 */
	@Override
	public void onPause(){
		super.onPause();
		if(deleteNote == false || !isNoteEmpty())
			saveToDatabase();
	}
	/**
	 * A function that checks if all value; text,title,reminder,location,imagepath, is not setted.
	 * @return
	 */
	public boolean isNoteEmpty(){
		if(this.imagePath.equals("") && this.reminderString.equals("") && this.location == null && this.getTextofNoteText().equals("") && this.getTitleofNoteText().equals(""))
			return true;
		else 
			return false;
	}
	/**
	 * Return the id 
	 * @return id
	 */
	public int getId() {
		return id;
	}
	public boolean checkIfValueIsSetted(NoteExtra noteExtra){
		if(this.id != -1){
			if(noteExtra.equals(NoteExtra.REMINDER)){
				if(!(this.reminderString.equals(""))){
					return true;
				}
			}
			if(noteExtra.equals(NoteExtra.IMAGE)){
				if(!(this.imagePath.equals(""))){
					return true;
				}
			}
			if(noteExtra.equals(NoteExtra.LOCATION)){
				if(!(this.location == null)){
					return true;
				}
			}	
		}
		return false;
	}

	/**
	 * Delete value from database
	 * @param noteExtra
	 */
	public void deleteValue(NoteExtra noteExtra){
		if(noteExtra.equals(NoteExtra.REMINDER)){
			DatabaseHandler.getInstance(this).updateNote(this.id,this.getTitleofNoteText(), this.getTextofNoteText(),location,this.imagePath,"");			
			reminderString = "";
		}
		if(noteExtra.equals(NoteExtra.IMAGE)){
			DatabaseHandler.getInstance(this).updateNote(this.id,this.getTitleofNoteText(), this.getTextofNoteText(),location,"",this.reminderString);			
			imagePath = "";
		}
		if(noteExtra.equals(NoteExtra.LOCATION)){
			DatabaseHandler.getInstance(this).updateNote(this.id,this.getTitleofNoteText(), this.getTextofNoteText(),null,this.imagePath,this.reminderString);		
			location = null;
		}	
		changeToSnotebarFragment();
	}
	/**
	 * Save to database, remove anotherFragment and replace it with snotbarFragment
	 */
	private void changeToSnotebarFragment(){
		saveToDatabase();
		snotebarFragment = new FragmentSnotebar();
		try{
			Bundle bundleToFrag = new Bundle();
			bundleToFrag.putInt(IntentExtra.id.toString(), this.id);
			snotebarFragment.setArguments(bundleToFrag);
		} catch(Exception e){		        	
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, snotebarFragment).commit();
		getSupportFragmentManager().beginTransaction().remove(anotherFragment);
		anotherFragment = null;
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
		EditText noteText = (EditText) findViewById(R.id.notetitle);
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

	/**
	 * If the boolean 'deletenote' is false,thismethod will save the id and 'anotherFragment' if it isn't null.
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		if(deleteNote == false){
			super.onSaveInstanceState(savedInstanceState);
			savedInstanceState.putInt(IntentExtra.id.toString(), this.id);
			//If anotherFragment isn't null is should be saved
			if(anotherFragment != null){
				getSupportFragmentManager().putFragment(savedInstanceState, "anotherFragment", anotherFragment);
			}
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
	 * If choosing to delete note, the current note will be deleted and the note activity will be deleted
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {	
		case android.R.id.home:
			InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
			finish();
			return true;
		case R.id.delete_note:
			if(this.id!=-1)
				DatabaseHandler.getInstance(this).deleteNote(this.id);
			deleteNote = true;
			this.finish();
			return true;
		case R.id.reset_snotebar:
			deleteAllValues();
			return true;
		case R.id.clean_notetext:
			deleteNoteText();
			return true;
		case R.id.clean_note:
			deleteNoteText();
			deleteAllValues();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_note, menu);
		return true;
	}

	/**
	 * Delete the note's text and title
	 */
	public void deleteNoteText(){
		EditText noteText = (EditText)findViewById(R.id.notetext);
		noteText.setText(""); 
		noteText.invalidate(); 
		saveToDatabase();
	}

	/**
	 * Delete all note extra values location,imagepath,reminder.
	 */
	public void deleteAllValues(){		
		reminderString = "";		
		imagePath = "";	
		location = null;
		DatabaseHandler.getInstance(this).updateNote(this.id,this.getTitleofNoteText(), this.getTextofNoteText(),location,this.imagePath,this.reminderString);			
		snotebarFragment = new FragmentSnotebar();
		try{
			Bundle bundleToFrag = new Bundle();
			bundleToFrag.putInt(IntentExtra.id.toString(), this.id);
			snotebarFragment.setArguments(bundleToFrag);
		} catch(Exception e){		        	
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, snotebarFragment).commit();
	}
}