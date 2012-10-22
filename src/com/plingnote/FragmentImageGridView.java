/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 Magnus Huttu
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
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * a public class that shows a list of notes in a gridview.
 * @author Magnus Huttu
 *
 */
public class FragmentImageGridView extends Fragment implements OnItemClickListener, Observer{

	public FragmentImageGridView(){}

	private LayoutInflater layoutInflater;
	private Point imgSize;
	private DatabaseHandler db;
	private List<Note> notes = new ArrayList<Note>();		
	private ActionMode actionBar;
	private GridView gView;
	private ImageAdapter imgAdapter;
	private boolean abOn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setImgSize(int x, int y){
		imgSize.set(x, y);
	}

	public static FragmentImageGridView instantiate(int index){
		FragmentImageGridView f = new FragmentImageGridView();

		Bundle args = new Bundle();
		args.putInt("index", index);


		f.setArguments(args);
		return f;

	}

	private List<Integer> imgIds = new ArrayList<Integer>();

	/**
	 * Creating the gridview layout.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null){
			return null;
		}
		abOn = false;

		DatabaseHandler.getInstance(getActivity()).addObserver(this);
		//Getting the database
		db = DatabaseHandler.getInstance(getActivity());

		//Creating the view based on orientation of the screen
		final View grid;
		getResources().getConfiguration();
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			grid = inflater.inflate(R.layout.fragment_gridview, container, false); 
		} else{
			grid = inflater.inflate(R.layout.fragment_gridview_land, container, false); 
		}

		//Setting image adapter for the view.
		this.imgAdapter = new ImageAdapter(getActivity());
		gView = (GridView) grid.findViewById(R.id.grid);
		gView.setAdapter(this.imgAdapter);
		gView.setOnItemClickListener(this);

		// Make it possible for the user to select multiple items.
		gView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
		gView.setMultiChoiceModeListener(new LongPress());
		layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		//Setting size for all the images used inside the view.
		imgSize = new Point();
		int side = getActivity().getResources().getDisplayMetrics().widthPixels / 4 ;
		this.setImgSize(side,side);

		//refreshes notes.
		this.refreshNotes();

		this.checkIfEmpty();

		return grid;
	}


	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(Context context) {
			super();
		}

		//@Override
		public int getCount() {
			return numberOfNotes();
		}

		//@Override
		public Object getItem(int position) {
			return null;
		}

		//@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * Returns a View with two imageviews and two textviews baked into it,
		 *  these create an image with some text on top.
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = new View(getActivity());

			if (convertView == null) {
				v = layoutInflater.inflate(R.layout.image_item, null);
			} else {
				v = convertView;
			}
			//initiating the views in the gridview item.
			ImageView imgView = (ImageView) v.findViewById(R.id.gridview_image);
			ImageView imgViewTop = (ImageView) v.findViewById(R.id.gridview_image_top);
			TextView tvTitle = (TextView) v.findViewById(R.id.gridview_image_title);
			TextView tvText = (TextView) v.findViewById(R.id.gridview_image_text);
			tvTitle.findViewById(R.id.gridview_image_title);
			tvText.findViewById(R.id.gridview_image_text);
			tvTitle.setText(notes.get(position).getTitle());
			tvText.setText(notes.get(position).getText());

			//Standard image set on icons
			imgView.setBackgroundResource(imgIds.get(position));
			if(abOn){
				imgViewTop.setBackgroundColor(Color.TRANSPARENT);
				if(gView.getCheckedItemPositions().get(position)){
					imgViewTop.setBackgroundResource(R.drawable.checked);
				}
			} else{
				imgViewTop.setBackgroundColor(Color.TRANSPARENT);
			}
			
			return v;
		}

	}

	/**
	 * Deletes all the notes in the list notes and then adds freshly from the database.
	 */
	public void refreshNotes(){
		this.clearNotes();

		this.imgIds.clear();
		for(Note n : this.db.getNoteList()){
			this.addNote(n);
		}
	}

	/**
	 * adds a note to the list notes.
	 * @param note adds this object to the list.
	 */
	public void addNote(Note note){
		notes.add(note);
		
		String category = note.getCategory().toString();
		
		if(category.equals(NoteCategory.Bank.toString())){
			imgIds.add(R.drawable.bank);	
		} else if(category.equals(NoteCategory.Chat.toString())){
			imgIds.add(R.drawable.chat);
		} else if(category.equals(NoteCategory.Fun.toString())){
			imgIds.add(R.drawable.fun);
		} else if(category.equals(NoteCategory.Lunch.toString())){
			imgIds.add(R.drawable.lunch);
		} else if(category.equals(NoteCategory.Meeting.toString())){
			imgIds.add(R.drawable.meeting);
		} else if(category.equals(NoteCategory.Shop.toString())){
			imgIds.add(R.drawable.shop);
		} else{
			imgIds.add(R.drawable.write);
		}
	}

