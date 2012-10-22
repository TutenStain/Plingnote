package com.plingnote;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class Utils {
	public static Rect getScreenPixels(Context context){
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return new Rect(0, 0, metrics.widthPixels, metrics.heightPixels);
	}

	/**
	 * Key for query a note.
	 */
	public final static String QUERY_NOTE = "com.plingnote.QUERY_NOTE";

	public final static String reminderString = "Reminder";
	
	public final static String imageString = "Image";
	
	public final static String categoryString = "Category";

	public static int getDrawable(NoteCategory noteCategory){
		
		switch (noteCategory) {
		case Bank:
			return R.drawable.bank;
		case Chat:
			return R.drawable.chat;	
		case Fun:
			return R.drawable.fun;
		case Lunch:
			return R.drawable.lunch;
		case Meeting:
			return R.drawable.meeting;
		case Shop:
			return R.drawable.shop;
		case Write:
			return R.drawable.write;
		default:
			return -1;
		}
	}
	
	/**
	 * The width of the gallery image
	 */
	public static final int SNOTEBAR_IMAGE_WIDTH = 120;

}
