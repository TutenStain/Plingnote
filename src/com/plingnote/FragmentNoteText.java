package com.plingnote;



import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;


/**
 * 
 * A note text fragment 
 * @author Julia Gustafsson
 *
 */
public class FragmentNoteText extends Fragment {

	private View view;
	private boolean isExisting = false;
	private int rowId;

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
		return view;
	}
	
	/**
	 *Set the view noteText text to the latest inserted note's text if is during editing.
	 *Sets the curser of the view noteText to the bottom of the text.
	 */
	@Override
	public void onStart(){
		super.onStart();
		EditText noteText = (EditText)view.findViewById(R.id.notetext);
		Rect rec  =Utils.getScreenPixels(getActivity());
		int height =rec.height();
		int widht = rec.width();	
		getResources().getConfiguration();
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			int h = height/10;
			noteText.setLayoutParams(new LinearLayout.LayoutParams(widht,h*6));
		}
		else{
			int h = height/15;
			noteText.setLayoutParams(new LinearLayout.LayoutParams(widht,h*11));
		}	
		//If this class was opened with an intent or saved instances, the notetext will get the text from the database
		if(isExisting){
			noteText = (EditText) view.findViewById(R.id.notetext);
			String txt = (DatabaseHandler.getInstance(getActivity()).getNote(rowId).getTitle());
			txt = txt +(DatabaseHandler.getInstance(getActivity()).getNote(rowId).getText());	
			//The cursor position will be saved even if turning the phone horizontal. Doesn't work with just setText or setSelection(noteText.getText().length()) if turning phone horizontal.
			noteText.setText(""); 
			noteText.append(txt);
			noteText.invalidate(); 
		}
	}

	/**
	 * Get the text from the view noteText. If it's during editing the note's value will be updated in the database. If not the note will be inserted in the database and the rowId will be saved. 
	 */
	@Override
	public void onPause (){
		super.onPause();
		//If this class was opened with an intent or saved instance we are updating that note.
		if(isExisting){
			DatabaseHandler.getInstance(getActivity()).updateNote(rowId,getTitleofNoteText(), getTextofNoteText(), null,null,null);
		}
		//If this class not was opened with an intent o saved instance we are inserting the note in database.
		else{
			if(getTitleofNoteText().length() >0 || getTextofNoteText().length() > 0){
			DatabaseHandler.getInstance(getActivity()).insertNote(getTitleofNoteText(), getTextofNoteText(), null,null,null);
			rowId = DatabaseHandler.getInstance(getActivity()).getNoteList().get(DatabaseHandler.getInstance(getActivity()).getNoteList().size()-1).getRowId();
			isExisting=true;
			}
		}
	}

	/**
	 * Find the first line of the text in notetext in the layout and returning it as a string
	 * @return
	 */
	public String getTitleofNoteText(){
		EditText noteText = (EditText) view.findViewById(R.id.notetext);
		if(noteText.getText().toString().length()>0){
			String txt = noteText.getText().toString();
			String[] txtLines = txt.split("\n");
			return txtLines[0]+"\n";
		}else
			return "";
	}
	
	/**
	 * Find the text(without the title) in notetext in the layout  as a string
	 * @return
	 */
	public String getTextofNoteText(){
		EditText noteText = (EditText) view.findViewById(R.id.notetext);
		String txt = noteText.getText().toString();
		if(txt.length()-getTitleofNoteText().length() >0){
			String text =txt.substring(getTitleofNoteText().length(), txt.length());
			return text;
		}else
			return "";
	}
	
	/**
	 * If savedInstanceState isn't null the method will set isEditing an rowId to new values 
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		this.isExisting = true;
		try{
			this.rowId = bundle.getInt("rowId");
			return;
		}catch(Exception e){ 
			try{
				this.rowId = savedInstanceState.getInt("rowId");
			}catch(Exception el){
				this.isExisting = false;
			}
		}
	}

	/**
	 * Save boolean isEditing and int rowId.
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("rowId", rowId);
	}	
}
