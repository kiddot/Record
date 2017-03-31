package com.android.record.list.api;

import com.android.record.bean.GsonCard;
import com.android.record.bean.SwipeCardBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kiddo on 17-3-31.
 */

public interface ListService  {

    @GET("list.php")
    Observable<GsonCard> getCard(@Query("format") String format,
                                 @Query("username") String username
    );

    @GET("list.php")
    Observable<GsonCard> sendCard(@Query("format") String format,
                                      @Query("username") String username,
                                       @Query("position") int position,
                                       @Query("description") String description,
                                       @Query("time") long time
                                      );
}
