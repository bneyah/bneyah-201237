package com.app.bneyah_201237.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bneyah_201237.SQLlite.DatabaseHelper;
import com.app.bneyah_201237.Firebase.EditStudentData_FB;
import com.app.bneyah_201237.Model.StudentsModel;
import com.app.bneyah_201237.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class StudentsFBAdapter extends RecyclerView.Adapter<StudentsFBAdapter.UserAdapterHolder> {
    Context context;
    List<StudentsModel> list;
    DatabaseHelper MyDB;

    public StudentsFBAdapter(Context context, List<StudentsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StudentsFBAdapter.UserAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_design, viewGroup, false);
        return new UserAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentsFBAdapter.UserAdapterHolder holder, final int position) {
        final StudentsModel students_Model = list.get(position);

        String id = students_Model.getID();
        String name = students_Model.getName();
        String surname = students_Model.getSurName();
        String fname = students_Model.getFatherName();
        String nationalid = students_Model.getNationalID();
        String dob = students_Model.getDOB();
        String gender = students_Model.getGender();

        holder.student_Id.setText(id);
        holder.name.setText(name);

        MyDB = new DatabaseHelper(context);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, EditStudentData_FB.class);
                i.putExtra("Push_Key", id);
                context.startActivity(i);
            }
        });
        holder.remove.setOnClickListener(view -> deleteStudentData(id));
        holder.savesql.setOnClickListener(view -> {
            MyDB.insertStudentRecord(id, name, surname, fname, nationalid, dob, gender);

            Toast.makeText(context, "Student record saved to SQLlite.", Toast.LENGTH_SHORT).show();
        });
        holder.itemView.setOnClickListener(view -> {
            Toast.makeText(context, name + " " + surname, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserAdapterHolder extends RecyclerView.ViewHolder {

        TextView student_Id, name;
        Button savesql, edit, remove;

        public UserAdapterHolder(@NonNull View v) {
            super(v);

            student_Id = v.findViewById(R.id.student_Id);
            name = v.findViewById(R.id.name);
            savesql = v.findViewById(R.id.savesql);
            edit = v.findViewById(R.id.edit);
            remove = v.findViewById(R.id.remove);

        }
    }

    private void deleteStudentData(String id) {
        DatabaseReference User_Reference = FirebaseDatabase.getInstance()
                .getReference("Students").child(id);
        User_Reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context,
                        "Student record with ID:" + id + " deleted.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}