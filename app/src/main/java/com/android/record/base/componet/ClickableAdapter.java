package com.android.record.base.componet;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 封装了长按和点击的RecyclerView的Adapter(使用TAG实现)
 *
 * @author Hans
 * @date 16.7.11
 * <p/>
 * Warning By Hans 2016.7.22 :
 * 目前发现了一个BUG... 通过长按删除并通过[RecyclerView.Adapter.notifyItemRemoved局部刷新的方法刷新]之后,
 * 我们会根据position在List中对应的数据项目,但是由于View已经被移除了
 * 而他们的position是根据他们的一开始列表的位置进行设置的,所以会造成原本的数据移除得并不正确....
 * </p>
 * 解决思路1:
 * 找一个办法对删除的position之后的holder中的itemView重新设置TAG(重设位置)
 * 解决进度:未解决
 * </p>
 * 解决思路2:
 * 不使用RecyclerView.Adapter.notifyItemRemoved局部刷新,使用RecyclerView.Adapter.notifyDataSetChanged全部重绘
 * 解决进度:可解决(但没有充分利用高效性)
 * </p>
 * 解决思路3:(update by Hans 2016.7.25)
 * 通过其他方法解决,目前已想出通过RecyclerView本身获取item的ViewHolder位置定位 position. 不使用TAG方式{@link ClickableRecyclerAdapter}
 * 解决进度:可解决(充分利用 RecyclerView局部刷新的高效性)
 */
public abstract class ClickableAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> implements View.OnClickListener, View.OnLongClickListener {
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;

    private static final int TAG_KEY = 0x12345678;

    @Override
    public void onBindViewHolder(V holder, int position) {
        if (mOnItemClickListener != null)
            holder.itemView.setOnClickListener(this);
        if (mOnItemLongClickListener != null)
            holder.itemView.setOnLongClickListener(this);
        holder.itemView.setTag(TAG_KEY, position);
        onBindVH(holder, position);
    }

    abstract public void onBindVH(V holder, int position);


    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(TAG_KEY);
        mOnItemClickListener.onItemClick(v, position);
    }

    @Override
    public boolean onLongClick(View v) {
        int position = (int) v.getTag(TAG_KEY);
        return mOnItemLongClickListener.onItemLongClick(v, position);

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }
}
