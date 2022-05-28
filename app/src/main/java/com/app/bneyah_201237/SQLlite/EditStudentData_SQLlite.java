package com.app.bneyah_201237.SQLlite;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.bneyah_201237.R;

public class EditStudentData_SQLlite extends AppCompatActivity {

    private EditText name, surname, father_name, national_id, dob, gender;
    private Button update;
    private String User_Key, ID;
    DatabaseHelper MyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_data_fb);

        MyDB = new DatabaseHelper(this);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        father_name = findViewById(R.id.father_name);
        national_id = findViewById(R.id.national_id);
        dob = findViewById(R.id.dob);
        gender = findViewById(R.id.gender);
        update = findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStudentData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getStudentRecord();
    }

    private void getStudentRecord() {
        Bundle bundle = getIntent().getExtras();
        User_Key = bundle.getString("Push_Key");

        Cursor cursor = MyDB.viewSpecificStudent(User_Key);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ID = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String NAME = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                String SURNAME = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SURNAME));
                String FNAME = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FNAME));
                String NATIONALID = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NATIONALID));
                String DOB = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DOB));
                String GENDER = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GENDER));

                name.setText(NAME);
                surname.setText(SURNAME);
                father_name.setText(FNAME);
                national_id.setText(NATIONALID);
                dob.setText(DOB);
                gender.setText(GENDER);

            } while (cursor.moveToNext());
        }
    }

    private void updateStudentData() {
        String Name = name.getText().toString();
        String SurName = surname.getText().toString();
        String FatherMame = father_name.getText().toString();
        String NationalID = national_id.getText().toString();
        String DOB = dob.getText().toString();
        String Gender = gender.getText().toString();

        MyDB.updateStudentRecord(ID, Name, SurName, FatherMame, NationalID, DOB, Gender);

        Toast.makeText(EditStudentData_SQLlite.this,
                "Student record updated.", Toast.LENGTH_SHORT).show();

        finish();
    }

}