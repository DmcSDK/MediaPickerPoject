package com.dmcbig.mediapicker.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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
    ArrayList<Media> selectMedias=new ArrayList<>();

    public  MediaGridAdapter( ArrayList<Media> list,Context context){
        this.medias=list;
        this.context=context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView media_image,check_image;
        public View mask_view;
        public TextView textView_size;
        public RelativeLayout video_info;
        public MyViewHolder(View view){
            super(view);
            media_image = (ImageView) view.findViewById(R.id.media_image);
            check_image = (ImageView) view.findViewById(R.id.check_image);
            mask_view =  view.findViewById(R.id.mask_view);
            video_info = (RelativeLayout) view.findViewById(R.id.video_info);
            textView_size = (TextView) view.findViewById(R.id.textView_size);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_view_item,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Media media=medias.get(position);
        Uri mediaUri = Uri.parse("file://" + media.path);
        Glide.with(context)
                .load(mediaUri)
                .into(holder.media_image);
        if(media.mediaType==3){
            holder.video_info.setVisibility(View.VISIBLE);
            holder.textView_size.setText(fileUtils.getSizeByUnit(media.size));
        }else{
            holder.video_info.setVisibility(View.INVISIBLE);
        }
        holder.mask_view.setVisibility(isSelect(media)>=0?View.VISIBLE:View.INVISIBLE);
        holder.media_image.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 if(mOnItemClickListener!=null){
//                     mOnItemClickListener.onItemClick(v,media );
//                 }
//                 holder.mask_view.setVisibility(isSelect(media)>=0?View.INVISIBLE:View.VISIBLE);
//                 setSelectMedias(media);
             }
         });
    }

    public void setSelectMedias(Media media){
        int index=isSelect(media);
        if(index==-1){
            selectMedias.add(media);
        }else{
            selectMedias.remove(index);
        }
    }

    public int isSelect(Media media){
        int is=-1;
        if(selectMedias.size()<=0){
            return is;
        }
        for(int i=0;i<selectMedias.size();i++){
            Media m=selectMedias.get(i);
            if(m.path.equals(media.path)){
                is=i;
                break;
            }
        }
        return is;
    }

    public void updateAdapter( ArrayList<Media> list){
        this.medias=list;
        notifyDataSetChanged();
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public ArrayList<Media> getSelectMedias(){
        return  selectMedias;
    }
    @Override
    public int getItemCount() {
        return medias.size();
    }

    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Media data ,ArrayList<Media> selectMedias);
    }
}
