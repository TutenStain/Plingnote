package com.plingnote;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
/**
 * Fragment representing a fragment with clickable icons
 * @author Julia Gustafsson
 *
 */
public class FragmentSnotebar extends Fragment {
	private View view;
	private int id = -1;
	public String reminderString = "Reminder";
	public String imageString = "Image";

	public List<IconView> icons = new ArrayList<IconView>();
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {		
		view = inflater.inflate(R.layout.fragment_snotebar, container, false);
		return view;
	}
	/**
	 * Passing parameter fragment to noteActivity method replace.
	 * @param noteExtra
	 */
	public void replaceFragment(Fragment fragment){
		Activity activityNote = getActivity();
		if(activityNote instanceof ActivityNote) { 
			((ActivityNote) activityNote).replaceFragment(fragment);
		}
	}
	/**
	 * Call set id and call setIcons method
	 */
	@Override 
	public void onStart(){
		super.onStart();
		Activity activityNote = getActivity();
		if(activityNote instanceof ActivityNote) { 
			this.id = ((ActivityNote) activityNote).getId();
		}
		setIcons();
	}	
	/**
	 * CLear icons list
	 */
	@Override 
	public void onPause(){
		super.onPause();
		icons.clear();
	}
	/**
	 * Setting icon depending if they got an id or not and if their is already information setted or not.
	 */
	public void setIcons(){
		icons.clear();
		//Check if id is -1, then no id is set and there is no information to fetch from database
		if(id == -1){
			icons.add(new IconView(getActivity(),"", reminderString, new FragmentReminder()));
			icons.add(new IconView(getActivity(),"", imageString, new SBImageSelector()));
		}else{
			//Check if id is set,then it is information to fetch from database else there is no information
			if(DatabaseHandler.getInstance(getActivity()).getNote(id).getAlarm() != null || !(DatabaseHandler.getInstance(getActivity()).getNote(id).getAlarm().equals(""))){
				icons.add(new IconView(getActivity(),DatabaseHandler.getInstance(getActivity()).getNote(id).getAlarm(), reminderString, new  FragmentReminder()));					
			}else{
				icons.add(new IconView(getActivity(),"", reminderString, new FragmentReminder()));
			}
		}
		
		RelativeLayout ll = (RelativeLayout) view.findViewById(R.id.icon1);
		icons.get(0).setOnClickListener(new PreviewListener());
		icons.get(0).setOnLongClickListener(new PreviewLongListner());
		ll.addView(icons.get(0));
		ll.invalidate();
		RelativeLayout kl = (RelativeLayout) view.findViewById(R.id.icon2);
		icons.get(1).setOnClickListener(new PreviewListener());
		icons.get(1).setOnLongClickListener(new PreviewLongListner());
		kl.addView(icons.get(1));
		kl.invalidate();
	}
	public boolean checkIfValueIsSetted(NoteExtra noteExtra){
		Activity activityNote = getActivity();
		if(activityNote instanceof ActivityNote) { 
			return ((ActivityNote) activityNote).checkIfValueIsSetted(noteExtra);
		}
		return false;
	}
	/**
	 * An on click listener
	 * @author Julia
	 *
	 */
	private class PreviewListener implements OnClickListener{
		public PreviewListener(){	
		}
		/**
		 * Cast view IconView and call the fragment snotebar method 'openNewFragment'
		 */
		public void onClick(View v) {		
			IconView icon = (IconView)v;
			FragmentSnotebar.this.replaceFragment(icon.getFragment());
		}
	}
	/**
	 * An on long click listener
	 * @author Julia
	 *
	 */
	private class PreviewLongListner implements OnLongClickListener {
		IconView icon;
		public boolean onLongClick(View v) {
			icon = (IconView)v;
			PluginFragment pluginFrag = (PluginFragment)icon.getFragment();
			if(FragmentSnotebar.this.checkIfValueIsSetted(pluginFrag.getKind())){
				DialogFragment newFragment = new AskIfReset();
				newFragment.show(getFragmentManager(), "Reset");
				return true;
			}else
				return false;
		} 

		/**
		 * A dialogfragment that ask if the user wants to reset the data of the icon from the note
		 * @author Julia
		 *
		 */
		private class AskIfReset extends DialogFragment {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Reset");
				builder.setIcon(android.R.drawable.ic_dialog_alert);
				builder.setMessage("Do you want to reset the " + icon.getDefaultText() + " ?");
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					//Call methods that will delete the data
					public void onClick(DialogInterface dialog, int id) {
						PluginFragment pluginFrag = (PluginFragment)icon.getFragment();
						FragmentSnotebar.this.deleteFragmentValue(pluginFrag.getKind());
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog
					}
				});
				return builder.create();
			}
		}
	}
	public void deleteFragmentValue(NoteExtra noteExtra){
		Activity activityNote = getActivity();
		if(activityNote instanceof ActivityNote) { 
			((ActivityNote) activityNote).deleteValue(noteExtra);
		}
	}

}

