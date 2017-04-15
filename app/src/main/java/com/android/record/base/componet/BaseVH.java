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

public class BaseVH<Data> extends RecyclerView.ViewHolder implements View.OnClickListener {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

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

    @Override
    public void onClick(View v) {

    }
}
