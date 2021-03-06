package com.android.record.list.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.record.R;
import com.android.record.base.componet.BaseActivity;
import com.android.record.base.util.ActivityUtils;
import com.android.record.list.presenter.ListPresenter;

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
    private Inventory mInventory;
    private ListPresenter mListPresenter;

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
        mInventory = new Inventory();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mInventory, R.id.list_fl_container);
        mListPresenter = new ListPresenter(mInventory);
        mInventory.setPresenter(mListPresenter);
    }

//    public void back(View view){
//        mFragment.back();
//    }
    public void addCard(View view){
        mInventory.addCard();
    }

}
