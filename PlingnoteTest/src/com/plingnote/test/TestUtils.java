package com.plingnote.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.plingnote.ActivityMain;
import com.plingnote.Utils;

public class TestUtils{

	/**
	 * @author Linus Karlsson
	 */
	public static  void sweepToList(Activity activity, Solo solo){
		solo.drag(Utils.getScreenPixels(activity).width() - 1, Utils
				.getScreenPixels(activity).left + 50, Utils
				.getScreenPixels(activity).exactCenterY(), Utils
				.getScreenPixels(activity).exactCenterY(), 50);
	}
	

}
