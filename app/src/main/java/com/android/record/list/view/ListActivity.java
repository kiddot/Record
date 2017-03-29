package com.android.record.list.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.android.record.R;
import com.android.record.base.componet.BaseActivity;
import com.android.record.list.SwipeCardBean;
import com.android.record.list.TanTanCallback;
import com.android.record.list.adapter.ListAdapter;
import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;

import java.util.List;

/**
 * Created by kiddo on 17-3-29.
 */

public class ListActivity extends BaseActivity {
    public static final String TAG = ListActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private List<SwipeCardBean> mDatas;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, ListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void getLayoutBinding() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list);
        mAdapter = new ListAdapter(this,mDatas = SwipeCardBean.initDatas(),R.layout.part_item_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new OverLayCardLayoutManager());
        mRecyclerView.setAdapter(mAdapter);

        CardConfig.initConfig(this);

        final TanTanCallback callback = new TanTanCallback(mRecyclerView, mAdapter, mDatas);

        //测试竖直滑动是否已经不会被移除屏幕
        //callback.setHorizontalDeviation(Integer.MAX_VALUE);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    public void editCard(View view){

    }
}
