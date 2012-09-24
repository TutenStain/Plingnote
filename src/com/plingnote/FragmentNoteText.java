package com.plingnote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * 
 * A note text fragment 
 * @author Julia Gustafsson
 *
 */
public class FragmentNoteText extends Fragment {

	private View view;
	private boolean isEditing = false;
	private int rowId;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_notetext, container, false);
		return view;
	}

	/**
	 *Set the view noteText text to the latest inserted note's text if is during editing.
	 */
	@Override
	public void onStart() {
		super.onStart(); 
		if(isEditing)
		{
			int size = DatabaseHandler.getInstance(getActivity()).getNoteList().size();
			EditText noteText = (EditText) view.findViewById(R.id.notetext);
			noteText.setText(DatabaseHandler.getInstance(getActivity()).getNoteList().get(size-1).getText());	
		}
	}

	/**
	 * Get the text from the view noteText. If it's during editing the note's value will be updated in the database. If not the note will be inserted in the database and the rowId will be saved. 
	 */
	@Override
	public void onPause (){
		super.onPause();
		EditText noteText = (EditText) view.findViewById(R.id.notetext);
		Editable eText = noteText.getText();		
		if(isEditing)
		{
			DatabaseHandler.getInstance(getActivity().getBaseContext()).updateNote(rowId,"title", eText.toString(), new Location(0.0, 0.0));
		}
		else
		{
			DatabaseHandler.getInstance(getActivity().getBaseContext()).insertNote("title", eText.toString(), new Location(0.0, 0.0));
			rowId = DatabaseHandler.getInstance(getActivity().getBaseContext()).getNoteList().get(DatabaseHandler.getInstance(getActivity().getBaseContext()).getNoteList().size()-1).getRowId();
		}
	}

	/**
	 * If savedInstanceState isn't null the method will set isEditing an rowId to new values 
	 */

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if(savedInstanceState != null)
		{
			isEditing =   savedInstanceState.getBoolean("IsEditing");
			rowId =  savedInstanceState.getInt("rowId");

		}

	}

	/**
	 * Save boolean isEditing and int rowId.
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putBoolean("IsEditing", true);
		savedInstanceState.putInt("rowId", rowId);
	}	
}
