package com.dmcbig.mediapicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by dmcBig on 2017/8/9.
 */

public class PreviewActivity extends AppCompatActivity implements View.OnClickListener{

    Button done;
    ImageView check_image;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_main);
        findViewById(R.id.btn_back).setOnClickListener(this);
        check_image=(ImageView) findViewById(R.id.check_image);
        check_image.setOnClickListener(this);
        done=(Button) findViewById(R.id.done);
        done.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_back){
            setResult(PickerConfig.RESULT_CODE);
            finish();
        }else if(id==R.id.done){

        }else if(id==R.id.check_image){

        }
    }
}
