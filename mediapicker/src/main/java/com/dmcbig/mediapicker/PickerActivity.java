package com.dmcbig.mediapicker;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmcbig.mediapicker.adapter.FolderAdapter;
import com.dmcbig.mediapicker.adapter.MediaGridAdapter;
import com.dmcbig.mediapicker.adapter.SpacingDecoration;
import com.dmcbig.mediapicker.data.DataCallback;
import com.dmcbig.mediapicker.data.ImageLoader;
import com.dmcbig.mediapicker.data.MediaLoader;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.entity.Folder;
import com.dmcbig.mediapicker.utils.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by dmcBig on 2017/6/9.
 */

public class PickerActivity extends Activity implements DataCallback ,View.OnClickListener{

    Intent argsIntent;
    RecyclerView recyclerView;
    Button done,category_btn,preview;
    MediaGridAdapter gridAdapter;
    ListPopupWindow mFolderPopupWindow;
    private FolderAdapter mFolderAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        argsIntent=getIntent();
        setContentView(R.layout.main);
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        findViewById(R.id.btn_back).setOnClickListener(this);
        ((TextView)findViewById(R.id.bar_title)).setText(argsIntent.getIntExtra(PickerConfig.SELECT_MODE,101)==101?getResources().getString(R.string.select_title):getResources().getString(R.string.select_image_title));
        done=(Button) findViewById(R.id.done);
        category_btn=(Button) findViewById(R.id.category_btn);
        preview=(Button) findViewById(R.id.preview);
        done.setOnClickListener(this);
        category_btn.setOnClickListener(this);
        //get view end
        createAdapter();
        createFolderAdapter();
        getMediaData(argsIntent.getIntExtra(PickerConfig.SELECT_MODE,101));
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
        ArrayList<Media> select=argsIntent.getParcelableArrayListExtra(PickerConfig.DEFAULT_SELECTED_LIST);
        int maxSelect=argsIntent.getIntExtra(PickerConfig.MAX_SELECT_COUNT,PickerConfig.DEFAULT_SELECTED_MAX_COUNT);
        int maxSize=argsIntent.getIntExtra(PickerConfig.MAX_SELECT_SIZE,PickerConfig.DEFAULT_SELECTED_MAX_SIZE);
        gridAdapter =new  MediaGridAdapter(medias,this,select,maxSelect,maxSize);
        recyclerView.setAdapter(gridAdapter);
    }

    void createFolderAdapter(){
        ArrayList<Folder> folders=new ArrayList<>();
        mFolderAdapter=new FolderAdapter(folders,this);
        mFolderPopupWindow = new ListPopupWindow(this);
        mFolderPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mFolderPopupWindow.setAdapter(mFolderAdapter);
        mFolderPopupWindow.setHeight((int)(ScreenUtils.getScreenHeight(this)*0.6));
        mFolderPopupWindow.setAnchorView(findViewById(R.id.footer));
        mFolderPopupWindow.setModal(true);
        mFolderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFolderAdapter.setSelectIndex(position);
                category_btn.setText(mFolderAdapter.getItem(position).name);
                gridAdapter.updateAdapter(mFolderAdapter.getSelectMedias());
                mFolderPopupWindow.dismiss();
            }
        });
    }

    void getMediaData(int type){
        if(type==PickerConfig.PICKER_IMAGE_VIDEO){
            getLoaderManager().initLoader(type,null,new MediaLoader(this,this));
        }else{
            getLoaderManager().initLoader(type,null,new ImageLoader(this,this));
        }
    }

    @Override
    public void onData( ArrayList<Folder> list) {
        setView(list);
        category_btn.setText(list.get(0).name);
        mFolderAdapter.updateAdapter(list);
    }

    void setView(ArrayList<Folder> list){
        final int max=argsIntent.getIntExtra(PickerConfig.MAX_SELECT_COUNT,PickerConfig.DEFAULT_SELECTED_MAX_COUNT);
        gridAdapter.updateAdapter(list.get(0).getMedias());
        gridAdapter.setOnItemClickListener(new MediaGridAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Media data,ArrayList<Media> selectMedias) {
                done.setText(getString(R.string.done)+"("+gridAdapter.getSelectMedias().size()+"/"+max+")");
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_back){
            finish();
        }else if(id==R.id.category_btn){
            if(mFolderPopupWindow.isShowing()){
                mFolderPopupWindow.dismiss();
            }else{
                mFolderPopupWindow.show();
            }
        }else if(id==R.id.done){
            Intent intent=new Intent();
            intent.putParcelableArrayListExtra("selects",gridAdapter.getSelectMedias());
            setResult(200,intent);
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
