package com.android.record.base.componet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kiddo on 17-4-15.
 */

public class BaseVH<Data> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;
    protected int mPosition;
    private BaseRvAdapter.OnItemClickListener mOnItemClickListener;
    private BaseRvAdapter.OnItemLongClickListener mOnItemLongClickListener;

    public BaseVH(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static BaseVH get(Context context, ViewGroup parent, int layoutId){
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        BaseVH holder = new BaseVH(context, itemView, parent);
        return holder;
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public BaseRvAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(BaseRvAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public BaseRvAdapter.OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public void setOnItemLongClickListener(BaseRvAdapter.OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public final void setPosition(int position) {
        mPosition = position;
    }

    public int getRealPosition(){
        return mPosition;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, mPosition);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(mOnItemLongClickListener!=null){
            mOnItemLongClickListener.onItemLongClick(v,mPosition);
        }
        return true;
    }
}
