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

public class BaseVH extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;
    protected int mPosition;
    private BaseRvAdapter.OnItemClickListener mOnItemClickListener;
    private BaseRvAdapter.OnItemLongClickListener mOnItemLongClickListener;

    /**
     * 构造ViewHolder
     * @param parent 父类容器
     * @param resId 布局资源文件id
     */
    public BaseVH(int resId, ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
        mViews = new SparseArray<>();
    }

    /**
     * 构建ViewHolder
     * @param view 布局View
     */
    public BaseVH(View view) {
        super(view);
        mViews = new SparseArray<>();
    }

    /**
     * 获取布局中的View
     * @param viewId view的Id
     * @param <T> View的类型
     * @return view
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 获取Context实例
     * @return context
     */
    protected Context getContext() {
        return itemView.getContext();
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
