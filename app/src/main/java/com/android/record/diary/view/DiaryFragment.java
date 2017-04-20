package com.android.record.diary.view;

import android.util.Log;

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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiddo on 17-4-13.
 */

public class DiaryFragment extends BaseFragment implements DiaryTaskContract.View{
    public static final String TAG = DiaryFragment.class.getSimpleName();
    private String mUserName;
    private UserManager mUserManager;
    private DiaryTaskContract.Presenter mPresenter;
    private DiaryAdapter mAdapter;
    private List<Diary> mDiaryList;

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
        mUserManager = UserManager.getInstance(getActivity());
        mDiaryList = new ArrayList<>();
        mAdapter = new DiaryAdapter(getActivity(),mDiaryList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveDiaryEvent(ReceiveDiaryEvent event){
        boolean success = event.isSuccess();
        if (success){
            mDiaryList.addAll(event.getmData());
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
        return null;
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

    }
}
