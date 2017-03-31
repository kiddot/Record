package com.android.record.list.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.android.record.R;
import com.android.record.base.componet.BaseFragment;
import com.android.record.base.dao.AppDaoManager;
import com.android.record.base.dao.DaoManager;
import com.android.record.bean.SwipeCardBean;
import com.android.record.common.dao.RecordDaoHelper;
import com.android.record.common.dao.RecordDaoManager;
import com.android.record.common.dialog.InputDialog;
import com.android.record.common.sp.UserManager;
import com.android.record.list.TanTanCallback;
import com.android.record.list.adapter.ListAdapter;
import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiddo on 17-3-31.
 */

public class ListFragment extends BaseFragment implements InputDialog.InputListener{
    public static final String TAG = ListFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private List<SwipeCardBean> mData;
    private InputDialog mInputDialog;
    private UserManager mUserManager;
    private String mUsername ;


    private void initData() {
        mUsername = mUserManager.getUserName();
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
        mUserManager = UserManager.getInstance(getActivity());
        mAdapter = new ListAdapter(getActivity(), mData = SwipeCardBean.initDatas(), R.layout.part_item_list);
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
        showToast("click");
        DaoManager daoManager = AppDaoManager.get(mUsername);
        if (daoManager ==null){
            daoManager = new RecordDaoManager(getActivity(), mUsername, RecordDaoHelper.getInstance());
        }
        List<SwipeCardBean> list = new ArrayList<>();
        SwipeCardBean swipeCardBean = new SwipeCardBean(mAdapter.getItemCount()+1,null,"ldk",2017);
        list.add(swipeCardBean);
        daoManager.insert(swipeCardBean);
        //TODO
    }

    /**
     * 弹出输入框
     */
    private void showInputDialog() {
        mInputDialog = new InputDialog();
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
        //TODO
    }
}
