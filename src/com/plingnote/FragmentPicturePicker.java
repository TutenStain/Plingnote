package com.plingnote;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment showing pictures in a horizontal scrollable list.
 * 
 * @author Linus Karlsson
 * 
 */
public class FragmentPicturePicker extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		/**
		 * If there's no view holding the fragment, there's no need to create
		 * its view hierarchy.
		 */
		if (container == null) {
			return null;
		}

		/**
		 * The view displayed to the user. Containing all the pictures from a
		 * specific folder.
		 */
		PictureViewer picView = (PictureViewer) getView().findViewById(
				R.id.pic_viewer);

		// Get files from a certain directory on the device.
		String filesDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/DCIM/Camera/";

		// Place all pictures in an array and add them to the view.
		File[] pictures = new File(filesDir).listFiles();
		for (File file : pictures) {
			picView.add(file.getAbsolutePath());
		}

		return inflater.inflate(R.layout.fragment_picture_picker, container,
				false);
	}
}
