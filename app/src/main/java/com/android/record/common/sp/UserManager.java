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

    public static String dbName;
    private static UserManager userManager;

    private String userName;
    private String sex;
    private String phone;
    private String email;
    private DaoManager daoManager;
    private SharedPreferences userPreferences;
    private Context context;

    private UserManager(Context context) {
        this.context = context.getApplicationContext();
        userPreferences = this.context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public synchronized static UserManager getInstance(Context context){
        if (userManager == null){
            synchronized (UserManager.class){
                if (userManager == null){
                    userManager = new UserManager(context.getApplicationContext());
                }
            }
        }
        return userManager;
    }

    private SharedPreferences.Editor getEditor() {
        return userPreferences.edit();
    }

    public void setUserName(String userName) {
        this.userName = userName;
        getEditor().putString("userName", userName).apply();
    }

    public String getUserName() {
        if (userName == null) {
            this.userName = userPreferences.getString("userName", null);
        }
        return userName;
    }

    public void setSex(String sex){
        this.sex = sex;
        getEditor().putString("sex", sex).apply();
    }

    public String getSex(){
        if (sex == null){
            this.sex = userPreferences.getString("sex", null);
        }
        return sex;
    }

    public void setEmail(String email){
        this.email = email;
        getEditor().putString("email" , null).apply();
    }

    public String getEmail(){
        if (email == null){
            this.email = userPreferences.getString("email", null);
        }
        return email;
    }

    public void setPhone(String phone){
        this.phone = phone;
        getEditor().putString("phone", phone).apply();
    }

    public String getPhone(){
        if (phone == null){
            this.phone = userPreferences.getString("phone", null);
        }
        return phone;
    }

    public DaoManager getDefaultDaoManager() {
        String dbName = "default";

        daoManager = AppDaoManager.get(dbName);
        if (daoManager == null)
            daoManager = new RecordDaoManager(this.context, dbName, RecordDaoHelper.getInstance());
        return daoManager;
    }

    public DaoManager getDaoManager() {
        if (dbName == null)
            dbName = "default";
        Log.d(TAG, "目前的数据库名称是:" + dbName);
        daoManager = AppDaoManager.get(dbName);

        dbName = getUserName();
        if (daoManager == null) {
            Log.d(TAG, "为null;创建数据库是:" + dbName);
            daoManager = new RecordDaoManager(this.context, dbName, RecordDaoHelper.getInstance());
        } else {
            Log.d(TAG, "不为null;数据库是:" + dbName);
        }
        return daoManager;
    }
}
