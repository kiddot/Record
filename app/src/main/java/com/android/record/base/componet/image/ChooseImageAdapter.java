package com.android.record.base.componet.image;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.record.R;
import com.android.record.base.componet.ClickableAdapter;
import com.nostra13.universalimageloader.core.download.ImageDownloader;


/**
 * Created by yanghuang on 2016/7/15.
 * 选择图片里的适配器
 *
 * @author yanghuang
 * @date 2016/7/15
 * <p>
 * Changed by yanghuang on 2016/7/22
 * 1.把点击处理移至activity，并增加单选模式，
 * 2.使用parent获得context，不需要传入context
 */
public class ChooseImageAdapter extends ClickableAdapter<ChooseImageAdapter.ChooseImageViewHolder> {
    public static final String TAG = ChooseImageAdapter.class.getSimpleName();

    private ChooseImagePresenter mChooseImagePresenter;

    public ChooseImageAdapter(ChooseImagePresenter mChooseImagePresenter) {
        this.mChooseImagePresenter = mChooseImagePresenter;
    }

    @Override
    public void onBindVH(final ChooseImageViewHolder holder, final int position) {

        holder.ivImage.setImageResource(R.mipmap.base_img_null);
        holder.ivSelectState.setImageResource(R.mipmap.choose_image_unselected);

        CommonImageLoader.displayImage(ImageDownloader.Scheme.FILE.wrap(mChooseImagePresenter.getAllImgs().get(position)), holder.ivImage
                , CommonImageLoader.MEMORY_CACHE_OPTIONS);

        final ImageView ivImageView = holder.ivImage;
        final ImageView ivSelect = holder.ivSelectState;
        final String imageUrl = mChooseImagePresenter.getAllImgs().get(position);
        Log.d(TAG, "onBindVH: " + imageUrl);

        ivImageView.setColorFilter(null);
        holder.itemView.setTag(position);

        holder.ivSelectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = mChooseImagePresenter.selectOrUndo(position);
                mChooseImagePresenter.notifyViewItemCheckedChanged(position, isChecked);
            }
        });

        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (mChooseImagePresenter.isSelected(imageUrl)) {
            ivSelect.setImageResource(R.mipmap.choose_image_selected);
            ivImageView.setColorFilter(Color.parseColor("#77000000"));
        }
    }

    @Override
    public ChooseImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChooseImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_choose_image, null, false));
    }

    @Override
    public int getItemCount() {
        return mChooseImagePresenter.getAllImgs() == null ? 0 : mChooseImagePresenter.getAllImgs().size();
    }

    public static class ChooseImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;
        public ImageView ivSelectState;

        public ChooseImageViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.choose_img_iv);
            ivSelectState = (ImageView) itemView.findViewById(R.id.choose_img_iv_select_state);
        }
    }
}
