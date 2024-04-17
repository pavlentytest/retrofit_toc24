package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexAPI {
    @GET("/api/v1/predict.json/complete")
    Call<Response> getComplete(
           @Query("key") String key,
           @Query("q") String q,
           @Query("lang") String lang,
           @Query("limit") Integer limit
    );
}
