package com.app.bneyah_201237.Firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.bneyah_201237.Adapters.StudentsFBAdapter;
import com.app.bneyah_201237.Model.StudentsModel;
import com.app.bneyah_201237.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetAllStudentsData_FB extends AppCompatActivity {

    private RecyclerView Recycler;
    private ProgressBar Progress_bar;
    private DatabaseReference Reference;
    private ArrayList<StudentsModel> arrayList = new ArrayList<>();
    private StudentsFBAdapter users_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_students_data_fb);

        Recycler = findViewById(R.id.recycler_id);
        Progress_bar = findViewById(R.id.progress_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        Recycler.setLayoutManager(gridLayoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        getStudentRecord();
    }

    private void getStudentRecord() {
        Progress_bar.setVisibility(View.VISIBLE);
        Reference = FirebaseDatabase.getInstance().getReference("Students");
        Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                Progress_bar.setVisibility(View.GONE);

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    arrayList.add(dataSnapshot1.getValue(StudentsModel.class));
                }
                if (arrayList.size() > 0) {
                    Recycler.setVisibility(View.VISIBLE);
                    users_adapter = new StudentsFBAdapter(GetAllStudentsData_FB.this, arrayList);
                    Recycler.setAdapter(users_adapter);
                    users_adapter.notifyDataSetChanged();
                } else {
                    Recycler.setVisibility(View.GONE);
                    Toast.makeText(GetAllStudentsData_FB.this,
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