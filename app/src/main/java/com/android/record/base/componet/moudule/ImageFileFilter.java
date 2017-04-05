package com.android.record.base.componet.moudule;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by yanghuang on 2016/7/15.
 * 过滤出图片文件
 * @author yanghuang
 * @date 2016/7/15
 */
public class ImageFileFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String filename) {
        if (filename.endsWith(".jpg") || filename.endsWith(".png")
                || filename.endsWith(".jpeg") || filename.endsWith(".JPEG")
                || filename.endsWith(".JPG"))
            return true;
        return false;
    }
}
