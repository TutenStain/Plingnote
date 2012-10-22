package com.plingnote.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.plingnote.ActivityMain;
import com.plingnote.DatabaseHandler;
import com.plingnote.R;
import com.plingnote.Utils;

/**
 * Class testing the behavior of the contextual action bar. The action bar has a
 * text saying "Select notes", so the test is searching for such strings on the
 * display.
 * 
 * @author Linus Karlsson
 * 
 */
public class TestContextualActionBar extends
ActivityInstrumentationTestCase2<ActivityMain> {
	private Solo solo;

	public TestContextualActionBar() {
		super("com.plingnote", ActivityMain.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void test1DismissBar() {
		// Enter list view
		sweepToList();

		// Add note to list view
		for(int i = 0; i < 2; i++){
			solo.clickOnView(solo.getView(R.id.add_new_note));
			solo.enterText(0, "Hello"+i);
			solo.sendKey(Solo.ENTER);
			solo.enterText(1, "yes, this is dog");
			solo.goBack();
		}

		// Long click on an item to bring up the contextual action bar
		solo.clickLongInList(1);

		Assert.assertTrue(solo.searchText("Select notes"));

		// Close the contextual bar manually
		solo.clickOnImage(0);

		Assert.assertFalse(solo.searchText("Select notes"));

	}

	public void test2NoSelectedNotes() {
		// Enter list view
		sweepToList();

		// Long click on an item to bring up the contextual action bar
		solo.clickLongInList(1);

		Assert.assertTrue(solo.searchText("Select notes"));

		// Re-click on same item
		solo.clickInList(1);

		Assert.assertFalse(solo.searchText("Select notes"));
	}

	public void test3DeleteNotes() {
		// Enter list view
		sweepToList();

		// Long click on an item to bring up the contextual action bar
		solo.clickLongInList(1);

		Assert.assertTrue(solo.searchText("Select notes"));

		// Click on remove button
		solo.clickOnScreen(Utils.getScreenPixels(getActivity()).right-1,
				Utils.getScreenPixels(getActivity()).top+50);

		Assert.assertFalse(solo.searchText("Select notes"));
	}

	public void test4ChangeTab() {
		// Enter list view
		sweepToList();

		// Long click on an item to bring up the contextual action bar
		solo.clickLongInList(1);

		Assert.assertTrue(solo.searchText("Select notes"));

		// Enter map view
		solo.drag(Utils.getScreenPixels(getActivity()).left, Utils
				.getScreenPixels(getActivity()).right - 50, Utils
				.getScreenPixels(getActivity()).exactCenterY(), Utils
				.getScreenPixels(getActivity()).exactCenterY(), 10);

		Assert.assertFalse(solo.searchText("Select notes"));
		DatabaseHandler.getInstance(getActivity()).deleteAllNotesInTestmode();
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void sweepToList() {
		solo.drag(Utils.getScreenPixels(getActivity()).width() - 1, Utils
				.getScreenPixels(getActivity()).left + 50, Utils
				.getScreenPixels(getActivity()).exactCenterY(), Utils
				.getScreenPixels(getActivity()).exactCenterY(), 10);
	}

}
