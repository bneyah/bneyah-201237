package com.app.bneyah_201237.Firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.bneyah_201237.Model.StudentsModel;
import com.app.bneyah_201237.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditStudentData_FB extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText name, surname, father_name, national_id, dob, gender;
    private Button update;
    private String User_Key;
    private StudentsModel students = new StudentsModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_data_fb);

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
                updateStudentRecord();
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

        mDatabase = FirebaseDatabase.getInstance().getReference("Students").child(User_Key);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students = dataSnapshot.getValue(StudentsModel.class);

                if (students != null) {
                    name.setText(students.getName());
                    surname.setText(students.getSurName());
                    father_name.setText(students.getFatherName());
                    national_id.setText(students.getNationalID());
                    dob.setText(students.getDOB());
                    gender.setText(students.getGender());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateStudentRecord() {
        String stdID = students.getID();
        String Name = name.getText().toString();
        String SurName = surname.getText().toString();
        String FatherMame = father_name.getText().toString();
        String NationalID = national_id.getText().toString();
        String DOB = dob.getText().toString();
        String Gender = gender.getText().toString();

        StudentsModel user = new StudentsModel(stdID, Name, SurName,
                FatherMame, NationalID, DOB, Gender);

        mDatabase = FirebaseDatabase.getInstance().getReference("Students");
        mDatabase.child(stdID).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditStudentData_FB.this,
                        "Student record updated.", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

}