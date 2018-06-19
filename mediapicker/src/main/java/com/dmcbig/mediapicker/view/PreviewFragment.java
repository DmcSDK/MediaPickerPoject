package com.dmcbig.mediapicker.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dmcbig.mediapicker.PreviewActivity;
import com.dmcbig.mediapicker.R;
import com.dmcbig.mediapicker.entity.Media;

import java.io.File;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by dmcBig on 2017/8/16.
 */

public class PreviewFragment extends Fragment {
    private PhotoView mPhotoView;
    ImageView play_view;
 //   private PhotoViewAttacher mAttacher;

    public static PreviewFragment newInstance(Media media, String label) {
        PreviewFragment f = new PreviewFragment();
        Bundle b = new Bundle();
        b.putParcelable("media", media);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.preview_fragment_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Media media = getArguments().getParcelable("media");
        play_view = (ImageView) view.findViewById(R.id.play_view);
        mPhotoView = (PhotoView) view.findViewById(R.id.photoview);
        mPhotoView.setMaximumScale(5);
        mPhotoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                PreviewActivity previewActivity=  (PreviewActivity)getActivity();
                previewActivity.setBarStatus();
            }
        });
        setPlayView(media);
        Glide.with(getActivity())
                .load(media.path)
                .into(mPhotoView);
    }

    void setPlayView(final Media media) {
        if (media.mediaType == 3) {
            play_view.setVisibility(View.VISIBLE);
            play_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(getUri(media.path), "video/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    if (isIntentAvailable(getContext(), intent)) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(),getString(R.string.cant_play_video), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    Uri getUri(String path){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          return   FileProvider.getUriForFile(getActivity(), getActivity().getPackageName()+ ".dmc", new File(path));
        }else {
          return Uri.fromFile(new File(path));
        }
    }

    /**
     * 检查是否有可以处理的程序
     *
     * @param context
     * @param intent
     * @return
     */
    private boolean isIntentAvailable(Context context, Intent intent) {
        List resolves = context.getPackageManager().queryIntentActivities(intent, 0);
        return resolves.size() > 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}