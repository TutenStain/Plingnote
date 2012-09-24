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
	private boolean scrollablePaging = true;
	private Context context;
	private Rect disabledScrollRect = new Rect();
	
	public ScrollableViewPager(Context context) {
		super(context);
		this.context = context;
		this.pagingEnabled = false;
		DisplayMetrics metrics = this.context.getResources().getDisplayMetrics();
		this.disabledScrollRect.set(30, 0, metrics.widthPixels - 30, metrics.heightPixels);
	}
	
	/**
	 * @param context the context
	 * @param scrollablePaging edge page swipe to change tabs
	 */
	public ScrollableViewPager(Context context, boolean scrollablePaging){
		this(context);
		this.scrollablePaging = scrollablePaging;
	}
	
	/**
	 * Handle the touch event. If we decided that we
	 * want to scroll we call on super to handle it
	 * for us. Otherwise if we do not want to scroll
	 * we just return false 
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(this.pagingEnabled)
			return super.onTouchEvent(event);
		
		return false;
	}
	/**
	 * Intercept the touch events and if we decide that
	 * a swipe started from the edges of the screen, 
	 * consume the event.
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if(scrollablePaging) {
			if(disabledScrollRect.contains((int)event.getX(), (int)event.getY()) == false) {
				pagingEnabled = true;
				return true;
			} else
				pagingEnabled = false;
		}
		
		return false;
	}
	/**
	 * This enables swipe between tabs if the swipe is
	 * originated from either side of the edges
	 * @param enabled edge page swipe to change tabs
	 */
	 public void setEdgeSwipePagingEnabled(boolean enabled) {
	        this.scrollablePaging = enabled;
	 }
	 
	 /**
	  * @return if edge swipe is enabled for this pager
	  */
	 public boolean isEdgeSwipePagingEnabled(){
		 return this.scrollablePaging;
	 }
}