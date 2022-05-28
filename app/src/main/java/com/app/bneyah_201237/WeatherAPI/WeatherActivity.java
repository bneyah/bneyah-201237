package com.app.bneyah_201237.WeatherAPI;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.bneyah_201237.Model.Clouds;
import com.app.bneyah_201237.Model.Main;
import com.app.bneyah_201237.Model.WeatherModel;
import com.app.bneyah_201237.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    private TextView clouds, temperature, humidity;
    private EditText City;
    private Button Get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        clouds = findViewById(R.id.clouds);
        temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);
        City = findViewById(R.id.city);
        Get = findViewById(R.id.get);

        Get.setOnClickListener(view -> {
            String city = City.getText().toString();

            WeatherSingleton data = WeatherSingleton.get(this);
            data.setMCity(city);

            getWeatherData();
        });
    }

    private void getWeatherData() {
        WeatherSingleton data = WeatherSingleton.get(this);
        String city = data.getMCity();
        String appid = "5fc337f100d860b7f85f9bfa98b671b9";

        if (isNetworkConnected()) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<WeatherModel> call = apiService.putWeatherDataa(city,
                    appid);
            call.enqueue(new Callback<WeatherModel>() {
                @Override
                public void onResponse(Call<WeatherModel> call,
                                       Response<WeatherModel> response) {
                    WeatherModel weatherModel = response.body();
                    if (weatherModel != null) {
                        Clouds cloudsModel = weatherModel.getClouds();
                        Main main = weatherModel.getMain();

                        String CloudsPer = cloudsModel.getAll() + "%";
                        String Temperature = main.getTemp() + "°K";
                        String Humidity = main.getHumidity() + "%";
                        clouds.setText(CloudsPer);
                        temperature.setText(Temperature);
                        humidity.setText(Humidity);
                    }
                }

                @Override
                public void onFailure(Call<WeatherModel> call, Throwable t) {
                    Log.d("onResponse", "No response reason: " + t.toString());
                }
            });
        } else {
            Log.d("onResponse", "No internet connection.");
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}