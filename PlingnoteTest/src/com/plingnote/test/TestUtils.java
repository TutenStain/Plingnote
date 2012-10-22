package com.plingnote.test;

import android.app.Activity;

import com.jayway.android.robotium.solo.Solo;
import com.plingnote.utils.Utils;

public class TestUtils{

	/**
	 * @author Linus Karlsson
	 */
	public static void sweepToList(Activity activity, Solo solo){
		solo.drag(Utils.getScreenPixels(activity).width() - 1, Utils
				.getScreenPixels(activity).left + 50, Utils
				.getScreenPixels(activity).exactCenterY(), Utils
				.getScreenPixels(activity).exactCenterY(), 10);
	}
	
	public static void swipeToGrid(Activity activity, Solo solo){
		int left = Utils.getScreenPixels(activity).left;
		int right = Utils.getScreenPixels(activity).right;
		int centerY = Utils.getScreenPixels(activity).bottom/2;
		solo.drag(right-1, left+25 , centerY, centerY, 10);
		solo.drag(right-1, left+25 , centerY, centerY, 10);
	}
	

}
