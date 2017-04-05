package com.android.record.base.componet.moudule;

import java.io.File;
import java.util.Comparator;

/**
 * Created by yanghuang on 2016/7/15.
 * 对全路径的文件进行按修改时间排序
 * @author yanghuang
 * @date 2016/7/15
 */
public class ImageFileModifiedComparator implements Comparator<String> {

    @Override
    public int compare(String lhs, String rhs) {
        if (new File(lhs).lastModified() < new File(rhs).lastModified()) {
            return 1;// 最后修改的照片在前
        } else if (new File(lhs).lastModified() > new File(rhs).lastModified()) {
            return -1;
        } else {
            return 0;
        }
    }
}
