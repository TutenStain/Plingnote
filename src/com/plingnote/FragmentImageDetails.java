package com.plingnote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentImageDetails extends Fragment{
	
	private int imgNbr;
	private ImageView imgView;
	
    static FragmentImageDetails newInstance(int imageNum) {
        final FragmentImageDetails f = new FragmentImageDetails();
        final Bundle args = new Bundle();
        args.putInt("resId", imageNum);
        f.setArguments(args);
        return f;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // image_detail_fragment.xml contains just an ImageView
        final View v = inflater.inflate(R.layout.fragment_imagedetail, container, false);
        imgView = (ImageView) v.findViewById(R.id.imageView);
        return v;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgNbr = getArguments() != null ? getArguments().getInt("resId") : -1;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int resId = FragmentImageGridView.imageResIds[imgNbr];
        imgView.setImageResource(resId); // Load image into ImageView
    }


}
