/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 Linus Karlsson, Sergey Tarasevich
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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @author Linus Karlsson
 * 
 */
public class SBImageAdapter extends BaseAdapter {

	private Context context;
	private List<String> imagePaths;

	public SBImageAdapter(Context context, List<String> imagePaths,
			ImageLoader imageLoader) {
		this.context = context;
		this.imagePaths = new ArrayList<String>(imagePaths);
	}

	public int getCount() {
		return imagePaths.size();
	}

	public Object getItem(int position) {
		return imagePaths.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = (ImageView) convertView;
		if (imageView == null) {
			imageView = (ImageView) LayoutInflater.from(context).inflate(
					R.layout.snotebar_image, parent, false);
		}

		// Options for how an image is displayed
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();

		ImageLoader.getInstance().displayImage(imagePaths.get(position),
				imageView, options);
		return imageView;
	}

}
