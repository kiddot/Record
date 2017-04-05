package com.android.record.base.componet.moudule;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;


import com.android.record.base.util.Check;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yanghuang on 2016/7/15.
 * 扫描手机中的图片工具
 * @author yanghuang
 * @date 2016/7/15
 */
public class ImageScanUtil {

    public static final String TAG = ImageScanUtil.class.getSimpleName();

    /**
     * 扫描手机上的图片，添加到参数中的List里
     * @param context
     * @param resultImgList
     * @param resultImgFolders
     * @return 图片总数
     */
    public static int scanAll(Context context, List<String> resultImgList, List<ImageFolderBean> resultImgFolders) {

        /**
         * 临时的辅助类，用于防止同一个文件夹的多次扫描
         */
        Set<String> dirPaths = new HashSet<>();

        /**
         * 扫描文件夹文件的时候只获取图片文件
         */
        ImageFileFilter imageFileFilter = new ImageFileFilter();

        /**
         * 根据最后修改时间给文件排序
         */
        ImageFileModifiedComparator fileComparator = new ImageFileModifiedComparator();

        /**
         * 照片的总数量
         */
        int allImgCount = 0;

        ContentResolver mContentResolver = context.getContentResolver();
        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor mCursor = mContentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null,
                MediaStore.Images.Media.DEFAULT_SORT_ORDER);

        assert mCursor != null;
        while (mCursor.moveToNext()) {

            // 获取图片的路径
            String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));

            // 获取该图片的父路径名
            File parentFile = new File(path).getParentFile();
            if (parentFile == null) {
                continue;
            }
            String parentPath = parentFile.getAbsolutePath();

            try {
                //检查此文件夹的是否已被扫描
                if (dirPaths.contains(parentFile.getAbsolutePath())) {
                    continue;
                }

                //获得文件夹里的所有图片
                List<String> childFileList = getImgFromParentPath(parentFile, imageFileFilter);
                for (int i = 0; i < childFileList.size(); i++) {
                    resultImgList.add(childFileList.get(i));
                }

                // 排序
                Collections.sort(childFileList, fileComparator);

                dirPaths.add(parentFile.getAbsolutePath());

                allImgCount += childFileList.size();

                if (!Check.isNull(resultImgFolders)) {
                    // 初始化imageFloder
                    ImageFolderBean imageFloder = new ImageFolderBean();
                    imageFloder.setDir(parentFile.getAbsolutePath());
                    imageFloder.setFirstImagePath(childFileList.get(0));
                    imageFloder.setCount(childFileList.size());
                    resultImgFolders.add(imageFloder);
                }
            } catch (Exception e) {
                continue;
            }
        }
        mCursor.close();

        // 扫描完成，辅助的HashSet也就可以释放内存了
        dirPaths.clear();
        dirPaths = null;

        // 排序
        Collections.sort(resultImgList, fileComparator);
        return allImgCount;
    }

    private static List<String> getImgFromParentPath(File parentFile, FilenameFilter filter) {
        /**
         * 不生成File数组节省内存
         */
        String[] fileList = parentFile.list(filter);
        List<String> imgs = new ArrayList<>(fileList.length);
        for (int i = 0; i < fileList.length; i++) {
            imgs.add(parentFile.getAbsolutePath() + "/" + fileList[i]);
        }
        return imgs;
    }

}
