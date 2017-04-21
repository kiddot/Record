package com.android.record.diary.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.record.R;
import com.android.record.base.componet.BaseActivity;
import com.android.record.base.util.Check;
import com.android.record.base.util.QueryWeek;
import com.android.record.common.sp.UserManager;
import com.android.record.diary.contract.DiaryTaskContract;
import com.android.record.diary.event.SaveDiaryEvent;
import com.android.record.diary.presenter.DiaryPresenter;
import com.github.clans.fab.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;

import me.james.biuedittext.BiuEditText;

/**
 * Created by kiddo on 17-4-21.
 */

public class WriteDiaryActivity extends BaseActivity implements
        TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener,
        FloatingActionButton.OnClickListener,DiaryTaskContract.View{
    public static final String TAG = WriteDiaryActivity.class.getSimpleName();
    private FloatingActionButton mFloatingActionButton;
    private TextView mTvDate;
    private TextView mTvTime;
    private BiuEditText mBtnTitle;
    private EditText mEtContent;
    private String mWeek;
    private String mDate;
    private String mTime;
    private String mContent;
    private String mTitle;
    private DiaryTaskContract.Presenter mPresenter;
    private UserManager mUserManager;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, WriteDiaryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void getLayoutBinding() {
        setContentView(R.layout.activity_diary_write);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        initListener();
    }

    private void initListener() {
        mFloatingActionButton.setOnClickListener(this);
    }

    private void initView() {
        EventBus.getDefault().register(this);
        mUserManager = UserManager.getInstance(this);
        setPresenter(new DiaryPresenter(this));
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.write_fab_finish);
        mTvDate = (TextView) findViewById(R.id.write_tv_date);
        mTvTime = (TextView) findViewById(R.id.write_tv_time);
        mBtnTitle = (BiuEditText) findViewById(R.id.write_et_title);
        mEtContent = (EditText) findViewById(R.id.write_et_content);
    }

    public void onClickDate(View view){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "DatePickerDialog");
    }

    public void onClickTime(View view){
        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                true
        );
        dpd.show(getFragmentManager(), "DatePickerDialog");
    }

    public void onClickEmotion(View view){

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d(TAG, "onDateSet: " + year + monthOfYear + dayOfMonth);
        String date =( monthOfYear + 1 ) + "." + dayOfMonth;
        mTvDate.setText(date);
        mWeek = QueryWeek.getWeek(new Date(year,monthOfYear+1,dayOfMonth));
        mDate = date;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        Log.d(TAG, "onDateSet: " + hourOfDay + minute + second);
        String time =hourOfDay  + ":" + minute;
        mTvTime.setText(time);
        mTime = time;
    }

    @Override
    public void onClick(View v) {
        if (Check.isEmpty(getDiaryTitle())){
            showToast("标题不能为空");
            return;
        }
        if (Check.isEmpty(getContent())){
            showToast("内容不能为空");
        } else {
            Log.d(TAG, "onClick: 开始保存日记");
            showLoading("正在上传日记");
            mPresenter.saveDiary(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaveDiaryEvent(SaveDiaryEvent event){
        Log.d(TAG, "onReceiveDiaryEvent: " + event.isSuccess());
        boolean success = event.isSuccess();
        if (success){
            dismissLoading();
            finish();
        } else {
            showToast("上传日记失败，请检查网络～重试");
        }
    }

    @Override
    public String getContent() {
        return mContent = mEtContent.getText().toString();
    }

    @Override
    public String getUserName() {
        return mUserManager.getUserName();
    }

    @Override
    public String getDiaryTime() {
        return mTime;
    }

    @Override
    public String getDiaryDate() {
        return mDate;
    }

    @Override
    public String getEmotion() {
        return "好";
    }

    @Override
    public String getWeek() {
        return mWeek;
    }

    @Override
    public String getDiaryTitle() {
        return mBtnTitle.getText().toString();
    }

    @Override
    public void setPresenter(DiaryTaskContract.Presenter presenter) {
        if (presenter != null){
            mPresenter = presenter;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}
