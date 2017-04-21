package com.android.record.diary.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.record.R;
import com.android.record.base.componet.BaseFragment;
import com.android.record.base.util.Toastor;
import com.android.record.bean.Diary;
import com.android.record.common.sp.UserManager;
import com.android.record.diary.adapter.DiaryAdapter;
import com.android.record.diary.contract.DiaryTaskContract;
import com.android.record.diary.event.ReceiveDiaryEvent;
import com.android.record.list.contract.ListTaskContract;
import com.android.record.list.event.SendCardEvent;
import com.github.clans.fab.FloatingActionButton;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by kiddo on 17-4-13.
 */

public class DiaryFragment extends BaseFragment implements DiaryTaskContract.View,WaveSwipeRefreshLayout.OnRefreshListener,FloatingActionButton.OnClickListener{
    public static final String TAG = DiaryFragment.class.getSimpleName();
    private String mUserName;
    private UserManager mUserManager;
    private DiaryTaskContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private DiaryAdapter mAdapter;
    private List<Diary> mDiaryList;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private FloatingActionButton mFabOne;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_diary;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {
        mUserName = mUserManager.getUserName();
        showLoading("正在加载数据...");
        mPresenter.getDiary(getActivity());
    }

    private void initView(){
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) getActivity().findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        mFabOne = (FloatingActionButton) getActivity().findViewById(R.id.menu_item_one);
        mFabOne.setOnClickListener(this);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.diary_rv_diary);
        mUserManager = UserManager.getInstance(getActivity());
        mDiaryList = new ArrayList<>();
        mAdapter = new DiaryAdapter(getActivity(),mDiaryList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveDiaryEvent(ReceiveDiaryEvent event){
        boolean success = event.isSuccess();
        if (success){
            dismissLoading();
            mDiaryList.addAll(event.getmData());
            mAdapter = new DiaryAdapter(getActivity(),mDiaryList);
            mRecyclerView.setAdapter(mAdapter);
            Log.d(TAG, "onReceiveDiaryEvent: 获取日记成功");
            mAdapter.notifyDataSetChanged();
        } else {
            showToast("获取数据失败，请检查网络～");
        }
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public String getUserName() {
        return mUserName;
    }

    @Override
    public String getDiaryTime() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setPresenter(DiaryTaskContract.Presenter presenter) {
        if (presenter != null){
            mPresenter = presenter;
        }
    }

    @Override
    public void onRefresh() {
        //TODO 执行刷新数据操作
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_item_one:
                //TODO 编辑日记
                break;
        }
    }
}
