package com.plingnote.test;

import junit.framework.Assert;

import com.plingnote.ActivityMain;
import com.plingnote.R.id;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

public class TestGridviewFunctions extends
ActivityInstrumentationTestCase2<ActivityMain> {
	private Solo solo;
	private String editText = "This text is edited";


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
		solo.sendKey(Solo.ENTER);
		solo.enterText(1, "yes, this is dog");
		solo.goBack();
		solo.clickOnImage(1);
		solo.enterText(0, "Hello?");
		solo.sendKey(Solo.ENTER);
		solo.enterText(1, "yes, this is dog");
		solo.goBack();
		Assert.assertEquals(4, solo.getCurrentImageViews(solo.getView(id.grid)).size());
	}

	//Tests if you can delete a note in gridview
	public void test3DeleteNote(){
		solo.clickOnImage(5);
		solo.clickLongOnScreen(150, 400);
		solo.clickOnImage(1);
		solo.clickOnImage(1);
		solo.goBack();
		Assert.assertEquals(2, solo.getCurrentImageViews(solo.getView(id.grid)).size());
	}

	//Tests if you can edit a note through gridview by first creating
	//one and then editing it
	public void test4EditNote(){
		solo.clickOnImage(5);
		solo.clickOnScreen(150, 400);
		solo.clearEditText(1);
		solo.enterText(1, editText);
		solo.goBack();
		solo.clickOnScreen(150, 400);
		Assert.assertEquals(editText, solo.getText(2).getEditableText().toString());
		solo.goBack();
	}

	//Tests if you can select multiple notes and then delete them
	public void test5DeleteMultipleNotes(){
		solo.clickOnImage(5);
		for(int i = 1; i < 6; i++){
			solo.clickOnImage(1);
			solo.enterText(0, "Hello?"+i);
			solo.sendKey(Solo.ENTER);
			solo.enterText(1, "yes, this is dog");
			solo.goBack();
		}
		Assert.assertEquals(12, solo.getCurrentImageViews(solo.getView(id.grid)).size());
		solo.clickLongOnScreen(350, 400);
		solo.clickOnScreen(350, 500);
		solo.clickOnScreen(150, 500);
		solo.clickOnScreen(550, 400);
		solo.clickOnScreen(150, 400);
		solo.clickOnScreen(550, 500);
		solo.clickOnImage(1);
	}
}
