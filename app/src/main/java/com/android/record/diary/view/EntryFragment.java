package com.android.record.diary.view;

import android.support.v7.widget.RecyclerView;

import com.android.record.R;
import com.android.record.base.componet.BaseFragment;
import com.android.record.diary.adapter.EntryAdapter;
import com.android.record.diary.bean.EntryBean;

import java.util.List;

/**
 * Created by kiddo on 17-4-14.
 */

public class EntryFragment extends BaseFragment{
    public static final String TAG = EntryFragment.class.getSimpleName();
    private List<EntryBean> mEntryList;
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_entry;
    }

    @Override
    protected void init() {
        initView();
        initEntryData();
        EntryAdapter adapter = new EntryAdapter(getActivity(), mEntryList);
        mRecyclerView.setAdapter(adapter);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.RecyclerView_topic);
    }

    private void initEntryData(){
        EntryBean entryBean = new EntryBean();
        entryBean.setName("日记");
        mEntryList.add(entryBean);
    }
}
