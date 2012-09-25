package com.plingnote;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NoteAdapter extends ArrayAdapter<Note> {

	private List<Note> notes;
	
	public NoteAdapter(Context context, int textViewResourceId, List<Note> notes) {
		super(context, textViewResourceId, notes);
		this.notes = notes;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item, null);
		}
		
		Note n = notes.get(position);
		
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView text = (TextView) view.findViewById(R.id.text);
		
		if(n != null) {
			title.setText(n.getTitle());
			text.setText(n.getText());
		}
		
		return view;
	}

}
