package com.android.record.list.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.android.record.R;
import com.android.record.base.componet.image.CommonImageLoader;
import com.android.record.bean.SwipeCardBean;
import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;

import java.util.List;

/**
 * Created by kiddo on 17-3-29.
 */

public class ListAdapter extends CommonAdapter<SwipeCardBean> {
    public static final String TAG = ListAdapter.class.getSimpleName();

    public ListAdapter(Context context, List data, int layoutId) {
        super(context, data, layoutId);
    }


    @Override
    public void convert(ViewHolder viewHolder, SwipeCardBean swipeCardBean) {
        viewHolder.setText(R.id.tvName, swipeCardBean.getName());
        viewHolder.setText(R.id.tvPrecent, swipeCardBean.getPosition() + " /" + mDatas.size());
        if (swipeCardBean.getUrl() != null && !swipeCardBean.getUrl().equals("0")){
            CommonImageLoader.displayImage(swipeCardBean.getUrl(), (ImageView) viewHolder.getView(R.id.list_iv_photo), CommonImageLoader.DOUBLE_CACHE_OPTIONS);
        }
            //Picasso.with(mContext).load(swipeCardBean.getUrl()).into((ImageView) viewHolder.getView(R.id.list_iv_photo));
    }
}
