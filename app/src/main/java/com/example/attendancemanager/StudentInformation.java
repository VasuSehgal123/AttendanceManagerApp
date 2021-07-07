package com.example.attendancemanager;

import java.util.ArrayList;

public class StudentInformation {

    public String name,rollno,department,phone,email;
    String password1;
   public ArrayList<Integer> subjects;
    public StudentInformation(){

    }

    public StudentInformation(String userid,String name, String department, String phone, String email,String password, ArrayList<Integer> subjects) {
        this.name = name;

        this.department = department;
        this.phone = phone;
        this.email = email;
        this.password1=password;
        this.subjects=subjects;
        this.rollno=userid;
    }


}




