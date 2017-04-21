package com.android.record.diary.api;

import com.android.record.bean.Diary;
import com.android.record.bean.GsonDiary;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kiddo on 17-4-13.
 */

public interface DiaryService {

    @GET("diary.php")
    Observable<GsonDiary> getDiary(@Query("format") String format, @Query("username") String username);

    @GET("diary.php")
    Observable<GsonDiary> saveDiary(@Query("format") String format,
                                @Query("username") String username,
                                @Query("title") String title,
                                @Query("date") String date,
                                @Query("week") String week,
                                @Query("content") String content,
                                @Query("emotion") String emotion,
                                @Query("time") long time);
}
