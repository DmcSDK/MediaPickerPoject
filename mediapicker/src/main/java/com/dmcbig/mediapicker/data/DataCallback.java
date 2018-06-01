package com.dmcbig.mediapicker.data;

import com.dmcbig.mediapicker.entity.Folder;

import java.util.ArrayList;


/**
 * Created by dmcBig on 2017/7/3.
 */

public interface DataCallback {


    void onData(ArrayList<Folder> list);

}
