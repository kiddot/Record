package com.android.record.login.model;

import android.content.Context;

import com.android.record.bean.User;
import com.android.record.common.sp.UserManager;

/**
 * Created by kiddo on 17-3-30.
 */

public class LoginInfoSave {

    public static void trySaveUserInfo(Context context, User user){
        if (user == null) return;
        UserManager userManager = UserManager.getInstance(context);
        userManager.setUserName(user.getUsername());
        userManager.setEmail(user.getEmail());
        userManager.setPhone(user.getPhone());
        userManager.setSex(user.getSex());
    }
}
