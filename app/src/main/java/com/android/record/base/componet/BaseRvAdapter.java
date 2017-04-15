package com.android.record.base.componet;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by kiddo on 17-4-15.
 */

public abstract class BaseRvAdapter<Data, CustomViewHolder extends BaseVH<Data>> extends RecyclerView.Adapter<CustomViewHolder>{
    protected List<Data> mDataList;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;


    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int position);
    }

}
