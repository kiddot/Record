package com.android.record.base.componet;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.record.base.util.DialogFragmentHelper;
import com.android.record.base.util.HandleUtil;
import com.android.record.base.util.Toastor;

import org.greenrobot.eventbus.EventBus;

import static com.android.record.RecordApplication.getContext;

/**
 * Created by kiddo on 17-3-31.
 */

public abstract class BaseFragment extends Fragment {
    private Toastor mToast;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    protected abstract int getLayoutId();
    protected abstract void init();
    private DialogFragment mLoadingDialog;

    protected void showLoading(String msg) {
        boolean isDestroy;
        if (Build.VERSION.SDK_INT > 16) {
            isDestroy = getActivity().isDestroyed();
        } else {
            isDestroy = getActivity().isFinishing();
        }
        if (mLoadingDialog == null && !isDestroy)
            mLoadingDialog = DialogFragmentHelper.showProgress(getChildFragmentManager(), msg, true);
    }

    protected void dismissLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    protected void showToast(final String content) {
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
