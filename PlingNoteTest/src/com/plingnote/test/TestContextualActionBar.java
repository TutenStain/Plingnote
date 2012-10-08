package com.plingnote.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import com.plingnote.ActivityMain;

/**
 * Class testing the behavior of the contextual action bar. The action bar has a
 * text saying "Select notes", so the test is searching for such strings on the display.
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
		solo.clickOnImage(4);

		// Long click on an item to bring up the contextual action bar
		solo.clickLongInList(1);

		// Close the contextual bar manually
		solo.clickOnImage(0);

		Assert.assertFalse(solo.searchText("Select notes"));

	}

	public void test2NoSelectedNotes() {
		// Enter list view
		solo.clickOnImage(4);

		// Long click on an item to bring up the contextual action bar
		solo.clickLongInList(1);

		// Re-click on same item
		solo.clickInList(1);

		Assert.assertFalse(solo.searchText("Select notes"));
	}

	public void test3DeleteNotes() {
		// Enter list view
		solo.clickOnImage(4);

		// Long click on an item to bring up the contextual action bar
		solo.clickLongInList(1);

		// Click on remove button
		solo.clickOnImage(1);

		Assert.assertFalse(solo.searchText("Select notes"));
	}

	public void test4ChangeTab() {
		// Enter list view
		solo.clickOnImage(4);

		// Long click on an item to bring up the contextual action bar
		solo.clickLongInList(1);

		// Enter map view
		solo.clickOnImage(2);

		Assert.assertFalse(solo.searchText("Select notes"));
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
