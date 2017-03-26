package com.android.record.base.componet;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.android.record.base.thread.ThreadPoolConst;
import com.android.record.base.thread.ThreadPoolManager;
import com.android.record.base.util.HandleUtil;
import com.android.record.base.util.Toastor;

import java.util.concurrent.Executor;

import butterknife.ButterKnife;

import static com.android.record.RecordApplication.getContext;


/**
 *
 * Created by kiddo on 17-1-9.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    //以ClassName作为TAG
    private static final String TAG = BaseActivity.class.getSimpleName();
    protected static Executor sHTTPExecutor = ThreadPoolManager.getInstance().getThreadPool(ThreadPoolConst.THREAD_TYPE_SIMPLE_HTTP);
    protected static Executor sWORKExecutor = ThreadPoolManager.getInstance().getThreadPool(ThreadPoolConst.THREAD_TYPE_WORK);
    private Toastor mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        getLayoutBinding();
        //setContentView(getLayoutId());
        //ButterKnife.bind(this);
        if (mToast == null && getContext() != null) mToast = new Toastor(getContext());
        init(savedInstanceState);
    }

    /*
    * 重写该方法来设置布局Id
    */
    protected abstract void getLayoutBinding();

    /*
    * 初始化
    * */
    protected abstract void init(Bundle savedInstanceState);

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
