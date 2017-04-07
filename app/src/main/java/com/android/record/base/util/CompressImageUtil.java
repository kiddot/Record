package com.android.record.base.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import com.android.record.common.AppConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class CompressImageUtil {
    private static final String TAG = "CompressImageUtil";

    /**
     * 获得图片文件的图片尺寸
     *
     * @return 返回的数组0位是宽，1位是高
     */
    private static int[] getImageSize(String imagePath) {
        int[] res = new int[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(imagePath, options);

        res[0] = options.outWidth;
        res[1] = options.outHeight;

        return res;
    }

    public static Bitmap getSmallBitmap(String filePath, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Log.v(CompressImageUtil.class.getSimpleName(), "filePath:" + filePath + "  imSampleSize:" + inSampleSize);
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 压缩位图到指定大小， 每次压缩减少6点quality
     *
     * @param maxLength 单位：KB
     * @return
     */
    public static byte[] compressBitmapSmallTo(Bitmap bitmap, int maxLength) throws RuntimeException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        while (stream.toByteArray().length / 1024 > maxLength && quality - 6 > 6) {
            stream.reset();
            quality -= 6;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        }
        Log.i(TAG, "compressBitmapSmallTo()：quality=" + quality);
        return stream.toByteArray();
    }

    public static String compressForSelect(String pfilePath) {

        int[] imageSize = getImageSize(pfilePath);
        int width = imageSize[0];
        int height = imageSize[1];
        int thumbW = width;
        int thumbH = height;

        //若宽大于高，则互换width和height方便进行计算，但thumbW、thumbH不变，保留做文件的真实处理
        // 保证 width/height <= 1
        if (thumbH < thumbW) {
            width = thumbH;
            height = thumbW;
        }

        // width/height 的比例
        double scale = ((double) width / height);

        // 最终的压缩生成的文件最大的大小，单位（kB）
        double outputFileMaxLenght = 60;

        //
        int inSampleSize = 1;

        // 0.5625 为 9/16 的值
        if (scale > 0.5625 && scale <= 1) { // 介于 方图 和 9/16 之间


            //分级计算压缩后的大小和图片尺寸
            if (height < 1664) {
                // 算法说明：
                // 一张 1664*1664 的图片，设置其压缩后的大小为150k
                // 一张 2495*2495 的图片，设置其压缩后的大小为300k
                // 一张 2560*2560 的图片，设置其压缩后的大小为300k
                // 一张 1440*2560 的图片，设置其压缩后的大小为200k
                // 一张 1280*  ？ 的长图，设置其压缩后的大小为500k
                // 根据 原图 与 1664*1664 的像素点比例，计算出压缩后的文件大小
                outputFileMaxLenght = (thumbW * thumbH) / Math.pow(1664, 2) * 150;
                //最小不能小于60k
                outputFileMaxLenght = Math.max(60, outputFileMaxLenght);
            } else if (height >= 1664 && height < 4990) {
                // 长宽均减半
                thumbW = width / 2;
                thumbH = height / 2;
                inSampleSize = 2;
                outputFileMaxLenght = (thumbW * thumbH) / Math.pow(2495, 2) * 300;
                outputFileMaxLenght = Math.max(60, outputFileMaxLenght);
            } else if (height >= 4990 && height < 10240) {
                // 长宽均为原图的 1/4
                thumbW = width / 4;
                thumbH = height / 4;
                inSampleSize = 4;
                outputFileMaxLenght = (thumbW * thumbH) / Math.pow(2560, 2) * 300;
                outputFileMaxLenght = Math.max(100, outputFileMaxLenght);
            } else {
                // 超过10240的图 根据 高为1280 来计算压缩比例
                int multiple = height / 1280 == 0 ? 1 : height / 1280;
                thumbW = width / multiple;
                thumbH = height / multiple;
                inSampleSize = multiple;
                outputFileMaxLenght = (thumbW * thumbH) / Math.pow(2560, 2) * 300;
                outputFileMaxLenght = Math.max(100, outputFileMaxLenght);
            }

        } else if (scale > 0.5 && scale <= 0.5625) { // 介于 9/16 和 1/2 之间，小长图
            //根据 高为1280 来计算压缩比例
            int multiple = height / 1280 == 0 ? 1 : height / 1280;
            thumbW = width / multiple;
            thumbH = height / multiple;
            inSampleSize = multiple;
            outputFileMaxLenght = (thumbW * thumbH) / (1440.0 * 2560.0) * 300;
            outputFileMaxLenght = Math.max(100, outputFileMaxLenght);

        } else {// 大于 1/2 的图，即长图或者宽图
            //根据 宽为1280 来计算压缩比例
            int multiple = (int) Math.ceil(width / 1280.0);
            thumbW = width / multiple;
            thumbH = height / multiple;
            inSampleSize = multiple;
            outputFileMaxLenght = 500;
        }


        //尺寸压缩
        Log.i(TAG, "尺寸压缩：原尺寸：width = " + width + "，height = " + height +
                "，inSampleSize = " + inSampleSize);
        Bitmap bitmap = getSmallBitmap(pfilePath, inSampleSize);

        //压缩失败
        if (Check.isNull(bitmap)) {
            throw new RuntimeException("img compress failed");
        }

        //旋转照片角度
        int degree = readPictureDegree(pfilePath);
        if (degree != 0) {
            bitmap = rotateBitmap(bitmap, 90);
        }

        // 质量压缩
        Log.i(TAG, "质量压缩：outputFileMaxLenght =" + outputFileMaxLenght);
        byte[] compressedByte = compressBitmapSmallTo(bitmap, (int) outputFileMaxLenght);


        //使用时间作为文件名
        String newFliePath = AppConstant.PATH_COMPRESSED_IMAGES + System.currentTimeMillis() + ".jpg";
        //创建新文件,会覆盖老文件（没有父目录存在情况下会创建新目录）
        FileUtil.createFile(newFliePath);


        //保存图片到文件
        FileUtil.writeFile(newFliePath, compressedByte);

        Log.i(TAG, "Bitmap compressed for selected success, newFilePath: " + newFliePath);
        //返回新文件路径
        return newFliePath;

    }

    /**
     * 压缩图片并保存到已压缩图片的文件夹，主要用于上传文件
     * 注意！上传完图片之后请将此处的图片进行删除
     *
     * @param pfilePath
     * @return
     */
    /*public static String compressForSelect(String pfilePath) {

        *//**
     * 压缩说明（和ios于7.29统一）
     * 1、质量压缩，统一0.75，（由于质量压缩与）
     * 2、尺寸压缩，计算照片尺寸对于常用的手机屏幕的分辨率大小（width768，height1024）的压缩比例
     *             方法见BitmapHelper.calculateInSampleSize
     *             大于分辨率大小的图片进行压缩，小于的不进行扩大
     *             按照比例进行压缩照片尺寸
     * 3、尺寸压缩，若尺寸压缩之后的照片仍大于200KB，再进行此步骤
     *             循环少幅压缩（防止低于200KB过多），逐渐降低图片到200KB大小以下
     *             每次循环根据长、宽各除以1.1
     *
     * 说明：质量压缩和尺寸压缩虽然都跟图片的清晰度相关，但是两者是不同维度的
     *      质量压缩不会影响尺寸，尺寸压缩不会影响质量压缩
     *      但是都对图片的清晰度有影响
     *
     * 缺点，在进行尺寸压缩的时候无法判断最后生成的文件的大小
     *      在后面每次尺寸长、宽除以1.1进行尺寸压缩的时候，
     *      每次循环均需要进行一次质量压缩来得到最后生成文件的大小
     *      会很大程度上影响性能，往能在android于ios统一的情况下进行优化
     *      （最终的目标是使图片在200k一下也保持较高的清晰度）
     *//*

        //尺寸压缩
        Bitmap bm = BitmapHelper.getSmallBitmap(pfilePath, 768, 1024);

        //在质量压缩前判断文件是否大于200k
        int quality = 100;
        if (bitmapToByte(bm, quality).length > 200 * 1024) {
            quality = 75;
        }

        //在0.75的质量压缩下，不断减小尺寸使图片大小小于200k
        while (bitmapToByte(bm, quality).length > 200 * 1024) {
            Bitmap compressedBitmap = bm;
            //尺寸压缩
            bm = BitmapHelper.scaleImageTo(bm, (int) (bm.getWidth() / 1.1), (int) (bm.getHeight() / 1.1));
            compressedBitmap.recycle();
        }

        Bitmap compressedBitmap = bm;

        //压缩失败
        if (Check.isNull(compressedBitmap)) {
            return null;
        }

        //旋转照片角度
        int degree = readPictureDegree(pfilePath);
        if (degree != 0) {
            compressedBitmap = rotateBitmap(compressedBitmap, 90);
        }

        //使用时间作为文件名
        String newFliePath = AppConstant.PATH_COMPRESSED_IMAGES + System.currentTimeMillis() + ".jpg";
        //创建新文件,会覆盖老文件（没有父目录存在情况下会创建新目录）
        FileUtilTemp.createFile(newFliePath);


        //保存图片到文件
        saveBitmap(compressedBitmap, newFliePath, quality, Bitmap.CompressFormat.JPEG);

        Log.i(TAG, "Bitmap compressed for selected success, newFilePath: " + newFliePath);
        //返回新文件路径
        return newFliePath;

    }

    public static byte[] bitmapToByte(Bitmap b, int quality) {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, quality, o);
        Log.d(TAG, "[bitmapToByte] success!!!");
        return o.toByteArray();
    }*/

    /**
     * 保存位图到指定的文件
     * 属于bitmapHelper里面的方法，但是不能指定格式，导致想保存jpg格式的图片保存成png，图片损坏
     * 粘贴出来加入Bitmap.CompressFormat参数使用
     *
     * @param bitmap
     * @param file
     * @return
     */
    public static boolean saveBitmap(Bitmap bitmap, File file, int quality, Bitmap.CompressFormat format) {
        if (bitmap == null)
            return false;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(format, quality, fos);
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean saveBitmap(Bitmap bitmap, String absPath, int quality, Bitmap.CompressFormat format) {
        return saveBitmap(bitmap, new File(absPath), quality, format);
    }

    /**
     * 将一个bitmap旋转指定的度数后返回
     *
     * @param bitmap
     * @param degree
     * @return
     */
    private static Bitmap rotateBitmap(Bitmap bitmap, int degree) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        Log.i(TAG, "Bitmap rotated success");
        return bitmap;
    }

    /**
     * 获得图片文件的degree
     *
     * @param path
     * @return
     */
    private static int readPictureDegree(String path) {
        int degree = 0;
        try {

            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Image file degree read success, degree:" + degree);
        return degree;
    }

}
