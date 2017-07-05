package com.dmcbig.mediapicker.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by dmcBig on 2017/7/4.
 */

public class MediaDir implements Parcelable {

    public String name;

    public  int count;

    ArrayList<Media> medias=new ArrayList<>();

    public void addMedias(Media media){
        medias.add(media);
    }

    public MediaDir(String name) {
        this.name=name;
    }

    public ArrayList<Media> getMedias(){
        return  this.medias;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.count);
        dest.writeTypedList(this.medias);
    }


    protected MediaDir(Parcel in) {
        this.name = in.readString();
        this.count = in.readInt();
        this.medias = in.createTypedArrayList(Media.CREATOR);
    }

    public static final Parcelable.Creator<MediaDir> CREATOR = new Parcelable.Creator<MediaDir>() {
        @Override
        public MediaDir createFromParcel(Parcel source) {
            return new MediaDir(source);
        }

        @Override
        public MediaDir[] newArray(int size) {
            return new MediaDir[size];
        }
    };
}
