package com.plingnote;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * A class representing a icon with icon and text, and that can return a fragment. 
 * @author Julia Gustafsson 
 *
 */
public class IconView extends View{
	private String text = ""; 
	private Fragment fragment;
	private String defaultText = "";

	public IconView(Context context,String text,String defaultText, Fragment fragment) {
		super(context);
		this.text = text;
		this.defaultText = defaultText;
		this.fragment = fragment;
	}

	/**
	 * Draw a icon with text under 
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(Color.WHITE); 
		paint.setTextSize(30); 
		Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		if(this.text.equals(""))
			canvas.drawText(defaultText,0,defaultText.length(),0,130, paint);
		else
			canvas.drawText(text,0,10,0,130,paint);
		canvas.drawBitmap(bit, 0, 10, null);	          
	}
	
	/**
	 * Return fragment
	 * @return
	 */
	public Fragment getFragment(){
		return fragment;
	}
}
