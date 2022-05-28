package com.app.bneyah_201237.SQLlite;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bneyah_201237.Adapters.StudentsSQLAdapter;
import com.app.bneyah_201237.Model.StudentsModel;
import com.app.bneyah_201237.R;

import java.util.ArrayList;

public class GetStudentsData_SQLlite extends AppCompatActivity {

    private RecyclerView Recycler;
    private ArrayList<StudentsModel> arrayList = new ArrayList<>();
    private StudentsSQLAdapter studentsSQLAdapter;
    DatabaseHelper MyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_students_data_fb);

        MyDB = new DatabaseHelper(this);

        Recycler = findViewById(R.id.recycler_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        Recycler.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getStudentRecord();
    }

    public void getStudentRecord() {
        arrayList.clear();

        Cursor cursor = MyDB.viewStudentsList();
        int count = cursor.getCount();
        if (count > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                String surname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SURNAME));
                String fname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FNAME));
                String nationalid = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NATIONALID));
                String dob = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DOB));
                String gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GENDER));

                StudentsModel students = new StudentsModel(id, name, surname,
                        fname, nationalid, dob, gender);
                arrayList.add(students);
            }
        }
        if (arrayList.size() > 0) {
            Recycler.setVisibility(View.VISIBLE);
            studentsSQLAdapter = new StudentsSQLAdapter(GetStudentsData_SQLlite.this, arrayList);
            Recycler.setAdapter(studentsSQLAdapter);
            studentsSQLAdapter.notifyDataSetChanged();
        } else {
            Recycler.setVisibility(View.GONE);
            Toast.makeText(GetStudentsData_SQLlite.this,
                    "No record found.", Toast.LENGTH_SHORT).show();
        }
    }

}