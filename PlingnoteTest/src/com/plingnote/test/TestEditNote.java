package com.plingnote.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.plingnote.R;
import com.plingnote.database.DatabaseHandler;
import com.plingnote.main.ActivityMain;
import com.plingnote.utils.Utils;

/**
 * Testing the connection between the edit and list view. When a note is edited,
 * the list should be updated. Pressing the edited note again should result in
 * the new text being showed in the edit view.
 * 
 * @author Linus Karlsson
 * 
 */
public class TestEditNote extends
		ActivityInstrumentationTestCase2<ActivityMain> {
	private Solo solo;

	public TestEditNote() {
		super("com.plingnote", ActivityMain.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testEditNote() {

		// Enter list view and select a note
		sweepToList();
		
		// Add note to list view
		for(int i = 0; i < 2; i++){
			solo.clickOnView(solo.getView(R.id.add_new_note));
			solo.enterText(0, "Hello"+i);
			solo.sendKey(Solo.ENTER);
			solo.enterText(1, "yes, this is dog");
			solo.goBack();
		}
		
		solo.clickInList(1);

		// Edit the text and leave the edit view
		solo.clearEditText(0);
		String newNote = "This note is now containing new text.";
		solo.enterText(0, newNote);
		solo.goBack();
		Assert.assertTrue(solo.searchText(newNote));

		// Click on the same item again and make sure it's the same
		solo.clickInList(1);
		Assert.assertTrue(solo.searchText(newNote));
		
		DatabaseHandler.getInstance(getActivity()).deleteAllNotesInTestmode();
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void sweepToList() {
		solo.drag(Utils.getScreenPixels(getActivity()).width() - 1, Utils
				.getScreenPixels(getActivity()).left + 10, Utils
				.getScreenPixels(getActivity()).exactCenterY(), Utils
				.getScreenPixels(getActivity()).exactCenterY(), 10);
	}

}
