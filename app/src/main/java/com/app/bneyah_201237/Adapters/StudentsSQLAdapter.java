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
import com.app.bneyah_201237.SQLlite.EditStudentData_SQLlite;
import com.app.bneyah_201237.SQLlite.GetStudentsData_SQLlite;
import com.app.bneyah_201237.Model.StudentsModel;
import com.app.bneyah_201237.R;

import java.util.List;

public class StudentsSQLAdapter extends RecyclerView.Adapter<StudentsSQLAdapter.UserAdapterHolder> {
    Context context;
    List<StudentsModel> list;
    DatabaseHelper MyDB;

    public StudentsSQLAdapter(Context context, List<StudentsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StudentsSQLAdapter.UserAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_sql_design, viewGroup, false);
        return new UserAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentsSQLAdapter.UserAdapterHolder holder, final int position) {
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

        holder.edit.setOnClickListener(view -> {
            Intent i = new Intent(context, EditStudentData_SQLlite.class);
            i.putExtra("Push_Key", id);
            context.startActivity(i);
        });
        holder.remove.setOnClickListener(view -> deleteStudentRecord(id));
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
        Button edit, remove;

        public UserAdapterHolder(@NonNull View v) {
            super(v);

            student_Id = v.findViewById(R.id.student_Id);
            name = v.findViewById(R.id.name);
            edit = v.findViewById(R.id.edit);
            remove = v.findViewById(R.id.remove);

        }
    }

    private void deleteStudentRecord(String id) {

        MyDB.deleteStudentRecord(id);

        ((GetStudentsData_SQLlite) context).getStudentRecord();

        Toast.makeText(context,
                "Student record with ID:" + id + " deleted.", Toast.LENGTH_SHORT).show();
    }
}