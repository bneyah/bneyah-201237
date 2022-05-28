package com.app.bneyah_201237.Firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.app.bneyah_201237.Model.StudentsModel;
import com.app.bneyah_201237.Model.WeatherModel;
import com.app.bneyah_201237.R;
import com.app.bneyah_201237.WeatherAPI.ApiClient;
import com.app.bneyah_201237.WeatherAPI.ApiInterface;
import com.app.bneyah_201237.WeatherAPI.WeatherSingleton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStudentData_FB extends AppCompatActivity {

    private TextView clouds, temperature, humidity;
    private DatabaseReference mDatabase;
    private EditText std_id, name, surname, father_name, national_id, dob, gender;
    private Button add;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_data_fb);

        clouds = findViewById(R.id.clouds);
        temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);
        std_id = findViewById(R.id.std_id);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        father_name = findViewById(R.id.father_name);
        national_id = findViewById(R.id.national_id);
        dob = findViewById(R.id.dob);
        gender = findViewById(R.id.gender);
        add = findViewById(R.id.add);

        sp = getSharedPreferences("students_Prefs", MODE_PRIVATE);
        name.setText(sp.getString("student_name", ""));
        std_id.setText(sp.getString("student_id", ""));

        add.setOnClickListener(view -> addRecord());

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

    private void addRecord() {
        String stdID = std_id.getText().toString();
        String Name = name.getText().toString();
        String SurName = surname.getText().toString();
        String FatherMame = father_name.getText().toString();
        String NationalID = national_id.getText().toString();
        String DOB = dob.getText().toString();
        String Gender = gender.getText().toString();

        if (!stdID.equalsIgnoreCase("")) {
            StudentsModel students = new StudentsModel(stdID, Name, SurName,
                    FatherMame, NationalID, DOB, Gender);

            mDatabase = FirebaseDatabase.getInstance().getReference("Students");
            mDatabase.child(stdID).setValue(students).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(AddStudentData_FB.this,
                            "Student record added.", Toast.LENGTH_SHORT).show();

                    finish();
                }
            });
        } else {
            Toast.makeText(this, "Student ID field is must.", Toast.LENGTH_SHORT).show();
        }
    }
}