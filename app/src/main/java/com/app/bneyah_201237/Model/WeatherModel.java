package com.app.bneyah_201237.Model;

import com.google.gson.annotations.SerializedName;

public class WeatherModel {
    @SerializedName("main")
    Main main;

    @SerializedName("clouds")
    Clouds clouds;

    public void setMain(Main main) {
        this.main = main;
    }

    public Main getMain() {
        return main;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Clouds getClouds() {
        return clouds;
    }
}