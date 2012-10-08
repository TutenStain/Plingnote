package com.plingnote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class FragmentImageGridView extends Fragment implements AdapterView.OnItemClickListener {

	public FragmentImageGridView(){}

	private Point imgSize;
	private int mNum;
	private ImageAdapter imgA = new ImageAdapter(getActivity());
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNum = getArguments() != null ? getArguments().getInt("num") : 1;
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

		final View grid = inflater.inflate(R.layout.fragment_imageview, container, false); 
		GridView g = (GridView) grid.findViewById(R.id.grid);
		g.setAdapter(new ImageAdapter(getActivity()));

		imgSize = new Point();
		int side = getActivity().getResources().getDisplayMetrics().widthPixels / 4 ;
		setImgSize(side,side);
		return grid;
	}

	public void onItemClick(AdapterView parent, View v, int position, long id) {
		final Intent i = new Intent(getActivity(), ActivityNote.class);
		i.putExtra("resId", position);
		startActivity(i);
	}


	public class ImageAdapter extends BaseAdapter {
		private final Context mContext;


		public ImageAdapter(Context context) {
			super();
			mContext = context;
		}

		@Override
		public int getCount() {
			return imageResIds.length;
		}

		@Override
		public Object getItem(int position) {
			return imageResIds;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}


	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(100 ,100));
	            imageView.setAdjustViewBounds(true);
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(1,1,1,1);
	        } else {
	            imageView = (ImageView) convertView;
	        }

	        imageView.setImageResource(imageResIds[position]);

	        return imageView;
	    }
		
		
		private final int[] imageResIds = new int[]{
			R.drawable.category_banking, R.drawable.category_chat, R.drawable.category_fun,
			R.drawable.category_lunch, R.drawable.category_meeting, R.drawable.category_shop,
			R.drawable.category_write
		};
		
	}

}
