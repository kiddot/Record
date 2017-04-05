package com.android.record;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.record.base.componet.image.CommonImageLoader;
import com.android.record.greendao.DaoMaster;
import com.android.record.greendao.DaoSession;

/**
 * Created by kiddo on 17-3-19.
 */

public class RecordApplication extends Application{
    public static Context mContext;
    public static RecordApplication mApplication;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mContext = getApplicationContext();
        initUniversialImageLoader();
        //setDatabase();
    }

    public static RecordApplication getApplication() {
        return mApplication;
    }

    public static Context getContext(){
        return mContext;
    }

    private void initUniversialImageLoader() {
        CommonImageLoader.init(getApplicationContext());
        CommonImageLoader.debug(false);
    }

    private void setDatabase() {

        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this,"notes-db", null);
        db =mHelper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }


    public SQLiteDatabase getDb() {
        return db;
    }
}
