package com.plingnote.test;

import junit.framework.Assert;

import com.plingnote.database.DatabaseHandler;
import com.plingnote.main.ActivityMain;
import com.plingnote.utils.Utils;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

public class TestMapFunctions extends
ActivityInstrumentationTestCase2<ActivityMain> {
	private Solo solo;

	public TestMapFunctions() {
		super(ActivityMain.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	//Tests if the map will start
	public void test1MapViewAtStartUp(){
		solo.waitForView(null, ActivityMain.TabsAdapter.POSITION_UNCHANGED, 0);
	}

	//Tests if the map is scrollable
	public void test2MapScroll(){
		int left = Utils.getScreenPixels(getActivity()).left;
		int right = Utils.getScreenPixels(getActivity()).right;
		int centerY = Utils.getScreenPixels(getActivity()).centerY();
		solo.drag(left+50, right-50, centerY, centerY, 20);
		solo.drag(left+50, right-50, centerY, centerY, 20);
		solo.drag(left+50, right-50, centerY, centerY, 20);
		solo.drag(left+50, right-50, centerY, centerY, 20);
	}


	//Tests if you can create a new note in mapview
	public void test3CreateNewNote(){
		solo.clickLongOnScreen(300, 500);
		solo.enterText(0, "Hello");
		solo.sendKey(Solo.ENTER);
		solo.enterText(1, "Yes this is dog");
		solo.clickOnScreen(300, 500);
		Assert.assertEquals("Yes this is dog", solo.getText(2).getEditableText().toString());
	}

	//Tests if you can change tabs from mapview to another and then back.
	public void test4ChangeViews(){
		int left = Utils.getScreenPixels(getActivity()).left;
		int right = Utils.getScreenPixels(getActivity()).right;
		int centerY = Utils.getScreenPixels(getActivity()).centerY();
		TestUtils.sweepToList(getActivity(), solo);
		solo.drag(left, right - 50, centerY, centerY, 10);
	}

	//Tests if you can edit an existing note
	public void test5EditNote(){
		solo.clickLongOnScreen(300, 300, 550);
		solo.clearEditText(1);
		solo.enterText(1, "This text is edited");
		solo.goBack();
		solo.clickOnScreen(300, 300);
		TestUtils.sweepToList(getActivity(), solo);
		Assert.assertTrue(solo.searchText("This text is edited"));
	}
	
	public void test6EndIt(){
		DatabaseHandler.getInstance(getActivity()).deleteAllNotesInTestmode();
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
