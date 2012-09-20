package com.plingnote;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

/**
 * A custom ViewPager that supports horizontal 
 * scrolling inside the view (tab). This class
 * decides if the user wanted to scroll to change
 * tab or just to scroll inside the current view.
 * If this class decides that the user wanted to
 * change view (tab), it consumes the MotionEvent.
 * Otherwise it passes the MotionEvent on to other
 * classes. The default behavior is that a swipe to
 * change view is intercepted if it starts from the
 * edges of the screen.
 */
public class ScrollableViewPager extends ViewPager {
	private boolean pagingEnabled;
	private Context context;
	private int screenWidth;
	private int screenHiehgt;
	private Rect rect = new Rect();
	
	public ScrollableViewPager(Context context) {
		super(context);
		this.context = context;
		this.pagingEnabled = false;
		DisplayMetrics metrics = this.context.getResources().getDisplayMetrics();
		this.screenWidth = metrics.widthPixels;
		this.screenHiehgt = metrics.heightPixels;
		rect.set(25, 0, screenWidth - 25, screenHiehgt);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(this.pagingEnabled)
			return super.onTouchEvent(event);
		
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {   				
		if(rect.contains((int)event.getX(), (int)event.getY()) == false) {
			pagingEnabled = true;
			return true;
		} else {
			pagingEnabled = false;
			return false;
		}
	}
	
	 public void setPagingEnabled(boolean enabled) {
	        this.pagingEnabled = enabled;
	 }
	 
	 public boolean isPagingEnabled(){
		 return this.pagingEnabled;
	 }
}
