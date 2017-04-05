package com.android.record.base.componet.event;

import java.util.List;

/**
 * Created by yanghuang on 2016/7/16.
 * 把选择图片的结果传出去
 * @author yanghuang
 * @date 2016/7/16
 */
public class ImageSelectedFinishedEvent {

    public int requestHashcode;
    public List<String> selectedImagesPath;

    public ImageSelectedFinishedEvent(int requestHashcode, List<String> selectedImagesPath) {
        this.requestHashcode = requestHashcode;
        this.selectedImagesPath = selectedImagesPath;
    }
}
