package com.android.record.common.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.record.base.dao.AppDaoManager;
import com.android.record.base.dao.DaoManager;
import com.android.record.common.dao.RecordDaoHelper;
import com.android.record.common.dao.RecordDaoManager;

/**
 * Created by kiddo on 17-3-30.
 */

public class UserManager {
    private static final String TAG = UserManager.class.getSimpleName();
    private static final String PREFERENCES_NAME = "record";

    private String dbName;
    private DaoManager daoManager;
    private SharedPreferences userPreferences;
    private Context context;

    private UserManager(Context context) {
        this.context = context.getApplicationContext();
        userPreferences = this.context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public DaoManager getDefaultDaoManager() {
        String dbName = "default";

        daoManager = AppDaoManager.get(dbName);

        if (daoManager == null)
            daoManager = new RecordDaoManager(this.context, dbName, RecordDaoHelper.getInstance());
        return daoManager;
    }

    public DaoManager getDaoManager() {
        //-Hans 8.17发现下面没有判断的BUG. 比较难发现
        //如果是老师 那么数据库名字是UserId
        if (dbName == null)
            dbName = "default";
        Log.d(TAG, "目前的数据库名称是:" + dbName);
        daoManager = AppDaoManager.get(dbName);

        if (daoManager == null) {
            Log.d(TAG, "为null;创建数据库是:" + dbName);
            daoManager = new RecordDaoManager(this.context, dbName, RecordDaoHelper.getInstance());
        } else {
            Log.d(TAG, "不为null;数据库是:" + dbName);
        }
        return daoManager;
    }
}
