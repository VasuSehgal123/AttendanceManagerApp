package com.example.attendancemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;



public class Login1 extends AppCompatActivity {
 public static String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }



    public void launchSecondActivity(Login1 view) {

        Intent intent = new Intent(this, Login2.class);


        startActivity(intent);
    }

    public void adminclick(View view) {
        category="Admin";
        launchSecondActivity(this);
    }

    public void facultyclick(View view) {
        category="Faculty";
        launchSecondActivity(this);
    }

    public void studentclick(View view) {
        category="Student";
        launchSecondActivity(this);
    }
}