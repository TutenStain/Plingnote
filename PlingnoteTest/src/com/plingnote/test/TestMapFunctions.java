package com.plingnote.test;

import com.plingnote.ActivityMain;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

public class TestMapFunctions extends
ActivityInstrumentationTestCase2<ActivityMain> {
	private Solo solo;

	public TestMapFunctions() {
		super("com.plingnote", ActivityMain.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void test1MapViewAtStartUp(){
		solo.waitForView(null, ActivityMain.TabsAdapter.POSITION_UNCHANGED, 0);
	}
	
	public void test2MapScroll(){
		solo.drag(100, 500, 500, 500, 50);
		solo.drag(100, 500, 500, 500, 50);
		solo.drag(100, 500, 500, 500, 50);
		solo.drag(100, 500, 500, 500, 50);
	}
	
	public void test3MapZoom(){
		solo.clickOnScreen(500, 500);
		solo.clickOnImageButton(1);
		solo.clickOnImageButton(0);
	}


	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
