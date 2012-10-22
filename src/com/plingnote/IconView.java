/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 Julia Gustafsson
 *
 * Plingnote is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
	private String path = "";
	private Paint paint;
	private Bitmap bit;

	public IconView(Context context,String text,String defaultText, Fragment fragment) {
		super(context);
		this.text = text;
		this.defaultText = defaultText;
		this.fragment = fragment;
		this.setPaintSettings();
		this.bit = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	}
	
	public IconView(Context context,String text,String defaultText, Fragment fragment, String path) {
		super(context);
		this.text = text;
		this.defaultText = defaultText;
		this.fragment = fragment;
		this.path = path;
		this.setPaintSettings();
		this.bit = ImageHelper.decodeSampledBitmapFromUri(this.path, 140, 140); 
	}
	
	public IconView(Context context,String text,String defaultText, Fragment fragment, int drawbleId) {
		super(context);
		this.text = text;
		this.defaultText = defaultText;
		this.fragment = fragment;
		this.setPaintSettings();
		this.bit = BitmapFactory.decodeResource(getResources(), drawbleId);
		this.bit = Bitmap.createScaledBitmap(this.bit, 140, 140, false);
	}

	public void setPaintSettings(){
		this.paint = new Paint();
		this.paint.setColor(Color.WHITE); 
		this.paint.setTextSize(30); 
	}

	/**
	 * Draw a icon with text under 
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		if(this.path.equals("")) {

			if(this.text.equals(""))
				canvas.drawText(this.defaultText, 0, this.defaultText.length(), 10, 180, paint);
			else{
				
				if(text.length() > 1)
					this.text = text.substring(0, 1).toUpperCase() + this.text.substring(1);
				
				if(text.length() > 10)
					this.text = text.substring(0, 10);
				canvas.drawText(text, 0, text.length(), 10, 180, this.paint);
			}
		}
		canvas.drawBitmap(bit, 0, 10, null);	          
	}

	public String getText() {
		return this.text;
	}

	public String getDefaultText() {
		return this.defaultText;
	}

	/**
	 * Return fragment
	 * @return
	 */
	public Fragment getFragment(){
		return this.fragment;
	}
}
