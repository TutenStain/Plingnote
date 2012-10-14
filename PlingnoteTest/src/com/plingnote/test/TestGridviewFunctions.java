package com.plingnote.test;

import com.plingnote.ActivityMain;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

public class TestGridviewFunctions extends
ActivityInstrumentationTestCase2<ActivityMain> {
	private Solo solo;


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
		solo.clickOnImage(5);
	}
	
	//Tests if you can add a new note, and if it's viewable in gridview
	public void test2AddNewNote(){
		solo.clickOnImage(5);
		solo.clickOnImage(1);
		solo.enterText(0, "Hello?");
		solo.enterText(0, "\nyes, this is dog");
		solo.goBack();
	}

	//Tests if you can delete a note in gridview
	public void test3DeleteNote(){
		solo.clickOnImage(5);
		solo.clickLongOnScreen(150, 400);
		solo.clickOnImage(1);
	}

	//Tests if you can edit a note through gridview by first creating
	//one and then editing it
	public void test4EditNote(){
		solo.clickOnImage(5);
		solo.clickOnImage(1);
		solo.enterText(0, "Hello?");
		solo.enterText(0, "\nyes, this is dog");
		solo.goBack();
		solo.clickOnScreen(150, 400);
		solo.enterText(0, "This text is edited");
		solo.goBack();
	}

	//Tests if you can select multiple notes and then delete them
	public void test5DeleteMultipleNotes(){
		solo.clickOnImage(5);
		for(int i = 1; i < 6; i++){
			solo.clickOnImage(1);
			solo.enterText(0, "Hello?"+i);
			solo.enterText(0, "\nyes, this is dog");
			solo.goBack();
		}
		solo.clickLongOnScreen(350, 400);
		solo.clickOnScreen(350, 500);
		solo.clickOnScreen(150, 500);
		solo.clickOnScreen(550, 400);
		solo.clickOnScreen(150, 400);
		solo.clickOnScreen(550, 500);
		solo.clickOnImage(1);
	}
	
	//Tests if you can swipe to the gridview
	public void test6swipe(){
		solo.drag(719, 1, 500, 500, 25);
		solo.drag(719, 1, 500, 500, 25);
	}

}
