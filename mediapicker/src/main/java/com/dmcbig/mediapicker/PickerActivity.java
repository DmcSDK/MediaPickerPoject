package com.dmcbig.mediapicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dmcbig.mediapicker.data.DataCallback;
import com.dmcbig.mediapicker.data.MediaLoader;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.entity.MediaDir;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by dmcBig on 2017/6/9.
 */

public class PickerActivity extends Activity implements DataCallback {

    Intent argsIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        argsIntent=getIntent();
        getMediaData(PickerConfig.PICKER_IMAGE);
    }

    void getMediaData(int type){
        getLoaderManager().initLoader(type,null,new MediaLoader(this,this));
    }

    @Override
    public void onData( ArrayList<MediaDir> list) {
        for(MediaDir mediaDir:list){
            Log.e("test","数组的："+mediaDir.name);
        }
    }
}
