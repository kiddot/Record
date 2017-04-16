package com.android.record.diary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.android.record.R;
import com.android.record.base.componet.BaseRvAdapter;
import com.android.record.base.componet.BaseVH;
import com.android.record.diary.bean.EntryBean;

import java.util.List;

/**
 * Created by kiddo on 17-4-14.
 */

public class EntryAdapter extends BaseRvAdapter {
    public static final String TAG = EntryAdapter.class.getSimpleName();
    public static final int NORMAL_ENTRY = 0;
    private List<EntryBean> mData;

    public EntryAdapter(Context context, List list) {
        super(context, list);
        mData = list;
    }

    @Override
    public int getCustomViewType(int position) {
        return NORMAL_ENTRY;
    }

    @Override
    public BaseVH createCustomViewHolder(ViewGroup parent, int viewType) {
        return new EntryHolder(R.layout.item_topic_diary, parent);
    }

    @Override
    public void bindCustomViewHolder(BaseVH holder, int position) {
        EntryHolder entryHolder = (EntryHolder) holder;
        ((EntryHolder) holder).entryType.setText(mData.get(position).getName());
    }

    public class EntryHolder extends BaseVH{
        TextView entryType;

        public EntryHolder(int resId, ViewGroup parent) {
            super(resId, parent);
        }
    }


}
