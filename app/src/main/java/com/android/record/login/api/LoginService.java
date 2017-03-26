package com.android.record.login.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import com.android.record.bean.GsonUser;
import com.android.record.bean.user;

/**
 * Created by kiddo on 17-3-26.
 */

public interface LoginService {

    @GET("login.php")
    Observable<GsonUser> getUser(@Query("format") String format,
                                 @Query("username") String username,
                                 @Query("password") String password
                                );

    @GET("login.php")
    Observable<String> addUser(@Query("format") String format,
                               @Query("username") String username,
                               @Query("password") String password,
                               @Query("sex") String sex,
                               @Query("email") String email,
                               @Query("phone") String phone
                                );

}
