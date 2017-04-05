package com.android.record.base.componet.image;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.record.R;
import com.android.record.base.componet.BasePopupWindowForRecyclerView;
import com.android.record.base.componet.event.ImageFolderChangeEvent;
import com.android.record.base.componet.moudule.ImageFolderBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by yanghuang on 2016/7/16.
 *
 * @author yanghuang
 * @date 2016/7/16
 */
public class ImageFolderListPopupwindow extends BasePopupWindowForRecyclerView<ImageFolderBean
        , ImageFolderListPopupwindow.ImageFolderViewHolder> {


    public ImageFolderListPopupwindow(View contentView, int recyclerViewId, int width, int height
            , boolean focusable, List<ImageFolderBean> datas) {
        super(contentView, recyclerViewId, width, height, focusable, datas);
    }

    @Override
    protected void onBindVH(ImageFolderViewHolder holder, ImageFolderBean item, int position) {
        holder.ivCover.setImageResource(R.mipmap.base_img_null);
        if (item.getFirstImagePath().endsWith(".mp4")
                || item.getFirstImagePath().endsWith(".3gp")
                || item.getFirstImagePath().endsWith(".avi")
                || item.getFirstImagePath().endsWith(".flv")) {
            // 获取视频的缩略图
            CommonImageLoader.displayImage(item.getFirstImagePath(), holder.ivCover
                    , CommonImageLoader.MEMORY_CACHE_OPTIONS);
            holder.tvCount.setText(item.getCount() + "个");
        } else {
            ImageLoader.getInstance().displayImage(
                    ImageDownloader.Scheme.FILE.wrap(item.getFirstImagePath())
                    , holder.ivCover
                    , CommonImageLoader.MEMORY_CACHE_OPTIONS);
            holder.tvCount.setText(item.getCount() + "张");
        }
        holder.tvName.setText(item.getName());
    }

    @Override
    protected ImageFolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageFolderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_choose_img_popup_folder, null));
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    @Override
    public void init() {

    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {

    }

    public class ImageFolderViewHolder extends RecyclerView.ViewHolder{
        ImageView ivCover;
        TextView tvCount;
        TextView tvName;

        public ImageFolderViewHolder(View itemView) {
            super(itemView);
            ivCover = (ImageView) itemView.findViewById(R.id.choose_img_popup_iv_folder);
            tvCount = (TextView) itemView.findViewById(R.id.choose_img_popup_tv_folder_count);
            tvName = (TextView) itemView.findViewById(R.id.choose_img_popup_tv_folder_name);
        }
    }

    @Override
    protected void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        EventBus.getDefault().post(new ImageFolderChangeEvent(mDatas.get(position)));
    }
}
