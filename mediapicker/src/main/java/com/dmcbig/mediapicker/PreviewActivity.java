package com.dmcbig.mediapicker;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dmcbig.mediapicker.entity.Folder;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.view.PreviewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmcBig on 2017/8/9.
 */

public class PreviewActivity extends AppCompatActivity implements View.OnClickListener{

    Button done;
    ImageView check_image;
    ViewPager viewpager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_main);
        findViewById(R.id.btn_back).setOnClickListener(this);
        check_image=(ImageView) findViewById(R.id.check_image);
        check_image.setOnClickListener(this);
        done=(Button) findViewById(R.id.done);
        done.setOnClickListener(this);
        viewpager=(ViewPager) findViewById(R.id.viewpager);
        ArrayList<Media> select=getIntent().getParcelableArrayListExtra(PickerConfig.DEFAULT_SELECTED_LIST);
        setView(select);
    }

    void setView(ArrayList<Media> list){
        ArrayList<Fragment> fragmentArrayList=new ArrayList<>();

        AdapterFragment adapterFragment=new AdapterFragment(getSupportFragmentManager(),fragmentArrayList);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_back){
            setResult(PickerConfig.RESULT_UPDATE_CODE);
            finish();
        }else if(id==R.id.done){
            setResult(PickerConfig.RESULT_CODE);
            finish();
        }else if(id==R.id.check_image){

        }
    }

    public class AdapterFragment extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;

        public AdapterFragment(FragmentManager fm, List<Fragment> mFragments) {
            super(fm);
            this.mFragments = mFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
