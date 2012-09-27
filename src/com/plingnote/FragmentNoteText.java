package com.plingnote;



import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


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
	/**
	 * Inflates the layout for this fragment and sets the height of the noteText in the layout depending on the screens measures and orientation.
	 */

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_notetext, container, false);
		EditText noteText = (EditText)view.findViewById(R.id.notetext);
		DisplayMetrics metric = view.getContext().getResources().getDisplayMetrics(); 

		int height =metric.heightPixels;
		int widht = metric.widthPixels;
		
		getResources().getConfiguration();
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			int h = height/10;
			noteText.setLayoutParams(new LinearLayout.LayoutParams(widht,h*6));
		}
		else
		{
			int h = height/15;
			noteText.setLayoutParams(new LinearLayout.LayoutParams(widht,h*11));
		}	
			
			return view;
	}

	/**
	 *Set the view noteText text to the latest inserted note's text if is during editing.
	 *Sets the curser of the view noteText to the bottom of the text.
	 */
	@Override
	public void onStart() {
		super.onStart();
	
		if(isEditing)
		{
			EditText noteText = (EditText) view.findViewById(R.id.notetext);
			int size = DatabaseHandler.getInstance(getActivity()).getNoteList().size();	
			String txt = (DatabaseHandler.getInstance(getActivity()).getNoteList().get(size-1).getText());	
			noteText.setText(""); //The cursor position will be saved even if turning the phone horizontal. Doesn't work with just setText or setSelection(noteText.getText().length()) if turning phone horizontal.
			noteText.append(txt);
		}
	}
	
	
	@Override
	public void onResume(){
		super.onResume();
	}

	/**
	 * Get the text from the view noteText. If it's during editing the note's value will be updated in the database. If not the note will be inserted in the database and the rowId will be saved. 
	 */
	@Override
	public void onPause (){
		super.onPause();
		EditText noteText = (EditText) view.findViewById(R.id.notetext);
		Editable eText = noteText.getText();		
		if(isEditing){
			DatabaseHandler.getInstance(getActivity().getBaseContext()).updateNote(rowId,getTitleofNoteText(), eText.toString(), new Location(0.0, 0.0));
		}
		else{
			DatabaseHandler.getInstance(getActivity().getBaseContext()).insertNote(getTitleofNoteText(), eText.toString(), new Location(0.0, 0.0));
			rowId = DatabaseHandler.getInstance(getActivity().getBaseContext()).getNoteList().get(DatabaseHandler.getInstance(getActivity().getBaseContext()).getNoteList().size()-1).getRowId();
		}
	}

	/**
	 * Find the first line of the text in notetext in the layout and returning it as a string
	 * @return
	 */
	public String getTitleofNoteText(){
		EditText noteText = (EditText) view.findViewById(R.id.notetext);
		String txt = noteText.getText().toString();
		String[] txtLines = txt.split("\n");
		return txtLines[0];
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
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
