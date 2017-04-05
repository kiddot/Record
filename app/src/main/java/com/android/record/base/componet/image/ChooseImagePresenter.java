package com.android.record.base.componet.image;

import android.content.Context;
import android.util.Log;


import com.android.record.base.componet.moudule.ImageFileFilter;
import com.android.record.base.componet.moudule.ImageFileModifiedComparator;
import com.android.record.base.componet.moudule.ImageFolderBean;
import com.android.record.base.componet.moudule.ImageScanUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 对应chooseImageActivity的presenter，
 * 为了保持多选的同步，在 ChooseImageAdapter 和 PreviewAdapter 中也有使用到
 * <p>
 * 在 chooseImageActivity 的 onDestroy 中记得调用次presenter的 destroy() 方法
 * <p>
 * 类逻辑涉及地方较多，请做好判断和连锁的测试
 * 多选是有两个模式的
 * SINGLE_CHOICE_MODE：单选模式，选了一个之后，再选第二个的话，前一个会自动取消掉，可以在修改头像的地方进行测试
 * MULTI_CHOICE_MODE：多选模式，会进行最多张数的判断，超过限制张数则不允许继续选择了，可以在班级时光测试
 * <p>
 * Created by yanghuang on 2016/11/20.
 */

public class ChooseImagePresenter {
    public static final String TAG = ChooseImagePresenter.class.getSimpleName();

    public static final int SINGLE_CHOICE_MODE = 0X010;
    public static final int MULTI_CHOICE_MODE = 0X020;

    /**
     * 所有的图片
     */
    private ArrayList<String> mAllImgs = new ArrayList<>();
    /**
     * 所有的图片文件夹
     */
    private List<ImageFolderBean> mImageFolders = new ArrayList<>();
    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    private List<String> mSelectedImage = new LinkedList<>();
    /**
     * 外部启动参数相关
     */
    private int mSelectLimitNum;
    private int mRequestHashcode;
    private int mPosition;
    /**
     * 选择模式，默认是多选模式
     */
    private int mChoiceMode = MULTI_CHOICE_MODE;
    private int mImgsCount = 0;

    /**
     * 回调View层进行更新UI用的，使用前请判断是否为空
     */
    private ChooseImageViewImpl mChooseImageView;

    public ChooseImagePresenter(int selectLimitNum, int requestHashcode, int choiceMode,
                                ChooseImageViewImpl view, int position) {
        this.mSelectLimitNum = selectLimitNum;
        this.mRequestHashcode = requestHashcode;
        this.mChoiceMode = choiceMode;
        this.mChooseImageView = view;
        this.mPosition = position;
    }

    public interface ChooseImageViewImpl {
        /**
         * 更新单独的一个item里面的选中状态，避免整个item重新加载影响性能
         * 需要在adapter中用position进行setTag，让此处可以通过position找到对应的item
         *
         * @param position
         */
        void notifyItemCheckedChanged(int position, boolean isChecked);

        void notifyRecycleViewRefresh();

        void showSingletonToast(String s);

        void refreshSelectedCount();
    }

    public void destroy() {
        this.mChooseImageView = null;
        this.mAllImgs.clear();
        this.mAllImgs = null;
        this.mImageFolders.clear();
        this.mImageFolders = null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 基本逻辑处理
    ///////////////////////////////////////////////////////////////////////////

    public void scanImageFile(final Context context) {
//        ExecutorManager.getInstance().addTask(new Tasker<Object>() {
//            @Override
//            protected Object doInBackground() {
//                mImgsCount = ImageScanUtil.scanAll(context.getApplicationContext(), mAllImgs, mImageFolders);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object data) {
//                super.onPostExecute(data);
//                if (mChooseImageView != null) {
//                    mChooseImageView.notifyRecycleViewRefresh();
//                }
//            }
//        });
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                Log.d(TAG, "call: ");
                mImgsCount = ImageScanUtil.scanAll(context.getApplicationContext(), mAllImgs, mImageFolders);
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                        if (mChooseImageView != null) {
                            mChooseImageView.notifyRecycleViewRefresh();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.d(TAG, "next: ");

                    }
                });
    }

    /**
     * 切换文件夹相关
     *
     * @param currentFolder
     */
    public void changeImageFolder(ImageFolderBean currentFolder) {
        mAllImgs.clear();

        File imgDir = new File(currentFolder.getDir());
        ImageFileFilter filter = new ImageFileFilter();
        String[] imgsName = imgDir.list(filter);
        for (int i = 0; i < imgsName.length; i++) {
            mAllImgs.add(currentFolder.getDir() + "/" + imgsName[i]);
        }
        Collections.sort(mAllImgs, new ImageFileModifiedComparator());
    }

    ///////////////////////////////////////////////////////////////////////////
    // getter \ setter
    ///////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getAllImgs() {
        return mAllImgs;
    }

    public List<String> getSelectedImage() {
        return mSelectedImage;
    }

    public int getRequestHashcode() {
        return mRequestHashcode;
    }

    public int getPosition(){
        return mPosition;
    }

    public List<ImageFolderBean> getImageFolders() {
        return mImageFolders;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 多选相关
    ///////////////////////////////////////////////////////////////////////////

    public boolean isSelected(String imagePath) {
        return mSelectedImage.contains(imagePath);
    }

    /**
     * @param position
     * @return false - 未选中
     * true  - 已选中
     */
    public boolean selectOrUndo(int position) {
        final String imageUrl = mAllImgs.get(position);
        return selectOrUndo(imageUrl);
    }

    /**
     * @param imageUrl
     * @return false - 未选中
     * true  - 已选中
     */
    public boolean selectOrUndo(String imageUrl) {

        // 已经选择过该图片
        if (mSelectedImage.contains(imageUrl)) {
            mSelectedImage.remove(imageUrl);
            return false;
        } else
        // 未选择该图片
        {
            switch (mChoiceMode) {
                case MULTI_CHOICE_MODE:
                    if (mSelectedImage.size() == mSelectLimitNum) {
                        if (mChooseImageView != null) {
                            mChooseImageView.showSingletonToast("最多选择" + mSelectLimitNum + "张图片");
                        }
                    } else {
                        mSelectedImage.add(imageUrl);
                        return true;
                    }
                    break;

                case SINGLE_CHOICE_MODE:
                    if (mSelectedImage.size() != 0) {
                        int previousPosition = mAllImgs.indexOf(mSelectedImage.get(0));
                        if (previousPosition != -1) {
                            notifyViewItemCheckedChanged(previousPosition, false);
                        }
                        mSelectedImage.clear();
                    }
                    mSelectedImage.add(imageUrl);
                    return true;

                default:
                    break;
            }
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 对外提供的操作View的方法
    ///////////////////////////////////////////////////////////////////////////

    public void notifyViewItemCheckedChanged(int position, boolean isChecked) {
        if (mChooseImageView != null) {
            mChooseImageView.notifyItemCheckedChanged(position, isChecked);
        }
    }

    public void refreshViewSelectedCount() {
        if (mChooseImageView != null) {
            mChooseImageView.refreshSelectedCount();
        }
    }

}
