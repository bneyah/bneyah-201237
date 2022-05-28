package com.app.bneyah_201237.SQLlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentsRecord.db";
    public static final String TABLE_NAME = "Student";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_FNAME = "fathername";
    public static final String COLUMN_NATIONALID = "nationalid";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_GENDER = "gender";

    // create database once this class is called
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //once database is created, create the table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " TEXT PRIMARY KEY,"
                        + COLUMN_NAME + " TEXT,"
                        + COLUMN_SURNAME + " TEXT,"
                        + COLUMN_FNAME + " TEXT,"
                        + COLUMN_NATIONALID + " TEXT,"
                        + COLUMN_DOB + " TEXT,"
                        + COLUMN_GENDER + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS Student");

            onCreate(db);
        }
    }

    public void insertStudentRecord(String id, String name, String surname,
                                    String fname, String nationalid, String dob, String gender) {
        // create instance to write to the database created earlier
        SQLiteDatabase db = this.getWritableDatabase();

        // insert values that user pass to the method into the table columns
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SURNAME, surname);
        values.put(COLUMN_FNAME, fname);
        values.put(COLUMN_NATIONALID, nationalid);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_GENDER, gender);
        db.insert(TABLE_NAME, null, values);
    }

    public Cursor viewStudentsList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    public Cursor viewSpecificStudent(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id=?", new String[]{id});
        return res;
    }

    public void updateStudentRecord(String id, String name, String surname,
                                    String fname, String nationalid, String dob, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SURNAME, surname);
        values.put(COLUMN_FNAME, fname);
        values.put(COLUMN_NATIONALID, nationalid);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_GENDER, gender);

        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{id});
    }

    public void deleteStudentRecord(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
    }
}
