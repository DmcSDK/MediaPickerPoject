package com.dmcbig.mediapicker.data;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.R;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.entity.MediaDir;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.path;


/**
 * Created by dmcBig on 2017/6/9.
 */

public class MediaLoader implements LoaderManager.LoaderCallbacks{
    String[] MEDIA_PROJECTION = {
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns._ID ,
            MediaStore.Files.FileColumns.PARENT};

    Context mContext;
    DataCallback mLoader;
    public MediaLoader(Context context, DataCallback loader){
        this.mContext=context;
        this.mLoader=loader;
    }

    @Override
    public Loader onCreateLoader(int picker_type, Bundle bundle) {
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        Uri queryUri = MediaStore.Files.getContentUri("external");
        CursorLoader cursorLoader = new CursorLoader(
                mContext,
                queryUri,
                MEDIA_PROJECTION,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {
        ArrayList<MediaDir> mediaDirs=new ArrayList<>();
        MediaDir allMediaDir=new MediaDir(mContext.getResources().getString(R.string.all_dir_name));
        mediaDirs.add(allMediaDir);
        MediaDir allVideoDir=new MediaDir(mContext.getResources().getString(R.string.video_dir_name));
        mediaDirs.add(allVideoDir);
        Cursor cursor=(Cursor) o;
        while (cursor.moveToNext()){
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME));
            long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED));
            int mediaType = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE));
            double size= cursor.getDouble(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE));
            if (size < 1) continue;
            int id= cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID));
            String dirName=getParent(path);

            Media media=new Media(path,name,dateTime,mediaType,size,id,dirName);
            allMediaDir.addMedias(media);
            if(mediaType==3){allVideoDir.addMedias(media);Log.e(PickerConfig.LOG_TAG,path);}

            int index=hasDir(mediaDirs,dirName);
            if(index!=-1){
                mediaDirs.get(index).addMedias(media);
            }else{
                MediaDir mediaDir=new MediaDir(dirName);
                mediaDir.addMedias(media);
                mediaDirs.add(mediaDir);
            }
        }
        mLoader.onData(mediaDirs);
    }

    public String getParent(String path) {
        String sp[]=path.split("/");
        return sp[sp.length-2];
    }

    public int hasDir(ArrayList<MediaDir> mediaDirs,String dirName){
        for(int i=0;i<mediaDirs.size();i++){
            MediaDir mediaDir=mediaDirs.get(i);
            if( mediaDir.name.equals(dirName)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
