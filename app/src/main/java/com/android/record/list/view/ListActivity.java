package com.android.record.list.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.android.record.R;
import com.android.record.base.componet.BaseActivity;

/**
 * Created by kiddo on 17-3-29.
 */

public class ListActivity extends BaseActivity{
    public static final String TAG = ListActivity.class.getSimpleName();

//    private RecyclerView mRecyclerView;
//    private ListAdapter mAdapter;
//    private List<SwipeCardBean> mData;
//    private InputDialog mInputDialog;
//    private UserManager mUserManager;
//    private String mUsername ;
    private ListFragment mFragment = null;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, ListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void getLayoutBinding() {
        setContentView(R.layout.activity_list);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.list_fl_container, mFragment).commit();
//        initView();
//        initData();
//        setConfig();
    }

//    private void initData() {
//        mUsername = mUserManager.getUserName();
//    }
//
////    public void initView(){
////        mUserManager = UserManager.getInstance(this);
////        mAdapter = new ListAdapter(this, mData = SwipeCardBean.initDatas(),R.layout.part_item_list);
////        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
////        mRecyclerView.setLayoutManager(new OverLayCardLayoutManager());
////        mRecyclerView.setAdapter(mAdapter);
////    }
////
////    public void setConfig(){
////        CardConfig.initConfig(this);
////        CardConfig.MAX_SHOW_COUNT = 6;
////        final TanTanCallback callback = new TanTanCallback(mRecyclerView, mAdapter, mData);
////
////        //测试竖直滑动是否已经不会被移除屏幕
////        //callback.setHorizontalDeviation(Integer.MAX_VALUE);
////
////        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
////        itemTouchHelper.attachToRecyclerView(mRecyclerView);
////    }
////
    public void editCard(View view){
        mFragment.editCard();
    }
////
    public void search(View view){
        mFragment.search();
    }
////
    public void addCard(View view){
        mFragment.addCard();
    }
////
////    /**
////     * 弹出输入框
////     */
////    private void showInputDialog() {
////        mInputDialog = new InputDialog();
////        mInputDialog.show(getSupportFragmentManager(), "inputDialog");
////    }
////
////    /**
////     * 关闭输入框
////     */
////    private void dismissInputDialog(int code) {
////        if (mInputDialog != null) {
////            mInputDialog.dismissInputDialog(code);
////        }
////    }
////
////    /**
////     * 获取输入框的内容
////     *
////     * @param text
////     */
////    @Override
////    public void onInputComplete(String text) {
////        //TODO
////    }
}
