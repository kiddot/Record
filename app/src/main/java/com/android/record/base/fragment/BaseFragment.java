package com.android.record.base.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


import com.android.record.base.thread.ThreadPoolConst;
import com.android.record.base.thread.ThreadPoolManager;
import com.android.record.base.util.DialogFragmentHelper;
import com.android.record.base.util.HandleUtil;
import com.android.record.base.util.Toastor;

import java.util.concurrent.Executor;

import butterknife.ButterKnife;


/**
 *
 * Created by kiddo on 17-1-10.
 */
public abstract class BaseFragment extends MSBaseFragment{
    public static final String TAG = BaseFragment.class.getSimpleName();
    protected static Executor sHTTPExecutor = ThreadPoolManager.getInstance().getThreadPool(ThreadPoolConst.THREAD_TYPE_SIMPLE_HTTP);
    protected static Executor sWORKExecutor = ThreadPoolManager.getInstance().getThreadPool(ThreadPoolConst.THREAD_TYPE_WORK);


    @Override
    protected void init(Bundle savedInstanceState) {
    }

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
}
