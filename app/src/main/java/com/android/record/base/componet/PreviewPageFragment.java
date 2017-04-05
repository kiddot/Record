//package com.android.record.base.componet;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewStub;
//import android.widget.CheckBox;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//
//import com.android.record.R;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * 选择图片中的预览页面
// * <p>
// * Created by yanghuang on 2016/11/20.
// */
//
//public class PreviewPageFragment extends BaseFragment implements View.OnClickListener {
//
//    CheckBox mCheckBox;
//    /**
//     * titlebar 相关
//     */
//    TextView mTvTitleName;
//    ImageView mBtnTitlebarBack;
//    ViewStub mVsRightButton;
//    private TextView mTvFinished;
//
//    private static final String TAG = "PreviewPageFragment";
//    private static final String TAG_DISPLAY_FRAGMENT = "TAG_DISPLAY_FRAGMENT";
//
//    private static final String KEY_URL_LIST = "url_list";
//    private static final String KEY_CLICK_POSITION = "click_position";
//    private static final String KEY_PREVIEW_MODE = "PREVIEW_MODE";
//
//    private ArrayList<String> mPicList;
//    private int mClickPosition;
//    private DisplayPhotoFragment mDisplayPhotoFragment;
//
//    private ChooseImagePresenter mChooseImagePresenter;
//
//    private int mPreviewMode;
//
//    /**
//     * 此处为了保证presenter和外部的引用保持一致
//     * 不使用bundle来进行传递参数
//     */
//    public static PreviewPageFragment newInstance(ChooseImagePresenter presenter, List<String> list, int clickPosition) {
//        PreviewPageFragment fragment = new PreviewPageFragment();
//        fragment.mChooseImagePresenter = presenter;
//        Bundle bundle = new Bundle();
//        ArrayList<String> tempList = new ArrayList<>();
//        tempList.addAll(list);
//        bundle.putSerializable(KEY_URL_LIST, tempList);
//        bundle.putInt(KEY_CLICK_POSITION, clickPosition);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_choose_image_preview;
//    }
//
//    @Override
//    protected void init() {
//        initView();
//        initTitlebar();
//        initVariables();
//        initFragment();
//        mCheckBox.setChecked(mChooseImagePresenter.isSelected(mPicList.get(mClickPosition)));
//    }
//
//    private void initView() {
//        mCheckBox = (CheckBox) getActivity().findViewById(R.id.choose_img_cb);
//        mBtnTitlebarBack = (ImageView) getActivity().findViewById(R.id.title_left);
//    }
//
//    private void initTitlebar() {
//        mVsRightButton.setVisibility(View.VISIBLE);
//        mBtnTitlebarBack.setVisibility(View.VISIBLE);
//        mTvFinished = (TextView) getActivity().findViewById(R.id.title_right);
//        mTvFinished.setOnClickListener(this);
//        mTvFinished.setText("完成");
//        mTvTitleName.setText("选择图片");
//    }
//
//    private void initVariables() {
//        Bundle bundle = getArguments();
//        if (bundle == null) {
//            return;
//        }
//        mPicList = (ArrayList<String>) bundle.getSerializable(KEY_URL_LIST);
//        mClickPosition = bundle.getInt(KEY_CLICK_POSITION);
//    }
//
//    private void initFragment() {
//        //TODO
//        mDisplayPhotoFragment = DisplayPhotoFragment.newInstance(mPicList, mClickPosition);
//        //设置滑动监听，保持activity里面的空间与当前图片信息对应
//        mDisplayPhotoFragment.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                mCheckBox.setChecked(mChooseImagePresenter.isSelected(mPicList.get(position)));
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        // 替换布局
//        // 若使用getFragmentManager，接下来移除PreviewPageFragment的时候不移除mDisplayPhotoFragment会造成内存泄漏
//        // 使用getChildFragmentManager，则只需关注移除PreviewPageFragment就可以了
//        getChildFragmentManager().beginTransaction().replace(R.id.photo_pager_fl_container,
//                mDisplayPhotoFragment, TAG_DISPLAY_FRAGMENT).commit();
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.title_right:
//                mTvFinished.setClickable(false);
//                EventBus.getDefault().post(
//                        new ImageSelectedFinishedEvent(mChooseImagePresenter.getRequestHashcode(),
//                                mChooseImagePresenter.getSelectedImage()));
//                getActivity().finish();
//                break;
//
//            default:
//                break;
//        }
//    }
//
//    @OnClick(R.id.choose_img_cb)
//    public void onClickCheckBox() {
//        int positionInPresenter = mChooseImagePresenter.getAllImgs().indexOf(
//                mPicList.get(mDisplayPhotoFragment.getCurrentPosition()));
//        if(positionInPresenter == -1) {
//            // presenter里面的数据源因为更换文件夹发生了变化，所以无法找到之前的位置
//            // 所以无法借助mChooseImagePresenter来进行选择
//            String path = mPicList.get(mDisplayPhotoFragment.getCurrentPosition());
//            boolean isChecked = mChooseImagePresenter.selectOrUndo(path);
//            mCheckBox.setChecked(isChecked);
//            // 不存在所以不用更新item，只需要更新数量
//            mChooseImagePresenter.refreshViewSelectedCount();
//
//        } else {
//            // 在presenter里面进行选择，有可能选满不能选的情况
//            boolean isSelected = mChooseImagePresenter.selectOrUndo(positionInPresenter);
//            mChooseImagePresenter.notifyViewItemCheckedChanged(positionInPresenter, isSelected);
//            mCheckBox.setChecked(isSelected);
//        }
//    }
//
//    @OnClick(R.id.titlebar_btn_left)
//    public void onClickBack() {
//        if (mTvFinished.isClickable()) {
//            int size = getFragmentManager().getFragments() == null ? 0 : getFragmentManager().getFragments().size();
//            Log.i(TAG, "Fragment正在准备移除，当前的Fragment数量为：" + size);
//
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.remove(this);
//            transaction.commit();
//        } else {
//            // 如果已经点击了选择图片的完成按钮，即不能移除这个fragment
//            // 以防止其在chooseImageActivity中再次点击完成按钮，导致发送多次事件
//            getActivity().finish();
//        }
//    }
//
//}
