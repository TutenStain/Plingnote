package com.plingnote.test;

import junit.framework.Assert;
import com.plingnote.R;
import android.content.res.Configuration;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import com.jayway.android.robotium.solo.Solo;
import com.plingnote.ActivityMain;
import com.plingnote.ActivityNote;

/**
 * Testing the activitynote from Activitymain
 * @author Julia Gustafsson
 *
 */
public class ActivityNoteTestFromMain extends ActivityInstrumentationTestCase2<ActivityMain>{
	private Solo solo;

	public ActivityNoteTestFromMain() {
		super(ActivityMain.class);
	}
	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	/**
	 * Test Activity note settings "Delete Note" the note and it's values should be deleted permanently
	 */
	public void testNoteSettingsDeleteNote(){ 
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityMain.class);
		//Open a new note
		solo.clickOnView(solo.getView(R.id.add_new_note));
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityNote.class);
		// Clear the editttext fields
		solo.clearEditText((EditText)solo.getView(R.id.notetitle));
		solo.clearEditText((EditText)solo.getView(R.id.notetext));
		String newNote = "This note should be deleted.";
		//Add text to the 'notetitle' and 'notetext' field.
		solo.enterText((EditText)solo.getView(R.id.notetitle), newNote);
		solo.enterText((EditText)solo.getView(R.id.notetext), newNote);
		//Click on settings
		solo.clickOnImage(2);
		solo.clickOnText("Delete this note");
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityMain.class);
		// Enter list view 
		int width = solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getWidth();
		int height = solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getHeight();
		if(solo.getCurrentActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			solo.clickOnImage(2);
		if(solo.getCurrentActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
			solo.clickOnScreen(width-1, height/8);
		//Make sure we can't find the note by searching after the text
		Assert.assertFalse(solo.searchText(newNote));
	}

	/**
	 * Test edit note title by entering ActivityNote by listview and check that it is saved 
	 * after going back to activity main and back to activtynote.
	 */
	public void testEditNoteTitle() {
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityMain.class);
		//Open a new note
		solo.clickOnView(solo.getView(R.id.add_new_note));
		// Check that we have the right activit
		solo.assertCurrentActivity("wrong activiy",ActivityNote.class);
		// Clear the editttext fields
		solo.clearEditText((EditText)solo.getView(R.id.notetitle));
		solo.clearEditText((EditText)solo.getView(R.id.notetext));
		String newNoteTitle = "This note is now containing new title.";	
		//Add text to the 'notetitle' field.
		solo.enterText((EditText)solo.getView(R.id.notetitle), newNoteTitle);
		//Go back to ActivityMain
		solo.goBack();
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityMain.class);
		// Enter list view 
		int width = solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getWidth();
		int height = solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getHeight();
		if(solo.getCurrentActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			solo.clickOnImage(2);
		if(solo.getCurrentActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
			solo.clickOnScreen(width-1, height/8);
		// Make sure we find the note by searching after the text
		Assert.assertTrue(solo.searchText(newNoteTitle));
		//Go back to ActivityMain
		solo.goBack();
	}

	/**
	 * Test Edit note text  by entering ActivityNote by listview and check that it is saved 
	 * after going back to activity main and back to activtynote.
	 */
	public void testEditNoteText() {
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityMain.class);
		//Open a new note
		solo.clickOnView(solo.getView(R.id.add_new_note));		
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityNote.class);
		// Clear the editttext fields
		solo.clearEditText((EditText)solo.getView(R.id.notetitle));
		solo.clearEditText((EditText)solo.getView(R.id.notetext));
		String newNoteText = "This note is now containing new text.";	
		//Add text to the 'notetext' field.
		solo.enterText((EditText)solo.getView(R.id.notetext), newNoteText);
		//Go back to ActivityMain
		solo.goBack();
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityMain.class);
		// Enter list view
				int width = solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getWidth();
				int height = solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getHeight();
				if(solo.getCurrentActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
					solo.clickOnImage(2);
				if(solo.getCurrentActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
					solo.clickOnScreen(width-1, height/8);
		// Make sure we find the note by searching after the text
		Assert.assertTrue(solo.searchText(newNoteText));
		//Go back to ActivityMain
		solo.goBack();
	}

	/**
	 * Testing the go back button(the application icon) i ActivityNote
	 */
	public void testGoBackButton(){
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityMain.class);
		//Open a new note
		solo.clickOnView(solo.getView(R.id.add_new_note));
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityNote.class);
		//The application image
		solo.clickOnImage(1);
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityMain.class);
	}

	/**
	 * Testing the add new note button works in ActivityMain by check if entering activitynote
	 */
	public void testAddNewNoteButton(){
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityMain.class);
		//Open a new note
		solo.clickOnView(solo.getView(R.id.add_new_note));
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityNote.class);
		//Go back to ActivityMain
		solo.goBack();
	}

	@Override
	public void tearDown() throws Exception {
		try {
			solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();
	}
}
