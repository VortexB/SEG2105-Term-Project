package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String INTENT_LOGIN_USERNAME="LOGIN_USERNAME";


    private TextView logger;
    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button registerButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logger = (TextView) findViewById(R.id.textViewLogger_Login);
        username = (EditText) findViewById(R.id.editTextUsername_Login);
        password = (EditText) findViewById(R.id.editTextPassword_Login);
        loginButton = (Button) findViewById(R.id.buttonLogin_Login);
        registerButton = (Button) findViewById(R.id.buttonRegister_Login);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

    }

    public void checkLogin(){
        DBHandler dbHandler = new DBHandler(this);
        User user;
        user = dbHandler.findUser(username.getText().toString(),dbHandler.TABLE_ADMIN);
        if(user!=null){
            if(user.getPassword().equals(password.getText().toString())){
                Intent intent = new Intent(this,AdminActivity.class);
                intent.putExtra(INTENT_LOGIN_USERNAME,username.getText().toString());
                startActivity(intent);
                return;
            }
            logger.setText("Incorrect Password");
            return;
        }
        user = dbHandler.findUser(username.getText().toString(),dbHandler.TABLE_INSTRUCTOR);
        if(user!=null){
            if(user.getPassword().equals(password.getText().toString())){
                Intent intent = new Intent(this,ActivityInstructor.class);
                intent.putExtra(INTENT_LOGIN_USERNAME,username.getText().toString());
                startActivity(intent);
                return;
            }
            logger.setText("Incorrect Password");
            return;

        }
        user = dbHandler.findUser(username.getText().toString(),dbHandler.TABLE_STUDENT);
        if(user!=null){
            if(user.getPassword().equals(password.getText().toString())){
                Intent intent = new Intent(this,ActivityStudent.class);
                intent.putExtra(INTENT_LOGIN_USERNAME,username.getText().toString());
                startActivity(intent);
                return;
            }
            logger.setText("Incorrect Password");
            return;
        }
        logger.setText("Incorrect Username");


    }
    public void openRegisterActivity(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}