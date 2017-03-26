package com.android.record.login.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import com.android.record.bean.user;

/**
 * Created by kiddo on 17-3-26.
 */

public interface LoginService {

    @GET("login.php")
    Observable<user> getUser(@Query("format") String format, @Query("username") String username, @Query("password") String password);
}
