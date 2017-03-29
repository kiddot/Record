package com.android.record.list.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.android.record.R;
import com.android.record.list.SwipeCardBean;
import com.android.record.list.view.ListActivity;
import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kiddo on 17-3-29.
 */

public class ListAdapter extends CommonAdapter<SwipeCardBean> {
    public static final String TAG = ListAdapter.class.getSimpleName();

    public ListAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
    }


    @Override
    public void convert(ViewHolder viewHolder, SwipeCardBean swipeCardBean) {
        viewHolder.setText(R.id.tvName, swipeCardBean.getName());
        viewHolder.setText(R.id.tvPrecent, swipeCardBean.getPostition() + " /" + mDatas.size());
        Picasso.with(mContext).load(swipeCardBean.getUrl()).into((ImageView) viewHolder.getView(R.id.iv));
    }
}
