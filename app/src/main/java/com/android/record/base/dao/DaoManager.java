package com.android.record.base.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.List;
import java.util.concurrent.Callable;

import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.query.QueryBuilder;


/**
 * 基于GreenDao生成的...DaoMaster（相当与一个数据库）数据接口的抽象</br> 须实现下列步骤：
 * 1）.根据生成的...DaoMaster，在initHelper()中创建DaoHelper，并实现DaoHelper的创表工作，即直接调用相应 DaoMaster.createAllTables(db, false);
 * 2）.根据生成的...DaoMaster，创建对象
 * Created by yzw on 2016/7/11 0011.
 */
public abstract class DaoManager implements DaoService {

    private String dbName;
    private Context context;
    private OnSQLiteDatabaseListener mSqLiteDatabaseListener;
    private AbstractDaoMaster daoMaster;
    private DaoConfig mDaoConfig;
    private AbstractDaoSession daoSession;
    // 旧数据库版本，当用户进行升级的时候能够判断和保存升级之前的数据库版本
    // 当无进行升级操作则是当前数据库的版本
    private int mOldVersion;
    private int mDaoVersion;
    public DaoManager(Context context, String dbName, OnSQLiteDatabaseListener sqLiteDatabaseListener) {
        this.mSqLiteDatabaseListener = sqLiteDatabaseListener;
        this.context = context.getApplicationContext();
        this.dbName = dbName;
        init();
        AppDaoManager.getInstance().put(dbName, this);
    }

    public String getDbName() {
        return dbName;
    }

    public AbstractDaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(AbstractDaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public int getOldDaoVersion() {
        return mOldVersion;
    }

    public int getDaoVersion() {
        return mDaoVersion;
    }


    private void init() {
        mDaoConfig = new DaoConfig(this.context, this.dbName, mSqLiteDatabaseListener);
        this.daoMaster = initDaoMaster(mDaoConfig.openOrCreateDatabase());
        this.daoSession = daoMaster.newSession();
        this.mOldVersion = daoMaster.getDatabase().getVersion();
        this.mDaoVersion = daoMaster.getSchemaVersion();
        mDaoConfig.checkUpdate(daoSession, daoMaster.getDatabase().getVersion(), daoMaster.getSchemaVersion());
    }

    /**
     * 升级数据库操作
     */
    public void checkUpdate() {
        try {
            mDaoConfig.checkUpdate(daoSession, daoMaster.getDatabase().getVersion(), daoMaster.getSchemaVersion());
        } catch (Exception e) {
            Log.d("DaoManager", "手动升级数据库失败!!!!");
        }
    }

    /**
     * 创建相应new DaoMaster(daoHelper.getWritableDb());
     *
     * @param writableDb
     * @return
     */
    protected abstract AbstractDaoMaster initDaoMaster(SQLiteDatabase writableDb);

    public void close() {
        daoMaster.getDatabase().close();
        daoMaster = null;
        daoSession = null;
        mSqLiteDatabaseListener = null;
        mDaoConfig = null;
    }

    @Override
    public <T> void insert(T t) {
        daoSession.insertOrReplace(t);
    }

    @Override
    public <T> void update(T t) {
        daoSession.update(t);
    }

    @Override
    public <T> void delete(T t) {
        daoSession.delete(t);
    }

    @Override
    public <T> void deleteAll(Class<T> t) {
        daoSession.deleteAll(t);
    }

    @Override
    public <T> List<T> loadAll(Class<T> t) {
        return daoSession.loadAll(t);
    }

    @Override
    public <T> QueryBuilder<T> query(Class<T> t) {
        return (QueryBuilder<T>) daoSession.getDao(t).queryBuilder();
    }

    @Override
    public void runInTx(Runnable runnable) {
        daoSession.runInTx(runnable);
    }

    @Override
    public <V> V callInTx(Callable<V> callable) throws Exception {
        return daoSession.callInTx(callable);
    }
}
