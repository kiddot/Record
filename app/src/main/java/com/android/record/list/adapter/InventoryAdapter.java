package com.android.record.list.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.record.R;
import com.android.record.base.componet.image.ChooseImageActivity;
import com.android.record.base.componet.image.CommonImageLoader;
import com.android.record.bean.SwipeCardBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiddo on 17-4-12.
 */

public class InventoryAdapter extends PagerAdapter implements CardAdapter{
    public static final String TAG = InventoryAdapter.class.getSimpleName();
    private List<CardView> mView;
    private List<SwipeCardBean> mData;
    private float mBaseElevation;

    public InventoryAdapter() {
        mData = new ArrayList<>();
        mView = new ArrayList<>();
    }


    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mView.get(position);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        Log.d(TAG, "instantiateItem: ");
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.part_item_list, container, false);
        container.addView(view);
        CardView cardView = (CardView) view.findViewById(R.id.part_cv_inventory);

        if (mBaseElevation == 0){
            mBaseElevation = cardView.getCardElevation();
        }

        //获取实例
        ImageView ivPhoto = (ImageView) view.findViewById(R.id.part_iv_photo);
        TextView tvDescription = (TextView) view.findViewById(R.id.part_tv_description);
        Button btnEdit = (Button) view.findViewById(R.id.part_bt_edit);
        TextView tvPosition = (TextView) view.findViewById(R.id.part_tv_position);

        //获取数据
        SwipeCardBean swipeCardBean = mData.get(position);
        //设置数据
        if (swipeCardBean.getUrl() != null && !swipeCardBean.getUrl().equals("0")){
            CommonImageLoader.displayImage(swipeCardBean.getUrl(), ivPhoto, CommonImageLoader.DOUBLE_CACHE_OPTIONS);
        } else {
            ivPhoto.setBackground(container.getContext().getResources().getDrawable(R.mipmap.fragment_list_add));
        }
        tvDescription.setText(swipeCardBean.getName());
        tvPosition.setText((position + 1) + "/" + mData.size());
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImageActivity.startActivityInSingleMode(container.getContext(), 1, 1, position + 1);
            }
        });
//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mView.add(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void addCardView(List<SwipeCardBean> list){
        mView.clear();
        mData.clear();
        for (int i =0 ; i < list.size(); i ++){
            mView.add(null);
        }
        mData.addAll(list);
        notifyDataSetChanged();
    }
}
