package com.android.record.diary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.android.record.diary.bean.EntryBean;

import java.util.List;

/**
 * Created by kiddo on 17-4-14.
 */

public class EntryAdapter extends BaseAdapter {
    private List<EntryBean> mData;

    public EntryAdapter(List<EntryBean> data) {
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
