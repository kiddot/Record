package com.android.record.base.componet;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by yanghuang on 2016/7/16.
 * 展现列表等的PopupWindow使用的超类
 * @author yanghuang
 * @date 2016/7/16
 */
public abstract class BasePopupWindowForRecyclerView<T, V extends RecyclerView.ViewHolder> extends BasePopupWindow {

    /**
     * ListView的数据集
     */
    protected List<T> mDatas;


    public BasePopupWindowForRecyclerView(View contentView, int recyclerViewId, int width, int height,
                                          boolean focusable, List<T> datas) {
        super(contentView, width, height, focusable, new Object[0]);
        if(datas != null) {
            mDatas = datas;
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(recyclerViewId);
        recyclerView.setLayoutManager(getLayoutManager());
        ClickableAdapter adapter = new ClickableAdapter() {
            @Override
            public void onBindVH(RecyclerView.ViewHolder holder, int position) {
                BasePopupWindowForRecyclerView.this.onBindVH((V) holder, mDatas.get(position), position);
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return BasePopupWindowForRecyclerView.this.onCreateViewHolder(parent, viewType);
            }

            @Override
            public int getItemCount() {
                return mDatas.size();
            }
        };
        adapter.setOnItemClickListener(new ClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                BasePopupWindowForRecyclerView.this.onItemClick(view, position);
            }
        });
        adapter.setOnLongClickListener(new ClickableAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                return BasePopupWindowForRecyclerView.this.onItemLongClick(view, position);
            }
        });
        recyclerView.setAdapter(adapter);

    }

    protected abstract void onBindVH(V holder, T item, int position);

    protected abstract V onCreateViewHolder(ViewGroup parent, int viewType);


    protected void onItemClick(View view, int position) {}

    protected boolean onItemLongClick(View view, int position) { return false; }

    public abstract RecyclerView.LayoutManager getLayoutManager();
}
