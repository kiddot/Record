package com.android.record.base.fragment;

import android.os.Bundle;

import com.android.memory.base.util.HandleUtil;
import com.android.memory.base.util.Toastor;

public abstract class MSBaseFragment extends MSLazyFragment {
    private Toastor mToast;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(getContentViewId());
        if (mToast == null && getContext() != null) mToast = new Toastor(getContext());
        init(savedInstanceState);
    }

    protected abstract int getContentViewId();

    protected abstract void init(Bundle savedInstanceState);


    public void showToast(final String content) {
        HandleUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (getContext() == null) return;
                if (mToast == null) mToast = new Toastor(getContext());
                mToast.showToast(content);
            }
        });
    }
}
