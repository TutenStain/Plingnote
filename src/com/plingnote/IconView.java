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

	public IconView(Context context,String text,String defaultText, Fragment fragment) {
		super(context);
		this.text = text;
		this.defaultText = defaultText;
		this.fragment = fragment;
	}
	public IconView(Context context,String text,String defaultText, Fragment fragment, String path) {
		super(context);
		this.text = text;
		this.defaultText = defaultText;
		this.fragment = fragment;
		this.path = path;
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
		Bitmap bit;
		if(this.path.equals("")){
			bit = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		}else{
			bit = BitmapFactory.decodeFile(path,null);
		
		}
			
		if(this.text.equals(""))
			canvas.drawText(defaultText,0,defaultText.length(),0,130, paint);
		else
			canvas.drawText(text,0,10,0,130,paint);
		canvas.drawBitmap(bit, 0, 10, null);	          
	}
	
	public String getText() {
		return text;
	}
	
	public String getDefaultText() {
		return defaultText;
	}

	/**
	 * Return fragment
	 * @return
	 */
	public Fragment getFragment(){
		return fragment;
	}
}
