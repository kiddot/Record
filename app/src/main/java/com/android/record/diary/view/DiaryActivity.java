package com.android.record.diary.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.record.R;
import com.android.record.base.componet.BaseActivity;
import com.android.record.base.util.ActivityUtils;
import com.android.record.diary.presenter.DiaryPresenter;

/**
 * Created by kiddo on 17-4-13.
 */

public class DiaryActivity extends BaseActivity{
    public static final String TAG = DiaryActivity.class.getSimpleName();
    private DiaryFragment mFragment;
    private DiaryPresenter mDiaryPrsenter;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, DiaryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void getLayoutBinding() {
        setContentView(R.layout.activity_diary);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mFragment = new DiaryFragment();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragment, R.id.diary_fl_container);
        mDiaryPrsenter = new DiaryPresenter(mFragment);
        mFragment.setPresenter(mDiaryPrsenter);
    }
}
