package com.android.record.list.adapter;

import android.support.v7.widget.CardView;

/**
 * Created by kiddo on 17-4-12.
 */

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 16;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
