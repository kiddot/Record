package com.android.record.main.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.record.R;
import com.android.record.base.componet.BaseActivity;
import com.github.clans.fab.FloatingActionButton;


/**
 * Created by kiddo on 17-3-28.
 */

public class MainActivity extends BaseActivity implements FloatingActionButton.OnClickListener{
    public static final String TAG = MainActivity.class.getSimpleName();

    private FloatingActionButton mFabOne;
    private FloatingActionButton mFabSecond;
    private FloatingActionButton mFabThird;
    private FloatingActionButton mFabFourth;


    public static void startActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void getLayoutBinding() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        initView();
        addListener();
    }

    private void addListener() {
        mFabOne.setOnClickListener(this);
        mFabSecond.setOnClickListener(this);
        mFabThird.setOnClickListener(this);
        mFabFourth.setOnClickListener(this);
    }

    private void initView() {
        mFabOne = (FloatingActionButton) findViewById(R.id.menu_item_one);
        mFabSecond = (FloatingActionButton) findViewById(R.id.menu_item_second);
        mFabThird = (FloatingActionButton) findViewById(R.id.menu_item_third);
        mFabFourth = (FloatingActionButton) findViewById(R.id.menu_item_fourth);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_item_one:
                showToast("one");
                break;
            case R.id.menu_item_second:
                showToast("two");
                break;
            case R.id.menu_item_third:
                break;
            case R.id.menu_item_fourth:
                break;
        }
    }
}
