package com.android.record.diary.view;

import com.android.record.R;
import com.android.record.base.componet.BaseFragment;
import com.android.record.diary.contract.DiaryTaskContract;
import com.android.record.list.event.SendCardEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by kiddo on 17-4-13.
 */

public class DiaryFragment extends BaseFragment implements DiaryTaskContract.View{


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_diary;
    }

    @Override
    protected void init() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveSendCardEvent(SendCardEvent event){

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
