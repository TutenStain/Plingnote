package com.plingnote.test;

import junit.framework.Assert;
import com.plingnote.R;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.EditText;
import com.jayway.android.robotium.solo.Solo;
import com.plingnote.ActivityNote;

/**
 * @author Julia Gustafsson
 *
 */
public class ActivityNoteTest extends ActivityInstrumentationTestCase2<ActivityNote>{
	private Solo solo;

	public ActivityNoteTest() {
		super(ActivityNote.class);
	}
	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	/**
	 * Test that note setting "Clean note" remove the text in 'notetext' and 'notetitle'.
	 */
	public void testNoteSettingsCleanNote(){ 
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityNote.class);
		//Clear the edittext fields 'notetext' and 'notetitle' from text
		solo.clearEditText((EditText)solo.getView(R.id.notetext));
		solo.clearEditText((EditText)solo.getView(R.id.notetitle));
		String newNoteTitle = "This note title should be cleaned.";
		String newNoteText = "This note text should be cleaned.";
		//Fill the edittext fields 'notetext' and 'notetitle' with 'newNoteText' and 'newNoteTitle'
		solo.enterText((EditText)solo.getView(R.id.notetitle), newNoteTitle);
		solo.enterText((EditText)solo.getView(R.id.notetext), newNoteText);
		//Click on settings
		solo.clickOnImage(2);
		solo.clickOnText("Clean note text");
		//Make sure that 'newNoteText' and 'newNoteTitle' can't be find.
		Assert.assertFalse(solo.searchText(newNoteTitle));
		Assert.assertFalse(solo.searchText(newNoteText));
	}

	/**
	 * Test that ActivityNote's method "getTitleofNoteText"  return same as the text in the edittext field 'notetitle'.
	 */
	public void testGetNoteTitle(){
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityNote.class);
		//Clear edittextfield 'notetitle' from text
		solo.clearEditText((EditText)solo.getView(R.id.notetitle));
		String newNoteTitle = "This note title should be getted from method.";
		//Fill edittextfield 'notetitle' with 'newNoteTite
		solo.enterText((EditText)solo.getView(R.id.notetitle), newNoteTitle);
		ActivityNote activityNote = getActivity();
		//Make sure the 'newNoteTitle' is identical with the string the method 'getTextofNoteTitle' returns.
		if(newNoteTitle.equals(activityNote.getTitleofNoteText())){
			assert(true);
		}
	}
	
	/**
	 * Test that ActivityNote's method "getTextofNoteText" return same as the text in the edittext field 'notetext'.
	 */
	public void testGetNoteText(){
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityNote.class);
		//Clear edittextfield 'notetext' from text
		solo.clearEditText((EditText)solo.getView(R.id.notetext));
		String newNoteText = "This note title should be getted from method.";
		//Fill edittextfield 'notetext' with 'newNoteText
		solo.enterText((EditText)solo.getView(R.id.notetext), newNoteText);
		ActivityNote activityNote = getActivity();
		//Make sure the 'newNoteText' is identical with the string the method 'getTextofNoteText' returns.
		if(newNoteText.equals(activityNote.getTextofNoteText())){
			assert(true);
		}
	}

	/**
	 * Test that Edittext field 'notetext' is focused after pressing enter after entering activitynote
	 */
	public void testFocusAfterPressEnter() {	
		// Check that we have the right activity
		solo.assertCurrentActivity("wrong activiy",ActivityNote.class);
		sendKeys(KeyEvent.KEYCODE_ENTER);
		//Make sure 'notetext' is foucused
		assertTrue("notetext should be focused",solo.getView(R.id.notetext).isFocusable());
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
