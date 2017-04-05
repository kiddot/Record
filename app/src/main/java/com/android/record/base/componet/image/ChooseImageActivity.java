package com.android.record.base.componet.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.record.R;
import com.android.record.base.componet.BaseActivity;
import com.android.record.base.componet.ClickableAdapter;
import com.android.record.base.componet.event.ImageFolderChangeEvent;
import com.android.record.base.componet.event.ImageSelectedFinishedEvent;
import com.android.record.base.componet.moudule.ImageFolderBean;
import com.android.record.base.util.DisplayUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by yanghuang on 2016/7/11.
 * <p>
 * 选择本机的图片，需要选择的图片数量可以在startActivity里面进行设置，默认是0张，可以打开选不了
 * 选择的图片通过EventBus中的ImageSelectedFinishedEvent进行事件传播
 * 注意！！！
 * 在监听ImageSelectedFinishedEvent的时候，需要判断自身的的hashcode是否相符再处理
 * if(this.hashCode() == event.requestHashcode)) {}
 * 避免其他模块调用选择图片的时候触发你的模块动作！！！
 *
 * @author yanghuang
 * @date 2016/7/11
 * <p>
 * Changed by yanghuang on 2016/8/22 增加单选模式，adapter的点击处理通过tag移至此处理
 * Changed by yanghuang on 2016/9/12. 加入6.0权限控制
 * Changed by yanghuang on 2016/11/21 调整成为mvp模式，添加预览功能
 */
public class ChooseImageActivity extends BaseActivity implements View.OnClickListener,
        ClickableAdapter.OnItemClickListener, ChooseImagePresenter.ChooseImageViewImpl {

    public static final String TAG = ChooseImageActivity.class.getSimpleName();
    private static final String SELECT_LIMIT_KEY = "select_limit_key";
    private static final String REQUEST_HASHCODE_KEY = "request_hashcode_key";
    private static final String KEY_CHOICE_MODE = "KEY_CHOICE_MODE";

    private static final int REQUEST_CODE_READ_EX_PERM = 0X333;

    //private static final String TAG_PREVIEW_FRAGMENT = PreviewPageFragment.class.getSimpleName();

    RecyclerView mRecyclerView;
    RelativeLayout mRlBottomBar;
    TextView mTvChangeDir;
    TextView mTvPreview;
    ChooseImageAdapter mAdapter;
    /**
     * titlebar 相关
     */
    TextView mTvTitleName;
    ImageView mBtnTitlebarBack;
    private TextView mTvFinished;
    /**
     * 显示图片文件夹
     */
    private ImageFolderListPopupwindow mImgFolderPopup;

    private ChooseImagePresenter mChooseImagePresenter;

    /**
     * 启动activity
     *
     * @param context
     * @param selectLimitNum  最多可以选择的数量
     * @param requestHashcode 请求类的hashcode，用于结果回调区别
     */
    public static void startActivity(Context context, int selectLimitNum, int requestHashcode) {
        Intent intent = new Intent(context, ChooseImageActivity.class);
        intent.putExtra(SELECT_LIMIT_KEY, selectLimitNum);
        intent.putExtra(REQUEST_HASHCODE_KEY, requestHashcode);
        intent.putExtra(KEY_CHOICE_MODE, ChooseImagePresenter.MULTI_CHOICE_MODE);
        context.startActivity(intent);
    }

    public static void startActivityInSingleMode(Context context, int selectLimitNum, int requestHashcode) {
        Intent intent = new Intent(context, ChooseImageActivity.class);
        intent.putExtra(SELECT_LIMIT_KEY, selectLimitNum);
        intent.putExtra(REQUEST_HASHCODE_KEY, requestHashcode);
        intent.putExtra(KEY_CHOICE_MODE, ChooseImagePresenter.SINGLE_CHOICE_MODE);
        context.startActivity(intent);
    }

    @Override
    protected void getLayoutBinding() {
        setContentView(R.layout.activity_choose_image);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initView();
        initVariables();
        initListener();
        initTitleBar();
        mChooseImagePresenter.scanImageFile(ChooseImageActivity.this);
    }

    private void initListener() {
        mTvFinished.setOnClickListener(this);
        mTvChangeDir.setOnClickListener(this);
        mBtnTitlebarBack.setOnClickListener(this);
        mTvPreview.setOnClickListener(this);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.choose_img_rv);
        mRlBottomBar = (RelativeLayout) findViewById(R.id.choose_img_rl_bottom_bar);
        mTvChangeDir = (TextView) findViewById(R.id.choose_img_tv_dir);
        mTvPreview = (TextView) findViewById(R.id.choose_img_tv_preview);
        mTvTitleName = (TextView) findViewById(R.id.title_name);
        mBtnTitlebarBack = (ImageView) findViewById(R.id.title_left);
        mTvFinished = (TextView) findViewById(R.id.title_right);
    }

    private void initTitleBar() {
        //mVsRightButton.setVisibility(View.VISIBLE);
        //mBtnTitlebarBack.setVisibility(View.VISIBLE);
        mTvFinished.setText("完成");
        mTvTitleName.setText("选择图片");
    }

    private void initVariables() {
        Intent intent = getIntent();
        int selectLimitNum = intent.getIntExtra(SELECT_LIMIT_KEY, 0);
        int requestHashcode = intent.getIntExtra(REQUEST_HASHCODE_KEY, 0);
        int choiceMode = intent.getIntExtra(KEY_CHOICE_MODE, ChooseImagePresenter.MULTI_CHOICE_MODE);
        mChooseImagePresenter = new ChooseImagePresenter(selectLimitNum, requestHashcode, choiceMode, this);
    }

    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: " +mChooseImagePresenter.getAllImgs().toString());
        mAdapter = new ChooseImageAdapter(mChooseImagePresenter);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(ChooseImageActivity.this, 3));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_right:
                onSelectFinished();
                break;
            case R.id.choose_img_tv_dir:
                showChooseImgFolderPopupwindow();
                break;
            case R.id.title_left:
                onClickBack();
                break;
            case R.id.choose_img_tv_preview:
                onClickPreview();
                break;
            default:
                break;
        }
    }

    private void onSelectFinished() {
        Log.d(TAG, "onSelectFinished: 选择图片完成");
        mTvFinished.setClickable(false);
        EventBus.getDefault().post(
                new ImageSelectedFinishedEvent(mChooseImagePresenter.getRequestHashcode(),
                        mChooseImagePresenter.getSelectedImage()));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.onBackPressed();
            onClickBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClickBack() {
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_PREVIEW_FRAGMENT);
//        if (fragment == null) {
//            finish();
//        } else {
//            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//        }
        finish();
    }

    public void onClickPreview() {
//        if (mChooseImagePresenter.getSelectedImage() != null
//                && mChooseImagePresenter.getSelectedImage().size() != 0) {
//            // 这里的预览是选中的那一部分
//            PreviewPageFragment fragment = PreviewPageFragment.newInstance(mChooseImagePresenter,
//                    mChooseImagePresenter.getSelectedImage(), 0);
//            getSupportFragmentManager().beginTransaction().replace(R.id.choose_image_fl_container,
//                    fragment, TAG_PREVIEW_FRAGMENT).commit();
//        }
    }

    /**
     * 打开图片文件夹窗口
     */
    public void showChooseImgFolderPopupwindow() {
        if (mImgFolderPopup == null) {
            initChooseImgFolderPopupWindow(mChooseImagePresenter.getImageFolders());
        }
        mImgFolderPopup.setAnimationStyle(R.style.anim_choose_img_popup_folders);
        mImgFolderPopup.showAsDropDown(mRlBottomBar, 0, 0);

        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = .3f;
        getWindow().setAttributes(lp);
    }

    /**
     * 创建和初始化文件夹窗口
     */
    private void initChooseImgFolderPopupWindow(List<ImageFolderBean> data) {
        mImgFolderPopup = new ImageFolderListPopupwindow(
                LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.popup_choose_img_folderlist, null)
                , R.id.choose_img_rv_folder
                , ViewGroup.LayoutParams.MATCH_PARENT
                , (int) (DisplayUtil.getDisplayMetrics(ChooseImageActivity.this).heightPixels * 0.7)
                , true
                , data);

        mImgFolderPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        //替换布局
