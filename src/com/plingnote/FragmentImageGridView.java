package com.plingnote;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentImageGridView extends Fragment implements OnItemClickListener{

	public FragmentImageGridView(){}

	private Point imgSize;
	private DatabaseHandler db;
	private List<Note> notes = new ArrayList<Note>();

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null){
			return null;
		}
		db = DatabaseHandler.getInstance(getActivity());

		refreshNotes();

		final View grid = inflater.inflate(R.layout.fragment_imageview, container, false); 
		GridView g = (GridView) grid.findViewById(R.id.grid);
		g.setAdapter(new ImageAdapter(getActivity()));
		g.setOnItemClickListener(this);


		imgSize = new Point();
		int side = getActivity().getResources().getDisplayMetrics().widthPixels / 4 ;
		setImgSize(side,side);


		return grid;
	}


	public class ImageAdapter extends BaseAdapter {
		private final Context mContext;


		public ImageAdapter(Context context) {
			super();
			mContext = context;
		}

		@Override
		public int getCount() {
			return numberOfNotes();
		}

		@Override
		public Object getItem(int position) {
			return imageIds;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}


		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;


			if (convertView == null) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(200 ,200));
				imageView.setAdjustViewBounds(true);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

			} else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(imageIds[position]);


			return imageView;
		}

		/**
		 * stores all the used images.
		 */
		private int[] imageIds = new int[]{
				R.drawable.category_banking, R.drawable.category_chat, R.drawable.category_fun,
				R.drawable.category_lunch, R.drawable.category_meeting, R.drawable.category_shop,
				R.drawable.category_write, R.drawable.category_banking, R.drawable.category_chat, R.drawable.category_fun,
				R.drawable.category_lunch, R.drawable.category_meeting, R.drawable.category_shop,
				R.drawable.category_write, R.drawable.category_banking, R.drawable.category_chat, R.drawable.category_fun,
				R.drawable.category_lunch, R.drawable.category_meeting, R.drawable.category_shop,
				R.drawable.category_write
		};

	}

	/**
	 * Deletes all the notes in the list notes and then adds freshly from the database.
	 */
	public void refreshNotes(){

		clearNotes();

		for(Note n : db.getNoteList()){
			addNote(n);
		}

	}
	/**
	 * adds a note to the list notes.
	 * @param note adds this object to the list.
	 */
	public void addNote(Note note){
		notes.add(note);
	}

	/**
	 * Deletes all notes in the list notes.
	 */
	public void clearNotes(){
		notes.clear();
	}

	/**
	 * @return returns an int with the size of the list notes.
	 */
	public int numberOfNotes(){
		return notes.size();
	}

	@Override
	public void onItemClick(AdapterView parent, View v, int position, long id) {
		Intent editNote = new Intent(getActivity(), ActivityNote.class);


		// Get the row ID of the clicked note.
		int noteId = notes.get(position).getId();
		editNote.putExtra(IntentExtra.id.toString(), noteId);

		startActivity(editNote);
	}

	public String[] getNoteTitles(){
		return null;
	}

}
