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
	
	//Tests if the map will start
	public void test1MapViewAtStartUp(){
		solo.waitForView(null, ActivityMain.TabsAdapter.POSITION_UNCHANGED, 0);
	}
	
	//Tests if the map is scrollable
	public void test2MapScroll(){
		solo.drag(100, 500, 500, 500, 50);
		solo.drag(100, 500, 500, 500, 50);
		solo.drag(100, 500, 500, 500, 50);
		solo.drag(100, 500, 500, 500, 50);
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