//        PreviewPageFragment fragment = PreviewPageFragment.newInstance(mChooseImagePresenter,
//                mChooseImagePresenter.getAllImgs(), position);
//        getSupportFragmentManager().beginTransaction().replace(R.id.choose_image_fl_container,
//                fragment, TAG_PREVIEW_FRAGMENT).commit();
    }

    /**
     * eventbus 监听切换文件夹
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImageFolderChange(ImageFolderChangeEvent event) {
        changeImageFolder(event.currentFolder);
    }

    private void changeImageFolder(ImageFolderBean currentFolder) {
        mChooseImagePresenter.changeImageFolder(currentFolder);

        mRecyclerView.getAdapter().notifyDataSetChanged();
        //更新相关显示数据
        mTvChangeDir.setText(currentFolder.getName());
        mImgFolderPopup.dismiss();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        // 调用presenter的destroy防止内存泄漏
        mChooseImagePresenter.destroy();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ChooseImageViewImpl接口的相关实现
    ///////////////////////////////////////////////////////////////////////////

    /*showSingletonToast()方法的实现在baseActivity*/

    @Override
    public void notifyRecycleViewRefresh() {
        //解决activity关闭造成的NullPointer错误
        if (mRecyclerView != null) {
            if (mRecyclerView.getAdapter() == null) {
                initRecyclerView();
            } else {
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showSingletonToast(String s) {
        showToast(s);
    }

    /**
     * 更新单独的一个item里面的选中状态，避免整个item重新加载影响性能
     * 需要在adapter中用position进行setTag，让此处可以通过position找到对应的item
     *
     * @param position
     */
    @Override
    public void notifyItemCheckedChanged(int position, boolean isChecked) {
        // 刷新数量
        refreshSelectedCount();
        if (mRecyclerView != null) {
            View itemView = mRecyclerView.findViewWithTag(position);
            if (itemView != null) {
                ChooseImageAdapter.ChooseImageViewHolder holder = new ChooseImageAdapter.ChooseImageViewHolder(itemView);

                if (isChecked) {
                    holder.ivSelectState.setImageResource(R.mipmap.choose_image_selected);
                    holder.ivImage.setColorFilter(Color.parseColor("#77000000"));
                } else {
                    holder.ivSelectState.setImageResource(R.mipmap.choose_image_unselected);
                    holder.ivImage.setColorFilter(null);
                }
            }
        }
    }

    @Override
    public void refreshSelectedCount() {
        int count = mChooseImagePresenter.getSelectedImage().size();
        if (count == 0) {
            mTvPreview.setVisibility(View.GONE);
        } else {
            mTvPreview.setVisibility(View.VISIBLE);
            mTvPreview.setText("预览(" + count + ")");
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 权限请求回调
    ///////////////////////////////////////////////////////////////////////////

//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        mToastor.showToast("权限不足，无法查看图片，请允许访问权限");
//        finish();
//    }
//
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        if (requestCode == REQUEST_CODE_READ_EX_PERM) {
//            mChooseImagePresenter.scanImageFile(ChooseImageActivity.this);
//        }
//    }

}
