package com.app.bneyah_201237.WeatherAPI;

import android.content.Context;

public class WeatherSingleton {

    private static WeatherSingleton weatherSingleton;
    private Context mAppContext;
    private String mCity = "Berlin";

    private WeatherSingleton(Context context) {
        mAppContext = context.getApplicationContext();
    }

    public static WeatherSingleton get(Context context) {
        if (weatherSingleton == null) {
            weatherSingleton = new WeatherSingleton(context);
        }
        return weatherSingleton;
    }

    public void setMCity(String value) {
        mCity = value;
    }

    public String getMCity() {
        return mCity;
    }
}