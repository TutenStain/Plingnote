package com.plingnote;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

/**
 * A custom ViewPager that supports horizontal 
 * scrolling inside the view (tab). This class
 * decides if the user wanted to scroll to change
 * tab or just to scroll inside the current view.
 * If this class decides that the user wanted to
 * change view (tab), it consumes the MotionEvent.
 * Otherwise it passes the MotionEvent on to other
 * classes. 
 */
public class ScrollableViewPager extends ViewPager {
	private boolean pagingEnabled;
	
	public ScrollableViewPager(Context context) {
		super(context);
		this.pagingEnabled = false;
	}
	
	public ScrollableViewPager(Context context, boolean pagingEnabled){
		this(context);
		this.pagingEnabled = pagingEnabled;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(this.pagingEnabled)
			return super.onTouchEvent(event);
		
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {   
		if(this.pagingEnabled)
			return super.onInterceptHoverEvent(event);
			
		return false;
	}
	
	 public void setPagingEnabled(boolean enabled) {
	        this.pagingEnabled = enabled;
	 }
	 
	 public boolean isPagingEnabled(){
		 return this.pagingEnabled;
	 }
}
