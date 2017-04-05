package com.android.record.base.util;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiddo on 17-4-5.
 */

public class CompressImagesHelper {
    private static final String TAG = CompressImagesHelper.class.getSimpleName();

    public static List<String> compress(List<String> paths) {
        List<String> compressedList = new ArrayList<>(paths.size());
        //压缩照片
        //压缩失败的会进行添加null
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            String compressedPath = null;
            try {
                //compressedPath = CompressImageUtil.compressForSelect(path);
            } catch (Exception e) {
                Log.e(TAG, "an error happened when a img was compressing");
                Log.e(TAG, "压缩图片过程发生异常,异常为:");
                e.printStackTrace();
                clearCacheFiles(compressedList);
                Log.e(TAG, "清除压缩的照片:");
                return null;//返回空集合
            }
            if (!Check.isEmpty(compressedPath))
                compressedList.add(compressedPath);
        }
        return compressedList;
    }

    public static void clearCacheFiles(List<String> filesList) {
        try {
            for (String bean : filesList) {
                if (!Check.isNull(bean)) {
                    deleteFile(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteFile(String path){
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }
}
