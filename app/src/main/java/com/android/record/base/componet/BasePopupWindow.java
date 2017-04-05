package com.android.record.base.componet;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by yanghuang on 2016/7/16.
 *
 * @author yanghuang
 * @date 2016/7/16
 */
public abstract class BasePopupWindow extends PopupWindow {
    protected Context mContext;
    /**
     * 布局文件的最外层View
     */
    protected View mContentView;



    public BasePopupWindow(View contentView, int width, int height, boolean focusable) {
        this(contentView, width, height, focusable, new Object[0]);
    }

    public BasePopupWindow(View contentView, int width, int height,
                           boolean focusable, Object... params) {
        super(contentView, width, height, focusable);
        this.mContentView = contentView;
        mContext = contentView.getContext();

        if (params != null && params.length > 0) {
            beforeInitWeNeedSomeParams(params);
        }

        setBackgroundDrawable(new ColorDrawable(0));
        setTouchable(true);
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        init();
    }

    public abstract void init();

    protected abstract void beforeInitWeNeedSomeParams(Object... params);

    public View findViewById(int id) {
        return mContentView.findViewById(id);
    }

    protected static int dpToPx(Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }
}
