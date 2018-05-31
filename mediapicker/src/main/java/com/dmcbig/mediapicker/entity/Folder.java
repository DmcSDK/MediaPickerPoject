package com.dmcbig.mediapicker.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by dmcBig on 2017/7/4.
 */

public class Folder implements Parcelable {

    public String name;

    public int count;

    ArrayList<Media> medias = new ArrayList<>();

    public void addMedias(Media media) {
        medias.add(media);
    }

    public Folder(String name) {
        this.name = name;
    }

    public ArrayList<Media> getMedias() {
        return this.medias;
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


    protected Folder(Parcel in) {
        this.name = in.readString();
        this.count = in.readInt();
        this.medias = in.createTypedArrayList(Media.CREATOR);
    }

    public static final Parcelable.Creator<Folder> CREATOR = new Parcelable.Creator<Folder>() {
        @Override
        public Folder createFromParcel(Parcel source) {
            return new Folder(source);
        }

        @Override
        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };
}
