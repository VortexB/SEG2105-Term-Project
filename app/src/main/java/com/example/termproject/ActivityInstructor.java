package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityInstructor extends AppCompatActivity {


    private TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);


        welcome = (TextView) findViewById(R.id.textViewWelcome_Student);


        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.INTENT_LOGIN_USERNAME);
        welcome.setText("Welcome instructor "+ name+"!");
    }
}