package com.android.record.common.dao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.record.base.dao.OnSQLiteDatabaseListener;
import com.android.record.greendao.DaoMaster;

import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;

/**
 * Created by kiddo on 17-3-24.
 */

public class RecordDaoHelper implements OnSQLiteDatabaseListener {
    private static final String TAG = "RecordDaoHelper";
    private volatile static RecordDaoHelper mInstance;

    public static synchronized RecordDaoHelper getInstance() {
        if (mInstance == null)
            mInstance = new RecordDaoHelper();
        return mInstance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate 创建数据库表");
        Database database = new StandardDatabase(db);
        DaoMaster.createAllTables(database, true);
    }

    @Override
    public void onUpgrade(AbstractDaoSession daoSession, int mOldVersion, int mNewVersion) {

    }
}
