package com.dmcbig.mediapicker;


import android.content.Intent;
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
import android.widget.TextView;

import com.dmcbig.mediapicker.entity.Folder;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.view.PreviewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmcBig on 2017/8/9.
 */

public class PreviewActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    Button done;
    ImageView check_image;
    ViewPager viewpager;
    TextView bar_title;
    ArrayList<Media> default_selects,selects;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_main);
        findViewById(R.id.btn_back).setOnClickListener(this);
        check_image=(ImageView) findViewById(R.id.check_image);
        check_image.setOnClickListener(this);
        bar_title=(TextView) findViewById(R.id.bar_title);
        done=(Button) findViewById(R.id.done);
        done.setOnClickListener(this);
        viewpager=(ViewPager) findViewById(R.id.viewpager);
        default_selects=getIntent().getParcelableArrayListExtra(PickerConfig.DEFAULT_SELECTED_LIST);
        selects=new ArrayList<>();
        selects.addAll(default_selects);
        setView(default_selects);
    }

    void setView(ArrayList<Media> default_list){
        setDoneView(default_list.size());
        bar_title.setText(1 + "/" + default_selects.size());
        ArrayList<Fragment> fragmentArrayList=new ArrayList<>();
        for(Media media:default_list){
            fragmentArrayList.add( PreviewFragment.newInstance(media,""));
        }
        AdapterFragment adapterFragment=new AdapterFragment(getSupportFragmentManager(),fragmentArrayList);
        viewpager.setAdapter(adapterFragment);
        viewpager.addOnPageChangeListener(this);
    }

    void setDoneView(int num1){
        done.setText(getString(R.string.done)+"("+num1+"/"+getIntent().getIntExtra(PickerConfig.MAX_SELECT_COUNT,PickerConfig.DEFAULT_SELECTED_MAX_COUNT)+")");
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_back){
            done(default_selects,PickerConfig.RESULT_UPDATE_CODE);
        }else if(id==R.id.done){
            done(default_selects,PickerConfig.RESULT_CODE);
        }else if(id==R.id.check_image){

        }
    }

    public void done(ArrayList<Media> default_selects,int code){
        Intent intent=new Intent();
        intent.putParcelableArrayListExtra(PickerConfig.EXTRA_RESULT,default_selects);
        setResult(code,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        done(default_selects,PickerConfig.RESULT_UPDATE_CODE);
        super.onBackPressed();
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        bar_title.setText((position + 1) + "/" + default_selects.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) { }
}
