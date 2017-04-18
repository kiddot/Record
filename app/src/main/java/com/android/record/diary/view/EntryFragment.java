package com.android.record.diary.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.record.R;
import com.android.record.base.componet.BaseFragment;
import com.android.record.base.componet.BaseRvAdapter;
import com.android.record.diary.adapter.EntryAdapter;
import com.android.record.diary.bean.EntryBean;
import com.android.record.list.event.SendCardEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiddo on 17-4-14.
 */

public class EntryFragment extends BaseFragment implements BaseRvAdapter.OnItemClickListener{
    public static final String TAG = EntryFragment.class.getSimpleName();
    private List<EntryBean> mEntryList;
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_entry;
    }

    @Override
    protected void init() {
        initEntryData();
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.RecyclerView_topic);
        EntryAdapter adapter = new EntryAdapter(getActivity(), mEntryList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(manager);
    }

    private void initEntryData(){
        mEntryList = new ArrayList<>();
        EntryBean entryBean = new EntryBean();
        entryBean.setName("日记");
        mEntryList.add(entryBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveSendCardEvent(SendCardEvent event){

    }

    @Override
    public void onItemClick(View itemView, int position) {
        Log.d(TAG, "onItemClick: position:" + position);
        if (position == 0){
            //日记模块
            DiaryActivity.startActivity(getActivity());
        }
    }
}