	/**
	 * Deletes all notes in the list notes.
	 */
	public void clearNotes(){
		this.notes.clear();
	}

	/**
	 * @return returns an int with the size of the list notes.
	 */
	public int numberOfNotes(){
		return notes.size();
	}

	//@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Intent editNote = new Intent(getActivity(), ActivityNote.class);

		// Get the ID of the clicked note.
		int noteId = notes.get(position).getId();
		editNote.putExtra(IntentExtra.id.toString(), noteId);
		editNote.putExtra(IntentExtra.justId.toString(),true);

		startActivity(editNote);		
	}

	/**
	 * Remove checked notes from the list.
	 */
	public void removeItem() {
		// Get the positions of all the checked items.
		SparseBooleanArray checkedItemPositions = gView
				.getCheckedItemPositions();

		// Walk through the notes and delete the checked ones.
		for (int i = notes.size() - 1; i >= 0; i--) {
			if (checkedItemPositions.get(i)) {
				this.db.deleteNote(notes.get(i).getId());
			}
		}
		// Refresh the note list and the view
		this.refreshNotes();
		this.imgAdapter.notifyDataSetChanged();
	}


	/**
	 * Private class handling long presses, which forces the action bar to show
	 * up. Users can also choose multiple notes.
	 * 
	 * @author Magnus Huttu
	 * 
	 */
	private class LongPress implements MultiChoiceModeListener {

		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			// Listen to user input and perform the action of choice.
			switch (item.getItemId()) {
			case R.id.remove:
				removeItem(); // Delete the selected notes.
				imgAdapter.notifyDataSetChanged();
				mode.finish(); // Close the action bar after deletion.
			default:
				return false;
			}

		}

		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			actionBar = mode;
			abOn = true;
			// Make the mobile vibrate on long click
			((Vibrator) getActivity()
					.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50);

			// Display contextual action bar to user.
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.multi_select_menu, menu);
			mode.setTitle("Select notes");
			return true;
		}

		public void onDestroyActionMode(ActionMode mode) {
			gView.getCheckedItemPositions().clear();
			abOn = false;
			gView.setAdapter(imgAdapter);
			checkIfEmpty();
		}

		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return true;
		}



		/**
		 * Called everytime the state of the list is changed, for example when a
		 * note is selected. Displays the number of selected notes to the user.
		 */
		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {

			imgAdapter.notifyDataSetChanged();
			switch (gView.getCheckedItemCount()) {
			case (0):
				// If no note is selected, don't set any subtitle.
				mode.setSubtitle(null);
			break;
			case (1):
				// If one note is selected
				mode.setSubtitle("One note selected");
			break;
			default:
				// If more than one time are selected, display the number of
				// selected notes to user.
				mode.setSubtitle("" + gView.getCheckedItemCount()
						+ " notes selected");
				break;
			}
		}
	}

	/**
	 * Close the contextual action bar (top menu) when changing to map view.
	 */
	@Override
	public void setUserVisibleHint(boolean isActive) {
		super.setUserVisibleHint(isActive);

		// Check if current view
		if (isVisible()) {
			if (!isActive) {
				// If user leaves the list view, a close the top menu.
				if(actionBar != null) {
					actionBar.finish();
				}
			}
		}
	}

	/**
	 * Refresh notes when returning to the gridview.
	 */
	@Override
	public void onResume() {
		super.onResume();
		this.refreshNotes();
	}

	public void update(Observable observable, Object data){
		if(observable instanceof DatabaseHandler){
			if(((DatabaseUpdate)data == DatabaseUpdate.UPDATED_LOCATION) 
					|| ((DatabaseUpdate)data == DatabaseUpdate.NEW_NOTE)
					|| ((DatabaseUpdate)data == DatabaseUpdate.UPDATED_NOTE)
					|| ((DatabaseUpdate)data == DatabaseUpdate.DELETED_NOTE)) {
				this.refreshNotes();
				this.checkIfEmpty();
				this.imgAdapter.notifyDataSetChanged();
			}
		}
	}

	public void checkIfEmpty(){
		if(notes.size() < 1){
			getResources().getConfiguration();
			if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
				gView.setBackgroundResource(R.drawable.empty_portrait);
			} else{
				gView.setBackgroundResource(R.drawable.empty_landscape);
			} 
		}
		else{
			gView.setBackgroundColor(Color.BLACK);
		}
	}
}