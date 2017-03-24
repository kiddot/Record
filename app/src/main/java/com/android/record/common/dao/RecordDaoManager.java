package com.android.record.common.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.record.base.dao.DaoManager;
import com.android.record.base.dao.OnSQLiteDatabaseListener;
import com.android.record.greendao.DaoMaster;

import org.greenrobot.greendao.AbstractDaoMaster;

/**
 * Created by kiddo on 17-3-24.
 */
//本项目本地数据库的管理类
public class RecordDaoManager extends DaoManager{

    public RecordDaoManager(Context context, String dbName, OnSQLiteDatabaseListener sqLiteDatabaseListener) {
        super(context, dbName, sqLiteDatabaseListener);
    }

    @Override
    protected AbstractDaoMaster initDaoMaster(SQLiteDatabase writableDb) {
        return new DaoMaster(writableDb);
    }
}
