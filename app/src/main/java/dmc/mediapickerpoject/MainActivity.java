package dmc.mediapickerpoject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.TakePhotoActivity;
import com.dmcbig.mediapicker.entity.Media;

import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=(ImageView) findViewById(R.id.immmgg);
        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });

        findViewById(R.id.take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent =new Intent(MainActivity.this, TakePhotoActivity.class); //Take a photo with a camera
            MainActivity.this.startActivityForResult(intent,200);
            }
        });
    }

    ArrayList<Media> select;
    void go(){
        Intent intent =new Intent(MainActivity.this, PickerActivity.class);
        intent.putExtra(PickerConfig.SELECT_MODE,PickerConfig.PICKER_IMAGE_VIDEO);//default image and video (Optional)
        long maxSize=188743680L;//long long long
        intent.putExtra(PickerConfig.MAX_SELECT_SIZE,maxSize); //default 180MB (Optional)
        intent.putExtra(PickerConfig.MAX_SELECT_COUNT,15);  //default 40 (Optional)
        intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST,select); // (Optional)
        MainActivity.this.startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200&&resultCode==PickerConfig.RESULT_CODE){
            select=data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
            Log.i("select","select.size"+select.size());
            for(Media media:select){
                Log.i("media",media.path);
                Log.e("media","s:"+media.size);
                imageView.setImageURI(Uri.parse(media.path));
            }
        }
    }
}
