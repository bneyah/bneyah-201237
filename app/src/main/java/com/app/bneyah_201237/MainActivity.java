package com.app.bneyah_201237;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bneyah_201237.Model.Clouds;
import com.app.bneyah_201237.Model.Main;
import com.app.bneyah_201237.Model.WeatherModel;
import com.app.bneyah_201237.WeatherAPI.ApiClient;
import com.app.bneyah_201237.WeatherAPI.ApiInterface;
import com.app.bneyah_201237.WeatherAPI.WeatherActivity;
import com.app.bneyah_201237.WeatherAPI.WeatherSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView clouds, temperature, humidity;
    private EditText etStdName, etStdId;
    private Button btnNext, btnGetWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clouds = findViewById(R.id.clouds);
        temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);

        etStdName = (EditText) findViewById(R.id.et_std_name);
        etStdId = (EditText) findViewById(R.id.et_std_id);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnGetWeather = (Button) findViewById(R.id.btn_get_weather);

        btnNext.setOnClickListener(view -> {
            String stdName = etStdName.getText().toString();
            String stdID = etStdId.getText().toString();
            if (!(stdName.equalsIgnoreCase("")
                    || stdID.equalsIgnoreCase(""))) {

                SharedPreferences sp = getSharedPreferences("students_Prefs", MODE_PRIVATE);

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("student_name", stdName);
                editor.putString("student_id", stdID);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, SelectOption.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Student Name & ID fields are must.", Toast.LENGTH_SHORT).show();
            }
        });
        btnGetWeather.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
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