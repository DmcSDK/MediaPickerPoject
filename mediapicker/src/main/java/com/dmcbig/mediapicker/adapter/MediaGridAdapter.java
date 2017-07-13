package com.dmcbig.mediapicker.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.VideoBitmapDecoder;
import com.dmcbig.mediapicker.R;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.utils.FileUtils;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by dmcBig on 2017/7/5.
 */

public class MediaGridAdapter  extends RecyclerView.Adapter<MediaGridAdapter.MyViewHolder>{

    ArrayList<Media> medias;
    Context context;
    FileUtils fileUtils=new FileUtils();
    public  MediaGridAdapter( ArrayList<Media> list,Context context){
        this.medias=list;
        this.context=context;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView media_image,check_image;
        public TextView textView_size;
        public RelativeLayout video_info;
        public MyViewHolder(View view){
            super(view);
            media_image = (ImageView) view.findViewById(R.id.media_image);
            check_image = (ImageView) view.findViewById(R.id.check_image);
            video_info = (RelativeLayout) view.findViewById(R.id.video_info);
            textView_size = (TextView) view.findViewById(R.id.textView_size);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener!=null){
                mOnItemClickListener.onItemClick(v, medias.get(getAdapterPosition()));
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_view_item,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

            Media media=medias.get(position);
            if(media.mediaType==3){
                holder.video_info.setVisibility(View.VISIBLE);
                holder.textView_size.setText(fileUtils.getSizeByUnit(media.size));
            }else{
                holder.video_info.setVisibility(View.INVISIBLE);
            }

            Uri mediaUri = Uri.parse("file://" + media.path);
            Glide.with(context)
                    .load(mediaUri)
                    .into(holder.media_image);


    }

    @Override
    public int getItemCount() {
        return medias.size();
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Media data);
    }
}
