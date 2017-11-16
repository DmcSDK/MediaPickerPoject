package com.dmcbig.mediapicker.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dmcBig on 2017/7/4.
 */

public class Media implements Parcelable {
    public String path;
    public String name;
    public long time;
    public int mediaType;
    public  long size;
    public  int id;
    public  String parentDir;
    public Media(String path, String name, long time, int mediaType,long size,int id,String parentDir){
        this.path = path;
        this.name = name;
        this.time = time;
        this.mediaType=mediaType;
        this.size=size;
        this.id=id;
        this.parentDir=parentDir;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.name);
        dest.writeLong(this.time);
        dest.writeInt(this.mediaType);
        dest.writeLong(this.size);
        dest.writeInt(this.id);
        dest.writeString(this.parentDir);
    }

    protected Media(Parcel in) {
        this.path = in.readString();
        this.name = in.readString();
        this.time = in.readLong();
        this.mediaType = in.readInt();
        this.size = in.readLong();
        this.id = in.readInt();
        this.parentDir = in.readString();
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}
