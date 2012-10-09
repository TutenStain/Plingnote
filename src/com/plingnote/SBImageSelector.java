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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Fragment showing pictures in a horizontal scrollable list.
 * 
 * @author Linus Karlsson
 * 
 */
public class SBImageSelector extends Fragment implements PluginFragment{
	private String selectedImage;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.snotebar_image_browser, container,
				false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Initialize ImageLoader
		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(getActivity()));

		// Get files from a certain directory on the device.
		String filesDir = Environment.getExternalStorageDirectory()
			.getAbsolutePath();
		
		// Insert the image paths into an array
		File[] pictures = new File(filesDir).listFiles();
		List<String> imagePaths = new ArrayList<String>();
		for (File file : pictures) {
			imagePaths.add(file.getAbsolutePath());
		}

		Gallery gallery = (Gallery) getView().findViewById(
				R.id.snotebar_image_browser);
		gallery.setAdapter(new SBImageAdapter(getActivity(), imagePaths,
				ImageLoader.getInstance()));
		gallery.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Intent snoteBar = new Intent(getActivity(), FragmentSnoteBar.class);

				// The path to the selected image
				selectedImage = (String)
				parent.getAdapter().getItem(position);

				// snoteBar.putExtra("MEDDELANDE", selectedImage);
			}

		});
	}

	@Override
	public void onStop() {
		ImageLoader.getInstance().stop();
		super.onStop();
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return selectedImage;
	}

	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public NoteExtra getKind() {
		// TODO Auto-generated method stub
		return NoteExtra.IMAGE;
	}

	public void replaceBackFragment() {
		// TODO Auto-generated method stub
		
	}
}
