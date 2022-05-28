package com.app.bneyah_201237.Model;

public class StudentsModel {

    String ID, Name, SurName, FatherName, NationalID, DOB, Gender;

    public StudentsModel() {
    }

    public StudentsModel(String ID, String name, String surName,
                         String fatherName, String nationalID, String dob, String gender) {
        this.ID = ID;
        this.Name = name;
        this.SurName = surName;
        this.FatherName = fatherName;
        this.NationalID = nationalID;
        this.DOB = dob;
        this.Gender = gender;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getNationalID() {
        return NationalID;
    }

    public void setNationalID(String nationalID) {
        NationalID = nationalID;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
