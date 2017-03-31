package com.android.record.list.view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.android.record.R;
import com.android.record.base.componet.BaseFragment;
import com.android.record.bean.SwipeCardBean;
import com.android.record.common.dialog.InputDialog;
import com.android.record.common.sp.UserManager;
import com.android.record.list.event.GetCardEvent;
import com.android.record.list.TanTanCallback;
import com.android.record.list.adapter.ListAdapter;
import com.android.record.list.contract.ListTaskContract;
import com.android.record.list.event.SendCardEvent;
import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiddo on 17-3-31.
 */

public class ListFragment extends BaseFragment implements InputDialog.InputListener, ListTaskContract.View{
    public static final String TAG = ListFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private List<SwipeCardBean> mData;
    private InputDialog mInputDialog;
    private UserManager mUserManager;
    private String mUsername ;
    private String mDescription;
    private ListTaskContract.Presenter mPresenter;


    private void initData() {
        mUsername = mUserManager.getUserName();
        showLoading("正在加载数据...");
        mPresenter.getCard(getActivity());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void init() {
        initView();
        initData();
        setConfig();
    }

    public void initView(){
        mData = new ArrayList<>();
        mUserManager = UserManager.getInstance(getActivity());
        mAdapter = new ListAdapter(getActivity(), mData, R.layout.part_item_list);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new OverLayCardLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setConfig(){
        CardConfig.initConfig(getActivity());
        CardConfig.MAX_SHOW_COUNT = 6;
        final TanTanCallback callback = new TanTanCallback(mRecyclerView, mAdapter, mData);

        //测试竖直滑动是否已经不会被移除屏幕
        //callback.setHorizontalDeviation(Integer.MAX_VALUE);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void editCard(){
        showInputDialog();
    }

    public void search(){
        //TODO
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
        return mAdapter.getItemCount() + 1;
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
    }

    @Override
    public void setPresenter(ListTaskContract.Presenter presenter) {
        if (presenter != null){
            mPresenter = presenter;
        }
    }
}
