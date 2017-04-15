package com.android.record.base.componet.moudule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.record.base.componet.BaseVH;

/**
 * Created by kiddo on 17-4-15.
 */

public abstract class BaseHFAdapter<M, VH extends BaseVH> extends RecyclerView.Adapter<BaseVH>{
    public static final String TAG = BaseHFAdapter.class.getSimpleName();

    public static final int VIEW_TYPE_HEADER = 1024;
    public static final int VIEW_TYPE_FOOTER = 2048;

    protected View headerView;
    protected View footerView;

    protected Context mContext;

    public BaseHFAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public BaseVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER){
            return new BaseVH(headerView);
        } else if (viewType == VIEW_TYPE_FOOTER){
            return new BaseVH(footerView);
        } else {
            return createCustomViewHolder(parent, viewType);
        }
    }

    /**
     * 创建自定义的ViewHolder
     *
     * @param parent 父类容器
     * @param viewType view类型{@link #getItemViewType(int)}
     * @return ViewHolder
     */
    public abstract VH createCustomViewHolder(ViewGroup parent, int viewType);

    /**
     * 绑定自定义的ViewHolder
     *
     * @param holder ViewHolder
     * @param position 位置
     */
    public abstract void bindCustomViewHolder(VH holder, int position);

    @Override
    public void onBindViewHolder(BaseVH holder, int position) {
        switch (holder.getItemViewType()){
            case VIEW_TYPE_HEADER:
            case VIEW_TYPE_FOOTER:
                break;
            default:
                bindCustomViewHolder((VH) holder, position);
        }
    }

    /**
     * 添加HeaderView
     *
     * @param headerView 顶部View对象
     */
    public void addHeaderView(View headerView) {
        if (headerView == null) {
            Log.w(TAG, "add the header view is null");
            return ;
        }
        this.headerView = headerView;
        notifyDataSetChanged();
    }

    /**
     * 移除HeaderView
     */
    public void removeHeaderView() {
        if (headerView != null) {
            headerView = null;
            notifyDataSetChanged();
        }
    }

    /**
     * 添加FooterView
     *
     * @param footerView View对象
     */
    public void addFooterView(View footerView) {
        if (footerView == null) {
            Log.w(TAG, "add the footer view is null");
            return;
        }
        this.footerView = footerView;
        notifyDataSetChanged();
    }

    /**
     * 移除FooterView
     */
    public void removeFooterView() {
        if (footerView != null) {
            footerView = null;
            notifyDataSetChanged();
        }
    }

    /**
     * 获取附加View的数量,包括HeaderView和FooterView
     *
     * @return 数量
     */
    public int getExtraViewCount() {
        int extraViewCount = 0;
        if (headerView != null) {
            extraViewCount++;
        }
        if (footerView != null) {
            extraViewCount++;
        }
        return extraViewCount;
    }

    /**
     * 获取顶部附加View数量,即HeaderView数量
     * @return 数量
     */
    public int getHeaderExtraViewCount() {
        return headerView == null ? 0 : 1;
    }

    /**
     * 获取底部附加View数量,即FooterView数量
     * @return 数量,0或1
     */
    public int getFooterExtraViewCount() {
        return footerView == null ? 0 : 1;
    }

    @Override
    public abstract long getItemId(int position);

}
