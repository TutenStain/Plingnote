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

package com.plingnote.snotebar;

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

import com.plingnote.R;
import com.plingnote.database.DatabaseHandler;
import com.plingnote.main.ActivityNote;
import com.plingnote.notifications.FragmentReminder;
import com.plingnote.utils.NoteExtra;
import com.plingnote.utils.Utils;

/**
 * Fragment representing a fragment with clickable icons
 * @author Julia Gustafsson
 *
 */
public class FragmentSnotebar extends Fragment {
	private View view;
	private int id = -1;
	private DatabaseHandler dbHandler = DatabaseHandler.getInstance(getActivity());

	public List<IconView> icons = new ArrayList<IconView>();
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {		
		this.view = inflater.inflate(R.layout.fragment_snotebar, container, false);
		return this.view;
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
		addAndSetIconstoLayout();

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
	 * Remove childrens view to the layout to avoid duplicated icons. 
	 * @param linearLayout
	 */
	public void removeChildren(LinearLayout linearLayout){
		if(linearLayout.getChildCount() > 0){
			linearLayout.removeAllViews();
		}
		linearLayout.invalidate();
	}

	/**
	 * Add new icons to list, it's information depends on if the note id exist and if the note has stored value
	 */
	public void addIcontoList(){
		
		if(this.id == -1){
			icons.add(new IconView(getActivity(), "",
					Utils.reminderString, new FragmentReminder(),R.drawable.plingnote_alarm));
			icons.add(new IconView(getActivity(), "",
					Utils.imageString, new SBImageSelector(),R.drawable.plingnote_images));
			icons.add(new IconView(getActivity(), "",
					Utils.categoryString, new SBCategorySelector(),R.drawable.plingnote_categories));		
		}else{
			
			//Check if id is set,then it is information to fetch from database else there is no information
			if(!(this.dbHandler.getNote(this.id).getAlarm().equals(""))){
				icons.add(new IconView(getActivity(), this.dbHandler.getNote(id).getAlarm(),
						Utils.reminderString, new FragmentReminder(),R.drawable.plingnote_alarm));					
			}else{
				icons.add(new IconView(getActivity(),"",
						Utils.reminderString, new FragmentReminder(),R.drawable.plingnote_alarm));
			}
			
			if(!(this.dbHandler.getNote(this.id).getImagePath().equals(""))){
				icons.add(new IconView(getActivity(), "",
						Utils.imageString, new SBImageSelector(), this.dbHandler.getNote(this.id).getImagePath()));					
			}else{
				icons.add(new IconView(getActivity(),"",
						Utils.imageString, new SBImageSelector(),R.drawable.plingnote_images));
			}
			
			if(this.dbHandler.getNote(id).getCategory() != NoteCategory.NO_CATEGORY){
				icons.add(new IconView(getActivity(), this.dbHandler.getNote(id).getCategory().toString(), Utils.categoryString,
						new SBCategorySelector(),
						Utils.getDrawable(this.dbHandler.getNote(id).getCategory())));					
			}else{
				icons.add(new IconView(getActivity(), "",
						Utils.categoryString, new SBCategorySelector(),R.drawable.plingnote_categories));
			}
		}
	}

	/**
	 * Add icons to the fragment layout 
	 * @param linearLayout
	 */
	public void addIcontoLayout(LinearLayout linearLayout){
		
		for(IconView item: icons){
			item.setOnClickListener(new PreviewListener());
			item.setOnLongClickListener(new PreviewLongListner());
			LinearLayout relative = new LinearLayout(getActivity());
			relative.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.FILL_PARENT, (float) 1));
			relative.addView(item);
			linearLayout.addView(relative);
		}
		
		linearLayout.invalidate();
	}

	/**
	 * Setting icon depending if they got an id or not and if their is already information setted or not.
	 */
	public void addAndSetIconstoLayout(){
		LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.snotebar);
		removeChildren(linearLayout);
		icons.clear();
		
		//Check if id is -1, then no id is set and there is no information to fetch from database
		addIcontoList();
		linearLayout = (LinearLayout)view.findViewById(R.id.snotebar);
		addIcontoLayout(linearLayout);
	}

	/**
	 * Checks if one of the note's extra value is setted 
	 * @param noteExtra
	 * @return
	 */
	public boolean checkIfValueIsSetted(NoteExtra noteExtra){
		Activity activityNote = getActivity();
		if(activityNote instanceof ActivityNote) { 
			return ((ActivityNote) activityNote).checkIfValueIsSetted(noteExtra);
		}
		return false;
	}

	/**
	 * An on click listener
	 * @author Julia Gustafsson
	 */
	private class PreviewListener implements OnClickListener{
		
		public PreviewListener(){	
		}
		
		 // Cast view IconView and call the fragment snotebar method 'openNewFragment' 
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
			PluginableFragment pluginFrag = (PluginableFragment)icon.getFragment();
			
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
						PluginableFragment pluginFrag = (PluginableFragment)icon.getFragment();
						
						//This code must unfortunalty be in this class or
						//in fragmentsnotebarclass because if the fragmentReminder isn't
						//in snotebar it's value it's getactivty return null and you can't remove the alarm.
						if(pluginFrag instanceof FragmentReminder){
							ActivityNote activityNote = (ActivityNote)getActivity();
							activityNote.removeReminder();
						}
						
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

	/**
	 * Delete one of the note 'extra' value, which value depends on the parameter.
	 * @param noteExtra
	 */
	public void deleteFragmentValue(NoteExtra noteExtra){
		Activity activityNote = getActivity();
		if(activityNote instanceof ActivityNote) { 
			((ActivityNote) activityNote).deleteValue(noteExtra);
		}
	}
}

