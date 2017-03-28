package com.android.record.main.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.android.record.R;
import com.android.record.base.componet.BaseActivity;

import yalantis.com.sidemenu.util.ViewAnimator;

/**
 * Created by kiddo on 17-3-28.
 */

public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    public static void startActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void getLayoutBinding() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

}
