//package com.android.record.base.dao;
//
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 数据库管理者，根据数据库名管理数据库，避免数据库过多导致代码混乱
// * Created by yzw on 2016/7/12 0012.
// */
//public class AppDaoManager {
//    private volatile ConcurrentHashMap<String, DaoManager> daoManagerMap;
//
//    private AppDaoManager() {
//        this.daoManagerMap = new ConcurrentHashMap<>();
//    }
//
//    public static AppDaoManager getInstance() {
//        return AppDaoManagerBuilder.appDaoManager;
//    }
//
//    // TODO: 2016/7/12 0012 并发问题
//    public static void put(String dbName, DaoManager daoManager) {
//        getInstance().daoManagerMap.put(dbName, daoManager);
//    }
//
//    public static DaoManager get(String dbName) {
//        return getInstance().daoManagerMap.get(dbName);
//    }
//
//    public static void remove(String name) {
//        DaoManager daoManager = getInstance().daoManagerMap.remove(name);
//        daoManager.close();
//    }
//
//    public static void removeAll() {
//        ConcurrentHashMap<String, DaoManager> map = getInstance().daoManagerMap;
//        for (String name : map.keySet()) {
//            map.remove(name).close();
//        }
//    }
//
//    public static void onlyRemoveAll() {
//        getInstance().daoManagerMap.clear();
//    }
//
//    private static class AppDaoManagerBuilder {
//        public static AppDaoManager appDaoManager = new AppDaoManager();
//    }
//}
