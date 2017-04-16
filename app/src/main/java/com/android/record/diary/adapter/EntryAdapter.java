package com.android.record.diary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.record.R;
import com.android.record.base.componet.BaseRvAdapter;
import com.android.record.base.componet.BaseVH;
import com.android.record.diary.bean.EntryBean;

import java.util.List;

/**
 * Created by kiddo on 17-4-14.
 */

public class EntryAdapter extends BaseRvAdapter<EntryBean, EntryAdapter.EntryHolder> {
    public static final String TAG = EntryAdapter.class.getSimpleName();
    public static final int NORMAL_ENTRY = 0;
    private List<EntryBean> mData;

    public EntryAdapter(Context context, List list) {
        super(context, list);
        Log.d(TAG, "EntryAdapter: ");
        mData = list;
    }

    @Override
    public int getCustomViewType(int position) {
        return NORMAL_ENTRY;
    }

    @Override
    public BaseVH createCustomViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "createCustomViewHolder: ");
        return new EntryHolder(R.layout.item_topic_diary, parent);
    }

    @Override
    public void bindCustomViewHolder(BaseVH holder, int position) {
        Log.d(TAG, "bindCustomViewHolder: " + mData.get(position).getName());
        ((EntryHolder) holder).entryType.setText(mData.get(position).getName());
        ((EntryHolder) holder).icon.setImageResource(R.mipmap.ic_topic_diary);
    }

    public class EntryHolder extends BaseVH{
        TextView entryType;
        ImageView icon;

        public EntryHolder(int resId, ViewGroup parent) {
            super(resId, parent);
            entryType = (TextView) itemView.findViewById(R.id.TV_topic_title);
            icon = (ImageView) itemView.findViewById(R.id.IV_topic_icon);
        }
    }


}
