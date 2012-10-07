package com.plingnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private final Context mContext;
	
	public final static int[] imageResIds = new int[]{
		R.drawable.category_banking, R.drawable.category_chat, R.drawable.category_fun,
		R.drawable.category_lunch, R.drawable.category_meeting, R.drawable.category_shop,
		R.drawable.category_write
	};

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
		return imageResIds[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  View v;

		  if (convertView == null) {
		    v = LayoutInflater.from(mContext).inflate(R.layout.fragment_imagedetail,null);
		    v.setLayoutParams(new GridView.LayoutParams(200,200));
		  }
		  else {
		    v = convertView;
		  }

		  ImageView imageview = (ImageView)v.findViewById(R.id.gridview);
		  imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
		  imageview.setPadding(6, 6, 6, 6);

		  return v;
		}
}
