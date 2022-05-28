package com.app.bneyah_201237.WeatherAPI;

import com.app.bneyah_201237.Model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("weather")
    Call<WeatherModel> putWeatherDataa(@Query("q") String lat,
                                       @Query("appid") String appid);
}
