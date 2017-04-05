package com.android.record.list.api;

import com.android.record.bean.GsonCard;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Multipart
    @POST("list.php")
    Call<ResponseBody> upload(@Query("format") String format,
                              @Query("username") String username,
                              @Query("position") int position,
                              @Part MultipartBody.Part file);
}
