package com.android.record.base.componet.event;


import com.android.record.base.componet.moudule.ImageFolderBean;

/**
 * Created by yanghuang on 2016/7/16.
 * 选择一个图片文件夹事件
 * @author yanghuang
 * @date 2016/7/16
 */
public class ImageFolderChangeEvent {

    public ImageFolderBean currentFolder;

    public ImageFolderChangeEvent(ImageFolderBean mCurrentFolder) {
        this.currentFolder = mCurrentFolder;
    }
}
