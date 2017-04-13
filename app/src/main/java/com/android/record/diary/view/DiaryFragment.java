package com.android.record.diary.view;

import com.android.record.base.componet.BaseFragment;
import com.android.record.diary.contract.DiaryTaskContract;

/**
 * Created by kiddo on 17-4-13.
 */

public class DiaryFragment extends BaseFragment implements DiaryTaskContract.View{
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void init() {

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
