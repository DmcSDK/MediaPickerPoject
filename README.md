# MediaPickerProject
[![](https://jitpack.io/v/Achenglove/MediaPickerPoject.svg)](https://jitpack.io/#Achenglove/MediaPickerPoject)
仿微信视频图片选择器，代码撸的非常简洁清爽好改，支持cordova调用。

同类的库很多很多，但都过于强大而笨重，代码不容易在特别短的时间里面改动，所以我创建了这个简单而轻量的库。

非常欢迎有空的小伙伴一同 pull requests 改进优化项目（特别是UI，我想再增加几种UI风格样式，比如模仿知乎，模仿Twitter,或纯material design 风格）。

How do I use?
-------------------
[Cordova版](https://github.com/DmcSDK/cordova-plugin-mediaPicker) : https://github.com/DmcSDK/cordova-plugin-mediaPicker 

[IOS版](https://github.com/DmcSDK/IOSMediaPicker) : https://github.com/DmcSDK/IOSMediaPicker

use Gradle:

```gradle
repositories {
    mavenCentral() // jcenter() works as well because it pulls from Maven Central
}

dependencies {
    compile 'com.dmcBig:mediapicker:+'
    
    或者implementation 'com.github.Achenglove:MediaPickerPoject:v1.0.1'
}
```

code:
```
    ArrayList<Media> defaultSelect;
    void go(){
        Intent intent =new Intent(MainActivity.this, PickerActivity.class);
        intent.putExtra(PickerConfig.SELECT_MODE,PickerConfig.PICKER_IMAGE_VIDEO);//default image and video (Optional)
        long maxSize=188743680L;//long long long long类型
        intent.putExtra(PickerConfig.MAX_SELECT_SIZE,maxSize); //default 180MB (Optional) 
        intent.putExtra(PickerConfig.MAX_SELECT_COUNT,15);  //default 40 (Optional)
        intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST,defaultSelect); //(Optional)默认选中的照片
        MainActivity.this.startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200&&resultCode==PickerConfig.RESULT_CODE){
            select=data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
        }
    }
```    

# Screenshots
![](https://github.com/dmcBig/MediaPickerPoject/blob/master/Screenshots/Screenshots1.png)
![](https://github.com/dmcBig/MediaPickerPoject/blob/master/Screenshots/Screenshots2.png)
![](https://github.com/dmcBig/MediaPickerPoject/blob/master/Screenshots/Screenshots3.png)
![](https://github.com/dmcBig/MediaPickerPoject/blob/master/Screenshots/Screenshots4.png)
![](https://github.com/dmcBig/MediaPickerPoject/blob/master/Screenshots/Screenshots5.png)
![](https://github.com/dmcBig/MediaPickerPoject/blob/master/Screenshots/Screenshots6.png)
