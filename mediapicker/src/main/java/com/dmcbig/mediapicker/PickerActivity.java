package com.dmcbig.mediapicker;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dmcbig.mediapicker.adapter.MediaGridAdapter;
import com.dmcbig.mediapicker.adapter.SpacingDecoration;
import com.dmcbig.mediapicker.data.DataCallback;
import com.dmcbig.mediapicker.data.MediaLoader;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.entity.Folder;

import java.util.ArrayList;

/**
 * Created by dmcBig on 2017/6/9.
 */

public class PickerActivity extends Activity implements DataCallback {

    Intent argsIntent;
    RecyclerView recyclerView;
    MediaGridAdapter gridAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        argsIntent=getIntent();
        createAdapter();
        getMediaData(PickerConfig.PICKER_IMAGE);
    }

    void createAdapter(){
        //创建默认的线性LayoutManager
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpacingDecoration(3));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        ArrayList<Media> medias =new ArrayList<>();
        gridAdapter =new  MediaGridAdapter(medias,this);
        recyclerView.setAdapter(gridAdapter);
        gridAdapter.setOnItemClickListener(new MediaGridAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Media data) {

            }
        });
    }

    void getMediaData(int type){
        getLoaderManager().initLoader(type,null,new MediaLoader(this,this));
    }

    @Override
    public void onData( ArrayList<Folder> list) {
        setView(list);
//        for(Folder m:list){
//         //   Log.e(PickerConfig.LOG_TAG,m.getMedias().get(0).path);
//        }
    }

    void setView(ArrayList<Folder> list){

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
    }
}
