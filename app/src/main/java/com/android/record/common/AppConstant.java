package com.android.record.common;

import android.os.Environment;

/**
 * Created by kiddo on 17-3-26.
 */

public class AppConstant {
    public static final String Url = "http://120.25.235.70/Record/";

    public final static String SAVE_PATH = Environment.getExternalStorageDirectory() + "/xxt/";

    /**
     * 本地保存图片的相对位置
     */
    public final static String PHOTO_SAVE_PATH = SAVE_PATH + "photo/";

    /**
     * 压缩图片暂存位置
     */
    public static final String PATH_COMPRESSED_IMAGES = AppConstant.SAVE_PATH + "compressedImages/";
}
