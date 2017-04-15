package com.android.record.base.componet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by kiddo on 17-4-15.
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseVH>{
    protected List<T> mDataList;
    private Context mContext;
    protected int mLayoutId;
    protected LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public BaseRvAdapter(List<T> mDataList, Context mContext, int mLayoutId) {
        this.mDataList = mDataList;
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public BaseVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseVH holder, int position) {
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int position);
    }

}
