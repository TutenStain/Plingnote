package com.plingnote;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
/**
 * Fragment representing a fragment with clickable icons
 * @author Julia Gustafsson
 *
 */
public class FragmentSnotebar extends Fragment {
	private View view;
	private int id = -1;
	public String reminderString = "Reminder";

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
	 * Call setIcons method
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
	 * CLear list
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
		}else{
			//Check if id is set,then it is information to fetch from database else there is no information
			if(DatabaseHandler.getInstance(getActivity()).getNote(id).getAlarm() != null || !(DatabaseHandler.getInstance(getActivity()).getNote(id).getAlarm().equals(""))){
				icons.add(new IconView(getActivity(),DatabaseHandler.getInstance(getActivity()).getNote(id).getAlarm(), reminderString, new  FragmentReminder()));					
			}else{
				icons.add(new IconView(getActivity(),"", reminderString, new FragmentReminder()));
			}
		}
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.icon);
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setPadding(30, 30, 30, 30);
		//Set icon on layout with onclicklistener.
		for(IconView item : icons){	
			
			item.setOnClickListener(new PreviewListener());
			item.setOnLongClickListener(new PreviewLongListner());
			item.setClickable(true);
			
			//item.setPadding(30, 30, 30, 30);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					(LayoutParams.FILL_PARENT), (LayoutParams.FILL_PARENT));
			item.setLayoutParams(lp);
			ll.addView(item);
			ll.invalidate();
		}
	}
	/**
	 * An onclicklistener
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
	private class PreviewLongListner implements OnLongClickListener {

		public boolean onLongClick(View v) {
			IconView icon = (IconView)v;
			PluginFragment pf = (PluginFragment)icon.getFragment();
			FragmentSnotebar.this.deleteFragmentValue(pf.getKind());
			return true;
		} 
   

    }
	
	public void deleteFragmentValue(NoteExtra noteExtra){
		Activity activityNote = getActivity();
		if(activityNote instanceof ActivityNote) { 
			((ActivityNote) activityNote).deleteValue(noteExtra);
		}
	}

}

