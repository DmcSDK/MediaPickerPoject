package com.dmcbig.mediapicker;

/**
 * Created by dmcBig on 2017/6/9.
 */

public class PickerConfig {
    public  static  final  String LOG_TAG="MediaPicker";

    /** 最大图片选择次数，int类型，默认40*/
    public static final String MAX_SELECT_COUNT = "max_select_count";

    public static final int DEFAULT_SELECTED_MAX_COUNT = 40;

    /** 最大文件大小，int类型，默认180m*/
    public static final String MAX_SELECT_SIZE = "max_select_size";

    public static final int DEFAULT_SELECTED_MAX_SIZE = 3072;

    /** 图片选择模式，默认多选 */
    public static final String SELECT_MODE = "select_mode";
    /** 是否显示相机，默认显示 */
    public static final String SHOW_CAMERA = "show_camera";
    /** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合  */
    public static final String EXTRA_RESULT = "select_result";
    /** 默认选择集 */
    public static final String DEFAULT_SELECTED_LIST = "default_list";
    /** 选择文件类型 */
    public static final String FILE_TYPE = "file_type";

    public static final int PICKER_IMAGE = 100;
    public static final int PICKER_IMAGE_VIDEO = 101;

}
