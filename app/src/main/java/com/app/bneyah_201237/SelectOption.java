package com.app.bneyah_201237;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.app.bneyah_201237.Firebase.GetAllStudentsData_FB;
import com.app.bneyah_201237.Firebase.GetSpecificStudentsData_FB;
import com.app.bneyah_201237.Firebase.AddStudentData_FB;
import com.app.bneyah_201237.Model.Clouds;
import com.app.bneyah_201237.Model.Main;
import com.app.bneyah_201237.Model.WeatherModel;
import com.app.bneyah_201237.SQLlite.GetStudentsData_SQLlite;
import com.app.bneyah_201237.WeatherAPI.ApiClient;
import com.app.bneyah_201237.WeatherAPI.ApiInterface;
import com.app.bneyah_201237.WeatherAPI.WeatherSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectOption extends AppCompatActivity {

    private TextView clouds, temperature, humidity;
    private Button btnInsert, btnGet, btnGetSpecific, btnGetSqllite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);

        clouds = findViewById(R.id.clouds);
        temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);

        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnGet = (Button) findViewById(R.id.btn_get);
        btnGetSpecific = (Button) findViewById(R.id.btn_get_specific);
        btnGetSqllite = (Button) findViewById(R.id.btn_get_sqllite);

        btnInsert.setOnClickListener(view -> {
            Intent intent = new Intent(SelectOption.this, AddStudentData_FB.class);
            startActivity(intent);
        });
        btnGet.setOnClickListener(view -> {
            Intent intent = new Intent(SelectOption.this, GetAllStudentsData_FB.class);
            startActivity(intent);
        });
        btnGetSpecific.setOnClickListener(view -> {
            Intent intent = new Intent(SelectOption.this, GetSpecificStudentsData_FB.class);
            startActivity(intent);
        });
        btnGetSqllite.setOnClickListener(view -> {
            Intent intent = new Intent(SelectOption.this, GetStudentsData_SQLlite.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWeatherData();
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