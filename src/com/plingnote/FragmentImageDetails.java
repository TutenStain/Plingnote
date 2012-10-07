package com.plingnote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentImageDetails extends Fragment{
    private static final String IMAGE_DATA_EXTRA = "resId";
    private int mImageNum;
    private ImageView mImageView;

    static FragmentImageDetails newInstance(int imageNum) {
        final FragmentImageDetails f = new FragmentImageDetails();
        final Bundle args = new Bundle();
        args.putInt(IMAGE_DATA_EXTRA, imageNum);
        f.setArguments(args);
        return f;
    }

    public FragmentImageDetails() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageNum = getArguments() != null ? getArguments().getInt(IMAGE_DATA_EXTRA) : -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_imagedetail, container, false);
        mImageView = (ImageView) v.findViewById(R.id.img);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ActivityMain.class.isInstance(getActivity())) {
            final int resId = ImageAdapter.imageResIds[mImageNum];
            // Call out to ImageDetailActivity to load the bitmap in a background thread
        }
    }
    
}