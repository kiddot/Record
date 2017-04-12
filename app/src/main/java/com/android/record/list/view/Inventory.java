package com.android.record.list.view;

import android.support.v4.view.ViewPager;
import android.util.Log;

import com.android.record.R;
import com.android.record.base.componet.BaseFragment;
import com.android.record.base.componet.event.ImageSelectedFinishedEvent;
import com.android.record.base.componet.image.ChooseImageActivity;
import com.android.record.bean.SwipeCardBean;
import com.android.record.common.dialog.InputDialog;
import com.android.record.common.sp.UserManager;
import com.android.record.list.adapter.InventoryAdapter;
import com.android.record.list.contract.ListTaskContract;
import com.android.record.list.event.GetCardEvent;
import com.android.record.list.event.SendCardEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiddo on 17-4-12.
 */

public class Inventory extends BaseFragment implements InputDialog.InputListener, ListTaskContract.View{
    public static final String TAG = Inventory.class.getSimpleName();
    private ViewPager mViewPager;
    private List<SwipeCardBean> mData;
    private InputDialog mInputDialog;
    private UserManager mUserManager;
    private String mUsername ;
    private String mDescription;
    private ListTaskContract.Presenter mPresenter;
    private InventoryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_inventory;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {
        mUsername = mUserManager.getUserName();
        showLoading("正在加载数据...");
        mPresenter.getCard(getActivity());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(false, new ShadowTransformer(mViewPager, mAdapter));
        mViewPager.setOffscreenPageLimit(3);
    }

    private void initView() {
        mViewPager = (ViewPager) getActivity().findViewById(R.id.list_vp_list);
        mUserManager = UserManager.getInstance(getActivity());
        mData = new ArrayList<>();
        mAdapter = new InventoryAdapter();
    }

    public void editCard(){
        showInputDialog();
    }

    public void addCard(){
        showInputDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveGetCardEvent(GetCardEvent event){
        boolean isSuccess = event.isSuccess();
        Log.d(TAG, "onReceiveGetCardEvent: " + isSuccess);
        dismissLoading();
        if (isSuccess){
            mData.addAll(event.getmCardList());
            Log.d(TAG, "onReceiveGetCardEvent: " + mData.size());
            mAdapter.addCardView(mData);
            mViewPager.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            showToast("获取数据失败，请检查网络~");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveSendCardEvent(SendCardEvent event){
        boolean isSuccess = event.isSuccess();
        Log.d(TAG, "onReceiveSendCardEvent: " + isSuccess);
        if (isSuccess){
            dismissInputDialog(1);
        } else {
            dismissInputDialog(-1);
            showToast("获取数据失败，请检查网络~");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveImageSelectedFinishedEvent(ImageSelectedFinishedEvent event){
        String path = event.selectedImagesPath.get(0);
        Log.d(TAG, "onReceiveImageSelectedFinishedEvent: " + path + event.position + mUsername);
        //mData.get(mAdapter)
        //mAdapter.notifyItemChanged(event.position - 1);
        mPresenter.uploadPhoto(getActivity(), path, mUsername, event.position);
    }

    @Override
    public String getDescription() {
        return mDescription;
    }

    @Override
    public String getUserName() {
        return mUsername;
    }

    @Override
    public int getPosition() {
        return mAdapter.getCount() + 1;
    }

    /**
     * 弹出输入框
     */
    private void showInputDialog() {
        if (mInputDialog == null){
            mInputDialog = new InputDialog();
            mInputDialog.setInputListener(this);
        }
        mInputDialog.show(getActivity().getSupportFragmentManager(), "inputDialog");
    }

    /**
     * 关闭输入框
     */
    private void dismissInputDialog(int code) {
        if (mInputDialog != null) {
            mInputDialog.dismissInputDialog(code);
        }
    }

    /**
     * 获取输入框的内容
     *
     * @param text
     */
    @Override
    public void onInputComplete(String text) {
        mDescription = text;
        mPresenter.sendCard(getActivity());
        SwipeCardBean swipeCardBean = new SwipeCardBean(mAdapter.getCount()+1,"0",text,System.currentTimeMillis());
        List<SwipeCardBean> list = new ArrayList<>();
        list.add(swipeCardBean);
        mData.addAll(list);
        mAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(mAdapter.getCount() - 1);
    }

    @Override
    public void setPresenter(ListTaskContract.Presenter presenter) {
        if (presenter != null){
            mPresenter = presenter;
        }
    }

}
