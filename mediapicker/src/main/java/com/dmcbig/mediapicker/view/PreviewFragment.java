package com.dmcbig.mediapicker.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dmcbig.mediapicker.R;
import com.dmcbig.mediapicker.entity.Media;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by dmcBig on 2017/8/16.
 */

public class PreviewFragment extends Fragment{
    private PhotoView mPhotoView;
    private PhotoViewAttacher mAttacher;
    public static PreviewFragment newInstance(Media media, String label) {
        PreviewFragment f = new PreviewFragment();
        Bundle b = new Bundle();
        b.putParcelable("media",media);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.preview_fragment_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mPhotoView = (PhotoView) view.findViewById(R.id.photoview);
        mAttacher = new PhotoViewAttacher(mPhotoView);
        mAttacher.setRotatable(true);
        mAttacher.setToRightAngle(true);
        mAttacher.update();
        Media media=getArguments().getParcelable("media");
        Glide.with(getActivity())
                .load(media.path)
                .into(mPhotoView);
    }

    @Override
    public void onDestroyView() {
        mAttacher.cleanup();
        super.onDestroyView();
    }
}
