package com.android.record.diary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.record.R;
import com.android.record.base.componet.BaseRvAdapter;
import com.android.record.base.componet.BaseVH;

import java.util.List;

/**
 * Created by kiddo on 17-4-18.
 */

public class DiaryAdapter extends BaseRvAdapter {

    public DiaryAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    @Override
    public BaseVH createCustomViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void bindCustomViewHolder(BaseVH holder, int position) {

    }

    public class DiaryHolder extends BaseVH{
        private TextView date;
        private TextView week;
        private TextView time;
        private TextView title;
        private TextView content;

        public DiaryHolder(int resId, ViewGroup parent) {
            super(resId, parent);
            date = (TextView) itemView.findViewById(R.id.part_diary_tv_date);
            week = (TextView) itemView.findViewById(R.id.part_diary_tv_week);
            time = (TextView) itemView.findViewById(R.id.part_diary_tv_time);
            title = (TextView) itemView.findViewById(R.id.part_diary_tv_title);
            content = (TextView) itemView.findViewById(R.id.part_diary_tv_content);
        }
    }

}
