package com.plingnote.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.plingnote.ActivityMain;
import com.plingnote.R;
import com.plingnote.Utils;

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
		super("com.plingnote", ActivityMain.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		
	}

	//Tests if the correct tab is available for use
	public void test1GoToGridTab(){
		int left = Utils.getScreenPixels(getActivity()).left;
		solo.drag(right-1, left+50 , centerY, centerY, 50);
		solo.drag(right-1, left+50 , centerY, centerY, 50);
	}
	
	//Tests if you can add a new note, and if it's viewable in gridview
	public void test2AddNewNote(){
		solo.clickOnImage(5);
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
		solo.clickOnImage(5);
		solo.clickLongOnScreen(left+100, 350);
		solo.clickOnImage(1);
		solo.clickOnView(solo.getView(R.id.add_new_note));
		solo.goBack();
		Assert.assertEquals(2, solo.getCurrentImageViews(solo.getView(R.id.grid)).size());
	}

	//Tests if you can edit a note through gridview by first creating
	//one and then editing it
	public void test4EditNote(){
		editText = "This text is edited";
		solo.clickOnImage(5);
		solo.clickOnScreen(left+100, 350);
		solo.clearEditText(1);
		solo.enterText(1, editText);
		solo.goBack();
		Assert.assertTrue(solo.searchText(editText));
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
