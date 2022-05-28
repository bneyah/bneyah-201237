package com.app.bneyah_201237.Firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bneyah_201237.Adapters.StudentsFBAdapter;
import com.app.bneyah_201237.Model.StudentsModel;
import com.app.bneyah_201237.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetSpecificStudentsData_FB extends AppCompatActivity {

    private EditText Std_id;
    private Button BtnGet;
    private RecyclerView Recycler;
    private DatabaseReference Reference;
    private ProgressBar Progress_bar;
    private ArrayList<StudentsModel> arrayList = new ArrayList<>();
    private StudentsFBAdapter studentsFBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_specific_students_data_fb);

        Std_id = findViewById(R.id.std_id);
        BtnGet = findViewById(R.id.btn_get);
        Recycler = findViewById(R.id.recycler_id);
        Progress_bar = findViewById(R.id.progress_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        Recycler.setLayoutManager(gridLayoutManager);

        BtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStudentRecord();
            }
        });
    }

    private void getStudentRecord() {
        arrayList.clear();
        Progress_bar.setVisibility(View.VISIBLE);

        String ID = Std_id.getText().toString();
        Reference = FirebaseDatabase.getInstance().getReference("Students").child(ID);
        Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Progress_bar.setVisibility(View.GONE);

                StudentsModel user = dataSnapshot.getValue(StudentsModel.class);
                if (user != null) {
                    arrayList.add(user);
                    Recycler.setVisibility(View.VISIBLE);
                    studentsFBAdapter = new StudentsFBAdapter(GetSpecificStudentsData_FB.this, arrayList);
                    Recycler.setAdapter(studentsFBAdapter);
                    studentsFBAdapter.notifyDataSetChanged();
                } else {
                    Recycler.setVisibility(View.GONE);
                    Toast.makeText(GetSpecificStudentsData_FB.this,
                            "No record found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Progress_bar.setVisibility(View.GONE);

            }
        });
    }
}