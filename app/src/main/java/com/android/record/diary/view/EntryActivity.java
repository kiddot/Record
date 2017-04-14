package com.android.record.diary.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.record.R;
import com.android.record.base.componet.BaseActivity;
import com.android.record.base.util.ActivityUtils;

/**
 * Created by kiddo on 17-4-14.
 */

public class EntryActivity extends BaseActivity {
    public static final String TAG = EntryActivity.class.getSimpleName();
    private EntryFragment mFragment;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, EntryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void getLayoutBinding() {
        setContentView(R.layout.activity_item);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mFragment = new EntryFragment();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragment, R.id.diary_fl_container);
    }
}
