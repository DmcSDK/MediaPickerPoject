# MediaPickerProject

仿微信视频图片选择器，代码撸的非常简洁好改，支持cordova调用，联系QQ：3451927565。

同类的库很多很多，但都过于强大而笨重，代码不容易在特别短的时间里面改动扩展，所以我创建了这个简单而轻量的库。

非常欢迎有空的小伙伴一同 pull requests 改进优化项目

特别是UI，我想再增加几种UI样式，比如模仿知乎图片选择UI风格，个人觉得知乎图片选择UI很清爽，如果有空的人可以加上后提交给我或者其他风格的也行，感谢。

怎么使用?
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
}
```

code:
```
    ArrayList<Media> select = new ArrayList<>();//装被选中的文件
    void go(){
        Intent intent =new Intent(MainActivity.this, PickerActivity.class);
        intent.putExtra(PickerConfig.SELECT_MODE,PickerConfig.PICKER_IMAGE_VIDEO);//设置选择类型，默认是图片和视频可一起选择(非必填参数)
        long maxSize=188743680L;//long long long long类型
        intent.putExtra(PickerConfig.MAX_SELECT_SIZE,maxSize); //最大选择大小，默认180M（非必填参数）
        intent.putExtra(PickerConfig.MAX_SELECT_COUNT,15);  //最大选择数量，默认40（非必填参数）
        ArrayList<Media> defaultSelect = select;//可以设置默认选中的照片，比如把select刚刚选择的list设置成默认的。
        intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST,defaultSelect); //可以设置默认选中的照片(非必填参数)
        MainActivity.this.startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200&&resultCode==PickerConfig.RESULT_CODE){
            select=data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);//选择完后返回的list
        }
    }
    
    //拍照
    // Intent intent =new Intent(MainActivity.this, TakePhotoActivity.class); //Take a photo with a camera
    // MainActivity.this.startActivityForResult(intent,200);
```    

# Screenshots
![](https://github.com/dmcBig/MediaPickerPoject/blob/master/Screenshots/Screenshots1.png)
![](https://github.com/dmcBig/MediaPickerPoject/blob/master/Screenshots/Screenshots2.png)
![](https://github.com/dmcBig/MediaPickerPoject/blob/master/Screenshots/Screenshots3.png)
![](https://github.com/dmcBig/MediaPickerPoject/blob/master/Screenshots/Screenshots4.png)
![](https://github.com/dmcBig/MediaPickerPoject/blob/master/Screenshots/Screenshots5.png)
![](https://github.com/dmcBig/MediaPickerPoject/blob/master/Screenshots/Screenshots6.png)
