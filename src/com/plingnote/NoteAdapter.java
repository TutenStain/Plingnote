package com.plingnote;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Custom Adapter for the note list. Inherits from ArrayAdapter.  
 * @author Linus Karlsson
 *
 */
public class NoteAdapter extends ArrayAdapter<Note> {
	private List<Note> notes;
	
	public NoteAdapter(Context context, int textViewResourceId, List<Note> notes) {
		super(context, textViewResourceId, notes);
		this.notes = notes;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		// If view is empty, render it.
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item, null);
		}
		
		/**
		 * Reference to the current Note object. 
		 */
		Note n = notes.get(position);
		
		if(n != null) {
			// Create textviews in defined XML files. 
			TextView title = (TextView) view.findViewById(R.id.title);
			TextView text = (TextView) view.findViewById(R.id.text);
			
			// If the views exists, assign text to it.
			if(title != null) {
				title.setText(n.getTitle());
			}
			
			if(text != null) {
				text.setText(n.getText());
			}
		}
		
		return view;
	}

}
