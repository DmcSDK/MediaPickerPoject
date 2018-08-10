package com.dmcbig.mediapicker;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.view.PreviewFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by dmcBig on 2017/8/9.
 */

public class PreviewActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{

    Button done;
    LinearLayout check_layout;
    ImageView check_image;
    ViewPager viewpager;
    TextView bar_title;
    View top,bottom;
    ArrayList<Media> preRawList, selects;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_main);
        findViewById(R.id.btn_back).setOnClickListener(this);
        check_image = (ImageView) findViewById(R.id.check_image);
        check_layout = (LinearLayout) findViewById(R.id.check_layout);
        check_layout.setOnClickListener(this);
        bar_title = (TextView) findViewById(R.id.bar_title);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(this);
        top= findViewById(R.id.top);
        bottom= findViewById(R.id.bottom);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        preRawList = getIntent().getParcelableArrayListExtra(PickerConfig.PRE_RAW_LIST);
        selects = new ArrayList<>();
        selects.addAll(preRawList);
        setView(preRawList);
    }

    void setView(ArrayList<Media> default_list) {
        setDoneView(default_list.size());
        bar_title.setText(1 + "/" + preRawList.size());
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        for (Media media : default_list) {
            fragmentArrayList.add(PreviewFragment.newInstance(media, ""));
        }
        AdapterFragment adapterFragment = new AdapterFragment(getSupportFragmentManager(), fragmentArrayList);
        viewpager.setAdapter(adapterFragment);
        viewpager.addOnPageChangeListener(this);
    }

    void setDoneView(int num1) {
        done.setText(getString(R.string.done) + "(" + num1 + "/" + getIntent().getIntExtra(PickerConfig.MAX_SELECT_COUNT, PickerConfig.DEFAULT_SELECTED_MAX_COUNT) + ")");
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back) {
            done(selects, PickerConfig.RESULT_UPDATE_CODE);
        } else if (id == R.id.done) {
            done(selects, PickerConfig.RESULT_CODE);
        } else if (id == R.id.check_layout) {
            Media media = preRawList.get(viewpager.getCurrentItem());
            int select = isSelect(media, selects);
            if (select < 0) {
                check_image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_selected));
                selects.add(media);
            } else {
                check_image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_unselected));
                selects.remove(select);
            }
            setDoneView(selects.size());
        }
    }

    /**
     * @param media
     * @return 大于等于0 就是表示以选择，返回的是在selectMedias中的下标
     */
    public int isSelect(Media media, ArrayList<Media> list) {
        int is = -1;
        if (list.size() <= 0) {
            return is;
        }
        for (int i = 0; i < list.size(); i++) {
            Media m = list.get(i);
            if (m.path.equals(media.path)) {
                is = i;
                break;
            }
        }
        return is;
    }

    public void done(ArrayList<Media> list, int code) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(PickerConfig.EXTRA_RESULT, list);
        setResult(code, intent);
        finish();
    }

    public void setBarStatus(){
        Log.e("setBarStatus","setBarStatus");
        if(top.getVisibility()==View.VISIBLE){
            top.setVisibility(View.GONE);
            bottom.setVisibility(View.GONE);
        }else{
            top.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onBackPressed() {
        done(selects, PickerConfig.RESULT_UPDATE_CODE);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        bar_title.setText((position + 1) + "/" + preRawList.size());
        check_image.setImageDrawable(isSelect(preRawList.get(position), selects) < 0 ? ContextCompat.getDrawable(this, R.drawable.btn_unselected) : ContextCompat.getDrawable(this, R.drawable.btn_selected));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
