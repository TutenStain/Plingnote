package com.plingnote.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.plingnote.R;
import com.plingnote.snotebar.NoteCategory;

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

	/**
	 *Return a drawable id.
	 * @param noteCategory
	 * @return int
	 */
	public static int getDrawable(NoteCategory noteCategory){
		
		switch (noteCategory) {
		case Meeting:
			return R.drawable.meeting;
		case Internet:
			return R.drawable.internet;
		case Hash:
			return R.drawable.hash;
		case Note:
			return R.drawable.note;
		case Comment:
			return R.drawable.comment;
		case Sun:
			return R.drawable.sun;
		case Steeringwheel:
			return R.drawable.steeringwheel;
		case Key:
			return R.drawable.key;
		case Mechanic:
			return R.drawable.mechanic;
		case Pin:
			return R.drawable.pin;
		default:
			return -1;
		}
	}
	
	/**
	 * The width of the gallery image
	 */
	public static final int SNOTEBAR_IMAGE_WIDTH = 120;
	
	/**
	 * Formats the integer value to represent time more correctly.
	 * Adds an 0 to integers less then 10.
	 * Like: 6 becomes 06
	 * 	   : 9 becomes 09
	 * 	   : 12 becomes 12
	 * @param x
	 * @return the formated String
	 */
	public static String getFormatedTime(int x){
		if(x < 10){
			return "0" + x;
		}
		
		return x + "";
	}

	public static Date parseDateFromDB(String d){
		SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		Date date = null;
		try {
			date = fmt.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}
}
