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
}
