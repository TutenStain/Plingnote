package com.plingnote.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.plingnote.R;
import com.plingnote.database.DatabaseHandler;
import com.plingnote.main.ActivityMain;
import com.plingnote.utils.Utils;

/**
 * This test only works to a 100% if there are no notes applied to the application beforehand.
 * @author magnushuttu
 *
 */

public class TestGridviewFunctions extends
ActivityInstrumentationTestCase2<ActivityMain> {
	private Solo solo;
	private String editText;
	private int left;
	private int right;
	private int centerY;


	public TestGridviewFunctions() {
		super(ActivityMain.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		
	}

	//Tests if the correct tab is available for use
	public void test1GoToGridTab(){
		TestUtils.swipeToGrid(getActivity(), solo);
		solo.assertCurrentActivity("right activity", ActivityMain.class);
	}
	
	//Tests if you can add a new note, and if it's viewable in gridview
	public void test2AddNewNote(){
		TestUtils.swipeToGrid(getActivity(), solo);
		solo.clickOnView(solo.getView(R.id.add_new_note));
		solo.enterText(0, "Hello"+0);
		solo.sendKey(Solo.ENTER);
		solo.enterText(1, "yes, this is dog");
		solo.goBack();
		solo.clickOnView(solo.getView(R.id.add_new_note));
		solo.enterText(0, "Hello"+1);
		solo.sendKey(Solo.ENTER);
		solo.enterText(1, "yes, this is dog");
		solo.goBack();
		Assert.assertEquals(4, solo.getCurrentImageViews(solo.getView(R.id.grid)).size());
	}

	//Tests if you can delete a note in gridview
	public void test3DeleteNote(){
		left = Utils.getScreenPixels(getActivity()).left;
		right = Utils.getScreenPixels(getActivity()).right;
		TestUtils.swipeToGrid(getActivity(), solo);
		solo.clickLongOnScreen(left+100, 325);
		solo.clickOnScreen(right-1, Utils.getScreenPixels(getActivity()).top+50);
		solo.goBack();
		Assert.assertEquals(2, solo.getCurrentImageViews(solo.getView(R.id.grid)).size());
	}

	//Tests if you can edit a note through gridview by first creating
	//one and then editing it
	public void test4EditNote(){
		left = Utils.getScreenPixels(getActivity()).left;
		right = Utils.getScreenPixels(getActivity()).right;
		centerY = Utils.getScreenPixels(getActivity()).bottom/2;

		editText = "This text is edited!";
		TestUtils.swipeToGrid(getActivity(), solo);
		solo.clickOnScreen(left+100, 325);
		solo.clearEditText(1);
		solo.enterText(1, editText);
		solo.goBack();
		Assert.assertTrue(solo.searchText(editText));
		solo.goBack();
		solo.drag(left, right-1, centerY, centerY, 10);
		solo.drag(left, right-1, centerY, centerY, 10);
		TestUtils.swipeToGrid(getActivity(), solo);
	}
	
	public void test5EndIt(){
		DatabaseHandler.getInstance(getActivity()).deleteAllNotesInTestmode();
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
