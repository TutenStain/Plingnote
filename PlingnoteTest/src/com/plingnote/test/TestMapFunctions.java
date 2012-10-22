package com.plingnote.test;

import junit.framework.Assert;

import com.plingnote.ActivityMain;
import com.plingnote.Utils;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

public class TestMapFunctions extends
ActivityInstrumentationTestCase2<ActivityMain> {
	private Solo solo;
	private int left = Utils.getScreenPixels(getActivity()).left;
	private int right = Utils.getScreenPixels(getActivity()).right;
	private int centerY = Utils.getScreenPixels(getActivity()).centerY();

	public TestMapFunctions() {
		super("com.plingnote", ActivityMain.class);
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
		solo.drag(left+50, right-50, 500, 500, 20);
		solo.drag(left+50, right-50, 500, 500, 20);
		solo.drag(left+50, right-50, 500, 500, 20);
		solo.drag(left+50, right-50, 500, 500, 20);
	}


	//Tests if you can create a new note in mapview
	public void test3CreateNewNote(){
		solo.clickLongOnScreen(300, 500);
		solo.enterText(0, "Hello");
		solo.sendKey(Solo.ENTER);
		solo.enterText(1, "Yes this is dog");
		solo.goBack();
		solo.clickOnScreen(300, 500);
		Assert.assertEquals("Yes this is dog", solo.getText(2).getEditableText().toString());
	}

	//Tests if you can change tabs from mapview to another and then back.
	public void test4ChangeViews(){

		solo.drag(right-1, left+50 , centerY, centerY, 50);
		solo.drag(left, right-50, centerY, centerY, 50);
	}

	//Tests if you can edit an existing note
	public void test5EditNote(){
		solo.clickOnScreen(300, 500);
		solo.clearEditText(1);
		solo.enterText(1, "This text is edited");
		solo.goBack();
		solo.clickOnScreen(300, 500);
		Assert.assertEquals("This text is edited", solo.getText(2).getEditableText().toString());
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
